package uk.ac.aber.cs221.gp03.grid;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import uk.ac.aber.cs221.gp03.utility.SceneControl;

import java.io.IOException;
import java.util.Objects;

/**
 * Represents a Tile on the game board
 *
 * @author Luke Purnell
 * @version 25/03/2022
 */
public class Tile extends Node {
    private final int XPOS, YPOS; // Coordinates of tile in the grid
    private TileType tileType;
    private Rectangle tileGraphic; // Visual depiction of tile
    private final Image landImg = new Image("/images/sprites/land.png"); // Sprite for land
    private final Image waterImg = new Image("/images/sprites/water.png"); // Sprite for sea

    public void setTileType(TileType tileType){
        this.tileType = tileType;
    }

    public TileType getTileType(){
        return tileType;
    }

    /**
     * Retrieves X coordinate
     * @return int of tile's X coordinate
     */
    public int getxPos(){
        return XPOS;
    }

    /**
     * Retrieves Y coordinate
     * @return int of tile'sY coordinate
     */
    public int getyPos(){
        return YPOS;
    }

    /**
     * Retrieves tile graphic
     * @return Rectangle of tile
     */
    public Rectangle getTileGraphic() {
        return tileGraphic;
    }

    /**
     * This is the constructor class for the tiles
     * @param xPos Tile's X coordinate in grid
     * @param yPos Tile's Y coordinate in grid
     * @param boardSize Size of the square board in tiles
     */
    public Tile(int xPos, int yPos, int boardSize) {
        this.XPOS = xPos;
        this.YPOS = yPos;
    }

    /**
     * Generate the Tile's graphical representation
     * @param boardSize used to calculate the coordinate of the tile generated
     */
    public void generateGraphic(int boardSize) {
        // Graphic size
        int graphicSize = 40;
        tileGraphic = new Rectangle(graphicSize, graphicSize);
        tileGraphic.setOnMouseClicked(t -> {
            try {
                SceneControl.gameCtrl.boardClick(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        if(tileType!=null){
            tileGraphic.setFill(new ImagePattern(tileType.getTileImage()));
            if (tileType instanceof Port) tileGraphic.setRotate((((Port) tileType).getStartFacing().degrees+180)%360);
        } else {
            tileGraphic.setFill(new ImagePattern(new Image("/images/sprites/water.png")));
        }
        if(tileType instanceof Port || tileType == null) tileGraphic.setStyle("-fx-stroke: black; -fx-stroke-width: 1px;");
        tileGraphic.setLayoutX(graphicSize * XPOS);
        tileGraphic.setLayoutY(graphicSize * (boardSize - YPOS));
    }

    /**
     * This method highlights the tiles which are possible moves
     */
    public void highlight() {
        if (this.tileType instanceof Port || this.tileType instanceof Bay) return;
        if(tileGraphic == null) generateGraphic(20);
        tileGraphic.setFill(Paint.valueOf(SceneControl.gameCtrl.getCurrentPlayer().getColor().getName()));
    }

    /**
     * This method unhighlights the tiles
     */
    public void unhighlight() {
        if (this.tileType instanceof Port || this.tileType instanceof Bay) return;
        tileGraphic.setFill(new ImagePattern(new Image("/images/sprites/water.png")));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return XPOS == tile.XPOS
                && YPOS == tile.YPOS
                && Objects.equals(tileType, tile.tileType);
    }
}
