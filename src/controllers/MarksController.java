package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.SetupScene;
import core.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import stages.PopupStage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MarksController implements Initializable {

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

    // Title Labels
    @FXML
    private Label bigTitleLabel;
    @FXML
    private Label optionalTitleLabel;

    // Small Panes and their elements
    @FXML
    private Pane pane1;
    @FXML
    private Label pane1Label;
    @FXML
    private Label pane1Value;

    @FXML
    private Pane pane2;
    @FXML
    private Label pane2Label;
    @FXML
    private Label pane2Value;

    @FXML
    private Pane pane3;
    @FXML
    private Label pane3Label;
    @FXML
    private Label pane3Value;

    @FXML
    private Pane pane4;
    @FXML
    private Label pane4Label;
    @FXML
    private Label pane4Value;

    // Add/Edit Buttons
    @FXML
    private Pane button1;
    @FXML
    private Label button1Label;
    @FXML
    private ImageView button1Image;

    @FXML
    private Pane button2;
    @FXML
    private Label button2Label;
    @FXML
    private ImageView button2Image;

    // Navigation buttons
    @FXML
    private ImageView goLeftButton;
    @FXML
    private ImageView goRightButton;
    @FXML
    private ImageView closeButton;
    @FXML
    private ImageView minimizeButton;
    @FXML
    private ImageView goBackButton;

    // Big Panes and their elements
    // Pane 5
    @FXML
    private Pane pane5;
    @FXML
    private Label pane5Title;
    @FXML
    private Label pane5Label1;
    @FXML
    private Label pane5Value1;
    @FXML
    private Label pane5Label2;
    @FXML
    private Label pane5Value2;
    @FXML
    private Label pane5Label3;
    @FXML
    private Label pane5Value3;
    @FXML
    private Label pane5Label4;
    @FXML
    private Label pane5Value4;
    // Pane 5 button
    @FXML
    private Pane pane5Button;
    @FXML
    private ImageView pane5ButtonImage;
    @FXML
    private Label pane5ButtonLabel;

    // Pane 6
    @FXML
    private Pane pane6;
    @FXML
    private Label pane6Title;
    @FXML
    private Label pane6label1;
    @FXML
    private Label pane6Value1;
    @FXML
    private Label pane6Label2;
    @FXML
    private Label pane6Value2;
    @FXML
    private Label pane6Label3;
    @FXML
    private Label pane6Value3;
    @FXML
    private Label pane6Label4;
    @FXML
    private Label pane6Value4;
    // Pane 6 button
    @FXML
    private Pane pane6Button;
    @FXML
    private ImageView pane6ButtonImage;
    @FXML
    private Label pane6ButtonLabel;

    // Pane 7
    @FXML
    private Pane pane7;
    @FXML
    private Label pane7Title;
    @FXML
    private Label pane7Label1;
    @FXML
    private Label pane7Value1;
    @FXML
    private Label pane7Label2;
    @FXML
    private Label pane7Value2;
    @FXML
    private Label pane7Label3;
    @FXML
    private Label pane7Value3;
    @FXML
    private Label pane7Label4;
    @FXML
    private Label pane7Value4;
    // Pane 7 button
    @FXML
    private Pane pane7Button;
    @FXML
    private ImageView pane7ButtonImage;
    @FXML
    private Label pane7ButtonLabel;


    // Below methods implement UI buttons
    @FXML
    private void button1Clicked() throws IOException {
        //TODO button1Clicked
        Stage popup = new Stage();
        new PopupStage(popup, "MarksPopupView.fxml");
    }

    /**
     * Changes colour of the 1st button if hovered
     */
    @FXML
    private void button1Exited() {
        ControlScene.buttonExited(button1, button1Image, button1Label, "edit_icon.png");
    }

    /**
     * Reverts to default colour of the 1st button if exited
     */
    @FXML
    private void button1Hovered() {
        ControlScene.buttonHovered(button1, button1Image, button1Label, "edit_icon_selected.png");
    }

    @FXML
    private void button2Clicked() {
        //TODO button2Clicked
    }

    /**
     * Reverts to default colour of the 2nd button if exited
     */
    @FXML
    private void button2Exited() {
        ControlScene.buttonExited(button2, button2Image, button2Label, "add_icon.png");
    }

    /**
     * Changes colour of the 2nd button if hovered
     */
    @FXML
    private void button2Hovered() {
        ControlScene.buttonHovered(button2, button2Image, button2Label, "add_icon_selected.png");
    }

    @FXML
    private void pane5ButtonClicked() {
        //TODO pane5ButtonClicked
    }

    /**
     * Reverts to default colour of pane5 button when exited
     */
    @FXML
    private void pane5ButtonExited() {
        ControlScene.buttonExited(pane5Button, pane5ButtonImage, pane5ButtonLabel, "more_icon.png");
    }

    /**
     * Changes the colour of pane5 button when hovered
     */
    @FXML
    private void pane5ButtonHovered() {
        ControlScene.buttonHovered(pane5Button, pane5ButtonImage, pane5ButtonLabel, "more_icon_selected.png");
    }

    @FXML
    private void pane6ButtonClicked() {
        //TODO pane6ButtonClicked
    }

    /**
     * Reverts to default colour of pane6 button when exited
     */
    @FXML
    private void pane6ButtonExited() {
        ControlScene.buttonExited(pane6Button, pane6ButtonImage, pane6ButtonLabel, "more_icon.png");
    }

    /**
     * Changes the colour of pane6 button when hovered
     */
    @FXML
    private void pane6ButtonHovered() {
        ControlScene.buttonHovered(pane6Button, pane6ButtonImage, pane6ButtonLabel, "more_icon_selected.png");
    }

    @FXML
    private void pane7ButtonClicked() {
        //TODO pane7ButtonClicked
    }

    /**
     * Reverts to default colour of pane7 button when exited
     */
    @FXML
    private void pane7ButtonExited() {
        ControlScene.buttonExited(pane7Button, pane7ButtonImage, pane7ButtonLabel, "more_icon.png");
    }

    /**
     * Changes the colour of pane7 button when hovered
     */
    @FXML
    private void pane7ButtonHovered() {
        ControlScene.buttonHovered(pane7Button, pane7ButtonImage, pane7ButtonLabel, "more_icon_selected.png");
    }

    // Below methods implement go left, go right, close window, go back to previous & minimize buttons
    /**
     * Method which moves the existing panes one to left and replaces
     * leftmost pane with new information.
     */
    @FXML
    private void goLeftClicked() {
        //TODO goLeftClicked
    }

    /**
     * Reverts to usual goLeft button colour if exited.
     */
    @FXML
    private void goLeftExited() {
        ControlScene.controlButtonEffect("next_element_left.png", goLeftButton);
    }

    /**
     * Changes goLeft button colour if hovered .
     */
    @FXML
    private void goLeftHovered() {
        ControlScene.controlButtonEffect("next_element_left_selected.png", goLeftButton);
    }

    /**
     * Method which moves the existing panes one to right and replaces
     * rightmost pane with new information.
     */
    @FXML
    private void goRightClicked() {
        //TODO goRightClicked
    }

    /**
     * Reverts to usual goRight button colour if exited.
     */
    @FXML
    private void goRightExited() {
        ControlScene.controlButtonEffect("next_element_right.png", goRightButton);
    }

    /**
     * Changes goRight button colour if hovered .
     */
    @FXML
    private void goRightHovered() {
        ControlScene.controlButtonEffect("next_element_right_selected.png", goRightButton);
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
    private void goBackClicked() {
        //TODO go back to previous screen button
    }

    /**
     * Method which changes the colour of close button when hovered.
     */
    @FXML
    private void closeHovered(){
        ControlScene.controlButtonEffect("close_icon_selected.png", closeButton);
    }

    /**
     * Method which changes the colour of close button to default when exited.
     */
    @FXML
    private void closeExited(){
        ControlScene.controlButtonEffect("close_icon.png", closeButton);
    }

    /**
     * Method which changes the colour of minimize button when hovered.
     */
    @FXML
    private void minimizeHovered(){
        ControlScene.controlButtonEffect("minimize_icon_selected.png", minimizeButton);
    }

    /**
     * Method which changes the colour of minimize button to default when exited.
     */
    @FXML
    private void minimizeExited(){
        ControlScene.controlButtonEffect("minimize_icon.png", minimizeButton);
    }

    /**
     * Method which changes the colour of go back button when hovered.
     */
    @FXML
    private void goBackHovered(){
        ControlScene.controlButtonEffect("back_icon_selected.png", goBackButton);
    }

    /**
     * Method which changes the colour of go back button to default when exited.
     */
    @FXML
    private void goBackExited(){
        ControlScene.controlButtonEffect("back_icon.png", goBackButton);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO Initialize method in Marks view
    }
}
