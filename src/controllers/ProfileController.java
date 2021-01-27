package controllers;

import controllers.utilities.DefaultNavigation;
import core.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

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
 * Class which handles Profile Tab functionality and UI.
 */
public class ProfileController extends DefaultNavigation implements Initializable {
    // Labels
    @FXML
    private Label userForenameLabel;

    /**
     * Implemented method for initialization tasks.
     *
     * @param location parameter not used.
     * @param resources parameter not used.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userForenameLabel.setText(Session.getSession().getForename());
    }
}
