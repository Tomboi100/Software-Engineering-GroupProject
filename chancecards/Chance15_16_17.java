package uk.ac.aber.cs221.gp03.items.chancecards;

import uk.ac.aber.cs221.gp03.game.GameLogic;
import uk.ac.aber.cs221.gp03.game.Ship;
import uk.ac.aber.cs221.gp03.items.CardManager;
import uk.ac.aber.cs221.gp03.items.ChanceCard;
import uk.ac.aber.cs221.gp03.game.PopUpChoice;
import uk.ac.aber.cs221.gp03.items.ChanceCardEnforcer;

/**
 * Class for executing chance card where a player takes treasure and reduces crew size.
 *
 * "Take treasure up to [value] in total value and reduce your ship's crew to [amount], by taking crew cards from your
 *  hand and placing them on Pirate Island."
 *
 * @author James Croucher
 * @version 05/05/2022
 */
public class Chance15_16_17 {
    public static class Chance15 extends ChanceCardEnforcer implements ChanceCardExecutor {
        public void execute(ChanceCard chanceCard, Ship ship, GameLogic gameLogic) {
            PopUpChoice.display("Chance Card",chanceCard.getDescription(),200,200,null);
            completeSearch(7, ship, gameLogic);
            if (ship.getAllCrewCardValue() > 10) {
                while (ship.getAllCrewCardValue() > 12) {
                    if (CardManager.playerToPirateValue(ship, 3, gameLogic) == null) {
                        break;
                    }
                }
                while (ship.getAllCrewCardValue() > 11) {
                    if (CardManager.playerToPirateValue(ship, 2, gameLogic) == null) {
                        break;
                    }
                }
                while (ship.getAllCrewCardValue() > 10) {
                    if (CardManager.playerToPirateValue(ship, 1, gameLogic) == null) {
                        break;
                    }
                }
            }
        }
    }

    public static class Chance16 extends ChanceCardEnforcer implements ChanceCardExecutor {
        public void execute(ChanceCard chanceCard, Ship ship, GameLogic gameLogic) {
            PopUpChoice.display("Chance Card",chanceCard.getDescription(),200,200,null);
            completeSearch(6,ship,gameLogic);
            if(ship.getAllCrewCardValue()>11){
                while(ship.getAllCrewCardValue()>13){
                    if(CardManager.playerToPirateValue(ship,3,gameLogic)==null){
                        break;
                    }
                }
                while(ship.getAllCrewCardValue()>12){
                    if(CardManager.playerToPirateValue(ship,2,gameLogic)==null){
                        break;
                    }
                }
                while(ship.getAllCrewCardValue()>11){
                    if(CardManager.playerToPirateValue(ship,1,gameLogic)==null){
                        break;
                    }
                }
            }
        }
    }
    public static class Chance17 extends ChanceCardEnforcer implements ChanceCardExecutor {
        public void execute(ChanceCard chanceCard, Ship ship, GameLogic gameLogic) {
            PopUpChoice.display("Chance Card",chanceCard.getDescription(),200,200,null);
            completeSearch(4,ship,gameLogic);
            givePlayerCards(ship,7,2,gameLogic);
        }
    }

}