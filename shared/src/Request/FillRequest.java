package Request;

/**
 * Used to generate a Fill request for the database
 */
public class FillRequest {
    private String username;
    private int generations;


    /**
     * Generates fill request
     *
     * @param username username
     * @param generations desired amount of generations
     */
    public FillRequest(String username, int generations) {
        this.username = username;
        this.generations = generations;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }
}
