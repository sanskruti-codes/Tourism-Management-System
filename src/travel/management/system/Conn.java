package travel.management.system;

import java.sql.*;

public class Conn {
    Connection conn;
    Statement stmt;
    
    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourism_management", "root", "Krishna@1234");
            stmt = conn.createStatement();
            System.out.println("Connection Successful!");
        } catch (Exception e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }
    }
    
    public Connection getConnection() {
        return conn;
    }
    
    public void closeConnection() {
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
            System.out.println("Connection Closed Successfully!");
        } catch (Exception e) {
            System.out.println("Error Closing Connection!");
            e.printStackTrace();
        }
    }
}

