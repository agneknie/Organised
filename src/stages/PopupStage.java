package stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Class representing a popup stage/window in the application
 */
public class PopupStage {
    // TODO: figure out the sizes, the icon, etc. Finish this method
    public PopupStage(Stage primaryStage, String viewName) throws IOException {
        // Loads the required scene
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/views/"+viewName));

        // Sets window (stage) to default size
        primaryStage.setScene(new Scene(root, 700, 450));

        // Sets modality of the stage (can't work with other windows if popup is open
        primaryStage.initModality(Modality.APPLICATION_MODAL);

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

}
