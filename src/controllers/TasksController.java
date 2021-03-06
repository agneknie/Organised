package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultNavigation;
import controllers.utilities.SetupScene;
import core.Day;
import core.Period;
import core.Session;
import core.Week;
import core.enums.TaskStatus;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import stages.PopupStage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
 * Class which handles Tasks Tab functionality and UI.
 */
public class TasksController extends DefaultNavigation implements Initializable {
    // Stacked bar chart
    @FXML
    private Label stackedBarChartLabel;
    @FXML
    private StackedBarChart<String, Integer> stackedBarChart;

    // No periods yet information label
    @FXML
    private Label noPeriodsYetLabel;

    // Go to today button
    @FXML
    private Pane goToTodayButton;
    @FXML
    private ImageView goToTodayButtonImage;
    @FXML
    private Label goToTodayButtonLabel;
    @FXML
    private Label errorMessage;

    // Navigation arrows
    @FXML
    private ImageView goRightButton;
    @FXML
    private ImageView goLeftButton;

    // Navigation Pane 1
    @FXML
    private Pane pane1;
    private int pane1Pointer;
    @FXML
    private Label pane1YearLabel;
    @FXML
    private Label pane1PeriodLabel;
    // Pane 1 go to period button
    @FXML
    private Pane pane1GoToPeriodButton;
    @FXML
    private ImageView pane1GoToPeriodButtonImage;
    @FXML
    private Label pane1GoToPeriodButtonLabel;
    // Pane 1 add recurring task button
    @FXML
    private Pane pane1AddTaskButton;
    @FXML
    private ImageView pane1AddTaskButtonImage;
    @FXML
    private Label pane1AddTaskButtonLabel;

    // Navigation Pane 2
    @FXML
    private Pane pane2;
    private int pane2Pointer;
    @FXML
    private Label pane2YearLabel;
    @FXML
    private Label pane2PeriodLabel;
    // Pane 2 go to period button
    @FXML
    private Pane pane2GoToPeriodButton;
    @FXML
    private ImageView pane2GoToPeriodButtonImage;
    @FXML
    private Label pane2GoToPeriodButtonLabel;
    // Pane 2 add recurring task button
    @FXML
    private Pane pane2AddTaskButton;
    @FXML
    private ImageView pane2AddTaskButtonImage;
    @FXML
    private Label pane2AddTaskButtonLabel;

    // Navigation Pane 3
    @FXML
    private Pane pane3;
    private int pane3Pointer;
    @FXML
    private Label pane3YearLabel;
    @FXML
    private Label pane3PeriodLabel;
    // Pane 3 go to period button
    @FXML
    private Pane pane3GoToPeriodButton;
    @FXML
    private ImageView pane3GoToPeriodButtonImage;
    @FXML
    private Label pane3GoToPeriodButtonLabel;
    // Pane 3 add recurring task button
    @FXML
    private Pane pane3AddTaskButton;
    @FXML
    private ImageView pane3AddTaskButtonImage;
    @FXML
    private Label pane3AddTaskButtonLabel;

    // User specific variables
    private List<Period> userPeriods;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Gets user periods
        userPeriods = Session.getSession().getAllPeriods();

        // If user doesn't have any periods yet
        if(userPeriods.isEmpty()){
            stackedBarChart.setVisible(false);
            stackedBarChartLabel.setVisible(false);
            goToTodayButton.setVisible(false);
        }
        else{
            noPeriodsYetLabel.setVisible(false);
            // Sets up the stacked bar chart
            setupStackedBarChart();
        }

        // Sets up panes with information
        setupNavigationPanes();

        // Sets up navigation
        setupNavigation();
    }

    /**
     * Method which sets up the stacked bar chart of the scene with
     * task completion data of each period.
     */
    @FXML
    private void setupStackedBarChart(){
        // Cleans chart in case it was setup before
        stackedBarChart.getData().clear();
        ((CategoryAxis) stackedBarChart.getXAxis()).getCategories().clear();

        // Period numbering/naming
        int periodNumber = 1;

        // Defines axis categories
        for(Period ignored : userPeriods){
            ((CategoryAxis) stackedBarChart.getXAxis()).getCategories().add("Period "+periodNumber);
            periodNumber++;
        }

        // Creates series
        XYChart.Series<String, Integer> seriesYes = new XYChart.Series<>();
        XYChart.Series<String, Integer> seriesNo = new XYChart.Series<>();
        XYChart.Series<String, Integer> seriesDropped = new XYChart.Series<>();

        // Names series
        seriesYes.setName("Completed");
        seriesNo.setName("Not Completed");
        seriesDropped.setName("Dropped");

        // Adds period data to the series
        periodNumber = 1;
        for(Period period : userPeriods){
            // Adds task data to series
            seriesYes.getData().add(new XYChart.Data<>("Period "+periodNumber, period.getTasksByStatus(TaskStatus.YES)));
            seriesNo.getData().add(new XYChart.Data<>("Period "+periodNumber, period.getTasksByStatus(TaskStatus.NO)));
            seriesDropped.getData().add(new XYChart.Data<>("Period "+periodNumber, period.getTasksByStatus(TaskStatus.DROPPED)));
            periodNumber++;
        }

        // Adds series to the chart
        stackedBarChart.getData().addAll(seriesYes, seriesNo, seriesDropped);

        // Styles the chart
        stackedBarChart.setCategoryGap(150.0);
        // Sets the font & size of the chart text
        stackedBarChart.getYAxis().setTickLabelFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 18));
        stackedBarChart.getXAxis().setTickLabelFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 18));
    }

    // Methods responsible for navigation between panes displaying periods
    /**
     * Method which handles left navigation arrow clicked.
     * Shows previous period
     */
    @FXML
    private void goLeftClicked() {
        // Cleans error message field in case it was displaying something
        errorMessage.setText("");

        // Updates the navigation pane pointers
        pane1Pointer--;
        pane2Pointer--;
        pane3Pointer--;

        // Updates the navigation panes
        setupPane1();
        setupPane2();
        setupPane3();

        // Updates navigation arrow visibility
        setupNavigation();
    }

    /**
     * Method which handles right navigation arrow clicked.
     * Shows next period.
     */
    @FXML
    private void goRightClicked() {
        // Cleans error message field in case it was displaying something
        errorMessage.setText("");

        // Updates the navigation pane pointers
        pane1Pointer++;
        pane2Pointer++;
        pane3Pointer++;

        // Updates the navigation panes
        setupPane1();
        setupPane2();
        setupPane3();

        // Updates navigation arrow visibility
        setupNavigation();
    }

    /**
     * Method which setups the navigation panes with information
     */
    private void setupNavigationPanes(){
        switch(userPeriods.size()){
            // No periods to display
            case 0:
                // Hides all panes
                pane1.setVisible(false);
                pane2.setVisible(false);
                pane3.setVisible(false);
                break;
            // One period available
            case 1:
                // Hides unused panes
                pane2.setVisible(false);
                pane3.setVisible(false);

                // Setups the first pane
                pane1Pointer = 0;
                setupPane1();
                break;
            // Two periods available
            case 2:
                // Hides unused panes
                pane3.setVisible(false);

                // Setups the first two panes
                pane1Pointer = 0;
                pane2Pointer = 1;
                setupPane1();
                setupPane2();
                break;
            // Three or more periods available
            default:
                // Setups all panes
                pane1Pointer = 0;
                pane2Pointer = 1;
                pane3Pointer = 2;
                setupPane1();
                setupPane2();
                setupPane3();
        }
    }

    /**
     * Method which setups the first pane with user information.
     */
    private void setupPane1(){
        // Makes visible if previously invisible
        pane1.setVisible(true);

        // Sets up pane labels
        pane1YearLabel.setText("Year " + userPeriods.get(pane1Pointer).getAssociatedYear());
        pane1PeriodLabel.setText(userPeriods.get(pane1Pointer).getName());
    }

    /**
     * Method which setups the second pane with user information.
     */
    private void setupPane2(){
        // Makes visible if previously invisible
        pane2.setVisible(true);

        // Sets up pane labels
        pane2YearLabel.setText("Year " + userPeriods.get(pane2Pointer).getAssociatedYear());
        pane2PeriodLabel.setText(userPeriods.get(pane2Pointer).getName());
    }

    /**
     * Method which setups the third pane with user information.
     */
    private void setupPane3(){
        // Makes visible if previously invisible
        pane3.setVisible(true);

        // Sets up pane labels
        pane3YearLabel.setText("Year " + userPeriods.get(pane3Pointer).getAssociatedYear());
        pane3PeriodLabel.setText(userPeriods.get(pane3Pointer).getName());
    }

    /**
     * Method which setups the navigation arrow visibility.
     */
    private void setupNavigation(){
        int periodNumber = userPeriods.size();
        // Enables both arrows to remove previous configurations
        goRightButton.setVisible(true);
        goLeftButton.setVisible(true);

        // If there are less than 3 periods, navigation arrows are not needed
        if(periodNumber <= 3){
            goLeftButton.setVisible(false);
            goRightButton.setVisible(false);
        }

        // Otherwise, one or both arrows might be needed
        else {
            if(pane1Pointer == 0) goLeftButton.setVisible(false);
            if(pane3Pointer == periodNumber-1) goRightButton.setVisible(false);
        }
    }

    // Methods responsible for handling button clicks
    /**
     * Method which goes to the schedule of the week, which has today's date.
     * If there are more than one "today(s)" goes to the first one.
     * If no today is present among the periods, displays error message.
     */
    @FXML
    private void goToTodayButtonClicked() {
        // Gets today's date
        LocalDate today = LocalDate.now();

        // Goes through all periods of user
        for(Period period : Session.getSession().getAllPeriods()){
            // Goes through all weeks of all periods
            for(Week week : period.getAllWeeks()){
                // Goes through all days of week
                for(Day day : week.getAllDays()){
                    // If current day is today, forwards to period of that day.
                    // Week configuration is done in TasksPeriod
                    if(day.getDate().equals(today)){
                        Session.setTasksPeriodSelected(period);
                        // Changes the scene to Period specific scene
                        try {
                            SetupScene.changeScene("TasksPeriodView.fxml", goToTodayButton);

                        } catch (IOException e) {
                            System.out.println("Exception whilst changing scene from general Tasks to Period specific Tasks.");
                        }
                    }
                }
            }
        }
        // If no today was found, displays error message
        errorMessage.setText("No today was found.");
    }

    /**
     * Method which opens recurring task addition popup for
     * period, which is described by pane1.
     */
    @FXML
    private void pane1AddTaskButtonClicked() throws IOException {
        // Cleans error message field in case it was displaying something
        errorMessage.setText("");

        // Saves selected Period in Session
        Session.setTasksPeriodSelected(userPeriods.get(pane1Pointer));

        // Opens the popup
        Stage popup = new Stage();
        new PopupStage(popup, "TasksPopupViewRecurringTask.fxml");
    }

    /**
     * Method which opens recurring task addition popup for
     * period, which is described by pane2.
     */
    @FXML
    private void pane2AddTaskButtonClicked() throws IOException {
        // Cleans error message field in case it was displaying something
        errorMessage.setText("");

        // Saves selected Period in Session
        Session.setTasksPeriodSelected(userPeriods.get(pane2Pointer));

        // Opens the popup
        Stage popup = new Stage();
        new PopupStage(popup, "TasksPopupViewRecurringTask.fxml");
    }

    /**
     * Method which opens recurring task addition popup for
     * period, which is described by pane3.
     */
    @FXML
    private void pane3AddTaskButtonClicked() throws IOException {
        // Cleans error message field in case it was displaying something
        errorMessage.setText("");

        // Saves selected Period in Session
        Session.setTasksPeriodSelected(userPeriods.get(pane3Pointer));

        // Opens the popup
        Stage popup = new Stage();
        new PopupStage(popup, "TasksPopupViewRecurringTask.fxml");
    }

    /**
     * Method which changes the scene to the period, which is
     * specified by pane1.
     */
    @FXML
    private void pane1GoToPeriodButtonClicked() {
        // Saves selected Period in Session
        Session.setTasksPeriodSelected(userPeriods.get(pane1Pointer));

        // Changes the scene to Period specific view
        try {
            SetupScene.changeScene("TasksPeriodView.fxml", goToTodayButton);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene from general Tasks to Period specific Tasks.");
        }
    }

    /**
     * Method which changes the scene to the period, which is
     * specified by pane2.
     */
    @FXML
    private void pane2GoToPeriodButtonClicked() {
        // Saves selected Period in Session
        Session.setTasksPeriodSelected(userPeriods.get(pane2Pointer));

        // Changes the scene to Period specific view
        try {
            SetupScene.changeScene("TasksPeriodView.fxml", goToTodayButton);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene from general Tasks to Period specific Tasks.");
            e.printStackTrace();
        }
    }

    /**
     * Method which changes the scene to the period, which is
     * specified by pane3.
     */
    @FXML
    private void pane3GoToPeriodButtonClicked() {
        // Saves selected Period in Session
        Session.setTasksPeriodSelected(userPeriods.get(pane3Pointer));

        // Changes the scene to Period specific view
        try {
            SetupScene.changeScene("TasksPeriodView.fxml", goToTodayButton);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene from general Tasks to Period specific Tasks.");
        }
    }

    // Methods responsible for handling element styling
    /**
     * Reverts goLeft button styling back to usual
     * when hover ends/button is exited.
     */
    @FXML
    private void goLeftExited() {
        ControlScene.controlButtonEffect("next_element_left.png", goLeftButton);
    }

    /**
     * Handles styling of the goLeftButton when hovered.
     */
    @FXML
    private void goLeftHovered() {
        ControlScene.controlButtonEffect("next_element_left_selected.png", goLeftButton);
    }

    /**
     * Reverts goRight button styling back to usual
     * when hover ends/button is exited.
     */
    @FXML
    private void goRightExited() {
        ControlScene.controlButtonEffect("next_element_right.png", goRightButton);
    }

    /**
     * Handles styling of the goRightButton when hovered.
     */
    @FXML
    private void goRightHovered() {
        ControlScene.controlButtonEffect("next_element_right_selected.png", goRightButton);
    }

    /**
     * Reverts goToTodayButton button styling back to usual
     * when hover ends/button is exited.
     */
    @FXML
    private void goToTodayButtonExited() {
        ControlScene.buttonExited(goToTodayButton, goToTodayButtonImage,
                goToTodayButtonLabel, "go_to_icon.png");
    }

    /**
     * Handles styling of the goToTodayButton when hovered.
     */
    @FXML
    private void goToTodayButtonHovered() {
        ControlScene.buttonHovered(goToTodayButton, goToTodayButtonImage,
                goToTodayButtonLabel, "go_to_icon_selected.png");
    }

    /**
     * Reverts pane1AddEventButton button styling back to usual
     * when hover ends/button is exited.
     */
    @FXML
    private void pane1AddTaskButtonExited() {
        ControlScene.buttonExited(pane1AddTaskButton, pane1AddTaskButtonImage,
                pane1AddTaskButtonLabel, "add_icon.png");
    }

    /**
     * Handles styling of the pane1AddEventButton when hovered.
     */
    @FXML
    private void pane1AddTaskButtonHovered() {
        ControlScene.buttonHovered(pane1AddTaskButton, pane1AddTaskButtonImage,
                pane1AddTaskButtonLabel, "add_icon_selected.png");
    }

    /**
     * Reverts pane1GoToPeriodButton button styling back to usual
     * when hover ends/button is exited.
     */
    @FXML
    private void pane1GoToPeriodButtonExited() {
        ControlScene.buttonExited(pane1GoToPeriodButton, pane1GoToPeriodButtonImage,
                pane1GoToPeriodButtonLabel, "go_to_icon.png");
    }

    /**
     * Handles styling of the pane1GoToPeriodButton when hovered.
     */
    @FXML
    private void pane1GoToPeriodButtonHovered() {
        ControlScene.buttonHovered(pane1GoToPeriodButton, pane1GoToPeriodButtonImage,
                pane1GoToPeriodButtonLabel, "go_to_icon_selected.png");
    }

    /**
     * Reverts pane2AddEventButton button styling back to usual
     * when hover ends/button is exited.
     */
    @FXML
    private void pane2AddTaskButtonExited() {
        ControlScene.buttonExited(pane2AddTaskButton, pane2AddTaskButtonImage,
                pane2AddTaskButtonLabel, "add_icon.png");
    }

    /**
     * Handles styling of the pane2AddEventButton when hovered.
     */
    @FXML
    private void pane2AddTaskButtonHovered() {
        ControlScene.buttonHovered(pane2AddTaskButton, pane2AddTaskButtonImage,
                pane2AddTaskButtonLabel, "add_icon_selected.png");
    }

    /**
     * Reverts pane2GoToPeriodButton button styling back to usual
     * when hover ends/button is exited.
     */
    @FXML
    private void pane2GoToPeriodButtonExited() {
        ControlScene.buttonExited(pane2GoToPeriodButton, pane2GoToPeriodButtonImage,
                pane2GoToPeriodButtonLabel, "go_to_icon.png");
    }

    /**
     * Handles styling of the pane2GoToPeriodButton when hovered.
     */
    @FXML
    private void pane2GoToPeriodButtonHovered() {
        ControlScene.buttonHovered(pane2GoToPeriodButton, pane2GoToPeriodButtonImage,
                pane2GoToPeriodButtonLabel, "go_to_icon_selected.png");
    }

    /**
     * Reverts pane3AddEventButton button styling back to usual
     * when hover ends/button is exited.
     */
    @FXML
    private void pane3AddTaskButtonExited() {
        ControlScene.buttonExited(pane3AddTaskButton, pane3AddTaskButtonImage,
                pane3AddTaskButtonLabel, "add_icon.png");
    }

    /**
     * Handles styling of the pane3AddEventButton when hovered.
     */
    @FXML
    private void pane3AddTaskButtonHovered() {
        ControlScene.buttonHovered(pane3AddTaskButton, pane3AddTaskButtonImage,
                pane3AddTaskButtonLabel, "add_icon_selected.png");
    }

    /**
     * Reverts pane3GoToPeriodButton button styling back to usual
     * when hover ends/button is exited.
     */
    @FXML
    private void pane3GoToPeriodButtonExited() {
        ControlScene.buttonExited(pane3GoToPeriodButton, pane3GoToPeriodButtonImage,
                pane3GoToPeriodButtonLabel, "go_to_icon.png");
    }

    /**
     * Handles styling of the pane3GoToPeriodButton when hovered.
     */
    @FXML
    private void pane3GoToPeriodButtonHovered() {
        ControlScene.buttonHovered(pane3GoToPeriodButton, pane3GoToPeriodButtonImage,
                pane3GoToPeriodButtonLabel, "go_to_icon_selected.png");
    }
}
