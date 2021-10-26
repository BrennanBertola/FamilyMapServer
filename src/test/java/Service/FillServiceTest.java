package Service;

import DAO.*;
import Model.*;
import Request.FillRequest;
import Service.FillService;
import Result.FillResult;

import Service.EventService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class FillServiceTest {
    private Database db;
    private AuthTokenDAO aDao;
    private EventDAO eDao;
    private PersonDAO pDao;
    private UserDAO uDao;
    private User user;
    private Person son;
    private Person dad;
    private Person lonely;
    private AuthToken token;
    private FillRequest request;


    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        db.clearTables();

        aDao = new AuthTokenDAO(conn);
        eDao = new EventDAO(conn);
        pDao = new PersonDAO(conn);
        uDao = new UserDAO(conn);

        token = new AuthToken("000", "111");
        son = new Person("111", "bman", "brennan", "bertola", "m", "222", null, null);
        dad = new Person("222", "bman", "jason", "Bertola", "m");
        user = new User("bman", "abc", "test@gmail.com", "Brennan", "Bertola", "m", "111");

        aDao.insert(token);
        pDao.insert(son);
        pDao.insert(dad);
        uDao.insert(user);

        db.closeConnection(true);

    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.getConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void fillPass() throws DataAccessException {
        request = new FillRequest("bman", 4);
        FillService service = new FillService();
        FillResult result = service.fill(request);
        assertNotNull(result);
        assertEquals(true, result.getSuccess());
        assertEquals(31, service.getPeopleAdded());
    }

    @Test
    public void fillFail() throws DataAccessException {
        request = new FillRequest("bman", -1);
        FillService service = new FillService();
        FillResult result = service.fill(request);
        assertNotNull(result);
        assertEquals(false, result.getSuccess());
    }
}
