package Model;

/**
 * Used to store User related information. Structured after information stored in database User table
 */
public class User {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;


    /**
     * @param username Username
     * @param password Password
     * @param email Email
     * @param firstName First Name
     * @param lastName Last Name
     * @param gender Gender
     * @param personID Person ID
     */
    public User(String username, String password, String email, String firstName, String lastName,
                String gender, String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if (obj instanceof User) {
            User o = (User) obj;
            return o.getUsername().equals(getUsername()) &&
                    o.getPassword().equals(getPassword()) &&
                    o.getEmail().equals(getEmail()) &&
                    o.getFirstName().equals(getFirstName()) &&
                    o.getLastName().equals(getLastName()) &&
                    o.getGender().equals(getGender()) &&
                    o.getPersonID().equals(getPersonID());
        }
        else {
            return false;
        }
    }
}
