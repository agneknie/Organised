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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

        // Sets up Day of Week combo box
        // Populates the combo box with Day of Week values
        dayOfWeekComboBox.getItems().setAll(Session.getScheduleWeekSelected().getAllDays());
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
        try {
            FileInputStream newImage = new FileInputStream("src/images/" + sceneType.toString().toLowerCase() + "_icon.png");
            actionButtonImage.setImage(new Image(newImage));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

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
        //TODO actionButtonClicked
    }

    /**
     * Method which handles actions when delete button is clicked.
     */
    @FXML
    private void deleteButtonClicked(MouseEvent event){
        // Deletes the event
        Session.getScheduleEventSelected().deleteEvent();
        // Removes event from session
        Session.setScheduleEventSelected(null);
        // Changes session variable to reflect the change
        Session.setScheduleCalendarChanged(true);
        // Closes the popup window
        this.closeClicked(event);
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
