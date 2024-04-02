package uk.ac.aber.cs221.gp03.game;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import uk.ac.aber.cs221.gp03.grid.Port;
import uk.ac.aber.cs221.gp03.grid.TileType;
import uk.ac.aber.cs221.gp03.items.CrewCard;
import uk.ac.aber.cs221.gp03.items.Tradable;
import uk.ac.aber.cs221.gp03.items.Treasure;
import uk.ac.aber.cs221.gp03.items.TreasureType;
import uk.ac.aber.cs221.gp03.utility.SceneControl;

import java.util.ArrayList;

import static uk.ac.aber.cs221.gp03.items.CrewCard.colorType.black;
import static uk.ac.aber.cs221.gp03.items.CrewCard.colorType.red;

public class  Trade {
    private final Ship ship;
    private final Port port;
    private int playerTotal, portTotal;
    private ArrayList<Tradable> playersBuyList, portBuyList;
    private boolean homePort = false;

    /**
     * This is the constructor for the trade class
     * @param ship the ship that is trading
     * @param port the port being traded with
     */
    public Trade(Ship ship, Port port){
        this.ship = ship;
        this.port = port;
        playerTotal = portTotal = 0;
        playersBuyList = new ArrayList<>();
        portBuyList = new ArrayList<>();
    }

    /**
     * This method checks if it is a home port
     * @return true if home port
     */
    public boolean isHomePort() {
        return homePort;
    }

    /**
     * This method deposits treasure to home port
     */
    public void depositToHomePort() {
        homePort = true;
    }

    /**
     * This method gets the ship that is trading
     * @return the ship that is trading
     */
    public Ship getShip(){ return ship; }

    /**
     * This method gets the port being traded with
     * @return the port being traded with
     */
    public Port getPort(){ return  port; }

    /**
     * This method checks if a trade is valid and then trades items
     */
    public void completeTrade(){
        int playerValue, portValue;
        playerValue = portValue = 0;
        for(Tradable tradable : playersBuyList){
            playerValue+= tradable.getValue();
        }
        for(Tradable tradable : portBuyList){
            portValue+= tradable.getValue();
        }
        if(portValue == playerValue || homePort && portBuyList.size() == 0){
            if((portBuyList.size()+ship.getShipTreasureNum()-playersBuyList.size())<=2) {
                transferItems();
                SceneControl.primaryStage.setScene(SceneControl.gameScene);
                SceneControl.primaryStage.setTitle("Buccaneer Game");
            } else {
                PopUp.display("Error: ","Not enough room on the ship",200,200);
            }
        } else {
            PopUp.display("Error: ","trade item's value not equal", 200, 200);
        }
        TileType currentTile = SceneControl.gameCtrl.currentPlayer.getPosition().getTileType();
        for (Ship ship : SceneControl.gameCtrl.ships) if (ship.getHomePort() == currentTile) {
            ((Port) currentTile).getCrewCards().forEach(ship::addCrewCard);
        }
    }

    /**
     * This method transfers items between the ship and the port
     */
    private void transferItems(){
        for(Tradable tradable : playersBuyList){
            if(tradable instanceof Treasure){
                ship.getShipTreasure().remove((Treasure)tradable);
                port.getTreasures().add((Treasure)tradable);
            } else {
                ship.getCrewCards().remove((CrewCard)tradable);
                port.getCrewCards().add((CrewCard)tradable);
            }
        }
        for(Tradable tradable : portBuyList){
            if(tradable instanceof Treasure){
                port.getTreasures().remove((Treasure)tradable);
                ship.addShipTreasure((Treasure)tradable);
            } else {
                port.getCrewCards().remove((CrewCard)tradable);
                ship.addCrewCard((CrewCard) tradable);
            }
        }
    }

    public void editOne(int change, BorderPane selectedItem) {
        if (selectedItem != null) {
            for (Node borderNode : (selectedItem.getChildren())) {
                if (borderNode instanceof Label borderNode1) {
                    String text = (borderNode1).getText();
                    String[] text2 = text.split("");
                    int number = Integer.parseInt(text2[0]) + change;
                    if (number >= 0 && number <= Integer.parseInt(text2[2])) {
                        editList(borderNode1, change);
                        borderNode1.setText(number + "/" + text2[2]);
                    } else {
                        PopUp.display("Error: ", "Out of bounds!", 200, 200);
                    }
                }
            }
        }
    }


    private void editList(Label borderNode, int change) {
        if (change == 1) {
            switch (borderNode.getId()) {
                case "playerDiamond" -> playersBuyList.add(new Treasure(TreasureType.Diamonds));
                case "playerRubies" -> playersBuyList.add(new Treasure(TreasureType.Rubies));
                case "playerGold" -> playersBuyList.add(new Treasure(TreasureType.GoldBars));
                case "playerPearls" -> playersBuyList.add(new Treasure(TreasureType.Pearls));
                case "playerRum" -> playersBuyList.add(new Treasure(TreasureType.BarrelsOfRum));
                case "black3" -> playersBuyList.add(new CrewCard(CrewCard.colorType.black,3));
                case "black2" -> playersBuyList.add(new CrewCard(CrewCard.colorType.black,2));
                case "black1" -> playersBuyList.add(new CrewCard(CrewCard.colorType.black,1));
                case "red3" -> playersBuyList.add(new CrewCard(red,3));
                case "red2" -> playersBuyList.add(new CrewCard(red,2));
                case "red1" -> playersBuyList.add(new CrewCard(red,1));
                case "portDiamond" -> portBuyList.add(new Treasure(TreasureType.Diamonds));
                case "portRubies" -> portBuyList.add(new Treasure(TreasureType.Rubies));
                case "portGold" -> portBuyList.add(new Treasure(TreasureType.GoldBars));
                case "portPearls" -> portBuyList.add(new Treasure(TreasureType.Pearls));
                case "portRum" -> portBuyList.add(new Treasure(TreasureType.BarrelsOfRum));
                case "portCrewBlackThree" -> portBuyList.add(new CrewCard(CrewCard.colorType.black,3));
                case "portCrewBlackTwo" -> portBuyList.add(new CrewCard(CrewCard.colorType.black,2));
                case "portCrewBlackOne" -> portBuyList.add(new CrewCard(CrewCard.colorType.black,1));
                case "portCrewRedThree" -> portBuyList.add(new CrewCard(red,3));
                case "portCrewRedTwo" -> portBuyList.add(new CrewCard(red,2));
                case "portCrewRedOne" -> portBuyList.add(new CrewCard(red,1));
            }
        } else {
            switch (borderNode.getId()) {
                case "playerDiamond" -> playersBuyList.remove(new Treasure(TreasureType.Diamonds));
                case "playerRubies" -> playersBuyList.remove(new Treasure(TreasureType.Rubies));
                case "playerGold" -> playersBuyList.remove(new Treasure(TreasureType.GoldBars));
                case "playerPearls" -> playersBuyList.remove(new Treasure(TreasureType.Pearls));
                case "playerRum" -> playersBuyList.remove(new Treasure(TreasureType.BarrelsOfRum));
                case "black3" -> playersBuyList.remove(new CrewCard(CrewCard.colorType.black,3));
                case "black2" -> playersBuyList.remove(new CrewCard(CrewCard.colorType.black,2));
                case "black1" -> playersBuyList.remove(new CrewCard(CrewCard.colorType.black,1));
                case "red3" -> playersBuyList.remove(new CrewCard(red,3));
                case "red2" -> playersBuyList.remove(new CrewCard(red,2));
                case "red1" -> playersBuyList.remove(new CrewCard(red,1));
                case "portDiamond" -> portBuyList.remove(new Treasure(TreasureType.Diamonds));
                case "portRubies" -> portBuyList.remove(new Treasure(TreasureType.Rubies));
                case "portGold" -> portBuyList.remove(new Treasure(TreasureType.GoldBars));
                case "portPearls" -> portBuyList.remove(new Treasure(TreasureType.Pearls));
                case "portRum" -> portBuyList.remove(new Treasure(TreasureType.BarrelsOfRum));
                case "portCrewBlackThree" -> portBuyList.remove(new CrewCard(CrewCard.colorType.black,3));
                case "portCrewBlackTwo" -> portBuyList.remove(new CrewCard(CrewCard.colorType.black,2));
                case "portCrewBlackOne" -> portBuyList.remove(new CrewCard(CrewCard.colorType.black,1));
                case "portCrewRedThree" -> portBuyList.remove(new CrewCard(red,3));
                case "portCrewRedTwo" -> portBuyList.remove(new CrewCard(red,2));
                case "portCrewRedOne" -> portBuyList.remove(new CrewCard(red,1));
            }
        }
    }

    /**
     * This method uses a helper method to populate an array with the appropriate treasure
     * amounts
     * @param entity Object of either Ship or Port type
     * @return Array with the amounts of each treasure
     * @author Olek
     */
    public int[] getTreasureAmount(Object entity) {
        int[] treasures = {0, 0, 0, 0, 0};
        if (entity instanceof Ship) {
            for (Treasure treasure : ((Ship) entity).getShipTreasure()) {
                calculateTreasures(treasures, treasure);
            }
        } else if (entity instanceof Port) {
            for (Treasure treasure : ((Port) entity).getTreasures()) {
                calculateTreasures(treasures, treasure);
            }
        }
        return treasures;
    }

    /**
     * This method uses a helper method to populate an array with the appropriate crew cards
     * amounts
     * @param entity Object of either Ship or Port type
     * @return Array with the amounts of each crew card
     */
    public int[] getCrewAmount(Object entity) {
        int[] cards = {0, 0, 0, 0, 0,0};
        if (entity instanceof Ship) {
            for (CrewCard card : ((Ship) entity).getCrewCards()) {
                calculateCrew(cards, card);
            }
        } else if (entity instanceof Port) {
            for (CrewCard card : ((Port) entity).getCrewCards()) {
                calculateCrew(cards, card);
            }
        }
        return cards;
    }

    /**
     * Helper method used for calculating amounts of different crew cards
     * @param cards Array 6 big, each element represents amount of crew card type
     * @param card The crew card type
     */
    private void calculateCrew(int[] cards, CrewCard card) {
        switch (card.getCardColor()) {
            case red:
                switch (card.getValue()){
                    case 3 -> cards[0]++;
                    case 2 -> cards[1]++;
                    case 1 -> cards[2]++;
                }
                break;
            case black:
                switch (card.getValue()){
                    case 3 -> cards[3]++;
                    case 2 -> cards[4]++;
                    case 1 -> cards[5]++;
                }
                break;
        }
    }

    /**
     * Helper method used for calculating amounts of different treasure
     * @param treasures Array 5 big, each element represents amount of treasure type
     * @param treasure  The treasure type
     */
    private void calculateTreasures(int[] treasures, Treasure treasure) {
        switch (treasure.getType()) {
            case Diamonds -> treasures[0]++;
            case Rubies -> treasures[1]++;
            case Pearls -> treasures[2]++;
            case GoldBars -> treasures[3]++;
            case BarrelsOfRum -> treasures[4]++;
            default -> PopUp.display(
                    "Error",
                    "Attempted to compute value of unrecognised treasure " + treasure.getType() + " (Trade.java line " + new Throwable().getStackTrace()[0].getLineNumber() + ")",
                    400,
                    400
            );
        }
    }

}
