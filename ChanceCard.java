package uk.ac.aber.cs221.gp03.items;

import java.util.Objects;

/**
 * Class to manage implementation of Chance Cards
 * @author Christian Harper
 * @author Tommy Boyle (tob31)
 * @version 06/05/2022
 */
public class ChanceCard {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChanceCard that = (ChanceCard) o;
        return id == that.id && Objects.equals(description, that.description) && type == that.type && Objects.equals(keep, that.keep);
    }

    /**
     * enum to manage types of cards
     * take if the user doesn't get to choose anything
     *   & select if the user can choose what occurs (choose one of your crew cards)
     */
    public enum cardType {
        take, select
    }

    private int id;             //The id to make it searchable
    private String description; //What the card does so the user can read it
    private cardType type;      //Whether the card allows for a choice or not
    private Boolean keep;       //Whether the card is can be held onto or not

    /**
     * Instantiate a new Chance Card
     * @param id The ID which corresponds to that specific card
     * @param description The text shown to the user when they look at the card
     * @param type Whether the user can make a decision about the execution of the card
     * @param keep Whether the user can hold onto the card or must use it once received
     */
    public ChanceCard(String id, String description, String type, String keep){
        this.id = Integer.parseInt(id);
        this.description = description;
        this.type = type.equals("take") ? cardType.take : cardType.select;
        this.keep = keep.equals("true");
    }

    /**
     * Get the ID of the Chance Card
     * @return The ID which corresponds to that specific card
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the text description of the Chance Card
     * @return description the text which describes what the card does
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the type of the Chance Card
     * @return cardType - whether the user can choose the outcome of a card or not
     */
    public cardType getType() {
        return type;
    }

    /**
     * Gets whether the Chance Card is kept or not
     * @return Boolean Whether the Chance card can be kept or not
     */
    public Boolean isKeep() {
        return keep;
    }

    /**
     * Produces a string of the information about the Chance card
     * @return String of the fields of the card
     */
    public String toString(){
        return "Card: "+description+"\n";
    }

}
