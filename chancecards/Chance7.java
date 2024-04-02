package uk.ac.aber.cs221.gp03.items.chancecards;

import uk.ac.aber.cs221.gp03.game.GameLogic;
import uk.ac.aber.cs221.gp03.game.Ship;
import uk.ac.aber.cs221.gp03.items.CardManager;
import uk.ac.aber.cs221.gp03.items.ChanceCard;
import uk.ac.aber.cs221.gp03.game.PopUpChoice;
import uk.ac.aber.cs221.gp03.items.ChanceCardEnforcer;

import static uk.ac.aber.cs221.gp03.game.PopUpChoice.userChoice;

/**
 * Class for executing chance card where a player loses one treasure or 2 crew cards to Flat Island.
 *
 * "One treasure from your ship or 2 crew cards from your hand are lost and washed overboard to Flat
 *  Island."
 *
 * @author James Croucher
 * @version 05/05/2022
 */
public class Chance7 extends ChanceCardEnforcer implements ChanceCardExecutor {
    public void execute(ChanceCard chanceCard, Ship ship, GameLogic gameLogic) {
        PopUpChoice.display("Chance Card",chanceCard.getDescription(),200,200,new String[] {"1 Treasure","2 crew cards"});
        if(userChoice ==0){
            gameLogic.getFlatIsland().getTreasures().add(ship.takeTreasure());
        } else {
            CardManager.playerToFlatRepeat(ship, gameLogic, 2);
        }
    }
}
