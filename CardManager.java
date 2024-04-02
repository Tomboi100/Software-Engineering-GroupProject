package uk.ac.aber.cs221.gp03.items;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import uk.ac.aber.cs221.gp03.game.*;
import uk.ac.aber.cs221.gp03.grid.FlatIsland;
import uk.ac.aber.cs221.gp03.grid.PirateIsland;
import uk.ac.aber.cs221.gp03.grid.Port;
import uk.ac.aber.cs221.gp03.grid.TreasureIsland;

import java.util.ArrayList;

import static uk.ac.aber.cs221.gp03.utility.ObjectStoreUtil.getNodes;

/**
 * Class to manage cards within the game
 * @author Christian Harper
 * @version 06/05/2022
 */
public class CardManager {

    /**
     * Method to create all the ChanceCards in the game from the xml file and add them to treasure island
     * @param treasureIsland the island which stores all the chance cards to be stored
     */
    public static void generateChanceCards(TreasureIsland treasureIsland){
        NodeList listOfChanceCards = getNodes();
        Node currentNode;
        for(int itr=0; itr<listOfChanceCards.getLength();itr++){
            currentNode = listOfChanceCards.item(itr);
            if(currentNode.getNodeType()==Node.ELEMENT_NODE){
                Element eElement = (Element) currentNode;
                ChanceCard newChanceCard = new ChanceCard(
                        eElement.getAttribute("id"),
                        eElement.getElementsByTagName("contents").item(0).getTextContent(),
                        eElement.getElementsByTagName("type").item(0).getTextContent(),
                        eElement.getElementsByTagName("keep").item(0).getTextContent()
                );
                treasureIsland.getChanceCards().add(newChanceCard);
            }
        }
        treasureIsland.shuffleChanceCards();
    }

    /**
     * Method to create all the required CrewCards and put them onto PirateIsland
     * @param pirateIsland the island the cards need to be put onto
     */
    public static void generateCrewCards(PirateIsland pirateIsland){
        for(int i=0;i<6;i++){
            for(int j=1;j<=3;j++){
                pirateIsland.getPileOfCrewCards().add(new CrewCard(CrewCard.colorType.red,j));
                pirateIsland.getPileOfCrewCards().add(new CrewCard(CrewCard.colorType.black,j));
            }
        }
    }

    /**
     * This method moves crew card from pirate island to the player's ship
     * @param ship the player gaining the crew card
     * @param gameLogic the game
     */
    public static void pirateToPlayer(Ship ship, GameLogic gameLogic){
        PirateIsland pirateIsland = gameLogic.getPirateIsland();
        ship.addCrewCard(pirateIsland.takeCrewCard());
    }

    /**
     * This method moves crew cards from the player's ship
     * @param ship The player losing the crew cards
     * @param gameLogic the game
     */
    public static void playerToPirate(Ship ship, GameLogic gameLogic){
        PirateIsland pirateIsland = gameLogic.getPirateIsland();
        pirateIsland.getPileOfCrewCards().add(ship.takeCrewCard());
    }

    /**
     * This method repeats the action of moving cards from the player to Flat island
     * @param ship the player which is losing the cards
     * @param gameLogic the game
     * @param loop the amount of cards being lost
     */
    public static void playerToFlatRepeat(Ship ship, GameLogic gameLogic, int loop){
        for(int i=0;i<loop;i++){
            playerToFlat(ship, gameLogic);
        }
    }

    /**
     * This method moves crew cards from the players ship to flat island
     * @param ship the player which is losing the crew cards
     * @param gameLogic the game
     */
    public static void playerToFlat(Ship ship, GameLogic gameLogic){
        FlatIsland flatIsland = gameLogic.getFlatIsland();
        flatIsland.getPileOfCrewCards().add(ship.takeCrewCard());
    }

    /**
     * This method moves crew cards equal to a certain value from the player to pirate island
     * @param ship the players ship
     * @param value the value of the crew cards being lost
     * @param gameLogic the game
     * @return the crew cards being transferred
     */
    public static CrewCard playerToPirateValue(Ship ship, int value, GameLogic gameLogic){
        PirateIsland pirateIsland = gameLogic.getPirateIsland();
        CrewCard transferCard = ship.takeCrewCardValue(value);
        pirateIsland.getPileOfCrewCards().add(transferCard);
        return transferCard;
    }

    /**
     * This method repeats moving crew cards between two players ships
     * @param sourceShip the ship the cards are coming from
     * @param destShip the ship the cards are going to
     * @param loop the amount of cards being passsed
     */
    public static void playerToPlayerRepeat(Ship sourceShip, Ship destShip, int loop){
        for(int i=0;i<loop;i++){
            playerToPlayer(sourceShip, destShip);
        }
    }

    /**
     * This method moves crew cards between two player's ships
     * @param sourceShip the ship the crew cards are being moved from
     * @param destShip the ship the crew cards are being moved to
     */
    public static void playerToPlayer(Ship sourceShip, Ship destShip){
        destShip.getCrewCards().add(sourceShip.takeCrewCard());
    }

    /**
     * This method deals chance cards to a ship
     * @param ship the ship gaining a chance card
     * @param gameLogic the game
     */
    public static void dealChanceCard(Ship ship, GameLogic gameLogic){
        TreasureIsland treasureIsland = gameLogic.getTreasureIsland();
        ChanceCard newChanceCard = treasureIsland.getChanceCards().poll();
        if(newChanceCard==null) PopUp.display("Error","No more Chance Cards remaining!", 200, 200);
        if(newChanceCard.isKeep()) {
            giveChanceCard(newChanceCard,ship);
            PopUpChoice.display("Chance Card", newChanceCard.getDescription(),200,200, null);
            return;
        }
        ChanceCardEnforcer.chanceClasses(newChanceCard, ship, gameLogic);
    }

    /**
     * Gives each of the ports two random Crew Cards
     */
    public static void distributeCrewCards(ArrayList<Port> ports, GameController game) {
        for (Port port : ports) {
            port.getCrewCards().add(((PirateIsland) game.getIslands()[1]).takeCrewCard());
            port.getCrewCards().add(((PirateIsland) game.getIslands()[1]).takeCrewCard());
        }
    }

    /**
     * This method transfers crew cards from ports to the ships
     * @param ships the player's ships
     */
    public static void transferCrewCards(Ship[] ships) {
        for(Ship ship : ships){
            ship.addCrewCard(ship.getHomePort().getCrewCards().get(0));
            ship.addCrewCard(ship.getHomePort().getCrewCards().get(1));
            ship.getHomePort().getCrewCards().removeAll(ship.getHomePort().getCrewCards());
        }
    }

    /**
     * This method gives a chance card to a palyer
     * @param chanceCard the chance card being given
     * @param ship the player gaining the card
     */
    public static void giveChanceCard(ChanceCard chanceCard, Ship ship){
        ship.getChanceCards().add(chanceCard);
    }
}
