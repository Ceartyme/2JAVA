package repository;

import model.Inventory;
import model.Response;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class InventoryRepository {
    public static Response<ArrayList<Inventory>> getItemsByStore(int idStore){
        Connection conn = null;
        ArrayList<Inventory> inventories = new ArrayList<>();
        try{
            conn = Repository.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM IStore.Inventories WHERE IdStore="+idStore+";");
            if(!rs.next()){
                return new Response<>("This store does not exist or does not have any item");
            }
            do {
                inventories.add(new Inventory(idStore,rs.getInt("Amount"),rs.getInt("IdItem")));
            }while (rs.next());
            return new Response<>(inventories);
        }catch (SQLException e){
            return new Response<>("SQL ERROR : "+e.getMessage());
        }finally {
            Repository.closeConnection(conn);
        }
    }
}
