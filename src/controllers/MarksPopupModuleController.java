package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultButtons;
import controllers.utilities.MarksDefaultPopup;
import core.Semester;
import core.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for Module popup in the Marks tab.
 * Used when a Module is selected and added, edited or deleted.
 */
public class MarksPopupModuleController extends MarksDefaultPopup implements Initializable {
    // Variable for determining the scene type: either Add or Edit
    private String sceneType = Session.getMarksPopupType();

    // Label of the scene's title
    @FXML
    private Label titleLabel;

    // Module code
    @FXML
    private Label moduleCodeLabel;
    @FXML
    private TextField moduleCodeField;

    // Module name
    @FXML
    private Label moduleNameLabel;
    @FXML
    private TextField moduleNameField;

    // Credits
    @FXML
    private Label creditsLabel;
    @FXML
    private TextField creditsField;

    // Semester
    @FXML
    private Label semesterLabel;
    @FXML
    private ComboBox<Semester> semesterComboBox;

    // Colour
    @FXML
    private Label colourLabel;
    @FXML
    private ColorPicker colourPicker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Sets the text of the title
        titleLabel.setText(sceneType + " Module.");

        // Sets up the action & delete buttons
        this.initializePopup();

        // If "Edit" is selected, disables module code field
        if(sceneType.equals("Edit")) moduleCodeField.setDisable(true);

        // Populates the combo box with Semester values
        semesterComboBox.getItems().setAll(Semester.values());
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
