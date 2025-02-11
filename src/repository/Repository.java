package repository;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Repository {

    private final static Dotenv dotenv = Dotenv.configure().directory("src/config").load();
    private final static String HOST = dotenv.get("DB_HOST");
    private final static String PORT = dotenv.get("DB_PORT");

    private final static String BDD_name = "IStore";
    private final static String url = "jdbc:mysql://"+HOST+":"+PORT+"/" + BDD_name;
    private final static String USER = dotenv.get("DB_USER");
    private final static String password = dotenv.get("DB_PASSWORD");


    public static void testConnection() throws Exception{
        Connection conn = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, USER, password);
        } catch (ClassNotFoundException ex){
            throw new Exception("Can't load Driver. \nVerify that you have imported the mysql connector library to your project ");
        } catch (SQLException e) {
            throw new Exception("Can't connect to database. \nVerify that your docker container is On and that your datasource is configured well");
        }
    }

    public static Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, USER, password);
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
