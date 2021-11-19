package Model;

import DataStructures.Location;

/**
 * Used to store event related information. Structured after information stored in database event table.
 */
public class Event {
    private String associatedUsername;
    private String eventID;
    private String personID;
    private float latitude;
    private float longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    /**
     * Creates Event object
     * @param eventID Event ID
     * @param username Corresponding Username
     * @param personID Corresponding Person ID
     * @param latitude Latitude of Event
     * @param longitude Longitude of Event
     * @param country Country of Event
     * @param city City of Event
     * @param eventType Type of Event
     * @param year Year of Event
     */
    public Event(String eventID, String username, String personID, float latitude, float longitude,
                 String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public Event(String eventID, String username, String personID, Location location, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = username;
        this.personID = personID;
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.country = location.getCountry();
        this.city = location.getCity();
        this.eventType = eventType;
        this.year = year;
    }


    public String getEventID() {
        return eventID;
    }


    public void setEventID(String eventID) {
        this.eventID = eventID;
    }


    public String getUsername() {
        return associatedUsername;
    }


    public void setUsername(String username) {
        this.associatedUsername = username;
    }


    public String getPersonID() {
        return personID;
    }


    public void setPersonID(String personID) {
        this.personID = personID;
    }


    public float getLatitude() {
        return latitude;
    }


    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }


    public float getLongitude() {
        return longitude;
    }


    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }


    public String getCountry() {
        return country;
    }


    public void setCountry(String country) {
        this.country = country;
    }


    public String getCity() {
        return city;
    }


    public void setCity(String city) {
        this.city = city;
    }


    public String getEventType() {
        return eventType;
    }


    public void setEventType(String eventType) {
        this.eventType = eventType;
    }


    public int getYear() {
        return year;
    }


    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof Event) {
            Event oEvent = (Event) o;
            return oEvent.getEventID().equals(getEventID()) &&
                    oEvent.getUsername().equals(getUsername()) &&
                    oEvent.getPersonID().equals(getPersonID()) &&
                    oEvent.getLatitude() == (getLatitude()) &&
                    oEvent.getLongitude() == (getLongitude()) &&
                    oEvent.getCountry().equals(getCountry()) &&
                    oEvent.getCity().equals(getCity()) &&
                    oEvent.getEventType().equals(getEventType()) &&
                    oEvent.getYear() == (getYear());
        } else {
            return false;
        }
    }
}
