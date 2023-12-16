package ConcreteCommand;

import java.sql.*;

import static config.config.*;

public class Administer {


    // Update a customer's information
    public static void updateCustomer(int customerId, String newName, int newAge, String newGender, String newPhone) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        String sql = "UPDATE customer SET username = ?, age = ?, gender = ?, phone = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setInt(2, newAge);
            pstmt.setString(3, newGender);
            pstmt.setString(4, newPhone);
            pstmt.setInt(5, customerId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Customer information updated successfully.");
            } else {
                System.out.println("Customer information update failed.");
            }
        }
    }

    // Delete a customer
    public static void deleteCustomer(int customerId) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        String sql = "DELETE FROM customer WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Customer deleted successfully.");
            } else {
                System.out.println("Customer deletion failed.");
            }
        }
    }

    // Update a vendor's information
    public static void updateVendor(int vendorId, String newName, String newAddress) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        String sql = "UPDATE vendor SET name = ?, address = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setString(2, newAddress);
            pstmt.setInt(3, vendorId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Vendor information updated successfully.");
            } else {
                System.out.println("Vendor information update failed.");
            }
        }
    }

    // Delete a vendor
    public static void deleteVendor(int vendorId) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        String sql = "DELETE FROM vendor WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, vendorId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Vendor deleted successfully.");
            } else {
                System.out.println("Vendor deletion failed.");
            }
        }
    }
    public static void deleteCommodity(int commodityID) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        String sql = "DELETE FROM Commodity WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, commodityID);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Vendor deleted successfully.");
            } else {
                System.out.println("Vendor deletion failed.");
            }
        }
    }

    // ... (main method and other parts of the class)
}
