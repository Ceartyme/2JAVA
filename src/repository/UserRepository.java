package repository;

import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserRepository {
    public static ArrayList<User> getAllUsers(){
        Connection conn = null;
        ArrayList<User> users = new ArrayList<User>();
        try {
            conn = Repository.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Users;");
            if(rs==null){
                System.out.println("Error not any user in database");
                return users;
            }
            while(rs.next()){
                User t = new User(rs.getInt("IdUser"),rs.getString("Email"),rs.getString("Password"),rs.getString("Username"),rs.getInt("IdRole"));
                users.add(t);
            }
            return users;
        } catch (SQLException e) {
            System.out.println("SQL Error : "+e.getMessage());
        }finally {
            Repository.closeConnection(conn);
        }
        return null;
    }


}
