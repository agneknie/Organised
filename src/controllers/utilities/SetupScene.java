package controllers.utilities;

import core.enums.TimeOfDay;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
 * Class which has helper methods used to setup scenes. Methods here
 * are used in more than one controller/scene.
 */
public class SetupScene {

    /**
     * Method which takes a path to a new scene fxml and a node from a current scene
     * and updates the stage with the specified scene.
     *
     * @param viewName name of the fxml of the new scene
     * @param node node from a scene, to get the stage
     * @throws IOException if fxml file could not be found
     */
    public static void changeScene(String viewName, Node node) throws IOException {
        // Loads new scene
        Parent root= FXMLLoader.load(SetupScene.class.getResource("/"+viewName));
                // FXMLLoader.load(new File(path).toURI().toURL());

        // Creates new scene and sets it's size
        Scene newScene = new Scene(root, 1400, 900);

        // Gets the current stage
        Stage currentStage = (Stage) node.getScene().getWindow();

        // Focuses away from the fields for prompt text to be visible
        root.requestFocus();

        // Sets the scene
        currentStage.setScene(newScene);
    }
    /**
     * Sets up the welcome messages in login and register scenes.
     * Uses user's local time to display the according messages.
     *
     * @param greeting main greeting field (e.g. displays: Good Morning!)
     * @param greetingMessage accompanying greeting field (e.g. displays: Start your day.)
     */
    public static void setupWelcomePanel(Label greeting, Label greetingMessage){

        switch(Objects.requireNonNull(TimeOfDay.getTimeOfDay())){
            case MORNING:
                greeting.setText("Good Morning!");
                greetingMessage.setText("Start your productive day.");
                break;

            case AFTERNOON:
                greeting.setText("Good Afternoon!");
                greetingMessage.setText("Prime productivity time.");
                break;

            case EVENING:
                greeting.setText("Good Evening!");
                greetingMessage.setText("End your day productively.");
                break;

            case NIGHT:
                greeting.setText("Good Evening!");
                greetingMessage.setText("Late night work rush?");
                break;
        }
    }
}
