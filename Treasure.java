package uk.ac.aber.cs221.gp03.items;

import java.util.Objects;

////////
// THIS SHOULD NOT
// BE A RECORD!!!!
//
// IT WILL BREAK
// THE SAVE/LOAD CODE
// IF IT IS. DONT
// CONVERT IT. TY
////////

/**
 * Represents a Treasure
 *
 * @author Luke Purnell
 * @author James Croucher
 * @author Christian Harper
 * @version 06/05/2022
 */
public class Treasure implements Tradable {
    public final TreasureType type;

    /**
     * Get the type of treasure.
     *
     * @return The type of treasure.
     */
    public TreasureType getType() {
        return type;
    }

    /**
     * Get point value of treasure.
     *
     * @return Value of treasure as int (points)
     */
    @Override
    public int getValue() {
        return type.getValue();
    }

    /**
     * Method to compare the value of a Treasure and a Tradable Object
     *
     * @param E The other object to be compared to
     * @return int which describes the comparative value of two objects
     */
    @Override
    public int compareTo(Tradable E) {
        return Tradable.super.compareTo(E);
    }

    /**
     * Gets the type of a Tradable object
     *
     * @return tradableType corresponding to the tradable's child class
     */
    @Override
    public tradableType getTradableType() {
        return tradableType.treasure;
    }

    /**
     * Instantiate a new treasure.
     *
     * @param type The type of treasure to instantiate.
     */
    public Treasure(TreasureType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Treasure treasure = (Treasure) o;
        return type == treasure.type;
    }

    public String toString(){
        return  type.readableType(type);
    }
}
