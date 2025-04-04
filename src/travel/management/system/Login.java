package travel.management.system;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class Login {
    public Login() {
        JFrame frame = new JFrame("Login");
        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");
        JTextField emailField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");
        
        frame.setLayout(new GridLayout(3, 2));
        frame.add(emailLabel); frame.add(emailField);
        frame.add(passwordLabel); frame.add(passwordField);
        frame.add(loginBtn); frame.add(registerBtn);
        
        loginBtn.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourism_management", "root", "Krishna@1234")) {
                String query = "SELECT * FROM users WHERE email = ? AND password = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, email);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    JOptionPane.showMessageDialog(frame, "Login Successful!");
                    frame.dispose();
                    new Dashboard(); // Ensure Dashboard.java exists
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid email or password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Database connection error!", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        
        registerBtn.addActionListener(e -> {
            frame.dispose();
            new Register(); // Ensure Register.java exists
        });
        
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new Login();
    }
}


