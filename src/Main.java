import database.Database;
import javafx.application.Application;
import javafx.stage.Stage;
import stages.MainStage;

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

        // Loads the main application window
        new MainStage(primaryStage);
    }

    /**
     * Method which handles exiting the application
     */
    @Override
    public void stop(){
        Database.closeConnection();
    }
}
