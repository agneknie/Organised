package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultButtons;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
 * Controller for the popup in the Marks tab.
 * Used when a Year is selected and added, edited or deleted.
 */
public class MarksPopupYearController extends DefaultButtons implements Initializable {
    // Variable which determines whether scene is edit or add
    private static String sceneType = "Add";

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

    // Add/Edit button
    @FXML
    private Pane actionButton;
    @FXML
    private ImageView actionButtonImage;
    @FXML
    private Label actionButtonLabel;

    // Delete button
    @FXML
    private Pane deleteButton;
    @FXML
    private ImageView deleteButtonImage;
    @FXML
    private Label deleteButtonLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Sets the text of the title
        titleLabel.setText(sceneType + " Year.");

        // Sets the text and the image of action button (add/edit)
        actionButtonLabel.setText(sceneType);
        try {
            FileInputStream newImage = new FileInputStream("src/images/" + sceneType.toLowerCase() + "_icon.png");
            actionButtonImage.setImage(new Image(newImage));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteButtonClicked() {
        //TODO deleteButtonClicked
    }

    /**
     * Reverts button styling back to normal.
     */
    @FXML
    private void deleteButtonExited() {
        ControlScene.buttonExited(deleteButton, deleteButtonImage, deleteButtonLabel,
                "delete_icon.png");
    }

    /**
     * Changes the styling of button when hovered.
     */
    @FXML
    private void deleteButtonHovered() {
        ControlScene.buttonHovered(deleteButton, deleteButtonImage, deleteButtonLabel,
                "delete_icon_selected.png");
    }

    @FXML
    private void actionButtonClicked() {
        //TODO actionButtonClicked
    }

    /**
     * Reverts button styling back to normal.
     */
    @FXML
    private void actionButtonExited() {
        ControlScene.buttonExited(actionButton, actionButtonImage, actionButtonLabel,
                sceneType.toLowerCase()+"_icon.png");
    }

    /**
     * Changes the styling of button when hovered.
     */
    @FXML
    private void actionButtonHovered() {
        ControlScene.buttonHovered(actionButton, actionButtonImage, actionButtonLabel,
                sceneType.toLowerCase()+"_icon_selected.png");
    }

}
