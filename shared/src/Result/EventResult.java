package Result;

import Model.Event;

/**
 * Stores Result of an Event service on the database
 */
public class EventResult {
    private String message;
    private Event data[];
    private String associatedUsername;
    private String eventID;
    private String personID;
    private float latitude;
    private float longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;
    private Boolean success;


    /**
     * Generates event result if there was an error
     *
     * @param message error message
     * @param success success status
     */
    public EventResult(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    /**
     * Generates event result if there was a provided event ID
     *
     * @param success success status
     * @param event event object with desired event ID
     */
    public EventResult(Boolean success, Event event) {
        this.success = success;

        this.associatedUsername = event.getUsername();
        this.eventID = event.getEventID();
        this.personID = event.getPersonID();
        this.latitude = event.getLatitude();
        this.longitude = event.getLongitude();
        this.country = event.getCountry();
        this.city = event.getCity();
        this.eventType = event.getEventType();
        this.year = event.getYear();

    }

    /**
     * Generates event result if there was no provided event ID
     *
     * @param success success status
     * @param data array of event objects
     */
    public EventResult(Boolean success, Event[] data) {
        this.data = data;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
        this.data = data;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }
}
