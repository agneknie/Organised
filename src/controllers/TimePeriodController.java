package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultNavigation;
import controllers.utilities.SetupScene;
import core.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import stages.AlertStage;

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
 * Class which handles Time Tab functionality and UI when a Period is selected.
 */
public class TimePeriodController extends DefaultNavigation implements Initializable {
    // Top information pane elements
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

    // Delete period button
    @FXML
    private Pane actionButton;
    @FXML
    private ImageView actionButtonImage;
    @FXML
    private Label actionButtonLabel;

    // Timer Pane elements
    @FXML
    private Label timerLabel;
    // Timer start/stop button
    @FXML
    private Pane timerActionButton;
    @FXML
    private Label timerActionButtonLabel;
    @FXML
    private ImageView timerActionButtonImage;

    // Timer reset button
    @FXML
    private Pane timerResetButton;
    @FXML
    private Label timerResetButtonLabel;
    @FXML
    private ImageView timerResetButtonImage;

    // Below timer information fields
    @FXML
    private Label minutesLeftForPeriodField;
    @FXML
    private Label dailyAverageField;

    // Add Time to Day Pane
    @FXML
    private TextField hoursField;
    @FXML
    private TextField minutesField;
    @FXML
    private ComboBox<Day> daysComboBox;
    @FXML
    private Label errorMessage;
    // Add minutes button
    @FXML
    private Pane addMinutesButton;
    @FXML
    private Label addMinutesButtonLabel;
    @FXML
    private ImageView addMinutesButtonImage;
    // Remove minutes button
    @FXML
    private Pane deleteMinutesButton;
    @FXML
    private Label deleteMinutesButtonLabel;
    @FXML
    private ImageView deleteMinutesButtonImage;

    // Information bellow minutes addition pane
    @FXML
    private Label weekOverallField;

    // Line chart to display days of the week data
    @FXML
    private LineChart<String, Number> lineChart;
    // Bar chart to display weeks of the period data
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private Pane barChartPane;
    private final Line baseLine = new Line();

    // Variables to store user data
    private Period userSelectedPeriod;
    private Week userSelectedWeek;
    private final Stopwatch stopwatch = new Stopwatch();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Gets the period selected by the user
        userSelectedPeriod = Session.getTimePeriodSelected();

        // Finds out which week should be selected by the user
        userSelectedWeek = Week.getCurrentWeek(userSelectedPeriod.getAllWeeks());

        // Setups the combo box for days in the minutes addition panel
        setupDayComboBox();

        // Setups the fields with user information
        setupUserInformation();

        // Configures navigation arrow visibility
        configureNavigationArrows();

        // Sets the name of the action button in timer panel
        timerActionButtonLabel.setText("Start");

        // Setups line chart
        setupLineChart();

        // Setups the baseline for the barchart
        setupBaselineOfBarChart();

        // Setups bar chart
        setupBarChart();

        // Draws the baseline for the data
        drawBarChartBaseline();
    }

    /**
     * Method which setups the day combo box in minutes panel with
     * selected week's days.
     */
    private void setupDayComboBox(){
        daysComboBox.getItems().setAll(userSelectedWeek.getAllDays());
        // Styles semester combo box text
        ControlScene.setupComboBoxStyle(daysComboBox);
        daysComboBox.setValue(null);
    }

    /**
     * Refreshes the screen if user deleted a Period
     */
    @FXML
    private void refreshView(){
        // If a Period was just deleted, forwards to general Time View
        if(Session.getTimePeriodSelected() == null)
            goBackClicked();
    }

    /**
     * Method which setups the scene fields with user information.
     */
    private void setupUserInformation(){
        // Setups top fields
        periodNameLabel.setText("Year " + userSelectedPeriod.getAssociatedYear() +
                ": " + userSelectedPeriod.getName());
        weekNameLabel.setText(userSelectedWeek.toString());
        weekDateLabel.setText(userSelectedWeek.getWeekDate());

        // Setups bottom fields
        minutesLeftForPeriodField.setText(userSelectedPeriod.getMinutesLeft()+" min");
        dailyAverageField.setText(userSelectedWeek.getDailyAverage()/60+"h  "
                +userSelectedWeek.getDailyAverage()%60+"min");
        weekOverallField.setText(userSelectedWeek.getAllWeekHours()+"h");
    }

    /**
     * Method which setups the bar chart of the scene.
     */
    private void setupBarChart(){
        // Disables chart animation
        barChart.setAnimated(false);

        // Disables the legend
        barChart.setLegendVisible(false);

        // Cleans previous data in case needed
        barChart.getData().clear();

        // Sets the font & size of the chart text
        barChart.getYAxis().setTickLabelFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 18));
        barChart.getXAxis().setTickLabelFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 18));

        // Adds user data to the series
        int elementNumber = loadUserDataBarChart();

        // Adjusts bar size based on the number of user data
        final int DEFAULT_CATEGORY_GAP = 100;
        final int GAP_ADJUSTING_INCREMENT = 20;
        final int NEED_TO_ADJUST_SIZE = 8;
        final int STARTING_BARS = 3;
        if(elementNumber < NEED_TO_ADJUST_SIZE){
            barChart.setCategoryGap(DEFAULT_CATEGORY_GAP-(elementNumber-STARTING_BARS)*GAP_ADJUSTING_INCREMENT);
        }

        // Draws the baseline of the chart
        drawBarChartBaseline();
    }

    /**
     * Method which loads user's week data of the given Period into the
     * bar chart.
     *
     * @return number of elements(bars) in the chart.
     */
    private int loadUserDataBarChart(){
        // Creates a series for the chart
        XYChart.Series<String, Number> barChartSeries = new XYChart.Series<>();

        // Adds each Week's of the Period data to the series
        for(Week week : userSelectedPeriod.getAllWeeks()){
            String name = week.toString();
            // If there are more weeks, changes the naming convention to fit nicely
            if(userSelectedPeriod.getAllWeeks().size()>5) name = "W"+week.getWeekNumber();
            barChartSeries.getData().add(new XYChart.Data<>(name, week.getAllWeekHours()));
        }

        // Setups Y axis bounds and tick mark density
        final int WORK_DAYS = 5;
        barChart.getYAxis().setAutoRanging(false);
        ((NumberAxis) barChart.getYAxis()).setTickUnit(5.0);
        ((NumberAxis) barChart.getYAxis()).setLowerBound(0);
        ((NumberAxis) barChart.getYAxis()).setUpperBound(Day.MAX_WORK_HOURS*WORK_DAYS);

        // Adds the data to the chart
        barChart.getData().add(barChartSeries);

        // Returns the number of elements(bars) in the chart
        return barChartSeries.getData().size();
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
     * Method which setups the line chart of the scene
     */
    private void setupLineChart(){
        // Disables chart animation
        lineChart.setAnimated(false);

        // Cleans chart data, in case there was something displayed
        lineChart.getData().clear();

        // Disables the legend
        lineChart.setLegendVisible(false);

        // Sets the font & size of the chart text
        lineChart.getYAxis().setTickLabelFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 18));
        lineChart.getXAxis().setTickLabelFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 18));

        // Setups Y axis bounds and tick mark density
        lineChart.getYAxis().setAutoRanging(false);
        ((NumberAxis) lineChart.getYAxis()).setTickUnit(1.0);
        ((NumberAxis) lineChart.getYAxis()).setLowerBound(0);
        ((NumberAxis) lineChart.getYAxis()).setUpperBound(Day.MAX_WORK_HOURS);

        // Adds the baseline to the line chart
        addBaselineToLineChart();

        // Populates line chart with user data
        loadUserDataLineChart();
    }

    /**
     * Method which loads user data about days of a given week into the line chart.
     */
    private void loadUserDataLineChart(){
        // Creates a series for the chart
        XYChart.Series<String, Number> lineChartSeries = new XYChart.Series<>();

        // Adds each Day's of the selected Week average as data to the bar chart
        for(Day day : userSelectedWeek.getAllDays()){
            lineChartSeries.getData().add(new XYChart.Data<>(day.getShortName(), day.getHoursSpent()));
        }

        // Adds the data to the chart
        lineChart.getData().add(lineChartSeries);
    }

    private void addBaselineToLineChart(){
        // Creates a series for the chart
        XYChart.Series<String, Number> lineChartSeries = new XYChart.Series<>();

        // Adds each point of the baseline
        double baseline = Session.getSession().getOverallHoursSpentDayBaseline();
        for(Day day : userSelectedWeek.getAllDays()){
            lineChartSeries.getData().add(new XYChart.Data<>(day.getShortName(), baseline));
        }

        // Adds the data to the chart
        lineChart.getData().add(lineChartSeries);
    }

    /**
     * Method which configures the visibility of navigation arrows,
     * which are used for navigation between weeks.
     */
    private void configureNavigationArrows(){
        // Finds the index of the selected Week in the list of all Weeks of Period
        List<Week> userWeeks = userSelectedPeriod.getAllWeeks();
        int indexOfSelectedWeek = userWeeks.indexOf(userSelectedWeek);

        // Makes both arrows visible, in case previously disabled
        goLeftButton.setVisible(true);
        goRightButton.setVisible(true);

        // Disables the arrows, based on current week selection
        if(indexOfSelectedWeek == 0) goLeftButton.setVisible(false);
        if(indexOfSelectedWeek == userWeeks.size()-1) goRightButton.setVisible(false);
    }

    /**
     * Method which is responsible for the behaviour
     * when "Delete Period" button is clicked.
     */
    @FXML
    private void actionButtonClicked() throws IOException {
        // Cleans error message in case it was displayed
        errorMessage.setText("");

        // Opens alert popup to confirm deletion
        Stage alert = new Stage();
        new AlertStage(alert, "TimeDeletePeriodAlertView.fxml");
    }

    /**
     * Method which is responsible for the behaviour
     * when "Add Minutes" button is clicked.
     */
    @FXML
    private void addMinutesButtonClicked() {
        // Normalises all fields in case they were marked as wrong before
        normaliseAllFields();
        // Resets error message field
        errorMessage.setText("");

        // Variables for time addition
        int hours = -1;
        int minutes = -1;
        Day day = daysComboBox.getValue();
        boolean valid = true;

        // Checks whether inputs are numbers where needed & not null
        try {
            minutes = Integer.parseInt(minutesField.getText());
        } catch (Exception e){
            ControlScene.highlightWrongField(minutesField);
            valid = false;
            errorMessage.setText("Wrong minutes input.");
        }
        try {
            hours = Integer.parseInt(hoursField.getText());
        } catch (Exception e){
            ControlScene.highlightWrongField(hoursField);
            valid = false;
            errorMessage.setText("Wrong hours input.");
        }
        if(day == null){
            ControlScene.highlightWrongField(daysComboBox);
            valid = false;
            errorMessage.setText("Please select valid day of week.");
        }
        if(minutes < 0){
            errorMessage.setText("Minutes have to be more than or equal to 0.");
            valid = false;
            ControlScene.highlightWrongField(minutesField);
        }
        if(minutes > 59){
            errorMessage.setText("Minutes have to be less than 60.");
            valid = false;
            ControlScene.highlightWrongField(minutesField);
        }
        if(hours < 0){
            errorMessage.setText("Hours have to be more than or equal to 0.");
            valid = false;
            ControlScene.highlightWrongField(hoursField);
        }

        // If field values are correct
        if(valid){
            // If minutes cannot be added to a selected day, displays error message
            if(!day.canAdd(hours*60+minutes+userSelectedPeriod.getMinutesLeft()))
                errorMessage.setText("Time exceeds maximum allowed time per day: "+Day.MAX_WORK_HOURS+"h.");

            // If everything is good, adds the time to the day
           else {
                // Adds the time
                userSelectedPeriod.addMinutes(day, hours*60+minutes);
                errorMessage.setText("Time addition successful!");

                // Refreshes the screen
                // Updates the fields with user information
                setupUserInformation();

                // Updates line chart
                setupLineChart();

                // Updates bar chart
                setupBarChart();
            }
        }
    }

    /**
     * Method which is responsible for the behaviour
     * when "Delete Minutes" button is clicked.
     */
    @FXML
    private void deleteMinutesButtonClicked() {
        // Normalises all fields in case they were marked as wrong before
        normaliseAllFields();
        // Resets error message field
        errorMessage.setText("");

        // Variables for time deletion
        int hours = -1;
        Day day = daysComboBox.getValue();
        boolean valid = true;

        // Checks whether inputs are numbers where needed & not null
        try {
            hours = Integer.parseInt(hoursField.getText());
        } catch (Exception e){
            ControlScene.highlightWrongField(hoursField);
            valid = false;
            errorMessage.setText("Wrong hours input.");
        }
        if(!minutesField.getText().isEmpty()) errorMessage.setText("Minutes cannot be subtracted from day.");
        if(day == null){
            ControlScene.highlightWrongField(daysComboBox);
            valid = false;
            errorMessage.setText("Please select valid day of week.");
        }

        // If all fields are valid
        if(valid){
            // If hours cannot be deleted from a selected day, displays error message
            if(!day.canRemove(hours*60)) errorMessage.setText("Hours for the day would be below 0.");

            // If everything is good, deletes the time from the day
            else {
                // Deletes the time
                day.removeHours(hours);
                day.updateDay();
                errorMessage.setText("Time deletion successful!");

                // Refreshes the screen
                // Updates the fields with user information
                setupUserInformation();

                // Updates line chart
                setupLineChart();

                // Updates bar chart
                setupBarChart();
            }
        }
    }

    /**
     * Method which is responsible for the behaviour
     * when "timer action" button is clicked.
     *
     * Either starts or stops the timer.
     */
    @FXML
    private void timerActionButtonClicked() {
        // Cleans error message in case it was displayed
        errorMessage.setText("");

        // Acts based on the current label
        // Starts the timer
        if(timerActionButtonLabel.getText().equals("Start")){
            stopwatch.start();
            final Timeline timeline = new Timeline(
                    new KeyFrame(
                            Duration.millis(500),
                            event -> timerLabel.setText(stopwatch.getElapsedTime())
                    )
            );
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

            // Changes timer button label
            timerActionButtonLabel.setText("Stop");
        }

        // Stops the timer
        else {
            stopwatch.stop();
            // Forwards elapsed time to the hours and minutes field
            hoursField.setText(Integer.toString(stopwatch.getHours()));
            minutesField.setText(Integer.toString(
                    stopwatch.getMinutes()-stopwatch.getHours()*60));
            // Changes timer button label
            timerActionButtonLabel.setText("Start");
        }
    }

    /**
     * Method which is responsible for the behaviour
     * when "timer reset" button is clicked.
     */
    @FXML
    private void timerResetButtonClicked() {
        // Cleans error message in case it was displayed
        errorMessage.setText("");

        // Resets the timer
        stopwatch.reset();

        // Removes the time from relevant fields
        timerLabel.setText("00:00:00");
        timerActionButtonLabel.setText("Start");
        minutesField.setText("");
        hoursField.setText("");
    }

    // Methods responsible for navigation
    /**
     * Method which is responsible for the behaviour
     * when "go back" button is clicked.
     */
    @FXML
    private void goBackClicked() {
        // Changes the scene to the the general Time scene scene
        try {
            SetupScene.changeScene("TimeView.fxml", goBackButton);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene from Period specific Time to general Time view.");
        }
    }

    /**
     * Method which is responsible for the behaviour
     * when left navigation arrow is clicked.
     */
    @FXML
    private void goLeftClicked() {
        // Finds the index of the selected Week in the list of all Weeks of Period
        List<Week> userWeeks = userSelectedPeriod.getAllWeeks();
        int indexOfSelectedWeek = userWeeks.indexOf(userSelectedWeek);

        // Updates the displayed week
        userSelectedWeek = userWeeks.get(indexOfSelectedWeek-1);

        // Updates the scene
        updateAfterNavigation();
    }

    /**
     * Method which is responsible for the behaviour
     * when right navigation arrow is clicked.
     */
    @FXML
    private void goRightClicked() {
        // Finds the index of the selected Week in the list of all Weeks of Period
        List<Week> userWeeks = userSelectedPeriod.getAllWeeks();
        int indexOfSelectedWeek = userWeeks.indexOf(userSelectedWeek);

        // Updates the displayed week
        userSelectedWeek = userWeeks.get(indexOfSelectedWeek+1);

        // Updates the scene
        updateAfterNavigation();
    }

    /**
     * Method which updates/resets the scene fields & elements
     * after a navigation arrow was pressed.
     */
    private void updateAfterNavigation(){
        // Updates day combo box in minutes panel
        setupDayComboBox();

        // Setups the fields with user information
        setupUserInformation();

        // Configures navigation arrow visibility
        configureNavigationArrows();

        // Setups line chart
        setupLineChart();

        // Removes possible error messages if any
        errorMessage.setText("");

        // Cleans minutes panel fields if any leftovers
        minutesField.setText("");
        hoursField.setText("");

        // Normalises the fields if they were marked as wrong before
        normaliseAllFields();
    }

    // Methods which deal with styling of UI elements
    /**
     * Normalises all text field borders in case they were highlighted
     * as wrong previously.
     */
    private void normaliseAllFields(){
        ControlScene.normaliseWrongField(hoursField);
        ControlScene.normaliseWrongField(minutesField);
        ControlScene.normaliseWrongField(daysComboBox);
    }

    /**
     * Reverts the styling back to normal if actionButton (delete period button)
     * is no longer hovered.
     */
    @FXML
    private void actionButtonExited() {
        ControlScene.buttonExited(actionButton, actionButtonImage, actionButtonLabel, "delete_icon.png");
    }

    /**
     * Changes the styling of actionButton (delete period button) if hovered.
     */
    @FXML
    private void actionButtonHovered() {
        ControlScene.buttonHovered(actionButton, actionButtonImage, actionButtonLabel, "delete_icon_selected.png");
    }

    /**
     * Reverts the styling back to normal if addMinutesButton is no
     * longer hovered.
     */
    @FXML
    private void addMinutesButtonExited() {
        ControlScene.buttonExited(addMinutesButton, addMinutesButtonImage, addMinutesButtonLabel, "add_icon.png");
    }

    /**
     * Changes the styling of addMinutesButton if hovered.
     */
    @FXML
    private void addMinutesButtonHovered() {
        ControlScene.buttonHovered(addMinutesButton, addMinutesButtonImage, addMinutesButtonLabel, "add_icon_selected.png");
    }

    /**
     * Reverts the styling back to normal if deleteMinutesButton is no
     * longer hovered.
     */
    @FXML
    private void deleteMinutesButtonExited() {
        ControlScene.buttonExited(deleteMinutesButton, deleteMinutesButtonImage, deleteMinutesButtonLabel, "subtract_icon.png");
    }

    /**
     * Changes the styling of deleteMinutesButton if hovered.
     */
    @FXML
    private void deleteMinutesButtonHovered() {
        ControlScene.buttonHovered(deleteMinutesButton, deleteMinutesButtonImage, deleteMinutesButtonLabel, "subtract_icon_selected.png");
    }

    /**
     * Reverts to usual goLeft button colour if exited.
     */
    @FXML
    private void goLeftExited() {
        ControlScene.controlButtonEffect("next_element_left.png", goLeftButton);
    }

    /**
     * Changes goLeft button colour if hovered.
     */
    @FXML
    private void goLeftHovered() {
        ControlScene.controlButtonEffect("next_element_left_selected.png", goLeftButton);
    }

    /**
     * Reverts to usual goRight button colour if exited.
     */
    @FXML
    private void goRightExited() {
        ControlScene.controlButtonEffect("next_element_right.png", goRightButton);
    }

    /**
     * Changes goRight button colour if hovered.
     */
    @FXML
    private void goRightHovered() {
        ControlScene.controlButtonEffect("next_element_right_selected.png", goRightButton);
    }

    /**
     * Reverts the styling back to normal if timerActionButton is no
     * longer hovered.
     */
    @FXML
    private void timerActionButtonExited() {
        ControlScene.buttonExited(timerActionButton, timerActionButtonImage, timerActionButtonLabel, "timer_icon.png");
    }

    /**
     * Changes the styling of timerActionButton if hovered.
     */
    @FXML
    private void timerActionButtonHovered() {
        ControlScene.buttonHovered(timerActionButton, timerActionButtonImage, timerActionButtonLabel, "timer_icon_selected.png");
    }

    /**
     * Reverts the styling back to normal if timerResetButton is no
     * longer hovered.
     */
    @FXML
    private void timerResetButtonExited() {
        ControlScene.buttonExited(timerResetButton, timerResetButtonImage, timerResetButtonLabel, "reset_icon.png");
    }

    /**
     * Changes the styling of timerResetButton if hovered.
     */
    @FXML
    private void timerResetButtonHovered() {
        ControlScene.buttonHovered(timerResetButton, timerResetButtonImage, timerResetButtonLabel, "reset_icon_selected.png");
    }

}
