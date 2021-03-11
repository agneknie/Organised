package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultNavigation;
import core.Module;
import core.Session;
import core.Task;
import core.Year;
import core.enums.PopupType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
 * Controller for Task popup in the Tasks tab.
 * Used when a Task is added, edited or deleted.
 */
public class TasksPopupTaskController extends DefaultNavigation implements Initializable {
    // Popup label
    @FXML
    private Label titleLabel;

    // Fields of the popup
    @FXML
    private ComboBox<Module> associatedModuleComboBox;
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
        // Sets up the popup based on type
        setupPopupBasedOnType();

        // Populates the combo box with modules of period
        int yearNumber = Session.getTasksPeriodSelected().getAssociatedYear();
        int userId = Session.getSession().getId();
        associatedModuleComboBox.getItems().setAll(Year.yearFromUserIdAndNumber(userId, yearNumber).getAllModules());
        // Styles modules combo box text
        ControlScene.setupComboBoxStyle(associatedModuleComboBox);
    }

    /**
     * Method which setups the popup fields/buttons, which are influenced
     * by type (either add or edit)
     */
    public void setupPopupBasedOnType(){
        PopupType sceneType = Session.getTasksPopupType();
        // Sets up the top title
        titleLabel.setText(sceneType + " Task.");

        // Sets up the action button
        actionButtonLabel.setText(sceneType.toString());

        Image newImage = new Image(TasksPopupTaskController.class.getResourceAsStream
                ("/"+sceneType.toString().toLowerCase() + "_icon.png"));
        actionButtonImage.setImage(newImage);

        // Hides delete button if popup is Add
        if(sceneType == PopupType.ADD) deleteButton.setVisible(false);
            // Fills popup fields with selected event data if popup is Edit
        else{
            Task selectedTask = Session.getTasksTaskSelected();
            associatedModuleComboBox.setValue(selectedTask.getModule());
            descriptionField.setText(selectedTask.getDescription());
        }
    }

    // Methods handling button clicks
    /**
     * Method which handles actions when action button (add or edit) is clicked.
     */
    @FXML
    private void actionButtonClicked(){
        // If action button is 'Add'
        if(Session.getTasksPopupType() == PopupType.ADD) addButtonClicked();

        // If action button is 'Edit'
        else editButtonClicked();
    }

    /**
     * Method which handles actions when delete button is clicked.
     */
    @FXML
    private void deleteButtonClicked(){
        // Deletes the task
        Session.getTasksTaskSelected().deleteTask();
        // Removes task from session
        Session.setTasksTaskSelected(null);
        // Changes session variable to reflect the change
        Session.setTasksTaskListChanged(true);
        // Closes the popup window
        Session.setSchedulePopupType(null);
        ((Stage) actionButton.getScene().getWindow()).close();
    }

    /**
     * Method which tries to add a task if add button is clicked.
     */
    private void addButtonClicked(){
        // Normalises all fields in case they were marked as wrong before
        normaliseAllFields();

        // Variables for new task construction
        boolean valid = true;
        Module module = associatedModuleComboBox.getValue();
        String description = descriptionField.getText();

        // Checks whether inputs are not null/not empty
        if(module==null){
            valid = false;
            ControlScene.highlightWrongField(associatedModuleComboBox);
        }
        if(description.isEmpty()){
            valid = false;
            ControlScene.highlightWrongField(descriptionField);
        }

        // If all fields valid creates the task
        if(valid){
            // Creates the task
            Task newTask = new Task(Session.getSession().getId(), module.getId(), Session.getTasksWeekSelected().getId(),
                    Task.alterTaskDescription(Session.getTasksWeekSelected().getWeekNumber(), description));
            // Adds task to database
            newTask.addTask();

            // Updates session for task list change
            Session.setTasksTaskListChanged(true);
            // Closes the popup
            ((Stage) actionButton.getScene().getWindow()).close();
        }
    }

    /**
     * Method which tries to edit a task if edit button is clicked.
     */
    private void editButtonClicked(){
        // Normalises all fields in case they were marked as wrong before
        normaliseAllFields();

        // Variables for task updating
        boolean valid = true;
        Module module = associatedModuleComboBox.getValue();
        String description = descriptionField.getText();

        // Checks whether inputs are not null/not empty
        if(module==null){
            valid = false;
            ControlScene.highlightWrongField(associatedModuleComboBox);
        }
        if(description.isEmpty()){
            valid = false;
            ControlScene.highlightWrongField(descriptionField);
        }

        // If all fields are valid, updates the task
        if(valid){
            // Updates task fields
            Task currentTask = Session.getTasksTaskSelected();
            currentTask.setDescription
                    (Task.alterTaskDescription(Session.getTasksWeekSelected().getWeekNumber(), description));
            currentTask.setModuleId(module.getId());

            // Updates task in database
            currentTask.updateTask();

            // Updates session for task list change
            Session.setTasksTaskListChanged(true);
            // Closes the popup
            ((Stage) actionButton.getScene().getWindow()).close();
        }
    }

    /**
     * Normalises all text field and combo box borders in case they
     * were highlighted as wrong previously.
     */
    private void normaliseAllFields(){
        ControlScene.normaliseWrongField(associatedModuleComboBox);
        ControlScene.normaliseWrongField(descriptionField);
    }

    // Methods handling styling of scene elements
    /**
     * Method which changes action button effect when hovered.
     */
    @FXML
    private void actionButtonHovered(){
        PopupType sceneType = Session.getTasksPopupType();
        ControlScene.buttonHovered(actionButton, actionButtonImage, actionButtonLabel,
                sceneType.toString().toLowerCase()+"_icon_selected.png");
    }

    /**
     * Method which reverts action button effect back to normal
     * when exited/hover ended.
     */
    @FXML
    private void actionButtonExited(){
        PopupType sceneType = Session.getTasksPopupType();
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
