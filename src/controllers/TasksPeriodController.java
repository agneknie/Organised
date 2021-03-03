package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultNavigation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import stages.PopupStage;

import java.io.IOException;
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
 * Class which handles Tasks Tab functionality and UI when a Period is
 * selected.
 */
public class TasksPeriodController extends DefaultNavigation implements Initializable {

    // Top pane fields
    @FXML
    private Label periodNameLabel;
    @FXML
    private Label weekNameLabel;
    @FXML
    private Label weekDateLabel;

    // Navigation between weeks
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

    // Week task completion pane
    @FXML
    private Label tasksCompletedLabel;
    @FXML
    private ProgressBar progressBar;

    // Add task button
    @FXML
    private Pane addTaskButton;
    @FXML
    private Label addTaskButtonLabel;
    @FXML
    private ImageView addTaskButtonImage;

    // Edit task button
    @FXML
    private Pane editTaskButton;
    @FXML
    private Label editTaskButtonLabel;
    @FXML
    private ImageView editTaskButtonImage;

    // More tasks button
    @FXML
    private Pane moreTasksButton;
    @FXML
    private Label moreTasksButtonLabel;
    @FXML
    private ImageView moreTasksButtonImage;

    // Main tasks pane holding all tasks panes
    @FXML
    private Pane allTasksPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO Initialize
    }

    // Methods handling event clicks

    /**
     * Method which opens the task addition popup when
     * add task button is clicked.
     */
    @FXML
    private void addTaskButtonClicked() {
        //TODO addEventButtonClicked
    }

    /**
     * Method which opens the task editing popup when
     * edit task button is clicked.
     */
    @FXML
    private void editTaskButtonClicked() {
        //TODO editEventButtonClicked
    }

    /**
     * Method which brings out the popup, which contains the
     * information about the modules of the period.
     */
    @FXML
    private void moduleInfoButtonClicked() throws IOException {
        //TODO moduleInfoButtonClicked
    }

    /**
     * Method which updates the tasks table with tasks,
     * which are not currently visible.
     */
    @FXML
    private void moreTasksButtonClicked(){
        //TODO moreTasksButtonClicked
    }

    // Methods handling navigation
    /**
     * Forwards the user to the general Tasks scene
     */
    @FXML
    private void goBackClicked() {
        //TODO goBackClicked
    }

    /**
     * Changes the currently displayed week to the week before
     * currently selected week.
     */
    @FXML
    private void goLeftClicked() {
        //TODO goLeftClicked
    }

    /**
     * Changes the currently displayed week to the week after
     * currently selected week.
     */
    @FXML
    private void goRightClicked() {
        //TODO goRightClicked
    }

    // Methods handling scene styling
    /**
     * Changes the styling of addTask button if hover
     * ended/mouse exited.
     */
    @FXML
    private void addTaskButtonExited() {
        ControlScene.buttonExited(addTaskButton, addTaskButtonImage,
                addTaskButtonLabel, "add_icon.png");
    }

    /**
     * Changes the styling of addTask button if hovered.
     */
    @FXML
    private void addTaskButtonHovered() {
        ControlScene.buttonHovered(addTaskButton, addTaskButtonImage,
                addTaskButtonLabel, "add_icon_selected.png");
    }

    /**
     * Changes the styling of editTask button if hover
     * ended/mouse exited.
     */
    @FXML
    private void editTaskButtonExited() {
        ControlScene.buttonExited(editTaskButton, editTaskButtonImage,
                editTaskButtonLabel, "edit_icon.png");
    }

    /**
     * Changes the styling of editTask button if hovered.
     */
    @FXML
    private void editTaskButtonHovered() {
        ControlScene.buttonHovered(editTaskButton, editTaskButtonImage,
                editTaskButtonLabel, "edit_icon_selected.png");
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

    /**
     * Changes the styling of moreTasks button if hovered.
     */
    @FXML
    private void moreTasksButtonHovered(){
        ControlScene.buttonHovered(moreTasksButton, moreTasksButtonImage,
                moreTasksButtonLabel, "more_icon_selected.png");
    }

    /**
     * Changes the styling of moreTasks button if hover ended/
     * mouse exited.
     */
    @FXML
    private void moreTasksButtonExited(){
        ControlScene.buttonExited(moreTasksButton, moreTasksButtonImage,
                moreTasksButtonLabel, "more_icon.png");
    }
}
