package uk.ac.aber.cs221.gp03.game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uk.ac.aber.cs221.gp03.grid.Tile;
import uk.ac.aber.cs221.gp03.items.Treasure;
import uk.ac.aber.cs221.gp03.items.TreasureType;
import uk.ac.aber.cs221.gp03.menu.HelpMenuController;
import uk.ac.aber.cs221.gp03.utility.SceneControl;
import javafx.scene.control.Label;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 * This class is the FXML I/O handler class for Game screen
 *
 * @author Luke Purnell
 * @author Tommy Boyle (tob31)
 * @version 06/05/2022
 */
public class GameController extends GameLogic  {
    @FXML
    AnchorPane gamePane;
    @FXML
    Label locLbl, bearingLbl, homeLbl, playerNameLbl, playerNumLbl, moveLbl, barrelLabel, pearlLabel, diamondLabel, rubyLabel,goldLabel;

    String chanceTxt,crewTxt,blackcrewcard1,blackcrewcard2,blackcrewcard3,redcrewcard1,redcrewcard2,redcrewcard3;

    @FXML
    public Label infoTitle;
    @FXML
    public Label infoBody;
    @FXML
    public Label diamondPortLabel, goldPortLabel, barrelPortLabel, rubyPortLabel, pearlPortLabel;
    @FXML
    public ImageView diamondPortImage, goldPortImage, barrelPortImage, rubyPortImage, pearlPortImage;
    @FXML
    public Button moreTreasure;


    /**
     * This method initializes the board and game graphics
     */
    @FXML
    public void initialize() {
        SceneControl.gameCtrl = this;
        Tile curTile;
        for (int x = 0; x < getBoard.BOARD_SIZE; x++) {
            for (int y = 0; y < getBoard.BOARD_SIZE; y++) {
                curTile = getBoard.tiles[x][y];
                curTile.generateGraphic(20);
                gamePane.getChildren().add(curTile.getTileGraphic());
            }
        }
    }

    /**
     * This method is a back button that returns you to the player menu
     */
    @FXML
    private void backButton(){
        SceneControl.primaryStage.setScene(SceneControl.playerMenuScene);
    }

    /**
     * This method checks if two objects have the same class
     * @param o Object to compare against
     * @return true if the objects have the same class
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }

    /**
     * Temporary method linked to a button to launch the trade screen
     */
    @FXML
    private void trade() {
        SceneControl.primaryStage.setScene(SceneControl.tradeScene);
        SceneControl.primaryStage.setTitle("Buccaneer Game");
    }

    /**
     * This method opens the help screen
     * @throws IOException
     */
    @FXML
    private void openHelp() throws IOException {
        SceneControl.helpCtrl.displayHelp();
    }

    /**
     * This method opens the chance cards in the players hand
     * @throws IOException
     */
    @FXML
    private void openChanceCards() throws IOException {
        SceneControl.chanceCtrl.DisplayChanceCards(chanceTxt);
    }

    /**
     * Debug button which adds 1 treasure of value 2 to current ship
     */
    @FXML
    private void addTreasureButton() {
        this.getCurrentPlayer().getHomePort().getTreasures().add(new Treasure(TreasureType.BarrelsOfRum));
    }

    /**
     * This method opens the crew cards in the players hand
     * @throws IOException
     */
    @FXML
    private void openCrewCards() throws IOException {
        SceneControl.crewCtrl.DisplayCrewCards(crewTxt,blackcrewcard1,blackcrewcard2,blackcrewcard3,redcrewcard1,redcrewcard2,redcrewcard3);
    }

    @FXML
    private void battleTest() {
        Battle.startAttack(ships[1], ships[2]);
    }
}
