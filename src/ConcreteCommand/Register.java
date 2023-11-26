package ConcreteCommand;
import sql.SQLReader;

import java.sql.*;

import static config.config.*;
import java.sql.DriverManager;

public class Register {
    public static void registerUser(String username, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // 假设您已经设置了数据库连接
            String url = DATABASE_URL;
            String user = DATABASE_USER;
            String pass = DATABASE_PASSWORD;

            conn = DriverManager.getConnection(url, user, pass);

            String sql = SQLReader.readSQL(BASEPATH+"src/sql/insert_user.sql");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("User registered successfully.");
            } else {
                System.out.println("User registration failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
