package Result;

/**
 * Stores Result of a Clear service on the database
 */
public class ClearResult {
    private String message;
    private Boolean success;


    /**
     * Generates a clear result
     *
     * @param message status message
     * @param success success state
     */
    public ClearResult(String message, Boolean success) {
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
}
