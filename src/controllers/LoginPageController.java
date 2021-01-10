package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.SetupScene;
import core.Session;
import core.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    private CheckBox keepLoggedInCheckbox;
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

        // Checks if the user is coming back from successful registration
        if(Session.getUserCreatedInSession()){
            errorMessage.setText("Successfully created a new user. Please log in.");
            Session.setUserCreatedInSession(false);
        }
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

    @FXML
    private void loginClicked() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User currentUser = null;

        // Resets error message text if there was any
        errorMessage.setText("");

        // If no username or no password inserted, display error
        if(username.isEmpty() || password.isEmpty())
            errorMessage.setText("Please fill all fields.");

        // Tries to construct user with given username and password
        else {
            try {
                currentUser = new User(username);
            } catch (SQLException e) {
                errorMessage.setText("Such user does not exist.");
                currentUser = null;
            }
        }

        // If user construction was successful & password is correct logs in the user
        if(currentUser!=null){
            if(!currentUser.passwordsMatch(password))
                errorMessage.setText("Wrong password.");
            else {
                Session.beginSession(currentUser);
                currentUser.setKeepLoggedIn(keepLoggedInCheckbox.isSelected());
            }
        }
    }

    /**
     * Method which changes the scene to register screen when registerButton is pressed.
     */
    @FXML
    private void registerClicked() {
        try {
           SetupScene.changeScene("RegisterPageView.fxml", registerButton);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene Login to Register by registerButton.");
        }
    }

    /**
     * Listener for keyboard events.
     * If enter is pressed, login is activated.
     * @param event used for identifying the key
     */
    @FXML
    private void keyPressed(KeyEvent event) {
        // Enter pressed
        if(event.getCode().toString().equals("ENTER")) loginClicked();
    }
}
