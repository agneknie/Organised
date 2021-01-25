package controllers;

import controllers.utilities.MarksDefaultPopup;
import core.Assignment;
import core.Session;
import core.enums.MarksPopupType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for Assignment popup in the Marks tab.
 * Used when a Assignment is selected and added, edited or deleted.
 */
public class MarksPopupAssignmentController extends MarksDefaultPopup implements Initializable {
    // Label of the scene's title
    @FXML
    private Label titleLabel;

    // Name
    @FXML
    private TextField nameField;

    // Worth %
    @FXML
    private TextField worthField;

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
        // Sets up the action & delete buttons
        this.initializePopup();

        // Sets the text of the title
        titleLabel.setText(sceneType + " Assignment.");

        // Disable score & maxScore fields, until selection is made
        scoreField.setDisable(true);
        maxScoreField.setDisable(true);

        // Setups attempted button
        attemptedButtonClicked();

        // Setups the popup based on scene type
        if(sceneType == MarksPopupType.EDIT) setupEdit();
        else {
            // Sets the prompt text of worth field
            worthField.setPromptText("Percent left: " + Session.getMarksModuleSelected().percentWorthLeft());
        }
    }

    /**
     * Method which setups the popup if its type is edit
     */
    private void setupEdit(){
        // Sets the prompt text of worth field
        worthField.setPromptText("Percent left: " + (Session.getMarksModuleSelected().percentWorthLeft()+
                Session.getMarksAssignmentSelected().getPercentWorth()));

        // Fills the fields with Assignment data
        Assignment thisAssignment = Session.getMarksAssignmentSelected();
        nameField.setText(thisAssignment.getFullName());
        worthField.setText(Double.toString(thisAssignment.getPercentWorth()));
        if(thisAssignment.getScore() != -1 && thisAssignment.getMaxScore() != -1){
            scoreField.setText(Double.toString(thisAssignment.getScore()));
            maxScoreField.setText(Double.toString(thisAssignment.getMaxScore()));
            attemptedButton.setSelected(true);
        }
        else {
            attemptedButton.setSelected(false);
            attemptedButtonClicked();
        }
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
        Session.setMarksAssignmentSelected(null);
    }

    /**
     * Deletes the selected Module and sets up to forward the user to
     * the previous screen.
     */
    @FXML
    private void deleteButtonClicked() {
        // Deletes the Assignment
        loggedUser.deleteAssignment(Session.getMarksAssignmentSelected());

        // Sets the session variable
        Session.setMarksJustDeleted(true);

        // Closes the popup/stage
        Session.setMarksPopupType(null);
        ((Stage) deleteButton.getScene().getWindow()).close();
    }

    /**
     * Either Adds or Edits the Module based on the current popup scene type.
     */
    @FXML
    private void actionButtonClicked() {
        // If action button is 'Add'
        if(sceneType == MarksPopupType.ADD) addButtonClicked();

            // If action button is 'Edit'
        else editButtonClicked();
    }

    /**
     * Method which performs Assignment addition when action button is pressed.
     */
    private void addButtonClicked(){
        //TODO addButtonClicked()
    }

    /**
     * Method which performs Assignment editing when action button is pressed.
     */
    private void editButtonClicked(){
        //TODO editButtonClicked
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
