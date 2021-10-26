package Service;

import java.sql.*;

import Model.Event;
import Request.LoadRequest;
import Result.LoadResult;

import DAO.*;

/**
 * Uses a Load Request to perform the appropriate operation on the database
 * returning the results as a Load Result
 */
public class LoadService {
    /**
     * Generates default Load Service
     */
    public LoadService() {}

    /**
     * Performs a Load Service on database
     * @param r Load Request
     * @return Load Result
     */
    public LoadResult load(LoadRequest r) {
        Database db = new Database();
        Connection conn;
        try {
            conn = db.getConnection();

            UserDAO uDao = new UserDAO(conn);
            for (int i = 0; i < r.getUsers().length; ++i) {
                uDao.insert(r.getUsers()[i]);
            }

            PersonDAO pDao = new PersonDAO(conn);
            for (int i = 0; i < r.getPersons().length; ++i) {
                pDao.insert(r.getPersons()[i]);
            }

            EventDAO eDao = new EventDAO(conn);
            for (int i = 0; i < r.getEvents().length; ++i) {
                eDao.insert(r.getEvents()[i]);
            }

            db.closeConnection(true);
        }
        catch (DataAccessException e) {
            LoadResult result = new LoadResult(e.getMessage(), false);
            return result;
        }

        String msg = "Successfully added " + r.getUsers().length + " users, " +
                r.getPersons().length + " persons, and " +
                r.getEvents().length + " events to the database.";


        LoadResult result = new LoadResult(msg, true);
        return result;
    }
}
