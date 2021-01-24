package core;

import core.enums.MarksPopupType;
import core.enums.MarksSelection;

/**
 * Class which saves the user which is currently logged in.
 * Used for access of the logged in user's data during different points
 * of the application.
 *
 * Also saves data of the current session for functionality of different
 * application windows.
 */
public class Session {
    // User currently using the system
    private static User loggedUser = null;
    // Whether non-logged in user has just created a new user by registration
    private static boolean userCreatedInSession;

    // Marks: which Year selected
    private static Year marksYearSelected = null;
    // Marks: which Module selected
    private static Module marksModuleSelected = null;
    // Marks: which Assignment selected
    private static Assignment marksAssignmentSelected = null;
    // Marks: whether add or edit button is clicked
    private static MarksPopupType marksPopupType = null;
    // Marks: current selection/display window
    private static MarksSelection marksSelectionType = null;
    // Marks: user just deleted an element
    private static boolean marksJustDeleted;

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

    /**
     * Getter for currently selected Year in Marks tab.
     * @return Year object
     */
    public static Year getMarksYearSelected() {
        return marksYearSelected;
    }

    /**
     * Setter for the Year which is currently selected by the user.
     * @param marksYearSelected Year to set
     */
    public static void setMarksYearSelected(Year marksYearSelected) {
        Session.marksYearSelected = marksYearSelected;
    }

    /**
     * Getter for currently selected Module in Marks tab.
     * @return Module object
     */
    public static Module getMarksModuleSelected() {
        return marksModuleSelected;
    }

    /**
     * Setter for the Module which is currently selected by the user.
     * @param marksModuleSelected Module to set
     */
    public static void setMarksModuleSelected(Module marksModuleSelected) {
        Session.marksModuleSelected = marksModuleSelected;
    }

    /**
     * Getter for value of a button, which is clicked
     * in Marks tab. Used for setting up the popup scene.
     * @return Either "Edit" or "Add"
     */
    public static MarksPopupType getMarksPopupType() {
        return marksPopupType;
    }

    /**
     * Setter for the variable, which determines what button was clicked in Marks
     * tab and is used for popup stage scene setup.
     * Parameter can only be "Edit" or "Add".
     * @param marksPopupType "Edit" or "Add"
     */
    public static void setMarksPopupType(MarksPopupType marksPopupType) {
        Session.marksPopupType = marksPopupType;
    }

    /**
     * Getter for currently selected Assignment in Marks tab.
     * @return Assignment object
     */
    public static Assignment getMarksAssignmentSelected() {
        return marksAssignmentSelected;
    }

    /**
     * Setter for the Assignment which is currently selected by the user.
     * @param marksAssignmentSelected Assignment to set
     */
    public static void setMarksAssignmentSelected(Assignment marksAssignmentSelected) {
        Session.marksAssignmentSelected = marksAssignmentSelected;
    }

    /**
     * Getter for the currently selected type in the Marks Tab
     * @return Marks Tab type
     */
    public static MarksSelection getMarksSelectionType() {
        return marksSelectionType;
    }

    /**
     * Setter for the current Marks Selection type
     * @param marksSelectionType Marks Tab type to set
     */
    public static void setMarksSelectionType(MarksSelection marksSelectionType) {
        Session.marksSelectionType = marksSelectionType;
    }

    /**
     * Getter for the variable which marks if user just deleted Year/Module/Assignment
     * @return true if deletion just happened, false otherwise
     */
    public static boolean getMarksJustDeleted() {
        return marksJustDeleted;
    }

    /**
     * Setter for the variable which marks if user just deleted Year/Module/Assignment
     * @param marksJustDeleted boolean to set
     */
    public static void setMarksJustDeleted(boolean marksJustDeleted) {
        Session.marksJustDeleted = marksJustDeleted;
    }
}
