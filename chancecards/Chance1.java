package uk.ac.aber.cs221.gp03.items.chancecards;

import uk.ac.aber.cs221.gp03.game.GameLogic;
import uk.ac.aber.cs221.gp03.game.Ship;
import uk.ac.aber.cs221.gp03.items.CardManager;
import uk.ac.aber.cs221.gp03.items.ChanceCard;
import uk.ac.aber.cs221.gp03.game.PopUpChoice;
import uk.ac.aber.cs221.gp03.items.ChanceCardEnforcer;

import java.util.ArrayList;

import static uk.ac.aber.cs221.gp03.game.PopUpChoice.userChoice;

/**
 * Class for executing chance card where a player can get another player to hand over 3 crew cards.
 *
 * "Present this card to any player who must then give you 3 crew cards. This card must be used at once
 *  then returned to the Chance card pack."
 *
 * @author James Croucher
 * @version 05/05/2022
 */
public class Chance1 extends ChanceCardEnforcer implements ChanceCardExecutor {
    public void execute(ChanceCard chanceCard, Ship ship, GameLogic gameLogic) {
        ArrayList<String> playerNames = new ArrayList<>();
        ArrayList<Ship> shipList = new ArrayList<>();
        for(Ship playerShip : gameLogic.getShips()){
            if(playerShip!=ship){
                shipList.add(playerShip);
                playerNames.add(playerShip.getName());
            }
        }
        PopUpChoice.display("Chance Card",chanceCard.getDescription(),200,200,playerNames.toArray(new String[0]));
        if(userChoice >=0 && userChoice < shipList.size()){
            CardManager.playerToPlayerRepeat(shipList.get(userChoice),ship, 3);
        }
    }
}
