package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultButtons;
import core.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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
 * Class which handles the functionality of alert, which pops up when
 * the user tries to delete a Period.
 */
public class TimeDeletePeriodAlertController extends DefaultButtons implements Initializable {
    @FXML
    private Label alertMessage;
    @FXML
    private ImageView confirmButton;
    @FXML
    private ImageView discardButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alertMessage.setText("Do you really want to delete this Period?");
    }

    /**
     * Method which deletes the period if confirm button is clicked.
     */
    @FXML
    private void confirmButtonClicked(MouseEvent event){
        // Deletes the Period
        Session.getSession().deletePeriod(Session.getTimePeriodSelected());
        Session.setTimePeriodSelected(null);

        // Closes the popup
        closeClicked(event);
    }

    /**
     * Method which closes the popup and does not delete the period
     * if discard button is clicked.
     */
    @FXML
    private void discardButtonClicked(MouseEvent event){
        closeClicked(event);
    }

    // Methods concerning button styling
    /**
     * Method which changes styling of confirmation button when exited.
     */
    @FXML
    private void confirmButtonExited() {
        ControlScene.controlButtonEffect("tasks_icon.png", confirmButton);
    }

    /**
     * Method which changes styling of confirmation button when hovered.
     */
    @FXML
    private void confirmButtonHovered() {
        ControlScene.controlButtonEffect("tasks_icon_selected.png", confirmButton);
    }

    /**
     * Method which changes styling of discard button when exited.
     */
    @FXML
    private void discardButtonExited() {
        ControlScene.controlButtonEffect("close_icon_black.png", discardButton);
    }

    /**
     * Method which changes styling of discard button when hovered.
     */
    @FXML
    private void discardButtonHovered() {
        ControlScene.controlButtonEffect("close_icon.png", discardButton);
    }
}
