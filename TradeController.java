package uk.ac.aber.cs221.gp03.game;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import uk.ac.aber.cs221.gp03.grid.Board;
import uk.ac.aber.cs221.gp03.grid.Port;
import uk.ac.aber.cs221.gp03.items.*;
import uk.ac.aber.cs221.gp03.utility.SceneControl;

import java.util.ArrayList;
import java.util.Objects;

/**
 * FXML I/O handler class for Trade Menu screen
 *
 * @author Olek Sadkowski
 * @author Christian Harper
 * @version 23/03/2022
 */

public class TradeController {

    private Trade currentTrade;
    private ArrayList<Tradable> playerSelectedItems, portSelectedItems;
    private BorderPane selectedItem;
    private Port curPort;
    private Ship curShip;

    @FXML
    private Label titleMessage;
    @FXML
    private Label playerDiamond;
    @FXML
    private Label playerRubies;
    @FXML
    private Label playerGold;
    @FXML
    private Label playerPearls;
    @FXML
    private Label playerRum;
    @FXML
    private Label portDiamond;
    @FXML
    private Label portRubies;
    @FXML
    private Label portGold;
    @FXML
    private Label portPearls;
    @FXML
    private Label portRum;
    @FXML
    private Button less;
    @FXML
    private Button more;
    @FXML
    private GridPane grid;
    @FXML
    private VBox playerSide;
    @FXML
    private VBox portSide;
    @FXML
    private Slider treasureSlider;
    @FXML
    private Label testingLabel;

    /**
     * This method is the display method to show a completed trade
     */
    @FXML
    private void completeTrade() {
        currentTrade.completeTrade();
    }

    /**
     * This method allows a user to pick a treasure in the trade screen
     * @param e the event used to pick a treasure
     * @throws InterruptedException
     */
    @FXML
    private void pickTreasure(MouseEvent e) throws InterruptedException {
        BorderPane background = ((BorderPane) e.getSource());
        if (background.getStyle().contains("-fx-border-color: red;")) {
            background.setStyle(null);
            return;
        }
        background.setStyle("-fx-border-color: red;");
        selectedItem = background;
        VBox[] sides = {playerSide, portSide};
        for (VBox side : sides) {
            for (Node vboxNode : side.getChildren()) {
                if (vboxNode instanceof HBox) {
                    for (Node hboxNode : ((HBox) vboxNode).getChildren()) {
                        if (hboxNode instanceof BorderPane && hboxNode != background) {
                            hboxNode.setStyle(null);
                        }
                    }
                }
            }
        }
    }

    /**
     * This method allows a user to select a card
     * @param e the mouse event used to pick a card
     * @throws InterruptedException
     */
    @FXML
    public void pickCard(MouseEvent e) throws InterruptedException{
        return;
    }

    /**
     * This method is used to initialize the GUI trade window
     */
    @FXML
    public void initialize() {
        SceneControl.tradeCtrl = this;
        playerSelectedItems = new ArrayList<>();
        portSelectedItems = new ArrayList<>();
    }

    /**
     * This method sets a new trade
     * @param newTrade the new trade
     */
    public void setTrade(Trade newTrade){
        currentTrade = newTrade;
        curShip = newTrade.getShip();
        curPort = newTrade.getPort();
        populateTradeWindow(curShip, curPort);
    }

    /**
     * This method populates the trade window with the amounts of treasure stored in the ship
     * and in the port
     *
     * @param ship The player's ship
     * @param port The port that the player is trading with
     */
    private void populateTradeWindow(Ship ship, Port port) {
        titleMessage.setText("Welcome to " + port.getName());
        int[] shipTreasureAmount = currentTrade.getTreasureAmount(ship);
        int[] shipCrewAmount = currentTrade.getCrewAmount(ship);
        int[] portTreasureAmount = currentTrade.getTreasureAmount(port);
        int[] portCrewAmount = currentTrade.getCrewAmount(port);
        populateLabels(shipTreasureAmount, shipCrewAmount, playerSide);
        populateLabels(portTreasureAmount, portCrewAmount, portSide);
    }

    /**
     * Helper method used to set text of specific labels in the scene
     * @param treasureAmount Array 5 big, each element represents amount of treasure type
     * @param side           Either the player's or port's item side
     */
    private void populateLabels(int[] treasureAmount,int[] crewAmount, VBox side) {
        for (Node vboxNode : side.getChildren()) {
            if (vboxNode instanceof HBox) {
                for (Node hboxNode : ((HBox) vboxNode).getChildren()) {
                    if (hboxNode instanceof BorderPane) {
                        for (Node borderNode : (((BorderPane) hboxNode).getChildren())) {
                            if (borderNode instanceof Label) {
                                if (currentTrade.isHomePort() && Objects.equals(vboxNode.getId(), "playerSide")) {
                                    switch (borderNode.getId()) {
                                        case "portDiamond" -> ((Label) borderNode).setText(String.valueOf(treasureAmount[0]));
                                        case "portRubies" -> ((Label) borderNode).setText(String.valueOf(treasureAmount[1]));
                                        case "portPearls" -> ((Label) borderNode).setText(String.valueOf(treasureAmount[2]));
                                        case "portGold" -> ((Label) borderNode).setText(String.valueOf(treasureAmount[3]));
                                        case "portRum" -> ((Label) borderNode).setText(String.valueOf(treasureAmount[4]));
                                        case "portCrewBlackThree" -> ((Label) borderNode).setText(0 + "/" + crewAmount[0]);
                                        case "portCrewBlackTwo" -> ((Label) borderNode).setText(0 + "/" + crewAmount[1]);
                                        case "portCrewBlackOne" -> ((Label) borderNode).setText(0 + "/" + crewAmount[2]);
                                        case "portCrewRedThree" -> ((Label) borderNode).setText(0 + "/" + crewAmount[3]);
                                        case "portCrewRedTwo" -> ((Label) borderNode).setText(0 + "/" + crewAmount[4]);
                                        case "portCrewRedOne" -> ((Label) borderNode).setText(0 + "/" + crewAmount[5]);
                                        default -> {
                                        }
                                    }
                                } else {
                                    switch (borderNode.getId()) {
                                        case "playerDiamond", "portDiamond" -> ((Label) borderNode).setText(0 + "/" + treasureAmount[0]);
                                        case "playerRubies", "portRubies" -> ((Label) borderNode).setText(0 + "/" + treasureAmount[1]);
                                        case "playerPearls", "portPearls" -> ((Label) borderNode).setText(0 + "/" + treasureAmount[2]);
                                        case "playerGold", "portGold" -> ((Label) borderNode).setText(0 + "/" + treasureAmount[3]);
                                        case "playerRum", "portRum" -> ((Label) borderNode).setText(0 + "/" + treasureAmount[4]);
                                        case "red3", "portCrewBlackThree" -> ((Label) borderNode).setText(0 + "/" + crewAmount[0]);
                                        case "red2", "portCrewBlackTwo" -> ((Label) borderNode).setText(0 + "/" + crewAmount[1]);
                                        case "red1", "portCrewBlackOne" -> ((Label) borderNode).setText(0 + "/" + crewAmount[2]);
                                        case "black3", "portCrewRedThree" -> ((Label) borderNode).setText(0 + "/" + crewAmount[3]);
                                        case "black2", "portCrewRedTwo" -> ((Label) borderNode).setText(0 + "/" + crewAmount[4]);
                                        case "black1", "portCrewRedOne" -> ((Label) borderNode).setText(0 + "/" + crewAmount[5]);
                                        default -> {
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * This method changes the scene back to the game view
     */
    @FXML
    private void backButton() {
        SceneControl.primaryStage.setScene(SceneControl.gameScene);
    }

    /**
     * This method decreases the amount of an item
     */
    @FXML
    private void removeOne() {
        currentTrade.editOne(-1, selectedItem);
    }

    /**
     * This method increases the amount of an item
     */
    @FXML
    private void addOne() {
        currentTrade.editOne(1, selectedItem);
    }
}
