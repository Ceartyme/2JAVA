package repository;

import model.Inventory;
import model.Response;

import java.sql.*;
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

    public static String createItemsInStore(int idStore, int idItem, int amount){
        Connection conn = null;
        try {
            conn = Repository.getConnection();

            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM IStore.Inventories WHERE IdStore = ? AND IdItem = ?");
            checkStmt.setInt(1, idStore);
            checkStmt.setInt(2, idItem);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                return "Error: Item already exists in the store.";
            }



            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO IStore.Inventories (IdStore, IdItem, Amount) VALUES (?, ?, ?) ");
            pstmt.setInt(1, idStore);
            pstmt.setInt(2, idItem);
            pstmt.setInt(3, amount);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                return "Item successfully added or updated in the store";
            } else {
                return "No rows affected. Operation failed.";
            }
        }catch (SQLException e) {
            return "SQL ERROR: " + e.getMessage();
        } finally {
            Repository.closeConnection(conn);
        }

    }

    public static String deleteItemFromStore(int idStore, int idItem) {
        Connection conn = null;
        try {
            conn = Repository.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM IStore.Inventories WHERE IdStore = ? AND IdItem = ?;"
            );
            pstmt.setInt(1, idStore);
            pstmt.setInt(2, idItem);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return "Item deleted successfully";
            } else {
                return "No matching item found in the store";
            }
        } catch (SQLException e) {
            return "SQL ERROR: " + e.getMessage();
        } finally {
            Repository.closeConnection(conn);
        }
    }

    public static String increaseItemInStore(int idStore, int idItem, int amount) {
        Connection conn = null;

        try{
            conn = Repository.getConnection();

            PreparedStatement pstmt = conn.prepareStatement("UPDATE IStore.Inventories SET Amount = Amount + ? WHERE IdStore = ? AND IdItem = ?;");
            pstmt.setInt(1, amount);
            pstmt.setInt(2, idStore);
            pstmt.setInt(3, idItem);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                return "Item quantity successfully increased.";
            } else {
                return "No item found or updated in the store.";
            }
        } catch (SQLException e) {
            return "SQL ERROR: " + e.getMessage();
        } finally {
            Repository.closeConnection(conn);
        }

    }

    public static String decreaseItemInStore(int idStore, int idItem, int amount) {
        Connection conn = null;

        try{
            conn = Repository.getConnection();

            PreparedStatement pstmt = conn.prepareStatement("UPDATE IStore.Inventories SET Amount = Amount - ? WHERE IdStore = ? AND IdItem = ? AND Amount >= ?;");
            pstmt.setInt(1, amount);
            pstmt.setInt(2, idStore);
            pstmt.setInt(3, idItem);
            pstmt.setInt(4, amount);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                return "Item quantity successfully decreased.";
            } else {
                return "Insufficient stock or item not found.";
            }
        } catch (SQLException e) {
            return "SQL ERROR: " + e.getMessage();
        } finally {
            Repository.closeConnection(conn);
        }
    }

}


