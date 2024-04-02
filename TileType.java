package uk.ac.aber.cs221.gp03.grid;

import javafx.scene.image.Image;
import uk.ac.aber.cs221.gp03.game.PopUp;
import uk.ac.aber.cs221.gp03.utility.BuccaneerImage;

import java.util.Objects;

public class TileType {
    protected final String name;
    protected final int xPos, yPos;
    protected BuccaneerImage tileImage;

    /**
     * This is the constructor method for the tile type
     * @param name tile name
     * @param fileLocation where the tile image is stored
     * @param xPos the x position of the tile
     * @param yPos the y position of the tile
     */
    public TileType(String name, String fileLocation, int xPos, int yPos){
        this.name = name;
        tileImage = new BuccaneerImage(fileLocation);
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /**
     * Get the image associated with this tile.
     * @return The image instance of the tile
     */
    public BuccaneerImage getTileImage() {
        return tileImage;
    }

    /**
     * Get the X position of this tile.
     * @return The X coordinate of the tile
     */
    public int getX() {
        return this.xPos;
    }

    /**
     * Get the Y position of this tile.
     * @return The Y coordinate of the tile
     */
    public int getY() {
        return this.yPos;
    }

    /**
     * Get the name of this tile.
     * @return The name of the tile
     */
    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TileType tileType = (TileType) o;
        return xPos == tileType.xPos && yPos == tileType.yPos;
    }

    /**
     * This method displays a popup with the name of the tile
     */
    public void tileInfo(){
        PopUp.display(name,"This is "+name+"!",300,400);
    }
}
