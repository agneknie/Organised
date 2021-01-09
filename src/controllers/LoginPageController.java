package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.SetupScene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
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
        SetupScene.setupWelcomePanel(greetingsLabel, greetingsMessageLabel);
    }

    /**
     * Method which closes the window when close button is clicked.
     * Refers to class ControlStage method closeWindow.
     *
     * @param event used for getting the scene
     */
    @FXML
    private void closeClicked(MouseEvent event) {
        ControlScene.closeWindow(event);
    }

    /**
     * Method which minimises the window when minimize button is clicked.
     * Refers to class ControlStage method minimizeWindow.
     *
     * @param event used for getting the scene
     */
    @FXML
    private void minimizeClicked(MouseEvent event) {
        ControlScene.minimizeWindow(event);
    }

    /**
     * Method which changes background colour of login button if it is hovered.
     * Refers to class ControlStage method buttonHovered.
     */
    @FXML
    private void LoginButtonHovered() {
        ControlScene.buttonHovered(loginButton);
    }

    /**
     * Method which changes background colour of register button if it is hovered.
     * Refers to class ControlStage method buttonHovered.
     */
    @FXML
    private void RegisterButtonHovered() {
        ControlScene.buttonHovered(registerButton);
    }

    /**
     * Method which changes background colour of login button if it is no
     * longer hovered.
     * Refers to class ControlStage method buttonExited.
     */
    @FXML
    private void LoginButtonExited(){
        ControlScene.buttonExited(loginButton);
    }

    /**
     * Method which changes background colour of register button if it is no
     * longer hovered.
     * Refers to class ControlStage method buttonExited.
     */
    @FXML
    private void RegisterButtonExited(){
        ControlScene.buttonExited(registerButton);
    }

    //TODO
    @FXML
    private void loginClicked() {

    }

    /**
     * Method which changes the scene to register screen when registerButton is pressed.
     */
    @FXML
    private void registerClicked() {
        try {
           SetupScene.changeScene("RegisterPageView.fxml", (Node) registerButton);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene Login to Register by registerButton.");
        }
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
            loginClicked();
        }
    }
}
