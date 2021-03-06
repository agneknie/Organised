package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.MarksDefaultPopup;
import core.Module;
import core.enums.PopupType;
import core.enums.Semester;
import core.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
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
 * Controller for Module popup in the Marks tab.
 * Used when a Module is selected and added, edited or deleted.
 */
public class MarksPopupModuleController extends MarksDefaultPopup implements Initializable {
    // Label of the scene's title
    @FXML
    private Label titleLabel;

    // Module code
    @FXML
    private TextField moduleCodeField;

    // Module name
    @FXML
    private TextField moduleNameField;

    // Credits
    @FXML
    private TextField creditsField;

    // Semester
    @FXML
    private ComboBox<Semester> semesterComboBox;

    // Colour
    @FXML
    private ColorPicker colourPicker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Sets up the action & delete buttons
        this.initializePopup();

        // Sets the text of the title
        titleLabel.setText(sceneType + " Module.");

        // Populates the combo box with Semester values
        semesterComboBox.getItems().setAll(Semester.values());
        // Styles semester combo box text
        ControlScene.setupComboBoxStyle(semesterComboBox);

        // Setups the popup based on scene type
        if(sceneType == PopupType.EDIT) setupEdit();
        else {
            // Sets the prompt text of worth field
            creditsField.setPromptText("Credits left: " + Session.getMarksYearSelected().creditsLeft());
        }
    }

    /**
     * Method which setups the popup if ts type is Edit
     */
    private void setupEdit(){
        // Disables module code field
        moduleCodeField.setDisable(true);

        // Sets the prompt text of worth field
        creditsField.setPromptText("Credits left: " + (Session.getMarksYearSelected().creditsLeft() +
                Session.getMarksModuleSelected().getCredits()));

        // Fills the fields with Module data
        Module thisModule = Session.getMarksModuleSelected();
        moduleCodeField.setText(thisModule.getCode());
        moduleNameField.setText(thisModule.getFullName());
        creditsField.setText(Integer.toString(thisModule.getCredits()));
        semesterComboBox.setValue(thisModule.getSemester());
        colourPicker.setValue(Color.web(thisModule.getColourAsString()));
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
     * Deletes the selected Module and sets up to forward the user to
     * the previous screen.
     */
    @FXML
    private void deleteButtonClicked() {
        // Deletes the Module
        loggedUser.deleteModule(Session.getMarksModuleSelected());

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
        normaliseWrongField(moduleCodeField);
        normaliseWrongField(moduleNameField);
        normaliseWrongField(creditsField);
        normaliseWrongField(semesterComboBox);
    }

    /**
     * Method which performs Module addition when action button is pressed.
     */
    private void addButtonClicked(){
        // Normalises all fields in case they were marked as wrong before
        normaliseAllFields();

        // Variables for new Module construction
        String moduleCode = moduleCodeField.getText();
        String moduleName = moduleNameField.getText();
        int credits = -1;
        Semester semester = semesterComboBox.getValue();
        Color colour = colourPicker.getValue();
        boolean valid = true;

        // Checks whether inputs are numbers where needed & not null
        try {
            credits = Integer.parseInt(creditsField.getText());
        } catch (Exception e){
            highlightWrongField(creditsField);
            valid = false;
        }
        if(moduleCode.isEmpty()){
            highlightWrongField(moduleCodeField);
            valid = false;
        }
        if(moduleName.isEmpty()){
            highlightWrongField(moduleNameField);
            valid = false;
        }

        // Checks if inputs are valid
        if(!Module.moduleCodeAvailable(moduleCode, loggedUser)){
            highlightWrongField(moduleCodeField);
            valid = false;
        }
        if(credits < 0 || credits > Session.getMarksYearSelected().creditsLeft()){
            highlightWrongField(creditsField);
            valid = false;
        }
        if(semesterComboBox.getValue() == null){
            highlightWrongField(semesterComboBox);
            valid = false;
        }
        else semester = semesterComboBox.getValue();

        //If all inputs valid, adds the new Module
        if(valid){
            Module newModule = new Module(loggedUser.getId(), moduleCode, moduleName, credits, semester,
                    Session.getMarksYearSelected().getYearNumber(), colour);
            Session.getMarksYearSelected().addModule(newModule);

            // Closes the popup/stage
            Session.setMarksPopupType(null);
            ((Stage) actionButton.getScene().getWindow()).close();
        }
    }

    /**
     * Method which performs Module editing when action button is pressed.
     */
    private void editButtonClicked(){
        // Normalises all fields in case they were marked as wrong before
        normaliseAllFields();

        // Variables for new Module construction
        String moduleName = moduleNameField.getText();
        int credits = -1;
        Semester semester = semesterComboBox.getValue();
        Color colour = colourPicker.getValue();
        boolean valid = true;

        // Checks whether inputs are numbers where needed & not null
        try {
            credits = Integer.parseInt(creditsField.getText());
        }catch (Exception e){
            highlightWrongField(creditsField);
            valid = false;
        }
        if(moduleName.isEmpty()){
            highlightWrongField(moduleNameField);
            valid = false;
        }

        // Checks if inputs are valid
        if(credits < 0 || credits > Session.getMarksYearSelected().creditsLeft()+
                Session.getMarksModuleSelected().getCredits()){
            highlightWrongField(creditsField);
            valid = false;
        }
        if(semesterComboBox.getValue() == null){
            highlightWrongField(semesterComboBox);
            valid = false;
        }
        else semester = semesterComboBox.getValue();

        // If all inputs valid, updates the module
        if(valid){
            Module thisModule = Session.getMarksModuleSelected();
            // Sets the new values
            thisModule.setFullName(moduleName);
            thisModule.setCredits(credits);
            thisModule.setSemester(semester);
            thisModule.setColour(colour);
            // Updates the Module in database
            thisModule.updateModule();

            // Closes the popup/stage
            Session.setMarksPopupType(null);
            ((Stage) actionButton.getScene().getWindow()).close();
        }
    }
}
