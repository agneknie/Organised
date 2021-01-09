package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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

    // Labels
    @FXML
    private Label greetingsLabel;
    @FXML
    private Label greetingsMessageLabel;

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
     * Changes the welcome message and greeting according to the time of day.
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
        // Something is wrong
        else{
            greetingsLabel.setText("Hello!");
            greetingsMessageLabel.setText("You are literally out of the comprehension of time.");
        }
    }

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
}
