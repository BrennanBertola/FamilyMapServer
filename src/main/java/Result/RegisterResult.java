package Result;

/**
 * Stores Result of a Register service on the database
 */
public class RegisterResult {
    private String message;
    private String authtoken;
    private String username;
    private String personID;
    private Boolean success;

    /**
     * Generates a register result for a success
     *
     * @param success boolean of success state
     * @param authtoken user authtoken
     * @param username username
     * @param personID person id
     */
    public RegisterResult(Boolean success, String authtoken, String username, String personID) {
        this.success = success;
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
    }

    /**
     * Generates a register result for an error
     *
     * @param message error message
     * @param success success state
     */
    public RegisterResult(String message, Boolean success) {
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
