package uk.ac.aber.cs221.gp03.items.chancecards;

import uk.ac.aber.cs221.gp03.game.GameLogic;
import uk.ac.aber.cs221.gp03.game.Ship;
import uk.ac.aber.cs221.gp03.items.ChanceCard;
import uk.ac.aber.cs221.gp03.game.PopUpChoice;
import uk.ac.aber.cs221.gp03.items.ChanceCardEnforcer;

/**
 * Class for executing chance card where a player loses their best crew card to Pirate Island.
 *
 * "The best crew card in your hand deserts for Pirate Island. The card must be placed there
 *  immediately."
 *
 * @author James Croucher
 * @version 05/05/2022
 */
public class Chance9 extends ChanceCardEnforcer implements ChanceCardExecutor {
    public void execute(ChanceCard chanceCard, Ship ship, GameLogic gameLogic) {
        PopUpChoice.display("Chance Card",chanceCard.getDescription(),200,200,new String[] {});
        gameLogic.getPirateIsland().getPileOfCrewCards().add(ship.takeBestCrewCard());
    }
}
