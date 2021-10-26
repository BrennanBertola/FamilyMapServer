package Service;

import DAO.DataAccessException;
import DAO.Database;
import DAO.PersonDAO;
import Model.Person;
import Result.ClearResult;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTest {

    @Test
    public void clearPassEmpty() {
        ClearService service = new ClearService();
        ClearResult result = service.clear();
        assertNotNull(result);
        assertTrue(result.getSuccess());
    }

    @Test
    public void clearPass() throws DataAccessException {
        Person person = new Person("Test", "test", "Test", "test", "m");
        Database db = new Database();
        Connection conn = db.getConnection();
        PersonDAO pDao = new PersonDAO(conn);
        pDao.insert(person);
        db.closeConnection(true);

        ClearService service = new ClearService();
        ClearResult result = service.clear();
        assertNotNull(result);
        assertTrue(result.getSuccess());
    }
}
