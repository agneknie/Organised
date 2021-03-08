package controllers;

import controllers.utilities.DefaultNavigation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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

    // Text areas for information display
    @FXML
    private TextArea userGuideText;
    @FXML
    private TextArea behindTheScenesText;
    @FXML
    private TextArea copyrightResourcesText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    // Methods handling button clicks
    @FXML
    private void behindTheScenesButtonClicked() {
        //TODO behindTheScenesButtonClicked
    }

    @FXML
    private void copyrightResourcesButtonClicked() {
        //TODO copyrightResourcesButtonClicked
    }

    @FXML
    private void userGuideButtonClicked() {
        //TODO userGuideButtonClicked
    }

    // Methods styling the buttons
    @FXML
    private void behindTheScenesButtonExited() {

    }

    @FXML
    private void behindTheScenesButtonHovered() {

    }

    @FXML
    private void copyrightResourcesButtonExited() {

    }

    @FXML
    private void copyrightResourcesButtonHovered() {

    }

    @FXML
    private void userGuideButtonExited() {

    }

    @FXML
    private void userGuideButtonHovered() {

    }
}
