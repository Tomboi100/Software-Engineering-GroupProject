package uk.ac.aber.cs221.gp03.items.chancecards;

import uk.ac.aber.cs221.gp03.game.GameLogic;
import uk.ac.aber.cs221.gp03.game.Ship;
import uk.ac.aber.cs221.gp03.grid.TreasureIsland;
import uk.ac.aber.cs221.gp03.items.CardManager;
import uk.ac.aber.cs221.gp03.items.ChanceCard;
import uk.ac.aber.cs221.gp03.game.PopUpChoice;
import uk.ac.aber.cs221.gp03.items.ChanceCardEnforcer;
import uk.ac.aber.cs221.gp03.utility.MoveAssistant;

/**
 * Class for executing chance card where a player exchanges crew cards for another player's crew cards.
 *
 * "If the ship of another player is anchored at Treasure Island, exchange 2 of your crew cards with that
 *  player. Take 2 cards from each other's hands without looking at them. If
 *  there is no other player at Treasure Island, place 2 of your crew cards on Pirate Island."
 *
 * @author James Croucher
 * @version 05/05/2022
 */
public class Chance19 extends ChanceCardEnforcer implements ChanceCardExecutor {
    public void execute(ChanceCard chanceCard, Ship ship, GameLogic gameLogic) {
        PopUpChoice.display("Chance Card",chanceCard.getDescription(),200,200,null);
        Ship foundShip=null;
        for(Ship curShip : gameLogic.getShips()){
            if(curShip!=ship&& MoveAssistant.detectIsland(curShip) instanceof TreasureIsland){
                foundShip = curShip;
            }
        }
        if (foundShip!=null) {
            CardManager.playerToPlayer(ship,foundShip);
            CardManager.playerToPlayer(foundShip,ship);
        } else {
            CardManager.playerToPirate(ship, gameLogic);
            CardManager.playerToPirate(ship, gameLogic);
        }
    }
}