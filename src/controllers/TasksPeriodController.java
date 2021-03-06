package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultNavigation;
import controllers.utilities.SetupScene;
import core.*;
import core.enums.PopupType;
import core.enums.TaskStatus;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import stages.PopupStage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    private Label tasksCompletedInfoLabel;
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
    private Label moduleLabel;
    @FXML
    private Label taskLabel;
    @FXML
    private Label completeLabel;
    @FXML
    private Pane allTasksPane;

    // User specific variables
    private Week userSelectedWeek;
    private Task[] taskList = new Task[]{null, null, null, null, null, null, null, null, null, null};
    private List<Task> tasksOfWeek;
    private int currentlyDisplayedBatch;

    // Number of panes on the window
    final int MAX_PANES = 10;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Finds out which week should be selected by the user
        userSelectedWeek = Week.getCurrentWeek(Session.getTasksPeriodSelected().getAllWeeks());
        // Saves selected week in session
        Session.setTasksWeekSelected(userSelectedWeek);
        // Saves tasks of the week in a list
        tasksOfWeek = userSelectedWeek.getAllTasks();

        // Sets up navigation arrow visibility
        configureNavigationArrows();

        // Sets up period and week information at the top
        setupTopInformation();

        // Sets up the progress bar and label
        setupProgressBar();

        // Determines visibility of moreTasksButton
        moreTasksButton.setVisible(tasksOfWeek.size() > MAX_PANES);

        // If there are no tasks to display, hides the task list elements
        if(tasksOfWeek.isEmpty()) visibilityOfTaskListElements(false);
        // Setups task list
        else{
            currentlyDisplayedBatch = 0;
            setupTaskList();
        }
    }

    /**
     * Method which handles visibility the task list labels.
     */
    private void visibilityOfTaskListElements(boolean visible){
        moduleLabel.setVisible(visible);
        taskLabel.setVisible(visible);
        completeLabel.setVisible(visible);
        // If no tasks are present, displays information message
        if(!visible){
            tasksCompletedInfoLabel.setText("Add some Tasks to get Organised.");
            tasksCompletedLabel.setText("");
            progressBar.setVisible(false);
        }
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
            updateAfterNavigation();
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
    }

    /**
     * Method which setups the progress bar and label, which
     * indicates the percentage of tasks completed for week.
     */
    private void setupProgressBar(){
        // Gets the relevant tasks for calculations
        double completedTasks = userSelectedWeek.getTasksByStatus(TaskStatus.YES) + userSelectedWeek.getTasksByStatus(TaskStatus.DROPPED);
        double allTasks = userSelectedWeek.getTasksByStatus(null);

        // Sets up the progress bar and the progress label
        progressBar.setVisible(true);
        progressBar.setProgress(completedTasks/allTasks);
        tasksCompletedInfoLabel.setText("Week Task Completion:");
        tasksCompletedLabel.setText(Math.round(completedTasks/allTasks*100) + "%");
    }

    /**
     * Method which sets up the task list.
     */
    private void setupTaskList(){
        // Makes all panes visible in case they weren't before
        for(Node node : allTasksPane.getChildren()){
            node.setVisible(true);
        }

        // Takes tasks, which will be displayed
        List<Task> displayedTasks = new ArrayList<>();
        for(int i = currentlyDisplayedBatch*MAX_PANES; i<tasksOfWeek.size(); i++){
            displayedTasks.add(tasksOfWeek.get(i));
        }

        // Counts panes which will be visible/have info in them
        int panesToSetup = MAX_PANES;
        if(MAX_PANES-displayedTasks.size()>0) panesToSetup = MAX_PANES-(MAX_PANES-displayedTasks.size());

        // Setups the panes with tasks
        for(int i = 0; i<panesToSetup; i++){
            setupTask(displayedTasks.get(i), (Pane) allTasksPane.getChildren().get(i));
            // Saves the task in the currently displayed task array for retrieval
            taskList[i] = displayedTasks.get(i);
        }

        // Determines pane visibility if not all are filled
        if(panesToSetup<MAX_PANES){
            for(int panesLeft = MAX_PANES-panesToSetup; panesLeft>0; panesLeft--){
                allTasksPane.getChildren().get(MAX_PANES-panesLeft).setVisible(false);
            }
        }
    }

    /**
     * Method which setups the given task pane with given task data.
     */
    private void setupTask(Task task, Pane pane){
        // Constant for colour to change text colour
        final double COLOR_THRESHOLD = 500.0;

        // Gets all pane elements separately
        Label code = (Label) pane.getChildren().get(0);
        Label description = (Label) pane.getChildren().get(1);
        Label status = (Label) pane.getChildren().get(2);

        // Sets the module and task labels with task data
        code.setText(task.getModule().getCode());
        description.setText(task.getDescription());

        // Sets the colour of the pane
        pane.setStyle("-fx-background-color: "+task.getTaskColourString()+"; " +
                "-fx-background-radius: 10;");

        // Configures text colour if background is too fair for module and task data
        Color moduleColor = task.getTaskColour();
        double colourOverall = moduleColor.getRed()*255 + moduleColor.getBlue()*255 + moduleColor.getGreen()*255;
        if(colourOverall >= COLOR_THRESHOLD){
            code.setTextFill(Paint.valueOf("#2B2B2B"));
            description.setTextFill(Paint.valueOf("#2B2B2B"));
        }
        else{
            code.setTextFill(Paint.valueOf("#FFFFFF"));
            description.setTextFill(Paint.valueOf("#FFFFFF"));
        }

        // Setups status field
        setupStatusField(status, task);
    }

    /**
     * Method which setups the status field of the task.
     *
     * @param statusLabel label field of the task status
     * @param task task which belongs to that field
     */
    private void setupStatusField(Label statusLabel, Task task){
        statusLabel.setText(task.getStatus().toString());
        statusLabel.setStyle("-fx-background-color: "+ task.getStatus().getColor() +"; -fx-background-radius: 10;");
    }

    /**
     * Method which cleans the task list of task data thus preparing for
     * a fresh task update.
     */
    private void cleanTaskList(){
        taskList = new Task[]{null, null, null, null, null, null, null, null, null, null};
    }

    // Methods handling button clicks
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
        // Counts the number of batches
        int batches = tasksOfWeek.size()/MAX_PANES-1;   // -1 because Batch numbering starts from 0
        if(tasksOfWeek.size()%MAX_PANES!=0) batches++;

        // Updates the batch
        if(currentlyDisplayedBatch==batches) currentlyDisplayedBatch = 0;
        else currentlyDisplayedBatch++;

        // Updates the task list
        cleanTaskList();
        setupTaskList();
    }

    /**
     * Method which opens the edit task popup for a task when a task
     * is clicked.
     */
    @FXML
    private void allTasksPaneClicked(MouseEvent event) throws IOException {
        // Gets click coordinates
        double xCoordinate = event.getX();
        double yCoordinate = event.getY();

        // Variables to determine mouse click purpose
        Task clickedTask = null;
        Pane taskPaneClicked = null;

        // Constants with allTasksPane measurements
        final int TASK_HEIGHT = 50;
        final int GAP_HEIGHT = 6;
        final int PANEL_HEIGHT = 554;
        final int TASK_WIDTH = 1089;
        final int STATUS_WIDTH = 159;       // -7 line width included

        // Figures out which task was selected
        int taskNumber = 0;
        for(int i=0; i<PANEL_HEIGHT; i+=(TASK_HEIGHT+GAP_HEIGHT)){
            if(yCoordinate>=i && yCoordinate<=i+TASK_HEIGHT){
                clickedTask = taskList[taskNumber];
                taskPaneClicked = (Pane) allTasksPane.getChildren().get(taskNumber);
            }
            taskNumber++;
        }

        // If a task was clicked, figures out whether to open edit popup or change the status
        if(clickedTask!=null){
            // If status was clicked, changes the status
            if(xCoordinate>=TASK_WIDTH-STATUS_WIDTH && xCoordinate<=TASK_WIDTH)
                statusClicked((Label) taskPaneClicked.getChildren().get(2), clickedTask);
            // If task was clicked opens edit task popup
            else taskClicked(clickedTask);
        }
    }

    /**
     * Method which changes the status field when clicked and updates the task status in
     * the database.
     *
     * @param status status label corresponding to the task
     * @param task task that has been clicked
     */
    private void statusClicked(Label status, Task task){
        // Gets the following status
        task.setStatus(task.getStatus().nextStatus());

        // Updates the task's status in database
        task.updateTask();

        // Sets up the new styling of status
        setupStatusField(status, task);

        // Updates the progress bar and label
        setupProgressBar();
    }

    /**
     * Method which opens a task edit popup when a task is clicked in the task
     * list.
     *
     * @param task task that was clicked
     */
    private void taskClicked(Task task) throws IOException {
        // Sets the popup type
        Session.setTasksPopupType(PopupType.EDIT);

        // Saves selected task in session
        Session.setTasksTaskSelected(task);

        // Opens the popup
        Stage popup = new Stage();
        new PopupStage(popup, "TasksPopupViewTask.fxml");
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
        setupProgressBar();

        // Configures navigation arrows
        configureNavigationArrows();

        // Updates the task list
        tasksOfWeek = userSelectedWeek.getAllTasks();

        // Determines visibility of moreTasksButton
        moreTasksButton.setVisible(tasksOfWeek.size() > MAX_PANES);

        // Determines visibility of task list labels
        visibilityOfTaskListElements(!tasksOfWeek.isEmpty());

        // Setups the task list
        currentlyDisplayedBatch = 0;
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
