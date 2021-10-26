
package DAO;

import DAO.DataAccessException;
import DAO.Database;
import DAO.AuthTokenDAO;
import Model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTokenDAOTest {
    private Database db;
    private AuthTokenDAO aDao;
    private AuthToken token;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        db.clearTables();

        aDao = new AuthTokenDAO(conn);
        token = new AuthToken("121212", "212121");
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.getConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void FindPass() throws DataAccessException {
        aDao.insert(token);
        AuthToken find = aDao.find(token.getAuthToken());
        assertNotNull(find);
    }

    @Test
    public void FindFail() throws DataAccessException {
        AuthToken find = aDao.find(token.getAuthToken());
        assertNull(find);
    }

    @Test
    public void InsertPass() throws DataAccessException{
        aDao.insert(token);
        AuthToken find = aDao.find(token.getAuthToken());
        assertNotNull(find);
        assertEquals(token, find);
    }

    @Test
    public void InsertFail() throws DataAccessException{
        aDao.insert(token);
        AuthToken find = aDao.find(token.getAuthToken());
        assertNotNull(find);
        assertEquals(token, find);

        assertThrows(DataAccessException.class, ()-> aDao.insert(token));
    }

}
