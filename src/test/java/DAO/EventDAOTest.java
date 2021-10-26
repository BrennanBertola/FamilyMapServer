package DAO;

import DAO.AuthTokenDAO;
import DAO.DataAccessException;
import DAO.Database;
import DAO.PersonDAO;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;

public class EventDAOTest {
    private Database db;
    private AuthToken token;
    private AuthTokenDAO aDao;
    private UserDAO uDao;
    private User user;

    private Event event1;
    private Event event2;
    private EventDAO eDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        token = new AuthToken("abcde", "12345");
        user = new User("bman", "test", "test", "First", "Last", "m", "12345");
        db = new Database();
        Connection conn = db.getConnection();
        aDao = new AuthTokenDAO(conn);
        uDao = new UserDAO(conn);
        uDao.insert(user);
        aDao.insert(token);
        db.closeConnection(true);

        conn = db.getConnection();
        eDao = new EventDAO(conn);
        event1 = new Event("1111", "bman", "12345", 1, 2, "Japan", "Tokyo", "trip", 2022);
        event2 = new Event("2222", "bman", "54321", 1, 2, "Japan", "Tokyo", "trip", 2022);



    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.getConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void findPass() throws DataAccessException, Exception {
        eDao.insert(event1);
        Event find = eDao.find(token.getAuthToken(), event1.getEventID());
        assertNotNull(find);

    }

    @Test
    public void findFail() throws DataAccessException, Exception {
        assertThrows(Exception.class, ()-> eDao.find(token.getAuthToken(), event1.getEventID()), "Failed to throw Exception");
    }

    @Test
    public void findMultiple() throws DataAccessException, Exception {
        eDao.insert(event1);
        eDao.insert(event2);
        Event[] data = eDao.findAll(token.getAuthToken());
        assertNotNull(data);
    }

    @Test
    public void findMultipleFail() throws DataAccessException, Exception {
        eDao.insert(event1);
        eDao.insert(event2);
        assertThrows(Exception.class, ()->eDao.findAll("token.getAuthToken()"), "Failed to throw exception");
    }

    @Test
    public void insertPass() throws DataAccessException, Exception {
        eDao.insert(event1);
        Event compareTest = eDao.find(token.getAuthToken(), event1.getEventID());
        assertNotNull(compareTest);
        assertEquals(event1, compareTest);

    }

    @Test
    public void insertFails() throws DataAccessException{
        db.closeConnection(false);
        Assertions.assertThrows(DataAccessException.class, ()-> eDao.insert(event1),
                "Failed to throw Exception");
    }

    @Test
    public void clear() throws DataAccessException {
        eDao.clear();
    }
}
