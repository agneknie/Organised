package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultNavigation;
import core.Session;
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
 * Class which handles About Tab functionality and UI.
 */
public class AboutController extends DefaultNavigation implements Initializable {
    @FXML
    private Label topicNameLabel;

    // User Guide Button
    @FXML
    private Pane userGuideButton;
    @FXML
    private ImageView userGuideButtonImage;
    @FXML
    private Label userGuideButtonLabel;

    // Behind the Scenes Button
    @FXML
    private Pane behindTheScenesButton;
    @FXML
    private ImageView behindTheScenesButtonImage;
    @FXML
    private Label behindTheScenesButtonLabel;

    // Copyright & Resources Button
    @FXML
    private Pane copyrightResourcesButton;
    @FXML
    private ImageView copyrightResourcesButtonImage;
    @FXML
    private Label copyrightResourcesButtonLabel;

    // Panes for information display
    @FXML
    private Pane userGuide;
    @FXML
    private Pane behindTheScenes;
    @FXML
    private Pane copyrightResources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Default selection is user guide button
        userGuideButtonClicked();
    }

    // Methods handling button clicks
    /**
     * Method which displays the pane and the label which corresponds
     * with the behind the scenes selection.
     */
    @FXML
    private void behindTheScenesButtonClicked() {
        topicNameLabel.setText("Behind the Scenes.");
        userGuide.setVisible(false);
        behindTheScenes.setVisible(true);
        copyrightResources.setVisible(false);
    }

    /**
     * Method which displays the pane and the label which corresponds
     * with the copyright & resources selection.
     */
    @FXML
    private void copyrightResourcesButtonClicked() {
        topicNameLabel.setText("Copyright.");
        userGuide.setVisible(false);
        behindTheScenes.setVisible(false);
        copyrightResources.setVisible(true);
    }

    /**
     * Method which displays the pane and the label which corresponds
     * with the user guide selection.
     */
    @FXML
    private void userGuideButtonClicked() {
        topicNameLabel.setText("User Guide.");
        userGuide.setVisible(true);
        behindTheScenes.setVisible(false);
        copyrightResources.setVisible(false);
    }

    // Methods for external link clicks
    /**
     * Method which forwards the user to the browser when github link is clicked.
     */
    @FXML
    private void githubLinkClicked(){
        Session.getHostServices().showDocument("https://github.com/agneknie/Organised.");
    }

    /**
     * Method which forwards the user to the browser when student link is clicked.
     */
    @FXML
    private void studentLinkClicked(){
        Session.getHostServices().showDocument("https://linkedin.com/in/agne-knietaite");
    }

    /**
     * Method which forwards the user to the browser when material link is clicked.
     */
    @FXML
    private void materialLinkClicked(){
        Session.getHostServices().showDocument("https://material.io/resources/icons/");
    }

    /**
     * Method which forwards the user to the browser when license link is clicked.
     */
    @FXML
    private void licenseLinkClicked(){
        Session.getHostServices().showDocument("https://github.com/agneknie/Organised./blob/master/LICENSE");
    }

    // Methods styling the buttons
    /**
     * Changes the styling of behindTheScenesButton when hover ends/
     * button exited.
     */
    @FXML
    private void behindTheScenesButtonExited() {
        ControlScene.buttonExited(behindTheScenesButton, behindTheScenesButtonImage, behindTheScenesButtonLabel,
                "door_icon.png");
    }

    /**
     * Changes the styling of behindTheScenesButton when hovered.
     */
    @FXML
    private void behindTheScenesButtonHovered() {
        ControlScene.buttonHovered(behindTheScenesButton, behindTheScenesButtonImage, behindTheScenesButtonLabel,
                "door_icon_selected.png");
    }

    /**
     * Changes the styling of copyrightResourcesButton when hover ends/
     * button exited.
     */
    @FXML
    private void copyrightResourcesButtonExited() {
        ControlScene.buttonExited(copyrightResourcesButton, copyrightResourcesButtonImage, copyrightResourcesButtonLabel,
                "copyright_icon.png");
    }

    /**
     * Changes the styling of copyrightResourcesButton when hovered.
     */
    @FXML
    private void copyrightResourcesButtonHovered() {
        ControlScene.buttonHovered(copyrightResourcesButton, copyrightResourcesButtonImage, copyrightResourcesButtonLabel,
                "copyright_icon_selected.png");
    }

    /**
     * Changes the styling of userGuideButton when hover ends/
     * button exited.
     */
    @FXML
    private void userGuideButtonExited() {
        ControlScene.buttonExited(userGuideButton, userGuideButtonImage, userGuideButtonLabel,
                "user_icon.png");
    }

    /**
     * Changes the styling of userGuideButton when hovered.
     */
    @FXML
    private void userGuideButtonHovered() {
        ControlScene.buttonHovered(userGuideButton, userGuideButtonImage, userGuideButtonLabel,
                "user_icon_selected.png");
    }
}
