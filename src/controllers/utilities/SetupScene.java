package controllers.utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

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
        // Constructs scene's fxml url
        String localDir = System.getProperty("user.dir");
        String prefix = "\\src\\views\\";
        String path = localDir+prefix+viewName;

        // Loads new scene
        Parent root= FXMLLoader.load(new File(path).toURI().toURL());

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
        // Gets current user's time
        LocalTime currentTime = LocalTime.now();

        // Times slicing the day into sequences
        LocalTime morningStart = LocalTime.of(5, 0);
        LocalTime afternoonStart = LocalTime.of(12, 0);
        LocalTime eveningStart = LocalTime.of(18, 0);
        LocalTime nightStart = LocalTime.of(22, 0);

        // Morning
        if(currentTime.isAfter(morningStart) && currentTime.isBefore(afternoonStart) || currentTime.equals(morningStart)){
            greeting.setText("Good Morning!");
            greetingMessage.setText("Start your productive day.");
        }
        // Afternoon
        else if(currentTime.isAfter(afternoonStart) && currentTime.isBefore(eveningStart) || currentTime.equals(afternoonStart)){
            greeting.setText("Good Afternoon!");
            greetingMessage.setText("Prime productivity time.");
        }
        // Evening
        else if(currentTime.isAfter(eveningStart) && currentTime.isBefore(nightStart) || currentTime.equals(eveningStart)){
            greeting.setText("Good Evening!");
            greetingMessage.setText("End your day productively.");
        }
        // Night
        else if(currentTime.isAfter(nightStart) || currentTime.isBefore(morningStart) || currentTime.equals(nightStart)){
            greeting.setText("Good Evening!");
            greetingMessage.setText("Late night work rush?");
        }
        // Something is seriously wrong
        else{
            greeting.setText("Hello!");
            greetingMessage.setText("You are literally out of the comprehension of time.");
        }
    }
}
