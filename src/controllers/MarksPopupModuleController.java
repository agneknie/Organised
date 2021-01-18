package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultButtons;
import controllers.utilities.MarksDefaultPopup;
import core.Semester;
import core.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

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
        // Sets the text of the title
        titleLabel.setText(sceneType + " Module.");

        // Sets up the action & delete buttons
        this.initializePopup();

        // If "Edit" is selected, disables module code field
        if(sceneType.equals("Edit")) moduleCodeField.setDisable(true);

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

    @FXML
    private void deleteButtonClicked() {
        //TODO deleteButtonClicked
    }

    @FXML
    private void actionButtonClicked() {
        //TODO actionButtonClicked
    }

}
