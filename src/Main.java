import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    // Launches the program
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Loads login page
        Parent root = FXMLLoader.load(getClass().getResource("/views/LoginPageView.fxml"));

        // Sets window (stage) to default size
        primaryStage.setScene(new Scene(root, 1400, 900));

        // Sets application window name
        primaryStage.setTitle("Organised.");

        // Makes scene non resizable and removes toolbar
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);

        // Adds the application logo
        primaryStage.getIcons().add(new Image("/views/images/icon.png"));

        // Shows the window
        primaryStage.show();
    }
}