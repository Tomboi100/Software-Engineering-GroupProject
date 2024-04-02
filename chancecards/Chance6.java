package uk.ac.aber.cs221.gp03.items.chancecards;

import uk.ac.aber.cs221.gp03.game.GameLogic;
import uk.ac.aber.cs221.gp03.game.Ship;
import uk.ac.aber.cs221.gp03.items.CardManager;
import uk.ac.aber.cs221.gp03.items.ChanceCard;
import uk.ac.aber.cs221.gp03.game.PopUpChoice;
import uk.ac.aber.cs221.gp03.items.ChanceCardEnforcer;

/**
 * Class for executing chance card where a player loses one treasure or 2 crew cards to the nearest ship.
 *
 * "One treasure from your ship or 2 crew cards from your hand are lost and washed overboard to the
 *  nearest ship. If 2 ships are equidistant from yours you may ignore this instruction."
 *
 * @author James Croucher
 * @version 05/05/2022
 */
public class Chance6 extends ChanceCardEnforcer implements ChanceCardExecutor {
    public void execute(ChanceCard chanceCard, Ship ship, GameLogic gameLogic) {
        PopUpChoice.display("Chance Card",chanceCard.getDescription(),200,200,new String[] {"Treasure","Crew Cards"});
        Ship destShip = null;
        double closestDist = Double.POSITIVE_INFINITY;
        int numAtClosestDist = 0;
        for(Ship other : gameLogic.getShips()) {
            if(other.getName().equals(ship.getName())) continue;
            double dist = ship.distanceFromOtherShip(other);
            if(destShip == null) {
                destShip = other;
                closestDist = dist;
            } else if(dist < closestDist) {
                destShip = other;
                closestDist = dist;
                numAtClosestDist = 1;
            } else if(dist == closestDist) {
                numAtClosestDist++;
            }
        }
        if(PopUpChoice.userChoice==0 && destShip!=null){
            destShip.getShipTreasure().add(ship.takeTreasure());
        } else if(destShip!=null) {
            if (numAtClosestDist == 1) CardManager.playerToPlayerRepeat(ship, destShip, 2);
        }
    }
}

