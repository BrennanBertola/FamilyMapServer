package Model;

/**
 * Used to store Person related information. Structured after information stored in database Person table
 */
public class Person {
    private String associatedUsername;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;


    /**
     * Creates Person object without father, mother, and spouse IDs
     * @param personID Person ID
     * @param username Username
     * @param firstName First Name
     * @param lastName Last Name
     * @param gender Gender
     */
    public Person(String personID, String username, String firstName, String lastName, String gender) {
        this.personID = personID;
        this.associatedUsername = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    /**
     * Creates Person object
     * @param personID Person ID
     * @param username Username
     * @param firstName First Name
     * @param lastName Last Name
     * @param gender Gender
     * @param fatherID Father Person ID
     * @param motherID Mother Person ID
     * @param spouseID Spouse Person ID
     */
    public Person(String personID, String username, String firstName, String lastName,
                  String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getUsername() {
        return associatedUsername;
    }

    public void setUsername(String username) {
        this.associatedUsername = username;
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

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof Person) {
            Person oPerson = (Person) o;
            if(!oPerson.getPersonID().equals(getPersonID())) return false;
            if(!oPerson.getUsername().equals(getUsername())) return false;
            if(!oPerson.getFirstName().equals(getFirstName())) return false;
            if(!oPerson.getLastName().equals(getLastName())) return false;
            if(!oPerson.getGender().equals(getGender())) return false;

            if (oPerson.getFatherID() == null || getFatherID() == null) {
                if (!(oPerson.getFatherID() == null && getFatherID() == null)) return false;
            }
            else if (!oPerson.getFatherID().equals(getFatherID()))return false;

            if (oPerson.getMotherID() == null || getMotherID() == null) {
                if (!(oPerson.getMotherID() == null && getMotherID() == null)) return false;
            }
            else if (!oPerson.getMotherID().equals(getMotherID())) return false;

            if (oPerson.getSpouseID() == null || getSpouseID() == null) {
                if (!(oPerson.getSpouseID() == null && getSpouseID() == null)) return false;
            }
            else if (!oPerson.getSpouseID().equals(getSpouseID())) return false;
        }
        else return false;

        return true;
    }
}
