package uk.ac.aber.cs221.gp03.game;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import uk.ac.aber.cs221.gp03.grid.Board;
import uk.ac.aber.cs221.gp03.grid.Island;
import uk.ac.aber.cs221.gp03.grid.Port;
import uk.ac.aber.cs221.gp03.grid.Tile;
import uk.ac.aber.cs221.gp03.items.ChanceCard;
import uk.ac.aber.cs221.gp03.items.CrewCard;
import uk.ac.aber.cs221.gp03.items.Treasure;
import uk.ac.aber.cs221.gp03.items.TreasureType;
import uk.ac.aber.cs221.gp03.utility.BuccaneerImage;
import uk.ac.aber.cs221.gp03.utility.MoveAssistant;
import uk.ac.aber.cs221.gp03.utility.SceneControl;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * Represents a Tile on the game board
 *
 * @author Olek Sadkowski
 * @author Luke Purnell
 * @author Christian Harper
 * @author Tommy Boyle
 * @author Steffan Davies-John
 * @version 06/05/2022
 */
public class Ship{
    private BearingType bearing; // Bearing of ship
    private Tile position; // Tile ship is currently on
    private final Port homePort; // Player associated with ship
    private final int MAX_TREASURE = 2; // Maximum amount of treasure the ship can hold
    private ArrayList<Treasure> treasures; // Treasure the ship holds
    private final BuccaneerImage shipImg; // The image to represent the player's ship on the board
    private Rectangle shipGraphic; // The graphic which javafx adds to the board
    final int GRAPHIC_SIZE = 40;
    private final String name;
    private final int playerNum;
    private ColorType color;
    private ArrayList<CrewCard> crewCards;
    private ArrayList<ChanceCard> chanceCards;
    private int diamondCount;
    private int pearlCount;
    private int barrelCount;
    private int goldCount;
    private int rubyCount;
    private int blackcrewcard1;
    private int blackcrewcard2;
    private int blackcrewcard3;
    private int redcrewcard1;
    private int redcrewcard2;
    private int redcrewcard3;

    /**
     * This method gets the number of diamonds on the ship
     * @return number of diamonds on the ship
     */
    public int getDiamondCount() {
        return diamondCount;
    }

    /**
     * This method gets the number of pearls on the ship
     * @return number of pearls on the ship
     */
    public int getPearlCount() 
    {return pearlCount;}

    /**
     * This method gets the number of rum barrels on the ship
     * @return number of rum barrels on the ship
     */
    public int getBarrelCount() 
    {return barrelCount;}

    /**
     * This method gets the number of gold bars on the ship
     * @return number of gold bars on the ship
     */
    public int getGoldCount() 
    {return goldCount;}

    /**
     * This method gets the number of rubies on the ship
     * @return number of rubies on the ship
     */
    public int getRubyCount() 
    {return rubyCount;}

    public void setDiamondCount(int input) {
        this.diamondCount = input;
    }

    /**
     * The constructor method for the ship class
     * @param name Name of the owner of this ship
     * @param playerNum The player number
     * @param color The colour of the ship
     * @param homePort Player associated with this ship
     * @param board The board this ship is placed on
     */
    public Ship(String name, int playerNum, String color, Port homePort, Board board){
        this.homePort = homePort;
        for(ColorType colorType : ColorType.values()) {
            if(colorType.getName()==color){
                this.color = colorType;
            }
        }
        assert this.color != null;
        homePort.setTileImage(this.color);
        this.name = name;
        this.playerNum = playerNum;
        shipImg = new BuccaneerImage(this.color.getShipImage());
        this.bearing = homePort.getStartFacing();
        //finds the starting position of the ship based on the player's home port
        position = board.tiles[homePort.getxPos()][homePort.getyPos()];
        treasures = new ArrayList<>();
        crewCards = new ArrayList<>();
        chanceCards = new ArrayList<>();
        generateGraphic(20);
    }

    /**
     * This method takes treasure from the ship
     * @return the treasure taken from the ship
     */
    public Treasure takeTreasure(){
        Random rand = new Random();
        int size = treasures.size();
        if(size<1){
            return null;
        }
        int index = rand.nextInt(treasures.size());
        Treasure chosenTreasure = treasures.get(index);
        treasures.remove(chosenTreasure);
        return chosenTreasure;
    }

    /**
     * This method takes the best/highest value treasure froma ship
     * @return the treasure taken from the ship
     */
    public Treasure takeBestTreasure(){
        int bestValue=0;
        Treasure bestTreasure = null;
        for(Treasure tempTreasure : treasures){
            if(tempTreasure.getValue()>bestValue){
                bestValue = tempTreasure.getValue();
                bestTreasure = tempTreasure;
            }
        }
        return bestTreasure;
    }

    /**
     * This method generates the graphic of the Ship to be shown on the board
     * @param boardSize int of the size of the board
     * @author Christian Harper
     */
    void generateGraphic(int boardSize){
        shipGraphic =  new Rectangle(GRAPHIC_SIZE, GRAPHIC_SIZE);
        shipGraphic.setOnMouseClicked(t -> {
            SceneControl.gameCtrl.boardClick(this);
        });
        shipGraphic.setFill(new ImagePattern(shipImg));
        shipGraphic.setLayoutX(GRAPHIC_SIZE * position.getxPos());
        shipGraphic.setLayoutY(GRAPHIC_SIZE * (boardSize-position.getyPos()));
        shipGraphic.setRotate(homePort.getStartFacing().getDegrees());
    }

    /**
     * This methods highlights the ship that is selected
     */
    public void finalizeLoad() {
        generateGraphic(20);
    }

    public void highlight() {
        if(shipGraphic == null) this.generateGraphic(20);
        shipGraphic.setStyle("-fx-stroke: red; -fx-stroke-width: 2px;");
    }

    /**
     * This method unhighlights the ship
     */
    public void unhighlight() {
        if(shipGraphic == null) this.generateGraphic(20);
        shipGraphic.setStyle(null);
    }

    /**
     * This method gets the graphic of the ship
     * @return the Rectangle object of the ship's image
     * @author Christian Harper
     */
    public Rectangle getShipGraphic() {
        if(shipGraphic == null) {
            generateGraphic(20);
        }
        return shipGraphic;
    }

    /**
     * This method gets the current bearing of the ship
     * @return String which holds the bearing
     * @author Olek Sadkowski
     */
    public BearingType getBearing() {
        return bearing;
    }

    /**
     * This method sets the new bearing of the ship
     * @param bearing String defining the new bearing
     * @author Olek Sadkowski
     */
    public void setBearing(BearingType bearing) {
        this.bearing = bearing;
        shipGraphic.setRotate(bearing.degrees);
    }

    /**
     * Gets the colour of the ship
     * @return String which holds the colour name
     * @author Olek Sadkowski
     */
    public ColorType getColour() {
        return color;
    }

    /**
     * Enables getting the current position of the ship on the grid
     * This method enables getting the current position of the ship on the grid
     * @return a list of size 2, index 0 is x coordinate and
     * index 1 is y coordinate
     * @author Olek Sadkowski
     */
    public Tile getPosition(){
        return position;
    }

    /**
     * This method sets the position of the ship
     * @param newPosition the tile being moved to
     */
    public void setPosition(Tile newPosition) {
        position = newPosition;
        shipGraphic.setLayoutX(GRAPHIC_SIZE * position.getxPos());
        shipGraphic.setLayoutY(GRAPHIC_SIZE * (20-position.getyPos()));
    }

    /**
     * This method gets the treasure on the ship
     * @return ArrayList containing all treasure on the ship
     */
    public ArrayList<Treasure> getShipTreasure(){
        return treasures;
    }

    /**
     * This method enables adding treasure to the ship
     * @param newTreasure Any new treasure to be added to the ship
     */
    public void addShipTreasure(Treasure newTreasure){
        if(treasures.size() < MAX_TREASURE){
            treasures.add(newTreasure);
        }
    }

    /**
     * This method gets the ships home port
     * @return the ship's home port
     */
    public Port getHomePort() {
        return homePort;
    }

    /**
     * This method gets the color of the ship
     * @return ColourType of the colour of the ship
     */
    public ColorType getColor(){ return color; }

    /**
     * This method takes the best crew card from the ship
     * @return the removed crew card
     */
    public CrewCard takeBestCrewCard(){
        CrewCard chosenCard=null;
        for(CrewCard crewCard : crewCards){
            if(chosenCard==null || chosenCard.getValue()<crewCard.getValue()){
                chosenCard = crewCard;
            }
            if(chosenCard.getValue()==3){
                break;
            }
        }
        crewCards.remove(crewCards);
        return chosenCard;
    }

    /**
     * This method removes a crew card from the ship
     * @return the crew card removed
     */
    public CrewCard takeCrewCard(){
        Random rand = new Random();
        int size = crewCards.size();
        if(size<1){
            return null;
        }
        int index = rand.nextInt(crewCards.size());
        CrewCard chosenCrewCards = crewCards.get(index);
        crewCards.remove(chosenCrewCards);
        return chosenCrewCards;
    }

    /**
     * This method removes a crew card of the specified value
     * @param value the value of the crew card to be removed
     * @return the removed crew card
     */
    public CrewCard takeCrewCardValue(int value){
        int size = crewCards.size();
        if(size<1) return null;
        for(CrewCard crewCard : crewCards){
            if(crewCard.getValue()==value) {
                crewCards.remove(crewCard);
                return crewCard;
            }

        }
        return null;
    }

    /**
     * This method gets the player's name
     * @return String of players name
     */
    public String getName() {
        return name;
    }

    /**
     * This method gets the player's number
     * @return int of player number
     */
    public int getPlayerNum() {
        return playerNum;
    }

    /**
     * This method adds a CrewCard to the player's hand
     * @param newCrewCard the CrewCard to be added
     */
    public void addCrewCard(CrewCard newCrewCard){crewCards.add(newCrewCard);}

    /**
     * This method gets the arraylist of the CrewCards in the player's hand
     * @return ArrayList(CrewCard) of all the Crew cards in the player's hand
     */
    public ArrayList<CrewCard> getCrewCards(){ return crewCards;}

    /**
     * This method gets number of crew cards a player has
     * @return Number of crew cards
     */
    public int getAllCrewCardNum(){ return crewCards.size();}

    /**
     * This method gets the ArrayList of the ChanceCards in the player's hand
     * @return ArrayList(ChanceCard) of all the ChanceCards in the player's hand
     */
    public ArrayList<ChanceCard> getChanceCards(){ return chanceCards; }

    /**
     * This method adds up the value of all crew cards
     * @return the total value of all crew cards
     */
    public int getAllCrewCardValue() {
        int totalValue = 0;
        for (CrewCard crew : crewCards) if(crew!=null)totalValue += crew.getValue();
        return totalValue;
    }

    /**
     * Adds treasure to the player's overall inventory
     * @param newTreasure Any new treasure to be added to the player's inventory
     */
    public void addAllTreasureToHome(Treasure newTreasure){
        getHomePort().getTreasures().add(newTreasure);
    }

    /**
     * This method gets all the treasure that the player holds on their ship and in their port
     * @return ArrayList of type Treasure
     */
    public ArrayList<Treasure> getAllTreasure(){
        return getHomePort().getTreasures();
    }

    /**
     * This method gets the number of treasure a player holds on their ship and in their port
     * @return int of number of treasures
     * @author Olek Sadkowski
     */
    public int getAllTreasureNum(){
        return getHomePort().getTreasures().size();
    }

    /**
     * This method counts all the crew cards for the player/ship
     */
    public void countCrewCards(){
        blackcrewcard1 = 0;
        blackcrewcard2 = 0;
        blackcrewcard3 = 0;
        redcrewcard1 = 0;
        redcrewcard2 = 0;
        redcrewcard3 = 0;
        for(CrewCard crewcard: crewCards){
            if(crewcard!=null) {
                if (crewcard.getCardColor() == CrewCard.colorType.black) {
                    if (crewcard.getValue() == 1) {
                        blackcrewcard1++;
                    }
                    if (crewcard.getValue() == 2) {
                        blackcrewcard2++;
                    }
                    if (crewcard.getValue() == 3) {
                        blackcrewcard3++;
                    }
                } else if (crewcard.getCardColor() == CrewCard.colorType.red) {
                    if (crewcard.getValue() == 1) {
                        redcrewcard1++;
                    }
                    if (crewcard.getValue() == 2) {
                        redcrewcard2++;
                    }
                    if (crewcard.getValue() == 3) {
                        redcrewcard3++;
                    }
                }
            }
        }
    }

    /**
     * This method gets the sum of the values of the chance cards stored in the port
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
     * This method gets the number of treasures on the ship
     * @return an integer between 0 and 2
     * @author Olek Sadkowski
     */
    public int getShipTreasureNum(){
        return treasures.size();
    }

    /**
     * This method calls getSurroundingTiles to get the tiles around the ship and check if they're island tiles
     */
    public Island isNextToIsland(){
        for (Tile tile : MoveAssistant.getSurroundingTiles(this))
            if (tile.getTileType() instanceof Island) {
                return (Island) tile.getTileType();
            }
        return null;
    }

    /**
     * This method checks if a ship is the same as another ship
     * @param o the ship being checked
     * @return true if the ships are identical
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return bearing == ship.bearing
                && Objects.equals(treasures, ship.treasures);
    }

    /**
     * This method gets a count of all of the players current treasure
     */
    public void getTreasureCounts() {
        diamondCount = 0;
        goldCount = 0;
        pearlCount = 0;
        rubyCount = 0;
        barrelCount = 0;
        for (Treasure treasure : treasures) {
            if(treasure!=null) {
                if (treasure.getType() == TreasureType.Diamonds) {
                    diamondCount++;
                } else if (treasure.getType() == TreasureType.GoldBars) {
                    goldCount++;
                } else if (treasure.getType() == TreasureType.Pearls) {
                    pearlCount++;
                } else if (treasure.getType() == TreasureType.Rubies) {
                    rubyCount++;
                } else if (treasure.getType() == TreasureType.BarrelsOfRum) {
                    barrelCount++;
                }
            }
        }
    }

    /**
     * Gets the crew cards
     */
    public int getBlackcrewcard1(){return blackcrewcard1;}
    public int getBlackcrewcard2(){return blackcrewcard2;}
    public int getBlackcrewcard3(){return blackcrewcard3;}
    public int getRedcrewcard1(){return redcrewcard1;}
    public int getRedcrewcard2(){return redcrewcard2;}
    public int getRedcrewcard3(){return redcrewcard3;}

    /**
     * This method calculates the distance from another ship
     * @param other the other ship
     * @return the distance from the other ship
     */
    public double distanceFromOtherShip(Ship other) {
        if(other == this) return 0;
        int otherX = other.getPosition().getxPos();
        int otherY = other.getPosition().getyPos();
        int myX = getPosition().getxPos();
        int myY = getPosition().getyPos();
        int distX = Math.abs(otherX - myX);
        int distY = Math.abs(otherY - myY);
        return Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
    }
}
