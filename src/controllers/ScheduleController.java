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
 * Class which handles Schedule Tab functionality and UI.
 */
public class ScheduleController extends DefaultNavigation implements Initializable {

    // Top panel fields
    @FXML
    private Label todayLabel;
    @FXML
    private Label timeLabel;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO Initialize
    }


    // Navigation buttons
    /**
     * Method which handles left navigation arrow clicked.
     */
    @FXML
    void goLeftClicked() {
        //TODO goLeftClicked
    }

    /**
     * Method which handles right navigation arrow clicked.
     */
    @FXML
    void goRightClicked() {
        //TODO goRightClicked
    }

    // Methods handling button functionality
    /**
     * Method which goes to the schedule of the week, which has today's date.
     * If there are more than one "todays" goes to the first one.
     * If no today is present among the periods, displays error message.
     */
    @FXML
    void goToTodayButtonClicked() {
        //TODO goToTodayButtonClicked
    }

    /**
     * Method which opens recurring event addition popup for
     * period, which is described by pane1.
     */
    @FXML
    void pane1AddEventButtonClicked() {
        //TODO pane1AddEventButtonClicked
    }

    /**
     * Method which changes the scene to the period, which is
     * specified by pane1.
     */
    @FXML
    void pane1GoToPeriodButtonClicked() {
        //TODO pane1GoToPeriodButtonClicked
    }

    /**
     * Method which opens recurring event addition popup for
     * period, which is described by pane2.
     */
    @FXML
    void pane2AddEventButtonClicked() {
        //TODO pane2AddEventButtonClicked
    }

    /**
     * Method which changes the scene to the period, which is
     * specified by pane2.
     */
    @FXML
    void pane2GoToPeriodButtonClicked() {
        //TODO pane2GoToPeriodButtonClicked
    }

    /**
     * Method which opens recurring event addition popup for
     * period, which is described by pane3.
     */
    @FXML
    void pane3AddEventButtonClicked() {
        //TODO pane3AddEventButtonClicked
    }

    /**
     * Method which changes the scene to the period, which is
     * specified by pane3.
     */
    @FXML
    void pane3GoToPeriodButtonClicked() {
        //TODO pane3GoToPeriodButtonClicked
    }

    // Methods concerning styling of scene
    /**
     * Reverts goLeft button styling back to usual
     * when hover ends/button is exited.
     */
    @FXML
    void goLeftExited() {
        ControlScene.controlButtonEffect("next_element_left.png", goLeftButton);
    }

    /**
     * Handles styling of the goLeftButton when hovered.
     */
    @FXML
    void goLeftHovered() {
        ControlScene.controlButtonEffect("next_element_left_selected.png", goLeftButton);
    }

    /**
     * Reverts goRight button styling back to usual
     * when hover ends/button is exited.
     */
    @FXML
    void goRightExited() {
        ControlScene.controlButtonEffect("next_element_right.png", goRightButton);
    }

    /**
     * Handles styling of the goRightButton when hovered.
     */
    @FXML
    void goRightHovered() {
        ControlScene.controlButtonEffect("next_element_right_selected.png", goRightButton);
    }

    /**
     * Reverts goToTodayButton button styling back to usual
     * when hover ends/button is exited.
     */
    @FXML
    void goToTodayButtonExited() {
        ControlScene.buttonExited(goToTodayButton, goToTodayButtonImage,
                goToTodayButtonLabel, "go_to_icon.png");
    }

    /**
     * Handles styling of the goToTodayButton when hovered.
     */
    @FXML
    void goToTodayButtonHovered() {
        ControlScene.buttonHovered(goToTodayButton, goToTodayButtonImage,
                goToTodayButtonLabel, "go_to_icon_selected.png");
    }

    /**
     * Reverts pane1AddEventButton button styling back to usual
     * when hover ends/button is exited.
     */
    @FXML
    void pane1AddEventButtonExited() {
        ControlScene.buttonExited(pane1AddEventButton, pane1AddEventButtonImage,
                pane1AddEventButtonLabel, "add_icon.png");
    }

    /**
     * Handles styling of the pane1AddEventButton when hovered.
     */
    @FXML
    void pane1AddEventButtonHovered() {
        ControlScene.buttonHovered(pane1AddEventButton, pane1AddEventButtonImage,
                pane1AddEventButtonLabel, "add_icon_selected.png");
    }

    /**
     * Reverts pane1GoToPeriodButton button styling back to usual
     * when hover ends/button is exited.
     */
    @FXML
    void pane1GoToPeriodButtonExited() {
        ControlScene.buttonExited(pane1GoToPeriodButton, pane1GoToPeriodButtonImage,
                pane1GoToPeriodButtonLabel, "go_to_icon.png");
    }

    /**
     * Handles styling of the pane1GoToPeriodButton when hovered.
     */
    @FXML
    void pane1GoToPeriodButtonHovered() {
        ControlScene.buttonHovered(pane1GoToPeriodButton, pane1GoToPeriodButtonImage,
                pane1GoToPeriodButtonLabel, "go_to_icon_selected.png");
    }

    /**
     * Reverts pane2AddEventButton button styling back to usual
     * when hover ends/button is exited.
     */
    @FXML
    void pane2AddEventButtonExited() {
        ControlScene.buttonExited(pane2AddEventButton, pane2AddEventButtonImage,
                pane2AddEventButtonLabel, "add_icon.png");
    }

    /**
     * Handles styling of the pane2AddEventButton when hovered.
     */
    @FXML
    void pane2AddEventButtonHovered() {
        ControlScene.buttonHovered(pane2AddEventButton, pane2AddEventButtonImage,
                pane2AddEventButtonLabel, "add_icon_selected.png");
    }

    /**
     * Reverts pane2GoToPeriodButton button styling back to usual
     * when hover ends/button is exited.
     */
    @FXML
    void pane2GoToPeriodButtonExited() {
        ControlScene.buttonExited(pane2GoToPeriodButton, pane2GoToPeriodButtonImage,
                pane2GoToPeriodButtonLabel, "go_to_icon.png");
    }

    /**
     * Handles styling of the pane2GoToPeriodButton when hovered.
     */
    @FXML
    void pane2GoToPeriodButtonHovered() {
        ControlScene.buttonHovered(pane2GoToPeriodButton, pane2GoToPeriodButtonImage,
                pane2GoToPeriodButtonLabel, "go_to_icon_selected.png");
    }

    /**
     * Reverts pane3AddEventButton button styling back to usual
     * when hover ends/button is exited.
     */
    @FXML
    void pane3AddEventButtonExited() {
        ControlScene.buttonExited(pane3AddEventButton, pane3AddEventButtonImage,
                pane3AddEventButtonLabel, "add_icon.png");
    }

    /**
     * Handles styling of the pane3AddEventButton when hovered.
     */
    @FXML
    void pane3AddEventButtonHovered() {
        ControlScene.buttonHovered(pane3AddEventButton, pane3AddEventButtonImage,
                pane3AddEventButtonLabel, "add_icon_selected.png");
    }

    /**
     * Reverts pane3GoToPeriodButton button styling back to usual
     * when hover ends/button is exited.
     */
    @FXML
    void pane3GoToPeriodButtonExited() {
        ControlScene.buttonExited(pane3GoToPeriodButton, pane3GoToPeriodButtonImage,
                pane3GoToPeriodButtonLabel, "go_to_icon.png");
    }

    /**
     * Handles styling of the pane3GoToPeriodButton when hovered.
     */
    @FXML
    void pane3GoToPeriodButtonHovered() {
        ControlScene.buttonHovered(pane3GoToPeriodButton, pane3GoToPeriodButtonImage,
                pane3GoToPeriodButtonLabel, "go_to_icon_selected.png");
    }
}
