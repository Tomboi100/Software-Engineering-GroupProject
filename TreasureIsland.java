package uk.ac.aber.cs221.gp03.grid;
import uk.ac.aber.cs221.gp03.game.PopUp;
import uk.ac.aber.cs221.gp03.items.ChanceCard;
import uk.ac.aber.cs221.gp03.items.Treasure;
import uk.ac.aber.cs221.gp03.utility.SceneControl;

import java.util.*;

/**
 * Is the object for the Treasure Island, Inherits from Island Class
 *
 * @author Christian Harper (cjh26)
 * @version 17/03/2022
 */
public class TreasureIsland extends Island{
    private Queue<ChanceCard> chanceCards;
    private List<Treasure> treasures;


    /**
     * Creates a new Island object with the correct name and positions
     */
    public TreasureIsland() {
        super("Treasure Island", 8, 8, 11, 11);
        chanceCards = new LinkedList<ChanceCard>();
        treasures = new LinkedList<Treasure>();
    }

    /**
     * This method gets the queue of the chance cards on treasure island
     * @return the queue of chance cards
     */
    public Queue<ChanceCard> getChanceCards(){ return chanceCards; }

    /**
     * Shuffles the ChanceCard queue
     */
    public void shuffleChanceCards() {
        Collections.shuffle((List<?>) chanceCards);
    }

    /**
     * Returns the list of Treasures (will change to individual methods)
     * @return pileOfTreasures
     */
    public List<Treasure> getTreasures(){
        return treasures;
    }

    /**
     * This method gets the number of instances of treasure within pileOfTreasures with a specific value
     * @param searchValue the value of the Treasures we are counting
     * @return the number of instances of Treasures with the correct value
     */
    public int searchForValue(int searchValue){
        int count=0;
        if(searchValue==0) return 1;
        for(Treasure treasure : treasures){
            if(treasure.getValue()==searchValue){
                count++;
            }
        }
        return count;
    }

    /**
     * This method removes and returns a Treasure of the specified value from TreasureIsland
     * @param searchValue int of the value of Treasure to be taken
     * @return the Treasure of the specific value that has been taken
     */
    public Treasure takeValue(int searchValue){
        Treasure foundTreasure = null;
        for(Treasure treasure : treasures){
            if(treasure.getValue()==searchValue){
                foundTreasure = treasure;
                treasures.remove(treasure);
                return foundTreasure;
            }
        }
        return null;
    }

    /**
     * Returns and removes a random treasure from the list of treasures
     * @return Treasure (randomly chosen)
     */
    public Treasure takeTreasure(){
        return getTreasure(treasures);
    }

    /**
     * Returns and removes a random treasure from the list of treasures (used by FlatIsland as well)
     * @param treasures the list of treasures stored in the island
     * @return the treasure taken from the list of treasures
     */
    static Treasure getTreasure(List<Treasure> treasures) {
        Random rand = new Random();
        int size = treasures.size();
        if(size<1){
            return null;
        }
        int index = rand.nextInt(treasures.size());
        Treasure chosenTreasure = treasures.get(index);
        treasures.remove(chosenTreasure);
        return chosenTreasure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TreasureIsland that = (TreasureIsland) o;
        int i = 0;
        for(ChanceCard x : chanceCards) {
            if(!x.equals(that.chanceCards.toArray()[i])) {
                return false;
            }
        }
        return Objects.equals(treasures, that.treasures);
    }

    /**
     * Displays information about Treasure Island when one of the island's tiles are clicked.
     */
    @Override
    public void tileInfo() {
        int chanceNo = chanceCards.size();
        makeTreasureInvisible();
        SceneControl.gameCtrl.infoTitle.setText("Treasure Island:");
        SceneControl.gameCtrl.infoBody.setText("There are "+chanceNo+" \nchance cards on \n"+name+".");
    }
}