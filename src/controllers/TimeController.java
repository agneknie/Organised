package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultNavigation;
import core.Day;
import core.Period;
import core.Session;
import core.Week;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
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
 * Class which handles Time Tab functionality and UI.
 */
public class TimeController extends DefaultNavigation implements Initializable {

    // Top pane values
    @FXML
    private Label dailyAverage;
    @FXML
    private Label weeklyAverage;

    // Add Period button
    @FXML
    private Pane actionButton;
    @FXML
    private Label actionButtonLabel;
    @FXML
    private ImageView actionButtonImage;

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
    // Line chart for Day data
    @FXML
    private LineChart<String, Number> lineChart;

    // Variables for storing user data
    private List<Period> userPeriods = Session.getSession().getAllPeriods();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Sets up top information panel
        dailyAverage.setText(Session.getSession().getOverallHoursSpentDay());
        weeklyAverage.setText(Session.getSession().getOverallHoursSpentWeek());

        // TODO Sets up navigation panels

        // Sets up the bar chart
        setupBarChart();

        // Sets up line chart
        setupLineChart();
    }

    /**
     * Method which setups the bar chart of the scene.
     */
    private void setupBarChart(){
        // Disables the legend
        barChart.setLegendVisible(false);

        // Sets the font & size of the chart text
        barChart.getYAxis().setTickLabelFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 18));
        barChart.getXAxis().setTickLabelFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 18));

        // Populates the bar chart with user data
        int dataCount = loadUserDataBarChart();

        // Adjusts bar size based on the number of user data
        final int DEFAULT_CATEGORY_GAP = 100;
        final int GAP_ADJUSTING_INCREMENT = 20;
        final int NEED_TO_ADJUST_SIZE = 8;
        final int STARTING_BARS = 3;
        if(dataCount < NEED_TO_ADJUST_SIZE)
            barChart.setCategoryGap(DEFAULT_CATEGORY_GAP-(dataCount-STARTING_BARS)*GAP_ADJUSTING_INCREMENT);
    }

    /**
     * Method which loads user data for the bar chart.
     * Displays information about weekly averages of all Periods.
     *
     * @return the number of elements (bars) in the chart
     */
    private int loadUserDataBarChart(){
        // Creates a series for the chart
        XYChart.Series<String, Number> barChartSeries = new XYChart.Series<String, Number>();

        // Adds each Period's weekly average as data to the bar chart
        int periodCounter = 0;
        for(Period period : userPeriods){
            periodCounter++;
            barChartSeries.getData().add(new XYChart.Data<String, Number>("Y"+period.getAssociatedYear()
                    +" P"+periodCounter, period.getWeeklyAverage()));
        }

        // Adds the data to the chart
        barChart.getData().add(barChartSeries);

        // Returns the number of elements(bars) in the chart
        return barChartSeries.getData().size();
    }

    /**
     * Method which setups the line chart of the scene
     */
    private void setupLineChart(){
        // Disables the legend
        lineChart.setLegendVisible(false);

        // Sets the font & size of the chart text
        lineChart.getYAxis().setTickLabelFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 18));
        lineChart.getXAxis().setTickLabelFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 18));

        // Populates line chart with user data
        loadUserDataLineChart();
    }

    /**
     * Method which loads user data for the line chart.
     * Displays information about daily average of all user Periods.
     */
    private void loadUserDataLineChart(){
        // Creates a series for this period
        XYChart.Series<String, Number> lineChartSeries = new XYChart.Series<String, Number>();

        // Goes through user's periods one by one adding week data to corresponding series
        int periodCounter = 0;
        for(Period period : userPeriods){
            periodCounter ++;
            lineChartSeries.getData().add(new XYChart.Data<String, Number>("Y"+period.getAssociatedYear()
                    +" P"+periodCounter, period.getDailyAverage()));
        }

        // Adds period data to the chart
        lineChart.getData().add(lineChartSeries);
    }

    /**
     * Method which brings a popup to add a Period if action button
     * (period addition button) is pressed.
     */
    @FXML
    private void actionButtonClicked() throws IOException {
        // Creates Period addition popup
        Stage popup = new Stage();
        new PopupStage(popup, "TimePopupViewPeriod.fxml");
    }

    /**
     * Method which changed the navigation panes information when
     * user clicks the left navigation arrow.
     */
    @FXML
    private void goLeftClicked(){
        // TODO goLeft clicked
    }

    /**
     * Method which changed the navigation panes information when
     * user clicks the right navigation arrow.
     */
    @FXML
    private void goRightClicked(){
        // TODO goRight clicked
    }

    /**
     * Method which forwards the user to the screen of the Period,
     * which is selected if navigationPane1 is clicked.
     */
    @FXML
    private void navigationPane1Clicked(){
        // TODO navigationPane1Clicked
    }

    /**
     * Method which forwards the user to the screen of the Period,
     * which is selected if navigationPane2 is clicked.
     */
    @FXML
    private void navigationPane2Clicked(){
        // TODO navigationPane2Clicked
    }

    /**
     * Method which forwards the user to the screen of the Period,
     * which is selected if navigationPane3 is clicked.
     */
    @FXML
    private void navigationPane3Clicked(){
        // TODO navigationPane3Clicked
    }

    /**
     * Method which forwards the user to the screen of the Period,
     * which is selected if navigationPane4 is clicked.
     */
    @FXML
    private void navigationPane4Clicked(){
        // TODO navigationPane4Clicked
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
