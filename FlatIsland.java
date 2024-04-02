package uk.ac.aber.cs221.gp03.grid;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import uk.ac.aber.cs221.gp03.game.PopUp;
import uk.ac.aber.cs221.gp03.items.CrewCard;
import uk.ac.aber.cs221.gp03.items.Treasure;
import uk.ac.aber.cs221.gp03.utility.SceneControl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static uk.ac.aber.cs221.gp03.grid.TreasureIsland.getTreasure;

/**
 * Inherited class of Island to represent the Flat Island
 * @version 29/04/2022
 * @author Christian Harper
 * @author Steffan Davies-John
 */
public class FlatIsland extends Island {
    private List<CrewCard> crewCards; // list of CrewCards on the Island
    private List<Treasure> treasures; // list of Treasures on the Island


    /**
     * Constructor for FlatIsland, parses flat island specific values to Island class
     */
    public FlatIsland(){
        super("Flat Island", 1, 15, 3, 18);
        crewCards = new ArrayList<>();
        treasures = new ArrayList<>();
    }

    /**
     * This method returns the list of CrewCards (will change to individual methods)
     * @return pileOfCrewCards
     */
    public List<CrewCard> getPileOfCrewCards(){
        return crewCards;
    }

    /**
     * This method returns and removes a random CrewCard from the list of CrewCards
     * @return CrewCards (randomly chosen)
     */
    public CrewCard takeCrewCard(){
        Random rand = new Random();
        int size = crewCards.size();
        if(size<1){
            return null;
        }
        int index = rand.nextInt(crewCards.size());
        CrewCard chosenCrewCards = crewCards.get(index);
        crewCards.remove(chosenCrewCards);
        return chosenCrewCards;
    }

    /**
     * Returns the list of Treasures (will change to individual methods)
     * @return pileOfTreasures
     */
    public List<Treasure> getTreasures(){
        return treasures;
    }

    /**
     * Returns and removes a random treasure from the list of treasures
     * @return Treasure (randomly chosen)
     */
    public Treasure takeTreasure(){
        return getTreasure(treasures);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FlatIsland that = (FlatIsland) o;
        return Objects.equals(crewCards, that.crewCards) && Objects.equals(treasures, that.treasures);
    }

    /**
     * Displays information about Flat Island when one of the island's tiles are clicked.
     */
    @Override
    public void tileInfo() {
        int treasureNo = treasures.size();
        int crewNo = crewCards.size();
        makeTreasureInvisible();
        SceneControl.gameCtrl.infoTitle.setText("Flat Island:");
        SceneControl.gameCtrl.infoBody.setText("There are "+treasureNo+" treasure cards.\nThere are "+crewNo+" crew cards.");
    }
}
