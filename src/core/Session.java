package core;

import core.enums.PopupType;
import core.enums.MarksSelection;
import javafx.application.HostServices;
import javafx.stage.Stage;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
 * Class which saves the user which is currently logged in.
 * Used for access of the logged in user's data during different points
 * of the application.
 *
 * Also saves data of the current session for functionality of different
 * application windows.
 */
public class Session {
    // Variables for dragging the Stage
    private static Stage mainStage;
    private static Stage popupStage;
    private static double xMouseOffset = 0;
    private static double yMouseOffset = 0;

    // User currently using the system
    private static User loggedUser = null;
    // Whether non-logged in user has just created a new user by registration
    private static boolean userCreatedInSession;

    // Variable for url opening in browser
    private static HostServices hostServices = null;

    // Marks: which Year selected
    private static Year marksYearSelected = null;
    // Marks: which Module selected
    private static Module marksModuleSelected = null;
    // Marks: which Assignment selected
    private static Assignment marksAssignmentSelected = null;
    // Marks: whether add or edit button is clicked
    private static PopupType marksPopupType = null;
    // Marks: current selection/display window
    private static MarksSelection marksSelectionType = null;
    // Marks: user just deleted an element
    private static boolean marksJustDeleted;

    // Time: currently selected Period
    private static Period timePeriodSelected = null;

    // Schedule: type of popup for event
    private static PopupType schedulePopupType = null;
    // Schedule: currently selected event
    private static Event scheduleEventSelected = null;
    // Schedule: currently selected week
    private static Week scheduleWeekSelected = null;
    // Schedule: currently selected period
    private static Period schedulePeriodSelected = null;
    // Schedule: event just added, edited or deleted
    private static boolean scheduleCalendarChanged;

    // Tasks: currently selected Period
    private static Period tasksPeriodSelected = null;
    // Tasks: type of popup for task
    private static PopupType tasksPopupType = null;
    // Tasks: currently selected week
    private static Week tasksWeekSelected = null;
    // Task: currently selected task
    private static Task tasksTaskSelected = null;
    // Tasks: task just added, edited or deleted
    private static boolean tasksTaskListChanged;

    // Popup: Module information popup origin
    private static String moduleInformationOrigin = null;

    /**
     * Getter for the primary stage of main window for stage dragging
     * @return primary stage of main window
     */
    public static Stage getMainStage() {
        return mainStage;
    }

    /**
     * Setter for the primary scene of main window for stage dragging
     * @param mainStage root node of primary scene
     */
    public static void setMainStage(Stage mainStage) {
        Session.mainStage = mainStage;
    }

    /**
     * Getter for the primary stage of popup window for stage dragging
     * @return primary stage of popup window
     */
    public static Stage getPopupStage() {
        return popupStage;
    }

    /**
     * Setter for the  primary scene of popup window for stage dragging
     * @param popupStage root node of primary scene
     */
    public static void setPopupStage(Stage popupStage) {
        Session.popupStage = popupStage;
    }

    /**
     * Getter for x coordinate of the mouse in relation to main stage/window
     * @return x coordinate of the mouse when clicked
     */
    public static double getXMouseOffset() {
        return xMouseOffset;
    }

    /**
     * Setter for x coordinate of the mouse in relation to main stage/window
     * @param xMouseOffset x coordinate of the mouse when clicked
     */
    public static void setXMouseOffset(double xMouseOffset) {
        Session.xMouseOffset = xMouseOffset;
    }

    /**
     * Getter for y coordinate of the mouse in relation to main stage/window
     * @return y coordinate of the mouse when clicked
     */
    public static double getYMouseOffset() {
        return yMouseOffset;
    }

    /**
     * Setter for y coordinate of the mouse in relation to main stage/window
     * @param yMouseOffset y coordinate of the mouse when clicked
     */
    public static void setYMouseOffset(double yMouseOffset) {
        Session.yMouseOffset = yMouseOffset;
    }

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
     * Returns host services used for url opening in browser.
     * @return host services of Application
     */
    public static HostServices getHostServices() {
        return hostServices;
    }

    /**
     * Sets host services used for url opening in browser.
     * @param hostServices host services of Application
     */
    public static void setHostServices(HostServices hostServices) {
        Session.hostServices = hostServices;
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
    public static PopupType getMarksPopupType() {
        return marksPopupType;
    }

    /**
     * Setter for the variable, which determines what button was clicked in Marks
     * tab and is used for popup stage scene setup.
     * Parameter can only be "Edit" or "Add".
     * @param popupType "Edit" or "Add"
     */
    public static void setMarksPopupType(PopupType popupType) {
        Session.marksPopupType = popupType;
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

    /**
     * Getter for the currently selected Period in the Time tab
     * @return user selected Period
     */
    public static Period getTimePeriodSelected() {
        return timePeriodSelected;
    }

    /**
     * Setter for the currently selected Period in the Time tab
     * @param timePeriodSelected Period which is selected by the user
     */
    public static void setTimePeriodSelected(Period timePeriodSelected) {
        Session.timePeriodSelected = timePeriodSelected;
    }

    /**
     * Getter for the current popup type in Schedule tab
     * @return user opened popup type
     */
    public static PopupType getSchedulePopupType() {
        return schedulePopupType;
    }

    /**
     * Setter for the current popup type in Schedule tab
     * @param schedulePopupType PopupType of the Schedule tab popup
     */
    public static void setSchedulePopupType(PopupType schedulePopupType) {
        Session.schedulePopupType = schedulePopupType;
    }

    /**
     * Getter for the currently selected event in Schedule tab
     * @return currently selected event by the user
     */
    public static Event getScheduleEventSelected() {
        return scheduleEventSelected;
    }

    /**
     * Setter for the currently selected event in Schedule tab
     * @param scheduleEventSelected currently selected event
     */
    public static void setScheduleEventSelected(Event scheduleEventSelected) {
        Session.scheduleEventSelected = scheduleEventSelected;
    }

    /**
     * Getter for the week, which is selected in the Schedule Period scene
     * @return currently selected week in Schedule
     */
    public static Week getScheduleWeekSelected() {
        return scheduleWeekSelected;
    }

    /**
     * Setter for the week, which is selected in the Schedule Period scene
     * @param scheduleWeekSelected week to set as selected in Schedule
     */
    public static void setScheduleWeekSelected(Week scheduleWeekSelected) {
        Session.scheduleWeekSelected = scheduleWeekSelected;
    }

    /**
     * Getter for the period, which is selected in Schedule scene
     * @return period selected in Schedule
     */
    public static Period getSchedulePeriodSelected() {
        return schedulePeriodSelected;
    }

    /**
     * Setter for the period, which is selected in Schedule scene
     * @param schedulePeriodSelected period selected in Schedule
     */
    public static void setSchedulePeriodSelected(Period schedulePeriodSelected) {
        Session.schedulePeriodSelected = schedulePeriodSelected;
    }

    /**
     * Getter for variable which is true, if user has just added a new event,
     * edited a an old event or deleted an event.
     * @return true if schedule just changed, false otherwise
     */
    public static boolean isScheduleCalendarChanged() {
        return scheduleCalendarChanged;
    }

    /**
     * Setter for variable which is true, if user has just added a new event,
     * edited a an old event or deleted an event.
     * @param scheduleCalendarChanged value to set
     */
    public static void setScheduleCalendarChanged(boolean scheduleCalendarChanged) {
        Session.scheduleCalendarChanged = scheduleCalendarChanged;
    }

    /**
     * Getter for period, which is selected in the Tasks scene.
     * @return period selected in Tasks
     */
    public static Period getTasksPeriodSelected() {
        return tasksPeriodSelected;
    }

    /**
     * Setter for period, which is selected in the Tasks scene.
     * @param tasksPeriodSelected period to set as selected
     */
    public static void setTasksPeriodSelected(Period tasksPeriodSelected) {
        Session.tasksPeriodSelected = tasksPeriodSelected;
    }

    /**
     * Getter for task editing/addition popup type in Tasks scene
     * @return popup type in Tasks scene
     */
    public static PopupType getTasksPopupType() {
        return tasksPopupType;
    }

    /**
     * Setter for task editing/addition popup type in Tasks scene
     * @param tasksPopupType popup type to set in Tasks scene
     */
    public static void setTasksPopupType(PopupType tasksPopupType) {
        Session.tasksPopupType = tasksPopupType;
    }

    /**
     * Getter for variable which returns true if a task was just edited,
     * added or deleted in the Tasks scene.
     * @return true if task was added/edited/deleted, false otherwise
     */
    public static boolean isTasksTaskListChanged() {
        return tasksTaskListChanged;
    }

    /**
     * Setter for variable which returns true if a task was just edited,
     * added or deleted in the Tasks scene.
     * @param tasksTaskListChanged boolean value to set
     */
    public static void setTasksTaskListChanged(boolean tasksTaskListChanged) {
        Session.tasksTaskListChanged = tasksTaskListChanged;
    }

    /**
     * Getter for the week, which is selected in the Tasks Period scene
     * @return currently selected week in Tasks
     */
    public static Week getTasksWeekSelected() {
        return tasksWeekSelected;
    }

    /**
     * Setter for the week, which is selected in the Tasks Period scene
     * @param tasksWeekSelected week to set as selected in Tasks
     */
    public static void setTasksWeekSelected(Week tasksWeekSelected) {
        Session.tasksWeekSelected = tasksWeekSelected;
    }

    /**
     * Getter for the task, which is currently selected in Tasks Period scene.
     * @return currently selected Task in Tasks
     */
    public static Task getTasksTaskSelected() {
        return tasksTaskSelected;
    }

    /**
     * Setter for the task, which is currently selected in Tasks Period scene.
     * @param tasksTaskSelected task to be set as selected
     */
    public static void setTasksTaskSelected(Task tasksTaskSelected) {
        Session.tasksTaskSelected = tasksTaskSelected;
    }

    /**
     * Getter for module information popup origin variable. Can be either
     * "Tasks" or "Schedule".
     * @return module information popup origin
     */
    public static String getModuleInformationOrigin() {
        return moduleInformationOrigin;
    }

    /**
     * Setter for module information popup origin variable. Can be either
     * "Tasks" or "Schedule".
     * @param moduleInformationOrigin module information popup origin
     */
    public static void setModuleInformationOrigin(String moduleInformationOrigin) {
        Session.moduleInformationOrigin = moduleInformationOrigin;
    }
}
