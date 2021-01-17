package controllers;

import controllers.utilities.MarksDefaultPopup;
import core.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for Assignment popup in the Marks tab.
 * Used when a Assignment is selected and added, edited or deleted.
 */
public class MarksPopupAssignmentController extends MarksDefaultPopup implements Initializable {
    // Variable for determining the scene type: either Add or Edit
    private String sceneType = Session.getMarksPopupType();

    // Label of the scene's title
    @FXML
    private Label titleLabel;

    // Name
    @FXML
    private TextField nameField;

    // Worth %
    @FXML
    private TextField weightField;

    // Attempted
    //TODO figure out what to do with Attempted?

    // Score
    @FXML
    private TextField scoreField;

    // Max Score
    @FXML
    private TextField maxScoreField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Sets the text of the title
        titleLabel.setText(sceneType + " Assignment.");

        // Sets up the action & delete buttons
        this.initializePopup();
    }

    @FXML
    private void deleteButtonClicked() {
        //TODO deleteButtonClicked
    }

    @FXML
    private void actionButtonClicked() {
        //TODO actionButtonClicked
    }

}
