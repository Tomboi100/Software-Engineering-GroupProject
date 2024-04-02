package uk.ac.aber.cs221.gp03.items.chancecards;

import uk.ac.aber.cs221.gp03.game.GameLogic;
import uk.ac.aber.cs221.gp03.game.Ship;
import uk.ac.aber.cs221.gp03.grid.Tile;
import uk.ac.aber.cs221.gp03.grid.TreasureIsland;
import uk.ac.aber.cs221.gp03.items.ChanceCard;
import uk.ac.aber.cs221.gp03.game.PopUpChoice;
import uk.ac.aber.cs221.gp03.items.ChanceCardEnforcer;
import uk.ac.aber.cs221.gp03.utility.MoveAssistant;

/**
 * Class for executing chance card where a ship is blown away from Treasure Island.
 *
 * "Your ship is blown 5 leagues (5 squares) off the coast of Treasure Island. If your crew total is 3 or
 *  less, take 4 crew cards from Pirate Island. If the square you are blown to is already occupied, move one
 *  square further)"
 *
 * @author James Croucher
 * @version 05/05/2022
 */
public class Chance0 extends ChanceCardEnforcer implements ChanceCardExecutor {
    public void execute(ChanceCard chanceCard, Ship ship, GameLogic gameLogic) {
        PopUpChoice.display("Chance Card",chanceCard.getDescription(),200,200, new String[] {});
        TreasureIsland treasureIsland = gameLogic.getTreasureIsland();
        Tile position = ship.getPosition();
        if(position.getxPos()< treasureIsland.getxPos1()){
            Tile newPosition = gameLogic.getBoard().tiles[position.getxPos()-5][position.getyPos()];
            if(MoveAssistant.getOccupiedTile(newPosition,ship)==null) {
                ship.setPosition(newPosition);
            } else {ship.setPosition(gameLogic.getBoard().tiles[position.getxPos()-6][position.getyPos()]); }
        } else if(position.getxPos()>treasureIsland.getxPos2()){
            Tile newPosition = gameLogic.getBoard().tiles[position.getxPos()+5][position.getyPos()];
            if(MoveAssistant.getOccupiedTile(newPosition,ship)==null) {
                ship.setPosition(newPosition);
            } else {ship.setPosition(gameLogic.getBoard().tiles[position.getxPos()+6][position.getyPos()]); }
        } else if(position.getyPos()< treasureIsland.getyPos1()){
            Tile newPosition = gameLogic.getBoard().tiles[position.getxPos()][position.getyPos()-5];
            if(MoveAssistant.getOccupiedTile(newPosition,ship)==null) {
                ship.setPosition(newPosition);
            } else {ship.setPosition(gameLogic.getBoard().tiles[position.getxPos()][position.getyPos()-6]); }
        } else if(position.getyPos()> treasureIsland.getyPos2()){
            Tile newPosition = gameLogic.getBoard().tiles[position.getxPos()][position.getyPos()+5];
            if(MoveAssistant.getOccupiedTile(newPosition,ship)==null) {
                ship.setPosition(newPosition);
            } else {ship.setPosition(gameLogic.getBoard().tiles[position.getxPos()][position.getyPos()+6]); }
        }
        ChanceCardEnforcer.givePlayerCards(ship,4,4,gameLogic);
    }
}
