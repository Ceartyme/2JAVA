package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Repository {
    private final static String BDD_name = "tripAgency";
    private final static String url = "jdbc:mysql://localhost:3306/" + BDD_name;
    private final static String user = "root";
    private final static String password = "root";

    private Repository() {
    }

    public static Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ex){
            System.out.println("Can't load Driver");
            System.exit(1);
        } catch (SQLException e) {
            System.out.println("Can't connect to database");
            System.exit(1);
        }
        return conn;
    }

    public static void closeConnection(Connection conn){
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Can't close connection to database");
        }
    }
}
