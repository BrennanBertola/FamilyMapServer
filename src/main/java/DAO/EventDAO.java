package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

import Model.Event;
import Model.Person;
import Model.AuthToken;
import Model.User;

/**
 * Used to access database with Event related needs
 */
public class EventDAO {
    private final Connection conn;
    /**
     * Creates EventDAO object
     */
    public EventDAO(Connection conn){
        this.conn = conn;
    }

    /**
     * Inserts an Event object
     * @param event Event object needing insertion
     */
    public void insert(Event event) throws DataAccessException {
        String sql = "INSERT INTO Event (EventID, Username, PersonID, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Finds and returns Event object
     * @param eventID eventID of desired Event
     * @return eventObject
     */
    public Event find (String authToken, String eventID) throws DataAccessException, Exception{
        AuthTokenDAO aDao = new AuthTokenDAO(conn);
        UserDAO uDao = new UserDAO(conn);
        AuthToken token = aDao.find(authToken);
        User user = uDao.findWithID(token.getPersonID());

        if(token == null) throw new Exception("Invalid authorization token");

        Event event = getEvent(eventID);
        if(event == null) throw new Exception("Event does not exist");
        if(!event.getUsername().equals(user.getUsername())) throw new Exception("Do not have access");

        return event;
    }

    public Event[] findAll(String authToken) throws DataAccessException, Exception{
        AuthTokenDAO aDao = new AuthTokenDAO(conn);
        UserDAO uDao = new UserDAO(conn);
        AuthToken token = aDao.find(authToken);
        User user = uDao.findWithID(token.getPersonID());

        ArrayList<Event> events = new ArrayList<>();
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Event WHERE Username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            rs = stmt.executeQuery();
            while (rs.next()) {
                event = new Event(rs.getString("EventID"), rs.getString("Username"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                events.add(event);
            }
            return events.toArray(new Event[0]);
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
     * Clears Event table
     */
    public void clear() throws DataAccessException{
        Database db = new Database();
        db.getConnection();
        db.clearEvent();
        db.closeConnection(true);
    }

    private Event getEvent(String eventID) throws DataAccessException{
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Event WHERE EventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("EventID"), rs.getString("Username"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
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
