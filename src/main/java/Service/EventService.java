package Service;

import Request.EventRequest;
import Result.EventResult;
import DAO.*;
import Model.*;

import java.sql.Connection;


/**
 * Uses an Event Request to perform the appropriate operation on the database
 * returning the results as an Event Result
 */
public class EventService {
    /**
     * Generates default Event Service object
     */
    public EventService() {}

    /**
     * Performs Event service on database
     * @param r Event Request
     * @return Event Result
     */
    public EventResult event(EventRequest r) {
        EventResult result;

        try {
            Database db = new Database();
            Connection conn = db.getConnection();
            try {
                AuthTokenDAO aDao = new AuthTokenDAO(conn);
                AuthToken token = aDao.find(r.getAuthToken());
                if (token == null) {
                    throw new Exception("Invalid AuthToken");
                }

                EventDAO eDao = new EventDAO(conn);
                if (r.getEventID() == null) {
                    Event events[] = eDao.findAll(r.getAuthToken());
                    result = new EventResult(true, events);
                } else {
                    Event event = eDao.find(r.getAuthToken(), r.getEventID());
                    result = new EventResult(true, event);
                }

                if (result.getEventID() == null && result.getData() == null) {
                    throw new Exception("Event does not exist or you lack access");
                }

                db.closeConnection(false);
            }
            catch (Exception e) {
                db.closeConnection(false);
                result = new EventResult("Error: " + e.getMessage(), false);
                return result;
            }
        }
        catch (DataAccessException e) {
            result = new EventResult("Error: " + e.getMessage(), false);
            return result;
        }


        return result;
    }
}
