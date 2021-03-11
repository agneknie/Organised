package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultNavigation;
import core.*;
import core.enums.PopupType;
import core.enums.ScheduleTime;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
 * Controller for Event popup in the Schedule tab.
 * Used when an Event is added, edited or deleted.
 */
public class SchedulePopupEventController extends DefaultNavigation implements Initializable {

    // Popup label
    @FXML
    private Label titleLabel;

    // Fields of the popup
    @FXML
    private ComboBox<Day> dayOfWeekComboBox;
    @FXML
    private ComboBox<Module> associatedModuleComboBox;
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<ScheduleTime> startTimeComboBox;
    @FXML
    private ComboBox<ScheduleTime> endTimeComboBox;
    @FXML
    private TextArea descriptionField;
    @FXML
    private Label errorMessageField;

    // Action button (either add or edit)
    @FXML
    private Pane actionButton;
    @FXML
    private ImageView actionButtonImage;
    @FXML
    private Label actionButtonLabel;

    // Delete button
    @FXML
    private Pane deleteButton;
    @FXML
    private ImageView deleteButtonImage;
    @FXML
    private Label deleteButtonLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Sets up the popup according to its type
        setupPopupBasedOnType();

        // Populates the combo box with Day of Week values
        dayOfWeekComboBox.getItems().setAll(Session.getScheduleWeekSelected().getWorkingDays());
        // Styles Day of Week combo box text
        ControlScene.setupComboBoxStyle(dayOfWeekComboBox);

        // Populates the combo box with modules of period
        int yearNumber = Session.getSchedulePeriodSelected().getAssociatedYear();
        int userId = Session.getSession().getId();
        associatedModuleComboBox.getItems().setAll(Year.yearFromUserIdAndNumber(userId, yearNumber).getAllModules());
        // Styles modules combo box text
        ControlScene.setupComboBoxStyle(associatedModuleComboBox);

        // Populates the combo box with available start times
        startTimeComboBox.getItems().setAll(ScheduleTime.getStartTimes());
        // Styles start times combo box text
        ControlScene.setupComboBoxStyle(startTimeComboBox);

        // Populates the combo box with available end times
        endTimeComboBox.getItems().setAll(ScheduleTime.getEndTimes());
        // Styles end times combo box text
        ControlScene.setupComboBoxStyle(endTimeComboBox);
    }

    /**
     * Listener for keyboard events.
     * If enter is pressed, action button is activated.
     * @param event used for identifying the key
     */
    @FXML
    private void keyPressed(KeyEvent event) {
        // Enter pressed
        if(event.getCode().toString().equals("ENTER")) actionButtonClicked();
    }

    /**
     * Method which setups the popup fields/buttons, which are influenced
     * by type (either add or edit)
     */
    public void setupPopupBasedOnType(){
        PopupType sceneType = Session.getSchedulePopupType();
        // Sets up the top title
        titleLabel.setText(sceneType + " Event.");

        // Sets up the action button
        actionButtonLabel.setText(sceneType.toString());

        Image newImage = new Image(SchedulePopupEventController.class.getResourceAsStream
                ("/"+sceneType.toString().toLowerCase() + "_icon.png"));
        actionButtonImage.setImage(newImage);

        // Hides delete button if popup is Add
        if(sceneType == PopupType.ADD) deleteButton.setVisible(false);
        // Fills popup fields with selected event data if popup is Edit
        else{
            Event selectedEvent = Session.getScheduleEventSelected();
            dayOfWeekComboBox.setValue(selectedEvent.getDay());
            associatedModuleComboBox.setValue(selectedEvent.getModule());
            nameField.setText(selectedEvent.getName());
            startTimeComboBox.setValue(selectedEvent.getStartTime());
            endTimeComboBox.setValue(selectedEvent.getEndTime());
            descriptionField.setText(selectedEvent.getDescription());
        }
    }

    // Methods handling button clicks
    /**
     * Method which handles actions when action button (add or edit) is clicked.
     */
    @FXML
    private void actionButtonClicked(){
        // If action button is 'Add'
        if(Session.getSchedulePopupType() == PopupType.ADD) addButtonClicked();

        // If action button is 'Edit'
        else editButtonClicked();
    }

    /**
     * Method which adds a new event if action button is clicked when
     * it is supposed to be and add button.
     */
    private void addButtonClicked(){
        // Normalises all fields in case they were marked as wrong before
        normaliseAllFields();

        // Variables for new event construction
        boolean valid = true;
        Day day = dayOfWeekComboBox.getValue();
        Module module = associatedModuleComboBox.getValue();
        String name = nameField.getText();
        ScheduleTime startTime = startTimeComboBox.getValue();
        ScheduleTime endTime = endTimeComboBox.getValue();
        String description = descriptionField.getText();

        // Checks whether inputs are not null/not empty
        if(day==null){
            valid = false;
            ControlScene.highlightWrongField(dayOfWeekComboBox);
        }
        if(module==null){
            valid = false;
            ControlScene.highlightWrongField(associatedModuleComboBox);
        }
        if(name.isEmpty()){
            valid = false;
            ControlScene.highlightWrongField(nameField);
        }
        if(startTime==null){
            valid = false;
            ControlScene.highlightWrongField(startTimeComboBox);
        }
        if(endTime==null){
            valid = false;
            ControlScene.highlightWrongField(endTimeComboBox);
        }
        if(description.isEmpty()){
            valid = false;
            ControlScene.highlightWrongField(descriptionField);
        }

        // Checks if inputs are valid
        if(valid && ScheduleTime.scheduleTimeToInt(startTime)>=ScheduleTime.scheduleTimeToInt(endTime)){
            valid = false;
            ControlScene.highlightWrongField(startTimeComboBox);
            ControlScene.highlightWrongField(endTimeComboBox);
        }

        // If all fields are valid creates the event
        if(valid){
            Event newEvent = new Event(Session.getSession().getId(), day.getId(), module.getId(), name,
                    Event.alterEventDescription(Session.getScheduleWeekSelected().getWeekNumber(), description),
                    startTime, endTime);
            // If newly created event clashes with existing events in the database it is not added
            if(newEvent.isTimeConflicting()){
                ControlScene.highlightWrongField(startTimeComboBox);
                ControlScene.highlightWrongField(endTimeComboBox);
                errorMessageField.setText("Event's time clashes with another event's time.");
            }
            // If event doesn't clash it is added to the database
            else{
                // Adds event to the database
                newEvent.addEvent();
                // Updates the session for calendar change
                Session.setScheduleCalendarChanged(true);
                // Closes the popup window
                ((Stage) actionButton.getScene().getWindow()).close();
            }
        }
    }

    /**
     * Method which edits an event if action button is clicked when
     * it is supposed to be and edit button.
     */
    private void editButtonClicked(){
        // Normalises all fields in case they were marked as wrong before
        normaliseAllFields();

        // Variables for event updating
        boolean valid = true;
        Day day = dayOfWeekComboBox.getValue();
        Module module = associatedModuleComboBox.getValue();
        String name = nameField.getText();
        ScheduleTime startTime = startTimeComboBox.getValue();
        ScheduleTime endTime = endTimeComboBox.getValue();
        String description = descriptionField.getText();

        // Checks whether inputs are not empty
        if(name.isEmpty()){
            valid = false;
            ControlScene.highlightWrongField(nameField);
        }
        if(description.isEmpty()){
            valid = false;
            ControlScene.highlightWrongField(descriptionField);
        }

        // Checks if inputs are valid
        if(valid && ScheduleTime.scheduleTimeToInt(startTime)>=ScheduleTime.scheduleTimeToInt(endTime)){
            valid = false;
            ControlScene.highlightWrongField(startTimeComboBox);
            ControlScene.highlightWrongField(endTimeComboBox);
        }

        // If all fields are valid updates the event
        if(valid){
            Event thisEvent = Session.getScheduleEventSelected();
            // Updates the time to check if it would clash
            ScheduleTime oldStartTime = thisEvent.getStartTime();
            ScheduleTime oldEndTime = thisEvent.getEndTime();
            thisEvent.setStartTime(startTime);
            thisEvent.setEndTime(endTime);
            thisEvent.updateEvent();
            // If newly updated event time clashes with existing events in the database the update is reversed
            if(thisEvent.isTimeConflicting()){
                ControlScene.highlightWrongField(startTimeComboBox);
                ControlScene.highlightWrongField(endTimeComboBox);
                errorMessageField.setText("Event's time clashes with another event's time.");
                // Reverts the event back to its old time
                thisEvent.setStartTime(oldStartTime);
                thisEvent.setEndTime(oldEndTime);
                thisEvent.updateEvent();
            }
            // If event doesn't clash all of its fields are updated
            else{
                // Updates all fields and updates the database
                thisEvent.setName(name);
                thisEvent.setDescription(Event.alterEventDescription
                        (Session.getScheduleWeekSelected().getWeekNumber(), description));
                thisEvent.setDayId(day.getId());
                thisEvent.setModuleId(module.getId());
                thisEvent.updateEvent();
                // Updates the session for calendar change
                Session.setScheduleCalendarChanged(true);
                // Closes the popup window
                ((Stage) actionButton.getScene().getWindow()).close();
            }
        }
    }

    /**
     * Normalises all text field and combo box borders in case they
     * were highlighted as wrong previously.
     * Also resets the message of errorMessageField to none.
     */
    private void normaliseAllFields(){
        ControlScene.normaliseWrongField(dayOfWeekComboBox);
        ControlScene.normaliseWrongField(associatedModuleComboBox);
        ControlScene.normaliseWrongField(nameField);
        ControlScene.normaliseWrongField(startTimeComboBox);
        ControlScene.normaliseWrongField(endTimeComboBox);
        ControlScene.normaliseWrongField(descriptionField);
        errorMessageField.setText("");
    }

    /**
     * Method which handles actions when delete button is clicked.
     */
    @FXML
    private void deleteButtonClicked(){
        // Deletes the event
        Session.getScheduleEventSelected().deleteEvent();
        // Removes event from session
        Session.setScheduleEventSelected(null);
        // Changes session variable to reflect the change
        Session.setScheduleCalendarChanged(true);
        // Closes the popup window
        Session.setSchedulePopupType(null);
        ((Stage) actionButton.getScene().getWindow()).close();
    }

    // Methods handling styling of scene
    /**
     * Method which changes action button effect when hovered.
     */
    @FXML
    private void actionButtonHovered(){
        PopupType sceneType = Session.getSchedulePopupType();
        ControlScene.buttonHovered(actionButton, actionButtonImage, actionButtonLabel,
                sceneType.toString().toLowerCase()+"_icon_selected.png");
    }

    /**
     * Method which reverts action button effect back to normal
     * when exited/hover ended.
     */
    @FXML
    private void actionButtonExited(){
        PopupType sceneType = Session.getSchedulePopupType();
        ControlScene.buttonExited(actionButton, actionButtonImage, actionButtonLabel,
                sceneType.toString().toLowerCase()+"_icon.png");
    }

    /**
     * Method which changes delete button effect when hovered.
     */
    @FXML
    private void deleteButtonHovered(){
        ControlScene.buttonHovered(deleteButton, deleteButtonImage, deleteButtonLabel, "delete_icon_selected.png");
    }

    /**
     * Method which reverts delete button effect back to normal
     * when exited/hover ended.
     */
    @FXML
    private void deleteButtonExited(){
        ControlScene.buttonExited(deleteButton, deleteButtonImage, deleteButtonLabel, "delete_icon.png");
    }
}
