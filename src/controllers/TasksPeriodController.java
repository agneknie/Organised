package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultNavigation;
import controllers.utilities.SetupScene;
import core.Period;
import core.Session;
import core.Week;
import core.enums.PopupType;
import core.enums.TaskStatus;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import stages.BiggerPopupStage;
import stages.PopupStage;

import java.io.IOException;
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

    // User specific variables
    private Week userSelectedWeek;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Finds out which week should be selected by the user
        userSelectedWeek = Week.getCurrentWeek(Session.getTasksPeriodSelected().getAllWeeks());

        // Saves selected week in session
        Session.setTasksWeekSelected(userSelectedWeek);

        // Sets up navigation arrow visibility
        configureNavigationArrows();

        // Sets up period and week information at the top
        setupTopInformation();

        // Sets up the progress bar and label
        setupProgressBar();

        // Hides more tasks button
        moreTasksButton.setVisible(false);

        // Setups task list
        setupTaskList();
    }

    /**
     * Method which refreshes the task list area of the screen
     * in case user added/edited a task.
     */
    @FXML
    private void refreshScene(){
        // If task list changed
        if(Session.isTasksTaskListChanged()){
            // Resets session variable
            Session.setTasksTaskListChanged(false);
            //TODO refreshScene
        }
    }

    /**
     * Method which setups period and week information at the
     * top of the scene.
     */
    private void setupTopInformation(){
        Period currentPeriod = Session.getTasksPeriodSelected();

        // Sets up period name label
        periodNameLabel.setText("Year " + currentPeriod.getAssociatedYear() +
                ": " + currentPeriod.getName());

        // Sets up week information labels
        weekNameLabel.setText(userSelectedWeek.toString());
        weekDateLabel.setText(userSelectedWeek.getWeekDate());

        // Sets up the progress bar and the progress label
        double completedTasks = userSelectedWeek.getTasksByStatus(TaskStatus.YES);
        double allTasks = userSelectedWeek.getTasksByStatus(null) - userSelectedWeek.getTasksByStatus(TaskStatus.DROPPED);
        progressBar.setProgress(completedTasks/allTasks);
        tasksCompletedLabel.setText(Math.round(completedTasks/allTasks*100) + "%");
    }

    /**
     * Method which setups the progress bar and label, which
     * indicates the percentage of tasks completed for week.
     */
    private void setupProgressBar(){
        //TODO setupProgressBar
    }

    /**
     * Method which sets up the task list.
     */
    private void setupTaskList(){
        //TODO setupTaskList
    }

    /**
     * Method which cleans the task list of task data thus preparing for
     * a fresh task update.
     */
    private void cleanTaskList(){
        //TODO cleanTaskList
    }

    // Methods handling event clicks
    /**
     * Method which opens the task addition popup when
     * add task button is clicked.
     */
    @FXML
    private void addTaskButtonClicked() throws IOException {
        // Sets the popup type
        Session.setTasksPopupType(PopupType.ADD);

        // Opens the popup
        Stage popup = new Stage();
        new PopupStage(popup, "TasksPopupViewTask.fxml");
    }

    /**
     * Method which brings out the popup, which contains the
     * information about the modules of the period.
     */
    @FXML
    private void moduleInfoButtonClicked() throws IOException {
        // Sets the session variable
        Session.setModuleInformationOrigin("Tasks");

        // Opens the popup
        Stage popup = new Stage();
        new PopupStage(popup, "ModuleInformationPopupView.fxml");
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
        // Changes the scene to the the general Tasks scene
        try {
            SetupScene.changeScene("TasksView.fxml", goBackButton);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene from Period specific Tasks to general Tasks view.");
        }
    }

    /**
     * Changes the currently displayed week to the week before
     * currently selected week.
     */
    @FXML
    private void goLeftClicked() {
        // Finds the index of the selected Week in the list of all Weeks of Period
        List<Week> userWeeks = Session.getTasksPeriodSelected().getAllWeeks();
        int indexOfSelectedWeek = userWeeks.indexOf(userSelectedWeek);

        // Updates the displayed week
        userSelectedWeek = userWeeks.get(indexOfSelectedWeek-1);

        // Saves selected week in session
        Session.setTasksWeekSelected(userSelectedWeek);

        // Updates the scene
        updateAfterNavigation();
    }

    /**
     * Changes the currently displayed week to the week after
     * currently selected week.
     */
    @FXML
    private void goRightClicked() {
        // Finds the index of the selected Week in the list of all Weeks of Period
        List<Week> userWeeks = Session.getTasksPeriodSelected().getAllWeeks();
        int indexOfSelectedWeek = userWeeks.indexOf(userSelectedWeek);

        // Updates the displayed week
        userSelectedWeek = userWeeks.get(indexOfSelectedWeek+1);

        // Saves selected week in session
        Session.setTasksWeekSelected(userSelectedWeek);

        // Updates the scene
        updateAfterNavigation();
    }

    /**
     * Method which updates the scene after user navigates from one week to other.
     */
    private void updateAfterNavigation(){
        // Updates top pane information
        setupTopInformation();

        // Configures navigation arrows
        configureNavigationArrows();

        // Disables visibility of more tasks button
        moreTasksButton.setVisible(false);

        // Setups the task list
        cleanTaskList();
        setupTaskList();
    }

    /**
     * Method which configures the visibility of navigation arrows,
     * which are used for navigation between weeks.
     */
    private void configureNavigationArrows(){
        // Finds the index of the selected Week in the list of all Weeks of Period
        List<Week> userWeeks = Session.getTasksPeriodSelected().getAllWeeks();
        int indexOfSelectedWeek = userWeeks.indexOf(userSelectedWeek);

        // Makes both arrows visible, in case previously disabled
        goLeftButton.setVisible(true);
        goRightButton.setVisible(true);

        // Disables the arrows, based on current week selection
        if(indexOfSelectedWeek == 0) goLeftButton.setVisible(false);
        if(indexOfSelectedWeek == userWeeks.size()-1) goRightButton.setVisible(false);
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
