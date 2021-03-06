package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultNavigation;
import controllers.utilities.SetupScene;
import core.Day;
import core.Period;
import core.Session;
import core.Week;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
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
 * Class which handles Time Tab functionality and UI.
 */
public class TimeController extends DefaultNavigation implements Initializable {

    // Top pane values
    @FXML
    private Label dailyAverage;
    @FXML
    private Label weeklyAverage;
    @FXML
    private Label noPeriodsYetLabel;

    // Add Period button
    @FXML
    private Pane actionButton;
    @FXML
    private Label actionButtonLabel;
    @FXML
    private ImageView actionButtonImage;

    // Go to Today button
    @FXML
    private Pane goToTodayButton;
    @FXML
    private Label goToTodayButtonLabel;
    @FXML
    private ImageView goToTodayButtonImage;
    @FXML
    private Label errorMessage;

    // Navigation buttons for selecting periods
    @FXML
    private ImageView goLeftButton;
    @FXML
    private ImageView goRightButton;

    // Navigation pane 1
    private int navigationPane1Pointer = -1;
    @FXML
    private Pane navigationPane1;
    @FXML
    private Label navigationPane1Year;
    @FXML
    private Label navigationPane1Period;

    // Navigation pane 2
    private int navigationPane2Pointer = -1;
    @FXML
    private Pane navigationPane2;
    @FXML
    private Label navigationPane2Year;
    @FXML
    private Label navigationPane2Period;

    // Navigation pane 3
    private int navigationPane3Pointer = -1;
    @FXML
    private Pane navigationPane3;
    @FXML
    private Label navigationPane3Year;
    @FXML
    private Label navigationPane3Period;

    // Navigation pane 4
    private int navigationPane4Pointer = -1;
    @FXML
    private Pane navigationPane4;
    @FXML
    private Label navigationPane4Year;
    @FXML
    private Label navigationPane4Period;

    // Bar chart for Week data
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private Label barChartLabel;
    @FXML
    private Pane barChartPane;
    private final Line baseLine = new Line();

    // Variables for storing user data
    private List<Period> userPeriods;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Cleans session variables in case they exist
        Session.setTimePeriodSelected(null);

        // Gets all user periods
        userPeriods = Session.getSession().getAllPeriods();

        // Sets up top information panel
        dailyAverage.setText(Session.getSession().getOverallHoursSpentDay());
        weeklyAverage.setText(Session.getSession().getOverallHoursSpentWeek());

        // Sets up navigation panes
        setupNavigation();

        // Sets up the baseline of bar chart
        setupBaselineOfBarChart();

        // If user doesn't have any periods yet
        if(userPeriods.isEmpty()){
            barChart.setVisible(false);
            barChartLabel.setVisible(false);
        }
        // If user has periods
        else{
            noPeriodsYetLabel.setVisible(false);

            // Sets up the bar chart
            setupBarChart();
        }
    }

    /**
     * Method which sets up the navigation panes.
     */
    private void setupNavigation(){
        switch(userPeriods.size()){
            // No periods to display
            case 0:
                // Hides all panes
                navigationPane1.setVisible(false);
                navigationPane2.setVisible(false);
                navigationPane3.setVisible(false);
                navigationPane4.setVisible(false);
                break;
            // One period available
            case 1:
                // Hides unused panes
                navigationPane2.setVisible(false);
                navigationPane3.setVisible(false);
                navigationPane4.setVisible(false);

                // Setups the first pane
                navigationPane1Pointer = 0;
                setupNavigationPane1();
                break;
            // Two periods available
            case 2:
                // Hides unused panes
                navigationPane3.setVisible(false);
                navigationPane4.setVisible(false);

                // Setups the first two panes
                navigationPane1Pointer = 0;
                navigationPane2Pointer = 1;
                setupNavigationPane1();
                setupNavigationPane2();
                break;
            // Three periods available
            case 3:
                // Hides unused pane
                navigationPane4.setVisible(false);

                // Setups the first three panes
                navigationPane1Pointer = 0;
                navigationPane2Pointer = 1;
                navigationPane3Pointer = 2;
                setupNavigationPane1();
                setupNavigationPane2();
                setupNavigationPane3();
                break;
            // Four or more periods available
            default:
                // Setups all panes
                navigationPane1Pointer = 0;
                navigationPane2Pointer = 1;
                navigationPane3Pointer = 2;
                navigationPane4Pointer = 3;
                setupNavigationPane1();
                setupNavigationPane2();
                setupNavigationPane3();
                setupNavigationPane4();
        }

        // Setups navigation arrow visibility
        setupNavigationArrowVisibility();
    }

    /**
     * Method which setups navigation pane 1 with period data.
     */
    private void setupNavigationPane1(){
        // Makes visible in case previously hidden
        navigationPane1.setVisible(true);

        // Populates fields with period data
        navigationPane1Year.setText("Year "+userPeriods.get(navigationPane1Pointer).getAssociatedYear());
        navigationPane1Period.setText(userPeriods.get(navigationPane1Pointer).getName());
    }

    /**
     * Method which setups navigation pane 2 with period data.
     */
    private void setupNavigationPane2(){
        // Makes visible in case previously hidden
        navigationPane2.setVisible(true);

        // Populates fields with period data
        navigationPane2Year.setText("Year "+userPeriods.get(navigationPane2Pointer).getAssociatedYear());
        navigationPane2Period.setText(userPeriods.get(navigationPane2Pointer).getName());
    }

    /**
     * Method which setups navigation pane 3 with period data.
     */
    private void setupNavigationPane3(){
        // Makes visible in case previously hidden
        navigationPane3.setVisible(true);

        // Populates fields with period data
        navigationPane3Year.setText("Year "+userPeriods.get(navigationPane3Pointer).getAssociatedYear());
        navigationPane3Period.setText(userPeriods.get(navigationPane3Pointer).getName());
    }

    /**
     * Method which setups navigation pane 4 with period data.
     */
    private void setupNavigationPane4(){
        // Makes visible in case previously hidden
        navigationPane4.setVisible(true);

        // Populates fields with period data
        navigationPane4Year.setText("Year "+userPeriods.get(navigationPane4Pointer).getAssociatedYear());
        navigationPane4Period.setText(userPeriods.get(navigationPane4Pointer).getName());
    }

    /**
     * Method which setups the bar chart of the scene.
     */
    private void setupBarChart(){
        // Disables chart animation
        barChart.setAnimated(false);

        // Cleans the chart of previous data
        barChart.getData().clear();

        // Sets the font & size of the chart text
        barChart.getYAxis().setTickLabelFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 18));
        barChart.getXAxis().setTickLabelFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 18));

        // Populates the bar chart with user data
        loadUserDataBarChart();

        // Draws the baseline for the data
        drawBarChartBaseline();
    }

    /**
     * Method which draws the baseline for the bar chart.
     */
    private void drawBarChartBaseline(){
        // Find chart area Node
        Node chartArea = barChart.lookup(".chart-plot-background");
        // Remember scene position of chart area
        Bounds chartAreaBounds = chartArea.localToParent(chartArea.getBoundsInLocal());

        // Sets x coordinate of the baseline
        baseLine.setStartX(chartAreaBounds.getMinX());
        baseLine.setEndX(chartAreaBounds.getMaxX());

        // Sets y coordinate of the baseline
        double yShift = chartAreaBounds.getMinY();
        // Find pixel position of the specified value
        double userAverageHours = barChart.getYAxis().getDisplayPosition(Session.getSession().getOverallHoursSpentWeekBaseline());
        baseLine.setStartY(yShift + userAverageHours);
        baseLine.setEndY(yShift + userAverageHours);
    }

    /**
     * Method which initially sets up the baseline for the barchart.
     */
    private void setupBaselineOfBarChart(){
        // Adds the baseline to the bar chart pane
        barChartPane.getChildren().addAll(baseLine);

        // Sets line style and adds it to the bar chart pane
        baseLine.setStyle("-fx-stroke: white; -fx-stroke-width: 3");

        // Listener to update the baseline after initial start
        barChart.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(this::drawBarChartBaseline));
    }

    /**
     * Method which configures navigation arrow visibility based on the number
     * of Periods the user has and how are they currently displayed.
     */
    private void setupNavigationArrowVisibility(){
        int periodNumber = userPeriods.size();
        // Enables both arrows to remove previous configurations
        goRightButton.setVisible(true);
        goLeftButton.setVisible(true);

        // If there are less than 4 periods, navigation arrows are not needed
        if(periodNumber <= 4){
            goLeftButton.setVisible(false);
            goRightButton.setVisible(false);
        }

        // Otherwise, one or both arrows might be needed
        else {
            if(navigationPane1Pointer == 0) goLeftButton.setVisible(false);
            if(navigationPane4Pointer == periodNumber-1) goRightButton.setVisible(false);
        }
    }

    /**
     * Method which loads user data for the bar chart.
     * Displays information about weekly averages of all Periods.
     */
    private void loadUserDataBarChart(){
        for(Period period : userPeriods){
            // Creates a series for the period
            XYChart.Series<String, Number> barChartSeries = new XYChart.Series<>();
            barChartSeries.setName(period.getName());
            // Adds all weeks to this series
            for(Week week : period.getAllWeeks()){
                barChartSeries.getData().add(new XYChart.Data<>("W"+week.getWeekNumber(), week.getAllWeekHours()));
            }
            // Adds the data to the chart
            barChart.getData().add(barChartSeries);
        }
    }

    /**
     * Method which brings a popup to add a Period if action button
     * (period addition button) is pressed.
     */
    @FXML
    private void actionButtonClicked() throws IOException {
        // Cleans error message in case it was displayed
        errorMessage.setText("");

        // Creates Period addition popup
        Stage popup = new Stage();
        new PopupStage(popup, "TimePopupViewPeriod.fxml");
    }

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
                        Session.setTimePeriodSelected(period);
                        // Changes the scene to Period specific scene
                        try {
                            SetupScene.changeScene("TimePeriodView.fxml", goToTodayButton);

                        } catch (IOException e) {
                            System.out.println("Exception whilst changing scene from general Time to Period specific Time.");
                        }
                    }
                }
            }
        }
        // If no today was found, displays error message
        errorMessage.setText("No today was found.");
    }

    /**
     * Method which changed the navigation panes information when
     * user clicks the left navigation arrow.
     */
    @FXML
    private void goLeftClicked(){
        // Cleans error message in case it was displayed
        errorMessage.setText("");

        // Updates the navigation pane pointers
        navigationPane1Pointer--;
        navigationPane2Pointer--;
        navigationPane3Pointer--;
        navigationPane4Pointer--;

        // Updates the navigation panes
        setupNavigationPane1();
        setupNavigationPane2();
        setupNavigationPane3();
        setupNavigationPane4();

        // Updates navigation arrow visibility
        setupNavigationArrowVisibility();
    }

    /**
     * Method which changed the navigation panes information when
     * user clicks the right navigation arrow.
     */
    @FXML
    private void goRightClicked(){
        // Cleans error message in case it was displayed
        errorMessage.setText("");

        // Updates the navigation pane pointers
        navigationPane1Pointer++;
        navigationPane2Pointer++;
        navigationPane3Pointer++;
        navigationPane4Pointer++;

        // Updates the navigation panes
        setupNavigationPane1();
        setupNavigationPane2();
        setupNavigationPane3();
        setupNavigationPane4();

        // Updates navigation arrow visibility
        setupNavigationArrowVisibility();
    }

    /**
     * Method which forwards the user to the screen of the Period,
     * which is selected if navigationPane1 is clicked.
     */
    @FXML
    private void navigationPane1Clicked(){
        // Sets the session variable for selected Period for Period specific view to use
        Session.setTimePeriodSelected(userPeriods.get(navigationPane1Pointer));

        // Changes the scene to the Period specific scene
        try {
            SetupScene.changeScene("TimePeriodView.fxml", navigationPane1);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene from general Time to Period specific Time.");
        }
    }

    /**
     * Method which forwards the user to the screen of the Period,
     * which is selected if navigationPane2 is clicked.
     */
    @FXML
    private void navigationPane2Clicked(){
        // Sets the session variable for selected Period for Period specific view to use
        Session.setTimePeriodSelected(userPeriods.get(navigationPane2Pointer));

        // Changes the scene to the Period specific scene
        try {
            SetupScene.changeScene("TimePeriodView.fxml", navigationPane2);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene from general Time to Period specific Time.");
        }
    }

    /**
     * Method which forwards the user to the screen of the Period,
     * which is selected if navigationPane3 is clicked.
     */
    @FXML
    private void navigationPane3Clicked(){
        // Sets the session variable for selected Period for Period specific view to use
        Session.setTimePeriodSelected(userPeriods.get(navigationPane3Pointer));

        // Changes the scene to the Period specific scene
        try {
            SetupScene.changeScene("TimePeriodView.fxml", navigationPane3);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene from general Time to Period specific Time.");
        }
    }

    /**
     * Method which forwards the user to the screen of the Period,
     * which is selected if navigationPane4 is clicked.
     */
    @FXML
    private void navigationPane4Clicked(){
        // Sets the session variable for selected Period for Period specific view to use
        Session.setTimePeriodSelected(userPeriods.get(navigationPane4Pointer));

        // Changes the scene to the Period specific scene
        try {
            SetupScene.changeScene("TimePeriodView.fxml", navigationPane4);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene from general Time to Period specific Time.");
            e.printStackTrace();
        }
    }

    /**
     * Listener for keyboard events.
     * If right/left arrow is pressed, changes the pane information (triggers
     * the navigation arrows actions)
     * @param event used for identifying the key
     */
    public void keyPressed(KeyEvent event){
        KeyCode key = event.getCode();
        // If left arrow is clicked
        if (key.equals(KeyCode.LEFT)) goLeftClicked();
        // If right arrow is clicked
        if (key.equals(KeyCode.RIGHT)) goRightClicked();
    }

    /**
     * Method which refreshes the view/scene if a Period was added.
     */
    @FXML
    private void refreshView(){
        // Gets all user periods, in case one was added
        userPeriods = Session.getSession().getAllPeriods();

        // Refreshes navigation panes
        setupNavigation();

        // If user has periods, loads the chart
        if(!userPeriods.isEmpty()){
            // Configures visibility of fields
            barChart.setVisible(true);
            barChartLabel.setVisible(true);
            noPeriodsYetLabel.setVisible(false);

            // Refreshes the bar chart
            setupBarChart();
        }
    }

    // Methods concerning the styling of elements
    /**
     * Reverts to usual goRight button colour if exited.
     */
    @FXML
    private void goRightExited() {
        ControlScene.controlButtonEffect("next_element_right.png", goRightButton);
    }

    /**
     * Changes goRight button colour if hovered .
     */
    @FXML
    private void goRightHovered() {
        ControlScene.controlButtonEffect("next_element_right_selected.png", goRightButton);
    }

    /**
     * Reverts to usual goLeft button colour if exited.
     */
    @FXML
    private void goLeftExited() {
        ControlScene.controlButtonEffect("next_element_left.png", goLeftButton);
    }

    /**
     * Changes goLeft button colour if hovered .
     */
    @FXML
    private void goLeftHovered() {
        ControlScene.controlButtonEffect("next_element_left_selected.png", goLeftButton);
    }

    /**
     * Changes the styling of action button if hovered.
     */
    @FXML
    private void actionButtonHovered(){
        ControlScene.buttonHovered(actionButton, actionButtonImage, actionButtonLabel, "add_icon_selected.png");
    }

    /**
     * Reverts to default button styling if exited.
     */
    @FXML
    private void actionButtonExited(){
        ControlScene.buttonExited(actionButton, actionButtonImage, actionButtonLabel, "add_icon.png");
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
     * General method for changing navigation pane style when hovered.
     * Used by navigationPaneXHovered methods, where X is pane number.
     *
     * @param pane hovered navigation pane
     * @param year year name label of the navigation pane
     * @param period period name label of the navigation pane
     */
    private void navigationPaneHovered(Pane pane, Label year, Label period){
        pane.setStyle("-fx-background-color: white; -fx-background-radius: 20; " +
                "-fx-border-style: solid; -fx-border-color: white; -fx-border-width: 5; -fx-border-radius: 20");
        year.setTextFill(Paint.valueOf("#000000"));
        period.setTextFill(Paint.valueOf("#000000"));
    }

    /**
     * General method for changing navigation pane style when exited.
     * Used by navigationPaneXExited methods, where X is pane number.
     *
     * @param pane previously hovered navigation pane
     * @param year year name label of the navigation pane
     * @param period period name label of the navigation pane
     */
    private void navigationPaneExited(Pane pane, Label year, Label period){
        pane.setStyle("-fx-background-color: none; -fx-background-radius: 20; " +
                "-fx-border-style: solid; -fx-border-color: white; -fx-border-width: 5; -fx-border-radius: 20");
        year.setTextFill(Paint.valueOf("#FFFFFF"));
        period.setTextFill(Paint.valueOf("#E6BB9A"));
    }

    /**
     * Changes the styling of the navigationPane 1 if hovered.
     */
    @FXML
    private void navigationPane1Hovered(){
        navigationPaneHovered(navigationPane1, navigationPane1Year, navigationPane1Period);
    }

    /**
     * Reverts to normal styling of the navigationPane 1 if exited.
     */
    @FXML
    private void navigationPane1Exited(){
        navigationPaneExited(navigationPane1, navigationPane1Year, navigationPane1Period);
    }

    /**
     * Changes the styling of the navigationPane 2 if hovered.
     */
    @FXML
    private void navigationPane2Hovered(){
        navigationPaneHovered(navigationPane2, navigationPane2Year, navigationPane2Period);
    }

    /**
     * Reverts to normal styling of the navigationPane 2 if exited.
     */
    @FXML
    private void navigationPane2Exited(){
        navigationPaneExited(navigationPane2, navigationPane2Year, navigationPane2Period);
    }

    /**
     * Changes the styling of the navigationPane 3 if hovered.
     */
    @FXML
    private void navigationPane3Hovered(){
        navigationPaneHovered(navigationPane3, navigationPane3Year, navigationPane3Period);
    }

    /**
     * Reverts to normal styling of the navigationPane 3 if exited.
     */
    @FXML
    private void navigationPane3Exited(){
        navigationPaneExited(navigationPane3, navigationPane3Year, navigationPane3Period);
    }

    /**
     * Changes the styling of the navigationPane 4 if hovered.
     */
    @FXML
    private void navigationPane4Hovered(){
        navigationPaneHovered(navigationPane4, navigationPane4Year, navigationPane4Period);
    }

    /**
     * Reverts to normal styling of the navigationPane 4 if exited.
     */
    @FXML
    private void navigationPane4Exited(){
        navigationPaneExited(navigationPane4, navigationPane4Year, navigationPane4Period);
    }
}
