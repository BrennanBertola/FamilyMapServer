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

public class PersonDAOTest {
    private Database db;
    private Person person;
    private Person father;
    private Person grandFather;
    private Person lonelyPerson;
    private PersonDAO pDao;
    private AuthToken token;
    private AuthTokenDAO aDao;
    private UserDAO uDao;
    private User user;

    @BeforeEach
    public void setUp() throws DataAccessException {
        token = new AuthToken("abcde", "12345");
        user = new User("father", "test", "test", "First", "Last", "m", "12345");
        db = new Database();
        Connection conn = db.getConnection();
        aDao = new AuthTokenDAO(conn);
        uDao = new UserDAO(conn);
        uDao.insert(user);
        aDao.insert(token);
        db.closeConnection(true);

        conn = db.getConnection();
        person = new Person ("12345", "father", "First", "Last", "m",
                "54321", null , null);
        father = new Person ("54321", "father", "father", "Father", "m",
                "77777", null, null);


        grandFather = new Person ("77777", "father", "grand", "father", "m");
        lonelyPerson = new Person("000000", "lonely", "sad", "depression", "m");
        pDao = new PersonDAO(conn);


    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.getConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void findPass() throws DataAccessException, Exception {
        pDao.insert(person);
        Person find = pDao.find(token.getAuthToken(), person.getPersonID());
        assertNotNull(find);

    }

    @Test
    public void findFail() throws DataAccessException, Exception {
        assertThrows(Exception.class, ()-> pDao.find(token.getAuthToken(), person.getPersonID()), "Failed to throw Exception");
    }

    @Test
    public void findMultiple() throws DataAccessException, Exception {
        pDao.insert(person);
        pDao.insert(father);
        pDao.insert(grandFather);
        Person[] data = pDao.findAll(token.getAuthToken());
        assertNotNull(data);
    }

    @Test
    public void findMultipleFail() throws DataAccessException, Exception {
        pDao.insert(person);
        pDao.insert(father);
        pDao.insert(grandFather);
        assertThrows(Exception.class, ()->pDao.findAll("token.getAuthToken()"), "Failed to throw exception");
    }

    @Test
    public void insertPass() throws DataAccessException, Exception {
        pDao.insert(person);
        Person compareTest = pDao.find(token.getAuthToken(), person.getPersonID());
        assertNotNull(compareTest);
        assertEquals(person, compareTest);

    }

    @Test
    public void insertFails() throws DataAccessException{
        db.closeConnection(false);
        Assertions.assertThrows(DataAccessException.class, ()-> pDao.insert(person),
                "Failed to throw Exception");
    }

    @Test
    public void clear() throws DataAccessException, Exception {
        pDao.insert(person);
        Person compareTest = pDao.find(token.getAuthToken(), person.getPersonID());
        assertNotNull(compareTest);
        assertEquals(person, compareTest);
        pDao.clear();
        assertThrows(Exception.class, ()-> pDao.find(token.getAuthToken(), person.getPersonID()), "Failed to throw Exception");
    }
}
