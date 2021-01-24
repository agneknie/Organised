package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultNavigation;
import core.Assignment;
import core.Module;
import core.Session;
import core.Year;
import core.enums.MarksPopupType;
import core.enums.MarksSelection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import stages.PopupStage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MarksController extends DefaultNavigation implements Initializable {
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

    // Big Panes and their elements
    // Pane 5
    @FXML
    private Pane pane5;
    private boolean pane5Loaded = false;
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
    private boolean pane6Loaded = false;
    @FXML
    private Label pane6Title;
    @FXML
    private Label pane6Label1;
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
    private boolean pane7Loaded = false;
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

    // Variables for storing user data
    private List<Year> userYears;
    private List<Module> userModules;
    private List<Assignment> userAssignments;
    // Indexes of the objects in the above lists, which are displayed by below panes
    private int pane5Pointer;
    private int pane6Pointer;
    private int pane7Pointer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Cleans the session variables of marks, if there were any
        cleanSession();

        // Loads degree information
        loadDegree();
    }

    // Helper methods used within this controller
    /**
     * Cleans the session variable if popup is closed.
     * @param event parameter not used
     */
    private void popupClosedFromTaskBar(WindowEvent event){
        Session.setMarksPopupType(null);
    }

    /**
     * Cleans the session variables for year, module, assignment &
     * popup, selection types.
     */
    private void cleanSession(){
        Session.setMarksYearSelected(null);
        Session.setMarksModuleSelected(null);
        Session.setMarksAssignmentSelected(null);
        Session.setMarksSelectionType(null);
        Session.setMarksPopupType(null);
    }

    /**
     * Sets pane 5, 6 & 7 statuses to not loaded.
     * Used when user is changing the scene view between Degree/Year/Module
     */
    private void cleanCurrentSelection(){
        pane5Loaded = false;
        pane6Loaded = false;
        pane7Loaded = false;
    }

    /**
     * Method which hides/shows navigation arrows if needed
     * @param objects User's Year, Module or Assignment list
     */
    private void determineNavigationVisibility(List<Object> objects){
        // In case there are only 3 objects to display, hides the navigation
        goLeftButton.setVisible(false);
        goRightButton.setVisible(false);

        // If there are objects hidden on the left, enables left navigation
        if(pane5Pointer > 0) goLeftButton.setVisible(true);
        // If there are objects hidden on the right, enables right navigation
        if(pane7Pointer < objects.size()-1) goRightButton.setVisible(true);

    }

    /**
     * Listener for keyboard events.
     * If right/left arrow is pressed, changes the pane information (triggers
     * the navigation arrows actions)
     * @param event used for identifying the key
     */
    public void keyPressed(KeyEvent event){
        KeyCode key = event.getCode();
        // If left arrow is clicked
        if (key.equals(KeyCode.LEFT)) goLeftClicked();
        // If right arrow is clicked
        if (key.equals(KeyCode.RIGHT)) goRightClicked();
    }

    /**
     * Refreshes the panels if user came back from the popup.
     * Also updates user information regarding Years, Modules or Assignments
     */
    @FXML
    private void refreshPanels(){
        // Refreshes Year/Module/Assignment list
        switch(Session.getMarksSelectionType()){
            case DEGREE:
                userYears = Session.getSession().getAllYears();
                break;
            case YEAR:
                userModules = Session.getMarksYearSelected().getAllModules();
                break;
            case MODULE:
                userAssignments = Session.getMarksModuleSelected().getAllAssignments();
                break;
        }
        // Updates the panels
        panelsUpdate();
    }

    // Methods concerning the setup up of the scene with user data
    /**
     * Setups the scene with degree data (panels have year data)
     */
    private void loadDegree(){
        // Loads the Years of the user
        userYears = Session.getSession().getAllYears();

        // Sets the main titles of the page
        bigTitleLabel.setText("Your Degree.");
        optionalTitleLabel.setText("");

        // Sets up the buttons
        button1.setVisible(false);
        button2Label.setText("Add Year");

        // Sets degree overall grade
        pane1Label.setText("Average:");
        double userGrade = Session.getSession().getDegreeGrade();
        if (userGrade == -1) pane1Value.setText("-");
        else pane1Value.setText(Double.toString(userGrade));

        // Set degree classification
        pane2Label.setText("Classification:");
        pane2Value.setText(Session.getSession().getClassification());

        // Hides unused top display panes
        pane3.setVisible(false);
        pane4.setVisible(false);

        // Load years in pane5, pane6 & pane7
        switch(userYears.size()){
            case 0:
                // TODO no Years present
                break;
            case 1:
                loadPane5(userYears.get(0));
                break;
            case 2:
                loadPane5(userYears.get(0));
                loadPane6(userYears.get(1));
                break;
            default:
                loadPane5(userYears.get(0));
                loadPane6(userYears.get(1));
                loadPane7(userYears.get(2));
                break;
        }

        // Hides year panes if unused
        hideUnusedPanes();

        // Configures navigation arrows
        determineNavigationVisibility(new ArrayList<>(userYears));

        // Sets the current Marks Tab Selection
        Session.setMarksSelectionType(MarksSelection.DEGREE);
    }

    /**
     * Setups the scene with Year data (panels have module data)
     */
    private void loadYear(){
        //TODO loadYear
    }

    /**
     * Setups the scene with Module data (panels have assignment data)
     */
    private void loadModule(){
        //TODO loadModule
    }

    // Methods concerning the big 3 panes for data display
    /**
     * Hides module/year/assignment panes if unused.
     */
    private void hideUnusedPanes(){
        if (!pane5Loaded) pane5.setVisible(false);
        if (!pane6Loaded) pane6.setVisible(false);
        if (!pane7Loaded) pane7.setVisible(false);
    }

    /**
     * Method which sets up Pane 5 with data.
     * @param object Year, Module or Assignment object to set.
     */
    private void loadPane5(Object object){
        // Gets the name of the class
        String className = object.getClass().getSimpleName();

        // Makes the pane visible, in case previously hidden
        pane5.setVisible(true);

        switch(className){

            // If class is Year, setups the panel as Year
            case("Year"):
                Year year = (Year) object;
                // Top Title
                pane5Title.setText("Year "+year.getYearNumber());

                // Credits
                pane5Label1.setText("Credits:");
                pane5Value1.setText(Integer.toString(year.getCredits()));

                // Worth in percent
                pane5Label2.setText("Worth:");
                pane5Value2.setText(year.getPercentWorth() + "%");

                // Grade
                pane5Label3.setText("Average:");
                if (year.getOverallGrade() == -1) pane5Value3.setText("-");
                else pane5Value3.setText(year.getOverallGrade() + "%");

                // % Complete:
                pane5Label4.setText("Complete:");
                pane5Value4.setText(year.getPercentComplete() + "%");

                // Marks pane as loaded
                pane5Loaded = true;

                // Marks which year in a list of years was added
                pane5Pointer = userYears.indexOf(year);
                break;

            // If class is Module, setups the panel as Module
            case ("Module"):
                Module module = (Module) object;
                break;

            // If class is Assignment, setups the panel as Assignment
            case ("Assignment"):
                Assignment assignment = (Assignment) object;
                break;
        }
    }

    /**
     * Method which sets up Pane 6 with data.
     * @param object Year, Module or Assignment object to set.
     */
    private void loadPane6(Object object){
        // Gets the name of the class
        String className = object.getClass().getSimpleName();

        // Makes the pane visible, in case previously hidden
        pane6.setVisible(true);

        switch(className){

            // If class is Year, setups the panel as Year
            case("Year"):
                Year year = (Year) object;
                // Top Title
                pane6Title.setText("Year "+year.getYearNumber());

                // Credits
                pane6Label1.setText("Credits:");
                pane6Value1.setText(String.valueOf(year.getCredits()));

                // Worth in percent
                pane6Label2.setText("Worth:");
                pane6Value2.setText(year.getPercentWorth() + "%");

                // Grade
                pane6Label3.setText("Average:");
                if (year.getOverallGrade() == -1) pane6Value3.setText("-");
                else pane6Value3.setText(year.getOverallGrade() + "%");

                // % Complete:
                pane6Label4.setText("Complete:");
                pane6Value4.setText(year.getPercentComplete() + "%");

                // Marks pane as loaded
                pane6Loaded = true;

                // Marks which year in a list of years was added
                pane6Pointer = userYears.indexOf(year);
                break;

            // If class is Module, setups the panel as Module
            case ("Module"):
                Module module = (Module) object;
                break;

            // If class is Assignment, setups the panel as Assignment
            case ("Assignment"):
                Assignment assignment = (Assignment) object;
                break;
        }
    }

    /**
     * Method which sets up Pane 7 with data.
     * @param object Year, Module or Assignment object to set.
     */
    private void loadPane7(Object object){
        // Gets the name of the class
        String className = object.getClass().getSimpleName();

        // Makes the pane visible, in case previously hidden
        pane7.setVisible(true);

        switch(className){

            // If class is Year, setups the panel as Year
            case("Year"):
                Year year = (Year) object;
                // Top Title
                pane7Title.setText("Year "+year.getYearNumber());

                // Credits
                pane7Label1.setText("Credits:");
                pane7Value1.setText(String.valueOf(year.getCredits()));

                // Worth in percent
                pane7Label2.setText("Worth:");
                pane7Value2.setText(year.getPercentWorth() + "%");

                // Grade
                pane7Label3.setText("Average:");
                if (year.getOverallGrade() == -1) pane7Value3.setText("-");
                else pane7Value3.setText(year.getOverallGrade() + "%");

                // % Complete:
                pane7Label4.setText("Complete:");
                pane7Value4.setText(year.getPercentComplete() + "%");

                // Marks pane as loaded
                pane7Loaded = true;

                // Marks which year in a list of years was added
                pane7Pointer = userYears.indexOf(year);
                break;

            // If class is Module, setups the panel as Module
            case ("Module"):
                Module module = (Module) object;
                break;

            // If class is Assignment, setups the panel as Assignment
            case ("Assignment"):
                Assignment assignment = (Assignment) object;
                break;
        }
    }

    /**
     * Helper method which updates the data in panels 5, 6 &7.
     * Also updates the visibility of navigation arrows.
     */
    private void panelsUpdate(){
        MarksSelection currentScene = Session.getMarksSelectionType();

        // Updates the panes
        switch(currentScene){
            // If degree information is currently displayed
            case DEGREE:
                loadPane5(userYears.get(pane5Pointer));
                loadPane6(userYears.get(pane6Pointer));
                loadPane7(userYears.get(pane7Pointer));

                // Updates navigation visibility
                determineNavigationVisibility(new ArrayList<>(userYears));
                break;

            // If Year information is currently displayed
            case YEAR:
                loadPane5(userModules.get(pane5Pointer));
                loadPane6(userModules.get(pane6Pointer));
                loadPane7(userModules.get(pane7Pointer));

                // Updates navigation visibility
                determineNavigationVisibility(new ArrayList<>(userModules));
                break;

            // If Module information is currently displayed
            case MODULE:
                loadPane5(userAssignments.get(pane5Pointer));
                loadPane6(userAssignments.get(pane6Pointer));
                loadPane7(userAssignments.get(pane7Pointer));

                // Updates navigation visibility
                determineNavigationVisibility(new ArrayList<>(userAssignments));
                break;
        }
    }

    // Below methods implement UI buttons
    @FXML
    private void button1Clicked() throws IOException {
        // Sets the popup type
        Session.setMarksPopupType(MarksPopupType.EDIT);

        // Creates the popup
        MarksSelection actionViewName = Session.getMarksSelectionType();
        Stage popup = new Stage();
        new PopupStage(popup, "MarksPopupView"+actionViewName+".fxml");

        // If popup gets closed from the taskbar, forwards to popupClosed method
        popup.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::popupClosedFromTaskBar);
    }

    @FXML
    private void button2Clicked() throws IOException {
        // Sets the popup type
        Session.setMarksPopupType(MarksPopupType.ADD);

        // Creates the popup
        MarksSelection actionViewName = Session.getMarksSelectionType();
        Stage popup = new Stage();
        new PopupStage(popup, "MarksPopupView"+actionViewName+".fxml");

        // If popup gets closed from the taskbar, forwards to popupClosed method
        popup.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::popupClosedFromTaskBar);
    }

    /**
     * Method which handles the change of scene view based on the user's selection.
     * Interaction with Pane 5.
     * @throws IOException if popup fxml is not found
     */
    @FXML
    private void pane5ButtonClicked() throws IOException {
        // Based on current selection, changes it and changes the displayed data accordingly
        MarksSelection currentSelection = Session.getMarksSelectionType();
        switch(currentSelection){
            // Populates the scene with selected Year information
            case DEGREE:
                Session.setMarksSelectionType(MarksSelection.YEAR);
                Session.setMarksYearSelected(userYears.get(pane5Pointer));
                cleanCurrentSelection();
                loadYear();
                break;

            // Populates the scene with selected Module information
            case YEAR:
                Session.setMarksSelectionType(MarksSelection.MODULE);
                Session.setMarksModuleSelected(userModules.get(pane5Pointer));
                cleanCurrentSelection();
                loadModule();
                break;

            // Creates a popup for assignment
            case MODULE:
                // Sets popup type
                Session.setMarksPopupType(MarksPopupType.EDIT);

                // Creates the popup
                Stage popup = new Stage();
                new PopupStage(popup, "MarksPopupViewAssignment.fxml");

                // If popup gets closed from the taskbar, forwards to popupClosed method
                popup.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST,
                        this::popupClosedFromTaskBar);
                break;
        }
    }

    /**
     * Method which handles the change of scene view based on the user's selection.
     * Interaction with Pane 6.
     * @throws IOException if popup fxml is not found
     */
    @FXML
    private void pane6ButtonClicked() {
        //TODO pane6ButtonClicked
    }

    /**
     * Method which handles the change of scene view based on the user's selection.
     * Interaction with Pane 6.
     * @throws IOException if popup fxml is not found
     */
    @FXML
    private void pane7ButtonClicked() {
        //TODO pane7ButtonClicked
    }



    // Below methods implement go left, go right, close window & go back to previous buttons
    /**
     * Method which moves the existing panes one to left and replaces
     * leftmost pane with new information.
     */
    @FXML
    private void goLeftClicked() {
        // Updates pointers
        pane5Pointer --;
        pane6Pointer --;
        pane7Pointer --;

        // Updates panes & navigation arrows visibility
        panelsUpdate();
    }

    /**
     * Method which moves the existing panes one to right and replaces
     * rightmost pane with new information.
     */
    @FXML
    private void goRightClicked() {
        // Updates pointers
        pane5Pointer ++;
        pane6Pointer ++;
        pane7Pointer ++;

        // Updates panes & navigation arrows visibility
        panelsUpdate();
    }

    /**
     * Method which changes the screen back to the previously visited screen
     * when goBack button is pressed.
     */
    public void goBackClicked() {
        //TODO go back to previous screen button
    }

    // Methods concerning the styling of elements
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
}
