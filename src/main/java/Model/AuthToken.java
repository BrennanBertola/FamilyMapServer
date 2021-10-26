package Model;

/**
 * Used to store AuthToken related information. Structured after information stored in database AuthToken table
 */
public class AuthToken {
    private String authToken;
    private String personID;


    /**
     * Creates AuthToken object
     * @param authToken Generated authentication String
     * @param personID Corresponding Person ID
     */
    public AuthToken(String authToken, String personID) {
        this.authToken = authToken;
        this.personID = personID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof AuthToken) {
            AuthToken o = (AuthToken) obj;
            return o.getAuthToken().equals(getAuthToken()) &&
                    o.getPersonID().equals(getPersonID());
        }
        else return false;
    }
}
