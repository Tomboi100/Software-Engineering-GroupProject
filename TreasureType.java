package uk.ac.aber.cs221.gp03.items;

/**
 * enum representing types of treasure
 * @author Christian Harper
 * @version 06/05/2022
 */
public enum TreasureType {

    Diamonds(5),
    Rubies(5),
    GoldBars(4),
    Pearls(3),
    BarrelsOfRum(2);

    private final int value;

    /**
     * The constructor for the treasure type
     * @param value the value of the treasure
     */
    private TreasureType(int value){
        this.value = value;
    }

    /**
     * Gets the value of the treasure based on its type
     * @return int of its value
     */
    public int getValue(){
        return this.value;
    }

    /**
     * This method turns the types into a readable version
     * @param type the type of treasure
     * @return readable version of the treasure
     */
    public String readableType(TreasureType type){
        String readtype = "null";
        switch (type){
            case Diamonds:
                readtype = "Diamonds";
                break;
            case Rubies:
                readtype = "Rubies";
                break;
            case Pearls:
                readtype = "Pearls";
                break;
            case GoldBars:
                readtype = "Gold Bars";
                break;
            case BarrelsOfRum:
                readtype = "Barrels of Rum";
                break;

        }
        return readtype;
    }

}