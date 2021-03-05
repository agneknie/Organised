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
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

        // Sets up the associated module combo box
        // Populates the combo box with modules of period
        int yearNumber = Session.getTasksPeriodSelected().getAssociatedYear();
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
        if(Session.getSchedulePopupType() == PopupType.ADD) addButtonClicked();

            // If action button is 'Edit'
        else editButtonClicked();
    }

    /**
     * Method which handles actions when delete button is clicked.
     */
    @FXML
    private void deleteButtonClicked(){
        //TODO deleteButtonClicked
    }

    /**
     * Method which tries to add a task if add button is clicked.
     */
    private void addButtonClicked(){
        //TODO addButtonClicked
    }

    /**
     * Method which tries to edit a task if edit button is clicked.
     */
    private void editButtonClicked(){
        //TODO editButtonClicked
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
