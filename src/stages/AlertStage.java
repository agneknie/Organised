package stages;

import core.Session;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
 * Class representing an alert stage/window in the application.
 * Acts as a PopupStage but is smaller.
 */
public class AlertStage {
    public AlertStage(Stage alert, String viewName) throws IOException {
        // Loads the required scene
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/"+viewName));

        // Sets window (stage) to default size
        alert.setScene(new Scene(root, 300, 200));

        // Sets modality of the stage (can't work with other windows if alert is open
        alert.initModality(Modality.APPLICATION_MODAL);

        // Sets application window name
        alert.setTitle("Organised.");

        // Makes scene non resizable and removes toolbar
        alert.setResizable(false);
        alert.initStyle(StageStyle.UNDECORATED);

        // Adds the application logo
        Image newImage = new Image(AlertStage.class.getResourceAsStream("/icon.png"));
        alert.getIcons().add(newImage);

        // Focuses away from the fields for prompt text to be visible
        root.requestFocus();

        // Saves the stage of the alert, to use it for dragging
        Session.setPopupStage(alert);

        // Sets the coordinates of the stage to the middle of the main stage
        alert.setX(Session.getMainStage().getX()+Session.getMainStage().getWidth()/7*3);
        alert.setY(Session.getMainStage().getY()+Session.getMainStage().getHeight()/3);

        // Shows the window
        alert.show();
    }
}
