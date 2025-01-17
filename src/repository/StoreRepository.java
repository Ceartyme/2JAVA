package repository;

import model.Response;
import model.Store;

import java.sql.*;
import java.util.ArrayList;

public class StoreRepository {
    public static Response<ArrayList<Store>> getAllStores(){
        Connection conn = null;
        ArrayList<Store> stores = new ArrayList<>();
        try{
            conn = Repository.getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute("USE IStore;");
            ResultSet rs = stmt.executeQuery("SELECT * FROM Stores");
            if(!rs.next()){
                return new Response<>("There are no Stores in the database");
            }
            do {
                stores.add(new Store(rs.getInt("IdStore"),rs.getString("Name")));
            }while(rs.next());
            return new Response<>(stores);
        }catch (SQLException e){
            return new Response<>("SQL ERROR : "+e.getMessage());
        } finally {
            Repository.closeConnection(conn);
        }
    }

    public static String createStore(String name){
        Connection conn = null;
        try{
            conn = Repository.getConnection();
            conn.createStatement().execute("USE IStore;");;
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Stores(Name) VALUE (?)");
            pstmt.setString(1,name);
            pstmt.executeUpdate();
            return "Store created Successfully";
        }catch (SQLException e){
            return "SQL ERROR : "+e.getMessage();
        }finally {
            Repository.closeConnection(conn);
        }
    }

    public static String deleteStore(int id){
        Connection conn = null;
        try{
            conn = Repository.getConnection();
            Statement stmt = conn.createStatement();
            int affectedRows = stmt.executeUpdate("DELETE FROM Stores WHERE IdStore="+id+";");
            if(affectedRows==0){
                return "No store with that Id in the database";
            }
            return "Store deleted Successfully";
        }catch (SQLException e){
            return "SQL ERROR : "+e.getMessage();
        }finally {
            Repository.closeConnection(conn);
        }
    }
}
