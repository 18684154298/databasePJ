package ConcreteCommand;

import java.sql.*;

import static config.config.DATABASE_PASSWORD;
import static config.config.DATABASE_USER;

public class CreateCommodity {

    public static void addCommodity(String vendorName, String commodityName, String category, String origin, Date mfd) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt_nameCheck = null;
        ResultSet rs = null;
        ResultSet nameCheck = null;
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Database connection setup
            String url = "jdbc:mysql://localhost:3306/comparison?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            String user = DATABASE_USER; // 替换为实际的用户名
            String pass = DATABASE_PASSWORD; // 替换为实际的密码

            // Establish a connection to the database
            conn = DriverManager.getConnection(url, user, pass);

            String checkSql = "SELECT 1 FROM vendor v " +
                    "JOIN sell s ON v.ID = s.Vendor_ID " +
                    "JOIN commodity c ON s.Commodity_ID = c.ID " +
                    "WHERE v.Name = ? AND c.Name = ?";
            pstmt_nameCheck = conn.prepareStatement(checkSql);
            pstmt_nameCheck.setString(1, vendorName);
            pstmt_nameCheck.setString(2, commodityName);
            nameCheck = pstmt_nameCheck.executeQuery();

            // Step 1: Find vendor ID by vendor name
            String vendorIdSql = "SELECT ID FROM vendor WHERE name = ?";
            pstmt = conn.prepareStatement(vendorIdSql);
            pstmt.setString(1, vendorName);
            rs = pstmt.executeQuery();

            if (!nameCheck.next()&&rs.next()) {
                int vendorId = rs.getInt("ID");

                // Step 2: Insert commodity into Commodity table
                String insertCommoditySql = "INSERT INTO Commodity (name, category, origin, MFD) VALUES (?, ?, ?, ?)";
                pstmt = conn.prepareStatement(insertCommoditySql, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, commodityName);
                pstmt.setString(2, category);
                pstmt.setString(3, origin);
                pstmt.setDate(4, mfd);
                int affectedRowsCommodity = pstmt.executeUpdate();

                // Retrieve commodity ID
                if (affectedRowsCommodity > 0) {
                    ResultSet generatedKeys = pstmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int commodityId = generatedKeys.getInt(1);

                        // Step 3: Insert into sell table
                        String insertSellSql = "INSERT INTO sell (Commodity_ID, Vendor_ID) VALUES (?, ?)";
                        pstmt = conn.prepareStatement(insertSellSql);
                        pstmt.setInt(1, commodityId);
                        pstmt.setInt(2, vendorId);
                        int affectedRowsSell = pstmt.executeUpdate();

                        if (affectedRowsSell > 0) {
                            System.out.println("Commodity added successfully and associated with the vendor.");
                        } else {
                            System.out.println("Failed to associate commodity with vendor.");
                        }
                    }
                } else {
                    System.out.println("Failed to add commodity.");
                }

            } else {
                System.out.println("Vendor not found or commodity already exists");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
