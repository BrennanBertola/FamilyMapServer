package Service;

import DAO.*;
import DataStructures.Location;
import Model.Event;
import Model.Person;
import Model.User;
import Request.FillRequest;
import Result.FillResult;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * Uses a Fill Request to perform the appropriate operation on the database
 * returning the results as a Fill Result
 */
public class FillService {
    int neededGen;
    int marriageYear;
    int eventsAdded;
    int peopleAdded;
    User baseUser;
    Connection conn;
    /**
     * Generates default Fill Service
     */
    public FillService() {
        neededGen = 0;
        marriageYear = 0;
        eventsAdded = 0;
        peopleAdded = 0;
    }

    /**
     * Performs Fill Service on database
     * @param r Fill Request
     * @return Fill Result
     */
    public FillResult fill(FillRequest r) {
        try {
            Database db = new Database();
            conn = db.getConnection();
            try {
                if (r.getGenerations() < 0) throw new Exception("Invalid number of generation");

                int currGen = 0;
                neededGen = r.getGenerations();
                marriageYear = 0;
                eventsAdded = 0;
                peopleAdded = 0;

                UserDAO uDao = new UserDAO(conn);
                baseUser = uDao.find(r.getUsername());
                if (baseUser == null) throw new Exception("User does not exist");
                uDao.clearRelatedData(baseUser);

                //generates person for currUser
                Person person = new Person(baseUser.getPersonID(), baseUser.getUsername(), baseUser.getFirstName(),
                        baseUser.getLastName(), baseUser.getGender());


                //generates birth for currUser
                int age = (int) (10 * Math.random()) + 10;
                int year = 2021 - age;
                String eventID = generateID();
                Location location = generateLocation();

                Event birth = new Event(eventID, baseUser.getUsername(), baseUser.getPersonID(),
                        location, "Birth " + baseUser.getFirstName(), year);
                EventDAO eDao = new EventDAO(conn);
                eDao.insert(birth);
                ++eventsAdded;

                generateNextGen(year, person, currGen);

                db.closeConnection(true);
            }
            catch (Exception e) {
                db.closeConnection(false);
                FillResult result = new FillResult("Error: " + e.getMessage(), false);
                return result;
            }
        }
        catch (DataAccessException e) {
            FillResult result = new FillResult("Error: " + e.getMessage(), false);
            return result;
        }


        String msg = "Successfully added " + peopleAdded + " persons and " + eventsAdded +
                     " events to the database";
        FillResult result = new FillResult(msg, true);
        return result;
    }

    private void generateNextGen(int childBirthYear, Person child, int currGen) throws Exception {
        currGen += 1;
        PersonDAO pDao = new PersonDAO(conn);

        if (currGen > neededGen) {
            pDao.insert(child);
            ++peopleAdded;
            return;
        }

        String fatherID = generateID();
        String motherID = generateID();

        child.setFatherID(fatherID);
        child.setMotherID(motherID);
        pDao.insert(child);
        ++peopleAdded;

        Person father = new Person (fatherID, child.getUsername(), getName('m'), child.getLastName(),
                "m", null, null, motherID);
        Person mother = new Person (motherID, child.getUsername(), getName('f'), getName('s'),
                "f", null, null, fatherID);


        //get birth events for parents
        Event motherBirth = generateBirth(childBirthYear, mother);
        Event fatherBirth = generateBirth(childBirthYear, father, motherBirth.getYear());


        //generates marriage events
        int minBirthYear = fatherBirth.getYear();
        if (motherBirth.getYear() > minBirthYear) minBirthYear = motherBirth.getYear();
        int marriageRange = 80 - 18;

        do {
            marriageYear = minBirthYear + ((int) (Math.random() * marriageRange) + 18);
        }while (marriageYear > 2021);

        Location marriageLocation = generateLocation();
        Event motherMarriage = generateMarriage(marriageYear, marriageLocation, mother);
        Event fatherMarriage = generateMarriage(marriageYear, marriageLocation, father);

        //generates death events
        int minDeathYear = marriageYear;
        if (childBirthYear > marriageYear) minDeathYear = childBirthYear;
        int fatherDeathYear;
        int motherDeathYear;
        if (minDeathYear >= 2021) {
            fatherDeathYear = 2021;
            motherDeathYear = 2021;
        }
        else {
            int deathRange = (fatherBirth.getYear() + 105) - minDeathYear;
            do {
                fatherDeathYear = ((int) (Math.random() * deathRange) + minDeathYear);
            }
            while (fatherDeathYear > 2021);

            deathRange = (motherBirth.getYear() + 105) - minDeathYear;
            do {
                motherDeathYear = ((int)(Math.random() * deathRange) + minDeathYear);
            }
            while (motherDeathYear > 2021);
        }
        Event fatherDeath = generateDeath(fatherDeathYear, father);
        Event motherDeath = generateDeath(motherDeathYear, mother);


        //inserts all events
        EventDAO eDao = new EventDAO(conn);
        eDao.insert(fatherBirth);
        eDao.insert(fatherMarriage);
        if(fatherDeath != null) {
            eDao.insert(fatherDeath);
            ++eventsAdded;
        }
        eventsAdded += 2;

        eDao.insert(motherBirth);
        eDao.insert(motherMarriage);
        if(motherDeath != null) {
            eDao.insert(motherDeath);
            ++eventsAdded;
        }
        eventsAdded += 2;

        generateNextGen(fatherBirth.getYear(), father, currGen);
        generateNextGen(motherBirth.getYear(), mother, currGen);
    }

    private String generateID() {
        String sample = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder builder = new StringBuilder(15);
        for (int i = 0; i < 15; ++i) {
            int j = (int)(sample.length() * Math.random());
            builder.append(sample.charAt(j));
        }
        return builder.toString();
    }

    private Location generateLocation() throws Exception{
        Gson gson = new Gson();
        Reader reader = new FileReader("json/locations.json");
        Map<?, ArrayList<Object>> map = gson.fromJson(reader, Map.class);

        ArrayList<Object> locations = map.get("data");
        int index = (int)(Math.random() * locations.size());
        LinkedTreeMap<String, ?> tmp = (LinkedTreeMap<String, ?>) locations.get(index);

        Location location = new Location();
        location.setCity((String) tmp.get("city"));
        location.setCountry((String) tmp.get("country"));
        location.setLatitude(((Double) tmp.get("latitude")).floatValue());
        location.setLongitude(((Double) tmp.get("longitude")).floatValue());

        return location;
    }

    private String getName(char prefix) throws Exception {
        if (!(prefix != 'm' || prefix != 's' || prefix != 'f')) return null;

        Gson gson = new Gson();
        Reader reader = new FileReader("json/" + prefix + "names.json");
        Map<?, ArrayList<String>> map = gson.fromJson(reader, Map.class);

        ArrayList<String> names = map.get("data");
        int index = (int)(Math.random() * names.size());
        return names.get(index);
    }

    private Event generateBirth(int childBirthYear, Person person) throws Exception{
        //generates birth
        int birthRange = 50 - 18;
        int birthYear = childBirthYear - ((int)(Math.random() * birthRange) + 18);
        Event birth = new Event(generateID(), person.getUsername(), person.getPersonID(),
                generateLocation(), "Birth " + person.getFirstName(), birthYear);
        return birth;
    }

    private Event generateBirth(int childBirthYear, Person person, int spouseBirthYear) throws Exception{
        //generates birth
        int birthRange = 7 - (-7);
        int birthYear = 0;

        do {
            birthYear = spouseBirthYear - ((int) (Math.random() * birthRange) + -5);
        }while (!(childBirthYear - birthYear > 18));

        Event birth = new Event(generateID(), person.getUsername(), person.getPersonID(),
                generateLocation(), "Birth " + person.getFirstName(), birthYear);
        return birth;
    }

    private Event generateMarriage(int marriageYear, Location location, Person person) {
        Event marriage = new Event (generateID(), person.getUsername(), person.getPersonID(),
                location, "Marriage " + person.getFirstName(), marriageYear);
        return marriage;
    }

    private Event generateDeath(int year, Person person) throws Exception{
        Event death = new Event(generateID(), person.getUsername(), person.getPersonID(),
                generateLocation(), "Death " + person.getFirstName(), year);
        return death;
    }

    public int getEventsAdded() {
        return eventsAdded;
    }

    public void setEventsAdded(int eventsAdded) {
        this.eventsAdded = eventsAdded;
    }

    public int getPeopleAdded() {
        return peopleAdded;
    }

    public void setPeopleAdded(int peopleAdded) {
        this.peopleAdded = peopleAdded;
    }
}
