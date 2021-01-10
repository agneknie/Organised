package core;

/**
 * Class which saves the user which is currently logged in.
 * Used for access of the logged in user's data during different points
 * of the application.
 */
public class Session {
    private static User loggedUser = null;

    /**
     * Cleans the session by resetting the logged in user to null.
     */
    public static void cleanSession(){
        loggedUser = null;
    }

    /**
     * Begins the session by assigning a user to the Session class
     * @param currentUser user which is to be logged in
     */
    public static void beginSession(User currentUser){
        loggedUser = currentUser;
    }

    /**
     * Returns the user of the current session
     * @return user which is logged in the current session
     */
    public static User getSession(){
        return loggedUser;
    }
}
