package uk.ac.aber.cs221.gp03.items;

/**
 * Class to manage implementation of Crew Cards
 * @author Christian Harper
 * @version 06/05/2022
 */
public class CrewCard implements Tradable{

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrewCard crewCard = (CrewCard) o;
        return value == crewCard.value && cardColor == crewCard.cardColor;
    }

    /**
     * enum to manage the colours of the crew cards
     */
    public enum colorType {
        red("Red"), black("Black");

        public final String name;

        colorType(String name){
            this.name = name;
        }

    }

    private final colorType cardColor;
    private final int value;

    /**
     * Instantiate a new Crew Card
     * @param cardColor colourType, red or black
     * @param value int the power of the crew card, 1, 2, or 3
     */
    public CrewCard(colorType cardColor, int value){
        this.cardColor = cardColor;
        this.value = value;
    }

    /**
     * Get the colour of the Crew Card
     * @return cardColour cardType, red or black
     */
    public colorType getCardColor() {
        return cardColor;
    }

    /**
     *Get the power of the Crew Card
     * @return value int of the value of the card
     */
    @Override
    public int getValue() {
        return value;
    }

    /**
     * Method to compare the value of a Tradable object to a Crew Card
     * @param E The other object to be compared to
     * @return int which describes the comparative value of two objects
     */
    @Override
    public int compareTo(Tradable E) {
        return Tradable.super.compareTo(E);
    }

    /**
     * Gets the type of a Tradable object
     * @return tradableType corresponding to the tradable's child class
     */
    @Override
    public tradableType getTradableType() {
        return tradableType.crewCard;
    }

    /**
     * Produces a string of the information about the Crew Card
     * @return String of the fields of the card
     */
    public String toString(){
        return cardColor + " crew card with value " + value;
    }

}
