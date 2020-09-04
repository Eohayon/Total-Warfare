import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * File Name: Barrel.java
 * <p>
 * Static Barrel object to act as a barrier between players
 *
 * @author  Jonah Belman
 * @version 1.0
 * @since   March 7, 2018
 */

class Barrel extends ImageView {

    /**
     *Constructor that initializes a barrel with a type and X and Y coordinates
     *
     * @param type Type of barrel (GREEN, GREY or RED)
     * @param x X Coordinate
     * @param y Y Coordinate
     * @author Jonah Belman
     */
    public Barrel(Enums.Barrel type, double x, double y) {

        Image image;
        if (type == Enums.Barrel.GREEN) {
            image = new Image("/resources/Barrel Green.png");
        } else if (type == Enums.Barrel.GREY) {
            image = new Image("/resources/Barrel Grey.png");
        } else {
            image = new Image("/resources/Barrel Red.png");
        }

        this.setImage(image);
        setTranslateX(x);
        setTranslateY(y);
    }
}

