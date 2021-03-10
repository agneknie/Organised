package controllers.utilities;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
 * Class which captures main methods, which control a scene.
 */
public class ControlScene {
    /**
     * Method which closes the window when close button is clicked.
     *
     * @param event used for identifying the stage
     */
    @FXML
    public static void closeWindow(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    /**
     * Method which minimises the window when minimize button is clicked.
     *
     * @param event used for identifying the stage
     */
    @FXML
    public static void minimizeWindow(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Method which changes the colour of control button when hovered.
     * Applied to minimize, close, go back and navigation arrows.
     *
     * @param imageName name of the image to change to
     * @param image image/button to change
     */
    public static void controlButtonEffect(String imageName, ImageView image) {
        Image newImage = new Image(ControlScene.class.getResourceAsStream("/"+imageName));
        image.setImage(newImage);
    }

    /**
     * Changes styling of the button if it's hovered.
     * Used for login & register pages.
     *
     * @param button button to change the style of
     */
    public static void buttonHovered(Button button){
        button.setStyle("-fx-background-color: #E6BB9A; -fx-background-radius: 10");
    }

    /**
     * Changes styling of the button and the image if it's hovered.
     * Used for buttons in marks page.
     *
     * @param pane "button" to change the style of
     * @param image image to change the colour of
     * @param label label to change the style of
     * @param imageName name of the image
     */
    public static void buttonHovered(Pane pane, ImageView image, Label label, String imageName){
        pane.setStyle("-fx-background-color: white; -fx-background-radius: 20");

        Image newImage = new Image(ControlScene.class.getResourceAsStream("/"+imageName));
        image.setImage(newImage);

        label.setTextFill(Color.rgb(0, 0, 0));
    }

    /**
     * Reverts to regular styling of the button if it's no longer
     * hovered.
     * Used for login & register pages.
     *
     * @param button button to change the style of
     */
    public static void buttonExited(Button button){
        button.setStyle("-fx-background-color: white; -fx-background-radius: 10");
    }

    /**
     * Changes styling of the button and the image if it's hovered.
     * Used for buttons in marks page.
     *
     * @param pane "button" to change the style of
     * @param image image to change the colour of
     * @param label label to change the style of
     * @param imageName name of the image
     */
    public static void buttonExited(Pane pane, ImageView image, Label label, String imageName){
        pane.setStyle("-fx-background-color: none; -fx-background-radius: 20; " +
                "-fx-border-style: solid; -fx-border-color: white; -fx-border-width: 3; -fx-border-radius: 20");

        Image newImage = new Image(ControlScene.class.getResourceAsStream("/"+imageName));
        image.setImage(newImage);

        label.setTextFill(Color.rgb(230,187,154));
    }

    /**
     * Changes styling of menu tab(pane) if it's hovered.
     *
     * @param pane pane to change the style of
     */
    public static void menuPaneHovered(Pane pane){
        pane.setStyle("-fx-background-color: white;");
    }

    /**
     * Reverts to regular styling of the menu tab(pane) if it's
     * no longer hovered.
     *
     * @param pane pane to change the style of
     */
    public static void menuPaneExited(Pane pane){
        pane.setStyle("-fx-background-color: none");
    }

    /**
     * Changes the styling of a text field if the input cannot be accepted.
     * Used when user adds/edits something in a popup and the value is unacceptable.
     *
     * @param problematicField field to highlight as wrong
     */
    public static void highlightWrongField(TextField problematicField){
        problematicField.setStyle("-fx-background-color: none; -fx-text-fill: white; " +
                "-fx-border-style: solid; -fx-border-color: #C75450; -fx-border-radius: 10;" +
                "-fx-border-width: 3");
    }

    /**
     * Changes the styling of a combo box if the input cannot be accepted.
     * Used when user adds/edits something in a popup and the value is unacceptable.
     *
     * @param problematicComboBox combo box to highlight as wrong
     */
    public static void highlightWrongField(ComboBox problematicComboBox){
        problematicComboBox.setStyle("-fx-background-color: none; -fx-text-fill: white; " +
                "-fx-border-style: solid; -fx-border-color: #C75450; -fx-border-radius: 10;" +
                "-fx-border-width: 3");
    }

    /**
     * Changes the styling of a text area if the input cannot be accepted.
     * Used when user adds/edits something in a popup and the value is unacceptable.
     *
     * @param problematicTextArea text area to highlight as wrong
     */
    public static void highlightWrongField(TextArea problematicTextArea){
        problematicTextArea.setStyle("-fx-background-color: none; -fx-text-fill: white; " +
                "-fx-border-style: solid; -fx-border-color: #C75450; -fx-border-radius: 10;" +
                "-fx-border-width: 3; -fx-wrap-text: true");
    }

    /**
     * Method which reverts the styling of a text field back to normal, after a
     * wrong input was received.
     * Used when user adds/edits something in a popup and the value is unacceptable.
     *
     * @param problematicField text field to un-highlight
     */
    public static void normaliseWrongField(TextField problematicField){
        problematicField.setStyle("-fx-background-color: none; -fx-text-fill: white; " +
                "-fx-border-style: solid; -fx-border-color: white; -fx-border-radius: 10;" +
                "-fx-border-width: 3");
    }

    /**
     * Method which reverts the styling of a combo box back to normal, after a
     * wrong input was received.
     * Used when user adds/edits something in a popup and the value is unacceptable.
     *
     * @param problematicComboBox combo box to un-highlight
     */
    public static void normaliseWrongField(ComboBox problematicComboBox){
        problematicComboBox.setStyle("-fx-background-color: none; -fx-text-fill: white; " +
                "-fx-border-style: solid; -fx-border-color: white; -fx-border-radius: 10;" +
                "-fx-border-width: 3");
    }

    /**
     * Method which reverts the styling of a text area back to normal, after a
     * wrong input was received.
     * Used when user adds/edits something in a popup and the value is unacceptable.
     *
     * @param problematicTextArea text area to un-highlight
     */
    public static void normaliseWrongField(TextArea problematicTextArea){
        problematicTextArea.setStyle("-fx-background-color: none; -fx-text-fill: white; " +
                "-fx-border-style: solid; -fx-border-color: white; -fx-border-radius: 10;" +
                "-fx-border-width: 3; -fx-wrap-text: true");
    }

    /**
     * Method which takes a combo box and sets up its styling.
     * @param comboBox combo box to style
     */
    public static void setupComboBoxStyle(ComboBox comboBox){
        comboBox.setButtonCell(new ListCell(){
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
}
