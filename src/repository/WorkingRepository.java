package repository;

import model.Response;
import model.Store;
import model.User;

import java.sql.*;
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

    public static String updateUserRoleEmployee(int idUser, int newIdRole){
        Connection conn = null;
        try {
            conn = Repository.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("UPDATE IStore.Users SET IdRole = ? WHERE IdUser = ?;");
            pstmt.setInt(1, newIdRole);
            pstmt.setInt(2, idUser);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                return "User role updated successfully";
            } else {
                return "User not found or no changes made";
            }
        } catch (SQLException e) {
            return "SQL ERROR: " + e.getMessage();
        } finally {
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

    public static String removeStoresFromUser(int userId) {
        Connection conn = null;
        try {
            conn = Repository.getConnection();


            String query = "DELETE FROM IStore.WORKING WHERE  IdUser = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                return "All stores have been successfully removed from the user.";
            } else {
                return "No stores were found for this user.";
            }
        } catch (SQLException e) {
            return "SQL ERROR: " + e.getMessage();
        } finally {
            Repository.closeConnection(conn);
        }
    }

    public static Response<ArrayList<Store>> getStoresFromEmployee(int idUser){
        Connection conn = null;
        ArrayList<Store> stores = new ArrayList<>();
        try {
            conn = Repository.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM IStore.WORKING WHERE IdUser="+idUser+";");
            if(!rs.next()){
                return new Response<>("There are no employees in this store");
            }
            do {
                Response<Store> storeResponse = StoreRepository.getStoreById(rs.getInt("IdStore"));
                if(Objects.equals(storeResponse.getMessage(), "Success")) {
                    stores.add(storeResponse.getValue());
                }
            }while (rs.next());
            return new Response<>(stores);
        }catch (SQLException e){
            return new Response<>("SQL ERROR : "+e.getMessage());
        }finally {
            Repository.closeConnection(conn);
        }
    }

    public static Response<Boolean> isWorking(int employeeId, int storeId){
        Connection conn = null;
        try {
            conn = Repository.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM IStore.WORKING WHERE IdUser="+employeeId+" AND IdStore="+storeId+";");
            return new Response<>(rs.next());
        }catch (SQLException e){
            return new Response<>("SQL ERROR : "+e.getMessage());
        }finally {
            Repository.closeConnection(conn);
        }
    }
}
