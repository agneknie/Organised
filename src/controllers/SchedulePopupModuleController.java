package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultNavigation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
 * Controller for Module Information popup in the Schedule tab.
 * Used when user wishes to view information about modules, which are
 * present in the selected period.
 */
public class SchedulePopupModuleController extends DefaultNavigation implements Initializable {

    // Title label
    @FXML
    private Label titleLabel;

    // More button
    @FXML
    private Pane moreButton;
    @FXML
    private ImageView moreButtonImage;
    @FXML
    private Label moreButtonLabel;

    // Close button
    @FXML
    private Pane actionButton;
    @FXML
    private ImageView actionButtonImage;
    @FXML
    private Label actionButtonLabel;

    // Panes for module display
    @FXML
    private Pane pane1;
    @FXML
    private Pane pane2;
    @FXML
    private Pane pane3;
    @FXML
    private Pane pane4;
    @FXML
    private Pane pane5;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO Initialize
    }

    // Methods concerning button clicks

    /**
     * Method which closes the popup when actionButton (close button)
     * is clicked.
     * @param event event to get the stage from
     */
    @FXML
    void actionButtonClicked(MouseEvent event) {
        ControlScene.closeWindow(event);
    }

    @FXML
    void moreButtonClicked() {
        //TODO moreButtonClicked
    }

    // Methods for styling scene elements
    @FXML
    void actionButtonExited() {
        ControlScene.buttonExited(actionButton, actionButtonImage, actionButtonLabel, "close_icon.png");
    }

    @FXML
    void actionButtonHovered() {
        ControlScene.buttonHovered(actionButton, actionButtonImage, actionButtonLabel, "close_icon_black.png");
    }

    @FXML
    void moreButtonExited() {
        ControlScene.buttonExited(moreButton, moreButtonImage, moreButtonLabel, "more_icon.png");
    }

    @FXML
    void moreButtonHovered() {
        ControlScene.buttonHovered(moreButton, moreButtonImage, moreButtonLabel, "more_icon_selected.png");
    }
}
