package controllers.utilities;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Abstract class for popup stages. Enables styling and functionality
 * of minimize and close buttons.
 */
public abstract class popupNavigation {
    // Navigation buttons
    @FXML
    private ImageView closeButton;
    @FXML
    private ImageView minimizeButton;

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
