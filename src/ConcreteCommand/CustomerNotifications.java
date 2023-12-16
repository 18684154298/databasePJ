package ConcreteCommand;

import java.sql.*;

import static config.config.DATABASE_PASSWORD;
import static config.config.DATABASE_USER;

public class CustomerNotifications {

    private static final String user = DATABASE_USER; // Replace with actual database username
    private static final String password = DATABASE_PASSWORD; // Replace with actual database password

    // Method to display all notifications for a user by username
    public static void notice(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Database connection setup
            String url = "jdbc:mysql://localhost:3306/comparison?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            conn = DriverManager.getConnection(url, user, password);

            // Step 1: Find user ID by username
            int userId = -1;
            String findUserIdSql = "SELECT id FROM customer WHERE username = ?";
            pstmt = conn.prepareStatement(findUserIdSql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("id");
            } else {
                System.out.println("User not found.");
                return;
            }

            // Step 2: Find all notifications for the user ID
            String findNotificationsSql = "SELECT n.content FROM notification n " +
                    "JOIN notice no ON n.ID = no.n_id " +
                    "WHERE no.c_id = ?";
            pstmt = conn.prepareStatement(findNotificationsSql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            // Print out all notifications
            while (rs.next()) {
                String content = rs.getString("content");
                System.out.println("Notification: " + content);
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
}
