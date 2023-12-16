package ConcreteCommand;

import java.math.BigDecimal;
import java.sql.*;

import static config.config.DATABASE_PASSWORD;
import static config.config.DATABASE_USER;

public class ModifyCommodityPrice {
    public static void modifyCommodityPrice(String vendorName, String commodityName, String platformName, BigDecimal newPrice) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Database connection setup
            String url = "jdbc:mysql://localhost:3306/comparison?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            String user = DATABASE_USER; // Replace with actual database username
            String pass = DATABASE_PASSWORD; // Replace with actual database password

            // Establish a connection to the database
            conn = DriverManager.getConnection(url, user, pass);

            // Step 1: Find vendor ID, commodity ID, and platform ID
            int vendorId = findVendorId(conn, vendorName);
            int commodityId = findCommodityId(conn, vendorId, commodityName);
            int platformId = findPlatformId(conn, platformName);

            // Step 2: Check if the commodity has been published on the platform
            if (isCommodityPublished(conn, commodityId, platformId)) {
                // The commodity is published, proceed to modify the price

                // Insert a new price record into time_price table
                String insertTimePriceSql = "INSERT INTO time_price (c_id, p_id, price, time) VALUES (?, ?, ?, ?)";
                pstmt = conn.prepareStatement(insertTimePriceSql);
                pstmt.setInt(1, commodityId);
                pstmt.setInt(2, platformId);
                pstmt.setBigDecimal(3, newPrice); // Use the new price passed to the function
                pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis())); // Current time
                pstmt.executeUpdate();
                String selectFavoritesSql = "SELECT cu_id, threshold FROM favorite WHERE co_id = ?";
                pstmt = conn.prepareStatement(selectFavoritesSql);
                pstmt.setInt(1, commodityId);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    int customerId = rs.getInt("cu_id");
                    BigDecimal threshold = rs.getBigDecimal("threshold");
                    if (newPrice.compareTo(threshold) < 0) {
                        // Price is below the threshold, create a notification
                        String notificationText = String.format("您关注的%s在%s上已经降价到了%s元，快去看看吧！",commodityName, platformName, newPrice.toPlainString());
                        String insertNotificationSql = "INSERT INTO notification (content) VALUES (?)";
                        try (PreparedStatement notificationPstmt = conn.prepareStatement(insertNotificationSql, Statement.RETURN_GENERATED_KEYS)) {
                            notificationPstmt.setString(1, notificationText);
                            notificationPstmt.executeUpdate();
                            try (ResultSet generatedKeys = notificationPstmt.getGeneratedKeys()) {
                                if (generatedKeys.next()) {
                                    int notificationId = generatedKeys.getInt(1);

                                    // Insert notice for the customer
                                    String insertNoticeSql = "INSERT INTO notice (n_id, c_id) VALUES (?, ?)";
                                    try (PreparedStatement noticePstmt = conn.prepareStatement(insertNoticeSql)) {
                                        noticePstmt.setInt(1, notificationId);
                                        noticePstmt.setInt(2, customerId);
                                        noticePstmt.executeUpdate();
                                    }
                                }
                            }
                        }
                    }
                }

                System.out.println("Commodity price updated successfully.");
            } else {
                System.out.println("This commodity has already been modified today or is not published.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Close all resources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    private static int findVendorId(Connection conn, String vendorName) throws SQLException {
        String query = "SELECT ID FROM vendor WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, vendorName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID");
                } else {
                    throw new SQLException("Vendor not found with name: " + vendorName);
                }
            }
        }
    }

    private static int findCommodityId(Connection conn, int vendorId, String commodityName) throws SQLException {
        String query = "SELECT c.ID FROM Commodity c " +
                "JOIN sell s ON c.ID = s.Commodity_ID " +
                "WHERE s.Vendor_ID = ? AND c.name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, vendorId);
            pstmt.setString(2, commodityName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID");
                } else {
                    throw new SQLException("Commodity not found with name: " + commodityName + " for vendor ID: " + vendorId);
                }
            }
        }
    }

    private static int findPlatformId(Connection conn, String platformName) throws SQLException {
        String query = "SELECT ID FROM Platform WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, platformName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID");
                } else {
                    throw new SQLException("Platform not found with name: " + platformName);
                }
            }
        }
    }

    private static boolean isCommodityPublished(Connection conn, int commodityId, int platformId) throws SQLException {
        String query = "SELECT modified FROM publish WHERE c_id = ? AND p_id = ?";
        boolean canModify = false;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, commodityId);
            pstmt.setInt(2, platformId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Check if the commodity's price has already been modified today
                    canModify = rs.getInt("modified") == 0;
                    if (canModify) {
                        // If not modified today, update the modified value to 1
                        String updateModifiedSql = "UPDATE publish SET modified = 1 WHERE c_id = ? AND p_id = ?";
                        try (PreparedStatement updatePstmt = conn.prepareStatement(updateModifiedSql)) {
                            updatePstmt.setInt(1, commodityId);
                            updatePstmt.setInt(2, platformId);
                            updatePstmt.executeUpdate();
                        }
                    }
                }
            }
        }
        return canModify;
    }

}
