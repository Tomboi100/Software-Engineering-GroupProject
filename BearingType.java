package uk.ac.aber.cs221.gp03.game;

import uk.ac.aber.cs221.gp03.grid.Tile;

import java.util.Arrays;

/**
 * This class is an enum to manage the bearing of a ship and the degrees to rotate the image on the board
 * @version 29/04/2022
 * @author Christian Harper
 * @author Olek
 */
public enum BearingType {
    N(0), NE(45), E(90), SE(135), S(180), SW(225), W(270), NW(315);

    public final int degrees; //the constant to correlate the bearing to the angle the image needs to be rotated on the board

    /**
     * This method gets the number of degrees the image needs to be rotated
     * @return int of the degrees the image should be rotated
     */
    public int getDegrees(){ return degrees; }

    /**
     * This method converts the degree a ship is rotated into a BearingType
     * @param degrees the angle (0 - 360) which the ship could be rotated
     *                this is only in multiples of 45 degrees to match N NE E etc.
     * @return BearingType which corresponds to the degrees
     */
    public static BearingType bearingFromDegrees(int degrees){
        return switch (degrees) {
            case 0 -> N;
            case 45 -> NE;
            case 90 -> E;
            case 135 -> SE;
            case 180 -> S;
            case 225 -> SW;
            case 270 -> W;
            case 315 -> NW;
            default -> null;
        };
    }

    /**
     * The constructor for a bearingType.
     * @param degrees int of the degrees the image should be rotated
     */
    BearingType(int degrees){
        this.degrees = degrees;
    }//this is private because it is only used to define the set of bearings within the enum and there won't be any additional bearings added externally


    /**
     * This method returns the new bearing the player has selected
     * @param from the tile that the ship is on
     * @param to the tile that the player has clicked
     * @return a bearing
     */
    public static BearingType calculateBearing(Tile from, Tile to){
        int[] pair = {from.getyPos() - to.getyPos(), from.getxPos() - to.getxPos()};
        switch (Arrays.toString(pair)) {
            case "[-1, 0]" -> {return BearingType.N;}
            case "[-1, -1]" -> {return BearingType.NE;}
            case "[0, -1]" -> {return BearingType.E;}
            case "[1, -1]" -> {return BearingType.SE;}
            case "[1, 0]" -> {return BearingType.S;}
            case "[1, 1]" -> {return BearingType.SW;}
            case "[0, 1]" -> {return BearingType.W;}
            case "[-1, 1]" -> {return BearingType.NW;}
            default -> {
                return null;
            }
        }
    }
}
