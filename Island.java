package uk.ac.aber.cs221.gp03.grid;

import uk.ac.aber.cs221.gp03.utility.SceneControl;

import java.util.Objects;

/**
 * Represents an Island
 *
 * @author Luke Purnell
 * @author Steffan Davies-John
 * @version 17/03/2022
 */
public class Island extends TileType{
    private final int xPos2, yPos2; // The coordinates of the opposite corners of the island

    /**
     * gets the name of the island
     * @return String of the name of the island
     */
    public String getName() {
        return name;
    }

    /**
     * gets the X coordinate of the first corner of the island
     * @return int of X[1] coordinate of the island
     */
    public int getxPos1() {
        return super.xPos;
    }

    /**
     * gets the Y coordinate of the first corner of the island
     * @return int of Y[1] coordinate of the island
     */
    public int getyPos1() {
        return super.yPos;
    }

    /**
     * gets the X coordinate of the second corner of the island
     * @return int of X[2] coordinate of the island
     */
    public int getxPos2() {
        return xPos2;
    }

    /**
     * gets the Y coordinate of the second corner of the island
     * @return int of Y[2] coordinate of the island
     */
    public int getyPos2() {
        return yPos2;
    }

    /**
     * Constructor for a new Island
     */
    public Island(String name, int xPos1, int yPos1, int xPos2, int yPos2) {
        super(name, "/images/sprites/land.png", xPos1, yPos1);
        this.xPos2 = xPos2;
        this.yPos2 = yPos2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Island island = (Island) o;
        return xPos2 == island.xPos2 && yPos2 == island.yPos2 && Objects.equals(name, island.name);
    }

    /**
     * Makes the port treasures that are displayed on the right hand side of the screen invisible.
     */
    public void makeTreasureInvisible(){
        SceneControl.gameCtrl.infoBody.setVisible(true);
        SceneControl.gameCtrl.diamondPortImage.setVisible(false);
        SceneControl.gameCtrl.goldPortImage.setVisible(false);
        SceneControl.gameCtrl.pearlPortImage.setVisible(false);
        SceneControl.gameCtrl.barrelPortImage.setVisible(false);
        SceneControl.gameCtrl.rubyPortImage.setVisible(false);
        SceneControl.gameCtrl.diamondPortLabel.setVisible(false);
        SceneControl.gameCtrl.goldPortLabel.setVisible(false);
        SceneControl.gameCtrl.pearlPortLabel.setVisible(false);
        SceneControl.gameCtrl.barrelPortLabel.setVisible(false);
        SceneControl.gameCtrl.rubyPortLabel.setVisible(false);
    }
}
