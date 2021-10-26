package Service;

import DAO.DataAccessException;
import Result.ClearResult;
import DAO.Database;

/**
 * Perform the appropriate operation on the database
 * returning the results as a Clear Result
 */
public class ClearService {

    /**
     * Generates default Clear Service Object
     */
    public ClearService() {}

    /**
     * Performs clear service on the database
     *
     * @return Clear Result
     */
    public ClearResult clear() {
        try {
            Database db = new Database();
            db.getConnection();
            db.clearTables();
            db.closeConnection(true);
        }
        catch (DataAccessException e) {
            ClearResult result = new ClearResult("Error: " + e.getMessage(), false);
        }

        ClearResult result = new ClearResult("Clear succeeded.", true);
        return result;
    }
}
