package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {
    // Buttons
    @FXML
    private ImageView closeButton;
    @FXML
    private ImageView minimizeButton;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;

    // Labels
    @FXML
    private Label greetingsLabel;
    @FXML
    private Label greetingsMessageLabel;

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
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Sets the welcome message & greeting according to the time of day
        setWelcomeMessages();
    }

    /**
     * Method which closes the window when close button is clicked.
     *
     * @param event
     */
    @FXML
    private void closeClicked(MouseEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Method which minimises the window when minimize button is clicked.
     *
     * @param event
     */
    @FXML
    private void minimizeClicked(MouseEvent event) {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Method which changes background colour of a button if it is hovered.
     *
     * @param event
     */
    @FXML
    private void buttonHovered(MouseEvent event) {
        if(loginButton.isHover()){
            loginButton.setStyle("-fx-background-color: #E6BB9A");
        }
        if(registerButton.isHover()){
            registerButton.setStyle("-fx-background-color: #E6BB9A");
        }
    }

    /**
     * Method which changes background colour of a button if it is not hovered
     * @param event
     */
    @FXML
    private void buttonExited(MouseEvent event){
        registerButton.setStyle("-fx-background-color: white");
        loginButton.setStyle("-fx-background-color: white");
    }

    @FXML
    private void loginClicked(ActionEvent event) {

    }

    @FXML
    private void registerClicked(ActionEvent event) {

    }



    /**
     * Listener for keyboard events.
     * - If enter is pressed and both text fields have text, login is activated.
     * @param event
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

    /**
     * Changes the welcome and greeting messages according to the time of day.
     */
    private void setWelcomeMessages(){
        // Gets current user's time
        LocalTime currentTime = LocalTime.now();

        // Times slicing the day into sequences
        LocalTime morningStart = LocalTime.of(5, 0);
        LocalTime afternoonStart = LocalTime.of(12, 0);
        LocalTime eveningStart = LocalTime.of(18, 0);
        LocalTime nightStart = LocalTime.of(22, 0);

        // Morning
        if(currentTime.isAfter(morningStart) && currentTime.isBefore(afternoonStart) || currentTime.equals(morningStart)){
            greetingsLabel.setText("Good Morning!");
            greetingsMessageLabel.setText("Start your productive day.");
        }
        // Afternoon
        else if(currentTime.isAfter(afternoonStart) && currentTime.isBefore(eveningStart) || currentTime.equals(afternoonStart)){
            greetingsLabel.setText("Good Afternoon!");
            greetingsMessageLabel.setText("Prime productivity time.");
        }
        // Evening
        else if(currentTime.isAfter(eveningStart) && currentTime.isBefore(nightStart) || currentTime.equals(eveningStart)){
            greetingsLabel.setText("Good Evening!");
            greetingsMessageLabel.setText("End your day productively.");
        }
        // Night
        else if(currentTime.isAfter(nightStart) || currentTime.isBefore(morningStart) || currentTime.equals(nightStart)){
            greetingsLabel.setText("Good Evening!");
            greetingsMessageLabel.setText("Late night work rush?");
        }
        // Something is seriously wrong
        else{
            greetingsLabel.setText("Hello!");
            greetingsMessageLabel.setText("You are literally out of the comprehension of time.");
        }
    }
}
