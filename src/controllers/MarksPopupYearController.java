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
 * Controller for Year popup in the Marks tab.
 * Used when a Year is selected and added, edited or deleted.
 */
public class MarksPopupYearController extends MarksDefaultPopup implements Initializable {
    // Variable for determining the scene type: either Add or Edit
    private String sceneType = Session.getMarksPopupType();

    // Label of the scene's title
    @FXML
    private Label titleLabel;

    // Year Number
    @FXML
    private Label yearNumberLabel;
    @FXML
    private TextField yearNumberField;

    // Credits
    @FXML
    private Label creditsLabel;
    @FXML
    private TextField creditsField;

    // Weight (%)
    @FXML
    private Label weightLabel;
    @FXML
    private TextField weightField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Sets the text of the title
        titleLabel.setText(sceneType + " Year.");

        // Sets up the action & delete buttons
        this.initializePopup();

        // If "Edit" is selected, disables year number field
        if(sceneType.equals("Edit")) yearNumberField.setDisable(true);

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
