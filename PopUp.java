package uk.ac.aber.cs221.gp03.game;

import javafx.scene.image.Image;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/**
 * Class to create message popups for errors or warnings
 * @author James Falkner
 * @version 13/04/2022
 */



public class PopUp {

    /**
     * This method  is used to display popup message
     * @param title the title to be displayed
     * @param message the message to be displayed
     * @param width the width of the popup
     * @param height the height of the popup
     */
    public static void display(String title, String message, int width, int height) {
        Stage popUp = new Stage();

        popUp.initModality(Modality.APPLICATION_MODAL);
        popUp.setTitle(title);
        popUp.setMinHeight(width);
        popUp.setMinWidth(height);

        Label label = new Label();
        label.setText(message);

        Button btnClose = new Button("Close");
        btnClose.setOnAction(e -> popUp.close());

        VBox structure = new VBox(25);
        structure.getChildren().addAll(label,btnClose);
        structure.setAlignment(Pos.CENTER);

        Scene scene = new Scene(structure);
        popUp.setScene(scene);
        popUp.showAndWait();

    }
}
