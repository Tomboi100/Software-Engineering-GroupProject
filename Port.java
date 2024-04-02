package uk.ac.aber.cs221.gp03.grid;

import uk.ac.aber.cs221.gp03.game.BearingType;
import uk.ac.aber.cs221.gp03.game.ColorType;
import uk.ac.aber.cs221.gp03.game.Ship;
import uk.ac.aber.cs221.gp03.items.CrewCard;
import uk.ac.aber.cs221.gp03.items.Treasure;
import uk.ac.aber.cs221.gp03.items.TreasureType;
import uk.ac.aber.cs221.gp03.utility.BuccaneerImage;
import uk.ac.aber.cs221.gp03.utility.SceneControl;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a Port stationed on a tile
 *
 * @author Luke Purnell
 * @author Christian Harper
 * @author Steffan Davies-John
 * @version 16/03/2022
 */
public class Port extends TileType{
    ArrayList<Treasure> treasures; // Treasure contained in the port
    ArrayList<CrewCard> crewCards; //Crew Cards contained within the port
    //Player owner; // Player who owns the port
    private BearingType startFacing; // The bearing that the ship starting at this port should face when the game starts
    private int diamondPortCount;
    private int pearlPortCount;
    private int barrelPortCount;
    private int goldPortCount;
    private int rubyPortCount;

    /**
     * This method gets the number of diamonds in the port
     * @return number of diamonds in the port
     */
    public int getDiamondPortCount() {
        return diamondPortCount;
    }

    /**
     * This method gets the number of pearls in the port
     * @return number of pearls in the port
     */
    public int getPearlPortCount() {
        return pearlPortCount;
    }

    /**
     * This method gets the number of barrels of rum in the port
     * @return  number of barrels in the port
     */
    public int getBarrelPortCount() {
        return barrelPortCount;
    }

    /**
     * This method gets the number of gold bars in the port
     * @return number of gold bars in the port
     */
    public int getGoldPortCount() {
        return goldPortCount;
    }

    /**
     * This method gets the number of rubies in the port
     * @return number of rubies in the port
     */
    public int getRubyPortCount() {
        return rubyPortCount;
    }

    /**
     * Gets the sum of the values of the treasures stored in the port
     * @return int total value of the treasures
     */
    public int getValueOfTreasures(){
        int totalValue=0;
        for (Treasure treasure : treasures) {
            totalValue += treasure.getValue();
        }
        return totalValue;
    }

    /**
     * Gets the sum of the values of the chance cards stored in the port
     * @return int total value of the chance cards
     */
    public int getValueOfCrewCards(){
        int totalValue=0;
        for (CrewCard crewcard : crewCards) {
            totalValue += crewcard.getValue();
        }
        return totalValue;
    }

    /**
     * Adds a treasure to the list of treasures
     * @param treasure the Treasure to be added
     */
    public void addTreasure(Treasure treasure){
        treasures.add(treasure);
    }

    /**
     * Gets name of port
     * @return String of port name
     */
    public String getName() {
        return name;
    }

    /**
     * Get treasures contained in port
     * @return ArrayList of type Treasure, representing the contents of the port
     */
    public ArrayList<Treasure> getTreasures() {
        return treasures;
    }

    /**
     * Get Crew Cards contained in port
     * @return ArrayList of type CrewCard, representing the contents of the port
     */
    public ArrayList<CrewCard> getCrewCards(){ return crewCards; }


    /**
     * This method sets the image for the port
     * @param colorType the colour of the port
     */
    public void setTileImage(ColorType colorType){
        super.tileImage = new BuccaneerImage(colorType.getPortImage());
    }

    /**
     * Gets the x coordinate of the port
     * @return int of the position
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * Gets the y coordinate of the port
     * @return int of the position
     */
    public int getyPos() {
        return yPos;
    }

    /**
     * This method gets the bearing that the Ship should face when starting at this Port
     * @return BearingType of the direction a ship should start facing
     */
    public BearingType getStartFacing(){ return startFacing; }

    /**
     * Sets the starting facing value of this Port
     * @param facing The facing bearing
     */
    public void setStartFacing(BearingType facing){ startFacing = facing; }

    /**
     * Constructs a Port object
     * @param name Name of the port
     * @param treasures Treasures in the port
     */
    public Port(String name, ArrayList<Treasure> treasures, ArrayList<CrewCard> crewCards, int xPos, int yPos) {
        super(name,"/images/sprites/NPCport.png", xPos, yPos);
        this.treasures = Objects.requireNonNullElseGet(treasures, ArrayList::new);
        this.crewCards = Objects.requireNonNullElseGet(crewCards, ArrayList::new);
    }

    /**
     * This method constructs a Port object without initial objects for the Player, CrewCards or Treasures
     * @param name String name of the Port
     * @param xPos int X coordinate of the Port's position on the board
     * @param yPos int Y coordinate of the Port's position on the board
     * @param startFacing BearingType of the direction the Port's ship should face when generated
     */
    public Port(String name, int xPos, int yPos, BearingType startFacing){
        super(name,"/images/sprites/NPCport.png", xPos, yPos);
        this.treasures = new ArrayList<>();
        this.crewCards = new ArrayList<>();
        this.startFacing = startFacing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Port port = (Port) o;
        return Objects.equals(name, port.name)
                && Objects.equals(treasures, port.treasures)
                && Objects.equals(crewCards, port.crewCards)
                && startFacing == port.startFacing;
    }

    /**
     * Gets a count of all of the players current treasure
     */
    public void getPortTreasureCounts() {
        diamondPortCount = 0;
        goldPortCount = 0;
        pearlPortCount = 0;
        rubyPortCount = 0;
        barrelPortCount = 0;
        for (Treasure treasure : treasures) {
            if (treasure.getType() == TreasureType.Diamonds) {
                diamondPortCount++;
            }
            else if (treasure.getType() == TreasureType.GoldBars) {
                goldPortCount++;
            }
            else if (treasure.getType() == TreasureType.Pearls) {
                pearlPortCount++;
            }
            else if (treasure.getType() == TreasureType.Rubies) {
                rubyPortCount++;
            }
            else if (treasure.getType() == TreasureType.BarrelsOfRum) {
                barrelPortCount++;
            }
        }
    }

    /**
     * This method displays the ports information on the screen by updating labels and making images visible
     * @param name the name of the port
     */
    public void portInfo(String name) {
        String crewString = crewCards.toString();
        String treasureString = treasures.toString();
        String ownerName = null;
        for(Ship ship : SceneControl.gameCtrl.getShips()){
            if(ship.getHomePort()==this) ownerName=ship.getName();
        }
        if(ownerName==null) {
            SceneControl.gameCtrl.infoTitle.setText(getName() + " " + "No Owner");
        } else {
            SceneControl.gameCtrl.infoTitle.setText(getName() + " " + ownerName);
        }
        SceneControl.gameCtrl.infoBody.setVisible(false);
        SceneControl.gameCtrl.diamondPortImage.setVisible(true);
        SceneControl.gameCtrl.goldPortImage.setVisible(true);
        SceneControl.gameCtrl.pearlPortImage.setVisible(true);
        SceneControl.gameCtrl.barrelPortImage.setVisible(true);
        SceneControl.gameCtrl.rubyPortImage.setVisible(true);
        SceneControl.gameCtrl.diamondPortLabel.setVisible(true);
        SceneControl.gameCtrl.goldPortLabel.setVisible(true);
        SceneControl.gameCtrl.pearlPortLabel.setVisible(true);
        SceneControl.gameCtrl.barrelPortLabel.setVisible(true);
        SceneControl.gameCtrl.rubyPortLabel.setVisible(true);
        SceneControl.gameCtrl.infoBody.setText("There are:\n"+crewString+"\n and "+ treasureString +"\n on "+name+" port.");
        getPortTreasureCounts();
        SceneControl.gameCtrl.diamondPortLabel.setText('x'+String.valueOf(getDiamondPortCount()));
        SceneControl.gameCtrl.goldPortLabel.setText('x'+String.valueOf(getGoldPortCount()));
        SceneControl.gameCtrl.pearlPortLabel.setText('x'+String.valueOf(getPearlPortCount()));
        SceneControl.gameCtrl.barrelPortLabel.setText('x'+String.valueOf(getBarrelPortCount()));
        SceneControl.gameCtrl.rubyPortLabel.setText('x'+String.valueOf(getRubyPortCount()));
    }




}
