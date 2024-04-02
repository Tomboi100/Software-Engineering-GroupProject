package uk.ac.aber.cs221.gp03.grid;

import uk.ac.aber.cs221.gp03.game.PopUp;
import uk.ac.aber.cs221.gp03.items.CrewCard;
import uk.ac.aber.cs221.gp03.utility.SceneControl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Inherited class of Island to represent the Pirate Island
 * @version 29/04/2022
 * @author Christian Harper
 * @author Steffan Davies-John
 */
public class PirateIsland extends Island{
    private List<CrewCard> pileOfCrewCards; // List of the CrewCards stored on the island

    /**
     * Constructor for PirateIsland, parses flat island specific values to Island class
     */
    public PirateIsland(){
        super("Pirate Island", 16, 1, 18, 4);
        pileOfCrewCards = new ArrayList<>();
    }

    /**
     * Returns the list of CrewCards (will change to individual methods)
     * @return pileOfCrewCards
     */
    public List<CrewCard> getPileOfCrewCards(){
        return pileOfCrewCards;
    }

    /**
     * Returns and removes a random CrewCard from the list of CrewCards
     * @return CrewCards (randomly chosen)
     */
    public CrewCard takeCrewCard(){
        Random rand = new Random();
        int size = pileOfCrewCards.size();
        if(size<1){
            return null;
        }
        int index = rand.nextInt(pileOfCrewCards.size());
        CrewCard chosenCrewCards = pileOfCrewCards.get(index);
        pileOfCrewCards.remove(chosenCrewCards);
        return chosenCrewCards;
    }

    /**
     * This method retrieves multiple crew cards from Pirate Island
     * @param numOfCrew the number of crew cards being taken
     * @return Array list containing the cards retrieved
     */
    public ArrayList<CrewCard> takeMultipleCrewCards(int numOfCrew){
        ArrayList<CrewCard> crew = new ArrayList<>();
        for (int i = 0; i < numOfCrew; i++) {
            crew.add(takeCrewCard());
        }
        return crew;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PirateIsland that = (PirateIsland) o;
        return Objects.equals(pileOfCrewCards, that.pileOfCrewCards);
    }

    /**
     * Displays information about Pirate Island when one of the island's tiles are clicked.
     */
    @Override
    public void tileInfo() {
        int crewPile = pileOfCrewCards.size();
        makeTreasureInvisible();
        SceneControl.gameCtrl.infoTitle.setText("Pirate Island:");
        SceneControl.gameCtrl.infoBody.setText("There are "+ crewPile +" \ncrew cards on "+name+".");
    }
}
