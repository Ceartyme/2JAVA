package repository;

import model.Response;

import java.sql.*;
import java.util.ArrayList;

public class EmailRepository {
    public static Response<Boolean> isEmailWhitelisted(String email){
        Connection conn = null;
        try {
            conn = Repository.getConnection();
            conn.createStatement().executeQuery("USE IStore;");
            PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM WhitelistedEmail WHERE Email=?");
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return new Response<>(rs.getInt(1)==1);
        } catch (SQLException e) {
            return new Response<>("SQL Error : "+e.getMessage());
        }finally {
            Repository.closeConnection(conn);
        }
    }

    public static Response<ArrayList<String>> getAllEmails(){
        Connection conn = null;
        ArrayList<String> emails = new ArrayList<>();
        try{
            conn = Repository.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeQuery("USE IStore;");
            ResultSet rs = stmt.executeQuery("SELECT * FROM WhitelistedEmail;");
            if(!rs.next()){
                return new Response<>("There are no whitelisted email");
            }
            do{
                emails.add(rs.getString("Email"));
            }while (rs.next());
            return new Response<>(emails);
        }catch (SQLException e){
            return new Response<>("SQL ERROR : "+e.getMessage());
        }finally {
            Repository.closeConnection(conn);
        }
    }

    public static String whitelistEmail(String email){
        Connection conn = null;
        try{
            conn = Repository.getConnection();
            conn.createStatement().executeQuery("USE IStore;");
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO WhitelistedEmail(Email) VALUE(?)");
            pstmt.setString(1,email);
            pstmt.executeUpdate();
            return "Email whitelisted Successfully";
        }catch (SQLException e){
            return "SQL ERROR : "+e.getMessage();
        }finally {
            Repository.closeConnection(conn);
        }
    }
}
