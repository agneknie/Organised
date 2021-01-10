package core;

/**
 * Class which saves the user which is currently logged in.
 * Used for access of the logged in user's data during different points
 * of the application.
 */
public class Session {
    private static User loggedUser = null;
    private static boolean userCreatedInSession;

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

    /**
     * Gets the variable, which informs whether the user was just created.
     * @return boolean true if user was just created (coming back from register screen)
     */
    public static boolean getUserCreatedInSession(){
        return userCreatedInSession;
    }

    /**
     * Setter for the variable userCreatedInSession.
     * @param userCreatedInSession boolean to set the variable to
     */
    public static void setUserCreatedInSession(boolean userCreatedInSession) {
        Session.userCreatedInSession = userCreatedInSession;
    }
}
