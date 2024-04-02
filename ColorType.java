package uk.ac.aber.cs221.gp03.game;

import java.util.ArrayList;

/**
 * This class is an enum to manage the different colours a ship can have
 * @version 29/02/2022
 * @author Christian Harper
 */
public enum ColorType {
    Blue("Blue", "/images/sprites/blue_ship.png","/images/sprites/blue_port.png"),
    Green("Green", "/images/sprites/green_ship.png","/images/sprites/green_port.png"),
    Purple("Purple", "/images/sprites/purple_ship.png","/images/sprites/purple_port.png"),
    Red("Red", "/images/sprites/red_ship.png","/images/sprites/red_port.png"),
    Yellow("Yellow", "/images/sprites/yellow_ship.png","/images/sprites/yellow_port.png"),
    Orange("Orange", "/images/sprites/orange_ship.png","/images/sprites/orange_port.png");

    public final String name, shipImage, portImage; //The name of the color as a string and the file location of the png to represent the colour ship

    /**
     * This method gets the name of the ship's colour as a string
     * @return String of the colour of the ship
     * @author Christian Harper
     */
    public String getName(){ return name; }

    /**
     * This method gets the file location of the image to represent the coloured ship
     * @return String of the file location of the image corresponding to the colour
     * @author Christian Harper
     */
    public String getShipImage(){ return shipImage; }


    /**
     * This method gets the file location of the image to represent the coloured port
     * @return String of the file location of the image corresponding to the colour
     * @author Christian Harper
     */
    public String getPortImage(){ return portImage; }

    /**
     * The constructor for a ColourType
     * @param name String of the name of the colour
     * @param imageLocation String of the file location corresponding to the colour of the ship
     * @author Christian Harper
     */
    ColorType(String name, String imageLocation, String portImage){
        this.name = name;
        this.shipImage = imageLocation;
        this.portImage = portImage;
    }
}
