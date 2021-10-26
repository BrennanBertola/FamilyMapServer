package Service;

import DAO.*;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Request.PersonRequest;
import Result.LoadResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

public class LoadServiceTest {
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
    private Event event1;
    private Event event2;

    private Person[] people;
    private Event[] events;
    private User[] users;


    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();


        token = new AuthToken("000", "12345");
        son = new Person("12345","bman", "brennan", "bertola", "m", "222", null, null);
        dad = new Person("222", "bman", "jason", "Bertola", "m");
        lonely = new Person("123", "lonely", "sad", "depression", "m");
        user = new User("bman", "test", "test", "Brennan", "Bertola", "m", "111");
        event1 = new Event("1111", "bman", "12345", 1, 2, "Japan", "Tokyo", "trip", 2022);
        event2 = new Event("2222", "bman", "222", 1, 2, "Japan", "Tokyo", "trip", 2022);

        people = new Person[]{son, dad, lonely};
        events = new Event[]{event1,event2};
        users = new User[] {user};

    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.getConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void loadPass() throws DataAccessException {
        LoadRequest request = new LoadRequest(users, people, events);
        LoadService service = new LoadService();
        LoadResult result = service.load(request);

        assertNotNull(result);
        assertTrue(result.getSuccess());
    }

    @Test
    public void loadPassWNoEvents() throws DataAccessException {
        users = new User[] {};
        LoadRequest request = new LoadRequest(users, people, events);
        LoadService service = new LoadService();
        LoadResult result = service.load(request);

        assertNotNull(result);
        assertTrue(result.getSuccess());
    }
}
