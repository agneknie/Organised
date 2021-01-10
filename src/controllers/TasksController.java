package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.SetupScene;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class TasksController {

    // Panes
    @FXML
    private Pane profilePane;
    @FXML
    private Pane marksPane;
    @FXML
    private Pane timePane;
    @FXML
    private Pane schedulePane;
    @FXML
    private Pane settingsPane;
    @FXML
    private Pane aboutPane;

    /**
     * Method which closes the window when close button is clicked.
     * Refers to class ControlStage method closeWindow.
     *
     * @param event used for getting the scene
     */
    @FXML
    private void closeClicked(MouseEvent event) {
        ControlScene.closeWindow(event);
    }

    /**
     * Method which minimises the window when minimize button is clicked.
     * Refers to class ControlStage method minimizeWindow.
     *
     * @param event used for getting the scene
     */
    @FXML
    private void minimizeClicked(MouseEvent event) {
        ControlScene.minimizeWindow(event);
    }

    /**
     * Changes marks pane background colour if hovered
     */
    @FXML
    private void profileHovered(){
        ControlScene.menuPaneHovered(profilePane);
    }

    /**
     * Changes marks pane background colour back to default
     */
    @FXML
    private void profileExited(){
        ControlScene.menuPaneExited(profilePane);
    }

    /**
     * Forwards user to marks view/scene
     */
    @FXML
    private void profileClicked(){
        try {
            SetupScene.changeScene("ProfileView.fxml", profilePane);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene from Tasks to Profile by Menu.");
        }
    }

    /**
     * Changes marks pane background colour if hovered
     */
    @FXML
    private void marksHovered(){
        ControlScene.menuPaneHovered(marksPane);
    }

    /**
     * Changes marks pane background colour back to default
     */
    @FXML
    private void marksExited(){
        ControlScene.menuPaneExited(marksPane);
    }

    /**
     * Forwards user to marks view/scene
     */
    @FXML
    private void marksClicked(){
        try {
            SetupScene.changeScene("MarksView.fxml", marksPane);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene from Tasks to Marks by Menu.");
        }
    }

    /**
     * Changes time pane background colour if hovered
     */
    @FXML
    private void timeHovered(){
        ControlScene.menuPaneHovered(timePane);
    }

    /**
     * Changes time pane background colour back to default
     */
    @FXML
    private void timeExited(){
        ControlScene.menuPaneExited(timePane);
    }

    /**
     * Forwards user to time view/scene
     */
    @FXML
    private void timeClicked(){
        try {
            SetupScene.changeScene("TimeView.fxml", timePane);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene from Tasks to Time by Menu.");
        }
    }

    /**
     * Changes schedule pane background colour if hovered
     */
    @FXML
    private void scheduleHovered(){
        ControlScene.menuPaneHovered(schedulePane);
    }

    /**
     * Changes schedule pane background colour back to default
     */
    @FXML
    private void scheduleExited(){
        ControlScene.menuPaneExited(schedulePane);
    }

    /**
     * Forwards user to schedule view/scene
     */
    @FXML
    private void scheduleClicked(){
        try {
            SetupScene.changeScene("ScheduleView.fxml", schedulePane);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene from Tasks to Schedule by Menu.");
        }
    }

    /**
     * Changes settings pane background colour if hovered
     */
    @FXML
    private void settingsHovered(){
        ControlScene.menuPaneHovered(settingsPane);
    }

    /**
     * Changes settings pane background colour back to default
     */
    @FXML
    private void settingsExited(){
        ControlScene.menuPaneExited(settingsPane);
    }

    /**
     * Forwards user to settings view/scene
     */
    @FXML
    private void settingsClicked(){
        try {
            SetupScene.changeScene("SettingsView.fxml", settingsPane);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene from Tasks to Settings by Menu.");
        }
    }

    /**
     * Changes about pane background colour if hovered
     */
    @FXML
    private void aboutHovered(){
        ControlScene.menuPaneHovered(aboutPane);
    }

    /**
     * Changes about pane background colour back to default
     */
    @FXML
    private void aboutExited(){
        ControlScene.menuPaneExited(aboutPane);
    }

    /**
     * Forwards user to about view/scene
     */
    @FXML
    private void aboutClicked(){
        try {
            SetupScene.changeScene("AboutView.fxml", aboutPane);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene from Tasks to About by Menu.");
        }
    }
}
