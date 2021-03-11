package controllers;

import controllers.utilities.DefaultNavigation;
import core.Session;
import core.User;
import core.enums.TimeOfDay;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
 * Class which handles Profile Tab functionality and UI.
 */
public class ProfileController extends DefaultNavigation implements Initializable {

    // Elements based on time
    @FXML
    private Label greetingLabel;
    @FXML
    private ImageView bannerImage;

    // Marks
    @FXML
    private Label numberOfAssignments;
    @FXML
    private Label numberOfModules;

    // Time
    @FXML
    private Label timeSpentOrganised;
    @FXML
    private Label busiestWeek;

    // Schedule
    @FXML
    private Label numberOfEvents;
    @FXML
    private Label busiestPeriod;

    // Tasks
    @FXML
    private Label numberOfTasks;
    @FXML
    private Label tasksCompleted;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Sets up the time of day based elements
        setupGreetings();

        // Sets up the stats of the user
        setupStatistics();
    }

    /**
     * Method which sets up the user greeting and displayed image based on time
     * of day.
     */
    private void setupGreetings(){
        // Gets time of day
        TimeOfDay timeOfDay = TimeOfDay.getTimeOfDay();

        // Sets up greeting label
        String userName = Session.getSession().getForename();
        switch (Objects.requireNonNull(timeOfDay)){
            case MORNING:
                greetingLabel.setText("Good Morning "+userName+".");
                break;
            case AFTERNOON:
                greetingLabel.setText("Good Afternoon "+userName+".");
                break;
            default:
                greetingLabel.setText("Good Evening "+userName+".");
        }

        // Sets up banner image
        String imageName = timeOfDay.toString().toLowerCase()+"-banner.png";

        Image newImage = new Image(ProfileController.class.getResourceAsStream("/"+imageName));
        bannerImage.setImage(newImage);
    }

    /**
     * Method which setups the statistics of the user.
     */
    private void setupStatistics(){
        // Gets the logged user
        User user = Session.getSession();

        // Marks section
        numberOfAssignments.setText(Integer.toString(user.getAssignmentNumber()));
        numberOfModules.setText(Integer.toString(user.getModuleNumber()));

        // Time section
        timeSpentOrganised.setText(user.getTimeSpentOrganised());
        busiestWeek.setText(user.getBusiestWeek());

        // Schedule section
        numberOfEvents.setText(Integer.toString(user.getEventsNumber()));
        busiestPeriod.setText(user.getBusiestPeriod());

        // Tasks section
        numberOfTasks.setText(Integer.toString(user.getTasksNumber()));
        tasksCompleted.setText(Integer.toString(user.getCompletedTasksNumber()));
    }
}
