package controllers;

import controllers.utilities.MarksDefaultPopup;
import core.Module;
import core.User;
import core.enums.MarksPopupType;
import core.enums.Semester;
import core.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
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

        // If "Edit" is selected, disables module code field
        if(sceneType == MarksPopupType.EDIT) moduleCodeField.setDisable(true);

        // Populates the combo box with Semester values
        semesterComboBox.getItems().setAll(Semester.values());
        // Styles semester combo box text
        semesterComboBox.setButtonCell(new ListCell(){
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                setFont(new Font("Arial", 16.0));
                // If nothing selected, styles like the prompt
                if(empty || item==null)
                    setStyle("-fx-text-fill: derive(-fx-control-inner-background,-30%)");
                // If something selected, styles accordingly
                else {
                    setStyle("-fx-text-fill: white");
                    setText(item.toString());
                }
            }
        });
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
        //TODO deleteButtonClicked
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
        }catch (Exception e){
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
        if(credits < 0){
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
        // TODO editButtonClicked
    }

}
