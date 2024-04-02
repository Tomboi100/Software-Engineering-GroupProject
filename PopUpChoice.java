package uk.ac.aber.cs221.gp03.game;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import uk.ac.aber.cs221.gp03.items.ChanceCardEnforcer;

import java.util.ArrayList;

/**
 * Class to create message popups for errors or warnings
 * @author James Falkner
 * @version 06/05/22
 */
public class PopUpChoice {


    public static int userChoice;

    /**
     * This method  is used to display popup message with different choices
     * @param title the title of the popup
     * @param message the message to be displayed to the user
     * @param width the width of the popup
     * @param height the height of the popup
     * @param choices the different choices available to the user
     */

    public static void display(String title, String message, int width, int height, String[]choices) {
        Stage popup = new Stage();

        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle(title);
        popup.setMinHeight(width);
        popup.setMinWidth(height);

        Label label = new Label();
        label.setText(message);

        ArrayList<Button> buttonArrayList = new ArrayList<>();
        if(choices==null || choices.length==0){
            Button tempButton = new Button("close");
            tempButton.setOnAction(e-> popup.close());
            buttonArrayList.add(tempButton);
        }else {
            for (int i = 0; i < choices.length; i++) {
                Button tempButton = new Button(choices[i]);
                int finalI = i;
                tempButton.setOnAction(e -> ChanceCardEnforcer.setChanceCardChoice(finalI, e));
                buttonArrayList.add(tempButton);
            }
        }

        VBox structure = new VBox(25);
        structure.getChildren().add(label);
        for(Button button : buttonArrayList){
            structure.getChildren().add(button);
        }
        structure.setAlignment(Pos.CENTER);

        Scene scene = new Scene(structure);
        popup.setScene(scene);
        popup.showAndWait();

    }

}
