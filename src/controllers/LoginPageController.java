package controllers;

import controllers.utilities.ControlStage;
import controllers.utilities.SetupStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {
    // Buttons
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;

    // Labels
    @FXML
    private Label greetingsLabel;
    @FXML
    private Label greetingsMessageLabel;
    @FXML
    private Label errorMessage;

    // Other elements
    @FXML
    private CheckBox checkboxKeepLoggedIn;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;



    /**
     * Implemented method for initialization tasks.
     * Changes the welcome message and greeting according to the time of day.
     *
     * @param location parameter not used.
     * @param resources parameter not used.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Sets the welcome message & greeting according to the time of day
        SetupStage.setupWelcomePanel(greetingsLabel, greetingsMessageLabel);
    }

    /**
     * Method which closes the window when close button is clicked.
     * Refers to class ControlStage method closeWindow.
     *
     * @param event used for getting the scene
     */
    @FXML
    private void closeClicked(MouseEvent event) {
        ControlStage.closeWindow(event);
    }

    /**
     * Method which minimises the window when minimize button is clicked.
     * Refers to class ControlStage method minimizeWindow.
     * @param event used for getting the scene
     */
    @FXML
    private void minimizeClicked(MouseEvent event) {
        ControlStage.minimizeWindow(event);
    }

    /**
     * Method which changes background colour of login button if it is hovered.
     */
    @FXML
    private void LoginButtonHovered() {
        ControlStage.buttonHovered(loginButton);
    }

    /**
     * Method which changes background colour of register button if it is hovered.
     */
    @FXML
    private void RegisterButtonHovered() {
        ControlStage.buttonHovered(registerButton);
    }

    /**
     * Method which changes background colour of login button if it is no
     * longer hovered.
     */
    @FXML
    private void LoginButtonExited(MouseEvent event){
        ControlStage.buttonExited(loginButton);
    }

    /**
     * Method which changes background colour of register button if it is no
     * longer hovered.
     */
    @FXML
    private void RegisterButtonExited(MouseEvent event){
        ControlStage.buttonExited(registerButton);
    }

    @FXML
    private void loginClicked(ActionEvent event) {

    }

    @FXML
    private void registerClicked(ActionEvent event) {

    }



    /**
     * Listener for keyboard events.
     * If enter is pressed and both text fields have text, login is activated.
     * @param event used for identifying the key
     */
    @FXML
    private void keyPressed(KeyEvent event) {
        // Enter pressed
        if(event.getCode().toString().equals("ENTER") &&
                !usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
            ActionEvent empty = new ActionEvent();
            loginClicked(empty);
        }
    }
}
