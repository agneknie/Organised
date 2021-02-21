package controllers;

import controllers.utilities.MarksDefaultPopup;
import core.Assignment;
import core.Session;
import core.enums.PopupType;
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
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
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
        if(sceneType == PopupType.EDIT) setupEdit();
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
        normaliseWrongField(nameField);
        normaliseWrongField(worthField);
        normaliseWrongField(scoreField);
        normaliseWrongField(maxScoreField);
    }

    /**
     * Method which performs Assignment addition when action button is pressed.
     */
    private void addButtonClicked(){
        // Normalises all fields in case they were marked as wrong before
        normaliseAllFields();

        // Variables for new Assignment construction
        String name = nameField.getText();
        double worth = -1;
        boolean attempted = attemptedButton.isSelected();
        double score = -1;
        double maxScore = -1;
        boolean valid = true;

        // Checks whether inputs are numbers where needed and not null otherwise
        if(name.isEmpty()){
            highlightWrongField(nameField);
            valid = false;
        }
        try{
            worth = Double.parseDouble(worthField.getText());
        } catch(Exception e){
            highlightWrongField(worthField);
            valid = false;
        }
        // If assignment scores are present, checks them as well
        if(attempted){
            try{
                score = Double.parseDouble(scoreField.getText());
            } catch(Exception e){
                highlightWrongField(scoreField);
                valid = false;
            }
            try{
                maxScore = Double.parseDouble(maxScoreField.getText());
            } catch(Exception e){
                highlightWrongField(maxScoreField);
                valid = false;
            }
        }

        // Checks if inputs are valid
        if(worth < 0 || worth > Session.getMarksModuleSelected().percentWorthLeft()){
            highlightWrongField(worthField);
            valid = false;
        }
        if(attempted){
            if(score < 0 || score > maxScore){
                highlightWrongField(scoreField);
                valid = false;
            }
            if(maxScore < 0){
                highlightWrongField(maxScoreField);
                valid = false;
            }
        }

        // If all inputs valid, adds the new Assignment
        if(valid){
            Assignment newAssignment;
            if(attempted) newAssignment = new Assignment(loggedUser.getId(),
                    Session.getMarksModuleSelected().getCode(), name, worth, maxScore, score);
            else newAssignment = new Assignment(loggedUser.getId(),
                    Session.getMarksModuleSelected().getCode(), name, worth, -1, -1);

            Session.getMarksModuleSelected().addAssignment(newAssignment);

            // Closes the popup/stage
            Session.setMarksPopupType(null);
            ((Stage) actionButton.getScene().getWindow()).close();
        }
    }

    /**
     * Method which performs Assignment editing when action button is pressed.
     */
    private void editButtonClicked(){
        // Normalises all fields in case they were marked as wrong before
        normaliseAllFields();

        // Variables for new Assignment construction
        String name = nameField.getText();
        double worth = -1;
        boolean attempted = attemptedButton.isSelected();
        double score = -1;
        double maxScore = -1;
        boolean valid = true;

        // Checks whether inputs are numbers where needed and not null otherwise
        if(name.isEmpty()){
            highlightWrongField(nameField);
            valid = false;
        }
        try{
            worth = Double.parseDouble(worthField.getText());
        } catch(Exception e){
            highlightWrongField(worthField);
            valid = false;
        }
        // If assignment scores are present, checks them as well
        if(attempted){
            try{
                score = Double.parseDouble(scoreField.getText());
            } catch(Exception e){
                highlightWrongField(scoreField);
                valid = false;
            }
            try{
                maxScore = Double.parseDouble(maxScoreField.getText());
            } catch(Exception e){
                highlightWrongField(maxScoreField);
                valid = false;
            }
        }

        // Checks if inputs are valid
        if(worth < 0 || worth > Session.getMarksModuleSelected().percentWorthLeft()+
                Session.getMarksAssignmentSelected().getPercentWorth()){
            highlightWrongField(worthField);
            valid = false;
        }
        if(attempted){
            if(score < 0 || score > maxScore){
                highlightWrongField(scoreField);
                valid = false;
            }
            if(maxScore < 0){
                highlightWrongField(maxScoreField);
                valid = false;
            }
        }

        // If all inputs valid, edits the Assignment
        if(valid){
            Assignment thisAssignment = Session.getMarksAssignmentSelected();
            // Sets the new values
            thisAssignment.setFullName(name);
            thisAssignment.setPercentWorth(worth);
            thisAssignment.setMaxScore(maxScore);
            thisAssignment.setScore(score);
            // Updates the Assignment in the database
            thisAssignment.updateAssignment();

            // Closes the popup/stage
            Session.setMarksPopupType(null);
            ((Stage) actionButton.getScene().getWindow()).close();
        }
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
