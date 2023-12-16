package ConcreteCommand;

import java.sql.*;

import static config.config.DATABASE_PASSWORD;
import static config.config.DATABASE_USER;

public class SelectCustomer {

    public static boolean selectCustomerByName(String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Load the MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Assume you've set the database connection
            String url = "jdbc:mysql://localhost:3306/comparison?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            String user = DATABASE_USER; // Replace with the actual username
            String pass = DATABASE_PASSWORD; // Replace with the actual password

            // Establish a connection to the database
            conn = DriverManager.getConnection(url, user, pass);

            // Prepare a statement to select the customer
            String checkSql = "SELECT COUNT(*) FROM customer WHERE username = ?";
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setString(1, name);

            // Execute the query
            rs = pstmt.executeQuery();

            // Check if the customer with the given name exists
            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Customer found
            } else {
                return false; // Customer not found
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false; // Returning false assuming failure is synonymous with not found
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Returning false assuming failure is synonymous with not found
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
