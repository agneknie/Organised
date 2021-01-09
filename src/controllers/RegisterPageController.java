package controllers;

import controllers.utilities.ControlStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.swing.text.html.ImageView;

public class RegisterPageController {
    // Labels
    @FXML
    private Label greetingsMessageLabel;
    @FXML
    private Label greetingsLabel;
    @FXML
    private Label errorMessage;

    // Buttons
    @FXML
    private ImageView closeButton;
    @FXML
    private ImageView minimizeButton;
    @FXML
    private ImageView goBackButton;
    @FXML
    private Button registerButton;

    // Fields
    @FXML
    private TextField forenameField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField repeatPasswordField;

    /**
     * Method which changes background colour of register button if it is no
     * longer hovered.
     * Refers to class ControlStage method buttonExited.
     */
    @FXML
    void buttonExited() {
        ControlStage.buttonExited(registerButton);
    }

    /**
     * Method which changes background colour of register button if it is hovered.
     * Refers to class ControlStage method buttonHovered.
     */
    @FXML
    void buttonHovered() {
        ControlStage.buttonHovered(registerButton);
    }

    /**
     * Method which closes the window when close button is clicked.
     * Refers to class ControlStage method closeWindow.
     *
     * @param event used for getting the scene
     */
    @FXML
    void closeClicked(MouseEvent event) {
        ControlStage.closeWindow(event);
    }

    /**
     * Method which minimises the window when minimize button is clicked.
     * Refers to class ControlStage method minimizeWindow.
     *
     * @param event used for getting the scene
     */
    @FXML
    private void minimizeClicked(MouseEvent event) {
        ControlStage.minimizeWindow(event);
    }

    //TODO
    @FXML
    void goBackClicked(MouseEvent event) {

    }

    //TODO
    @FXML
    void registerClicked(ActionEvent event) {

    }

}
