package Service;

import DAO.AuthTokenDAO;
import DAO.DataAccessException;
import DAO.Database;
import DAO.PersonDAO;
import Model.AuthToken;
import Model.Person;
import Request.PersonRequest;
import Result.PersonResult;

import java.sql.Connection;

/**
 * Uses a Person Request to perform the appropriate operation on the database
 * returning the results as a Person Result
 */
public class PersonService {
    /**
     * Generates default Person Service object
     */
    public PersonService() {}

    /**
     * Performs Person Service on database
     * @param r Person Request
     * @return Person Result
     */
    public PersonResult person(PersonRequest r) {
        PersonResult result;

        try {
            Database db = new Database();
            Connection conn = db.getConnection();
            try {
                AuthTokenDAO aDao = new AuthTokenDAO(conn);
                AuthToken token = aDao.find(r.getAuthToken());
                if (token == null) {
                    throw new Exception("Invalid AuthToken");
                }

                PersonDAO pDao = new PersonDAO(conn);
                if (r.getPersonID() == null) {
                    Person people[] = pDao.findAll(r.getAuthToken());
                    result = new PersonResult(true, people);
                } else {
                    Person person = pDao.find(r.getAuthToken(), r.getPersonID());
                    result = new PersonResult(true, person);
                }

                if (result.getPersonID() == null && result.getData() == null) {
                    throw new Exception("Person does not exist or you lack access");
                }

                db.closeConnection(false);
            }
            catch (Exception e) {
                db.closeConnection(false);
                result = new PersonResult("Error: " + e.getMessage(), false);
                return result;
            }
        }
        catch (DataAccessException e) {
            result = new PersonResult("Error: " + e.getMessage(), false);
            return result;
        }


        return result;
    }
}
