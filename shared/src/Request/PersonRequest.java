package Request;

/**
 * Used to generate a Person Request for the database
 */
public class PersonRequest {
    private String authToken;
    private String personID;

    /**
     * Generates a person request with given person ID
     *
     * @param authToken user auth token
     * @param personID desired person ID
     */
    public PersonRequest(String authToken, String personID) {
        this.authToken = authToken;
        this.personID = personID;
    }

    /**
     * Generates a person request when not give person ID
     *
     * @param authToken user auth token
     */
    public PersonRequest(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPersonID() {return personID;}

    public void setPersonID(String personID) {this.personID = personID;}
}
