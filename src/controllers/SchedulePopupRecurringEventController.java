package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultNavigation;
import core.Day;
import core.Module;
import core.Week;
import core.enums.ScheduleTime;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
 * Controller for Event popup in the Schedule tab.
 * Used when a Recurring Event is added.
 */
public class SchedulePopupRecurringEventController extends DefaultNavigation implements Initializable {
    // Day of Week combo box
    @FXML
    private ComboBox<Day> dayOfWeekComboBox;

    // Associated module combo box
    @FXML
    private ComboBox<Module> associatedModuleComboBox;

    // Name field label
    @FXML
    private TextField nameField;

    // Start & End time combo boxes
    @FXML
    private ComboBox<ScheduleTime> startTimeComboBox;
    @FXML
    private ComboBox<ScheduleTime> endTimeComboBox;

    // Start & End week combo boxes
    @FXML
    private ComboBox<Week> startWeekComboBox;
    @FXML
    private ComboBox<Week> endWeekComboBox;

    // Description field text area
    @FXML
    private TextArea descriptionField;

    // Error message field label
    @FXML
    private Label errorMessageField;

    // Action/Add button
    @FXML
    private Pane actionButton;
    @FXML
    private ImageView actionButtonImage;
    @FXML
    private Label actionButtonLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO Initialize
    }

    /**
     * Method which tries to add a series of events (a recurring event)
     * to the selected period when clicked.
     */
    @FXML
    private void actionButtonClicked() {
        //TODO actionButtonClicked
    }

    // Methods concerning styling of elements
    /**
     * Changes the styling of action button (add button) when
     * it is hovered by user.
     */
    @FXML
    private void actionButtonExited() {
        ControlScene.buttonExited(actionButton, actionButtonImage, actionButtonLabel, "add_icon.png");
    }

    /**
     * Method which reverts the styling of action button (add button)
     * back to normal when button is exited/hover ended.
     */
    @FXML
    private void actionButtonHovered() {
        ControlScene.buttonHovered(actionButton, actionButtonImage, actionButtonLabel, "add_icon_selected.png");
    }
}
