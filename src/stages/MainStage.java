package stages;

import core.Session;
import core.User;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Class representing the main stage/window of the application
 */
public class MainStage {
    double xOffset = 0;
    double yOffset = 0;
    public MainStage(Stage primaryStage) throws IOException {
        // Loads the required scene
        Parent root;
        User loggedInUser = User.findLoggedInUser();

        // If logged in user exists, loads their data into session and forwards to profile view
        if(loggedInUser!=null) {
            Session.beginSession(loggedInUser);
            root = FXMLLoader.load(getClass().getResource("/views/ProfileView.fxml"));
        }
        // Otherwise, forwards to login page
        else
            root = FXMLLoader.load(getClass().getResource("/views/LoginPageView.fxml"));

        // Sets window (stage) to default size
        primaryStage.setScene(new Scene(root, 1400, 900));

        // Sets application window name
        primaryStage.setTitle("Organised.");

        // Makes scene non resizable and removes toolbar
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);

        // Adds the application logo
        primaryStage.getIcons().add(new Image("/images/icon.png"));

        // Focuses away from the fields for prompt text to be visible
        root.requestFocus();

        // Saves primaryStage in Session, so it can be used for dragging
        Session.setMainStage(primaryStage);

        // Shows the window
        primaryStage.show();
    }
}
