package Service;

import DAO.AuthTokenDAO;
import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDAO;
import Model.AuthToken;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;


import java.sql.Connection;

/**
 * Uses a Login Request to perform the appropriate operation on the database
 * returning the results as a Login Result
 */
public class LoginService {
    /**
     * Generates default Login Service object
     */
    public LoginService() {}

    /**
     * Performs Login Service on database
     * @param r Login Request
     * @return Login Result
     */
    public LoginResult login(LoginRequest r) {
        LoginResult result;
        boolean commit = false;
        try {
            Database db = new Database();
            Connection conn = db.getConnection();
            try {
                UserDAO uDao = new UserDAO(conn);
                User user = uDao.find(r.getUsername());

                if (user == null) {
                    throw new Exception("Username or password do not match");
                }
                if (!user.getPassword().equals(r.getPassword())) {
                    throw new Exception("Username or passwords do not match");
                }

                //get auth token or creates new one
                AuthTokenDAO aDao = new AuthTokenDAO(conn);
                AuthToken aToken = aDao.findWithID(user.getPersonID());
                if (aToken == null) {
                    aToken = new AuthToken(generateID(), user.getPersonID());
                    aDao.insert(aToken);
                    commit = true;
                }

                result = new LoginResult(true, aToken.getAuthToken(), user.getUsername(), user.getPersonID());
                db.closeConnection(commit);
            }
            catch (Exception e) {
                db.closeConnection(false);
                result = new LoginResult("Error: " + e.getMessage() , false);
                return result;
            }
        }
        catch (DataAccessException e) {
            result = new LoginResult("Error: " + e.getMessage(), false);
            return result;
        }



        return result;
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
}
