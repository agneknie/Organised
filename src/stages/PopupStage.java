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
    public PopupStage(Stage popup, String viewName) throws IOException {
        // Loads the required scene
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/views/"+viewName));

        // Sets window (stage) to default size
        popup.setScene(new Scene(root, 700, 600));

        // Sets modality of the stage (can't work with other windows if popup is open
        popup.initModality(Modality.APPLICATION_MODAL);

        // Sets application window name
        popup.setTitle("Organised.");

        // Makes scene non resizable and removes toolbar
        popup.setResizable(false);
        popup.initStyle(StageStyle.UNDECORATED);

        // Adds the application logo
        popup.getIcons().add(new Image("/images/icon.png"));

        // Focuses away from the fields for prompt text to be visible
        root.requestFocus();

        // Shows the window
        popup.show();
    }

}
