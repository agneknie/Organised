package controllers.utilities;

import core.Session;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
 * Abstract class for default buttons. Enables styling and functionality
 * of minimize and close buttons.
 * Also implements methods which enable stage dragging.
 */
public abstract class DefaultButtons {
    // Navigation buttons
    @FXML
    private ImageView closeButton;
    @FXML
    private ImageView minimizeButton;

    /**
     * Method which saves the current mouse coordinates in the Session variable.
     * Used for dragging the stage.
     * @param event MouseEvent from which the coordinates are gotten
     */
    @FXML
    public void mousePressed(MouseEvent event){
        Session.setXMouseOffset(event.getSceneX());
        Session.setYMouseOffset(event.getSceneY());
    }

    /**
     * Method which moves the stage when dragged.
     * Used for the main application window.
     * @param event MouseEvent from which the coordinates are gotten
     */
    @FXML
    public void mouseDragged(MouseEvent event){
        Session.getMainStage().setX(event.getScreenX() - Session.getXMouseOffset());
        Session.getMainStage().setY(event.getScreenY() - Session.getYMouseOffset());
    }

    /**
     * Method which moves the stage when dragged.
     * Used for the popup windows.
     * @param event MouseEvent from which the coordinates are gotten
     */
    @FXML
    public void mouseDraggedPopup(MouseEvent event){
        Session.getPopupStage().setX(event.getScreenX() - Session.getXMouseOffset());
        Session.getPopupStage().setY(event.getScreenY() - Session.getYMouseOffset());
    }

    /**
     * Method which closes the window when close button is clicked.
     * Refers to class ControlStage method closeWindow.
     *
     * @param event used for getting the scene
     */
    @FXML
    public void closeClicked(MouseEvent event) {
        ControlScene.closeWindow(event);
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
}
