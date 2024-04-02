package uk.ac.aber.cs221.gp03.items;

import uk.ac.aber.cs221.gp03.game.GameController;
import uk.ac.aber.cs221.gp03.game.GameLogic;
import uk.ac.aber.cs221.gp03.game.Ship;
import uk.ac.aber.cs221.gp03.grid.FlatIsland;
import uk.ac.aber.cs221.gp03.grid.Port;
import uk.ac.aber.cs221.gp03.utility.SceneControl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class to manage Treasure within the game
 * @author Christian Harper
 * @version 24/04/2022
 */
public class TreasureManager {

    /**
     * Method to create all the required Treasures and put them onto TreasureIsland
     * @param game the game
     */
    public static void generateTreasures(GameController game){
        TreasureType[] listOfTreasureTypes = {TreasureType.Diamonds, TreasureType.Rubies, TreasureType.GoldBars, TreasureType.Pearls, TreasureType.BarrelsOfRum};
        for(int i=0; i<5;i++){
            for(int j=0; j<4;j++){
                game.getTreasureIsland().getTreasures().add(new Treasure(listOfTreasureTypes[i]));
            }
        }
        Collections.shuffle(game.getTreasureIsland().getTreasures());
    }

    /**
     * This method abstracts the call to search for the number of instance of treasure of a specific value on treasure island
     * @param value the value which is being searched for
     * @return int of the number of instances of treasure with that corresponding value
     */
    public static int findValue(int value, GameLogic game){
        return game.getTreasureIsland().searchForValue(value);
    }

    /**
     * This method abstracts the call to take a treasure of the specified value on treasure island
     * @param value the value which is being taken
     * @param port the port which the treasure is being assigned to
     */
    public static void takeValue(int value, Port port, GameController game){
        Treasure newTreasure = game.getTreasureIsland().takeValue(value);
        if(newTreasure!=null) port.addTreasure(newTreasure);
    }

    /**
     * This method deals treasures from flat island
     * @param ship the player being dealt to
     */
    public static void dealTreasures(Ship ship){
        FlatIsland flatIsland = SceneControl.gameCtrl.getFlatIsland();
        List<Treasure> treasures = flatIsland.getTreasures();
        Collections.sort(treasures);
        for(Treasure treasure : treasures){
            if(ship.getShipTreasureNum()<2){
                ship.addShipTreasure(treasure);
                treasures.remove(treasure);
            } else {
                break;
            }
        }
        for(CrewCard crewCard : flatIsland.getPileOfCrewCards()){
            ship.getCrewCards().add(crewCard);
        }
        flatIsland.getPileOfCrewCards().clear();
    }

    /**
     * Method to take treasures from TreasureIsland and distribute them to the ports correctly
     * @param ports arraylist of ports in the game
     * @param game the game
     */
    public static void distributeTreasures(ArrayList<Port> ports, GameController game){
        int valueLeft, searchValue, startingValue = 8;
        Treasure tempTreasure = null;
        for (Port port : ports) {
            valueLeft = startingValue - port.getValueOfCrewCards();
            switch(valueLeft) {
                case 6:
                    if(findValue(3,game)<2){
                        if(findValue(4,game)>0&&findValue(2,game)>0){
                            takeValue(4,port,game);
                            takeValue(2,port,game);
                        }
                    } else {
                        takeValue(3,port,game);
                        takeValue(3,port,game);
                    }
                    break;
                case 5:
                    if(findValue(5,game)>0){
                        takeValue(5,port,game);
                    } else if(findValue(3,game)>0 && findValue(2,game)>0){
                        takeValue(3,port,game);
                        takeValue(2,port,game);
                    }
                    break;
                case 4:
                    if(findValue(4,game)>0){
                        takeValue(4,port,game);
                    } else if(findValue(2,game)>1){
                        takeValue(2,port,game);
                        takeValue(2,port,game);
                    }
                    break;
                case 3:
                case 2:
                    if(findValue(2,game)>0){
                        takeValue(2,port,game);
                    }
                    break;
            }
        }
    }

}
