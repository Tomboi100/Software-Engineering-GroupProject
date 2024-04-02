package uk.ac.aber.cs221.gp03.items;

/**
 * Interface to enable comparison of Crew Cards and Treasure
 * @author Christian Harper
 * @version 06/05/2022
 */
public interface Tradable extends Comparable<Tradable> {

    public enum tradableType{
        crewCard, treasure
    }

    /**
     * Method to compare the value of two Tradable objects
     * @param E The other object to be compared to
     * @return int which describes the comparative value of two objects
     */
    @Override
    default int compareTo(Tradable E){
        return Integer.compare(this.getValue(), E.getValue());
    }

    /**
     * Gets the value of a Tradable object
     * @return int corresponding to the value of the item
     */
    int getValue();

    /**
     * Gets the type of a Tradable object
     * @return tradableType corresponding to the tradable's child class
     */
    tradableType getTradableType();

}
