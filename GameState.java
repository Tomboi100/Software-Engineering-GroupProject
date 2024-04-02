package uk.ac.aber.cs221.gp03.game;

import java.util.Objects;

/**
 * This class handles the state that the game is in
 * @version 06/05/22
 */

public class GameState {

    private StateType currentGameState;

    /**
     * This is the constructor for the Game state
     */
    public GameState(){
        currentGameState = StateType.MoveAnyDirection;
    }

    /**
     * This method sets the game state
     * @param state the new game state
     */
    public void setState(StateType state) {
        currentGameState = state;
    }

    /**
     * This method gets the current game state for the game
     * @return the current game state
     */
    public StateType getState() {
        return currentGameState;
    }

    /**
     * This method checks if two objects have the same game state
     * @param o the object being checked
     * @return true if the game states are the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameState gameState = (GameState) o;
        return currentGameState == gameState.currentGameState;
    }
}
