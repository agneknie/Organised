package controllers;

import controllers.utilities.DefaultNavigation;
import core.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

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
