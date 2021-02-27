package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultNavigation;
import core.*;
import core.enums.ScheduleTime;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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
 * Controller for Event popup in the Schedule tab.
 * Used when a Recurring Event is added.
 */
public class SchedulePopupRecurringEventController extends DefaultNavigation implements Initializable {
    // Day of Week combo box
    @FXML
    private ComboBox<Day> dayOfWeekComboBox;

    // Associated module combo box
    @FXML
    private ComboBox<Module> associatedModuleComboBox;

    // Name field label
    @FXML
    private TextField nameField;

    // Start & End time combo boxes
    @FXML
    private ComboBox<ScheduleTime> startTimeComboBox;
    @FXML
    private ComboBox<ScheduleTime> endTimeComboBox;

    // Start & End week combo boxes
    @FXML
    private ComboBox<Week> startWeekComboBox;
    @FXML
    private ComboBox<Week> endWeekComboBox;

    // Description field text area
    @FXML
    private TextArea descriptionField;

    // Error message field label
    @FXML
    private Label errorMessageField;

    // Action/Add button
    @FXML
    private Pane actionButton;
    @FXML
    private ImageView actionButtonImage;
    @FXML
    private Label actionButtonLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Sets up Day of Week combo box
        // Populates the combo box with Day of Week values
        dayOfWeekComboBox.getItems().setAll(Week.daysForComboBox());
        // Styles Day of Week combo box text
        dayOfWeekComboBox.setButtonCell(new ListCell(){
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                setFont(new Font("Arial", 16.0));
                // If nothing selected, styles like the prompt
                if(empty || item==null)
                    setStyle("-fx-text-fill: derive(-fx-control-inner-background,-30%)");
                    // If something selected, styles accordingly
                else {
                    setStyle("-fx-text-fill: white");
                    setText(item.toString());
                }
            }
        });

        // Sets up the associated module combo box
        // Populates the combo box with modules of period
        int yearNumber = Session.getSchedulePeriodSelected().getAssociatedYear();
        int userId = Session.getSession().getId();
        associatedModuleComboBox.getItems().setAll(Year.yearFromUserIdAndNumber(userId, yearNumber).getAllModules());
        // Styles modules combo box text
        associatedModuleComboBox.setButtonCell(new ListCell(){
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                setFont(new Font("Arial", 16.0));
                // If nothing selected, styles like the prompt
                if(empty || item==null)
                    setStyle("-fx-text-fill: derive(-fx-control-inner-background,-30%)");
                    // If something selected, styles accordingly
                else {
                    setStyle("-fx-text-fill: white");
                    setText(item.toString());
                }
            }
        });

        // Sets up start time combo box
        // Populates the combo box with available start times
        startTimeComboBox.getItems().setAll(ScheduleTime.getStartTimes());
        // Styles start times combo box text
        startTimeComboBox.setButtonCell(new ListCell(){
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                setFont(new Font("Arial", 16.0));
                // If nothing selected, styles like the prompt
                if(empty || item==null)
                    setStyle("-fx-text-fill: derive(-fx-control-inner-background,-30%)");
                    // If something selected, styles accordingly
                else {
                    setStyle("-fx-text-fill: white");
                    setText(item.toString());
                }
            }
        });

        // Sets up end time combo box
        // Populates the combo box with available end times
        endTimeComboBox.getItems().setAll(ScheduleTime.getEndTimes());
        // Styles end times combo box text
        endTimeComboBox.setButtonCell(new ListCell(){
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                setFont(new Font("Arial", 16.0));
                // If nothing selected, styles like the prompt
                if(empty || item==null)
                    setStyle("-fx-text-fill: derive(-fx-control-inner-background,-30%)");
                    // If something selected, styles accordingly
                else {
                    setStyle("-fx-text-fill: white");
                    setText(item.toString());
                }
            }
        });

        // Sets up start week combo box
        // Populates the combo box with available weeks
        startWeekComboBox.getItems().setAll(Session.getSchedulePeriodSelected().getAllWeeks());
        // Styles start times combo box text
        startWeekComboBox.setButtonCell(new ListCell(){
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                setFont(new Font("Arial", 16.0));
                // If nothing selected, styles like the prompt
                if(empty || item==null)
                    setStyle("-fx-text-fill: derive(-fx-control-inner-background,-30%)");
                    // If something selected, styles accordingly
                else {
                    setStyle("-fx-text-fill: white");
                    setText(item.toString());
                }
            }
        });

        // Sets up end week combo box
        // Populates the combo box with available end times
        endWeekComboBox.getItems().setAll(Session.getSchedulePeriodSelected().getAllWeeks());
        // Styles end times combo box text
        endWeekComboBox.setButtonCell(new ListCell(){
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                setFont(new Font("Arial", 16.0));
                // If nothing selected, styles like the prompt
                if(empty || item==null)
                    setStyle("-fx-text-fill: derive(-fx-control-inner-background,-30%)");
                    // If something selected, styles accordingly
                else {
                    setStyle("-fx-text-fill: white");
                    setText(item.toString());
                }
            }
        });
    }

    /**
     * Method which tries to add a series of events (a recurring event)
     * to the selected period when clicked.
     */
    @FXML
    private void actionButtonClicked() {
        // Normalises all fields in case they were marked as wrong before
        normaliseAllFields();

        // Variables for new recurring event construction
        boolean valid = true;
        Day day = dayOfWeekComboBox.getValue();
        Module module = associatedModuleComboBox.getValue();
        String name = nameField.getText();
        ScheduleTime startTime = startTimeComboBox.getValue();
        ScheduleTime endTime = endTimeComboBox.getValue();
        Week startWeek = startWeekComboBox.getValue();
        Week endWeek = endWeekComboBox.getValue();
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
        if(startWeek==null){
            valid = false;
            ControlScene.highlightWrongField(startWeekComboBox);
        }
        if(endWeek==null){
            valid = false;
            ControlScene.highlightWrongField(endWeekComboBox);
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
        if(valid && startWeek.getWeekNumber()>= endWeek.getWeekNumber()){
            valid = false;
            ControlScene.highlightWrongField(startWeekComboBox);
            ControlScene.highlightWrongField(endWeekComboBox);
        }

        // If all fields are valid tries to create recurring event
        if(valid){
            // Creates a list of weeks during which the recurring event takes place
            List<Week> weeksOfEvent = Session.getSchedulePeriodSelected().getAllWeeks();
            int startIndex = weeksOfEvent.indexOf(startWeek);
            int endIndex = weeksOfEvent.indexOf(endWeek);
            weeksOfEvent = weeksOfEvent.subList(startIndex, endIndex+1);

            // Creates a dummy event to check whether time doesn't clash with other events
            Event dummyEvent = new Event(0, 0, 0, "N/A", "N/A", startTime, endTime);
            // Goes through all weeks of event to check for time clashes
            boolean timeConflicts = false;
            for(Week week : weeksOfEvent){
                dummyEvent.setDayId(week.getDay(day.getDate().getDayOfWeek()).getId());
                timeConflicts = dummyEvent.isTimeConflicting();
            }

            // If time conflicts, displays error message
            if(timeConflicts){
                ControlScene.highlightWrongField(startTimeComboBox);
                ControlScene.highlightWrongField(endTimeComboBox);
                errorMessageField.setText("Event's time clashes with another event's time.");
            }
            // If time does not conflict, creates recurring event
            else{
                Event.addRecurringEvent(Session.getSession().getId(), day.getDate().getDayOfWeek(), weeksOfEvent,
                        module.getId(), name, description, startTime, endTime);
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
        ControlScene.normaliseWrongField(startWeekComboBox);
        ControlScene.normaliseWrongField(endWeekComboBox);
        ControlScene.normaliseWrongField(descriptionField);
        errorMessageField.setText("");
    }

    // Methods concerning styling of elements
    /**
     * Changes the styling of action button (add button) when
     * it is hovered by user.
     */
    @FXML
    private void actionButtonExited() {
        ControlScene.buttonExited(actionButton, actionButtonImage, actionButtonLabel, "add_icon.png");
    }

    /**
     * Method which reverts the styling of action button (add button)
     * back to normal when button is exited/hover ended.
     */
    @FXML
    private void actionButtonHovered() {
        ControlScene.buttonHovered(actionButton, actionButtonImage, actionButtonLabel, "add_icon_selected.png");
    }
}
