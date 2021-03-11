package controllers.utilities;

import core.Session;
import core.User;
import core.enums.PopupType;
import core.enums.Semester;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
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
    public PopupType sceneType = Session.getMarksPopupType();

    // Variable for storing user data
    public User loggedUser = Session.getSession();

    /**
     * Sets the text and the image of action button (add/edit).
     */
    protected void initializePopup(){
        // Sets up the action button
        actionButtonLabel.setText(sceneType.toString());

        Image newImage = new Image(MarksDefaultPopup.class.getResourceAsStream
                ("/"+sceneType.toString().toLowerCase() + "_icon.png"));
        actionButtonImage.setImage(newImage);

        // Hides delete button if needed
        if(sceneType == PopupType.ADD) deleteButton.setVisible(false);
    }

    /**
     * Listener for keyboard events.
     * If enter is pressed, action button is activated.
     * @param event used for identifying the key
     */
    @FXML
    private void keyPressed(KeyEvent event) {
        // Enter pressed
        if(event.getCode().toString().equals("ENTER")) actionButtonClicked();
    }

    /**
     * Action button which either Adds or Edits the Year, Module or Assignment.
     * Should be overridden by methods implementing this class.
     */
    @FXML
    public abstract void actionButtonClicked();

    /**
     * Reverts button styling back to normal.
     */
    @FXML
    private void actionButtonExited() {
        ControlScene.buttonExited(actionButton, actionButtonImage, actionButtonLabel,
                sceneType.toString().toLowerCase()+"_icon.png");
    }

    /**
     * Changes the styling of button when hovered.
     */
    @FXML
    private void actionButtonHovered() {
        ControlScene.buttonHovered(actionButton, actionButtonImage, actionButtonLabel,
                sceneType.toString().toLowerCase()+"_icon_selected.png");
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

    /**
     * Changes the styling of a text field if the input cannot be accepted.
     * Used when user adds a Year/Module/Assignment and the value is unacceptable.
     *
     * @param problematicField field to highlight as wrong
     */
    public void highlightWrongField(TextField problematicField){
        ControlScene.highlightWrongField(problematicField);
    }

    /**
     * Changes the styling of a combo box if the input cannot be accepted.
     * Used when user adds a Year/Module/Assignment and the value is unacceptable.
     *
     * @param problematicComboBox combo box to highlight as wrong
     */
    public void highlightWrongField(ComboBox<Semester> problematicComboBox){
        ControlScene.highlightWrongField(problematicComboBox);
    }

    /**
     * Method which reverts the styling of a text field back to normal, after a
     * wrong input was received.
     * Used when user adds a Year/Module/Assignment and the value is unacceptable.
     *
     * @param problematicField text field to un-highlight
     */
    public void normaliseWrongField(TextField problematicField){
        ControlScene.normaliseWrongField(problematicField);
    }

    /**
     * Method which reverts the styling of a combo box back to normal, after a
     * wrong input was received.
     * Used when user adds a Year/Module/Assignment and the value is unacceptable.
     *
     * @param problematicComboBox combo box to un-highlight
     */
    public void normaliseWrongField(ComboBox<Semester> problematicComboBox){
        ControlScene.normaliseWrongField(problematicComboBox);
    }
}
