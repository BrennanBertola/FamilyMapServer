package Service;

import DAO.DataAccessException;
import DAO.Database;
import Model.AuthToken;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

public class RegisterServiceTest {
    private User user;
    private User user2;
    private AuthToken token;
    private Connection conn;
    private Database db;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        conn = db.getConnection();

    }

    @AfterEach
    public void tearDown() throws DataAccessException{
        db.getConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void registerPass() {
        RegisterRequest request = new RegisterRequest("bman", "hehe", "test",
                "Brennan", "Bertola", "m");
        RegisterService service = new RegisterService();
        RegisterResult result = service.register(request);

        assertNotNull(result);
        assertTrue(result.getSuccess());
    }

    @Test
    public void registerFail() {
        RegisterRequest request = new RegisterRequest("bman", "hehe", "test",
                "Brennan", "Bertola", "m");
        RegisterService service = new RegisterService();
        RegisterResult result = service.register(request);

        assertNotNull(result);
        assertTrue(result.getSuccess());

        request = new RegisterRequest("bman", "hehe", "test",
                "Brennan", "Bertola", "m");
        result = service.register(request);

        assertNotNull(result);
        assertFalse(result.getSuccess());
    }
}
