package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultNavigation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

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
 * Class which handles Tasks Tab functionality and UI.
 */
public class TasksController extends DefaultNavigation implements Initializable {

    // Go to today button
    @FXML
    private Pane goToTodayButton;
    @FXML
    private ImageView goToTodayButtonImage;
    @FXML
    private Label goToTodayButtonLabel;

    // Navigation arrows
    @FXML
    private ImageView goRightButton;
    @FXML
    private ImageView goLeftButton;

    // Navigation Pane 1
    @FXML
    private Pane pane1;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO Initialize
    }

    // Methods responsible for navigation between panes displaying periods
    /**
     * Method which handles left navigation arrow clicked.
     * Shows previous period
     */
    @FXML
    private void goLeftClicked() {
        //TODO goLeftClicked
    }

    /**
     * Method which handles right navigation arrow clicked.
     * Shows next period.
     */
    @FXML
    private void goRightClicked() {
        //TODO goRightClicked
    }

    // Methods responsible for handling button clicks
    /**
     * Method which goes to the schedule of the week, which has today's date.
     * If there are more than one "todays" goes to the first one.
     * If no today is present among the periods, displays error message.
     */
    @FXML
    private void goToTodayButtonClicked() {
        //TODO goToTodayButtonClicked
    }

    /**
     * Method which opens recurring task addition popup for
     * period, which is described by pane1.
     */
    @FXML
    private void pane1AddTaskButtonClicked() {
        //TODO pane1AddTaskButtonClicked
    }

    /**
     * Method which opens recurring task addition popup for
     * period, which is described by pane2.
     */
    @FXML
    private void pane2AddTaskButtonClicked() {
        //TODO pane2AddTaskButtonClicked
    }

    /**
     * Method which opens recurring task addition popup for
     * period, which is described by pane3.
     */
    @FXML
    private void pane3AddTaskButtonClicked() {
        //TODO pane3AddTaskButtonClicked
    }

    /**
     * Method which changes the scene to the period, which is
     * specified by pane1.
     */
    @FXML
    private void pane1GoToPeriodButtonClicked() {
        //TODO pane1GoToPeriodButtonClicked
    }

    /**
     * Method which changes the scene to the period, which is
     * specified by pane2.
     */
    @FXML
    private void pane2GoToPeriodButtonClicked() {
        //TODO pane2GoToPeriodButtonClicked
    }

    /**
     * Method which changes the scene to the period, which is
     * specified by pane3.
     */
    @FXML
    private void pane3GoToPeriodButtonClicked() {
        //TODO pane3GoToPeriodButtonClicked
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
