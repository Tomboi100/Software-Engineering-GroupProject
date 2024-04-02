package uk.ac.aber.cs221.gp03.game;

import uk.ac.aber.cs221.gp03.items.CrewCard;
import uk.ac.aber.cs221.gp03.items.Treasure;
import uk.ac.aber.cs221.gp03.utility.SceneControl;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Class for controlling battles
 * @version 06/05/22
 * @author Luke Purnell
 */

public class Battle {

    /**
     * This method allows a ship to attack another ship
     * @param attacker the ship attacking
     * @param defender the ship defending
     * @return returns the ship that loses
     */
    public static Ship startAttack(Ship attacker, Ship defender) {
        Ship winner, loser;
        if (calculateStrength(attacker) > calculateStrength(defender)) {
            winner = attacker;
            loser = defender;
        } else {
            winner = defender;
            loser = attacker;
        }
        ArrayList<String> lootText = awardLoot(winner, loser);
        SceneControl.primaryStage.setScene(SceneControl.battleScene);
        SceneControl.battleCtrl.populateText(winner.getName(), loser.getName(), lootText);
        return loser;
    }

    /**
     * This method calculates the fighting strength for each ship
     * @param ship the ship that the fight strength is being calculated
     * @return the fighting strength of the ship
     */
    private static int calculateStrength(Ship ship) {
        int value = 0;
        for (CrewCard card: ship.getCrewCards()) {
            switch (card.getCardColor()) {
                case red:
                    value += card.getValue();
                    break;
                case black:
                    value -= card.getValue();
                    break;
            }
        }
        return Math.abs(value);
    }

    /**
     * This method awards loot from the loser to the winner's ship
     * @param winner the ship that won the fight
     * @param loser the ship that lost the fight
     * @return an array list containing the loot that has been collected by the winner
     */
    private static ArrayList<String> awardLoot(Ship winner, Ship loser) {

        ArrayList<String> lootText = new ArrayList<>();

        if (loser.getShipTreasureNum() > 0) {
            ArrayList<Treasure> loot = winner.getShipTreasure();
            while (loot.size() <= winner.getShipTreasureNum() && loser.getShipTreasure().size() != 0) {
                Treasure item = loser.getShipTreasure().remove(0);
                loot.add(item);
                lootText.add(item.getType().name());
            }

            for (int i = 0; i < loser.getAllTreasureNum(); i++) {
                SceneControl.gameCtrl.treasureIsland.getTreasures().add(loser.getAllTreasure().remove(0));
            }

            for (Treasure item : loot) {
                winner.addShipTreasure(item);
            }

        } else if (loser.getAllCrewCardNum() > 0) {
            for (int i = 0; i < 2; i++) {
                CrewCard worstCard = getLowestValue(loser.getCrewCards());
                if (worstCard != null) {
                    loser.getCrewCards().remove(worstCard);
                    winner.getCrewCards().add(worstCard);
                    lootText.add(worstCard.toString());
                }
            }
        }
        return lootText;
    }

    /**
     * This method gets the lowest value of the crew cards in the losers hand
     * @param crewCards The array list containing the crew cards held
     * @return the lowest value crew card
     */
    private static CrewCard getLowestValue(ArrayList<CrewCard> crewCards) {
        return crewCards.stream()
                .min(Comparator.comparing(CrewCard::getValue))
                .orElse(null);
    }
}
