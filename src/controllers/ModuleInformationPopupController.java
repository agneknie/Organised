package controllers;

import controllers.utilities.ControlScene;
import controllers.utilities.DefaultNavigation;
import core.Module;
import core.Session;
import core.Year;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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
 * Controller for Module Information popup in the Schedule & Tasks tabs.
 * Used when user wishes to view information about modules, which are
 * present in the selected period.
 */
public class ModuleInformationPopupController extends DefaultNavigation implements Initializable{
    // Title label
    @FXML
    private Label titleLabel;

    // More button
    @FXML
    private Pane moreButton;
    @FXML
    private ImageView moreButtonImage;
    @FXML
    private Label moreButtonLabel;

    // Close button
    @FXML
    private Pane actionButton;
    @FXML
    private ImageView actionButtonImage;
    @FXML
    private Label actionButtonLabel;

    // Panes for module display
    @FXML
    private Pane pane1;
    @FXML
    private Pane pane2;
    @FXML
    private Pane pane3;
    @FXML
    private Pane pane4;
    @FXML
    private Pane pane5;
    private final List<Pane> modulePanes = new ArrayList<>();

    // User specific variables
    private List<Module> userModules;
    private int currentlyDisplayedBatch;

    // Number of panes on the window
    final int MAX_PANES = 5;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int yearNumber = 0;     // Placeholder year number

        // Gets all modules of the year associated with selected period#
        if(Session.getModuleInformationOrigin().equals("Schedule"))
            yearNumber = Session.getSchedulePeriodSelected().getAssociatedYear();
        else if(Session.getModuleInformationOrigin().equals("Tasks"))
            yearNumber = Session.getTasksPeriodSelected().getAssociatedYear();


        int userId = Session.getSession().getId();
        userModules = Year.yearFromUserIdAndNumber(userId, yearNumber).getAllModules();

        // Adds module display panes to the list of module panes
        addModulePanes();

        // Setups the table which displays module data
        currentlyDisplayedBatch = 0;
        updateModuleTable();

        // Determines visibility of moreButton
        moreButton.setVisible(userModules.size() > MAX_PANES);
    }

    /**
     * Method which adds the panes for module display to
     * the module pane list
     */
    private void addModulePanes(){
        modulePanes.add(pane1);
        modulePanes.add(pane2);
        modulePanes.add(pane3);
        modulePanes.add(pane4);
        modulePanes.add(pane5);
    }

    /**
     * Method which setups the module table with
     * relevant module information.
     */
    private void updateModuleTable(){
        // Constant for colour to change text colour
        final double COLOR_THRESHOLD = 500.0;

        // Makes all panes visible in case they weren't before
        for(Pane pane : modulePanes){
            pane.setVisible(true);
        }

        // Takes modules, which will be displayed
        List<Module> displayedModules = new ArrayList<>();
        for(int i = currentlyDisplayedBatch*MAX_PANES; i<userModules.size(); i++){
            displayedModules.add(userModules.get(i));
        }

        // Counts panes which will be visible/have info in them
        int panesToSetup = MAX_PANES;
        if(MAX_PANES-displayedModules.size()>0) panesToSetup = MAX_PANES-(MAX_PANES-displayedModules.size());

        // Setups the panes with modules
        for(int i = 0; i<panesToSetup; i++){
            // Gets all pane elements separately
            Pane currentPane = modulePanes.get(i);
            Label code = (Label) currentPane.getChildren().get(0);
            Label name = (Label) currentPane.getChildren().get(1);

            // Sets the labels with module data
            code.setText(displayedModules.get(i).getCode());
            name.setText(displayedModules.get(i).getFullName());

            // Sets the colour of the pane
            currentPane.setStyle("-fx-background-color: "+displayedModules.get(i).getColourAsString()+"; " +
                    "-fx-background-radius: 10;");

            // Configures text colour if background is too fair
            Color moduleColor = displayedModules.get(i).getColour();
            double colourOverall = moduleColor.getRed()*255 + moduleColor.getBlue()*255 + moduleColor.getGreen()*255;
            if(colourOverall >= COLOR_THRESHOLD){
                code.setTextFill(Paint.valueOf("#2B2B2B"));
                name.setTextFill(Paint.valueOf("#2B2B2B"));
            }
            else{
                code.setTextFill(Paint.valueOf("#FFFFFF"));
                name.setTextFill(Paint.valueOf("#FFFFFF"));
            }
        }

        // Determines pane visibility if not all are filled
        if(panesToSetup<MAX_PANES){
            for(int panesLeft = MAX_PANES-panesToSetup; panesLeft>0; panesLeft--){
                modulePanes.get(MAX_PANES-panesLeft).setVisible(false);
            }
        }
    }

    // Methods concerning button clicks
    /**
     * Method which closes the popup when actionButton (close button)
     * is clicked.
     * @param event event to get the stage from
     */
    @FXML
    void actionButtonClicked(MouseEvent event) {
        // Closes the popup
        ControlScene.closeWindow(event);
    }

    /**
     * Method which changes the displayed modules.
     * Only visible when there are more modules than
     * module display panes.
     */
    @FXML
    void moreButtonClicked() {
        // Counts the number of batches
        int batches = userModules.size()/MAX_PANES-1;   // -1 because Batch numbering starts from 0
        if(userModules.size()%MAX_PANES!=0) batches++;

        // Updates the batch
        if(currentlyDisplayedBatch==batches) currentlyDisplayedBatch = 0;
        else currentlyDisplayedBatch++;

        // Updates the module panes
        updateModuleTable();
    }

    // Methods for styling scene elements
    /**
     * Method which reverts actionButton (close button) styling back to
     * normal when hover ends.
     */
    @FXML
    void actionButtonExited() {
        ControlScene.buttonExited(actionButton, actionButtonImage, actionButtonLabel, "close_icon.png");
    }

    /**
     * Method which changes the styling of actionButton(close button)
     * when hovered.
     */
    @FXML
    void actionButtonHovered() {
        ControlScene.buttonHovered(actionButton, actionButtonImage, actionButtonLabel, "close_icon_black.png");
    }

    /**
     * Method which reverts moreButton styling back to normal when hover ends.
     */
    @FXML
    void moreButtonExited() {
        ControlScene.buttonExited(moreButton, moreButtonImage, moreButtonLabel, "more_icon.png");
    }

    /**
     * Method which changes the styling of moreButton
     * when hovered.
     */
    @FXML
    void moreButtonHovered() {
        ControlScene.buttonHovered(moreButton, moreButtonImage, moreButtonLabel, "more_icon_selected.png");
    }
}
