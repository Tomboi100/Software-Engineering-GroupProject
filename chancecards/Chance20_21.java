package uk.ac.aber.cs221.gp03.items.chancecards;

import uk.ac.aber.cs221.gp03.game.GameLogic;
import uk.ac.aber.cs221.gp03.game.PopUpChoice;
import uk.ac.aber.cs221.gp03.game.Ship;
import uk.ac.aber.cs221.gp03.items.ChanceCard;
import uk.ac.aber.cs221.gp03.items.ChanceCardEnforcer;

public class Chance20_21 extends ChanceCardEnforcer implements ChanceCardExecutor{
    public void execute(ChanceCard chanceCard, Ship ship, GameLogic gameLogic){
        PopUpChoice.display("Chance Card", chanceCard.getDescription(),200,200,new String[] {});
        completeSearch(7,ship,gameLogic);
    }

}
