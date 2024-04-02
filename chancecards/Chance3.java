package uk.ac.aber.cs221.gp03.items.chancecards;

import uk.ac.aber.cs221.gp03.game.GameLogic;
import uk.ac.aber.cs221.gp03.game.Ship;
import uk.ac.aber.cs221.gp03.items.ChanceCard;
import uk.ac.aber.cs221.gp03.game.PopUpChoice;
import uk.ac.aber.cs221.gp03.items.ChanceCardEnforcer;

/**
 * Class for executing chance card where a player is blown to Cliff Creek.
 *
 * "You are blown to Cliff Creek. If your crew total is 3 or less, take 4 crew cards from Pirate Island"
 *
 * @author James Croucher
 * @version 05/05/2022
 */
public class Chance3 extends ChanceCardEnforcer implements ChanceCardExecutor {
    public void execute(ChanceCard chanceCard, Ship ship, GameLogic gameLogic) {
        PopUpChoice.display("Chance Card",chanceCard.getDescription(),200,200,new String[] {});
        ship.setPosition(gameLogic.getBoard().tiles[gameLogic.getBays().get(2).getxPos()][gameLogic.getBays().get(2).getyPos()]);
        givePlayerCards(ship,4,4,gameLogic);
    }
}