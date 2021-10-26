package Result;

/**
 * Stores Result of a Login service on the database
 */
public class LoginResult {
    private String message;
    private String authtoken;
    private String username;
    private String personID;
    private Boolean success;


    /**
     * Generates login result for a success
     *
     * @param success success status
     * @param authtoken user auth token
     * @param username username
     * @param personID password
     */
    public LoginResult(Boolean success, String authtoken, String username, String personID) {
        this.success = success;
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
    }

    /**
     * Generates login result for an error
     *
     * @param message error message
     * @param success success status
     */
    public LoginResult(String message, Boolean success) {
        this.message = message;
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

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
