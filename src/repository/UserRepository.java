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
            ResultSet rs = stmt.executeQuery("SELECT * FROM IStore.Users;");
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

    public static Response<User> getUserById(int id){
        Connection conn = null;
        try{
            conn = Repository.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM IStore.Users WHERE IdUser="+id+";");
            if(!rs.next()){
                return new Response<>("No User found with that Id");
            }else {
                return new Response<>(new User(rs.getInt("IdUser"),rs.getString("Email"),rs.getString("Password"),rs.getString("Username"),rs.getInt("IdRole")));
            }
        }catch (SQLException e){
            return new Response<>("SQL ERROR : "+e.getMessage());
        }finally {
            Repository.closeConnection(conn);
        }
    }

    public static Response<Boolean> isUsernameExisting(String username) {
        Connection conn = null;
        try {
            conn = Repository.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM IStore.Users WHERE Username = ?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Response<>(true);
            }
            return new Response<>(false);
        } catch (SQLException e) {
            return new Response<>("SQL ERROR: " + e.getMessage());
        } finally {
            Repository.closeConnection(conn);
        }
    }

    public static Response<Boolean> isEmailExisting(String email) {
        Connection conn = null;
        try {
            conn = Repository.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM IStore.Users WHERE Email = ?");
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Response<>(true);
            }
            return new Response<>(false);
        } catch (SQLException e) {
            return new Response<>("SQL ERROR: " + e.getMessage());
        } finally {
            Repository.closeConnection(conn);
        }
    }

    public static Response<ArrayList<User>> getAllEmployees(){
        Connection conn = null;

        ArrayList<User> users = new ArrayList<>();
        try {
            conn = Repository.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM IStore.Users WHERE IdRole=2;");
            if(!rs.next()){
                return new Response<>("Error not any employees in database");
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

    public static Response<ArrayList<User>> getAllAdmin(){
        Connection conn = null;

        ArrayList<User> users = new ArrayList<>();
        try {
            conn = Repository.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM IStore.Users WHERE IdRole=1;");
            if(!rs.next()){
                return new Response<>("Error not any administrator in database");
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

    public static Response<User> createUser(String username,String email, String password, int idRole){
        Connection conn =null;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);
        try{
            conn = Repository.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO IStore.Users(Email, Password, Username, IdRole) VALUE(?,?,?,?)");
            pstmt.setString(1,email);
            pstmt.setString(2,hashedPassword);
            pstmt.setString(3,username);
            pstmt.setInt(4,idRole);
            pstmt.executeUpdate();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(IdUser) FROM IStore.Users");
            rs.next();
            return new Response<>(new User(rs.getInt(1),email,password,username,idRole));
        }catch(SQLException e){
            return new Response<>("SQL ERROR : "+e.getMessage());
        }finally {
            Repository.closeConnection(conn);
        }
    }

    public static Response<User> login(String email, String password) {
        Connection conn = null;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        try {
            conn = Repository.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM IStore.Users WHERE Email=?");
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

    public static Response<User> updateUser(User updatedUser) {
        Connection conn = null;

        try{
            conn = Repository.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("UPDATE IStore.Users SET Email = ?, Password = ?, Username = ?, IdRole = ? WHERE IdUser = ?;");
            pstmt.setString(1, updatedUser.getEmail());
            pstmt.setString(2, updatedUser.getPassword());
            pstmt.setString(3, updatedUser.getUsername());
            pstmt.setInt(4, updatedUser.getRoleId());
            pstmt.setInt(5, updatedUser.getIdUser());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0){
                return new Response<>(updatedUser);
            }else{
                return new Response<>("User not found or no changes made");
            }
        }catch(SQLException e){
            return new Response<>("SQL ERROR : "+e.getMessage());
        }finally{
            Repository.closeConnection(conn);
        }
    }

    public static String deleteUser(int idUser) {
        Connection conn = null;
        try{
            conn = Repository.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM IStore.Users WHERE IdUser = ?;");
            pstmt.setInt(1, idUser);
            int affectedRows = pstmt.executeUpdate();
            if(affectedRows==0){
                return "No user with that Id";
            }
            return "User Deleted Successfully";
        }catch(SQLException e){
            return "SQL ERROR : "+e.getMessage();
        }finally {
            Repository.closeConnection(conn);
        }
    }

    public static Response<ArrayList<Integer>> getIdUser() {
        Connection conn = null;

        ArrayList<Integer> idList = new ArrayList<>();

        try{
            conn = Repository.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT idUser FROM IStore.Users");

            while (rs.next()) {
                idList.add(rs.getInt("IdUser"));
            }

            if (idList.isEmpty()) {
                return new Response<>("No users found.");
            }

            return new Response<>(idList);
        } catch (SQLException e) {
            return new Response<>("SQL ERROR: " + e.getMessage());
        } finally {
            Repository.closeConnection(conn);
        }

    }

}


