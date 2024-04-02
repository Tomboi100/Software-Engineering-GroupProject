package uk.ac.aber.cs221.gp03.grid;

import uk.ac.aber.cs221.gp03.game.Ship;
import uk.ac.aber.cs221.gp03.utility.SceneControl;

import java.util.Objects;

/**
 * Class to represent the 3 Bays on the board
 * @version 29/04/2022
 * @author Christian Harper
 */
public class Bay extends TileType {

    /**
     * This method gets the name of the Bay
     * @return String of the name of the Bay
     */
    public String getName(){ return name; }

    /**
     * This method gets the X coordinate of the position of the Bay
     * @return int of the x position
     */
    public int getxPos(){ return super.xPos; }

    /**
     * This method gets the Y coordinate of the position of the Bay
     * @return int of the y position
     */
    public int getyPos(){ return super.yPos; }

    /**
     * Constructor for a new Bay
     * @param name String of the name of the Bay
     * @param xPos int of the X coordinate of the Bay
     * @param yPos int of the Y coordinate of the Bay
     */
    public Bay(String name, int xPos, int yPos, String fileLocation){
        super(name, fileLocation, xPos, yPos);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Bay bay = (Bay) o;
        return Objects.equals(name, bay.name);
    }

    public void bayInfo(String name) {
        SceneControl.gameCtrl.infoBody.setText(name);
    }
}
