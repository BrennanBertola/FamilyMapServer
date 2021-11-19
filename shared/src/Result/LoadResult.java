package Result;

/**
 * Stores Result of a Load service on the database
 */
public class LoadResult {
    private String message;
    private Boolean success;


    /**
     * Generates load result
     *
     * @param message status message
     * @param success success state
     */
    public LoadResult(String message, Boolean success) {
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
