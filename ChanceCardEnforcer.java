package uk.ac.aber.cs221.gp03.items;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import uk.ac.aber.cs221.gp03.game.GameController;
import uk.ac.aber.cs221.gp03.game.GameLogic;
import uk.ac.aber.cs221.gp03.game.PopUp;
import uk.ac.aber.cs221.gp03.game.Ship;
import uk.ac.aber.cs221.gp03.items.chancecards.*;

import java.util.*;

import static uk.ac.aber.cs221.gp03.game.PopUpChoice.userChoice;

public class ChanceCardEnforcer{

    private static final int[] nums = {5, 4, 3, 2};
    protected ChanceCard chanceCard;
    protected static ChanceCardExecutor[] executors = {};

    /**
     * This method creates an executor for the chance cards
     */
    public static void createExecutor(){
        executors = new ChanceCardExecutor[]{
                new Chance0(),
                new Chance1(),
                new Chance2(),
                new Chance3(),
                new Chance4(),
                new Chance5(),
                new Chance6(),
                new Chance7(),
                new Chance8(),
                new Chance9(),
                new Chance10_11_12_13_22.Chance10_12(),
                new Chance10_11_12_13_22.Chance11_26(),
                new Chance10_11_12_13_22.Chance10_12(),
                new Chance10_11_12_13_22.Chance13_27(),
                new Chance14_23_24_25(),
                new Chance15_16_17.Chance15(),
                new Chance15_16_17.Chance16(),
                new Chance15_16_17.Chance17(),
                new Chance18(),
                new Chance19(),
                new Chance20_21(),
                new Chance20_21(),
                new Chance10_11_12_13_22.Chance22(),
                new Chance14_23_24_25(),
                new Chance14_23_24_25(),
                new Chance14_23_24_25(),
                new Chance10_11_12_13_22.Chance11_26(),
                new Chance10_11_12_13_22.Chance13_27()
        };
    }

    /**
     * This method finds treasures of the target value
     * @param target target value for treasure
     * @return hash map containing the makeup of the target treasure value
     */
    private static Map<Integer,Integer> findTreasures(int target){
        Map<Integer,Integer> numMap = new HashMap<>();
        if(target>5) {
            if(target==6){
                numMap.put(4,2);
                numMap.put(3,3);
                numMap.put(5,0);//this is a catch in the event that it cannot find any treasures worth the correct value, it will find one of value target -1
            } else {
                numMap.put(5, target - 5);
            }
        } else if(target==5){
            numMap.put(5,0);
            numMap.put(3,2);
            numMap.put(4,0); //this is a catch in the event that it cannot find any treasures worth the correct value, it will find one of value target -1
        } else if(target==4){
            numMap.put(4,0);
            numMap.put(2,2);
            numMap.put(3,0);//this is a catch in the event that it cannot find any treasures worth the correct value, it will find one of value target -1
        } else if(target==3){
            numMap.put(3,0);
            numMap.put(2,0);//this is a catch in the event that it cannot find any treasures worth the correct value, it will find one of value target -1
        } else {
            numMap.put(2,0);
        }
        return numMap;
    }

    /**
     * This method trys to take treasures with the specified values
     * @param val1 treasure value 1
     * @param val2 treasure value 2
     * @param ship the player gaining treasure
     * @param gameLogic the game
     * @return true if its possible to find treasures of those values
     */
    private static boolean tryTakeValues(int val1, int val2, Ship ship, GameLogic gameLogic){
        if(ship.getShipTreasureNum()<1 && TreasureManager.findValue(val1,gameLogic)>0 && TreasureManager.findValue(val2,gameLogic)>0) {
            if(val1!=0)ship.addShipTreasure(gameLogic.getTreasureIsland().takeValue(val1));
            if(val1!=0)ship.addShipTreasure(gameLogic.getTreasureIsland().takeValue(val2));
            return true;
        }
        return false;
    }

    /**
     * This method initializes and executes chance card classes
     * @param chanceCard The chance card
     * @param ship the players ship
     * @param gameLogic the game
     */
    public static void chanceClasses(ChanceCard chanceCard, Ship ship, GameLogic gameLogic){
        Random rand = new Random();
        if(executors.length == 0) createExecutor();
        if(chanceCard.getId() < executors.length) {
            ChanceCardExecutor executor = executors[chanceCard.getId()];
            executor.execute(chanceCard, ship, gameLogic);
            gameLogic.updateStats((GameController)gameLogic);
        } else {
            PopUp.display(
                    "Error",
                    "Attempted to execute a chance card of index " + chanceCard.getId() + ", but no such chance card exists!",
                    400,
                    400
            );
        }
        if(chanceCard.isKeep()){
            ship.getChanceCards().remove(chanceCard);
        }
        gameLogic.getTreasureIsland().getChanceCards().add(chanceCard);
    }

    /**
     * This method allows a player to take either trreasure or crew cards
     * @param chanceCard the chance card
     * @param ship the player
     * @param gameLogic the game
     * @param repeatTreasure the amount of treasure being taken
     * @param repeatCard the amount of crew cards being taken
     */
    protected static void takeTreasureOrCrewCard(ChanceCard chanceCard, Ship ship, GameLogic gameLogic, int repeatTreasure, int repeatCard){
        if(userChoice ==0){
            completeSearch(repeatTreasure,ship,gameLogic);
        } else {
            repeatPirateToPlayer(repeatCard,ship,gameLogic);
        }
    }

    /**
     * This method is used to search for values in card types
     * @param valueToSearch value being searched fro
     * @param ship the player
     * @param gameLogic the game
     * @return true if the value is found
     */
    protected static boolean completeSearch(int valueToSearch, Ship ship, GameLogic gameLogic){
        Map<Integer,Integer> values = findTreasures(valueToSearch);
        for(Map.Entry<Integer,Integer> entry : values.entrySet()){
            if(entry.getValue()==0 && ship.getShipTreasureNum()<2) {
                if (tryTakeValues(entry.getKey(), entry.getValue(), ship, gameLogic)) {
                    return true;
                }
            } else if(ship.getShipTreasureNum()<1){
                if(tryTakeValues(entry.getKey(),entry.getValue(),ship,gameLogic)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method repeats giving cards from pirate island to the player
     * @param noOfReps the number of cards
     * @param ship the player
     * @param gameLogic the game
     */
    private static void repeatPirateToPlayer(int noOfReps, Ship ship,GameLogic gameLogic){
        for(int i=0;i<noOfReps;i++){
            CardManager.pirateToPlayer(ship,gameLogic);
        }
    }

    /**
     * This method gives the player crew cards
     * @param ship the player
     * @param numberOfCards the number of cards being given
     * @param noOfReps the number of repetition
     * @param gameLogic the game
     */
    protected static void givePlayerCards(Ship ship, int numberOfCards, int noOfReps, GameLogic gameLogic){
        if(ship.getAllCrewCardNum()<numberOfCards){
            repeatPirateToPlayer(noOfReps,ship,gameLogic);
        }
    }

    /**
     * This method sets the choices for a chance card
     * @param value the value of the chance card
     * @param event the mouse event
     */
    public static void setChanceCardChoice(int value,ActionEvent event){
        userChoice = value;
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}
