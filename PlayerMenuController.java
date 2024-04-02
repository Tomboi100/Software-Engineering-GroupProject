package uk.ac.aber.cs221.gp03.menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import uk.ac.aber.cs221.gp03.game.GameLogic;
import uk.ac.aber.cs221.gp03.game.PopUp;
import uk.ac.aber.cs221.gp03.utility.SceneControl;

import java.io.IOException;

/**
 * FXML I/O handler class for Player Menu screen
 *
 * @author James Falkner
 * @author James Croucher
 * @author Steffan Davies-John
 * @author Tommy Boyle
 * @version 06/05/2022
 */
public class PlayerMenuController {
    ObservableList<String> colourList = FXCollections.observableArrayList("Green", "Blue", "Orange", "Purple", "Red", "Yellow");

    @FXML
    private ChoiceBox choicePlay1;
    @FXML
    private ChoiceBox choicePlay2;
    @FXML
    private ChoiceBox choicePlay3;
    @FXML
    private ChoiceBox choicePlay4;
    @FXML
    private TextField txtPlay1;
    @FXML
    private TextField txtPlay2;
    @FXML
    private TextField txtPlay3;
    @FXML
    private TextField txtPlay4;

    /**
     * Validation states when checking player names and colors.
     */
    public enum PlayerValidationState {
        VALID,
        NAME_INVALID_LENGTH,
        NAME_CONTAINS_NUMBERS,
        NAME_COLLISION,
        COLOR_COLLISION,
        COLOR_UNSELECTED
    }

    /**
     * Returns an error message based on a validation state.
     *
     * @param state The state to return an error for.
     * @return The error message
     */
    private String getErrorForState(PlayerValidationState state) {
        return switch (state) {
            case NAME_INVALID_LENGTH -> "Player names must be between 4 and 20 characters long.";
            case NAME_CONTAINS_NUMBERS -> "Player names cannot contain numbers or special characters.";
            case NAME_COLLISION -> "Player names must be unique.";
            case COLOR_COLLISION -> "Player colors must be unique.";
            case COLOR_UNSELECTED -> "A player does not have a color selected.";
            default -> "";
        };
    }

    /**
     * Validates that player names and colors are valid, and there are no collisions
     * where multiple players share the same properties.
     *
     * @param playerNames  The names of the players.
     * @param playerColors The colors of the players.
     */
    public static PlayerValidationState validateInputs(String[] playerNames, Object[] playerColors) {
        for (int index = 0; index < 4; index++) {
            if (playerNames[index].length() > 20 || playerNames[index].length() < 3) {
                return PlayerValidationState.NAME_INVALID_LENGTH;
            } else if (!playerNames[index].matches("[a-zA-Z]+")) {
                return PlayerValidationState.NAME_CONTAINS_NUMBERS;
            } else if (playerColors[index] == null) {
                return PlayerValidationState.COLOR_UNSELECTED;
            }
            for (int index2 = index + 1; index2 < 4; index2++) {
                if (playerNames[index].equals(playerNames[index2]) && playerColors[index].equals(playerColors[index2])) {
                    return PlayerValidationState.NAME_COLLISION;
                } else if(playerColors[index].equals(playerColors[index2])) {
                    return PlayerValidationState.COLOR_COLLISION;
                }
            }
        }

        return PlayerValidationState.VALID;
    }

    /**
     * This method checks that the names and colours are valid inputs
     * @throws IOException
     */
    @FXML
    private void checkInput() throws IOException {
        String[] playerNames = {txtPlay1.getText(), txtPlay2.getText(), txtPlay3.getText(), txtPlay4.getText()};
        Object[] playerColors = {choicePlay1.getValue(), choicePlay2.getValue(), choicePlay3.getValue(), choicePlay4.getValue()};
        PlayerValidationState state = PlayerMenuController.validateInputs(playerNames, playerColors);
        if (state != PlayerValidationState.VALID) {
            PopUp.display("Error!", getErrorForState(state),300,400);
            return;
        }
        GameLogic.setPlayerValues(playerNames, playerColors);
        FXMLLoader loader = new FXMLLoader(SceneControl.class.getResource("/fxml/Game.fxml"));
        SceneControl.gameScene = new Scene(loader.load());
        SceneControl.gameCtrl.addShips();
        SceneControl.gameCtrl.runGame();
        SceneControl.primaryStage.setScene(SceneControl.gameScene);
        SceneControl.primaryStage.setTitle("Buccaneer Game");
    }

    /**
     * This method initializes the lists of the choice boxes
     */
    @FXML
    private void initialize() {
        SceneControl.playerMenuCtrl = this;
        choicePlay1.setItems(colourList);
        choicePlay2.setItems(colourList);
        choicePlay3.setItems(colourList);
        choicePlay4.setItems(colourList);
    }

    /**
     * This method is a back button back to main menu
     */
    @FXML
    private void backButton() {
        SceneControl.primaryStage.setScene(SceneControl.mainMenuScene);
    }


    /**
     * This method is a button that displays the help screen
     */
    @FXML
    private void help() throws IOException {
        SceneControl.helpCtrl.displayHelp();
    }

}
