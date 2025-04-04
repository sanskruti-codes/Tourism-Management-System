package travel.management.system;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class Payment {
    public Payment() {
        JFrame frame = new JFrame("Payment");
        JLabel bookingIdLabel = new JLabel("Booking ID:");
        JLabel amountLabel = new JLabel("Amount:");
        JTextField bookingIdField = new JTextField(20);
        JTextField amountField = new JTextField(20);
        JButton payBtn = new JButton("Make Payment");
        JButton backBtn = new JButton("Back");
        
        frame.setLayout(new GridLayout(3, 2));
        frame.add(bookingIdLabel); frame.add(bookingIdField);
        frame.add(amountLabel); frame.add(amountField);
        frame.add(payBtn); frame.add(backBtn);
        
        payBtn.addActionListener(e -> {
            String bookingId = bookingIdField.getText();
            String amount = amountField.getText();
            
            if (bookingId.isEmpty() || amount.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourism_management", "root", "password")) {
                String query = "INSERT INTO payments (booking_id, amount) VALUES (?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, bookingId);
                stmt.setString(2, amount);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Payment Successful!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        backBtn.addActionListener(e -> {
            frame.dispose();
            new Dashboard();
        });
        
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new Payment();
    }
}
