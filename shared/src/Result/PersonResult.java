package Result;

import Model.Person;

/**
 * Stores Result of a Person service on the database
 */
public class PersonResult {
    private String message;
    private Person data[];
    private String associatedUsername;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;

    private Boolean success;

    /**
     * Generates person result if there was an error
     *
     * @param message error message
     * @param success success status
     */
    public PersonResult(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    /**
     * Generates person result if there was a provided person ID
     *
     * @param success success status
     * @param person person object with desired person ID
     */
    public PersonResult(Boolean success, Person person) {
        this.success = success;
        this.associatedUsername = person.getUsername();
        this.personID = person.getPersonID();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.gender = person.getGender();
        this.fatherID = person.getFatherID();
        this.motherID = person.getMotherID();
        this.spouseID = person.getSpouseID();
    }

    /**
     * Generates person result if thew was no provided person ID
     *
     * @param success success status
     * @param people array of person objects
     */
    public PersonResult(Boolean success, Person[] people) {
        this.success = success;
        this.data = people;
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

    public Person[] getData() {
        return data;
    }

    public void setData(Person[] data) {
        this.data = data;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
