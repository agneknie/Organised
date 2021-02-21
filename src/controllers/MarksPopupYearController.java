package controllers;

import controllers.utilities.MarksDefaultPopup;
import core.Session;
import core.Year;
import core.enums.PopupType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
 * Controller for Year popup in the Marks tab.
 * Used when a Year is selected and added, edited or deleted.
 */
public class MarksPopupYearController extends MarksDefaultPopup implements Initializable {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Sets up the action & delete buttons
        this.initializePopup();

        // Sets the text of the title
        titleLabel.setText(sceneType + " Year.");

        // Sets up the scene based on its type
        if (sceneType == PopupType.EDIT) setupEdit();
        else{
            // Sets the prompt text of worth field
            worthField.setPromptText("Percent left: " + Year.percentWorthLeft(loggedUser) + "%");
        }
    }

    /**
     * Method which setups the popup if its type is Edit.
     */
    private void setupEdit(){
        // Disables year number field
        yearNumberField.setDisable(true);

        // Sets the prompt text of worth field
        worthField.setPromptText("Percent left: " + (Year.percentWorthLeft(loggedUser)+
                Session.getMarksYearSelected().getPercentWorth())+ "%");

        // Fills the fields with Year data
        Year thisYear = Session.getMarksYearSelected();
        yearNumberField.setText(Integer.toString(thisYear.getYearNumber()));
        creditsField.setText(Integer.toString(thisYear.getCredits()));
        worthField.setText(Double.toString(thisYear.getPercentWorth()));
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

    /**
     * Deletes the selected Year and sets up to forward the user to
     * the previous screen.
     */
    @FXML
    private void deleteButtonClicked() {
        // Deletes the Year
        loggedUser.deleteYear(Session.getMarksYearSelected());

        // Sets the session variable
        Session.setMarksJustDeleted(true);

        // Closes the popup/stage
        Session.setMarksPopupType(null);
        ((Stage) deleteButton.getScene().getWindow()).close();
    }

    /**
     * Either Adds or Edits the Year based on the current popup scene type.
     */
    @Override
    public void actionButtonClicked() {
        // If action button is 'Add'
        if(sceneType == PopupType.ADD) addButtonClicked();

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
            yearNumber = Integer.parseInt(yearNumberField.getText());
        } catch(Exception e){
            highlightWrongField(yearNumberField);
            valid = false;
        }
        try{
            credits = Integer.parseInt(creditsField.getText());
        } catch (Exception e){
            highlightWrongField(creditsField);
            valid = false;
        }
        try{
            percentWorth = Double.parseDouble(worthField.getText());
        } catch (Exception e){
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
        // Normalises all fields in case they were marked as wrong before
        normaliseAllFields();

        // Variables for new Year construction
        int credits = -1;
        double percentWorth = -1;
        boolean valid = true;

        // Checks whether inputs are numbers/not null
        try{
            credits = Integer.parseInt(creditsField.getText());
        } catch (Exception e){
            highlightWrongField(creditsField);
            valid = false;
        }
        try{
            percentWorth = Double.parseDouble(worthField.getText());
        } catch (Exception e){
            highlightWrongField(worthField);
            valid = false;
        }

        // Checks if inputs are valid
        if(credits < 0){
            highlightWrongField(creditsField);
            valid = false;
        }
        if(percentWorth < 0 || percentWorth > Year.percentWorthLeft(loggedUser)+
                Session.getMarksYearSelected().getPercentWorth()){
            highlightWrongField(worthField);
            valid = false;
        }

        // If all inputs are valid, updates the Year
        if(valid){
            Year thisYear = Session.getMarksYearSelected();
            // Sets the new values
            thisYear.setCredits(credits);
            thisYear.setPercentWorth(percentWorth);
            // Updates Year in database
            thisYear.updateYear();

            // Closes the popup/stage
            Session.setMarksPopupType(null);
            ((Stage) actionButton.getScene().getWindow()).close();
        }
    }
}
