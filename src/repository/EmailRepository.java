package repository;

import model.Response;

import java.sql.*;

public class EmailRepository {
    public static Response<Boolean> isEmailWhitelisted(String email){
        Connection conn = null;
        try {
            conn = Repository.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM WhitelistedEmail WHERE Email=?");
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return new Response<>(rs.getInt(1)==1);
        } catch (SQLException e) {
            return new Response<>("SQL Error : "+e.getMessage());
        }finally {
            Repository.closeConnection(conn);
        }
    }
}
