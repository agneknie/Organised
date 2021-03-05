package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultNavigation;
import controllers.utilities.SetupScene;
import core.Event;
import core.Session;
import core.Week;
import core.enums.PopupType;
import core.enums.ScheduleTime;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import stages.BiggerPopupStage;
import stages.PopupStage;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
 * Class which handles Schedule Tab functionality and UI when a Period is
 * selected.
 */
public class SchedulePeriodController extends DefaultNavigation implements Initializable {

    // Period name and navigation labels
    @FXML
    private Label periodNameLabel;
    @FXML
    private Label weekNameLabel;
    @FXML
    private Label weekDateLabel;

    // Navigation buttons
    @FXML
    private ImageView goLeftButton;
    @FXML
    private ImageView goRightButton;

    // Module information button
    @FXML
    private Pane moduleInfoButton;
    @FXML
    private Label moduleInfoButtonLabel;
    @FXML
    private ImageView moduleInfoButtonImage;

    // Event information pane
    @FXML
    private Pane eventInformationPane;
    @FXML
    private Label moduleCodeLabel;
    @FXML
    private Label eventNameLabel;
    @FXML
    private Label eventDescriptionLabel;

    // Add event button
    @FXML
    private Pane addEventButton;
    @FXML
    private Label addEventButtonLabel;
    @FXML
    private ImageView addEventButtonImage;

    // Edit event button
    @FXML
    private Pane editEventButton;
    @FXML
    private Label editEventButtonLabel;
    @FXML
    private ImageView editEventButtonImage;

    // Days of week date labels
    @FXML
    private Label mondayDateLabel;
    @FXML
    private Label tuesdayDateLabel;
    @FXML
    private Label wednesdayDateLabel;
    @FXML
    private Label thursdayDateLabel;
    @FXML
    private Label fridayDateLabel;

    // Days of week events panels
    @FXML
    private Pane mondayEventsPane;
    @FXML
    private Pane tuesdayEventsPane;
    @FXML
    private Pane wednesdayEventsPane;
    @FXML
    private Pane thursdayEventsPane;
    @FXML
    private Pane fridayEventsPane;

    // User specific variables
    private Week userSelectedWeek;
    private Event[] mondayEventsList = new Event[]{null, null, null, null, null, null, null, null};
    private Event[] tuesdayEventsList = new Event[]{null, null, null, null, null, null, null, null};
    private Event[] wednesdayEventsList = new Event[]{null, null, null, null, null, null, null, null};
    private Event[] thursdayEventsList = new Event[]{null, null, null, null, null, null, null, null};
    private Event[] fridayEventsList = new Event[]{null, null, null, null, null, null, null, null};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Finds out which week should be selected by the user
        userSelectedWeek = Week.getCurrentWeek(Session.getSchedulePeriodSelected().getAllWeeks());

        // Saves selected week in session
        Session.setScheduleWeekSelected(userSelectedWeek);

        // Sets up navigation arrow visibility
        configureNavigationArrows();

        // Sets up period and week information at the top
        setupTopInformation();

        // Sets up dates of days in the calendar
        setupWeekdaysDates();

        // Disables visibility of event pane
        eventInformationPane.setVisible(false);

        // Disables visibility of edit event button, because no event is selected yet
        editEventButton.setVisible(false);

        // Setups the schedule
        setupSchedule();
    }

    /**
     * Method which refreshes the schedule/calendar area of the screen
     * in case user added/edited an event.
     */
    @FXML
    private void refreshScene(){
        // If calendar/schedule changed
        if(Session.isScheduleCalendarChanged()){
            // Resets session variable
            Session.setScheduleCalendarChanged(false);
            // Hides the event pane in case event modified or deleted
            eventInformationPane.setVisible(false);
            // Hides the edit button in case event modified or deleted
            editEventButton.setVisible(false);
            // Updates the calendar/schedule
            cleanSchedule();
            setupSchedule();
        }
    }

    /**
     * Method which setups period and week information at the
     * top of the scene.
     */
    private void setupTopInformation(){
        // Sets up period name label
        periodNameLabel.setText("Year " + Session.getSchedulePeriodSelected().getAssociatedYear() +
                ": " + Session.getSchedulePeriodSelected().getName());

        // Sets up week information labels
        weekNameLabel.setText(userSelectedWeek.toString());
        weekDateLabel.setText(userSelectedWeek.getWeekDate());
    }

    /**
     * Method which setups the dates of each weekday in the calendar.
     */
    private void setupWeekdaysDates(){
        mondayDateLabel.setText(userSelectedWeek.getDay(DayOfWeek.MONDAY).getShortDate());
        tuesdayDateLabel.setText(userSelectedWeek.getDay(DayOfWeek.TUESDAY).getShortDate());
        wednesdayDateLabel.setText(userSelectedWeek.getDay(DayOfWeek.WEDNESDAY).getShortDate());
        thursdayDateLabel.setText(userSelectedWeek.getDay(DayOfWeek.THURSDAY).getShortDate());
        fridayDateLabel.setText(userSelectedWeek.getDay(DayOfWeek.FRIDAY).getShortDate());
    }

    /**
     * Method which configures the visibility of navigation arrows,
     * which are used for navigation between weeks.
     */
    private void configureNavigationArrows(){
        // Finds the index of the selected Week in the list of all Weeks of Period
        List<Week> userWeeks = Session.getSchedulePeriodSelected().getAllWeeks();
        int indexOfSelectedWeek = userWeeks.indexOf(userSelectedWeek);

        // Makes both arrows visible, in case previously disabled
        goLeftButton.setVisible(true);
        goRightButton.setVisible(true);

        // Disables the arrows, based on current week selection
        if(indexOfSelectedWeek == 0) goLeftButton.setVisible(false);
        if(indexOfSelectedWeek == userWeeks.size()-1) goRightButton.setVisible(false);
    }

    /**
     * Method which updates the scene after user navigates from one week to other.
     */
    private void updateAfterNavigation(){
        // Updates weekdays' labels
        setupWeekdaysDates();

        // Updates week information
        weekNameLabel.setText(userSelectedWeek.toString());
        weekDateLabel.setText(userSelectedWeek.getWeekDate());

        // Configures navigation arrows
        configureNavigationArrows();

        // Disables visibility of event pane
        eventInformationPane.setVisible(false);

        // Disables visibility of edit event button, because no event is selected yet
        editEventButton.setVisible(false);

        // Setups the schedule
        cleanSchedule();
        setupSchedule();
    }

    /**
     * Cleans the schedule/calendar area of the scene.
     */
    private void cleanSchedule(){
        // Adds all panes to a list of panes
        List<Pane> weekdaysPanes = new ArrayList<>();
        weekdaysPanes.add(mondayEventsPane);
        weekdaysPanes.add(tuesdayEventsPane);
        weekdaysPanes.add(wednesdayEventsPane);
        weekdaysPanes.add(thursdayEventsPane);
        weekdaysPanes.add(fridayEventsPane);

        // Goes through all panes
        for(Pane weekdayPane : weekdaysPanes){
            // Goes through all time slot labels of the weekday
            for(Node timeSlotNode : weekdayPane.getChildren()){
                Label timeSlot = (Label) timeSlotNode;
                // Resets the background of the time slot
                timeSlot.setStyle("-fx-background-color: transparent; " +
                        "-fx-background-radius: 10;");
                // Resets the text of time slot
                timeSlot.setText("");
            }
        }

        // Deletes all references from references lists
        mondayEventsList = new Event[]{null, null, null, null, null, null, null, null};
        tuesdayEventsList = new Event[]{null, null, null, null, null, null, null, null};
        wednesdayEventsList = new Event[]{null, null, null, null, null, null, null, null};
        thursdayEventsList = new Event[]{null, null, null, null, null, null, null, null};
        fridayEventsList = new Event[]{null, null, null, null, null, null, null, null};
    }

    /**
     * Setups the schedule/calendar area of the scene with event information.
     */
    private void setupSchedule(){
        setupMondaySchedule();
        setupTuesdaySchedule();
        setupWednesdaySchedule();
        setupThursdaySchedule();
        setupFridaySchedule();
    }

    /**
     * Method which setups schedule of Monday.
     */
    private void setupMondaySchedule(){
        // Gets all events of Monday
        List<Event> dayEvents = userSelectedWeek.getDay(DayOfWeek.MONDAY).getAllEvents();
        // Goes through all events of that day
        for(Event event : dayEvents){
            List<Label> timeSlots = event.getTimeSlotsOfEvent(mondayEventsPane);
            // Adds event to references list
            addToEventList(event, mondayEventsList);
            // Sets up the event
            setupEvent(event, timeSlots);
        }
    }

    /**
     * Method which setups schedule of Tuesday.
     */
    private void setupTuesdaySchedule(){
        // Gets all events of Tuesday
        List<Event> dayEvents = userSelectedWeek.getDay(DayOfWeek.TUESDAY).getAllEvents();
        // Goes through all events of that day
        for(Event event : dayEvents){
            List<Label> timeSlots = event.getTimeSlotsOfEvent(tuesdayEventsPane);
            // Adds event to references list
            addToEventList(event, tuesdayEventsList);
            // Sets up the event
            setupEvent(event, timeSlots);
        }
    }

    /**
     * Method which setups schedule of Wednesday.
     */
    private void setupWednesdaySchedule(){
        // Gets all events of Wednesday
        List<Event> dayEvents = userSelectedWeek.getDay(DayOfWeek.WEDNESDAY).getAllEvents();
        // Goes through all events of that day
        for(Event event : dayEvents){
            List<Label> timeSlots = event.getTimeSlotsOfEvent(wednesdayEventsPane);
            // Adds event to references list
            addToEventList(event, wednesdayEventsList);
            // Sets up the event
            setupEvent(event, timeSlots);
        }
    }

    /**
     * Method which setups schedule of Wednesday.
     */
    private void setupThursdaySchedule(){
        // Gets all events of Thursday
        List<Event> dayEvents = userSelectedWeek.getDay(DayOfWeek.THURSDAY).getAllEvents();
        // Goes through all events of that day
        for(Event event : dayEvents){
            List<Label> timeSlots = event.getTimeSlotsOfEvent(thursdayEventsPane);
            // Adds event to references list
            addToEventList(event, thursdayEventsList);
            // Sets up the event
            setupEvent(event, timeSlots);
        }
    }

    /**
     * Method which setups schedule of Wednesday.
     */
    private void setupFridaySchedule(){
        // Gets all events of Friday
        List<Event> dayEvents = userSelectedWeek.getDay(DayOfWeek.FRIDAY).getAllEvents();
        // Goes through all events of that day
        for(Event event : dayEvents){
            List<Label> timeSlots = event.getTimeSlotsOfEvent(fridayEventsPane);
            // Adds event to references list
            addToEventList(event, fridayEventsList);
            // Sets up the event
            setupEvent(event, timeSlots);
        }
    }

    /**
     * Method which takes an event and adds it to the relevant event list.
     * Each event list has 8 elements, for each time slot/label.
     *
     * @param event event to add to the list
     * @param eventArray list to add the event to
     */
    private void addToEventList(Event event, Event[] eventArray){
        // Picks the time slot which is the start of the event
        int timeSlotNumber = 0;
        switch (event.getStartTime()){
            case NINE:
                timeSlotNumber = 0;
                break;
            case TEN:
                timeSlotNumber = 1;
                break;
            case ELEVEN:
                timeSlotNumber = 2;
                break;
            case TWELVE:
                timeSlotNumber = 3;
                break;
            case THIRTEEN:
                timeSlotNumber = 4;
                break;
            case FOURTEEN:
                timeSlotNumber = 5;
                break;
            case FIFTEEN:
                timeSlotNumber = 6;
                break;
            case SIXTEEN:
                timeSlotNumber = 7;
                break;
        }

        // Finds event length and adds that event to the reference for each time slot
        int eventLength = ScheduleTime.hoursBetweenTimes(event.getStartTime(), event.getEndTime());
        while(eventLength!=0){
            eventArray[timeSlotNumber] = event;
            timeSlotNumber++;
            eventLength--;
        }
    }

    /**
     * Method which setups the event in the schedule.
     *
     * @param event event to setup
     * @param timeSlots List of labels which represent time slots during which
     *                  the event takes place.
     */
    private void setupEvent(Event event, List<Label> timeSlots){
        // Gets the colour of event, because it's more efficient than querying the db each time
        String eventColorString = event.getEventColourString();
        Color eventColor = event.getEventColour();

        // Constant for colour to change text colour
        final double COLOR_THRESHOLD = 500.0;
        // Configures text colour if background is too fair
        double colourOverall = eventColor.getRed()*255 + eventColor.getBlue()*255 + eventColor.getGreen()*255;
        if(colourOverall >= COLOR_THRESHOLD)
            timeSlots.get(0).setTextFill(Paint.valueOf("#2B2B2B"));
        else
            timeSlots.get(0).setTextFill(Paint.valueOf("#FFFFFF"));

        // If event is just an hour long
        if(timeSlots.size()==1){
            timeSlots.get(0).setText(event.getName());
            timeSlots.get(0).setStyle("-fx-background-color: "+eventColorString+"; " +
                    "-fx-background-radius: 15; -fx-border-style: solid; " +
                    "-fx-border-color: #2B2B2B; -fx-border-width: 2; -fx-border-radius: 10;" +
                    "-fx-wrap-text: true; -fx-text-alignment: center;");
        }
        // If event is more than an hour long
        else{
            // Sets the name of event in the first time slot
            timeSlots.get(0).setText(event.getName());
            // Sets the style of the first time slot
            timeSlots.get(0).setStyle("-fx-background-color: "+eventColorString+"; " +
                    "-fx-background-radius: 15 15 0 0; -fx-border-style: solid solid hidden solid; " +
                    "-fx-border-color: #2B2B2B; -fx-border-width: 2; -fx-border-radius: 10 10 0 0;" +
                    "-fx-wrap-text: true; -fx-text-alignment: center;");

            // Sets the style of the last time slot
            timeSlots.get(timeSlots.size()-1).setStyle("-fx-background-color: "+eventColorString+"; " +
                    "-fx-background-radius: 0 0 15 15; -fx-border-style: hidden solid solid solid; " +
                    "-fx-border-color: #2B2B2B; -fx-border-width: 2; -fx-border-radius: 0 0 10 10;" +
                    "-fx-wrap-text: true; -fx-text-alignment: center;");

            // Removes the first and last time slots form the list of time slots
            timeSlots.remove(0);
            timeSlots.remove(timeSlots.size()-1);

            // Sets the style of remaining time slots
            for(Label timeSlot : timeSlots){
                timeSlot.setStyle("-fx-background-color: "+eventColorString+"; " +
                        "-fx-background-radius: 0;" +
                        "-fx-wrap-text: true; -fx-text-alignment: center;");
            }
        }
    }

    // Methods for navigation buttons
    /**
     * Method which forwards user to the main Schedule scene
     * for selecting periods when go back button is clicked.
     */
    @FXML
    private void goBackClicked(){
        // Changes the scene to the the general Schedule scene
        try {
            SetupScene.changeScene("ScheduleView.fxml", goBackButton);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene from Period specific Schedule to general Schedule view.");
        }
    }

    /**
     * Method which handles navigation between weeks.
     * Goes to the previous week.
     */
    @FXML
    private void goLeftClicked() {
        // Finds the index of the selected Week in the list of all Weeks of Period
        List<Week> userWeeks = Session.getSchedulePeriodSelected().getAllWeeks();
        int indexOfSelectedWeek = userWeeks.indexOf(userSelectedWeek);

        // Updates the displayed week
        userSelectedWeek = userWeeks.get(indexOfSelectedWeek-1);

        // Saves selected week in session
        Session.setScheduleWeekSelected(userSelectedWeek);

        // Updates the scene
        updateAfterNavigation();
    }

    /**
     * Method which handles navigation between weeks.
     * Goes to the next week.
     */
    @FXML
    private void goRightClicked() {
        // Finds the index of the selected Week in the list of all Weeks of Period
        List<Week> userWeeks = Session.getSchedulePeriodSelected().getAllWeeks();
        int indexOfSelectedWeek = userWeeks.indexOf(userSelectedWeek);

        // Updates the displayed week
        userSelectedWeek = userWeeks.get(indexOfSelectedWeek+1);

        // Saves selected week in session
        Session.setScheduleWeekSelected(userSelectedWeek);

        // Updates the scene
        updateAfterNavigation();
    }

    // Methods handling button functionality
    /**
     * Method which opens the event addition popup when
     * add event button is clicked.
     */
    @FXML
    private void addEventButtonClicked() throws IOException {
        // Sets the popup type
        Session.setSchedulePopupType(PopupType.ADD);

        // Opens the popup
        Stage popup = new Stage();
        new BiggerPopupStage(popup, "SchedulePopupViewEvent.fxml");
    }

    /**
     * Method which opens the event editing popup when
     * edit event button is clicked.
     */
    @FXML
    private void editEventButtonClicked() throws IOException {
        // Sets the popup type
        Session.setSchedulePopupType(PopupType.EDIT);

        // Opens the popup
        Stage popup = new Stage();
        new BiggerPopupStage(popup, "SchedulePopupViewEvent.fxml");
    }

    /**
     * Method which brings out the popup, which contains the
     * information about the modules of the period.
     */
    @FXML
    private void moduleInfoButtonClicked() throws IOException {
        // Sets the session variable
        Session.setModuleInformationOrigin("Schedule");

        // Opens the popup
        Stage popup = new Stage();
        new PopupStage(popup, "ModuleInformationPopupView.fxml");
    }

    // Methods which handle clicking on timetabled events
    /**
     * Updates the event pane at the top with clicked event information.
     * Handles Monday events.
     * @param event MouseEvent to determine which event is clicked.
     */
    @FXML
    private void mondayEventsPaneClicked(MouseEvent event){
        double yCoordinate = event.getY();
        // If click was on an event timeslot
        if(yCoordinate<=512){
            Session.setScheduleEventSelected(mondayEventsList[findClickedTimeSlot(yCoordinate)]);
            // Displays event information in the event pane
            displayEventInfo();
        }
    }

    /**
     * Updates the event pane at the top with clicked event information.
     * Handles Tuesday events.
     * @param event MouseEvent to determine which event is clicked.
     */
    @FXML
    private void tuesdayEventsPaneClicked(MouseEvent event){
        double yCoordinate = event.getY();
        // If click was on an event timeslot
        if(yCoordinate<=512){
            Session.setScheduleEventSelected(tuesdayEventsList[findClickedTimeSlot(yCoordinate)]);
            // Displays event information in the event pane
            displayEventInfo();
        }
    }

    /**
     * Updates the event pane at the top with clicked event information.
     * Handles Wednesday events.
     * @param event MouseEvent to determine which event is clicked.
     */
    @FXML
    private void wednesdayEventsPaneClicked(MouseEvent event){
        double yCoordinate = event.getY();
        // If click was on an event timeslot
        if(yCoordinate<=512){
            Session.setScheduleEventSelected(wednesdayEventsList[findClickedTimeSlot(yCoordinate)]);
            // Displays event information in the event pane
            displayEventInfo();
        }
    }

    /**
     * Updates the event pane at the top with clicked event information.
     * Handles Thursday events.
     * @param event MouseEvent to determine which event is clicked.
     */
    @FXML
    private void thursdayEventsPaneClicked(MouseEvent event){
        double yCoordinate = event.getY();
        // If click was on an event timeslot
        if(yCoordinate<=512){
            Session.setScheduleEventSelected(thursdayEventsList[findClickedTimeSlot(yCoordinate)]);
            // Displays event information in the event pane
            displayEventInfo();
        }
    }

    /**
     * Updates the event pane at the top with clicked event information.
     * Handles Friday events.
     * @param event MouseEvent to determine which event is clicked.
     */
    @FXML
    private void fridayEventsPaneClicked(MouseEvent event){
        double yCoordinate = event.getY();
        // If click was on an event timeslot
        if(yCoordinate<=512){
            Session.setScheduleEventSelected(fridayEventsList[findClickedTimeSlot(yCoordinate)]);
            // Displays event information in the event pane
            displayEventInfo();
        }
    }

    /**
     * Method which finds the number of the time slot which was clicked
     * in a weekday event pane.
     *
     * @param yCoordinate y coordinate of the mouse click
     */
    private int findClickedTimeSlot(double yCoordinate){
        final int TIME_SLOT_HEIGHT = 64;
        if(yCoordinate<=TIME_SLOT_HEIGHT) return 0;
        else if(yCoordinate<=TIME_SLOT_HEIGHT*2) return 1;
        else if(yCoordinate<=TIME_SLOT_HEIGHT*3) return 2;
        else if(yCoordinate<=TIME_SLOT_HEIGHT*4) return 3;
        else if(yCoordinate<=TIME_SLOT_HEIGHT*5) return 4;
        else if(yCoordinate<=TIME_SLOT_HEIGHT*6) return 5;
        else if(yCoordinate<=TIME_SLOT_HEIGHT*7) return 6;
        else return 7;
    }

    /**
     * Method which sets up the event information pane.
     * Sets up with event, which is selected in Session variable.
     */
    private void displayEventInfo(){
        Event event = Session.getScheduleEventSelected();
        if(event!=null){
            // Populates event information pane with event data
            eventInformationPane.setVisible(true);
            moduleCodeLabel.setText(event.getModuleCode());
            eventNameLabel.setText(event.getName());
            eventDescriptionLabel.setText(event.getDescription());

            // Enables the edit button
            editEventButton.setVisible(true);
        }
    }

    // Methods handling styling of scene elements
    /**
     * Changes the styling of addEvent button if hover
     * ended/mouse exited.
     */
    @FXML
    private void addEventButtonExited() {
        ControlScene.buttonExited(addEventButton, addEventButtonImage,
                addEventButtonLabel, "add_icon.png");
    }

    /**
     * Changes the styling of addEvent button if hovered.
     */
    @FXML
    private void addEventButtonHovered() {
        ControlScene.buttonHovered(addEventButton, addEventButtonImage,
                addEventButtonLabel, "add_icon_selected.png");
    }

    /**
     * Changes the styling of editEvent button if hover
     * ended/mouse exited.
     */
    @FXML
    private void editEventButtonExited() {
        ControlScene.buttonExited(editEventButton, editEventButtonImage,
                editEventButtonLabel, "edit_icon.png");
    }

    /**
     * Changes the styling of editEvent button if hovered.
     */
    @FXML
    private void editEventButtonHovered() {
        ControlScene.buttonHovered(editEventButton, editEventButtonImage,
                editEventButtonLabel, "edit_icon_selected.png");
    }

    /**
     * Changes the styling of goLeft button if hover
     * ended/mouse exited.
     */
    @FXML
    private void goLeftExited() {
        ControlScene.controlButtonEffect("next_element_left.png", goLeftButton);
    }

    /**
     * Changes the styling of goLeft button if hovered.
     */
    @FXML
    private void goLeftHovered() {
        ControlScene.controlButtonEffect("next_element_left_selected.png", goLeftButton);
    }

    /**
     * Changes the styling of goRight button if hover
     * ended/mouse exited.
     */
    @FXML
    private void goRightExited() {
        ControlScene.controlButtonEffect("next_element_right.png", goRightButton);
    }

    /**
     * Changes the styling of goRight button if hovered.
     */
    @FXML
    private void goRightHovered() {
        ControlScene.controlButtonEffect("next_element_right_selected.png", goRightButton);
    }

    /**
     * Changes the styling of moduleInfo button if hover
     * ended/mouse exited.
     */
    @FXML
    private void moduleInfoButtonExited() {
        ControlScene.buttonExited(moduleInfoButton, moduleInfoButtonImage,
                moduleInfoButtonLabel, "info_icon.png");
    }

    /**
     * Changes the styling of moduleInfo button if hovered.
     */
    @FXML
    private void moduleInfoButtonHovered() {
        ControlScene.buttonHovered(moduleInfoButton, moduleInfoButtonImage,
                moduleInfoButtonLabel, "info_icon_selected.png");
    }
}
