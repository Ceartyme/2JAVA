package repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
}
