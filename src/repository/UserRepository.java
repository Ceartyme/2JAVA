package repository;

import model.Response;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserRepository {
    public static Response<ArrayList<User>> getAllUsers(){
        Connection conn = null;
        ArrayList<User> users = new ArrayList<>();
        try {
            conn = Repository.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Users;");
            if(rs==null){
                return new Response<>("Error not any user in database");
            }
            while(rs.next()){
                User t = new User(rs.getInt("IdUser"),rs.getString("Email"),rs.getString("Password"),rs.getString("Username"),rs.getInt("IdRole"));
                users.add(t);
            }
            return new Response<>(users);
        } catch (SQLException e) {
            return new Response<>("SQL Error : "+e.getMessage());
        }finally {
            Repository.closeConnection(conn);
        }
    }
}
