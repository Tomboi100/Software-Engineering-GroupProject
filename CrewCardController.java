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
 * FXML I/O handler class for Crew card card display screen
 *
 * @author Tommy Boyle (tob31)
 * @version 06/05/2022
 */
public class CrewCardController {
    @FXML
    Label redCrew1, redCrew2, redCrew3, blackCrew1, blackCrew2, blackCrew3;

    @FXML
    Button btnCrewClose;

    public void initialize() {
        SceneControl.crewCtrl = this;
    }

    /**
     * Creates the window/stage and displays the count of the crew cards as an integer that increments
     * @param chanceTxt the text for the cahnce card
     * @param blackcrewcard1 black crew value 1
     * @param blackcrewcard2 black crew value 2
     * @param blackcrewcard3 black crew value 3
     * @param redcrewcard1 red crew value 1
     * @param redcrewcard2 red crew value 2
     * @param redcrewcard3 red crew value 3
     * @throws IOException
     */
    @FXML
    public void DisplayCrewCards(String chanceTxt, String blackcrewcard1, String blackcrewcard2, String blackcrewcard3, String redcrewcard1, String redcrewcard2, String redcrewcard3) throws IOException {
        Stage ChanceCardWindow = new Stage();
        ChanceCardWindow.initModality(Modality.APPLICATION_MODAL);
        ChanceCardWindow.setTitle("crewCardWindow");
        ChanceCardWindow.setMinHeight(600);
        ChanceCardWindow.setMinWidth(800);
        Scene chanceScene = SceneControl.crewScene;
        ChanceCardWindow.setScene(chanceScene);
        redCrew1.setText(redcrewcard1);
        redCrew2.setText(redcrewcard2);
        redCrew3.setText(redcrewcard3);
        blackCrew1.setText(blackcrewcard1);
        blackCrew2.setText(blackcrewcard2);
        blackCrew3.setText(blackcrewcard3);

        ChanceCardWindow.showAndWait();
    }

    /**
     * Closes the window
     * @param event the mouse event to close the window
     */
    @FXML
    public void CrewClose(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}
