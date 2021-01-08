package controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginPageController {

    public ImageView closeButton;
    public ImageView minimizeButton;

    /**
     * Method which closes the window when close button is clicked
     *
     * @param event
     */
    @FXML
    void closeClicked(MouseEvent event) {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Method which minimises the window when minimize button is clicked
     *
     * @param event
     */
    @FXML
    void minimizeClicked(MouseEvent event) {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }
}
