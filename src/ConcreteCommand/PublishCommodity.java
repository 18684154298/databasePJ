package ConcreteCommand;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;

import static config.config.DATABASE_PASSWORD;
import static config.config.DATABASE_USER;

public class PublishCommodity {
    public static void publishCommodity(String vendorName, String commodityName, String platformName, BigDecimal price) {
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
            int vendorId = findVendorId(conn, vendorName);
            int commodityId = findCommodityId(conn, vendorId,commodityName);
            int platformId = findPlatformId(conn, platformName);

            // Step 1: Find vendor ID, commodity ID, and platform ID
            // ... (You need to write SQL and logic to find these IDs based on the names provided)

            // Assume we found the IDs and they are stored in vendorId, commodityId, platformId variables

            // Step 2: Check if the vendor has already published the commodity
            // ... (Write SQL to check this condition)

            // Step 3: If not, insert into publish and time_price tables
            // ... (Write SQL to perform the insert operations)

            // Example insert into publish table (You need to adjust the SQL according to your schema)
            if (!isCommodityPublished(conn, commodityId, platformId)) {
                // Step 3: If not, insert into publish and time_price tables
                // Insert into publish table
                String insertPublishSql = "INSERT INTO publish (c_id, p_id, modified) VALUES (?, ?, 0)";
                pstmt = conn.prepareStatement(insertPublishSql);
                pstmt.setInt(1, commodityId);
                pstmt.setInt(2, platformId);
                pstmt.executeUpdate();

                // Insert into time_price table
                String insertTimePriceSql = "INSERT INTO time_price (c_id, p_id, price, time) VALUES (?, ?, ?, ?)";
                pstmt = conn.prepareStatement(insertTimePriceSql);
                pstmt.setInt(1, commodityId);
                pstmt.setInt(2, platformId);
                pstmt.setBigDecimal(3, price); // Use the price parameter passed to the function
                pstmt.setTimestamp(4, new Timestamp(new Date().getTime())); // Current time
                pstmt.executeUpdate();

                System.out.println("Commodity published successfully.");
            } else {
                System.out.println("This commodity has already been published on the platform.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Close all resources
            try {
                if (rs != null) rs.close();
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
        String query = "SELECT 1 FROM publish WHERE c_id = ? AND p_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, commodityId);
            pstmt.setInt(2, platformId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // If there's a record, return true, meaning it's published.
            }
        }
    }

}
