import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;
import java.util.stream.IntStream;

/**
 * File Name: Projectile.java
 * <p>
 * Object for the games projectiles containing all required calculations to simulate real world conditions
 *
 * @author  Ethan Ohayon
 * @version 1.0
 * @since   February 28, 2018
 */

class Projectile extends Ellipse {

    private final Image image;
    private double time;
    private final double mass;
    private double theta;
    private double initialX;
    private double initialY;
    private final double initialX2;
    private final double initialY2;
    private double velocity;
    private double velocityX;
    private double velocityY;
    private boolean IN_MOTION;
    private final double accelerationY;
    private int frameCounter = 0;
    private final double frameRate = 0.006;
    private final Timeline shoot = new Timeline();

    /**
     * Constructor for Projectile with the parameters that are known at the start
     *
     * @param initialX Initial X coordinate of the Projectile
     * @param initialY Initial Y coordinate of the Projectile
     * @param accelerationY The acceleration in the Y direction
     * @param mass The mass of the projectile
     * @param type The type of Projectile (CHAIM or TRACTOR)
     * @author Ethan Ohayon
     */
    public Projectile(double initialX, double initialY, double accelerationY, double mass, Enums.Rocket type) {

        if (type == Enums.Rocket.CHAIM) {
            image = new Image("/resources/Rocket Chaim.png");
        } else {
            image = new Image("/resources/Rocket Tractor.png");
        }
        this.mass          = mass;
        this.initialX      = initialX;
        this.initialX2     = initialX;
        this.initialY      = initialY;
        this.initialY2     = initialY;
        this.accelerationY = accelerationY;

        setCenterX(initialX);
        setCenterY(initialY);
        super.setRadiusX(25);
        super.setRadiusY(17);
        super.setFill(Color.TRANSPARENT);
    }

    /**
     * Sets the KeyFrame to be played in the main timeline to update the game.
     *
     * @author Ethan Ohayon
     */
    public void animate() {

        //Calls updateGame every 0.001 seconds

        IN_MOTION = true;

        KeyFrame shotFrames = new KeyFrame(Duration.seconds(0.001), event -> {

            time += frameRate;
            Main.updateGame();
            super.setFill(new ImagePattern(image));
        });
        shoot.setCycleCount(Animation.INDEFINITE);
        shoot.getKeyFrames().clear();
        shoot.getKeyFrames().add(shotFrames);
        shoot.playFromStart();
    }

    /**
     * Calculates a new point for the Projectile
     *
     * @return Point2D newPoint
     * @author Ethan Ohayon
     */
    public Point2D getNewPoint() {

        //Calculates the X and Y coordinates of the projectile according to how long its been airborne

        time = time + frameRate;

        double positionX = initialX - (getVelocityX()*time);
        double positionY = initialY - (getVelocityY()*time - (this.getAccelerationY()/2)*Math.pow(time, 2));

        return new Point2D(positionX, positionY);
    }

    /**
     * Resets certain parameters of the Projectile so it can be shot again
     *
     * @author Ethan Ohayon
     */
    public void reset() {

        //Resets all the properties of the projectile so it can be shot again without creating a new one

        shoot.stop();
        setTime(0);
        setInitialX(initialX2);
        setInitialY(initialY2);
        super.setRadiusX(25);
        super.setRadiusY(17);
        super.setFill(new ImagePattern(image));
        IN_MOTION = false;
    }

    /**
     * Animates the explosion of the Projectile
     *
     * @author Ethan Ohayon
     */
    public void explode() {

        //Animates the exploding of the projectile

        Timeline explosion = new Timeline();
        Image[] frames = IntStream.range(0, 12).mapToObj(i -> new Image("/resources/Explosion " + String.valueOf(i+1) + ".png")).toArray(Image[]::new);

        KeyFrame explosionFrames = new KeyFrame(Duration.seconds(0.05), event -> {

            setRadiusX(37);
            setRadiusY(37);
            setFill(new ImagePattern(frames[frameCounter++]));
            if (frameCounter == 12) {
                frameCounter = 0;
                setFill(Color.TRANSPARENT);
            }
            IN_MOTION = true;
        });
        explosion.setCycleCount(12);
        explosion.getKeyFrames().add(explosionFrames);
        explosion.play();

        explosion.setOnFinished(finished -> IN_MOTION = false);

        reset();
    }

    /**
     * Calculates the vertex of the Projectile to be used when rotating it
     *
     * @return Point2D vertex
     * @author Ethan Ohayon
     */
    public Point2D getVertex() {

        //Calculates the vertex of the trajectory

        double time = Math.abs((2 * getVelocity() * Math.sin(getTheta()) / getAccelerationY())) / 2;

        double positionX = initialX - (getVelocityX()*time);
        double positionY = initialY - (getVelocityY()*time - (this.getAccelerationY()/2)*Math.pow(time, 2));

        return new Point2D(positionX, positionY);
    }

    /**
     * Returns the IN_MOTION boolean
     *
     * @return boolean IN_MOTION
     * @author Ethan Ohayon
     */
    public boolean isInMotion() {
        return IN_MOTION;
    }

    /**
     * Returns the angle the projectile is to be shot at
     *
     * @return double theta
     * @author Ethan Ohayon
     */
    private double getTheta() {
        return theta - 270;
    }

    /**
     * Sets the angle the projectile is to be shot at
     *
     * @param theta Angle of Projectile
     *
     * @author Ethan Ohayon
     */
    public void setTheta(double theta) {

        //Sets the angle that the projectile is to be shot at and calculates its X and Y velocities
        velocityX  = getVelocity() * Math.sin(Math.toRadians(theta));
        velocityY  = getVelocity() * Math.cos(Math.toRadians(theta));
        this.theta = theta;
    }

    /**
     * Sets the time that the Projectile has been in motion
     *
     *
     * @param time Time the Projectile has been in motion
     *
     * @author Ethan Ohayon
     */
    private void setTime(double time) {
        this.time = time;
    }

    /**
     * Sets the initial X of the Projectile
     *
     * @param initialX Initial X coordinate
     *
     * @author Ethan Ohayon
     */
    public void setInitialX(double initialX) {
        this.initialX = initialX;
    }

    /**
     * Sets the initial Y of the Projectile
     *
     * @param initialY Initial Y coordinate
     *
     * @author Ethan Ohayon
     */
    private void setInitialY(double initialY) {
        this.initialY = initialY;
    }

    /**
     * Returns the velocity of the Projectile
     *
     * @return double velocity
     * @author Ethan Ohayon
     */
    private double getVelocity() {
        return velocity;
    }

    /**
     * Sets the velocity of the Projectile
     *
     * @param velocity Velocity of the projectle
     *
     * @author Ethan Ohayon
     */
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    /**
     * Returns the X velocity of the Projectile
     *
     * @return velocityX
     * @author Ethan Ohayon
     */
    private double getVelocityX() {
        return velocityX;
    }

    /**
     * Returns the Y velocity of the Projectile
     *
     * @return velocityY
     * @author Ethan Ohayon
     */
    private double getVelocityY() {
        return velocityY;
    }

    /**
     * Returns the Y acceleration of the Projectile
     *
     * @return double accelerationY
     * @author Ethan Ohayon
     */
    private double getAccelerationY() {
        return accelerationY;
    }

    /**
     * Returns the mass of the Projectile
     *
     * @return double mass
     * @author Ethan Ohayon
     */
    private double getMass() {
        return mass;
    }

    /**
     * Returns the force applied by the Projectile
     *
     * @return double force
     * @author Ethan Ohayon
     */
    public double getForce(){
        //Calculates the force applied by the projectile on another object
        return (0.5 * this.getMass() * Math.pow(this.getVelocity(), 2));
    }
}
