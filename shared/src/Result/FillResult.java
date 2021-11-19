package Result;

/**
 * Stores Result of a Fill service on the database
 */
public class FillResult {
    private String message;
    private Boolean success;

    /**
     * Generates fill result
     *
     * @param message status message
     * @param success success state
     */
    public FillResult(String message, Boolean success) {
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
