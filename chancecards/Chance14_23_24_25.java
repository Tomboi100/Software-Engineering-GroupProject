package uk.ac.aber.cs221.gp03.items.chancecards;

import uk.ac.aber.cs221.gp03.game.GameLogic;
import uk.ac.aber.cs221.gp03.game.Ship;
import uk.ac.aber.cs221.gp03.items.ChanceCard;
import uk.ac.aber.cs221.gp03.game.PopUpChoice;
import uk.ac.aber.cs221.gp03.items.ChanceCardEnforcer;

/**
 * Class for executing chance card where a player takes 2 cards from Pirate Island.
 *
 * "Take 2 crew cards from Pirate Island"
 *
 * @author James Croucher
 * @version 05/05/2022
 */
public class Chance14_23_24_25 extends ChanceCardEnforcer implements ChanceCardExecutor {
    public void execute(ChanceCard chanceCard, Ship ship, GameLogic gameLogic) {
        PopUpChoice.display("Chance Card",chanceCard.getDescription(),200,200,new String[] {});
        gameLogic.getPirateIsland().takeMultipleCrewCards(2)
                .forEach(ship::addCrewCard);
    }
}
