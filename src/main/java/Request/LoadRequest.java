package Request;

import Model.*;

/**
 * Used to generate a Load Request for the database
 */
public class LoadRequest {
    private User users[];
    private Person persons[];
    private Event events[];

    /**
     * Generates a load request
     *
     * @param users array of User objects
     * @param persons array of Person objects
     * @param events array of Event objects
     */
    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] people) {
        this.persons = people;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
