package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultNavigation;
import core.Period;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

import java.util.List;

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
public class TimeController extends DefaultNavigation {

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
    @FXML
    private Pane navigationPane1;
    @FXML
    private Label navigationPane1Year;
    @FXML
    private Label navigationPane1Period;

    // Navigation pane 2
    @FXML
    private Pane navigationPane2;
    @FXML
    private Label navigationPane2Year;
    @FXML
    private Label navigationPane2Period;

    // Navigation pane 3
    @FXML
    private Pane navigationPane3;
    @FXML
    private Label navigationPane3Year;
    @FXML
    private Label navigationPane3Period;

    // Navigation pane 4
    @FXML
    private Pane navigationPane4;
    @FXML
    private Label navigationPane4Year;
    @FXML
    private Label navigationPane4Period;

    /**
     * Method which brings a popup to add a Period if action button
     * (period addition button) is pressed.
     */
    @FXML
    private void actionButtonClicked(){
        // TODO add Period actionButton clicked
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
