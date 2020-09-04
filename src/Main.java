import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.*;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * File Name: Main.java
 * <p>
 * Program Name: Total Warfare
 * <p>
 * Opens the game window, initializes the game and ends the game when necessary
 *
 * @author  Ethan Ohayon
 * @version 1.0
 * @since   February 28, 2018
 */
public class Main extends Application {

    static  final int width = 1250;
    static  final int height = 500;
    static  final Group root = new Group();
    static  int round = 1;
    private static Tank tank = new Tank();
    private static Tank tank2 = new Tank();
    private static Scene scene;
    static  int player1Wins = 0;
    static  int player2Wins = 0;
    static  int player1Shots = 0;
    static  int player2Shots = 0;
    static  int count = 0;
    static  boolean IN_GAME = true;
    static  boolean MUTED = true;
    static  boolean SHOOT = true;
    static  Projectile rocket = new Projectile(40, 445, 9.8, 25, Enums.Rocket.TRACTOR);
    static  Projectile rocket2 = new Projectile(990, 445, 9.8, 25, Enums.Rocket.CHAIM);
    private static double lineLength = 0;
    private static double launchAngle = 0;
    private static double initialVelocity;
    private static final int health = 400000;
    static  final  Record record = new Record();
    private static final double MAX_VELOCITY = 100;
    private static final Text winText = new Text();
    static  final Line powerLine = new Line();
    static  Enums.Player player = Enums.Player.ONE;
    static  SortedLinkedList<Record> records = new SortedLinkedList<>();
    private static ArrayList<Barrel> barrels = new ArrayList<>();
    static  final  ProgressBar healthBar = new ProgressBar(1);
    static  final  ProgressBar healthBar2 = new ProgressBar(1);
    static  final  ProgressBar fuelBar  = new ProgressBar(1);
    static  final  ProgressBar fuelBar2 = new ProgressBar(1);
    static  final  Text scoreText = new Text(562, 50, "0 | 0");
    static  final  DoubleProperty fuelLevel = new SimpleDoubleProperty(100);
    private static final Rectangle playerRect = new Rectangle(220, 65, Color.TRANSPARENT);
    static  final  MediaPlayer themeSound = new MediaPlayer(new Media(Main.class.getResource("/resources/Theme.mp3").toString()));
    private static MediaPlayer explosionSound = new MediaPlayer(new Media(Main.class.getResource("/resources/Explosion.mp3").toString()));

    /**
     * Opens the games window, starts playing the background music and calls the menu() method.
     *
     * @param primaryStage Game window
     * @author Ethan Ohayon
     */
    @Override
    public void start(Stage primaryStage) {
        
        scene = new Scene(root, width, height);

        primaryStage.setTitle("Total Warfare");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        root.setFocusTraversable(true);
        root.requestFocus();
        root.getStylesheets().add("/stylesheet.css");

        themeSound.play();
        themeSound.setCycleCount(-1); //-1 results in infinite cycle count

        Menu.menu();
    }

    /**
     * Reads scores from the scores file to initialize the records.
     * <p>
     * Initializes all elements of the game so that the game can be properly played.
     * <p>
     * Adds mouse and key listeners for player input.
     *
     * @author Ethan Ohayon
     */
    static void initializeGame() {

        root.requestFocus();
        tank = new Tank();
        tank2 = new Tank();
        fuelLevel.set(100);

        File file = new File("scores.tws");

        //Reads scores if the file has any written

        if (file.length() != 0 && file.exists()) {
            try {
                records = Score.read();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        //Sets the colour of the tanks as the users picked

        if (record.getP1Type() == Enums.Tank.DESERT) {
            tank.setType(Enums.Tank.DESERT);
        } else if (record.getP1Type() == Enums.Tank.NAVY) {
            tank.setType(Enums.Tank.NAVY);
        } else if (record.getP1Type() == Enums.Tank.GREY) {
            tank.setType(Enums.Tank.GREY);
        } else {
            tank.setType(Enums.Tank.GREEN);
        }
        if (record.getP2Type() == Enums.Tank.DESERT) {
            tank2.setType(Enums.Tank.DESERT);
        } else if (record.getP2Type() == Enums.Tank.NAVY) {
            tank2.setType(Enums.Tank.NAVY);
        } else if (record.getP2Type() == Enums.Tank.GREY) {
            tank2.setType(Enums.Tank.GREY);
        } else {
            tank2.setType(Enums.Tank.GREEN);
        }

        int maxMid1 = 492;
        int maxMid2 = 759;
        player = Enums.Player.ONE;
        Rectangle background = new Rectangle(width, height);
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop(0, Color.YELLOW), new Stop(1, Color.RED));
        LinearGradient gradient2 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop(0, Color.RED), new Stop(1, Color.YELLOW));
        Text fuelText = new Text(10, 45, "Player 1 Fuel");
        Text fuelText2 = new Text(1040, 45, "Player 2 Fuel");
        Text healthText = new Text(10, 17, "Player 1 Health");
        Text healthText2 = new Text(1040, 17, "Player 2 Health");

        scoreText.setFont(Font.loadFont(Main.class.getResourceAsStream("/resources/Army Font.otf"), 60));
        playerRect.setStroke(Color.RED);
        playerRect.setStrokeWidth(3);
        playerRect.relocate(-5, -7);

        fuelBar.setTranslateX(110);
        fuelBar.setTranslateY(32);
        fuelBar.setProgress(1);

        fuelBar2.setTranslateX(1140);
        fuelBar2.setTranslateY(32);
        fuelBar2.setProgress(1);

        healthBar.setTranslateX(110);
        healthBar.setTranslateY(4);
        healthBar.setProgress(1);

        healthBar2.setTranslateX(1140);
        healthBar2.setTranslateY(4);
        healthBar2.setProgress(1);

        tank.setTranslateY(450);
        tank2.setTranslateY(450);
        tank2.setTranslateX(1185);
        tank2.setScaleX(-1);
        tank2.setHealth(health);
        tank.setHealth(health);
        rocket2.setScaleX(-1);
        background.setFill(new ImagePattern(new Image("/resources/Background.png")));
        powerLine.setStrokeWidth(3);
        powerLine.setStrokeLineCap(StrokeLineCap.ROUND);
        powerLine.getStrokeDashArray().addAll(5d, 18d, 5d, 15d, 5d, 15d, 5d, 15d, 5d,
                15d, 5d, 15d, 5d, 15d, 5d, 15d, 5d, 15d,
                5d, 15d, 5d, 15d, 5d, 15d, 5d, 15d, 5d,
                15d, 5d, 15d, 5d, 15d, 5d, 15d, 5d, 1000d);

        root.getChildren().remove(Menu.muteButton);
        root.getChildren().addAll(background, powerLine, tank, tank2, fuelBar, fuelBar2, fuelText2, fuelText, healthBar, healthText, healthBar2, healthText2, scoreText, winText, playerRect, GameSetup.homeButton, Menu.muteButton);

        //Adds and creates all the barrels for the first round

        barrels.clear();
        barrels.add(new Barrel(Enums.Barrel.GREY, 492, 453));
        barrels.add(new Barrel(Enums.Barrel.GREEN, 530, 453));
        barrels.add(new Barrel(Enums.Barrel.RED, 568, 453));
        barrels.add(new Barrel(Enums.Barrel.GREY, 606, 453));
        barrels.add(new Barrel(Enums.Barrel.RED, 644, 453));
        barrels.add(new Barrel(Enums.Barrel.GREEN, 682, 453));
        barrels.add(new Barrel(Enums.Barrel.GREY, 720, 453));
        barrels.add(new Barrel(Enums.Barrel.RED, 530, 406));
        barrels.add(new Barrel(Enums.Barrel.GREY, 568, 406));
        barrels.add(new Barrel(Enums.Barrel.GREEN, 606, 406));
        barrels.add(new Barrel(Enums.Barrel.GREY, 644, 406));
        barrels.add(new Barrel(Enums.Barrel.RED, 682, 406));
        barrels.add(new Barrel(Enums.Barrel.GREEN, 568, 359));
        barrels.add(new Barrel(Enums.Barrel.GREY, 606, 359));
        barrels.add(new Barrel(Enums.Barrel.GREEN, 644, 359));
        barrels.add(new Barrel(Enums.Barrel.RED, 606, 312));

        IntStream.range(0, 16).forEach(i -> root.getChildren().add(barrels.get(i)));

        root.getChildren().addAll(rocket, rocket2);

        scene.setOnKeyPressed(keyPressed -> {

            if (IN_GAME && !rocket.isInMotion() && !rocket2.isInMotion()) { //Only allows driving if the game is being played


                if (keyPressed.getCode() == KeyCode.RIGHT || keyPressed.getCode() == KeyCode.D) {

                    if (player == Enums.Player.ONE && tank.getTranslateX() + tank.getWidth() <= maxMid1 && fuelLevel.get() > 0) {
                        tank.drive(Enums.Direction.RIGHT, Enums.Player.ONE);
                    } else if (player == Enums.Player.TWO && tank2.getTranslateX() + tank2.getWidth() <= width - 5 && fuelLevel.get() > 0) {
                        tank2.drive(Enums.Direction.RIGHT, Enums.Player.TWO);
                    }
                }

                if (keyPressed.getCode() == KeyCode.LEFT || keyPressed.getCode() == KeyCode.A) {

                    if (player == Enums.Player.ONE && tank.getTranslateX() > 0 && fuelLevel.get() > 0) {
                        tank.drive(Enums.Direction.LEFT, Enums.Player.ONE);
                    } else if (player == Enums.Player.TWO && tank2.getTranslateX() >= maxMid2 && fuelLevel.get() > 0) {
                        tank2.drive(Enums.Direction.LEFT, Enums.Player.TWO);
                    }
                }
            }
        });

        scene.setOnKeyReleased(keyReleased -> {

            if (IN_GAME) {

                if (keyReleased.getCode() == KeyCode.RIGHT || keyReleased.getCode() == KeyCode.LEFT || keyReleased.getCode() == KeyCode.A || keyReleased.getCode() == KeyCode.D) {
                    tank.brake();
                    tank2.brake();
                }
            }
        });

        scene.setOnMouseMoved(move -> {

            double mouseX = move.getX();
            double mouseY = move.getY();

            if (player == Enums.Player.ONE) {

                powerLine.setStroke(gradient);
                powerLine.setStartX(tank.getTranslateX() + 33);
                powerLine.setStartY(tank.getTranslateY() + 3);

                lineLength = Math.sqrt(Math.pow((mouseX - (tank.getTranslateX() - 45)), 2) + Math.pow((mouseY - (height - 55)), 2)); //Finds length of the line
                launchAngle = Math.toDegrees(Math.atan((height - (rocket.getTranslateY() + 47) - mouseY) / (mouseX - tank.getTranslateX() - 35))); //Finds the launch angle

                if (lineLength >= 350) { //Limits the initial velocity of the projectile
                    lineLength = 350;
                }
                if (mouseX - tank.getTranslateX() > 45 && mouseX > powerLine.getStartX()) {
                    powerLine.setEndX(mouseX);
                    powerLine.setEndY(mouseY);
                    tank.rotate(launchAngle);
                }
                if (launchAngle >= -90 && launchAngle <= -60 || mouseX < powerLine.getStartX()) {
                    launchAngle = 89;
                }
                initialVelocity = (lineLength / 350) * MAX_VELOCITY; //Calculates velocity based off of line length
            }

            if (player == Enums.Player.TWO) {

                powerLine.setStroke(gradient2);
                powerLine.setStartX(width - (1218 - tank2.getTranslateX()));
                powerLine.setStartY(tank2.getTranslateY() + 6);

                lineLength = Math.sqrt(Math.pow(mouseX - (tank2.getTranslateX() + 100), 2) + Math.pow((mouseY - (height - 55)), 2)); //Finds length of the line
                launchAngle = 360 - Math.toDegrees(Math.atan((height - (rocket2.getTranslateY() + 47) - mouseY) / (mouseX - tank2.getTranslateX() - 35))); //Finds the launch angle

                if (lineLength >= 350) { //Limits the initial velocity of the projectile
                    lineLength = 350;

                }
                if (mouseX + tank2.getTranslateX() > 985 && mouseX < powerLine.getStartX()) {
                    powerLine.setEndX(mouseX);
                    powerLine.setEndY(mouseY);
                    tank2.rotate(launchAngle);

                }
                if (launchAngle >= -90 && launchAngle <= -80 || mouseX > powerLine.getStartX()) {
                    launchAngle = 89;
                }
                initialVelocity = (lineLength / 350) * MAX_VELOCITY; //Calculates velocity based off of line length
            }
        });

        scene.setOnMouseClicked(click -> {

            if (!rocket.isInMotion() && !rocket2.isInMotion() && IN_GAME && SHOOT) { //Checks that neither rockets are in motion

                if (!MUTED) {
                    MediaPlayer mediaPlayer = new MediaPlayer(new Media(Main.class.getResource("/resources/Shoot.mp3").toString()));
                    mediaPlayer.play();
                }

                //Resets the rocket and launches it with the calculated velocity

                if (player == Enums.Player.ONE) {
                    rocket.reset();
                    rocket.setVelocity(initialVelocity);
                    rocket.setTheta(270 + launchAngle);
                    rocket.setInitialX(tank.getTranslateX() + 40);
                    rocket.setCenterX(tank.getTranslateX() + 40);
                    rocket.setCenterY(450);
                    rocket.animate();
                    player1Shots++;
                }

                if (player == Enums.Player.TWO) {
                    rocket2.reset();
                    rocket2.setVelocity(initialVelocity);
                    rocket2.setTheta(90 - launchAngle);
                    rocket2.setInitialX(tank2.getTranslateX() + 40);
                    rocket2.setCenterX(tank2.getTranslateX() + 40);
                    rocket2.setCenterY(450);
                    rocket2.animate();
                    player2Shots++;
                }
            }
        });
    }

    /**
     * To be called in a timeline in order to update all of the games properties.
     * <p>
     * Checks collisions between rockets and all other objects and appropriately deals with them
     *
     * @author Ethan Ohayon
     */
    static void updateGame() {

        IN_GAME = true;
        root.requestFocus();

        if (player == Enums.Player.ONE) {

            //Translates rocket to new point

            Point2D point = rocket.getNewPoint();
            Point2D vertex = rocket.getVertex();
            rocket.setCenterX(point.getX());
            rocket.setCenterY(point.getY());

            double rocketAngle = Math.toDegrees(Math.atan((rocket.getCenterY() - vertex.getY()) / (rocket.getCenterX() - vertex.getX()))); //Finds angle from current coordinates to vertex
            rocket.setRotate(rocketAngle); //Sets the angle of the rocket

            if (rocket.getCenterY() + rocket.getRadiusY() >= height) { //Ground collision check
                rocket.explode();
                rocket.reset();
                rocket.setCenterY(rocket.getCenterY() - 18);
                player = Enums.Player.TWO;
                fuelLevel.set(100);
                fuelBar.setProgress(1);
                playerRect.relocate(1030, -7);
                count++;

                if (!MUTED) {
                    explosionSound = new MediaPlayer(new Media(Main.class.getResource("/resources/Explosion.mp3").toString()));
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(explosionSound.volumeProperty(), 0)));
                    explosionSound.play();
                    timeline.play();
                }
                moveMouse(1, 0);

                if (count == 1) {
                    MUTED = false;
                }
            }

            for (Barrel barrel : barrels) {
                if (rocket.getBoundsInParent().intersects(barrel.getBoundsInParent())) { //Barrel collision check
                    rocket.explode();
                    rocket.reset();
                    player = Enums.Player.TWO;
                    fuelLevel.set(100);
                    fuelBar.setProgress(1);
                    playerRect.relocate(1030, -7);

                    if (!MUTED) {
                        explosionSound = new MediaPlayer(new Media(Main.class.getResource("/resources/Explosion.mp3").toString()));
                        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(explosionSound.volumeProperty(), 0)));
                        explosionSound.play();
                        timeline.play();
                    }
                    moveMouse(1, 0);
                }
            }

            if (rocket.getBoundsInParent().intersects(tank2.getBoundsInParent())) { //Tank collision check
                rocket.explode();
                rocket.reset();
                player = Enums.Player.TWO;
                fuelLevel.set(100);
                fuelBar.setProgress(1);
                tank2.setHealth(tank2.getHealth() - rocket.getForce());
                healthBar2.setProgress(tank2.getHealth()/health);
                playerRect.relocate(1030, -7);

                if (!MUTED) {
                    explosionSound = new MediaPlayer(new Media(Main.class.getResource("/resources/Explosion.mp3").toString()));
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(explosionSound.volumeProperty(), 0)));
                    explosionSound.play();
                    timeline.play();
                }
                moveMouse(1, 0);

                if (tank2.getHealth() <= 50000) {
                    endGame(Enums.Player.ONE);
                    healthBar2.setProgress(0);
                }
            }
        }

        if (player == Enums.Player.TWO) {

            //Translates rocket to new point

            Point2D point = rocket2.getNewPoint();
            Point2D vertex = rocket2.getVertex();
            rocket2.setCenterX(point.getX());
            rocket2.setCenterY(point.getY());

            double rocketAngle = Math.toDegrees(Math.atan((rocket2.getCenterY() - vertex.getY()) / (rocket2.getCenterX() - vertex.getX()))); //Finds angle from current coordinates to vertex
            rocket2.setRotate(rocketAngle); //Sets the angle of the rocket

            if (rocket2.getCenterY() + rocket2.getRadiusY() >= height) { //Ground collision check
                rocket2.explode();
                rocket2.reset();
                rocket2.setCenterY(rocket2.getCenterY() - 18);
                player = Enums.Player.ONE;
                fuelLevel.set(100);
                fuelBar2.setProgress(1);
                playerRect.relocate(-5, -7);

                if (!MUTED) {
                    explosionSound = new MediaPlayer(new Media(Main.class.getResource("/resources/Explosion.mp3").toString()));
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(explosionSound.volumeProperty(), 0)));
                    explosionSound.play();
                    timeline.play();
                }
                moveMouse(-1, 0);
            }

            for (Barrel barrel : barrels) {
                if (rocket2.getBoundsInParent().intersects(barrel.getBoundsInParent())) { //Barrel collision check
                    rocket2.explode();
                    rocket2.reset();
                    player = Enums.Player.ONE;
                    fuelLevel.set(100);
                    fuelBar2.setProgress(1);
                    playerRect.relocate(-5, -7);

                    if (!MUTED) {
                        explosionSound = new MediaPlayer(new Media(Main.class.getResource("/resources/Explosion.mp3").toString()));
                        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(explosionSound.volumeProperty(), 0)));
                        explosionSound.play();
                        timeline.play();
                    }
                    moveMouse(-1, 0);
                }
            }

            if (rocket2.getBoundsInParent().intersects(tank.getBoundsInParent())) { //Tank collision check
                rocket2.explode();
                rocket2.reset();
                player = Enums.Player.ONE;
                fuelLevel.set(100);
                fuelBar2.setProgress(1);
                tank.setHealth(tank.getHealth() - rocket2.getForce());
                healthBar.setProgress(tank.getHealth()/health);
                playerRect.relocate(-5, -7);

                if (!MUTED) {
                    explosionSound = new MediaPlayer(new Media(Main.class.getResource("/resources/Explosion.mp3").toString()));
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(explosionSound.volumeProperty(), 0)));
                    explosionSound.play();
                    timeline.play();
                }
                moveMouse(-1, 0);

                if (tank.getHealth() <= 50000) {
                    endGame(Enums.Player.TWO);
                    healthBar.setProgress(0);
                }
            }
        }
    }

    /**
     * To be called at the end of each round to either transition to the next or show the final scores.
     * <p>
     * If the game has been won, the scores are written to the file.
     *
     * @param winner Specifies if player 1 or player 2 won
     * @author Ethan Ohayon
     */
    private static void endGame(Enums.Player winner) {

        IN_GAME = false;
        SHOOT = false;

        if (winner == Enums.Player.ONE) {
            player1Wins++;
            winText.setText("PLAYER " + 1 + " WINS!");
        } else {
            player2Wins++;
            winText.setText("PLAYER " + 2 + " WINS!");
        }
        if (player1Wins == 2 || player2Wins == 2) { //Checks if either player has won

            Text text = new Text();
            Rectangle rect = new Rectangle(width, height);
            StackPane stackPane = new StackPane(rect, text);
            stackPane.setTranslateX(-width);
            text.setFont(Font.loadFont(Main.class.getResourceAsStream("/resources/Army Font.otf"), 80));
            record.setP1Shots(player1Shots);
            record.setP2Shots(player2Shots);
            record.setP1Wins(player1Wins);
            record.setP2Wins(player2Wins);
            records.add(record);

            if (player1Wins == 2) {

                //Sets the background as player one's colour and the text as player two's

                record.setWinner(Enums.Player.ONE);
                text.setText("PLAYER " + 1 + " WINS!");

                if (tank.getType() == Enums.Tank.DESERT) {
                    rect.setFill(Color.web("#E1D4BA"));
                } else if (tank.getType() == Enums.Tank.NAVY) {
                    rect.setFill(Color.web("#2E4154"));
                }  else if (tank.getType() == Enums.Tank.GREEN) {
                    rect.setFill(Color.web("#2DCB71"));
                } else {
                    rect.setFill(Color.web("#859495"));
                }
                if (tank2.getType() == Enums.Tank.DESERT) {
                    text.setFill(Color.web("#E1D4BA"));
                } else if (tank2.getType() == Enums.Tank.NAVY) {
                    text.setFill(Color.web("#2E4154"));
                }  else if (tank2.getType() == Enums.Tank.GREEN) {
                    text.setFill(Color.web("#2DCB71"));
                } else {
                    text.setFill(Color.web("#859495"));
                }

            } else {

                //Sets the background as player two's colour and the text as player one's

                record.setWinner(Enums.Player.TWO);
                text.setText("PLAYER " + 2 + " WINS!");

                if (tank2.getType() == Enums.Tank.DESERT) {
                    rect.setFill(Color.web("#E1D4BA"));
                } else if (tank2.getType() == Enums.Tank.NAVY) {
                    rect.setFill(Color.web("#2E4154"));
                }  else if (tank2.getType() == Enums.Tank.GREEN) {
                    rect.setFill(Color.web("#2DCB71"));
                } else {
                    rect.setFill(Color.web("#859495"));
                }
                if (tank.getType() == Enums.Tank.DESERT) {
                    text.setFill(Color.web("#E1D4BA"));
                } else if (tank.getType() == Enums.Tank.NAVY) {
                    text.setFill(Color.web("#2E4154"));
                }  else if (tank.getType() == Enums.Tank.GREEN) {
                    text.setFill(Color.web("#2DCB71"));
                } else {
                    text.setFill(Color.web("#859495"));
                }
            }

            try {
                Score.write(records);
            } catch (IOException e) {
                e.printStackTrace();
            }
            root.getChildren().add(stackPane);

            TranslateTransition tt = new TranslateTransition(Duration.seconds(1.5), stackPane);
            tt.setByX(width);
            tt.play();

            tt.setOnFinished(finished -> {

                TranslateTransition tt2 = new TranslateTransition(Duration.seconds(1), text);
                tt2.setByY(-200);
                tt2.play();

                tt2.setOnFinished(finished3 -> {

                    //Displays the finals scores of the game

                    Text p1ScoreText = new Text("Player 1:");
                    Text p2ScoreText = new Text("Player 2:");
                    Button menuButton = new Button();
                    Font font = Font.loadFont(Main.class.getResourceAsStream("/resources/Army Font.otf"), 40);
                    BorderPane borderPane = new BorderPane(menuButton, null, p2ScoreText, null, p1ScoreText);

                    menuButton.setScaleX(0.8);
                    menuButton.setScaleY(0.8);
                    menuButton.setStyle("-fx-background-color: transparent;");
                    menuButton.graphicProperty().bind(Bindings.when(menuButton.pressedProperty())
                            .then(new ImageView(new Image("/resources/MenuButtonPress.png")))
                            .otherwise(new ImageView(new Image("/resources/MenuButton.png"))));

                    p1ScoreText.setText("PLAYER 1:\n\n" + "Name: " + record.getP1Name() + "\nShots: " + record.getP1Shots() + "\nWins: " + record.getP1Wins() + "/3");
                    p1ScoreText.setFont(font);
                    p1ScoreText.setTextAlignment(TextAlignment.CENTER);
                    p1ScoreText.setWrappingWidth(300);
                    p1ScoreText.setTranslateY(150);

                    p2ScoreText.setText("PLAYER 2:\n\n" + "Name: " + record.getP2Name() + "\nShots: " + record.getP2Shots() + "\nWins: " + record.getP2Wins() + "/3");
                    p2ScoreText.setFont(font);
                    p2ScoreText.setTextAlignment(TextAlignment.CENTER);
                    p2ScoreText.setWrappingWidth(300);
                    p2ScoreText.setTranslateY(150);

                    borderPane.setOpacity(0);
                    borderPane.setMinSize(width, height);
                    borderPane.setPadding(new Insets(0, 100, 0, 100));
                    root.getChildren().add(borderPane);

                    FadeTransition ft2 = new FadeTransition(Duration.seconds(1.5), borderPane);
                    ft2.setFromValue(0);
                    ft2.setToValue(1);
                    ft2.play();

                    menuButton.setOnAction(menuClicked -> {

                        //Resets values and returns to the menu

                        root.getChildren().clear();
                        Menu.CLICKABLE = true;
                        GameSetup.desertClicked = false;
                        GameSetup.navyClicked = false;
                        GameSetup.greyClicked = false;
                        GameSetup.greenClicked = false;
                        GameSetup.desertClicked2 = false;
                        GameSetup.navyClicked2 = false;
                        GameSetup.greyClicked2 = false;
                        GameSetup.greenClicked2 = false;
                        scoreText.setText("0 | 0");
                        player = Enums.Player.ONE;
                        player1Wins = 0;
                        player2Wins = 0;
                        player1Shots = 0;
                        player2Shots = 0;
                        round = 1;
                        Menu.menu();
                    });
                });
            });

        } else { //Reached if the game isn't over yet

            round++;
            scoreText.setText(player1Wins + " | " + player2Wins);
            winText.setFont(Font.loadFont(Main.class.getResourceAsStream("/resources/Army Font.otf"), 80));
            winText.setFill(Color.BLACK);
            winText.setOpacity(0);
            winText.setTranslateX(310);
            winText.setTranslateY(150);

            Text roundText = new Text("ROUND " + round);
            StackPane stackPane = new StackPane(new Rectangle(width, height, Color.BLACK), roundText);
            roundText.setFont(Font.loadFont(Main.class.getResourceAsStream("/resources/Army Font.otf"), 80));
            roundText.setFill(Color.WHITE);
            stackPane.setTranslateX(-width);
            root.getChildren().add(stackPane);

            FadeTransition ft = new FadeTransition(Duration.seconds(3), winText);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();

            ft.setOnFinished(finished -> {

                winText.setOpacity(0);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(1.5), stackPane);
                tt.setByX(width);
                tt.play();

                TranslateTransition tt2 = new TranslateTransition(Duration.seconds(1.5), stackPane);
                tt2.setByX(width);

                tt.setOnFinished(finished2 -> {

                    //Changes the layout of the barrels depending on what the round is

                    if (round == 2) {
                        IntStream.range(0, 16).forEach(i -> root.getChildren().remove(barrels.get(i)));
                        root.getChildren().remove(stackPane);

                        barrels.clear();
                        barrels.add(new Barrel(Enums.Barrel.GREY, 492, 453));
                        barrels.add(new Barrel(Enums.Barrel.GREEN, 492, 406));
                        barrels.add(new Barrel(Enums.Barrel.RED, 492, 359));
                        barrels.add(new Barrel(Enums.Barrel.RED, 568, 453));
                        barrels.add(new Barrel(Enums.Barrel.GREY, 568, 406));
                        barrels.add(new Barrel(Enums.Barrel.GREEN, 568, 359));
                        barrels.add(new Barrel(Enums.Barrel.RED, 568, 312));
                        barrels.add(new Barrel(Enums.Barrel.GREY, 568, 265));
                        barrels.add(new Barrel(Enums.Barrel.GREEN, 644, 453));
                        barrels.add(new Barrel(Enums.Barrel.RED, 644, 406));
                        barrels.add(new Barrel(Enums.Barrel.GREY, 644, 359));
                        barrels.add(new Barrel(Enums.Barrel.GREEN, 644, 312));
                        barrels.add(new Barrel(Enums.Barrel.RED, 644, 265));
                        barrels.add(new Barrel(Enums.Barrel.RED, 720, 453));
                        barrels.add(new Barrel(Enums.Barrel.GREEN, 720, 406));
                        barrels.add(new Barrel(Enums.Barrel.GREY, 720, 359));

                        IntStream.range(0, 16).forEach(i -> root.getChildren().add(barrels.get(i)));
                        root.getChildren().add(stackPane);

                    } else if (round == 3) {

                        IntStream.range(0, 16).forEach(i -> root.getChildren().remove(barrels.get(i)));
                        root.getChildren().removeAll(stackPane, scoreText);

                        barrels.clear();
                        barrels.add(new Barrel(Enums.Barrel.RED, 492, 0));
                        barrels.add(new Barrel(Enums.Barrel.GREY, 530, 0));
                        barrels.add(new Barrel(Enums.Barrel.GREEN, 568, 0));
                        barrels.add(new Barrel(Enums.Barrel.RED, 606, 0));
                        barrels.add(new Barrel(Enums.Barrel.GREEN, 644, 0));
                        barrels.add(new Barrel(Enums.Barrel.GREY, 682, 0));
                        barrels.add(new Barrel(Enums.Barrel.RED, 720, 0));
                        barrels.add(new Barrel(Enums.Barrel.RED, 530, 47));
                        barrels.add(new Barrel(Enums.Barrel.GREEN, 568, 47));
                        barrels.add(new Barrel(Enums.Barrel.GREY, 606, 47));
                        barrels.add(new Barrel(Enums.Barrel.GREEN, 644, 47));
                        barrels.add(new Barrel(Enums.Barrel.RED, 682, 47));
                        barrels.add(new Barrel(Enums.Barrel.GREY, 568, 94));
                        barrels.add(new Barrel(Enums.Barrel.RED, 606, 94));
                        barrels.add(new Barrel(Enums.Barrel.GREY, 644, 94));
                        barrels.add(new Barrel(Enums.Barrel.GREEN, 606, 141));
                        barrels.add(new Barrel(Enums.Barrel.GREY, 492, 453));
                        barrels.add(new Barrel(Enums.Barrel.GREEN, 530, 453));
                        barrels.add(new Barrel(Enums.Barrel.RED, 568, 453));
                        barrels.add(new Barrel(Enums.Barrel.GREY, 606, 453));
                        barrels.add(new Barrel(Enums.Barrel.RED, 644, 453));
                        barrels.add(new Barrel(Enums.Barrel.GREEN, 682, 453));
                        barrels.add(new Barrel(Enums.Barrel.GREY, 720, 453));
                        barrels.add(new Barrel(Enums.Barrel.RED, 530, 406));
                        barrels.add(new Barrel(Enums.Barrel.GREY, 568, 406));
                        barrels.add(new Barrel(Enums.Barrel.GREEN, 606, 406));
                        barrels.add(new Barrel(Enums.Barrel.GREY, 644, 406));
                        barrels.add(new Barrel(Enums.Barrel.RED, 682, 406));
                        barrels.add(new Barrel(Enums.Barrel.GREEN, 568, 359));
                        barrels.add(new Barrel(Enums.Barrel.GREY, 606, 359));
                        barrels.add(new Barrel(Enums.Barrel.GREEN, 644, 359));
                        barrels.add(new Barrel(Enums.Barrel.RED, 606, 312));

                        IntStream.range(0, 32).forEach(i -> root.getChildren().add(barrels.get(i)));
                        root.getChildren().addAll(stackPane, scoreText);
                    }
                    tt2.play();
                });
                tt2.setOnFinished(finished3 -> {

                    //Starts the next round

                    moveMouse(1, 0);
                    tank.setHealth(health);
                    tank.setTranslateX(0);
                    tank2.setHealth(health);
                    tank2.setTranslateX(1185);
                    fuelLevel.set(100);
                    healthBar.setProgress(1);
                    healthBar2.setProgress(1);
                    IN_GAME = true;
                    SHOOT = true;
                });
            });
        }
    }

    /**
     * Moves the mouse by a given amount of pixels.
     * <p>
     * Used to fire the mouse listener when the turn has switched.
     *
     * @param xOffset Number of pixels to move on the X axis
     * @param yOffset Number of pixels to move on the Y axis
     * @author Ethan Ohayon
     */
    private static void moveMouse(int xOffset, int yOffset) {

        int mouseX = (int) MouseInfo.getPointerInfo().getLocation().getX();
        int mouseY = (int) MouseInfo.getPointerInfo().getLocation().getY();

        try {
            new Robot().mouseMove(mouseX + xOffset, mouseY + yOffset);
        } catch (AWTException ignored) {
        }

    }

    /**
     * Launches the program.
     *
     * @param args The command line arguments
     * @author Ethan Ohayon
     */
    public static void main(String[] args) {
        launch(args);
    }
}
