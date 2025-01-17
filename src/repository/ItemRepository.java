package repository;

import model.Item;
import model.Response;

import java.sql.*;
import java.util.ArrayList;

public class ItemRepository {
    public static Response<ArrayList<Item>> getAllItems(){
        Connection conn = null;
        ArrayList<Item> items = new ArrayList<>();
        try{
            conn = Repository.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeQuery("USE IStore;");
            ResultSet rs = stmt.executeQuery("SELECT * FROM Items");
            if(!rs.next()){
                return new Response<>("There are no Items in the database");
            }
            do {
                items.add(new Item(rs.getInt("IdItem"),rs.getString("Name"),rs.getDouble("Price")));
            }while (rs.next());
            return new Response<>(items);
        }catch (SQLException e){
            return new Response<>("SQL ERROR : "+e.getMessage());
        }finally {
            Repository.closeConnection(conn);
        }
    }
}
