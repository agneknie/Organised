package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultNavigation;
import controllers.utilities.SetupScene;
import core.Session;
import core.Week;
import core.enums.PopupType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import stages.BiggerPopupStage;
import stages.PopupStage;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Finds out which week should be selected by the user
        userSelectedWeek = Week.getCurrentWeek(Session.getSchedulePeriodSelected().getAllWeeks());

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
        //TODO cleanSchedule
    }

    /**
     * Setups the schedule/calendar area of the scene with
     * event information.
     */
    private void setupSchedule(){
        // TODO setupSchedule
    }

    // Methods for navigation buttons
    /**
     * Method which forwards user to the main Schedule scene
     * for selecting periods when go back button is clicked.
     */
    @FXML
    private void goBackClicked(){
        // Changes the scene to the the general Time scene scene
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
        // Opens the popup
        Stage popup = new Stage();
        new PopupStage(popup, "SchedulePopupModuleView.fxml");
    }

    // Methods which handle clicking on timetabled events
    /**
     * Updates the event pane at the top with clicked event information.
     * Handles Monday events.
     * @param event MouseEvent to determine which event is clicked.
     */
    @FXML
    private void mondayEventsPaneClicked(MouseEvent event){
        //TODO mondayEventsPaneClicked
    }

    /**
     * Updates the event pane at the top with clicked event information.
     * Handles Tuesday events.
     * @param event MouseEvent to determine which event is clicked.
     */
    @FXML
    private void tuesdayEventsPaneClicked(MouseEvent event){
        //TODO tuesdayEventsPaneClicked
    }

    /**
     * Updates the event pane at the top with clicked event information.
     * Handles Wednesday events.
     * @param event MouseEvent to determine which event is clicked.
     */
    @FXML
    private void wednesdayEventsPaneClicked(MouseEvent event){
        //TODO wednesdayEventsPaneClicked
    }

    /**
     * Updates the event pane at the top with clicked event information.
     * Handles Thursday events.
     * @param event MouseEvent to determine which event is clicked.
     */
    @FXML
    private void thursdayEventsPaneClicked(MouseEvent event){
        //TODO thursdayEventsPaneClicked
    }

    /**
     * Updates the event pane at the top with clicked event information.
     * Handles Friday events.
     * @param event MouseEvent to determine which event is clicked.
     */
    @FXML
    private void fridayEventsPaneClicked(MouseEvent event){
        //TODO fridayEventsPaneClicked
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
