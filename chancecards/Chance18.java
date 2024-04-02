package uk.ac.aber.cs221.gp03.items.chancecards;

import uk.ac.aber.cs221.gp03.game.GameLogic;
import uk.ac.aber.cs221.gp03.game.Ship;
import uk.ac.aber.cs221.gp03.items.CardManager;
import uk.ac.aber.cs221.gp03.items.ChanceCard;
import uk.ac.aber.cs221.gp03.game.PopUpChoice;
import uk.ac.aber.cs221.gp03.items.ChanceCardEnforcer;

/**
 * Class for executing chance card where a player exchanges crew cards for ones on Pirate Island
 *
 * "Exchange all crew cards in your hand as far as possible for the same number of crew cards from Pirate
 *  Island."
 *
 * @author James Croucher
 * @version 05/05/2022
 */
public class Chance18 extends ChanceCardEnforcer implements ChanceCardExecutor {
    public void execute(ChanceCard chanceCard, Ship ship, GameLogic gameLogic) {
        PopUpChoice.display("Chance Card",chanceCard.getDescription(),200,200,null);
        int playerCrewNum = ship.getCrewCards().size();
        int pirateCrewNum = gameLogic.getPirateIsland().getPileOfCrewCards().size();
        for (int i = 0; i < Math.min(playerCrewNum, pirateCrewNum); i++){
            CardManager.playerToPirate(ship,gameLogic);
            CardManager.pirateToPlayer(ship,gameLogic);
        }
    }
}