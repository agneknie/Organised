package controllers;

import controllers.utilities.MarksDefaultPopup;
import core.Session;
import core.User;
import core.Year;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
    private TextField yearNumberField;

    // Credits
    @FXML
    private TextField creditsField;

    // Worth (%)
    @FXML
    private TextField worthField;

    // Variables for storing user data
    private User loggedUser = Session.getSession();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Sets the text of the title
        titleLabel.setText(sceneType + " Year.");

        // Sets up the action & delete buttons
        this.initializePopup();

        // If "Edit" is selected, disables year number field
        if(sceneType.equals("Edit")) yearNumberField.setDisable(true);

        // Sets the prompt text of worth field
        worthField.setPromptText("Percent left: " + Year.percentWorthLeft(loggedUser) + "%");

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
        // If action button is 'Add'
        if(sceneType.equals("Add")) addButtonClicked();

        // If action button is 'Edit'
        else editButtonClicked();
    }

    /**
     * Normalises all text field borders in case they were highlighted
     * as wrong previously.
     */
    private void normaliseAllFields(){
        normaliseWrongField(yearNumberField);
        normaliseWrongField(creditsField);
        normaliseWrongField(worthField);
    }

    /**
     * Method which performs Year addition when action button is pressed.
     */
    private void addButtonClicked(){
        // Normalises all fields in case they were marked as wrong before
        normaliseAllFields();

        // Variables for new Year construction
        int yearNumber = -1;
        int credits = -1;
        double percentWorth = -1;
        boolean valid = true;

        // Checks whether inputs are numbers/not null
        try {
            yearNumber = Integer.valueOf(yearNumberField.getText());
        }catch(Exception e){
            highlightWrongField(yearNumberField);
            valid = false;
        }
        try{
            credits = Integer.valueOf(creditsField.getText());
        }catch (Exception e){
            highlightWrongField(creditsField);
            valid = false;
        }
        try{
            percentWorth = Double.valueOf(worthField.getText());
        }catch (Exception e){
            highlightWrongField(worthField);
            valid = false;
        }

        // Checks if inputs are valid
        if(yearNumber < 0 || !Year.yearNumberAvailable(yearNumber, loggedUser)){
            highlightWrongField(yearNumberField);
            valid = false;
        }
        if(credits < 0){
            highlightWrongField(creditsField);
            valid = false;
        }
        if(percentWorth < 0 || percentWorth > Year.percentWorthLeft(loggedUser)){
            highlightWrongField(worthField);
            valid = false;
        }

        // If all inputs valid, adds the new Year
        if(valid){
            Year newYear = new Year(loggedUser.getId(), yearNumber, credits, percentWorth);
            loggedUser.addYear(newYear);

            // Closes the popup/stage
            Session.setMarksPopupType(null);
            ((Stage) actionButton.getScene().getWindow()).close();
        }
    }

    /**
     * Method which performs Year editing when action button is pressed.
     */
    private void editButtonClicked(){
        // TODO editButtonClicked
    }

}
