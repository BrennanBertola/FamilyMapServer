
package Service;

import DAO.*;
import Model.*;
import Result.EventResult;
import Request.EventRequest;
import Handlers.EventHandler;

import Service.EventService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class EventServiceTest {
    private Database db;
    private UserDAO uDao;
    private AuthTokenDAO aDao;
    private EventDAO eDao;
    private PersonDAO pDao;
    private Person person;
    private AuthToken token;
    private User user;
    private Event event;
    private EventRequest request;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        db.clearTables();

        aDao = new AuthTokenDAO(conn);
        eDao = new EventDAO(conn);
        pDao = new PersonDAO(conn);
        uDao = new UserDAO(conn);

        event = new Event("1234", "test", "111", 10, 10 ,"test",
                "test","test", 2021);
        token = new AuthToken("000", "111");
        person = new Person("111","test", "test", "test", "m");
        user = new User("test", "test", "test", "Test", "test", "m", "111");

        aDao.insert(token);
        eDao.insert(event);
        pDao.insert(person);
        uDao.insert(user);

        event = new Event("12345", "test", "111", 10, 10 ,"test",
                "test","test2", 2021);
        eDao.insert(event);

        db.closeConnection(true);

    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.getConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void eventPass() throws DataAccessException {
        request = new EventRequest("000", "1234");
        EventService service = new EventService();
        EventResult result = service.event(request);
        assertNotNull(result);
        assertTrue(result.getSuccess());
        assertEquals("1234", result.getEventID());
    }

    @Test
    public void eventFail() throws DataAccessException {
        request = new EventRequest("111", "1234");
        EventService service = new EventService();
        EventResult result = service.event(request);
        assertNotNull(result);
        assertFalse(result.getSuccess());
    }
}
