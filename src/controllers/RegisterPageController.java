package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultButtons;
import controllers.utilities.SetupScene;
import core.Session;
import core.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
 * Class which handles Register page functionality and UI.
 */
public class RegisterPageController extends DefaultButtons implements Initializable {
    // Labels
    @FXML
    private Label greetingsMessageLabel;
    @FXML
    private Label greetingsLabel;
    @FXML
    private Label errorMessage;

    // Buttons
    @FXML
    private Button registerButton;

    // Navigation buttons
    @FXML
    private ImageView goBackButton;

    // Fields
    @FXML
    private TextField forenameField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField repeatPasswordField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Sets the welcome message & greeting according to the time of day
        SetupScene.setupWelcomePanel(greetingsLabel, greetingsMessageLabel);
    }

    /**
     * Method which changes background colour of register button if it is no
     * longer hovered.
     * Refers to class ControlStage method buttonExited.
     */
    @FXML
    public void buttonExited() {
        ControlScene.buttonExited(registerButton);
    }

    /**
     * Method which changes background colour of register button if it is hovered.
     * Refers to class ControlStage method buttonHovered.
     */
    @FXML
    public void buttonHovered() {
        ControlScene.buttonHovered(registerButton);
    }

    /**
     * Method which changes the screen back to login when goBack button is pressed.
     */
    @FXML
    void goBackClicked() {
        try {
            SetupScene.changeScene("LoginPageView.fxml", registerButton);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene Register to Login by goBack button.");
        }
    }

    /**
     * Method which changes the colour of go back button when hovered.
     */
    @FXML
    private void goBackHovered(){
        ControlScene.controlButtonEffect("back_icon_selected.png", goBackButton);
    }

    /**
     * Method which changes the colour of go back button to default when exited.
     */
    @FXML
    private void goBackExited(){
        ControlScene.controlButtonEffect("back_icon.png", goBackButton);
    }

    @FXML
    void registerClicked() {
        String forename = forenameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String passwordRepeat = repeatPasswordField.getText();

        errorMessage.setText("");
        boolean canRegister = true;

        // Checks if all fields have been filled
        if(forename.isEmpty() || username.isEmpty() || password.isEmpty() || passwordRepeat.isEmpty()){
            canRegister = false;
            errorMessage.setText("Please fill all fields.");
        }

        // Checks if username is unique
        if(!User.usernameAvailable(username)){
            canRegister = false;
            errorMessage.setText("Username is taken. Please choose another.");
        }

        // Checks if password is strong
        if(canRegister && !User.isPasswordStrong(password)){
            canRegister = false;
            errorMessage.setText("Password has to have: \n-At least 8 symbols;" +
                    "\n-A lowercase letter;\n-An uppercase letter;\n-A number.");
        }

        // Checks if passwords match
        if(canRegister && !password.equals(passwordRepeat)){
            canRegister = false;
            errorMessage.setText("Passwords do not match.");
        }

        // If above checks are passed, registers the user and forwards them to the login screen
        if(canRegister){
            // Adds user to the database
            User newUser = new User(forename, username, password);
            boolean successful = User.addUser(newUser);
            if(successful){
                // Forwards the user to the login page
                try {
                    Session.setUserCreatedInSession(true);
                    SetupScene.changeScene("LoginPageView.fxml", registerButton);
                } catch (IOException e) {
                    System.out.println("Exception whilst changing scene Register to Login by registerButton.");
                }
            }
            else errorMessage.setText("User creation was unsuccessful. Please try again.");
        }
    }

    /**
     * Listener for keyboard events.
     * If enter is pressed, register is activated.
     * @param event used for identifying the key
     */
    @FXML
    private void keyPressed(KeyEvent event) {
        // Enter pressed
        if(event.getCode().toString().equals("ENTER")) registerClicked();
    }
}
