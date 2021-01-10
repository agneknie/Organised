package core;

import database.Database;

import javax.jws.soap.SOAPBinding;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class representing a user in the system.
 */
public class User {
    private String forename;
    private String username;
    private String passwordHash;
    private boolean keepLoggedIn;

    /**
     * Getter for user's forename
     * @return users's forename
     */
    public String getForename() {
        return forename;
    }

    /**
     * Getter for user's username
     * @return users's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for user's password hash
     * @return users's passwordHash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Getter for user's keep logged in preference
     * @return users's keepLoggedIn variable
     */
    public boolean getKeepLoggedIn(){
        return keepLoggedIn;
    }

    /**
     * Setter assigning the value of variable keepLoggedIn.
     * @param keepLoggedIn status of the user's choice of wanting to be logged in
     */
    public void setKeepLoggedIn(boolean keepLoggedIn){
        this.keepLoggedIn = keepLoggedIn;
    }

    /**
     * Constructor which takes a username and constructs an existing user
     * from the database.
     *
     * @param username username of the user to reconstruct
     * @throws SQLException if such username does not exist in the database
     */
    public User(String username) throws SQLException {
        // Constructs SQL query
        String query = "SELECT * FROM User WHERE username = ?";

        boolean found = false;
        // Opens the database, gets the user with the specified id
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        try {
            // Prepares the statement
            pStatement = Database.getConnection().prepareStatement(query);
            pStatement.setString(1, username);

            //Executes the statement, gets the result set
            rs = pStatement.executeQuery();

            // Gets the user data in the database
            while(rs.next()) {
                this.username = username;   // Supplied already
                forename = rs.getString("forename");
                passwordHash = rs.getString("passwordHash");
                keepLoggedIn = rs.getBoolean("keepLoggedIn");
                found = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closes the prepared statement and result set
            if (pStatement != null){
                try {
                    pStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // If such user doesn't exist, throws an exception
        if(!found) throw new SQLException("Could not find username " + username + " in the database");
    }

    /**
     * Constructor which constructs a new user with given parameters.
     *
     * @param forename forename of the user
     * @param username username of the user
     * @param password password of the user, which is later turned into password hash
     */
    public User(String forename, String username, String password){
        // Constructs the user
        this.forename = forename;
        this.username = username;
        passwordHash = User.generatePasswordHash(password);
        keepLoggedIn = false;
    }

    /**
     * Checks if given username already exists in the database.
     *
     * @param username username to check for existence in the database.
     * @return boolean whether username exists or not
     */
    public static boolean usernameAvailable(String username){
        boolean usernameAvailable = false;

        // Constructs SQL query
        String query = "SELECT * FROM User WHERE username = ?";

        // Opens the database, gets the user with the specified id
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        try {
            // Prepares the statement
            pStatement = Database.getConnection().prepareStatement(query);
            pStatement.setString(1, username);

            //Executes the statement, gets the result set
            rs = pStatement.executeQuery();

            // If there are items in the result set, the username is taken
            usernameAvailable = !rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closes the prepared statement and result set
            if (pStatement != null){
                try {
                    pStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return usernameAvailable;
    }

    /**
     * Adds user to the database.
     *
     * @param user User to add
     * @return boolean whether operation was successful
     */
    public static boolean addUser(User user){
        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;
        int rowsAffected = 0;

        // Sets up the query
        String query = "INSERT INTO User VALUES(null,?,?,?,?);";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setString(1, user.getUsername());
            pStatement.setString(2, user.getForename());
            pStatement.setString(3, user.getPasswordHash());
            pStatement.setBoolean(4, user.getKeepLoggedIn());

            // Result of query is true if SQL command worked
            rowsAffected = pStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            // Closes the prepared statement and result set
            if (pStatement != null){
                try {
                    pStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // Returns whether insertion was successful
        return rowsAffected == 1;
    }

    /**
     * Method which takes a password as a string and checks whether it matches the stored password.
     *
     * @param inputPassword password inserted by the user (whilst logging in)
     * @return true if passwords match, false otherwise
     * */
    public boolean passwordsMatch(String inputPassword) {
        String inputPasswordHash = generatePasswordHash(inputPassword);

        if(inputPasswordHash.equals(this.passwordHash)) return true;
        else return false;
    }

    /**
     * Method which takes a password and generates a hash which will be stored in memory.
     *
     * @param password which has to be hashed
     * @return string which is a generated password hash
     * */
    private static String generatePasswordHash(String password) {
        String passwordHash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < messageDigest.length; i++) {
                sb.append(Integer.toString((messageDigest[i] & 0xff) + 0x100, 16).substring(1));
            }
            passwordHash = sb.toString();
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return passwordHash;
    }
}
