package Service;

import DAO.*;
import Model.*;
import Request.PersonRequest;
import Service.PersonService;
import Result.PersonResult;

import Service.EventService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest {
    private Database db;
    private AuthTokenDAO aDao;
    private UserDAO uDao;
    private PersonDAO pDao;
    private Person son;
    private Person dad;
    private Person lonely;
    private AuthToken token;
    private User user;
    private PersonRequest request;


    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        db.clearTables();

        aDao = new AuthTokenDAO(conn);
        pDao = new PersonDAO(conn);
        uDao = new UserDAO(conn);

        token = new AuthToken("000", "111");
        son = new Person("111","bman", "brennan", "bertola", "m", "222", null, null);
        dad = new Person("222", "bman", "jason", "Bertola", "m");
        lonely = new Person("123", "lonely", "sad", "depression", "m");
        user = new User("bman", "test", "test", "Brennan", "Bertola", "m", "111");

        aDao.insert(token);
        pDao.insert(son);
        pDao.insert(dad);
        pDao.insert(lonely);
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
    public void personPass() throws DataAccessException {
        request = new PersonRequest("000", "222");
        PersonService service = new PersonService();
        PersonResult result = service.person(request);
        assertNotNull(result);
        assertEquals(true, result.getSuccess());
        assertEquals("222", result.getPersonID());
    }

    @Test
    public void personFail() throws DataAccessException {
        request = new PersonRequest("111", "222");
        PersonService service = new PersonService();
        PersonResult result = service.person(request);
        assertNotNull(result);
        assertEquals(false, result.getSuccess());
    }
}
