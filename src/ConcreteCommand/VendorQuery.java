package ConcreteCommand;
import java.math.BigDecimal;
import java.sql.*;

import static config.config.DATABASE_PASSWORD;
import static config.config.DATABASE_USER;

public class VendorQuery {
    // 1. 根据商家名查询商家的其余信息
    public static void queryVendorInfo(String vendorName) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/comparison?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String user = DATABASE_USER; // Replace with actual database username
        String pass = DATABASE_PASSWORD; // Replace with actual database password
        conn = DriverManager.getConnection(url, user, pass);

        String query = "SELECT * FROM vendor WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, vendorName);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // 假设vendor表有ID, name, address字段
                    System.out.println("ID: " + rs.getInt("ID") + ", Name: " + rs.getString("name") + ", Address: " + rs.getString("address"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void queryCustomerInfo(String username) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/comparison?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String user = DATABASE_USER; // Replace with actual database username
        String pass = DATABASE_PASSWORD; // Replace with actual database password
        Connection conn = DriverManager.getConnection(url, user, pass);

        String query = "SELECT * FROM customer WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // 假设vendor表有ID, name, address字段
                    System.out.println("ID: " + rs.getInt("ID") + ", Name: " + rs.getString("name") + ", Gender: " + rs.getString("gender") +  ", age: "+ rs.getInt("age") +", phone: "+rs.getString("phone"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. 根据商家名查询所有该商家已经sell的商品
    public static void queryVendorCommodities(String vendorName) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/comparison?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String user = DATABASE_USER; // Replace with actual database username
        String pass = DATABASE_PASSWORD; // Replace with actual database password
        conn = DriverManager.getConnection(url, user, pass);

        String query = "SELECT c.* FROM Commodity c JOIN sell s ON c.ID = s.Commodity_ID JOIN vendor v ON s.Vendor_ID = v.ID WHERE v.name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, vendorName);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // 假设Commodity表有ID, name, category, origin, MFD字段
                    System.out.println("ID: " + rs.getInt("ID") + ", Name: " + rs.getString("name") + ", Category: " + rs.getString("category") + ", Origin: " + rs.getString("origin") + ", Manufacture Date: " + rs.getDate("MFD"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. 根据商家名和商品名查询这个商品在所有平台上的最新价格
    public static void queryLatestPrice(String vendorName, String commodityName) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/comparison?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String user = DATABASE_USER; // Replace with actual database username
        String pass = DATABASE_PASSWORD; // Replace with actual database password
        conn = DriverManager.getConnection(url, user, pass);
        int commodityId = -1;
        String query = "SELECT c.ID FROM Commodity c " +
                "JOIN sell s ON c.ID = s.Commodity_ID " +
                "JOIN vendor v ON s.Vendor_ID = v.ID " +
                "WHERE v.name = ? AND c.name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {//获取该商品id
            pstmt.setString(1, vendorName);
            pstmt.setString(2, commodityName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    commodityId = rs.getInt("ID");
                } else {
                    System.out.println("Commodity not found with name: " + commodityName + " for vendor: " + vendorName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(commodityId!=-1){//获取该商品在每个平台上的最新价格
            query = "SELECT p.name, tp.price FROM Platform p " +
                    "JOIN time_price tp ON p.ID = tp.p_id " +
                    "WHERE tp.c_id = ? " +
                    "AND tp.time = (SELECT MAX(time) FROM time_price WHERE c_id = ? AND p_id = tp.p_id) " +
                    "ORDER BY p.name";

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, commodityId);
                pstmt.setInt(2, commodityId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String platformName = rs.getString(1);
                        BigDecimal latestPrice = rs.getBigDecimal(2);
                        System.out.println("Platform: " + platformName + ", Latest Price: " + latestPrice);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // ... (main method and other parts of the class)
}

