package controllers.utilities;

import core.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Class for Marks view popups. Handles button styling
 * and scene setup.
 */
public abstract class MarksDefaultPopup extends DefaultButtons{
    // Add/Edit button
    @FXML
    protected Pane actionButton;
    @FXML
    protected ImageView actionButtonImage;
    @FXML
    protected Label actionButtonLabel;

    // Delete button
    @FXML
    protected Pane deleteButton;
    @FXML
    protected ImageView deleteButtonImage;
    @FXML
    protected Label deleteButtonLabel;

    // Variable to determine popup type
    private String sceneType = Session.getMarksPopupType();

    /**
     * Sets the text and the image of action button (add/edit).
     */
    protected void initializePopup(){
        // Sets up the action button
        actionButtonLabel.setText(sceneType);
        try {
            FileInputStream newImage = new FileInputStream("src/images/" + sceneType.toLowerCase() + "_icon.png");
            actionButtonImage.setImage(new Image(newImage));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Hides delete button if needed
        if(sceneType.equals("Add")) deleteButton.setVisible(false);
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
}
