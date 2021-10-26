package Service;

import DAO.AuthTokenDAO;
import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDAO;
import Model.AuthToken;
import Model.User;
import Request.LoginRequest;
import Service.LoginService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Result.LoginResult;

import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {
    private User user;
    private AuthToken token;
    private Connection conn;
    private Database db;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        conn = db.getConnection();

        user = new User("bman", "hehe", "test@gamil.com", "Brennan",
                "Bertola", "m", "1234");
        token = new AuthToken("111", "1234");
    }

    @AfterEach
    public void tearDown() throws DataAccessException{
        db.getConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void userExistWToken() throws DataAccessException{
        UserDAO uDao = new UserDAO(conn);
        uDao.insert(user);
        AuthTokenDAO aDao = new AuthTokenDAO(conn);
        aDao.insert(token);
        db.closeConnection(true);

        LoginRequest request = new LoginRequest(user.getUsername(), user.getPassword());
        LoginService service = new LoginService();
        LoginResult result = service.login(request);

        assertNotNull(result);
        assertNotNull(result.getAuthtoken());
        assertNotNull(result.getUsername());
        assertNotNull(result.getPersonID());
        assertNull(result.getMessage());

        assertTrue(result.getSuccess());
        assertEquals("bman", result.getUsername());
        assertEquals("1234", result.getPersonID());
        assertEquals("111", result.getAuthtoken());
    }

    @Test
    public void userExistWOToken() throws DataAccessException{
        UserDAO uDao = new UserDAO(conn);
        uDao.insert(user);
        db.closeConnection(true);

        LoginRequest request = new LoginRequest(user.getUsername(), user.getPassword());
        LoginService service = new LoginService();
        LoginResult result = service.login(request);

        assertNotNull(result);
        assertNotNull(result.getAuthtoken());
        assertNotNull(result.getUsername());
        assertNotNull(result.getPersonID());
        assertNull(result.getMessage());

        assertTrue(result.getSuccess());
        assertEquals("bman", result.getUsername());
        assertEquals("1234", result.getPersonID());
    }

    @Test
    public void fail() throws DataAccessException{
        LoginRequest request = new LoginRequest(user.getUsername(), user.getPassword());
        LoginService service = new LoginService();
        LoginResult result = service.login(request);

        assertNotNull(result);
        assertNull(result.getAuthtoken());
        assertNull(result.getUsername());
        assertNull(result.getPersonID());
        assertNotNull(result.getMessage());
        assertFalse(result.getSuccess());
    }

}
