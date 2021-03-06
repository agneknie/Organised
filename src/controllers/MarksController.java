package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultNavigation;
import core.Assignment;
import core.Module;
import core.Session;
import core.Year;
import core.enums.PopupType;
import core.enums.MarksSelection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import stages.PopupStage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
 * Class which handles Marks Tab functionality and UI.
 */
public class MarksController extends DefaultNavigation implements Initializable {
    // Title Labels
    @FXML
    private Label bigTitleLabel;
    @FXML
    private Label optionalTitleLabel;

    // Small Panes and their elements
    @FXML
    private Label pane1Label;
    @FXML
    private Label pane1Value;

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
    private int pane5Pointer = -1;
    private int pane6Pointer = -1;
    private int pane7Pointer = -1;

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
        Session.setMarksAssignmentSelected(null);
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
        pane5Pointer = -1;
        pane6Pointer = -1;
        pane7Pointer = -1;
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
        if(pane7Pointer < objects.size()-1 && pane7Pointer != -1) goRightButton.setVisible(true);
    }

    /**
     * Refreshes the panels if user came back from the popup.
     * Also updates user information regarding Years, Modules or Assignments
     */
    @FXML
    private void refreshPanels(){
        // If user just deleted an object, they need to return to the previous screen
        if(Session.getMarksJustDeleted()){
            Session.setMarksJustDeleted(false);
            if(Session.getMarksAssignmentSelected() != null)
                Session.setMarksSelectionType(MarksSelection.ASSIGNMENT);
            Session.setMarksAssignmentSelected(null);
            goBackClicked();
        }
        // Just refresh the panes in case there was an information update
        else{
            switch(Session.getMarksSelectionType()){
                // Scene type is Degree
                case DEGREE:
                    refreshPanelsYears();
                    break;

                // Scene type is Year
                case YEAR:
                    refreshPanelsModules();
                    break;

                // Scene type is Module
                case MODULE:
                    // Assignment addition/editing could have changed some module data
                    double moduleGrade = Session.getMarksModuleSelected().getOverallGrade();
                    if(moduleGrade != -1) pane1Value.setText(moduleGrade + "%");
                    else pane1Value.setText("-");
                    pane2Value.setText(Session.getMarksModuleSelected().getPercentComplete() + "%");
                    refreshPanelsAssignments();
                    break;
            }
        }
    }

    /**
     * Method which refreshes panels with currently displayed Years.
     * Used by refreshPanels method.
     */
    private void refreshPanelsYears(){
        userYears = Session.getSession().getAllYears();
        // Updates the panels
        switch (userYears.size()){
            case 0:
                // Does nothing, because no panels to display
                break;
            case 1:
                optionalTitleLabel.setText("");
                if(pane5Pointer!=-1) loadPane5(userYears.get(pane5Pointer));
                else loadPane5(userYears.get(0));
                break;
            case 2:
                optionalTitleLabel.setText("");
                loadPane5(userYears.get(pane5Pointer));
                if(pane6Pointer!=-1) loadPane6(userYears.get(pane6Pointer));
                else loadPane6(userYears.get(1));
                break;
            default:
                optionalTitleLabel.setText("");
                loadPane5(userYears.get(pane5Pointer));
                loadPane6(userYears.get(pane6Pointer));
                if(pane7Pointer!=-1) loadPane7(userYears.get(pane7Pointer));
                else loadPane7(userYears.get(2));
        }
        determineNavigationVisibility(new ArrayList<>(userYears));
    }

    /**
     * Method which refreshes panels with currently displayed Modules.
     * Used by refreshPanels method.
     */
    private void refreshPanelsModules(){
        userModules = Session.getMarksYearSelected().getAllModules();
        // Updates the panels
        switch (userModules.size()){
            case 0:
                // Does nothing, because no panels to display
                break;
            case 1:
                optionalTitleLabel.setText("");
                if(pane5Pointer!=-1) loadPane5(userModules.get(pane5Pointer));
                else loadPane5(userModules.get(0));
                break;
            case 2:
                optionalTitleLabel.setText("");
                loadPane5(userModules.get(pane5Pointer));
                if(pane6Pointer!=-1) loadPane6(userModules.get(pane6Pointer));
                else loadPane6(userModules.get(1));
                break;
            default:
                optionalTitleLabel.setText("");
                loadPane5(userModules.get(pane5Pointer));
                loadPane6(userModules.get(pane6Pointer));
                if(pane7Pointer!=-1) loadPane7(userModules.get(pane7Pointer));
                else loadPane7(userModules.get(2));
        }
        determineNavigationVisibility(new ArrayList<>(userModules));
    }

    /**
     * Method which refreshes panels with currently displayed Assignments.
     * Used by refreshPanels method.
     */
    private void refreshPanelsAssignments(){
        userAssignments = Session.getMarksModuleSelected().getAllAssignments();
        // Updates the panels
        switch (userAssignments.size()){
            case 0:
                // Does nothing, because no panels to display
                break;
            case 1:
                optionalTitleLabel.setText(Session.getMarksModuleSelected().getFullName() + ".");
                if(pane5Pointer!=-1) loadPane5(userAssignments.get(pane5Pointer));
                else loadPane5(userAssignments.get(0));
                break;
            case 2:
                optionalTitleLabel.setText(Session.getMarksModuleSelected().getFullName() + ".");
                loadPane5(userAssignments.get(pane5Pointer));
                if(pane6Pointer!=-1) loadPane6(userAssignments.get(pane6Pointer));
                else loadPane6(userAssignments.get(1));
                break;
            default:
                optionalTitleLabel.setText(Session.getMarksModuleSelected().getFullName() + ".");
                loadPane5(userAssignments.get(pane5Pointer));
                loadPane6(userAssignments.get(pane6Pointer));
                if(pane7Pointer!=-1) loadPane7(userAssignments.get(pane7Pointer));
                else loadPane7(userAssignments.get(2));
        }
        determineNavigationVisibility(new ArrayList<>(userAssignments));
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
        goBackButton.setVisible(false);

        // Sets degree overall grade
        pane1Label.setText("Grade:");
        double userGrade = Session.getSession().getDegreeGrade();
        if (userGrade == -1) pane1Value.setText("-");
        else pane1Value.setText(userGrade + "%");

        // Sets how much of the degree is completed
        pane2Label.setText("Complete:");
        pane2Value.setText(Session.getSession().getDegreePercentComplete() + "%");

        // Set degree classification
        pane3Label.setText("Classification:");
        pane3Value.setText(Session.getSession().getClassification());

        // Hides unused top display panes
        pane4.setVisible(false);

        // Load years in pane5, pane6 & pane7
        switch(userYears.size()){
            case 0:
                optionalTitleLabel.setText("Add some Years to start getting Organised.");
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
        // Loads modules of the Year
        Year thisYear = Session.getMarksYearSelected();
        userModules = thisYear.getAllModules();

        // Sets the main titles of the page
        bigTitleLabel.setText("Year "+thisYear.getYearNumber()+".");
        optionalTitleLabel.setText("");

        // Sets up buttons
        button1.setVisible(true);
        button1Label.setText("Edit Year");
        button2Label.setText("Add Module");
        goBackButton.setVisible(true);

        // Sets Year overall grade
        pane1Label.setText("Overall   Grade:");
        double yearGrade = thisYear.getOverallGrade();
        if(yearGrade == -1) pane1Value.setText("-");
        else pane1Value.setText(yearGrade + "%");

        // Sets % Complete
        pane2Label.setText("Complete:");
        pane2Value.setText(thisYear.getPercentComplete() + "%");

        // Sets Autumn Grade
        pane3.setVisible(true);
        pane3Label.setText("Autumn Grade:");
        double springGrade = thisYear.getAutumnGrade();
        if(springGrade == -1) pane3Value.setText("-");
        else pane3Value.setText(springGrade + "%");

        // Sets Spring Grade
        pane4.setVisible(true);
        pane4Label.setText("Spring   Grade:");
        double autumnGrade = thisYear.getSpringGrade();
        if(autumnGrade == -1) pane4Value.setText("-");
        else pane4Value.setText(autumnGrade + "%");

        // Load modules in pane5, pane6 & pane7
        switch(userModules.size()){
            case 0:
                optionalTitleLabel.setText("Add some Modules to start getting Organised.");
                break;
            case 1:
                loadPane5(userModules.get(0));
                break;
            case 2:
                loadPane5(userModules.get(0));
                loadPane6(userModules.get(1));
                break;
            default:
                loadPane5(userModules.get(0));
                loadPane6(userModules.get(1));
                loadPane7(userModules.get(2));
        }

        // Hides module panes if unused
        hideUnusedPanes();

        // Configures navigation arrows
        determineNavigationVisibility(new ArrayList<>(userModules));

        // Sets the current Marks Tab Selection
        Session.setMarksSelectionType(MarksSelection.YEAR);
    }

    /**
     * Setups the scene with Module data (panels have assignment data)
     */
    private void loadModule(){
        // Loads assignments of the Module
        Module thisModule = Session.getMarksModuleSelected();
        userAssignments = thisModule.getAllAssignments();

        // Sets the main titles of the page
        bigTitleLabel.setText(thisModule.getCode() + ".");
        optionalTitleLabel.setText(thisModule.getFullName() + ".");

        // Sets up buttons
        button1.setVisible(true);
        button1Label.setText("Edit Module");
        button2Label.setText("Add Assignment");
        goBackButton.setVisible(true);

        // Sets Module overall grade
        pane1Label.setText("Grade:");
        double moduleGrade = thisModule.getOverallGrade();
        if(moduleGrade != -1) pane1Value.setText(moduleGrade + "%");
        else pane1Value.setText("-");

        // Sets Module % complete
        pane2Label.setText("Complete:");
        pane2Value.setText(thisModule.getPercentComplete() + "%");

        // Sets Module credits
        pane3Label.setText("Credits:");
        pane3Value.setText(Integer.toString(thisModule.getCredits()));

        // Sets Module semester
        pane4Label.setText("Semester:");
        pane4Value.setText(thisModule.getSemester().toString());

        // Loads assignments in pane5, pane6 & pane7
        switch(userAssignments.size()){
            case 0:
                optionalTitleLabel.setText("Add some Assignments to start getting Organised.");
                break;
            case 1:
                loadPane5(userAssignments.get(0));
                break;
            case 2:
                loadPane5(userAssignments.get(0));
                loadPane6(userAssignments.get(1));
                break;
            default:
                loadPane5(userAssignments.get(0));
                loadPane6(userAssignments.get(1));
                loadPane7(userAssignments.get(2));
        }

        // Hides assignment panes if unused
        hideUnusedPanes();

        // Configures navigation arrows
        determineNavigationVisibility(new ArrayList<>(userAssignments));

        // Sets the current Marks Tab Selection
        Session.setMarksSelectionType(MarksSelection.MODULE);
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

                // Grade
                pane5Label1.setText("Grade:");
                if (year.getOverallGrade() == -1) pane5Value1.setText("-");
                else pane5Value1.setText(year.getOverallGrade() + "%");

                // % Complete:
                pane5Label2.setText("Complete:");
                pane5Value2.setText(year.getPercentComplete() + "%");

                // Credits
                pane5Label3.setText("Credits:");
                pane5Value3.setText(Integer.toString(year.getCredits()));

                // Worth in percent
                pane5Label4.setText("Worth:");
                pane5Value4.setText(year.getPercentWorth() + "%");

                // Marks pane as loaded
                pane5Loaded = true;

                // Marks which year in a list of years was added
                pane5Pointer = userYears.indexOf(year);
                break;

            // If class is Module, setups the panel as Module
            case ("Module"):
                Module module = (Module) object;

                // Top Title
                pane5Title.setText(module.getCode());

                // Grade
                pane5Label1.setText("Grade:");
                double moduleGrade = module.getOverallGrade();
                if(moduleGrade !=- 1) pane5Value1.setText(moduleGrade + "%");
                else pane5Value1.setText("-");

                // Semester
                pane5Label2.setText("Semester:");
                pane5Value2.setText(module.getSemester().toString());

                // % Complete
                pane5Label3.setText("Complete:");
                pane5Value3.setText(module.getPercentComplete() + "%");

                // Credits
                pane5Label4.setText("Credits:");
                pane5Value4.setText(Integer.toString(module.getCredits()));

                // Marks pane as loaded
                pane5Loaded = true;

                // Marks which module in a list of modules was added
                pane5Pointer = userModules.indexOf(module);
                break;

            // If class is Assignment, setups the panel as Assignment
            case ("Assignment"):
                Assignment assignment = (Assignment) object;

                // Top Title
                pane5Title.setText(assignment.getFullName());

                // Grade
                pane5Label1.setText("Grade:");
                double assignmentGrade = assignment.getGrade();
                if (assignmentGrade != -1) pane5Value1.setText(assignment.getGrade() + "%");
                else pane5Value1.setText("-");

                // % Worth
                pane5Label2.setText("Worth:");
                pane5Value2.setText(assignment.getPercentWorth() + "%");

                // Resets unused elements
                pane5Label3.setText("");
                pane5Value3.setText("");
                pane5Label4.setText("");
                pane5Value4.setText("");

                // Marks pane as loaded
                pane5Loaded = true;

                // Marks which assignment in a list of assignments was added
                pane5Pointer = userAssignments.indexOf(assignment);
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

                // Grade
                pane6Label1.setText("Grade:");
                if (year.getOverallGrade() == -1) pane6Value1.setText("-");
                else pane6Value1.setText(year.getOverallGrade() + "%");

                // % Complete:
                pane6Label2.setText("Complete:");
                pane6Value2.setText(year.getPercentComplete() + "%");

                // Credits
                pane6Label3.setText("Credits:");
                pane6Value3.setText(String.valueOf(year.getCredits()));

                // Worth in percent
                pane6Label4.setText("Worth:");
                pane6Value4.setText(year.getPercentWorth() + "%");

                // Marks pane as loaded
                pane6Loaded = true;

                // Marks which year in a list of years was added
                pane6Pointer = userYears.indexOf(year);
                break;

            // If class is Module, setups the panel as Module
            case ("Module"):
                Module module = (Module) object;

                // Top Title
                pane6Title.setText(module.getCode());

                // Grade
                pane6Label1.setText("Grade:");
                double moduleGrade = module.getOverallGrade();
                if(moduleGrade !=- 1) pane6Value1.setText(moduleGrade + "%");
                else pane6Value1.setText("-");

                // Semester
                pane6Label2.setText("Semester:");
                pane6Value2.setText(module.getSemester().toString());

                // % Complete
                pane6Label3.setText("Complete");
                pane6Value3.setText(module.getPercentComplete() + "%");

                // Credits
                pane6Label4.setText("Credits:");
                pane6Value4.setText(Integer.toString(module.getCredits()));

                // Marks pane as loaded
                pane6Loaded = true;

                // Marks which module in a list of modules was added
                pane6Pointer = userModules.indexOf(module);
                break;

            // If class is Assignment, setups the panel as Assignment
            case ("Assignment"):
                Assignment assignment = (Assignment) object;

                // Top Title
                pane6Title.setText(assignment.getFullName());

                // Grade
                pane6Label1.setText("Grade:");
                double assignmentGrade = assignment.getGrade();
                if (assignmentGrade != -1) pane6Value1.setText(assignment.getGrade() + "%");
                else pane6Value1.setText("-");

                // % Worth
                pane6Label2.setText("Worth:");
                pane6Value2.setText(assignment.getPercentWorth() + "%");

                // Resets unused elements
                pane6Label3.setText("");
                pane6Value3.setText("");
                pane6Label4.setText("");
                pane6Value4.setText("");

                // Marks pane as loaded
                pane6Loaded = true;

                // Marks which assignment in a list of assignments was added
                pane6Pointer = userAssignments.indexOf(assignment);
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

                // Grade
                pane7Label1.setText("Grade:");
                if (year.getOverallGrade() == -1) pane7Value1.setText("-");
                else pane7Value1.setText(year.getOverallGrade() + "%");

                // % Complete:
                pane7Label2.setText("Complete:");
                pane7Value2.setText(year.getPercentComplete() + "%");

                // Credits
                pane7Label3.setText("Credits:");
                pane7Value3.setText(String.valueOf(year.getCredits()));

                // Worth in percent
                pane7Label4.setText("Worth:");
                pane7Value4.setText(year.getPercentWorth() + "%");

                // Marks pane as loaded
                pane7Loaded = true;

                // Marks which year in a list of years was added
                pane7Pointer = userYears.indexOf(year);
                break;

            // If class is Module, setups the panel as Module
            case ("Module"):
                Module module = (Module) object;

                // Top Title
                pane7Title.setText(module.getCode());

                // Grade
                pane7Label1.setText("Grade:");
                double moduleGrade = module.getOverallGrade();
                if(moduleGrade !=- 1) pane7Value1.setText(moduleGrade + "%");
                else pane7Value1.setText("-");

                // Semester
                pane7Label2.setText("Semester:");
                pane7Value2.setText(module.getSemester().toString());

                // % Complete
                pane7Label3.setText("Complete");
                pane7Value3.setText(module.getPercentComplete() + "%");

                // Credits
                pane7Label4.setText("Credits:");
                pane7Value4.setText(Integer.toString(module.getCredits()));

                // Marks pane as loaded
                pane7Loaded = true;

                // Marks which module in a list of modules was added
                pane7Pointer = userModules.indexOf(module);
                break;

            // If class is Assignment, setups the panel as Assignment
            case ("Assignment"):
                Assignment assignment = (Assignment) object;

                // Top Title
                pane7Title.setText(assignment.getFullName());

                // Grade
                pane7Label1.setText("Grade:");
                double assignmentGrade = assignment.getGrade();
                if (assignmentGrade != -1) pane7Value1.setText(assignment.getGrade() + "%");
                else pane7Value1.setText("-");

                // % Worth
                pane7Label2.setText("Worth:");
                pane7Value2.setText(assignment.getPercentWorth() + "%");

                // Resets unused elements
                pane7Label3.setText("");
                pane7Value3.setText("");
                pane7Label4.setText("");
                pane7Value4.setText("");

                // Marks pane as loaded
                pane7Loaded = true;

                // Marks which assignment in a list of assignments was added
                pane7Pointer = userAssignments.indexOf(assignment);
                break;
        }
    }

    /**
     * Helper method which updates the data in panels 5, 6 & 7 after
     * a navigation arrow was clicked.
     * Also updates the visibility of navigation arrows.
     */
    private void panelsUpdateNavigation(){
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
        Session.setMarksPopupType(PopupType.EDIT);

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
        Session.setMarksPopupType(PopupType.ADD);

        // Creates the popup
        MarksSelection actionViewName = Session.getMarksSelectionType().next();
        Stage popup = new Stage();
        new PopupStage(popup, "MarksPopupView"+actionViewName+".fxml");

        // If popup gets closed from the taskbar, forwards to popupClosed method
        popup.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::popupClosedFromTaskBar);
    }

    /**
     * Method which handles the change of scene view based on the user's selection
     * and the given pointer, to identify the selected Pane.
     *
     * @param panePointer pointer to select the relevant object
     */
    private void paneButtonClicked(int panePointer) throws IOException {
        // Based on current selection, changes it and changes the displayed data accordingly
        MarksSelection currentSelection = Session.getMarksSelectionType();
        switch(currentSelection){
            // Populates the scene with selected Year information
            case DEGREE:
                Session.setMarksSelectionType(MarksSelection.YEAR);
                Session.setMarksYearSelected(userYears.get(panePointer));
                cleanCurrentSelection();
                loadYear();
                break;

            // Populates the scene with selected Module information
            case YEAR:
                Session.setMarksSelectionType(MarksSelection.MODULE);
                Session.setMarksModuleSelected(userModules.get(panePointer));
                cleanCurrentSelection();
                loadModule();
                break;

            // Creates a popup for assignment
            case MODULE:
                // Sets popup type
                Session.setMarksPopupType(PopupType.EDIT);
                Session.setMarksAssignmentSelected(userAssignments.get(panePointer));

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
     * Interaction with Pane 5.
     * @throws IOException if popup fxml is not found
     */
    @FXML
    private void pane5ButtonClicked() throws IOException {
        // Forwards to paneButtonClicked method
        paneButtonClicked(pane5Pointer);
    }

    /**
     * Method which handles the change of scene view based on the user's selection.
     * Interaction with Pane 6.
     * @throws IOException if popup fxml is not found
     */
    @FXML
    private void pane6ButtonClicked() throws IOException {
        // Forwards to paneButtonClicked method
        paneButtonClicked(pane6Pointer);
    }

    /**
     * Method which handles the change of scene view based on the user's selection.
     * Interaction with Pane 7.
     * @throws IOException if popup fxml is not found
     */
    @FXML
    private void pane7ButtonClicked() throws IOException {
        // Forwards to paneButtonClicked method
        paneButtonClicked(pane7Pointer);
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
        panelsUpdateNavigation();
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
        panelsUpdateNavigation();
    }

    /**
     * Method which changes the screen back to the previously visited screen
     * when goBack button is pressed.
     */
    public void goBackClicked() {
        switch(Session.getMarksSelectionType()){
            case DEGREE:
                // Nothing, there's no go back button on Degree display
                break;
            case YEAR:
                Session.setMarksSelectionType(MarksSelection.DEGREE);
                Session.setMarksYearSelected(null);
                cleanCurrentSelection();
                loadDegree();
                break;
            case MODULE:
                Session.setMarksSelectionType(MarksSelection.YEAR);
                Session.setMarksModuleSelected(null);
                cleanCurrentSelection();
                loadYear();
                break;
            case ASSIGNMENT:
                // In case user deletes an Assignment
                cleanCurrentSelection();
                loadModule();
                break;
        }
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
