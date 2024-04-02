package uk.ac.aber.cs221.gp03.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uk.ac.aber.cs221.gp03.utility.SceneControl;

import java.io.IOException;

/**
 * FXML I/O handler class for Chance card display screen
 *
 * @author Tommy Boyle (tob31)
 * @version 06/05/2022
 */
public class ChanceCardController {
    @FXML
    Label chanceLabel;

    @FXML
    Button btnChanceClose;

    public void initialize() {
        SceneControl.chanceCtrl = this;
    }

    /**
     * Creates the window/stage and displays the chance cards as text on the screen
     * @param chanceTxt the text for the chance cards
     */
    @FXML
    public void DisplayChanceCards(String chanceTxt) throws IOException {
        Stage ChanceCardWindow = new Stage();
        ChanceCardWindow.initModality(Modality.APPLICATION_MODAL);
        ChanceCardWindow.setTitle("ChanceCardWindow");
        ChanceCardWindow.setMinHeight(600);
        ChanceCardWindow.setMinWidth(800);
        Scene chanceScene = SceneControl.chanceScene;
        ChanceCardWindow.setScene(chanceScene);
        chanceLabel.setText(chanceTxt);
        ChanceCardWindow.showAndWait();
    }

    /**
     * Closes the window
     * @param event the mouse event using to button
     */
    @FXML
    public void ChanceClose(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}

