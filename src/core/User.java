package core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
     * Setter assigning the value of variable keepLoggedIn.
     * @param keepLoggedIn status of the user's choice of wanting to be logged in
     */
    public void setKeepLoggedIn(boolean keepLoggedIn){
        this.keepLoggedIn = keepLoggedIn;
    }

    /**
     * Constructor which takes a username and constructs an existing user
     * from the database.
     * @param username username of the user to reconstruct
     */
    public User(String username){
        // TODO User constructor: takes username -> constructs from db
    }

    /**
     * Constructor which constructs a new user with given parameters and
     * saves them in the database.
     * @param forename forename of the user
     * @param username username of the user
     * @param password password of the user, which is later turned into password hash
     */
    public User(String forename, String username, String password){
        // TODO User constructor: takes parameters -> constructs user, adds to db
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
    private String generatePasswordHash(String password) {
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
