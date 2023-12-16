package ConcreteCommand;

import java.sql.*;

import static config.config.DATABASE_PASSWORD;
import static config.config.DATABASE_USER;

public class SelectVendor {

    public static boolean selectVendorByName(String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 加载MySQL JDBC驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 假设您已经设置了数据库连接
            String url = "jdbc:mysql://localhost:3306/comparison?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            String user = DATABASE_USER; // 替换为实际的用户名
            String pass = DATABASE_PASSWORD; // 替换为实际的密码

            // Establish a connection to the database
            conn = DriverManager.getConnection(url, user, pass);

            // Prepare a statement to select the vendor
            String checkSql = "SELECT COUNT(*) FROM vendor WHERE name = ?";
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setString(1, name);

            // Execute the query
            rs = pstmt.executeQuery();

            // Check if the vendor with the given name exists
            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Vendor found
            } else {
                return false; // Vendor not found
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false; // Returning 0 assuming failure is synonymous with not found
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Returning 0 assuming failure is synonymous with not found
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
