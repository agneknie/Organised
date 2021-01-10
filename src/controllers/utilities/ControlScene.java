package controllers.utilities;

import database.Database;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControlScene {
    /**
     * Method which closes the window when close button is clicked.
     * Also closes the database connection.
     *
     * @param event used for identifying the scene
     */
    @FXML
    public static void closeWindow(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    /**
     * Method which minimises the window when minimize button is clicked.
     *
     * @param event used for identifying the scene
     */
    @FXML
    public static void minimizeWindow(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Changes styling of the button if it's hovered.
     *
     * @param button button to change the style of
     */
    public static void buttonHovered(Button button){
        button.setStyle("-fx-background-color: #E6BB9A; -fx-background-radius: 10");
    }

    /**
     * Reverts to regular styling of the button if it's no longer
     * hovered.
     *
     * @param button button to change the style of
     */
    public static void buttonExited(Button button){
        button.setStyle("-fx-background-color: white; -fx-background-radius: 10");
    }
}
