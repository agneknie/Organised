package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.SetupScene;
import core.Session;
import core.User;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class SettingsController {

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
    private Pane tasksPane;
    @FXML
    private Pane aboutPane;
    @FXML
    private Pane signOutPane;

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
            System.out.println("Exception whilst changing scene from Settings to Profile by Menu.");
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
            System.out.println("Exception whilst changing scene from Settings to Marks by Menu.");
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
            System.out.println("Exception whilst changing scene from Settings to Time by Menu.");
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
            System.out.println("Exception whilst changing scene from Settings to Schedule by Menu.");
        }
    }

    /**
     * Changes tasks pane background colour if hovered
     */
    @FXML
    private void tasksHovered(){
        ControlScene.menuPaneHovered(tasksPane);
    }

    /**
     * Changes tasks pane background colour back to default
     */
    @FXML
    private void tasksExited(){
        ControlScene.menuPaneExited(tasksPane);
    }

    /**
     * Forwards user to tasks view/scene
     */
    @FXML
    private void tasksClicked(){
        try {
            SetupScene.changeScene("TasksView.fxml", tasksPane);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene from Settings to Tasks by Menu.");
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
            System.out.println("Exception whilst changing scene from Settings to About by Menu.");
        }
    }

    /**
     * Changes sign out pane background colour if hovered
     */
    @FXML
    private void signOutHovered(){
        ControlScene.menuPaneHovered(signOutPane);
    }

    /**
     * Changes sign out pane background colour back to default
     */
    @FXML
    private void signOutExited(){
        ControlScene.menuPaneExited(signOutPane);
    }

    /**
     * Logs out the user and forwards to login view/scene
     */
    @FXML
    private void signOutClicked(){
        try {
            SetupScene.changeScene("LoginPageView.fxml", signOutPane);
            User.signOutUser();

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene from Settings to Login by Menu.");
        }
    }
}
