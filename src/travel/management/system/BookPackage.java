package travel.management.system;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class BookPackage {
    private Connection conn;

    public BookPackage(int userId) { // Accept user_id instead of email
        JFrame frame = new JFrame("Book Tour Package");
        JLabel packageLabel = new JLabel("Select Tour Package:");
        JComboBox<String> packageDropdown = new JComboBox<>();
        JButton bookBtn = new JButton("Book Tour");
        JButton backBtn = new JButton("Back");

        // Establish database connection
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourism_management", "root", "Krishna@1234");
            System.out.println("✅ Database Connected Successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Database Connection Failed!", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return;
        }

        // Fetch available packages
        try {
            String query = "SELECT package_name FROM packages";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                packageDropdown.addItem(rs.getString("package_name"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Failed to load packages!", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        frame.setLayout(new GridLayout(3, 1));
        frame.add(packageLabel);
        frame.add(packageDropdown);
        frame.add(bookBtn);
        frame.add(backBtn);

        // Book Tour Button Action
        bookBtn.addActionListener(e -> {
            String selectedPackage = (String) packageDropdown.getSelectedItem();
            if (selectedPackage == null) {
                JOptionPane.showMessageDialog(frame, "No package selected!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Fetch package_id
                String packageQuery = "SELECT package_id FROM packages WHERE package_name = ?";
                PreparedStatement pkgStmt = conn.prepareStatement(packageQuery);
                pkgStmt.setString(1, selectedPackage);
                ResultSet rs = pkgStmt.executeQuery();

                if (rs.next()) {
                    int packageId = rs.getInt("package_id");

                    // Insert booking into database
                    String bookingQuery = "INSERT INTO bookings (user_id, package_id, status) VALUES (?, ?, 'pending')";
                    PreparedStatement bookingStmt = conn.prepareStatement(bookingQuery);
                    bookingStmt.setInt(1, userId);
                    bookingStmt.setInt(2, packageId);
                    int rowsInserted = bookingStmt.executeUpdate();

                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(frame, "Tour booked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                        new Dashboard(); // Open dashboard after booking
                    } else {
                        JOptionPane.showMessageDialog(frame, "Booking failed!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid package selection!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // Back Button Action
        backBtn.addActionListener(e -> {
            frame.dispose();
            new Dashboard();
        });

        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new BookPackage(1); // Test with sample user_id = 1
    }
}
