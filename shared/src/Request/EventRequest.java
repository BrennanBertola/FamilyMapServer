package Request;

/**
 * Used to generate an event related request for the database
 */
public class EventRequest {
    private String authToken;
    private String eventID;

    /**
     * Generates event request if provided event ID
     *
     * @param authToken user's auth token
     * @param eventID desired event's ID
     */
    public EventRequest(String authToken, String eventID) {
        this.authToken = authToken;
        this.eventID = eventID;
    }

    /**
     * Generates event request when not provided event ID
     *
     * @param authToken user's auth token
     */
    public EventRequest(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }
}
