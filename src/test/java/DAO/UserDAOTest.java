package DAO;

import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDAO;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
    private Database db;
    private UserDAO uDao;
    private User user;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        db.clearTables();

        uDao = new UserDAO(conn);
        user = new User("test", "bad-password", "test@gmail.com", "test",
                "test", "f", "12345");

    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.getConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void findPass() throws DataAccessException {
        uDao.insert(user);
        User find = uDao.find(user.getUsername());
        assertNotNull(find);
    }

    @Test
    public void findFail() throws DataAccessException {
        User find = uDao.find(user.getUsername());
        assertNull(find);
    }

    @Test
    public void insertPass() throws DataAccessException {
        uDao.insert(user);
        User find = uDao.find(user.getUsername());
        assertNotNull(find);
        assertEquals(user, find);
    }

    @Test
    public void insertFail() throws DataAccessException {
        uDao.insert(user);
        User find = uDao.find(user.getUsername());
        assertNotNull(find);
        assertEquals(user, find);
        assertThrows(DataAccessException.class, ()-> uDao.insert(user));
    }

    @Test
    public void clear() throws DataAccessException {
        uDao.insert(user);
        User find = uDao.find(user.getUsername());
        assertNotNull(find);
        assertEquals(user, find);
        uDao.clear();
        find = uDao.find(user.getUsername());
        assertNull(find);
    }
}

