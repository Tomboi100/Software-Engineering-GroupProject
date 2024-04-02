package uk.ac.aber.cs221.gp03.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import uk.ac.aber.cs221.gp03.utility.SceneControl;

import java.io.IOException;
import java.util.Objects;


import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/**
 * FXML I/O handler class for help display screen
 *
 * @author Tommy Boyle (tob31)
 * @author James Falkner (jaf43)
 * @author Steffan Davies-John (std36)
 * @version 06/05/2022
 */
public class HelpMenuController {

    @FXML
    private Label lblTitle;
    @FXML
    private Label lblBody;
    @FXML
    private Label lblPageNo;
    @FXML
    private static Button btnHelpClose;
    int pageNo =  1;

    public void initialize() {
        SceneControl.helpCtrl = this;
    }

    /**
     * help information content that uses a switch case for each page to update the labels with the information
     */
    @FXML
    private void pageContent(){
        switch (pageNo){
            case 0:
                lblTitle.setText("How To Win");
                lblBody.setText("First player to bring 20 points of treasure to their home port wins!");
                lblPageNo.setText("1/5");
                break;
            case 1:
                lblTitle.setText("Moving");
                lblBody.setText("The rules for movement are as follows: \n1) At the start of each move the player can either chose to move or rotate their ship" +
                        "\n2) Each move must be in a straight line \n" +
                        "3) You can only move your ship equivalent to the value of your sailing cards \n4) At the end of the move you must move your ship to face the direction you wish to sail in next turn \n" +
                        "5) A ship must be moved or turned each turn unless its in your home port" +
                        "\n6) A player who has lost all their sailing cards may only move 1 square per turn" +
                        "\n7) If you cross paths with another player or finish your turn in an adjacent tile you can attack!  ");
                lblPageNo.setText("2/5");
                break;
            case 2:
                lblTitle.setText("Attacking");
                lblBody.setText("If a player is sharing a square with another player either can choose to attack, however ships at port or Treasure Island are unable to be attacked. Once a player has declared an attack both players reveal their fighting strength." +
                        " The winner gets either:" +
                        "\nAll the treasure on board the losers ship. If the winner already has their own treasure the winner takes the highest value treasure and the rest of the losers treasure returns to Treasure Island;" +
                        "\nOr if the loser has no treasure the winner receives the two lowest value crew cards." +
                        "\nThe loser then makes a free move out of turn, in any direction and up to the maximum of their sailing strength but they cannot attack. They can move to Treasure Island and take a chance card.");
                lblPageNo.setText("3/5");
                break;
            case 3:
                lblTitle.setText("Treasure Island");
                lblBody.setText("If a player is adjacent to Treasure Island in any way they take a chance card and obeys the instructions on the card." +
                        "\nIf the card gives the player an amount of treasure it's their choice how they make the value up from the treasure available." +
                        "\nProviding a ship moves or changes direction they can continue to take chance cards fo as long as they're adjacent to Treasure Island." +
                        "However a ship MAY NOT carry more than 2 pieces of treasure at any one time.");
                lblPageNo.setText("4/5");
                break;
            case 4:
                lblTitle.setText("Trading");
                lblBody.setText("When a player enters a port they have the option to trade any of the cards in their hand" +
                        " for any of the cards at the port or island. The trade has to be for cards of equivalent value (5 treasure for 5 treasure; 3 crew for 3 crew)." +
                        " Any cards (crew or chance) that are traded with someones home port are transferred directly into the owner's hand.");
                lblPageNo.setText("5/5");
                break;
        }
    }

    /**
     * Creates the window/stage and displays help information that updates the labels on the screen
     */
    public void displayHelp()throws IOException {
        Stage help = new Stage();
        Scene helpScene = SceneControl.helpScene;
        /*
        FXMLLoader loader = new FXMLLoader(SceneControl.class.getResource("/fxml/HelpMenu.fxml"));
        Scene helpScene = new Scene(loader.load());
        */
        help.setScene(helpScene);

        //help.initModality(Modality.APPLICATION_MODAL);
        help.setTitle("Help");
        help.setMinHeight(help.getHeight());
        help.setMinWidth(help.getWidth());

        help.setScene(helpScene);
        help.showAndWait();
    }

    /**
     * Moves to the next help screen page
     */
    public void btnNext(){
        pageContent();
        pageNo++;
        if (pageNo > 4){
            pageNo %= 5;
        }
    }

    /**
     * Closes the help screen
     * @param event the mouse event to close the window
     */
    @FXML
    public void helpClose(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

}
