package uk.ac.aber.cs221.gp03.game;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import uk.ac.aber.cs221.gp03.utility.SceneControl;

import java.util.ArrayList;

/**
 * This is the FXML I/O handler class for Battle class
 * @version 06/05/22
 * @author
 */
public class BattleController {
    @FXML
    private Label winnerLabel;

    @FXML
    public Label lootLabel;

    /**
     * This method initializes the Battle controller
     */
    @FXML
    public void initialize() {
        SceneControl.battleCtrl = this;
    }

    /**
     * This method populates the labels displayed in the battle scene
     * @param winnerName Name of the winner
     * @param loserName Name of the loser
     * @param lootText The loot the winner has gained
     */
    public void populateText(String winnerName, String loserName, ArrayList<String> lootText) {
        winnerLabel.setText(winnerName + " defeated " + loserName + "!!!");
        StringBuilder lootString = new StringBuilder();
        lootString.append(winnerName).append( " looted:\n");
        for (String s: lootText) {
            lootString.append(s).append('\n');
        }
        lootLabel.setText(lootString.toString());

    }

    /**
     * This method returns the player to the game.
     */
    @FXML
    private void back() {
        SceneControl.primaryStage.setScene(SceneControl.gameScene);
    }

}
