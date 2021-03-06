package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultNavigation;
import controllers.utilities.SetupScene;
import core.Day;
import core.Session;
import core.Period;
import core.Week;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import stages.BiggerPopupStage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
 * Class which handles Schedule Tab functionality and UI.
 */
public class ScheduleController extends DefaultNavigation implements Initializable {

    // Top panel fields
    @FXML
    private Label todayLabel;
    @FXML
    private Label timeLabel;
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

    // Navigation buttons
    @FXML
    private ImageView goLeftButton;
    @FXML
    private ImageView goRightButton;

    // Pane 1
    @FXML
    private Pane pane1;
    @FXML
    private Label pane1YearLabel;
    @FXML
    private Label pane1PeriodLabel;
    private int pane1Pointer;
    // Pane 1 go to today button
    @FXML
    private Pane pane1GoToPeriodButton;
    @FXML
    private ImageView pane1GoToPeriodButtonImage;
    @FXML
    private Label pane1GoToPeriodButtonLabel;
    // Pane 1 add recurring event button
    @FXML
    private Pane pane1AddEventButton;
    @FXML
    private ImageView pane1AddEventButtonImage;
    @FXML
    private Label pane1AddEventButtonLabel;

    // Pane 2
    @FXML
    private Pane pane2;
    @FXML
    private Label pane2YearLabel;
    @FXML
    private Label pane2PeriodLabel;
    private int pane2Pointer;
    // Pane 2 go to today button
    @FXML
    private Pane pane2GoToPeriodButton;
    @FXML
    private ImageView pane2GoToPeriodButtonImage;
    @FXML
    private Label pane2GoToPeriodButtonLabel;
    // Pane 2 add recurring event button
    @FXML
    private Pane pane2AddEventButton;
    @FXML
    private ImageView pane2AddEventButtonImage;
    @FXML
    private Label pane2AddEventButtonLabel;

    // Pane 3
    @FXML
    private Pane pane3;
    @FXML
    private Label pane3YearLabel;
    @FXML
    private Label pane3PeriodLabel;
    private int pane3Pointer;
    // Pane 3 go to today button
    @FXML
    private Pane pane3GoToPeriodButton;
    @FXML
    private ImageView pane3GoToPeriodButtonImage;
    @FXML
    private Label pane3GoToPeriodButtonLabel;
    // Pane 3 add recurring event button
    @FXML
    private Pane pane3AddEventButton;
    @FXML
    private ImageView pane3AddEventButtonImage;
    @FXML
    private Label pane3AddEventButtonLabel;

    // User specific variables
    private List<Period> userPeriods;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Gets user periods
        userPeriods = Session.getSession().getAllPeriods();

        // If user has periods, hides information label
        if(!userPeriods.isEmpty())  noPeriodsYetLabel.setVisible(false);

        // Sets up top pane with data
        initializeTopPane();

        // Sets up panes with information
        setupNavigationPanes();

        // Sets up navigation
        setupNavigation();
    }

    /**
     * Method which sets up top pane with data concerning
     * today's date and time.
     */
    private void initializeTopPane(){
        // Creates date formatter
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");
        // Gets current day and day of week
        LocalDate today = LocalDate.now();
        // Sets the date
        todayLabel.setText(today.format(dateFormatter));

        // Sets the time label
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        timeLabel.setText(LocalDateTime.now().format(timeFormatter));
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(500),
                        event -> timeLabel.setText(LocalDateTime.now().format(timeFormatter))
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
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

    // Navigation buttons
    /**
     * Method which handles left navigation arrow clicked.
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

    // Methods handling button functionality
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
                    // Week configuration is done in SchedulePeriod
                    if(day.getDate().equals(today)){
                        Session.setSchedulePeriodSelected(period);
                        // Changes the scene to Period specific view
                        try {
                            SetupScene.changeScene("SchedulePeriodView.fxml", goToTodayButton);

                        } catch (IOException e) {
                            System.out.println("Exception whilst changing scene from general Schedule to Period specific Schedule.");
                        }
                    }
                }
            }
        }
        // If no today was found, displays error message
        errorMessage.setText("No today was found.");
    }

    /**
     * Method which changes the scene to the period, which is
     * specified by pane1.
     */
    @FXML
    private void pane1GoToPeriodButtonClicked() {
        // Saves selected Period in Session
        Session.setSchedulePeriodSelected(userPeriods.get(pane1Pointer));

        // Changes the scene to Period specific view
        try {
            SetupScene.changeScene("SchedulePeriodView.fxml", goToTodayButton);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene from general Schedule to Period specific Schedule.");
        }
    }

    /**
     * Method which changes the scene to the period, which is
     * specified by pane2.
     */
    @FXML
    private void pane2GoToPeriodButtonClicked() {
        // Saves selected Period in Session
        Session.setSchedulePeriodSelected(userPeriods.get(pane2Pointer));

        // Changes the scene to Period specific view
        try {
            SetupScene.changeScene("SchedulePeriodView.fxml", goToTodayButton);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene from general Schedule to Period specific Schedule.");
        }
    }

    /**
     * Method which changes the scene to the period, which is
     * specified by pane3.
     */
    @FXML
    private void pane3GoToPeriodButtonClicked() {
        // Saves selected Period in Session
        Session.setSchedulePeriodSelected(userPeriods.get(pane3Pointer));

        // Changes the scene to Period specific view
        try {
            SetupScene.changeScene("SchedulePeriodView.fxml", goToTodayButton);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene from general Schedule to Period specific Schedule.");
        }
    }

    /**
     * Method which opens recurring event addition popup for
     * period, which is described by pane1.
     */
    @FXML
    private void pane1AddEventButtonClicked() throws IOException {
        // Cleans error message field in case it was displaying something
        errorMessage.setText("");

        // Saves selected Period in Session
        Session.setSchedulePeriodSelected(userPeriods.get(pane1Pointer));

        // Opens the popup
        Stage popup = new Stage();
        new BiggerPopupStage(popup, "SchedulePopupViewRecurringEvent.fxml");
    }

    /**
     * Method which opens recurring event addition popup for
     * period, which is described by pane2.
     */
    @FXML
    private void pane2AddEventButtonClicked() throws IOException {
        // Cleans error message field in case it was displaying something
        errorMessage.setText("");

        // Saves selected Period in Session
        Session.setSchedulePeriodSelected(userPeriods.get(pane2Pointer));

        // Opens the popup
        Stage popup = new Stage();
        new BiggerPopupStage(popup, "SchedulePopupViewRecurringEvent.fxml");
    }

    /**
     * Method which opens recurring event addition popup for
     * period, which is described by pane1.
     */
    @FXML
    private void pane3AddEventButtonClicked() throws IOException {
        // Cleans error message field in case it was displaying something
        errorMessage.setText("");

        // Saves selected Period in Session
        Session.setSchedulePeriodSelected(userPeriods.get(pane3Pointer));

        // Opens the popup
        Stage popup = new Stage();
        new BiggerPopupStage(popup, "SchedulePopupViewRecurringEvent.fxml");
    }

    // Methods concerning styling of scene
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
    private void pane1AddEventButtonExited() {
        ControlScene.buttonExited(pane1AddEventButton, pane1AddEventButtonImage,
                pane1AddEventButtonLabel, "add_icon.png");
    }

    /**
     * Handles styling of the pane1AddEventButton when hovered.
     */
    @FXML
    private void pane1AddEventButtonHovered() {
        ControlScene.buttonHovered(pane1AddEventButton, pane1AddEventButtonImage,
                pane1AddEventButtonLabel, "add_icon_selected.png");
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
    private void pane2AddEventButtonExited() {
        ControlScene.buttonExited(pane2AddEventButton, pane2AddEventButtonImage,
                pane2AddEventButtonLabel, "add_icon.png");
    }

    /**
     * Handles styling of the pane2AddEventButton when hovered.
     */
    @FXML
    private void pane2AddEventButtonHovered() {
        ControlScene.buttonHovered(pane2AddEventButton, pane2AddEventButtonImage,
                pane2AddEventButtonLabel, "add_icon_selected.png");
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
    private void pane3AddEventButtonExited() {
        ControlScene.buttonExited(pane3AddEventButton, pane3AddEventButtonImage,
                pane3AddEventButtonLabel, "add_icon.png");
    }

    /**
     * Handles styling of the pane3AddEventButton when hovered.
     */
    @FXML
    private void pane3AddEventButtonHovered() {
        ControlScene.buttonHovered(pane3AddEventButton, pane3AddEventButtonImage,
                pane3AddEventButtonLabel, "add_icon_selected.png");
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
