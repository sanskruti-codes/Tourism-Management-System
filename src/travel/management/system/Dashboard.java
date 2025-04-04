package travel.management.system;

import java.awt.*;
import javax.swing.*;

public class Dashboard {
    public Dashboard() {
        JFrame frame = new JFrame("Dashboard");
        JButton bookingBtn = new JButton("Booking System");
        JButton paymentsBtn = new JButton("Payments");
        JButton userManagementBtn = new JButton("User Management");
        JButton logoutBtn = new JButton("Logout");
        
        frame.setLayout(new GridLayout(4, 1));
        frame.add(bookingBtn);
        frame.add(paymentsBtn);
        frame.add(userManagementBtn);
        frame.add(logoutBtn);
        
        bookingBtn.addActionListener(e -> {
            frame.dispose();
            //new BookPackage();
            new BookPackage(1);
        });
        
        paymentsBtn.addActionListener(e -> {
            frame.dispose();
            new Payment();
        });
        
        userManagementBtn.addActionListener(e -> {
            frame.dispose();
            new UserManagement();
        });
        
        logoutBtn.addActionListener(e -> {
            frame.dispose();
            new Login();
        });
        
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new Dashboard();
    }
}
