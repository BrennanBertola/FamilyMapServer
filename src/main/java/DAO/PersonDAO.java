package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;

/**
 * Used to access database with Person related needs
 */
public class PersonDAO {
    private final Connection conn;
    ArrayList<Person> data;
    /**
     * Creates PersonDAO object
     */
    public PersonDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a Person object
     * @param person Person object needing insertion
     */
    public void insert(Person person) throws DataAccessException{
        String sql = "INSERT INTO Person (PersonID, Username, FirstName, LastName, Gender, " +
                "FatherID, MotherID, SpouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Finds and returns Person object
     * @param personID personID of desired Person
     * @return personObject
     */
    public Person find (String authToken, String personID) throws DataAccessException, Exception {
        AuthTokenDAO aDao = new AuthTokenDAO(conn);
        AuthToken token = aDao.find(authToken);
        Person desired = getPerson(personID);

        if(token == null) throw new Exception("Invalid authorization token");
        if(desired == null) throw new Exception("Person does not exist");

        UserDAO uDao = new UserDAO(conn);
        User user = uDao.findWithID(token.getPersonID());

        if(!desired.getUsername().equals(user.getUsername())) throw new Exception("Do not have access");

        return desired;
    }

    public Person[] findAll(String authToken) throws DataAccessException, Exception {
        AuthTokenDAO aDao = new AuthTokenDAO(conn);
        UserDAO uDao = new UserDAO(conn);
        AuthToken token = aDao.find(authToken);
        User user = uDao.findWithID(token.getPersonID());

        if(token == null) throw new Exception("Invalid authorization token");

        ArrayList<Person> people = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM Person WHERE Username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            rs = stmt.executeQuery();
            while (rs.next()) {
                Person person;
                person = new Person(rs.getString("PersonID"), rs.getString("Username"),
                        rs.getString("FirstName"), rs.getString("LastName"),
                        rs.getString("Gender"), rs.getString("FatherID"),
                        rs.getString("MotherID"), rs.getString("SpouseID"));
                people.add(person);
            }
            return people.toArray(new Person[0]);
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
    }


    /**
     * Clears Person table
     */
    public void clear() throws DataAccessException {
        try (Statement stmt = conn.createStatement()){
            String sql =  "DELETE FROM Person";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }

    private Person getPerson(String personID) throws DataAccessException{
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Person WHERE PersonID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("PersonID"), rs.getString("Username"),
                        rs.getString("FirstName"), rs.getString("LastName"),
                        rs.getString("Gender"), rs.getString("FatherID"),
                        rs.getString("MotherID"), rs.getString("SpouseID"));
                return person;
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
}
