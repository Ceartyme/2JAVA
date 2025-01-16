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
}
