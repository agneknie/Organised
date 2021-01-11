import core.Session;
import core.User;
import database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    /**
     * Launching the application
     * @param args default parameter
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Sets up the stage:
     * - removes the default windows toolbar;
     * - adds the application name and icon;
     * - locks the screen to desired width & height.
     *
     * @param primaryStage stage/window of the application
     * @throws IOException thrown if specified fxml not found
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Opens the database connection
        Database.openConnection();

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

        // Shows the window
        primaryStage.show();
    }

    /**
     * Method which handles exiting the application
     */
    @Override
    public void stop(){
        Database.closeConnection();
    }
}
