import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Program Name: Total Warfare
 * File Name: Tank.java
 * Object for the tanks in the game
 *
 * @author  Ethan Ohayon
 * @version 1.0
 * @since   February 28, 2018
 */

class Tank extends StackPane {

    private final ImageView turret;
    private final AnimationTimer timer;
    private double velocity;
    private double health;
    private Enums.Player player;
    private Enums.Tank type;

    /**
     * Constructor for Tank which creates the AnimationTimer used when driving
     *
     * @author Ethan Ohayon
     */
    public Tank() {

        turret = new ImageView(new Image("/resources/Turret.png"));
        turret.setTranslateY(-20);
        this.getChildren().addAll(turret);

        timer = new AnimationTimer() {
            /**
             * AnimationTimer to be run when driving
             *
             * @param timestamp The timestamp of the curent frame in nanoseconds
             * @author Ethan Ohayon
             */
            @Override
            public void handle(long timestamp) {
                setTranslateX(getTranslateX() + velocity);
                Main.fuelLevel.set(Main.fuelLevel.get() - 1.5); //Reduces the fuel level every time the AnimationTimer is run

                if (Main.fuelLevel.get() == -0.5 || Main.fuelLevel.get() == 0) { //Compensates for the possibility of the fuel level becoming negative
                    stop();
                    Main.fuelLevel.set(0);
                }

                if (player == Enums.Player.ONE) {

                    //Moves the power bar along with the tank and sets the boundaries for driving

                    Main.powerLine.setStartX(getTranslateX() + 30);
                    Main.powerLine.setEndX(Main.powerLine.getEndX() + velocity);
                    Main.fuelBar.setProgress(Main.fuelLevel.get()/100);

                    if (getTranslateX() + getWidth() >= 492 || getTranslateX() <= 0) {
                        brake();
                    }

                } else {

                    //Moves the power bar along with the tank and sets the boundaries for driving

                    Main.powerLine.setStartX(Main.width - (1220 - getTranslateX()));
                    Main.powerLine.setEndX(Main.powerLine.getEndX() + velocity);
                    Main.fuelBar2.setProgress(Main.fuelLevel.get()/100);

                    if (getTranslateX() < 759 || getTranslateX() + getWidth() >= 1250) {
                        brake();
                    }
                }
            }
        };
    }

    /**
     * Sets the angle of the turret
     *
     * @param theta The angle to rotate to
     * @author Ethan Ohayon
     */
    public void rotate(double theta) {
        turret.setRotate(360 - theta);
    }

    /**
     * Sets the velocity of the driving
     *
     * @param direction The direction to drive (LEFT or RIGHT)
     * @param player The player that is driving (ONE or TWO)
     * @author Ethan Ohayon
     */
    public void drive(Enums.Direction direction, Enums.Player player) {

        this.player = player;

        if (direction == Enums.Direction.RIGHT) {
            velocity = 5;
        } else {
            velocity = -5;
        }
         timer.start();
    }

    /**
     * Stops the AnimationTimer in order to stop the movement of the tank
     *
     * @author Ethan Ohayon
     */
    public void brake() {
        timer.stop();
    }

    /**
     * Sets the type/design of the tank
     *
     * @param type The type of tank (DESERT, NAVY, GREY or GREEN)
     * @author Ethan Ohayon
     */
    public void setType(Enums.Tank type) {

        Image image;
        this.type = type;

        if (type == Enums.Tank.DESERT) {
            image = new Image("/resources/Tank Desert.png");
        } else if (type == Enums.Tank.GREEN) {
            image = new Image("/resources/Tank Green.png");
        } else if (type == Enums.Tank.NAVY) {
            image = new Image("/resources/Tank Navy.png");
        } else {
            image = new Image("/resources/Tank Grey.png");
        }
        this.getChildren().add(new ImageView(image));
    }

    /**
     * Returns the type of the tank
     *
     * @return Enums.Tank type
     * @author Ethan Ohayon
     */
    public Enums.Tank getType() {
        return type;
    }

    /**
     * Returns the amount of health left for the tank
     *
     * @return double health
     * @author Ethan Ohayon
     */
    public double getHealth() {
        return health;
    }

    /**
     * Sets the health for the tank
     *
     * @param health Health value of the tank
     * @author Ethan Ohayon
     */
    public void setHealth(double health) {
        this.health = health;
    }
}