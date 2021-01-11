package core;

import database.Database;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class representing a user in the system.
 */
public class User {
    private String forename;
    private String username;
    private String passwordHash;
    private boolean keepLoggedIn;
    private int id;

    private static final int PASSWORD_MIN_LENGTH = 8;

    /**
     * Getter for user's forename
     *
     * @return users's forename
     */
    public String getForename() {
        return forename;
    }

    /**
     * Getter for user's username
     *
     * @return users's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for user's password hash
     *
     * @return users's passwordHash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Getter for user's keep logged in preference
     *
     * @return users's keepLoggedIn variable
     */
    public boolean getKeepLoggedIn() {
        return keepLoggedIn;
    }

    /**
     * Getter for user id in the database
     *
     * @return users's id
     */
    public int getId(){
        return id;
    }

    /**
     * Setter assigning the value of variable keepLoggedIn. Updates the database as well.
     *
     * @param keepLoggedIn status of the user's choice of wanting to be logged in
     */
    public void setKeepLoggedIn(boolean keepLoggedIn) {
        // Sets user variable
        this.keepLoggedIn = keepLoggedIn;

        // Updates the value in the database
        PreparedStatement pStatement = null;

        String sql = "UPDATE User SET keepLoggedIn = ? WHERE username = ?";
        try {
            // Fill the prepared statement and execute
            pStatement = Database.getConnection().prepareStatement(sql);
            pStatement.setBoolean(1, this.keepLoggedIn);
            pStatement.setString(2, this.username);
            pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close prepared statement
            if (pStatement != null) {
                try {
                    pStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
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
            while (rs.next()) {
                this.username = username;   // Supplied already
                forename = rs.getString("forename");
                passwordHash = rs.getString("passwordHash");
                keepLoggedIn = rs.getBoolean("keepLoggedIn");
                id = rs.getInt("id");
                found = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closes the prepared statement and result set
            if (pStatement != null) {
                try {
                    pStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // If such user doesn't exist, throws an exception
        if (!found) throw new SQLException("Could not find username " + username + " in the database");
    }

    /**
     * Constructor which constructs a new user with given parameters.
     * To be used whilst registering a user
     *
     * @param forename forename of the user
     * @param username username of the user
     * @param password password of the user, which is later turned into password hash
     */
    public User(String forename, String username, String password) {
        // Constructs the user
        this.forename = forename;
        this.username = username;
        passwordHash = User.generatePasswordHash(password);
        keepLoggedIn = false;
    }

    /**
     * Constructor which constructs a new user with given parameters.
     * To be used when logged in user is reconstructed on startup.
     *
     * @param forename forename of the user
     * @param username username of the user
     * @param passwordHash password hash of the user
     * @param keepLoggedIn status of variable keepLoggedIn
     * @param id id of the user in the database
     */
    public User(String forename, String username, String passwordHash, Boolean keepLoggedIn, int id){
        this.forename = forename;
        this.username = username;
        this.passwordHash = passwordHash;
        this.keepLoggedIn = keepLoggedIn;
        this.id = id;
    }

    /**
     * Checks if given username already exists in the database.
     *
     * @param username username to check for existence in the database.
     * @return boolean whether username exists or not
     */
    public static boolean usernameAvailable(String username) {
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
            if (pStatement != null) {
                try {
                    pStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
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
    public static boolean addUser(User user) {
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
        } finally {
            // Closes the prepared statement and result set
            if (pStatement != null) {
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
     */
    public boolean passwordsMatch(String inputPassword) {
        String inputPasswordHash = generatePasswordHash(inputPassword);

        return inputPasswordHash.equals(this.passwordHash);
    }

    /**
     * Method which takes a password and generates a hash which will be stored in memory.
     *
     * @param password which has to be hashed
     * @return string which is a generated password hash
     */
    private static String generatePasswordHash(String password) {
        String passwordHash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            passwordHash = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return passwordHash;
    }

    /**
     * Method which checks password strength:
     * - Has to have at least PASSWORD_MIN_LENGTH letters;
     * - Has to have a number;
     * - Has to have an uppercase and a lowercase letter.
     *
     * @param password password to check for strength
     * @return boolean true if password is strong enough
     */
    public static boolean isPasswordStrong(String password) {
        // Creates patterns
        Pattern upperCase = Pattern.compile("[A-Z]");
        Pattern lowerCase = Pattern.compile("[a-z]");
        Pattern number = Pattern.compile("[0-9]");

        // Creates matchers
        Matcher hasUpperCase = upperCase.matcher(password);
        Matcher hasLowerCase = lowerCase.matcher(password);
        Matcher hasNumber = number.matcher(password);

        // Checks whether password is strong
        return hasUpperCase.find() && hasLowerCase.find() && hasNumber.find() &&
                password.length() >= PASSWORD_MIN_LENGTH;
    }

    /**
     * Method which signs out a user. Removes from the session
     * and sets the keepLoggedIn variable to false.
     */
    public static void signOutUser() {
        Session.getSession().setKeepLoggedIn(false);
        Session.cleanSession();
    }

    /**
     * Method which goes through the database and looks for a user, which is
     * logged in (variable keepLoggedIn is true).
     * If such user exists, returns this user, if not, returns null.
     *
     * @return User which is logged in or null
     */
    public static User findLoggedInUser() {
        User loggedInUser = null;
        // Constructs SQL query
        String query = "SELECT * FROM User WHERE keepLoggedIn = True";

        // Opens the database, gets the user with the specified id
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        try {
            // Prepares the statement
            pStatement = Database.getConnection().prepareStatement(query);

            //Executes the statement, gets the result set
            rs = pStatement.executeQuery();

            // If there are items in the result set, such user exists
            while (rs.next()) {
                String username = rs.getString("username");
                String forename = rs.getString("forename");
                String passwordHash = rs.getString("passwordHash");
                int id = rs.getInt("id");
                // keepLoggedIn is true, because this wouldn't happen if it wouldn't be
                loggedInUser = new User(forename, username, passwordHash, true, id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closes the prepared statement and result set
            if (pStatement != null) {
                try {
                    pStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return loggedInUser;
    }
}
