package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultNavigation;
import core.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
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
 * Controller for Recurring Task popup in the Tasks tab.
 * Used when a Recurring Task is added, edited or deleted.
 */
public class TasksPopupRecurringTaskController extends DefaultNavigation implements Initializable {
    // Popup label
    @FXML
    private Label titleLabel;

    // Fields of the popup
    @FXML
    private ComboBox<Module> associatedModuleComboBox;
    @FXML
    private ComboBox<Week> startWeekComboBox;
    @FXML
    private ComboBox<Week> endWeekComboBox;
    @FXML
    private TextArea descriptionField;

    // Action button/Add button
    @FXML
    private Pane actionButton;
    @FXML
    private ImageView actionButtonImage;
    @FXML
    private Label actionButtonLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Populates the combo box with modules of period
        int yearNumber = Session.getTasksPeriodSelected().getAssociatedYear();
        int userId = Session.getSession().getId();
        associatedModuleComboBox.getItems().setAll(Year.yearFromUserIdAndNumber(userId, yearNumber).getAllModules());
        // Styles modules combo box text
        ControlScene.setupComboBoxStyle(associatedModuleComboBox);

        // Populates the combo box with available weeks
        startWeekComboBox.getItems().setAll(Session.getTasksPeriodSelected().getAllWeeks());
        // Styles start times combo box text
        ControlScene.setupComboBoxStyle(startWeekComboBox);

        // Populates the combo box with available end times
        endWeekComboBox.getItems().setAll(Session.getTasksPeriodSelected().getAllWeeks());
        // Styles end times combo box text
        ControlScene.setupComboBoxStyle(endWeekComboBox);
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

    // Methods handling button clicks
    /**
     * Method which handles actions when action button/add button is clicked.
     */
    @FXML
    private void actionButtonClicked(){
        // Normalises all fields in case they were marked as wrong before
        normaliseAllFields();

        // Variables for new event construction
        boolean valid = true;
        Module module = associatedModuleComboBox.getValue();
        Week startWeek = startWeekComboBox.getValue();
        Week endWeek = endWeekComboBox.getValue();
        String description = descriptionField.getText();

        // Checks whether inputs are not null/not empty
        if(module==null){
            valid = false;
            ControlScene.highlightWrongField(associatedModuleComboBox);
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

        // Checks if week values are valid
        if(valid && startWeek.getWeekNumber()>= endWeek.getWeekNumber()){
            valid = false;
            ControlScene.highlightWrongField(startWeekComboBox);
            ControlScene.highlightWrongField(endWeekComboBox);
        }

        // If all fields valid creates the task
        if(valid){
            // Creates a list of weeks during which the recurring task takes place
            List<Week> weeksOfTask = Session.getTasksPeriodSelected().getAllWeeks();
            int startIndex = weeksOfTask.indexOf(startWeek);
            int endIndex = weeksOfTask.indexOf(endWeek);
            weeksOfTask = weeksOfTask.subList(startIndex, endIndex+1);

            // Adds the recurring task
            Task.addRecurringTask(Session.getSession().getId(), weeksOfTask, module.getId(), description);

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
        ControlScene.normaliseWrongField(startWeekComboBox);
        ControlScene.normaliseWrongField(endWeekComboBox);
        ControlScene.normaliseWrongField(descriptionField);
    }

    // Methods handling styling of scene elements
    /**
     * Method which changes action button effect when hovered.
     */
    @FXML
    private void actionButtonHovered(){
        ControlScene.buttonHovered(actionButton, actionButtonImage, actionButtonLabel, "add_icon_selected.png");
    }

    /**
     * Method which reverts action button effect back to normal
     * when exited/hover ended.
     */
    @FXML
    private void actionButtonExited(){
        ControlScene.buttonExited(actionButton, actionButtonImage, actionButtonLabel, "add_icon.png");
    }
}
