package travel.management.system;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class ViewPackages extends JFrame {
    JTable table;
    JButton backButton;

    public ViewPackages() {
        setTitle("Available Tour Packages");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        String[] columnNames = {"package_id", "package_name", "Destination", "Price","Duration"};
        String[][] data = fetchPackages();

        table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.addActionListener(e -> dispose());
        add(backButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private String[][] fetchPackages() {
        try {
            Conn c = new Conn();
            String query = "SELECT package_id, package_name, destination, price, duration FROM packages";
            //PreparedStatement stmt = c.conn.prepareStatement(query);
            PreparedStatement stmt = c.conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery();

            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            String[][] data = new String[rowCount][5];
            int i = 0;
            while (rs.next()) {
                data[i][0] = String.valueOf(rs.getInt("package_id"));
                data[i][1] = rs.getString("package_name");
                data[i][2] = rs.getString("destination");
                data[i][3] = rs.getString("price");
                data[i][4] = rs.getString("duration");
                i++;
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return new String[0][0];
        }
    }

    public static void main(String[] args) {
        new ViewPackages();
    }
}

