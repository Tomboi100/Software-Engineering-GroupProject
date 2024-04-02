package uk.ac.aber.cs221.gp03.items.chancecards;

import uk.ac.aber.cs221.gp03.game.GameLogic;
import uk.ac.aber.cs221.gp03.game.Ship;
import uk.ac.aber.cs221.gp03.items.ChanceCard;

/**
 * Executor interface for chance cards.
 *
 * @author James Croucher
 */
public interface ChanceCardExecutor {
    /**
     * Executor function for chance cards.
     *
     * Each chance card implements this function to match its own functionality.
     * @param card The chance card
     * @param ship The ship of the player that pulled the chance card
     * @param gameLogic The overall game's logic driver
     * @author James Croucher
     */
    void execute(ChanceCard card, Ship ship, GameLogic gameLogic);
}
