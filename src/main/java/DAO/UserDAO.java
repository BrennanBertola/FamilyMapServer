package DAO;

import Model.AuthToken;
import Model.Person;
import Model.User;

import java.sql.*;

/**
 * Used to access database with User related needs
 */
public class UserDAO {
    private final Connection conn;
    /**
     * Creates UserDAO object
     */
    public UserDAO(Connection conn){
        this.conn = conn;
    }

    /**
     * Inserts User object
     * @param user User object needing insertion
     */
    public void insert(User user) throws DataAccessException{
        String sql = "INSERT INTO User (Username, Password, Email, FirstName, LastName, Gender, " +
                "PersonID) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Finds and returns User object
     * @param username username of desired User
     * @return userObject
     */
    public User find (String username) throws DataAccessException{
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM User WHERE Username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User (rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("Email"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Gender"),
                        rs.getString("PersonID"));
                return user;
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

    public User findWithID (String personID) throws DataAccessException{
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM User WHERE PersonID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User (rs.getString("Username"),
                        rs.getString("Password"),
                        rs.getString("Email"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Gender"),
                        rs.getString("PersonID"));
                return user;
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
     * Clears User table
     */
    public void clear() throws DataAccessException {
        try (Statement stmt = conn.createStatement()){
            String sql =  "DELETE FROM User";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }

    public void clearRelatedData(User user) throws DataAccessException {
        String username = user.getUsername();

        String sql = "Delete FROM Person WHERE Username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }

        sql = "Delete FROM Event WHERE Username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }

        sql = "Delete FROM AuthToken WHERE PersonID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }

    }

}
