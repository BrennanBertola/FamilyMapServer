package Service;

import DAO.AuthTokenDAO;
import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDAO;
import Model.AuthToken;
import Model.User;
import Request.FillRequest;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.LoginResult;
import Result.RegisterResult;

import java.sql.Connection;

/**
 * Uses a Register Request to perform the appropriate operation on the database
 * returning the results as a Register Result
 */
public class RegisterService {
    private int defaultGen = 4;

    /**
     * Generates default Register Service
     */
    public RegisterService() {}

    /**
     * Performs Register Service on database
     * @param r Register Request
     * @return Register Result
     */
    public RegisterResult register(RegisterRequest r) {
        RegisterResult result;

        try {
            Database db = new Database();
            Connection conn = db.getConnection();
            try {
                UserDAO uDao = new UserDAO(conn);
                User user = new User(r.getUsername(), r.getPassword(), r.getEmail(), r.getFirstName(),
                        r.getLastName(), r.getGender(), generateID());
                if (uDao.find(user.getUsername()) != null) {
                    throw new Exception("username taken");
                }
                uDao.insert(user);
                db.closeConnection(true);

                FillService fillService = new FillService();
                FillRequest fillRequest = new FillRequest(user.getUsername(), defaultGen);
                fillService.fill(fillRequest);

                conn = db.getConnection();
                AuthTokenDAO aDao = new AuthTokenDAO(conn);
                AuthToken token = new AuthToken(generateID(), user.getPersonID());
                aDao.insert(token);
                db.closeConnection(true);



                LoginService loginService = new LoginService();
                LoginResult loginResult = loginService.login(covert(r));
                result = convert(loginResult);

            }
            catch (Exception e) {
                db.closeConnection(false);
                result = new RegisterResult("Error: " + e.getMessage(), false);
                return result;
            }
        }
        catch (DataAccessException e) {
            result = new RegisterResult("Error: " + e.getMessage(), false);
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

    private RegisterResult convert(LoginResult r) {
        if (r.getSuccess()) {
            RegisterResult result = new RegisterResult(r.getSuccess(), r.getAuthtoken(),
                    r.getUsername(), r.getPersonID());
            return result;
        }
        RegisterResult result = new RegisterResult(r.getMessage(), r.getSuccess());
        return result;
    }

    private LoginRequest covert(RegisterRequest r) {
        LoginRequest request = new LoginRequest(r.getUsername(), r.getPassword());
        return request;
    }
}
