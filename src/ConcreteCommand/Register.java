package ConcreteCommand;
import sql.SQLReader;

import java.sql.*;

import static config.config.*;
import java.sql.DriverManager;

public class Register {
    public static void registerCostumer(String name, String age,String gender,String tel) throws SQLException {
        Connection conn = null;
        int ageInt = Integer.parseInt(age);
        String url = DATABASE_URL;
        String user = DATABASE_USER;
        String pass = DATABASE_PASSWORD;

        conn = DriverManager.getConnection(url, user, pass);

        // Prepare the SQL statement to insert a new customer
        String sql = "INSERT INTO customer (username, age, gender, phone) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set the parameters for the prepared statement
            pstmt.setString(1, name);
            pstmt.setInt(2, ageInt);
            pstmt.setString(3, gender);
            pstmt.setString(4, tel);

            // Execute the insert statement
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Customer registered successfully.");
            } else {
                System.out.println("Customer registration failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
