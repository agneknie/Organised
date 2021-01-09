package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.SetupScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class RegisterPageController implements Initializable {
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
    void buttonExited() {
        ControlScene.buttonExited(registerButton);
    }

    /**
     * Method which changes background colour of register button if it is hovered.
     * Refers to class ControlStage method buttonHovered.
     */
    @FXML
    void buttonHovered() {
        ControlScene.buttonHovered(registerButton);
    }

    /**
     * Method which closes the window when close button is clicked.
     * Refers to class ControlStage method closeWindow.
     *
     * @param event used for getting the scene
     */
    @FXML
    void closeClicked(MouseEvent event) {
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
     * Method which changes the screen back to login when goBack button is pressed.
     */
    @FXML
    void goBackClicked() {
        try {
            SetupScene.changeScene("LoginPageView.fxml", (Node) registerButton);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene Register to Login by goBack button.");
        }
    }

    //TODO
    @FXML
    void registerClicked(ActionEvent event) {

    }
}
