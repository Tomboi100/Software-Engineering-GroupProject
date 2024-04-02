package uk.ac.aber.cs221.gp03.menu;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import uk.ac.aber.cs221.gp03.game.*;
import uk.ac.aber.cs221.gp03.utility.SceneControl;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML I/O handler class for Main Menu screen
 *
 * @author Luke Purnell
 * @author Olek Sadkowski
 * @author James Croucher
 * @version 06/05/2022
 */
public class MainMenuController implements Initializable {

    @FXML
    private ImageView oceanWaves;
    @FXML
    private ImageView ship;
    @FXML
    private ImageView waveLeft;
    @FXML
    private ImageView waveRight;
    @FXML
    private VBox menuBox;

    /**
     * Launch the game screen.
     */
    @FXML
    private void startGame() {
        SceneControl.primaryStage.setScene(SceneControl.playerMenuScene);
        SceneControl.primaryStage.setTitle("Buccaneer Game");

    }

    /**
     * Continues a game from a save.json save file.
     *
     * Will not return successfully if there is no save file or the save file is corrupted.
     */
    @FXML
    private void continueGame() throws IOException {
        // random values
        String[] playerNames = {"", "", "", ""};
        ColorType[] playerColors = {ColorType.Blue, ColorType.Green, ColorType.Orange, ColorType.Purple};
        GameLogic.setPlayerValues(playerNames, playerColors);
        FXMLLoader loader = new FXMLLoader(SceneControl.class.getResource("/fxml/Game.fxml"));
        SceneControl.gameScene = new Scene(loader.load());
        SceneControl.gameCtrl = loader.getController();
        if(SceneControl.gameCtrl == null) {
            PopUp.display("Error", "Failed to initialize game controller", 100, 400);
            return;
        }
        try {
            SceneControl.gameCtrl.setGameStateFromFile("./save.json");
        } catch(IOException ex) {
            PopUp.display("Error", "Failed to load game from save file", 100, 400);
            return;
        }

        SceneControl.primaryStage.setScene(SceneControl.gameScene);
        SceneControl.primaryStage.setTitle("Buccaneer Game");
    }

    /**
     * Terminate program.
     */
    @FXML
    private void quit() {
        System.exit(0);
    }

    /**
     * Temporary method linked to a button to launch the battle screen
     */
    @FXML
    private void battle() {
        SceneControl.primaryStage.setScene(SceneControl.battleScene);
        SceneControl.primaryStage.setTitle("Battle");
    }

    /**
     * Sets hand cursor for all buttons and begins wave animation
     * @param url the url of the images
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SceneControl.mainMenuCtrl = this;
        objectTranslation(oceanWaves,-30, 50, 0, 0, 5000);
        objectTranslation(waveLeft, -30, 30, 0, 0, 5000);
        objectTranslation(waveRight, -30, 30, 0, 0, 5000);
        objectRotation(ship, 5000, 10, -10);
    }

    /**
     * Translate an object from point x1 to x2 and from y1 to y2
     * @param node The node to be translated
     * @param x1 from x coordinate
     * @param x2 to x coordinate
     * @param y1 from y coordinate
     * @param y2 to y coordinate
     * @param duration time taken for the translation to be completed
     */
    public void objectTranslation(Node node, int x1, int x2, int y1, int y2, int duration){
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(node);
        translate.setDuration(Duration.millis(duration));
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setFromX(x1);
        translate.setToX(x2);
        translate.setFromY(y1);
        translate.setToY(y2);
        translate.setAutoReverse(true);
        translate.play();
    }

    /**
     * Rotate an object from one angle to another
     * @param node The Node to be rotated
     * @param duration How long the rotation should take
     * @param fromAngle Original angle
     * @param toAngle The final angle
     */
    public void objectRotation(Node node, int duration, int fromAngle, int toAngle){
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(node);
        rotate.setDuration(Duration.millis(duration));
        rotate.setCycleCount(TranslateTransition.INDEFINITE);
        rotate.setFromAngle(fromAngle);
        rotate.setToAngle(toAngle);
        rotate.setAutoReverse(true);
        rotate.play();
    }

    /**
     * Helper class to change cursor of a button
     * @param btn button which cursor needs to be changed
     * @param cursor new cursor to be set
     */
    public void switchCursor(Button btn, Cursor cursor){
        btn.setOnMouseEntered((event) -> {
            btn.setCursor(cursor);
        });
    }
}
