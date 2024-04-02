package uk.ac.aber.cs221.gp03.game;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import uk.ac.aber.cs221.gp03.grid.*;
import uk.ac.aber.cs221.gp03.items.*;
import uk.ac.aber.cs221.gp03.savegame.ImageAdapter;
import uk.ac.aber.cs221.gp03.savegame.PortAdapter;
import uk.ac.aber.cs221.gp03.savegame.TileAdapter;
import uk.ac.aber.cs221.gp03.savegame.TreasureAdapter;
import uk.ac.aber.cs221.gp03.utility.BuccaneerImage;
import uk.ac.aber.cs221.gp03.utility.MoveAssistant;
import uk.ac.aber.cs221.gp03.utility.ObjectStoreUtil;
import uk.ac.aber.cs221.gp03.utility.SceneControl;

import java.io.IOException;
import java.util.*;

/**
 * Driver class for the game
 *
 * @author Luke Purnell
 * @author Christian Harper
 * @author Olek Sadkowski
 * @author Tommy Boyle (tob31)
 * @version 16/03/2022
 */
public class GameLogic {
    protected static String[] playerNames;
    protected static Object[] playerColors;
    protected final int GAME_WIN = 20; // The amount of treasure required for a 'Win' state
    protected final int MAX_PLAYER = 4; // The number of players in the game
    protected int turnNum; // The current turn
    protected Ship currentPlayer; // The index of playerArr to represent the current players turn (might be replaced by enhanced for loop)
    protected FlatIsland flatIsland; // The instance of FlatIsland for this game
    protected PirateIsland pirateIsland; // The instance of PirateIsland for this game
    protected TreasureIsland treasureIsland; // The instance of TreasureIsland for this game
    protected Bay mudBay;
    protected Bay anchorBay;
    protected Bay cliffCreek;
    protected ArrayList<Port> ports; // The list of all the ports in the game
    protected Board getBoard; // Game board.
    protected Ship[] ships;
    protected ArrayList<Bay> bays;
    protected GameState gameState;
    protected Trade newTrade;
    private Ship battleLoser;
    private Ship previousShip;

    /**
     * The constructor method which initialises the game items
     */
    public GameLogic() {
        ships = new Ship[]{null, null, null, null};
        bays = new ArrayList<>();
        ports = new ArrayList<>();
        turnNum = 0;
        flatIsland = new FlatIsland();
        pirateIsland = new PirateIsland();
        treasureIsland = new TreasureIsland();
        bays = new ArrayList<>();
        bays.add(new Bay("Mud Bay", 0, 0, "/images/sprites/mudbay.png"));
        bays.add(new Bay("Anchor Bay", 19, 0, "/images/sprites/AnchorBay.png"));
        bays.add(new Bay("Cliff Creek", 19, 19, "/images/sprites/CliffCreek.png"));
        ports.add(new Port("London", 13, 0, BearingType.N));
        ports.add(new Port("Genoa", 0, 6, BearingType.E));
        ports.add(new Port("Marseilles", 6, 19, BearingType.S));
        ports.add(new Port("Cadiz", 19, 13, BearingType.W));
        ports.add(new Port("Amsterdam", 13, 19, BearingType.S));
        ports.add(new Port("Venice", 6, 0, BearingType.N));
        getBoard = new Board();
        initializePlayerInfo();
        //assignPorts();
        setupBoard();
        ArrayList<Integer> intOrder = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        Collections.shuffle(intOrder);
        for (int i = 0; i < 4; i++) {
            ships[i] = new Ship(playerNames[intOrder.get(i)], intOrder.get(i) + 1, playerColors[intOrder.get(i)].toString(), ports.get(i), getBoard());
        }
        ArrayList<ColorType> colorTypes = new ArrayList<>(Arrays.asList(ColorType.values()));
        ArrayList<ColorType> playerColorList = new ArrayList<>();
        for (Ship ship : ships) {
            playerColorList.add(ship.getColor());
        }
        for (ColorType colorType : playerColorList) {
            colorTypes.remove(colorType);
        }
        ports.get(4).setTileImage(colorTypes.get(0));
        ports.get(5).setTileImage(colorTypes.get(1));
        CardManager.generateChanceCards(treasureIsland);
        CardManager.generateCrewCards(pirateIsland);
        TreasureManager.generateTreasures((GameController) this);
        CardManager.distributeCrewCards(ports, (GameController) this);
        TreasureManager.distributeTreasures(ports, (GameController) this);
        CardManager.transferCrewCards(ships);
        ChanceCardEnforcer.createExecutor();
    }

    /**
     * This Method sets the player names and ship colours
     *
     * @param playerNames   inputted player names
     * @param playerColours inputted player colours
     */
    public static void setPlayerValues(String[] playerNames, Object[] playerColours) {
        GameLogic.playerNames = playerNames;
        GameLogic.playerColors = playerColours;
    }

    /**
     * This method creates a new game state and gets the first player
     */
    public void runGame() {
        if (SceneControl.gameScene != null) {
            SceneControl.gameScene.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
                if (KeyCode.ESCAPE == event.getCode()) {
                    SceneControl.gameCtrl.moreTreasure.setVisible(!SceneControl.gameCtrl.moreTreasure.isVisible());
                }
            });
        }
        gameState = new GameState();
        setCurrentPlayer(getNextPlayer());
    }

    /**
     * This method returns the bays on the board
     *
     * @return Array lis of Bays on the board
     */
    public ArrayList<Bay> getBays() {
        return bays;
    }

    /**
     * This method gets the list of islands
     *
     * @return static list of islands
     */
    public Island[] getIslands() {
        return new Island[]{flatIsland, pirateIsland, treasureIsland};
    }

    /**
     * This method is called before the game scene is loaded to initialise all the relevant variables and lists
     */
    public void initializePlayerInfo() {
        ArrayList<ColorType> playerColorList = new ArrayList<>();
        for (Object object : GameLogic.playerColors) { //converts the list of player choices into a list of ColorTypes
            for (ColorType colorType : ColorType.values()) {
                if (colorType.getName().equals(object.toString())) {
                    playerColorList.add(colorType);
                }
            }
        }
    }

    /**
     * This method sets up the board and places islands, bays, and ports in the correct places
     */
    private void setupBoard() {
        for (Port port : ports) {
            getBoard.tiles[port.getxPos()][port.getyPos()].setTileType(port);
        }
        for (Island island : getIslands()) {
            for (int i = island.getxPos1(); i <= island.getxPos2(); i++) {
                for (int j = island.getyPos1(); j <= island.getyPos2(); j++) {
                    getBoard.tiles[i][j].setTileType(island);
                }
            }
        }
        for (Bay bay : getBays()) {
            getBoard.tiles[bay.getxPos()][bay.getyPos()].setTileType(bay);
        }
    }

    /**
     * This method gets the list of ports
     *
     * @return a list of the ports for this game
     * @author Christian Harper
     */
    public ArrayList<Port> getPorts() {
        return ports;
    }

    public StateType getGameState() {
        return gameState.getState();
    }

    /**
     * Adds each of the generated ships to the board
     *
     * @author Christian Harper
     */
    public void addShips() {
        for (Ship ship : ships) {
            ((GameController) this).gamePane.getChildren().add(ship.getShipGraphic());
        }
    }

//    /**
//     * Assigns the players at random to their player ports
//     * @author Christian Harper
//     */
//    private void assignPorts() {
//        ArrayList<ColorType> colourTypes = new ArrayList<>(Arrays.asList(ColorType.values()));
//        Collections.shuffle(players);
//        for (int i = 0; i < MAX_PLAYER; i++) {
//            ports.get(i).setTileImage(players.get(i).getColor());
//            players.get(i).setHomePort(ports.get(i));
//            colorTypes.remove(players.get(i).getColor());
//        }
//        for(int i=4;i<6;i++){
//            ports.get(i).setTileImage(colorTypes.remove(0));
//        }
//    }

    /**
     * This method gets the array list of ships in play
     *
     * @return ships in play
     */
    public Ship[] getShips() {
        return ships;
    }

    /**
     * This method gets Pirate Island
     *
     * @return pirate island
     */
    public PirateIsland getPirateIsland() {
        return pirateIsland;
    }

    /**
     * This method gets Treasure Island
     *
     * @return treasure island
     */
    public TreasureIsland getTreasureIsland() {
        return treasureIsland;
    }

    /**
     * This method gets Flat Island
     *
     * @return flat island
     */
    public FlatIsland getFlatIsland() {
        return flatIsland;
    }

    /**
     * This method gets the board
     *
     * @return board
     */
    public Board getBoard() {
        return getBoard;
    }

    /**
     * This method gets the next active player
     *
     * @return Player object
     * @author Olek Sadkowski
     */
    private Ship getNextPlayer() {
        Ship nextShip = ships[turnNum %= MAX_PLAYER];
        turnNum++;
        return nextShip;
    }

    /**
     * This method resets the player after a battle has occurred
     */
    private void resetCurrentPlayer() {
        currentPlayer.unhighlight();
        currentPlayer = previousShip;
    }

    /**
     * This method gets the current player
     *
     * @return the ship which is the current player
     */
    public Ship getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * This method sets the current player
     *
     * @param newPlayer the ship that is the new player
     */
    private void setCurrentPlayer(Ship newPlayer) {
        if (newPlayer != currentPlayer && currentPlayer != null) currentPlayer.unhighlight();
        currentPlayer = newPlayer;
        currentPlayer.highlight();
        updateStats((GameController) this);
        if (currentPlayer.getPosition().getTileType() instanceof Port) {
            gameState.setState(StateType.MoveAnyDirection);
        }
        if (gameState.getState() != StateType.AfterBattleMove && SceneControl.gameCtrl != null)
            for (Tile validMove : MoveAssistant.getValidMoves(currentPlayer)) validMove.highlight();
    }

    /**
     * This method ends the players turn, checks for win condition and then saves the game
     */
    private void finishTurn() {
        boolean win = winGame();
        try {
            if (!win) saveGame("./save.json");
        } catch (IOException e) {
            PopUp.display("Error", "Failed to save game: " + e.getMessage(), 600, 600);
        }
        gameState.setState(StateType.Move);
        setCurrentPlayer(getNextPlayer());
        battleLoser = null;
        previousShip = null;
        // THIS IS A HACK
        Runtime.getRuntime().gc();
    }

    /**
     * This method checks to see if the players home port has treasure value >= 20
     *
     * @return Whether the game has been won
     */
    boolean winGame() {
        int homeTreasure = currentPlayer.getHomePort().getValueOfTreasures();
        if (homeTreasure >= GAME_WIN) {
            if (SceneControl.primaryStage != null) {
                SceneControl.primaryStage.setScene(SceneControl.winScene);
                SceneControl.winCtrl.changeText(currentPlayer.getName(), GAME_WIN);
            }
            return true;
        }
        return false;
    }

    /**
     * This method moves a ship from one tile to another
     *
     * @param tile the tile being moved to
     */
    public void moveShip(Tile tile) {
        for (Tile validMove : MoveAssistant.getValidMoves(currentPlayer)) validMove.unhighlight();
        currentPlayer.setPosition(tile);
        checkPosition();
    }

    /**
     * This method checks if a player is in a port or bay and the actions they can take there
     */
    public void checkPosition() {
        TileType currentTileType = currentPlayer.getPosition().getTileType();
        if (currentTileType instanceof Port) {
            //deposit to home port
            newTrade = new Trade(currentPlayer, (Port) currentTileType);
            if (currentPlayer.getHomePort().equals(currentTileType)) {
                newTrade.depositToHomePort();
                SceneControl.tradeCtrl.setTrade(newTrade);
                SceneControl.primaryStage.setScene(SceneControl.tradeScene);
            } else {
                //trade with others
                SceneControl.tradeCtrl.setTrade(newTrade);
                SceneControl.primaryStage.setScene(SceneControl.tradeScene);
            }

        } else if (currentTileType instanceof Bay && currentTileType == bays.get(1)) {
            ChanceCard correctCard = null;
            for (ChanceCard chanceCard : currentPlayer.getChanceCards()) {
                if (chanceCard.getId() == 20 || chanceCard.getId() == 21) {
                    correctCard = chanceCard;
                }
            }
            ChanceCardEnforcer.chanceClasses(correctCard, currentPlayer, this);
        } else if (currentTileType == null) {
            if (MoveAssistant.detectIsland(currentPlayer) == null) return;
            islandReached(MoveAssistant.detectIsland(currentPlayer));
        }
    }

    /**
     * This method checks if a player is by an island and completes the action needed
     *
     * @param islandReached the island that has been reached by the player
     */
    private void islandReached(Island islandReached) {
        if (islandReached instanceof TreasureIsland) {
            CardManager.dealChanceCard(currentPlayer, this);
        } else if (islandReached instanceof FlatIsland) {
            TreasureManager.dealTreasures(currentPlayer);
        }
    }

    /**
     * This method rotates a ship to face a tile
     *
     * @param tile the tile being faced
     */
    public void rotateShip(Tile tile) {
        for (Tile validRotate : MoveAssistant.getSurroundingSailable(currentPlayer)) validRotate.unhighlight();
        currentPlayer.setBearing(BearingType.calculateBearing(currentPlayer.getPosition(), tile));
    }

    /**
     * This method handles when a tile is clicked
     *
     * @param tile the tile being clicked
     * @throws IOException
     */
    public void boardClick(Tile tile) throws IOException {
        if (MoveAssistant.getValidMoves(currentPlayer).contains(tile)) {
            if (gameState.getState() == StateType.Move || gameState.getState() == StateType.MoveAnyDirection) {
                Ship shipInTheWay = shipInTheWay(); // Check if ship is in the way
                if (shipInTheWay != null) { // If a ship is in the path...
                    if (!battleChoice(currentPlayer, shipInTheWay)) { // If current player declines...
                        battleChoice(shipInTheWay, currentPlayer); // Allow stationary player to initiate battle.
                    }
                }
                moveShip(tile); // Move to selected tile;
                if (currentPlayer.getPosition().getTileType() instanceof Port) {
                    currentPlayer.setBearing(((Port) currentPlayer.getPosition().getTileType()).getStartFacing());
                    finishTurn();
                } else if (battleLoser != null) {
                    setCurrentPlayer(battleLoser);
                    moveShip(tile);
                    resetCurrentPlayer();
                    finishTurn();
                } else {
                    gameState.setState(StateType.Rotate);
                    for (Tile validRotate : MoveAssistant.getSurroundingSailable(currentPlayer))
                        validRotate.highlight();
                    return;
                }
            }
        }
        if (MoveAssistant.getSurroundingSailable(currentPlayer).contains((tile))) {
            if (gameState.getState() == StateType.Rotate) {
                rotateShip(tile);
                finishTurn();
            }
        }
        if (gameState.getState() == StateType.AfterBattleMove) {
            rotateShip(tile);
            gameState.setState(StateType.Move);
            for (Tile validMove : MoveAssistant.getValidMoves(currentPlayer)) validMove.highlight();
        }
        if (tile.getTileType() != null) {
            if (tile.getTileType() instanceof Port) {
                String name = getPortName(tile);
                ((Port) tile.getTileType()).portInfo(name);
            } else if (tile.getTileType() instanceof Bay){
                ((Bay) tile.getTileType()).bayInfo(tile.getTileType().getName());
            } else {
                tile.getTileType().tileInfo();
            }
        }
    }

    boolean battleChoice(Ship instigator, Ship victim) {
        PopUpChoice.display("Battle Opportunity", instigator.getName() + ": Do you want to battle " + victim.getName() + '?', 200, 200, new String[]{"Yes", "No"});
        switch (PopUpChoice.userChoice) {
            case 0:
                Battle.startAttack(instigator, victim);
                return true;
            case 1:
                break;
        }
        return false;
    }

    /**
     * Detect if there's a ship in the way of the selected movement
     *
     * @return The ship in the way
     */
    private Ship shipInTheWay() {
        Set<Tile> movementTiles = MoveAssistant.getValidMoves(currentPlayer);
        for (Ship ship : ships) {
            if (movementTiles.contains(ship.getPosition())) {
                return ship;
            }
        }
        return null;
    }

    /**
     * This method handles click events on ships
     *
     * @param ship Ship that was clicked on
     */
    public void boardClick(Ship ship) {
        if (ship == currentPlayer) {
            moveShip(ship.getPosition());
            for (Tile validRotate : MoveAssistant.getSurroundingSailable(currentPlayer)) validRotate.highlight();
            gameState.setState(StateType.Rotate);
        } else if (gameState.getState() != StateType.Rotate) {
            if (MoveAssistant.getValidMoves(currentPlayer).contains(ship.getPosition())) {
                for (Tile validMove : MoveAssistant.getValidMoves(currentPlayer)) validMove.unhighlight();
                tryBattle(ship);
            }
        }
        if (gameState.getState() == StateType.Rotate) {
            if (MoveAssistant.getSurroundingSailable(currentPlayer).contains(ship.getPosition())) {
                rotateShip(ship.getPosition());
                finishTurn();
            }

        }
    }

    /**
     * Checks if valid battle scenario and initiates battle on click
     *
     * @author Luke Purnell
     */
    private void tryBattle(Ship targetShip) {
        if (targetShip.getPosition().getTileType() instanceof Port || MoveAssistant.detectIsland(targetShip) instanceof TreasureIsland) {
            PopUp.display("Error", "Can't attack a player in a Port or on the coast of Treasure Island", 300, 300);
            return;
        }
        battleLoser = Battle.startAttack(currentPlayer, targetShip);
        previousShip = currentPlayer;
        gameState.setState(StateType.AfterBattleMove);
        moveShip(targetShip.getPosition());
        setCurrentPlayer(battleLoser);
        for (Tile validRotate : MoveAssistant.getSurroundingSailable(battleLoser)) validRotate.highlight();
    }

    /**
     * This method gets the name of the port that is occupying a tile
     *
     * @param tile the tile being occupied
     * @return the name of the port
     */
    public String getPortName(Tile tile) {
        return ((Port) tile.getTileType()).getName();
    }

    /**
     * This method compares this GameLogic object to another GameLogic object to check for equality.
     *
     * @param o Object to compare against
     * @return If the objects are the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameLogic gameLogic = (GameLogic) o;
        return turnNum == gameLogic.turnNum
                && Objects.equals(currentPlayer, gameLogic.currentPlayer)
                && Objects.equals(flatIsland, gameLogic.flatIsland)
                && Objects.equals(pirateIsland, gameLogic.pirateIsland)
                && Objects.equals(treasureIsland, gameLogic.treasureIsland)
                && Objects.equals(mudBay, gameLogic.mudBay)
                && Objects.equals(anchorBay, gameLogic.anchorBay)
                && Objects.equals(cliffCreek, gameLogic.cliffCreek)
                && Objects.equals(ports, gameLogic.ports)
                && Objects.equals(getBoard, gameLogic.getBoard)
                && Arrays.equals(ships, gameLogic.ships)
                && Objects.equals(bays, gameLogic.bays);
    }

    /**
     * This method updates and changes the labels on the screen to displays the statistics
     */
    public void updateStats(GameController game) {
        // this check disables labels for junit tests where labels are null
        if (game.locLbl != null) {
            game.locLbl.setText(currentPlayer.getPosition().getxPos() + "x, " + currentPlayer.getPosition().getyPos() + "y");
            game.bearingLbl.setText(currentPlayer.getBearing().toString());
            game.homeLbl.setText(currentPlayer.getHomePort().getName());
            game.playerNumLbl.setText(String.valueOf(currentPlayer.getPlayerNum()));
            game.moveLbl.setText(String.valueOf(currentPlayer.getAllCrewCardValue()));
            game.playerNameLbl.setText(String.valueOf(currentPlayer.getName()));
            //displays the players chance cards and crew as text
            game.chanceTxt = (String.valueOf(currentPlayer.getChanceCards()));
            //game.crewTxt = (String.valueOf(currentPlayer.getCrewCards()));
            //
            currentPlayer.getTreasureCounts();
            game.barrelLabel.setText('x' + String.valueOf(currentPlayer.getBarrelCount()));
            game.diamondLabel.setText('x' + String.valueOf(currentPlayer.getDiamondCount()));
            game.rubyLabel.setText('x' + String.valueOf(currentPlayer.getRubyCount()));
            game.pearlLabel.setText('x' + String.valueOf(currentPlayer.getPearlCount()));
            game.goldLabel.setText('x' + String.valueOf(currentPlayer.getGoldCount()));
            game.goldPortLabel.setVisible(false);
            game.pearlPortLabel.setVisible(false);
            game.barrelPortLabel.setVisible(false);
            game.rubyPortLabel.setVisible(false);
            game.diamondPortLabel.setVisible(false);
            game.goldPortImage.setVisible(false);
            game.pearlPortImage.setVisible(false);
            game.barrelPortImage.setVisible(false);
            game.rubyPortImage.setVisible(false);
            game.diamondPortImage.setVisible(false);
            currentPlayer.countCrewCards();
            game.blackcrewcard1 = ('x' + String.valueOf(currentPlayer.getBlackcrewcard1()));
            game.blackcrewcard2 = ('x' + String.valueOf(currentPlayer.getBlackcrewcard2()));
            game.blackcrewcard3 = ('x' + String.valueOf(currentPlayer.getBlackcrewcard3()));
            game.redcrewcard1 = ('x' + String.valueOf(currentPlayer.getRedcrewcard1()));
            game.redcrewcard2 = ('x' + String.valueOf(currentPlayer.getRedcrewcard2()));
            game.redcrewcard3 = ('x' + String.valueOf(currentPlayer.getRedcrewcard3()));
        }
    }

    /**
     * This method gets the GsonBuilder suitable for saving and loading the game.
     * Regular GsonBuilders are not suitable because speciaic classes have custom serialization and deserialization strategies.
     *
     * @return The GsonBuilder
     */
    private GsonBuilder getGameGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();

        // register type adapters for nonstandard json parsing
        builder.registerTypeAdapter(Port.class, new PortAdapter());
        builder.registerTypeAdapter(Tile.class, new TileAdapter());
        builder.registerTypeAdapter(BuccaneerImage.class, new ImageAdapter());
        builder.registerTypeAdapter(Treasure.class, new TreasureAdapter());

        // exclude a bunch of unnecessary javafx stuff from being saved
        ExclusionStrategy strategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                return field.getDeclaredType() == Rectangle.class
                        || field.getDeclaredType() == AnchorPane.class
                        || field.getDeclaredType() == Label.class
                        || field.getDeclaredType() == ImageView.class
                        || field.getDeclaredType() == Button.class;
            }

            @Override
            public boolean shouldSkipClass(Class<?> c) {
                return false;
            }
        };

        builder.setExclusionStrategies(strategy);

        return builder;
    }

    /**
     * This method saves the game to a file.
     * Not all game information is saved, only enough to restore the game to the
     * state it was before it was saved.
     * <p>
     * If the file already exists the save data will be overwritten.
     * If the file does not exist it will be created.
     *
     * @param fileLocation The file location to save game data to.
     * @throws IOException If there was an error when writing the file.
     * @author James Croucher
     */
    public void saveGame(String fileLocation) throws IOException {
        // gets the builder to use, with all the necessary rules
        GsonBuilder builder = getGameGsonBuilder();

        // write game to file
        ObjectStoreUtil.writeObjectWithBuilder(fileLocation, this, builder);
    }

    /**
     * This method sets this game's state from a file.
     * Loads a JSON file with save data and sets the properties of this object to match what is stored.
     *
     * @param fileLocation The location of the file to load.
     * @author James Croucher
     */
    public void setGameStateFromFile(String fileLocation) throws IOException {
        // get correct builder and read file
        GsonBuilder builder = getGameGsonBuilder();
        GameController output = (GameController) ObjectStoreUtil.readObjectWithBuilder(fileLocation, builder, GameController.class);

        // assign all necessary properties
        this.turnNum = output.turnNum;
        this.currentPlayer = output.currentPlayer;
        this.flatIsland = output.flatIsland;
        this.pirateIsland = output.pirateIsland;
        this.treasureIsland = output.treasureIsland;
        this.mudBay = output.mudBay;
        this.anchorBay = output.anchorBay;
        this.cliffCreek = output.cliffCreek;
        this.ports = output.ports;
        this.ships = output.ships;
        this.bays = output.bays;
        this.gameState = new GameState();
        this.gameState.setState(StateType.Move);
        // set correct rotation of ships and render their graphics
        for (Ship ship : this.ships) {
            ship.generateGraphic(20);
            ship.getShipGraphic().setRotate(ship.getBearing().getDegrees());
        }
        addShips();
        // move player to next
        this.setCurrentPlayer(getNextPlayer());
    }
}