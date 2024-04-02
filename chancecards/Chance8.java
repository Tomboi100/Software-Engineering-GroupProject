package uk.ac.aber.cs221.gp03.items.chancecards;

import uk.ac.aber.cs221.gp03.game.GameLogic;
import uk.ac.aber.cs221.gp03.game.Ship;
import uk.ac.aber.cs221.gp03.items.ChanceCard;
import uk.ac.aber.cs221.gp03.game.PopUpChoice;
import uk.ac.aber.cs221.gp03.items.ChanceCardEnforcer;

/**
 * Class for executing chance card where a player loses either their best treasure or best crew card.
 *
 * "Your most valuable treasure on board or if no treasure, the best crew card from your hand is washed
 * overboard to Flat Island."
 *
 * @author James Croucher
 * @version 05/05/2022
 */
public class Chance8 extends ChanceCardEnforcer implements ChanceCardExecutor {
    public void execute(ChanceCard chanceCard, Ship ship, GameLogic gameLogic) {
        PopUpChoice.display("Chance Card",chanceCard.getDescription(),200,200,new String[] {});
        if(ship.getShipTreasureNum() == 0){
            gameLogic.getFlatIsland().getPileOfCrewCards().add(ship.takeBestCrewCard());
        } else {
            gameLogic.getFlatIsland().getTreasures().add(ship.takeBestTreasure());
        }
    }
}
