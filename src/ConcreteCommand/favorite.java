package ConcreteCommand;

import java.sql.*;

import static config.config.DATABASE_PASSWORD;
import static config.config.DATABASE_USER;

public class favorite {
    public static void favorite(String username,int commodityID,int threshold) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/comparison?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String user = DATABASE_USER; // Replace with actual database username
        String pass = DATABASE_PASSWORD; // Replace with actual database password
        conn = DriverManager.getConnection(url, user, pass);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // 1. Find customer id
            int customerId = -1;
            String customerQuery = "SELECT id FROM customer WHERE username = ?";
            pstmt = conn.prepareStatement(customerQuery);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                customerId = rs.getInt("id");
            } else {
                throw new SQLException("No customer found with username: " + username);
            }

            // 2. Check if commodityID exists
            String commodityQuery = "SELECT ID FROM Commodity WHERE ID = ?";
            pstmt = conn.prepareStatement(commodityQuery);
            pstmt.setInt(1, commodityID);
            rs = pstmt.executeQuery();
            if (!rs.next()) {
                throw new SQLException("No commodity found with ID: " + commodityID);
            }

            // 3. Insert into favorite
            String insertFavoriteSql = "INSERT INTO favorite (cu_id, co_id, threshold) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(insertFavoriteSql);
            pstmt.setInt(1, customerId);
            pstmt.setInt(2, commodityID);
            pstmt.setInt(3, threshold);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting favorite failed, no rows affected.");
            }

            System.out.println("Favorite commodity added successfully.");
        } finally {
            // Close all resources
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }
}
