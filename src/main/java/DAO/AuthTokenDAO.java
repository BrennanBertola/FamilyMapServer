package DAO;

import Model.AuthToken;
import Model.Person;

import java.sql.*;

/**
 * Used to access database with authorization tokens Specifically
 */
public class AuthTokenDAO {
    private final Connection conn;
    /**
     * Creates AuthTokenDAO
     */
    public AuthTokenDAO(Connection conn){
        this.conn = conn;
    }

    /**
     * Inserts an AuthToken object
     * @param authToken AuthToken needing insertion
     */
    public void insert(AuthToken authToken) throws DataAccessException{
        String sql = "INSERT INTO AuthToken (AuthToken, PersonID) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken.getAuthToken());
            stmt.setString(2, authToken.getPersonID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Finds and returns AuthToken object
     * @param authToken ID of desired AuthToken
     * @return authToken object
     */
    public AuthToken find (String authToken) throws DataAccessException{
        AuthToken token;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthToken WHERE AuthToken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                token = new AuthToken(rs.getString("AuthToken"), rs.getString("PersonID"));
                return token;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    public AuthToken findWithID (String personID) throws DataAccessException{
        AuthToken token;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthToken WHERE PersonID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                token = new AuthToken(rs.getString("AuthToken"), rs.getString("PersonID"));
                return token;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /**
     * Clears AuthToken table
     */
    public void clear() throws DataAccessException{
        try (Statement stmt = conn.createStatement()){
            String sql =  "DELETE FROM AuthToken";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }
}
