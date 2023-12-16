package ConcreteCommand;

import java.sql.*;

import static config.config.DATABASE_PASSWORD;
import static config.config.DATABASE_USER;

public class RegisterVendor {

    public static void registerVendor(String name, String address) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 假设您已经设置了数据库连接
            String url = "jdbc:mysql://localhost:3306/comparison?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            String user = DATABASE_USER; // 替换为实际的用户名
            String pass = DATABASE_PASSWORD; // 替换为实际的密码

            // Establish a connection to the database
            conn = DriverManager.getConnection(url, user, pass);

            // Check if the vendor name already exists
            String checkSql = "SELECT COUNT(*) FROM vendor WHERE name = ?";
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            // If the vendor name does not exist, insert the new vendor
            if (rs.next() && rs.getInt(1) == 0) {
                String insertSql = "INSERT INTO vendor (name, address) VALUES (?, ?)";
                pstmt.close(); // Close the previous prepared statement

                pstmt = conn.prepareStatement(insertSql);
                pstmt.setString(1, name);
                pstmt.setString(2, address);

                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Vendor registered successfully.");
                } else {
                    System.out.println("Vendor registration failed.");
                }
            } else {
                System.out.println("Vendor with this name already exists.");
            }
        } catch (SQLException e) {
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
}
