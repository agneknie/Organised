package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultButtons;
import core.Session;
import core.Year;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
 * Controller for Period popup in the Time tab.
 * Used when a Period is added.
 */
public class TimePopupPeriodController extends DefaultButtons implements Initializable {
    // Period name field
    @FXML
    private TextField nameField;
    // Associated Year to Period combo box
    @FXML
    private ComboBox<Year> yearComboBox;
    // Number of weeks of period field
    @FXML
    private TextField numberOfWeeksField;
    // Start of period date picker
    @FXML
    private DatePicker startOfPeriodDatePicker;
    // Start week number field
    @FXML
    private TextField startWeekNumberField;

    // Add period button pane
    @FXML
    private Pane actionButton;
    // Add period button image
    @FXML
    private ImageView actionButtonImage;
    // Add period button label
    @FXML
    private Label actionButtonLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Sets up Year combo box with year values
        yearComboBox.getItems().setAll(Session.getSession().getAllYears());
        // Styles Year combo box text
        ControlScene.setupComboBoxStyle(yearComboBox);

        // Sets the value of the date picker to the closest coming Monday
        startOfPeriodDatePicker.setValue(closestMonday());

        // Disables all days which are not Mondays
        startOfPeriodDatePicker.setDayCellFactory(dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.getDayOfWeek() != DayOfWeek.MONDAY);
            }
        });
    }

    /**
     * Method which takes today's date and returns a closest coming Monday.
     *
     * @return closest Monday to the current date
     */
    private LocalDate closestMonday(){
        // Gets current day and day of week
        LocalDate today = LocalDate.now();
        DayOfWeek thisDay = today.getDayOfWeek();

        // Variable which is going to be returned
        LocalDate displayDay = today;

        // Based on day of week, finds the next Monday
        switch(thisDay){
            case TUESDAY:
                displayDay = today.plusDays(6);
                break;
            case WEDNESDAY:
                displayDay = today.plusDays(5);
                break;
            case THURSDAY:
                displayDay = today.plusDays(4);
                break;
            case FRIDAY:
                displayDay = today.plusDays(3);
                break;
            case SATURDAY:
                displayDay = today.plusDays(2);
                break;
            case SUNDAY:
                displayDay = today.plusDays(1);
                break;
        }
        return displayDay;
    }

    /**
     * Normalises all field borders in case they were highlighted
     * as wrong previously.
     */
    private void normaliseAllFields(){
        ControlScene.normaliseWrongField(nameField);
        ControlScene.normaliseWrongField(numberOfWeeksField);
        ControlScene.normaliseWrongField(yearComboBox);
        ControlScene.normaliseWrongField(startWeekNumberField);
    }

    /**
     * Method which tries to add a period when an action button is clicked.
     */
    @FXML
    void actionButtonClicked() {
        // Normalises all fields in case they were highlighted as wrong previously
        normaliseAllFields();
        boolean valid = true;

        // Variables for new Period construction
        String name = nameField.getText();
        int yearNumber = -1;
        int numberOfWeeks = -1;
        LocalDate periodStart = startOfPeriodDatePicker.getValue();
        int startWeekNumber = -1;

        // Checks whether inputs are numbers where needed & not null
        try{
           yearNumber = yearComboBox.getValue().getYearNumber();
        } catch (Exception e){
            ControlScene.highlightWrongField(yearComboBox);
            valid = false;
        }
        try{
            numberOfWeeks = Integer.parseInt(numberOfWeeksField.getText());
        } catch (Exception e){
            ControlScene.highlightWrongField(numberOfWeeksField);
            valid = false;
        }
        try{
            startWeekNumber = Integer.parseInt(startWeekNumberField.getText());
        } catch (Exception e){
            ControlScene.highlightWrongField(startWeekNumberField);
            valid = false;
        }

        // Checks if inputs are valid
        if(numberOfWeeks <= 0 || numberOfWeeks > 53){   // Maximum number of weeks in a year is 53
            ControlScene.highlightWrongField(numberOfWeeksField);
            valid = false;
        }
        if(name.isEmpty()){
            ControlScene.highlightWrongField(nameField);
            valid = false;
        }
        if(startWeekNumber<=0){
            ControlScene.highlightWrongField(startWeekNumberField);
            valid = false;
        }

        // If all inputs are valid, adds the new period
        if(valid){
            // Adds the Period
            Session.getSession().constructPeriod(yearNumber, name, numberOfWeeks, periodStart, startWeekNumber);

            // Closes the popup/stage
            ((Stage) actionButton.getScene().getWindow()).close();
        }
    }

    /**
     * Listener for keyboard events.
     * If enter is pressed, action button is activated.
     * @param event used for identifying the key
     */
    @FXML
    void keyPressed(KeyEvent event) {
        // Enter pressed
        if(event.getCode().toString().equals("ENTER")) actionButtonClicked();
    }

    // Methods concerning the styling of the scene
    /**
     * Method which changes button colour when action button is exited.
     */
    @FXML
    void actionButtonExited() {
        ControlScene.buttonExited(actionButton, actionButtonImage, actionButtonLabel,
                "add_icon.png");
    }

    /**
     * Method which changes button colour when action button is hovered.
     */
    @FXML
    void actionButtonHovered() {
        ControlScene.buttonHovered(actionButton, actionButtonImage, actionButtonLabel,
                "add_icon_selected.png");
    }
}
