package controllers;

import controllers.utilities.MarksDefaultPopup;
import core.Session;
import core.enums.MarksPopupType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for Assignment popup in the Marks tab.
 * Used when a Assignment is selected and added, edited or deleted.
 */
public class MarksPopupAssignmentController extends MarksDefaultPopup implements Initializable {
    // Variable for determining the scene type: either Add or Edit
    private final MarksPopupType sceneType = Session.getMarksPopupType();

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
    @FXML
    private ToggleButton attemptedButton;

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

        // Disable score & maxScore fields, until selection is made
        scoreField.setDisable(true);
        maxScoreField.setDisable(true);
    }

    /**
     * Calls default method.
     * Cleans session variable responsible for popup type.
     * @param event used for getting the scene
     */
    @Override
    public void closeClicked(MouseEvent event) {
        super.closeClicked(event);
        Session.setMarksPopupType(null);
    }

    @FXML
    private void deleteButtonClicked() {
        //TODO deleteButtonClicked
    }

    @FXML
    private void actionButtonClicked() {
        //TODO actionButtonClicked
    }

    /**
     * Changes the styling of attemptedButton based on user choice.
     * Disables score & maxScore fields if needed.
     */
    @FXML
    private void attemptedButtonClicked(){
        // Sets button properties depending on selection
        String colour;
        String text;
        if(attemptedButton.isSelected()){
            colour = "#60A572;";
            text = "Yes";
        }
        else {
            colour ="#C75450;";
            text = "No";
        }

        // Sets the button style (colours the border)
        String firstHalfStyles = "-fx-background-color: none; -fx-text-fill: white; " +
                "-fx-border-style: solid; -fx-border-color: ";
        String secondHalfStyles = " -fx-border-radius: 10; -fx-border-width: 3";
        attemptedButton.setStyle(firstHalfStyles+colour+secondHalfStyles);

        // Sets button text (Yes/No)
        attemptedButton.setText(text);

        // Disables the buttons, if needed
        scoreField.setDisable(!attemptedButton.isSelected());
        maxScoreField.setDisable(!attemptedButton.isSelected());
    }
}
