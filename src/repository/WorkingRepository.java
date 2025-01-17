package repository;

import model.Response;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class WorkingRepository {
    public static String hire(int idStore, int idUser){
        Connection conn = null;
        try {
            conn = Repository.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO IStore.WORKING(IdStore, IdUser) VALUE ("+idStore+","+idUser+");");
            return "User hired successfully";
        }catch (SQLException e){
            return "SQL ERROR : "+e.getMessage();
        }finally {
            Repository.closeConnection(conn);
        }
    }

    public static String fire(int idStore, int idUser){
        Connection conn = null;
        try{
            conn = Repository.getConnection();
            Statement stmt = conn.createStatement();
            int affectedRows = stmt.executeUpdate("DELETE FROM IStore.WORKING WHERE IdUser="+idUser+" AND IdStore="+idStore+";");
            if(affectedRows==0){
                return "There are no employee with that id in that store";
            }
            return "Employee fired Successfully";
        }catch (SQLException e){
            return "SQL ERROR : "+e.getMessage();
        }finally {
            Repository.closeConnection(conn);
        }
    }

    public static Response<ArrayList<User>> getEmployeesFromStore(int idStore){
        Connection conn = null;
        ArrayList<User> employees = new ArrayList<>();
        try {
            conn = Repository.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM IStore.WORKING WHERE IdStore="+idStore+";");
            if(!rs.next()){
                return new Response<>("There are no employees in this store");
            }
            do {
                Response<User> userResponse = UserRepository.getUserById(rs.getInt("IdUser"));
                if(Objects.equals(userResponse.getMessage(), "Success")) {
                    employees.add(userResponse.getValue());
                }
            }while (rs.next());
            return new Response<>(employees);
        }catch (SQLException e){
            return new Response<>("SQL ERROR : "+e.getMessage());
        }finally {
            Repository.closeConnection(conn);
        }
    }
}
