package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultNavigation;
import core.Day;
import core.Period;
import core.Session;
import core.Week;
import core.enums.Semester;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.time.DayOfWeek;
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

    // Add Minutes to Day Pane
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

    // Variables to store user data
    private final Period userSelectedPeriod = Session.getTimePeriodSelected();
    private Week userSelectedWeek;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO Initialize

        // Finds out which week should be selected by the user
        userSelectedWeek = Week.getCurrentWeek(userSelectedPeriod.getAllWeeks());

        // Setups the combo box for days in the minutes addition panel
        daysComboBox.getItems().setAll(userSelectedWeek.getAllDays());
        // Styles semester combo box text
        daysComboBox.setButtonCell(new ListCell(){
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

        // Setups line chart
        setupLineChart();

        // Setups bar chart
        setupBarChart();
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
        // Creates a series for the chart
        XYChart.Series<String, Number> barChartSeries = new XYChart.Series<String, Number>();

        // TODO Adds user data to the series

        // Adds the data to the chart
        barChart.getData().add(barChartSeries);

        // Adjusts bar size based on the number of user data
        final int DEFAULT_CATEGORY_GAP = 100;
        final int GAP_ADJUSTING_INCREMENT = 20;
        final int NEED_TO_ADJUST_SIZE = 8;
        final int STARTING_BARS = 3;
        if(barChartSeries.getData().size() < NEED_TO_ADJUST_SIZE){
            barChart.setCategoryGap(DEFAULT_CATEGORY_GAP-(barChartSeries.getData().size()
                    -STARTING_BARS)*GAP_ADJUSTING_INCREMENT);
        }
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

        // TODO Populates line chart with user data
    }

    /**
     * Method which is responsible for the behaviour
     * when "Delete Period" button is clicked.
     */
    @FXML
    private void actionButtonClicked() {
        //TODO deletePeriod button clicked
    }

    /**
     * Method which is responsible for the behaviour
     * when "Add Minutes" button is clicked.
     */
    @FXML
    private void addMinutesButtonClicked() {
        //TODO addMinutes button clicked
    }

    /**
     * Method which is responsible for the behaviour
     * when "Delete Minutes" button is clicked.
     */
    @FXML
    private void deleteMinutesButtonClicked() {
        //TODO deleteMinutes button clicked
    }

    /**
     * Method which is responsible for the behaviour
     * when "timer action" button is clicked.
     *
     * Either starts or stops the timer.
     */
    @FXML
    private void timerActionButtonClicked() {
        //TODO timer start/stop button clicked
    }

    /**
     * Method which is responsible for the behaviour
     * when "timer reset" button is clicked.
     */
    @FXML
    private void timerResetButtonClicked() {
        //TODO timer reset button clicked
    }

    // Methods responsible for navigation
    /**
     * Method which is responsible for the behaviour
     * when "go back" button is clicked.
     */
    @FXML
    private void goBackClicked() {
        //TODO goBack clicked
    }

    /**
     * Method which is responsible for the behaviour
     * when left navigation arrow is clicked.
     */
    @FXML
    private void goLeftClicked() {
        // TODO goLeft clicked
    }

    /**
     * Method which is responsible for the behaviour
     * when right navigation arrow is clicked.
     */
    @FXML
    private void goRightClicked() {
        // TODO goRight clicked
    }

    // Methods which deal with styling of UI elements
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
