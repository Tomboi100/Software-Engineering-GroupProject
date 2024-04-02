package uk.ac.aber.cs221.gp03.items.chancecards;

import uk.ac.aber.cs221.gp03.game.GameLogic;
import uk.ac.aber.cs221.gp03.game.Ship;
import uk.ac.aber.cs221.gp03.grid.Port;
import uk.ac.aber.cs221.gp03.items.ChanceCard;
import uk.ac.aber.cs221.gp03.game.PopUpChoice;
import uk.ac.aber.cs221.gp03.items.ChanceCardEnforcer;

import java.util.ArrayList;

/**
 * Class for executing chance card where a player is blown to the nearest port.
 *
 * "You are blown to the nearest port in the direction you are heading. If your crew total is 3 or less,
 * take 4 crew cards from Pirate Island."
 *
 * @author James Croucher
 * @version 05/05/2022
 */
public class Chance5 extends ChanceCardEnforcer implements ChanceCardExecutor {
    public void execute(ChanceCard chanceCard, Ship ship, GameLogic gameLogic) {
        PopUpChoice.display("Chance Card",chanceCard.getDescription(),200,200,new String[] {});
        ArrayList<Integer> portDistance = new ArrayList<>();
        int shortestDistance = 100;
        Port curPort, shortestPort = null;
        for(int i=0; i<6; i++){
            curPort = gameLogic.getPorts().get(i);
            portDistance.add((int) Math.sqrt(Math.pow(curPort.getxPos()-ship.getPosition().getxPos(),2) + Math.pow(curPort.getyPos()-ship.getPosition().getyPos(),2)));
            if(portDistance.get(i)<shortestDistance){
                shortestDistance = portDistance.get(i);
                shortestPort = curPort;
            };
        }
        if(shortestPort!=null)ship.setPosition(gameLogic.getBoard().tiles[shortestPort.getxPos()][shortestPort.getyPos()]);
        givePlayerCards(ship,4,4,gameLogic);
    }
}
