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
            ResultSet rs = stmt.executeQuery("SELECT * FROM IStore.Stores");
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

    public static Response<ArrayList<Store>> getStoresByEmployeeId(int employeeId) {
        Connection conn = null;
        ArrayList<Store> stores = new ArrayList<>();
        try {
            conn = Repository.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT s.IdStore, s.Name FROM IStore.Stores s JOIN IStore.WORKING es ON s.IdStore = es.IdStore WHERE es.IdUser = ?"
            );
            pstmt.setInt(1, employeeId);
            ResultSet rs = pstmt.executeQuery();


            if (!rs.next()) {
                return new Response<>("No stores found for the given employee ID.");
            }

            do {
                int id = rs.getInt("IdStore");
                String name = rs.getString("Name");
                stores.add(new Store(id, name));
            } while (rs.next());

            return new Response<>(stores);
        } catch (SQLException e) {
            return new Response<>("SQL ERROR: " + e.getMessage());
        } finally {
            Repository.closeConnection(conn);
        }
    }


    public static Response<Store> createStore(String name){
        Connection conn = null;
        try{
            conn = Repository.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO IStore.Stores(Name) VALUE (?)");
            pstmt.setString(1,name);
            pstmt.executeUpdate();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(IdStore) FROM IStore.Stores");
            rs.next();
            return new Response<>(new Store(rs.getInt(1),name));
        }catch (SQLException e){
            return new Response<>("SQL ERROR : "+e.getMessage());
        }finally {
            Repository.closeConnection(conn);
        }
    }

    public static Response<ArrayList<Integer>> getIdStoresByEmployeeId(int employeeId) {
        Response<ArrayList<Store>> storeResponse = getStoresByEmployeeId(employeeId);

        if (!storeResponse.getMessage().equals("Success")) {
            return new Response<>(storeResponse.getMessage());
        }

        ArrayList<Store> stores = storeResponse.getValue();
        ArrayList<Integer> storeIds = new ArrayList<>();

        for (Store store : stores) {
            storeIds.add(store.getIdStore());
        }

        if (storeIds.isEmpty()) {
            return new Response<>("No store IDs found for the given employee.");
        }

        return new Response<>(storeIds);
    }


    public static String deleteStore(int id){
        Connection conn = null;
        try{
            conn = Repository.getConnection();
            Statement stmt = conn.createStatement();
            int affectedRows = stmt.executeUpdate("DELETE FROM IStore.Stores WHERE IdStore="+id+";");
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

    public static String updateStore(Store store){
        Connection conn = null;
        try{
            conn = Repository.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("UPDATE IStore.Stores SET Name=? WHERE IdStore=?;");
            pstmt.setString(1,store.getName());
            pstmt.setInt(2,store.getIdStore());
            int affectedRows = pstmt.executeUpdate();
            if(affectedRows== 0){
                return "No store with that Id in the database";
            }
            return "Store Updated Successfully";
        }catch (SQLException e){
            return e.getMessage();
        }finally {
            Repository.closeConnection(conn);
        }
    }

    public static Response<ArrayList<Integer>> getIdStores() {
        Connection conn = null;

        ArrayList<Integer> idList = new ArrayList<>();

        try{
            conn = Repository.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT IdStore FROM IStore.Stores");

            while (rs.next()) {
                idList.add(rs.getInt("IdStore"));
            }

            if (idList.isEmpty()) {
                return new Response<>("No stores found.");
            }

            return new Response<>(idList);
        } catch (SQLException e) {
            return new Response<>("SQL ERROR: " + e.getMessage());
        } finally {
            Repository.closeConnection(conn);
        }

    }
}
