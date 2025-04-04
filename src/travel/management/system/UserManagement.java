package travel.management.system;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class UserManagement {
    private JFrame frame;
    private JTable userTable;
    private DefaultListModel<String> userListModel;
    private Connection conn;

    public UserManagement() {
        frame = new JFrame("User Management");
        userListModel = new DefaultListModel<>();
        JList<String> userList = new JList<>(userListModel);
        JButton deleteBtn = new JButton("Delete User");
        JButton backBtn = new JButton("Back");

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(userList), BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteBtn);
        buttonPanel.add(backBtn);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Database Connection
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourism_management", "root", "Krishna@1234");
            System.out.println("✅ Connected to Database");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Database Connection Failed!", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return;
        }

        // Load Users from Database
        loadUsers();

        // Delete User Action
        deleteBtn.addActionListener(e -> {
            String selectedUser = userList.getSelectedValue();
            if (selectedUser == null) {
                JOptionPane.showMessageDialog(frame, "Please select a user to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete " + selectedUser + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                deleteUser(selectedUser);
            }
        });

        // Back Button Action
        backBtn.addActionListener(e -> {
            frame.dispose();
            new Dashboard();
        });

        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void loadUsers() {
        userListModel.clear();
        try {
            String query = "SELECT user_id, email FROM users";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                userListModel.addElement(rs.getInt("user_id") + " - " + rs.getString("email"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error loading users!", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void deleteUser(String selectedUser) {
        try {
            int userId = Integer.parseInt(selectedUser.split(" - ")[0]); // Extract user_id

            String query = "DELETE FROM users WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(frame, "User deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadUsers(); // Refresh the list
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to delete user!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new UserManagement();
    }
}
