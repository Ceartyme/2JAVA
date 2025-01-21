package repository;

import model.Inventory;
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
            stmt.execute("USE IStore;");
            ResultSet rs = stmt.executeQuery("SELECT * FROM IStore.Items;");
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

    public static Response<Item> createItem(String name, double price){
        Connection conn = null;
        try{
            conn = Repository.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO IStore.Items(Name, Price) VALUE (?,?);");
            pstmt.setString(1,name);
            pstmt.setDouble(2,price);
            pstmt.executeUpdate();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(IdItem) FROM IStore.Items");
            rs.next();
            return new Response<>(new Item(rs.getInt(1),name,price));
        }catch (SQLException e){
            return new Response<>("SQL ERROR : "+e.getMessage());
        }finally {
            Repository.closeConnection(conn);
        }
    }

    public static String deleteItem(int id){
        Connection conn = null;
        try{
            conn = Repository.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM IStore.Items WHERE IdItem=?;");
            pstmt.setInt(1,id);
            int affectedRows = pstmt.executeUpdate();
            if(affectedRows==0){
                return "No item with that Id in the database";
            }
            return "Item deleted Successfully";
        }catch (SQLException e){
            return "SQL ERROR : "+e.getMessage();
        }finally {
            Repository.closeConnection(conn);
        }
    }

    public static Response<Item> getItemById(int id){
        Connection conn = null;
        try {
            conn = Repository.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM IStore.Items WHERE IdItem="+id+";");
            if(!rs.next()){
                return new Response<>("No item with that id in the database");
            }
            return new Response<>(new Item(rs.getInt("IdItem"),rs.getString("Name"),rs.getDouble("Price")));
        }catch (SQLException e){
            return new Response<>("SQL ERROR : "+e.getMessage());
        }finally {
            Repository.closeConnection(conn);
        }
    }

    public static Response<ArrayList<Inventory>> getStores(int id){
        Connection conn = null;
        ArrayList<Inventory> inventories = new ArrayList<>();
        try{
            conn = Repository.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM IStore.Inventories WHERE IdItem="+id+";");
            if(!rs.next()){
                return new Response<>("there are no item with that id in any store");
            }
            do{
                inventories.add(new Inventory(rs.getInt("IdStore"),rs.getInt("Amount"),rs.getInt("IdItem")));
            }while (rs.next());
            return new Response<>(inventories);
        }catch (SQLException e){
            return new Response<>("SQL ERROR : "+e.getMessage());
        }finally {
            Repository.closeConnection(conn);
        }
    }

    public static String updateItem(Item item){
        Connection conn = null;
        try{
            conn = Repository.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("UPDATE IStore.Items SET Name=?,Price=? WHERE IdItem=?;");
            pstmt.setString(1,item.getName());
            pstmt.setDouble(2,item.getPrice());
            pstmt.setInt(3,item.getIdItem());
            int affectedRows = pstmt.executeUpdate();
            if(affectedRows== 0){
                return "No item with that Id in the database";
            }
            return "Item Updated Successfully";
        }catch (SQLException e){
            return e.getMessage();
        }finally {
            Repository.closeConnection(conn);
        }
    }
}
