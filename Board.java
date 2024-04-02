package uk.ac.aber.cs221.gp03.grid;

import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import uk.ac.aber.cs221.gp03.game.GameLogic;
import uk.ac.aber.cs221.gp03.game.Ship;
import uk.ac.aber.cs221.gp03.utility.SceneControl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;


/**
 * Represents the game board.
 *
 * @author Luke Purnell
 * @author Christian Harper
 * @version 17/03/2022
 */
public class Board {
    public final int BOARD_SIZE = 20; // The width and length of board in tiles
    public Tile[][] tiles = new Tile[BOARD_SIZE][BOARD_SIZE]; // The 2D array of tiles on the board

    /**
     * Constructor for the board
     */
    public Board() {
        generate();
    }

    /**
     * This method populates tiles with the tiles and their required information
     */
    private void generate() {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                tiles[x][y] = new Tile(x, y, BOARD_SIZE);
            }
        }
    }

    /**
     * This method checks the tile at a specific coordinate to see if it is at an Island
     * @param checkX the X coordinate to be checked
     * @param checkY the Y coordinate to be checked
     * @return the Island that is at that coordinate or null if there is no Island there
     */
    private Island getIsland(int checkX, int checkY) {
        for (Island island : SceneControl.gameCtrl.getIslands()) {
            if (island.getxPos1() <= checkX && checkX <= island.getxPos2()) {
                if (island.getyPos1() <= checkY && checkY <= island.getyPos2()) {
                    return island;
                }
            }
        }
        return null;
    }

    /**
     * This method gets the port at the given coordinates
     * @param checkX the x coordinate
     * @param checkY the y coordinate
     * @return Port or null if there isn't one at that location
     */
    private Port getPort(int checkX, int checkY) {
        for(Port port : SceneControl.gameCtrl.getPorts()){
            if(port.getxPos()==checkX && port.getyPos()==checkY){
                return port;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Arrays.deepEquals(tiles, board.tiles);
    }
}
