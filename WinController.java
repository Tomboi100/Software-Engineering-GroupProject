package uk.ac.aber.cs221.gp03.menu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import uk.ac.aber.cs221.gp03.utility.SceneControl;
import javafx.scene.control.Button;

public class WinController {
    @FXML
    private Button btnWinMenu;

    @FXML
    private Label winnerLabel;

    public void initialize() {
        SceneControl.winCtrl = this;
    }

    /**
     * This method changes the label to display the winners name
     * @param winnerName the winning player name
     * @param maxTreasure the treasure collected
     */
    public void changeText(String winnerName, int maxTreasure) {
        winnerLabel.setText(winnerName + " has reached " + maxTreasure + " treasure!");
    }

    /**
     * This method returns the user to the main menu
     */
    @FXML
    private void home(){
        SceneControl.primaryStage.setScene(SceneControl.mainMenuScene);
    }
}
