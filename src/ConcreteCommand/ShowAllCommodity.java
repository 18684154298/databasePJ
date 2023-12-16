package ConcreteCommand;

import java.sql.*;

import static config.config.DATABASE_PASSWORD;
import static config.config.DATABASE_USER;

public class ShowAllCommodity {
    public static void show_all_commodity() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 假设您已经设置了数据库连接
        String url = "jdbc:mysql://localhost:3306/comparison?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String user = DATABASE_USER; // 替换为实际的用户名
        String pass = DATABASE_PASSWORD; // 替换为实际的密码

        // Establish a connection to the database
        Connection conn = DriverManager.getConnection(url, user, pass);

        String query = "SELECT c.ID as commodity_id, v.name as vendor_name, c.name, c.category, c.origin, c.MFD FROM Commodity c " +
                "JOIN sell s ON c.ID = s.Commodity_ID " +
                "JOIN vendor v ON s.Vendor_ID = v.ID";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int commodityId = rs.getInt("commodity_id");
                String vendorName = rs.getString("vendor_name");
                String commodityName = rs.getString("name");
                String category = rs.getString("category");
                String origin = rs.getString("origin");
                Date mfd = rs.getDate("MFD");

                // Output the commodity information
                System.out.printf("Commodity ID: %d, Vendor Name: %s, Name: %s, Category: %s, Origin: %s, Manufacture Date: %s%n",
                        commodityId, vendorName, commodityName, category, origin, mfd.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        public static void showCommodityById(int commodityIdInput) throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 假设您已经设置了数据库连接
            String url = "jdbc:mysql://localhost:3306/comparison?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            String user = DATABASE_USER; // 替换为实际的用户名
            String pass = DATABASE_PASSWORD; // 替换为实际的密码

            // Establish a connection to the database
            try (Connection conn = DriverManager.getConnection(url, user, pass)) {
                String query = "SELECT c.ID as commodity_id, v.name as vendor_name, c.name, c.category, c.origin, c.MFD FROM Commodity c " +
                        "JOIN sell s ON c.ID = s.Commodity_ID " +
                        "JOIN vendor v ON s.Vendor_ID = v.ID WHERE c.ID = ?";

                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setInt(1, commodityIdInput);
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        int commodityId = rs.getInt("commodity_id");
                        String vendorName = rs.getString("vendor_name");
                        String commodityName = rs.getString("name");
                        String category = rs.getString("category");
                        String origin = rs.getString("origin");
                        Date mfd = rs.getDate("MFD");

                        // Output the commodity information
                        System.out.printf("Commodity ID: %d, Vendor Name: %s, Name: %s, Category: %s, Origin: %s, Manufacture Date: %s%n",
                                commodityId, vendorName, commodityName, category, origin, mfd.toString());
                    } else {
                        System.out.println("No commodity found with ID: " + commodityIdInput);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    public static void show_my_favorite_commodity(String username) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 假设您已经设置了数据库连接
        String url = "jdbc:mysql://localhost:3306/comparison?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String user = DATABASE_USER; // 替换为实际的用户名
        String pass = DATABASE_PASSWORD; // 替换为实际的密码

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            // Step 1: Find user ID by username
            int userId = -1;
            String findUserIdSql = "SELECT id FROM customer WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(findUserIdSql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        userId = rs.getInt("id");
                    } else {
                        System.out.println("User not found.");
                        return;
                    }
                }
            }

            // Step 2: Find favorite commodity IDs for the user
            String findFavoritesSql = "SELECT co_id FROM favorite WHERE cu_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(findFavoritesSql)) {
                pstmt.setInt(1, userId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int commodityId = rs.getInt("co_id");

                        // Step 3: Output commodity information for each found commodity ID
                        String query = "SELECT c.ID as commodity_id, v.name as vendor_name, c.name, c.category, c.origin, c.MFD FROM Commodity c " +
                                "JOIN sell s ON c.ID = s.Commodity_ID " +
                                "JOIN vendor v ON s.Vendor_ID = v.ID WHERE c.ID = ?";
                        try (PreparedStatement commodityPstmt = conn.prepareStatement(query)) {
                            commodityPstmt.setInt(1, commodityId);
                            try (ResultSet commodityRs = commodityPstmt.executeQuery()) {
                                if (commodityRs.next()) {
                                    // Output the commodity information
                                    System.out.printf("Commodity ID: %d, Vendor Name: %s, Name: %s, Category: %s, Origin: %s, Manufacture Date: %s%n",
                                            commodityRs.getInt("commodity_id"), commodityRs.getString("vendor_name"),
                                            commodityRs.getString("name"), commodityRs.getString("category"),
                                            commodityRs.getString("origin"), commodityRs.getDate("MFD").toString());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
