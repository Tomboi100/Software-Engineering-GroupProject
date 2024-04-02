package uk.ac.aber.cs221.gp03.items.chancecards;

import uk.ac.aber.cs221.gp03.game.GameLogic;
import uk.ac.aber.cs221.gp03.game.Ship;
import uk.ac.aber.cs221.gp03.items.ChanceCard;
import uk.ac.aber.cs221.gp03.game.PopUpChoice;
import uk.ac.aber.cs221.gp03.items.ChanceCardEnforcer;

/**
 * Class for executing chance card where a player gets to take treasure worth some value or 2 crew cards.
 *
 * "Take treasure up to [value] in total value, or 2 crew cards from Pirate Island."
 *
 * @author James Croucher
 * @version 05/05/2022
 */
public class Chance10_11_12_13_22 {

    public static class Chance10_12 extends ChanceCardEnforcer implements ChanceCardExecutor {
        public void execute(ChanceCard chanceCard, Ship ship, GameLogic gameLogic) {
            PopUpChoice.display("Chance Card",chanceCard.getDescription(),200,200,new String[] {"Treasure", "Crew Cards"});
            takeTreasureOrCrewCard(chanceCard, ship, gameLogic, 5,2);
        }
    }

    public static class Chance11_26 extends ChanceCardEnforcer implements ChanceCardExecutor {
        public void execute(ChanceCard chanceCard, Ship ship, GameLogic gameLogic) {
            PopUpChoice.display("Chance Card",chanceCard.getDescription(),200,200,new String[] {"Treasure", "Crew Cards"});
            takeTreasureOrCrewCard(chanceCard, ship, gameLogic, 4,2);
        }
    }

    public static class Chance13_27 extends ChanceCardEnforcer implements ChanceCardExecutor {
        public void execute(ChanceCard chanceCard, Ship ship, GameLogic gameLogic) {
            PopUpChoice.display("Chance Card",chanceCard.getDescription(),200,200,new String[] {"Treasure", "Crew Cards"});
            takeTreasureOrCrewCard(chanceCard, ship, gameLogic, 7,3);
        }
    }

    public static class Chance22 extends ChanceCardEnforcer implements ChanceCardExecutor {
        public void execute(ChanceCard chanceCard, Ship ship, GameLogic gameLogic) {
            PopUpChoice.display("Chance Card",chanceCard.getDescription(),200,200,new String[] {"Treasure", "Crew Cards"});
            takeTreasureOrCrewCard(chanceCard, ship, gameLogic, 5,3);
        }
    }
}
