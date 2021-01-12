package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.SetupScene;
import core.Session;
import core.User;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MarksController {

    // Side menu panes
    @FXML
    private Pane profilePane;
    @FXML
    private Pane timePane;
    @FXML
    private Pane schedulePane;
    @FXML
    private Pane tasksPane;
    @FXML
    private Pane settingsPane;
    @FXML
    private Pane aboutPane;
    @FXML
    private Pane signOutPane;

    // Below methods implement UI buttons
    @FXML
    void button1Clicked() {
        //TODO button1Clicked
    }

    /**
     * Changes colour of the 1st button if hovered
     */
    @FXML
    void button1Exited() {
        //TODO button1Exited
    }

    /**
     * Reverts to default colour of the 1st button if exited
     */
    @FXML
    void button1Hovered() {
        //TODO button1Hovered
    }

    @FXML
    void button2Clicked() {
        //TODO button2Clicked
    }

    /**
     * Reverts to default colour of the 2nd button if exited
     */
    @FXML
    void button2Exited() {
        //TODO button2Exited
    }

    /**
     * Changes colour of the 2nd button if hovered
     */
    @FXML
    void button2Hovered() {
        //TODO button2Hovered
    }

    @FXML
    void pane5ButtonClicked() {
        //TODO pane5ButtonClicked
    }

    /**
     * Reverts to default colour of pane5 button when exited
     */
    @FXML
    void pane5ButtonExited() {
        //TODO pane5ButtonExited
    }

    /**
     * Changes the colour of pane5 button when hovered
     */
    @FXML
    void pane5ButtonHovered() {
        //TODO pane5ButtonHovered
    }

    @FXML
    void pane6ButtonClicked() {
        //TODO pane6ButtonClicked
    }

    /**
     * Reverts to default colour of pane6 button when exited
     */
    @FXML
    void pane6ButtonExited() {
        //TODO pane6ButtonExited
    }

    /**
     * Changes the colour of pane6 button when hovered
     */
    @FXML
    void pane6ButtonHovered() {
        //TODO pane6ButtonHovered
    }

    @FXML
    void pane7ButtonClicked() {
        //TODO pane7ButtonClicked
    }

    /**
     * Reverts to default colour of pane7 button when exited
     */
    @FXML
    void pane7ButtonExited() {
        //TODO pane7ButtonExited
    }

    /**
     * Changes the colour of pane7 button when hovered
     */
    @FXML
    void pane7ButtonHovered() {
        //TODO pane7ButtonHovered
    }

    // Below methods implement go left, go right, close window, go back to previous & minimize buttons
    /**
     * Method which moves the existing panes one to left and replaces
     * leftmost pane with new information.
     */
    @FXML
    void goLeftClicked() {
        //TODO goLeftClicked
    }

    /**
     * Reverts to usual goLeft button colour if exited.
     */
    @FXML
    void goLeftExited() {
        //TODO goLeftExited
    }

    /**
     * Changes goLeft button colour if hovered .
     */
    @FXML
    void goLeftHovered() {
        //TODO goLeftHovered
    }

    /**
     * Method which moves the existing panes one to right and replaces
     * rightmost pane with new information.
     */
    @FXML
    void goRightClicked() {
        //TODO goRightClicked
    }

    /**
     * Reverts to usual goRight button colour if exited.
     */
    @FXML
    void goRightExited() {
        //TODO goRightExited
    }

    /**
     * Changes goRight button colour if hovered .
     */
    @FXML
    void goRightHovered() {
        //TODO goRightHovered
    }

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
     * Method which changes the screen back to the previously visited screen
     * when goBack button is pressed.
     */
    @FXML
    void goBackClicked() {
        //TODO go back to previous screen button
    }

    // Below methods implement menu functionality
    /**
     * Changes profile pane background colour if hovered
     */
    @FXML
    private void profileHovered(){
        ControlScene.menuPaneHovered(profilePane);
    }

    /**
     * Changes profile pane background colour back to default
     */
    @FXML
    private void profileExited(){
        ControlScene.menuPaneExited(profilePane);
    }

    /**
     * Forwards user to profile view/scene
     */
    @FXML
    private void profileClicked(){
        try {
            SetupScene.changeScene("ProfileView.fxml", profilePane);

        } catch (IOException e) {
            System.out.println("Exception whilst changing scene from Marks to Profile by Menu.");
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
            System.out.println("Exception whilst changing scene from Marks to Time by Menu.");
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
            System.out.println("Exception whilst changing scene from Marks to Schedule by Menu.");
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
            System.out.println("Exception whilst changing scene from Marks to Tasks by Menu.");
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
            System.out.println("Exception whilst changing scene from Marks to Settings by Menu.");
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
            System.out.println("Exception whilst changing scene from Marks to About by Menu.");
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
            System.out.println("Exception whilst changing scene from Marks to Login by Menu.");
        }
    }
}
