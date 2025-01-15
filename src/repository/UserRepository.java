package repository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import model.Response;
import model.User;

import java.sql.*;
import java.util.ArrayList;

public class UserRepository {
    public static Response<ArrayList<User>> getAllUsers(){
        Connection conn = null;

        ArrayList<User> users = new ArrayList<>();
        try {
            conn = Repository.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Users;");
            if(!rs.next()){
                return new Response<>("Error not any user in database");
            }
            do{
                User t = new User(rs.getInt("IdUser"),rs.getString("Email"),rs.getString("Password"),rs.getString("Username"),rs.getInt("IdRole"));
                users.add(t);
            }while(rs.next());
            return new Response<>(users);
        } catch (SQLException e) {
            return new Response<>("SQL Error : "+e.getMessage());
        }finally {
            Repository.closeConnection(conn);
        }
    }

    public static String createUser(String username,String email, String password, int idRole){
        Connection conn =null;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);
        try{
            conn = Repository.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Users(Email, Password, Username, IdRole) VALUE(?,?,?,?)");
            pstmt.setString(1,email);
            pstmt.setString(2,hashedPassword);
            pstmt.setString(3,username);
            pstmt.setInt(4,idRole);
            pstmt.executeUpdate();
            return "User Created Successfully";
        }catch(SQLException e){
            return "SQL ERROR : "+e.getMessage();
        }finally {
            Repository.closeConnection(conn);
        }
    }

    public static Response<User> login(String email, String password) {
        Connection conn = null;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        try {
            conn = Repository.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Users WHERE Email=?");
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                return new Response<>("Incorrect Email or Password");
            }
            if (!encoder.matches(password, rs.getString("Password"))) {
                return new Response<>("Incorrect Email or Password");
            }else{
                return new Response<>(new User(rs.getInt("IdUser"),rs.getString("Email"),rs.getString("Password"),rs.getString("Username"),rs.getInt("IdRole")));
            }
        } catch (SQLException e) {
            return new Response<>("SQL ERROR : " + e.getMessage());
        }finally{
            Repository.closeConnection(conn);
        }
    }
}
