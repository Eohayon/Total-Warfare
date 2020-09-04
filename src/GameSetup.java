import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * File Name: GameSetup.java
 * <p>
 * Setup screen for the game where users select their tank types and input their names
 *
 * @author  Jonah Belman
 * @version 1.0
 * @since   April 30, 2018
 */

class GameSetup {

    static boolean desertClicked = false;
    static boolean navyClicked = false;
    static boolean greyClicked = false;
    static boolean greenClicked = false;
    static boolean desertClicked2 = false;
    static boolean navyClicked2 = false;
    static boolean greyClicked2 = false;
    static boolean greenClicked2 = false;
    static Button homeButton = new Button();

    /**
     * Adds a popup for users to input their information
     *
     * @author Jonah Belman
     */
    static void gameSetup() {

        TextField p1Name = new TextField();
        TextField p2Name = new TextField();
        Button playButton = new Button();
        ImageView desertTank = new ImageView(new Image("/resources/Tank Desert.png"));
        ImageView navyTank   = new ImageView(new Image("/resources/Tank Navy.png"));
        ImageView greyTank   = new ImageView(new Image("/resources/Tank Grey.png"));
        ImageView greenTank  = new ImageView(new Image("/resources/Tank Green.png"));
        ImageView desertTank2 = new ImageView(new Image("/resources/Tank Desert.png"));
        ImageView navyTank2  = new ImageView(new Image("/resources/Tank Navy.png"));
        ImageView greyTank2   = new ImageView(new Image("/resources/Tank Grey.png"));
        ImageView greenTank2  = new ImageView(new Image("/resources/Tank Green.png"));
        ImageView namePrompt = new ImageView(new Image("/resources/PromptBG.png"));
        StackPane stackPane  = new StackPane(namePrompt, p1Name, p2Name, desertTank, navyTank, greyTank, greenTank, desertTank2, navyTank2, greyTank2, greenTank2, playButton);
        DropShadow dropShadow = new DropShadow();
        DropShadow dropShadow2 = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(-5.0);
        dropShadow.setOffsetY(5.0);
        dropShadow.setColor(Color.BLACK);

        dropShadow2.setRadius(5.0);
        dropShadow2.setOffsetX(-5.0);
        dropShadow2.setOffsetY(5.0);
        dropShadow2.setColor(Color.BLACK);

        p1Name.setMaxWidth(160);
        p2Name.setMaxWidth(160);
        p1Name.setTranslateX(56);
        p1Name.setTranslateY(-85);
        p2Name.setTranslateX(56);
        p2Name.setTranslateY(10);

        desertTank.setTranslateX(-112);
        navyTank.setTranslateX(-37);
        greyTank.setTranslateX(38);
        greenTank.setTranslateX(113);

        desertTank.setTranslateY(-40);
        navyTank.setTranslateY(-40);
        greyTank.setTranslateY(-40);
        greenTank.setTranslateY(-40);

        desertTank2.setTranslateX(113);
        navyTank2.setTranslateX(38);
        greyTank2.setTranslateX(-37);
        greenTank2.setTranslateX(-112);

        desertTank2.setTranslateY(55);
        navyTank2.setTranslateY(55);
        greyTank2.setTranslateY(55);
        greenTank2.setTranslateY(55);

        desertTank2.setScaleX(-1);
        navyTank2.setScaleX(-1);
        greyTank2.setScaleX(-1);
        greenTank2.setScaleX(-1);

        playButton.setScaleX(0.8);
        playButton.setScaleY(0.8);
        playButton.setTranslateY(180);
        playButton.setStyle("-fx-background-color: transparent;");
        playButton.graphicProperty().bind(Bindings.when(playButton.pressedProperty())
                .then(new ImageView(new Image("/resources/PlayButtonPress.png")))
                .otherwise(new ImageView(new Image("/resources/PlayButton.png"))));


        homeButton.setStyle("-fx-background-color: transparent;");
        homeButton.setTranslateX(220);
        homeButton.setTranslateY(5);
        homeButton.setGraphic(new ImageView(new Image("/resources/HomeButton.png")));

        stackPane.setTranslateX(438);
        stackPane.setTranslateY(75);
        Main.root.getChildren().remove(Menu.muteButton);
        Main.root.getChildren().addAll(stackPane, homeButton, Menu.muteButton);
        Menu.muteButton.setTranslateX(-305);

        desertTank.setOnMouseEntered(desertEnter -> desertTank.setEffect(dropShadow));
        desertTank.setOnMouseExited(desertExit -> {
            if (desertClicked) {
                desertTank.setEffect(dropShadow);
            } else {
                desertTank.setEffect(null);
            }
        });
        desertTank.setOnMouseClicked(desertClick -> {
            if (!desertClicked2) {
                desertTank.setEffect(dropShadow);
                navyTank.setEffect(null);
                greenTank.setEffect(null);
                greyTank.setEffect(null);
                desertClicked = true;
                navyClicked = false;
                greyClicked = false;
                greenClicked = false;
            }
        });

        navyTank.setOnMouseEntered(navyEnter -> navyTank.setEffect(dropShadow));
        navyTank.setOnMouseExited(navyExit -> {
            if (navyClicked) {
                navyTank.setEffect(dropShadow);
            } else {
                navyTank.setEffect(null);
            }
        });
        navyTank.setOnMouseClicked(navyClick -> {
            if (!navyClicked2) {
                navyTank.setEffect(dropShadow);
                desertTank.setEffect(null);
                greenTank.setEffect(null);
                greyTank.setEffect(null);
                navyClicked = true;
                desertClicked = false;
                greyClicked = false;
                greenClicked = false;
            }
        });

        greyTank.setOnMouseEntered(greyEnter -> greyTank.setEffect(dropShadow));
        greyTank.setOnMouseExited(greyExit -> {
            if (greyClicked) {
                greyTank.setEffect(dropShadow);
            } else {
                greyTank.setEffect(null);
            }
        });
        greyTank.setOnMouseClicked(greyClick -> {
            if (!greyClicked2) {
                greyTank.setEffect(dropShadow);
                desertTank.setEffect(null);
                greenTank.setEffect(null);
                navyTank.setEffect(null);
                greyClicked = true;
                desertClicked = false;
                navyClicked = false;
                greenClicked = false;
            }
        });

        greenTank.setOnMouseEntered(greenEnter -> greenTank.setEffect(dropShadow));
        greenTank.setOnMouseExited(greenExit -> {
            if (greenClicked) {
                greenTank.setEffect(dropShadow);
            } else {
                greenTank.setEffect(null);
            }
        });
        greenTank.setOnMouseClicked(greenClick -> {
            if (!greenClicked2) {
                greenTank.setEffect(dropShadow);
                desertTank.setEffect(null);
                greyTank.setEffect(null);
                navyTank.setEffect(null);
                greenClicked = true;
                desertClicked = false;
                navyClicked = false;
                greyClicked = false;
            }
        });

        desertTank2.setOnMouseEntered(desert2Enter -> desertTank2.setEffect(dropShadow2));
        desertTank2.setOnMouseExited(desert2Exit -> {

            if (desertClicked2) {
                desertTank2.setEffect(dropShadow2);
            } else {
                desertTank2.setEffect(null);
            }
        });
        desertTank2.setOnMouseClicked(desert2Click -> {
            if (!desertClicked) {
                desertTank2.setEffect(dropShadow2);
                navyTank2.setEffect(null);
                greenTank2.setEffect(null);
                greyTank2.setEffect(null);
                desertClicked2 = true;
                navyClicked2 = false;
                greyClicked2 = false;
                greenClicked2 = false;
            }
        });

        navyTank2.setOnMouseEntered(navy2Enter -> navyTank2.setEffect(dropShadow2));
        navyTank2.setOnMouseExited(navy2Exit -> {
            if (navyClicked2) {
                navyTank2.setEffect(dropShadow2);
            } else {
                navyTank2.setEffect(null);
            }
        });
        navyTank2.setOnMouseClicked(navy2Click -> {
            if (!navyClicked) {
                navyTank2.setEffect(dropShadow2);
                desertTank2.setEffect(null);
                greenTank2.setEffect(null);
                greyTank2.setEffect(null);
                navyClicked2 = true;
                desertClicked2 = false;
                greyClicked2 = false;
                greenClicked2 = false;
            }
        });

        greyTank2.setOnMouseEntered(grey2Enter -> greyTank2.setEffect(dropShadow2));
        greyTank2.setOnMouseExited(grey2Exit -> {
            if (greyClicked2) {
                greyTank2.setEffect(dropShadow2);
            } else {
                greyTank2.setEffect(null);
            }
        });
        greyTank2.setOnMouseClicked(grey2Click -> {
            if (!greyClicked) {
                greyTank2.setEffect(dropShadow2);
                desertTank2.setEffect(null);
                greenTank2.setEffect(null);
                navyTank2.setEffect(null);
                greyClicked2 = true;
                navyClicked2 = false;
                desertClicked2 = false;
                greenClicked2 = false;
            }
        });

        greenTank2.setOnMouseEntered(green2Enter -> greenTank2.setEffect(dropShadow2));
        greenTank2.setOnMouseExited(green2Exit -> {
            if (greenClicked2) {
                greenTank2.setEffect(dropShadow2);
            } else {
                greenTank2.setEffect(null);
            }
        });
        greenTank2.setOnMouseClicked(green2Click -> {
            if (!greenClicked) {
                greenTank2.setEffect(dropShadow2);
                desertTank2.setEffect(null);
                greyTank2.setEffect(null);
                navyTank2.setEffect(null);
                desertClicked2 = false;
                greenClicked2 = true;
                navyClicked2 = false;
                greyClicked2 = false;
            }
        });

        homeButton.setOnAction(homeClicked -> {
            Main.root.getChildren().clear();
            Menu.CLICKABLE = true;
            GameSetup.desertClicked = false;
            GameSetup.navyClicked = false;
            GameSetup.greyClicked = false;
            GameSetup.greenClicked = false;
            GameSetup.desertClicked2 = false;
            GameSetup.navyClicked2 = false;
            GameSetup.greyClicked2 = false;
            GameSetup.greenClicked2 = false;
            Main.healthBar.setProgress(1);
            Main.healthBar2.setProgress(1);
            Main.scoreText.setText("0 | 0");
            Main.player = Enums.Player.ONE;
            Main.player1Wins = 0;
            Main.player2Wins = 0;
            Main.player1Shots = 0;
            Main.player2Shots = 0;
            Main.IN_GAME = false;
            Main.SHOOT = false;
            Main.round = 1;
            Menu.menu();

            if (Main.MUTED) {
                Menu.muteButton.setGraphic(new ImageView(new Image("/resources/SoundOff.png")));

            } else {
                Menu.muteButton.setGraphic(new ImageView(new Image("/resources/SoundOn.png")));
            }
        });

        playButton.setOnAction(action -> {

            //If all required data has been entered the game is started

            if (!p1Name.getText().equals("") && !p2Name.getText().equals("") && (desertClicked || navyClicked || greyClicked || greenClicked) && (desertClicked2 || navyClicked2 || greyClicked2 || greenClicked2)) {

                Main.IN_GAME = true;
                Main.record.setP1Name(p1Name.getText());
                Main.record.setP2Name(p2Name.getText());
                Main.root.getChildren().remove(homeButton);

                if (desertClicked) {
                    Main.record.setP1Type(Enums.Tank.DESERT);
                } else if (navyClicked) {
                    Main.record.setP1Type(Enums.Tank.NAVY);
                }  else if (greyClicked) {
                    Main.record.setP1Type(Enums.Tank.GREY);
                }   else {
                    Main.record.setP1Type(Enums.Tank.GREEN);
                }
                if (desertClicked2) {
                    Main.record.setP2Type(Enums.Tank.DESERT);
                } else if (navyClicked2) {
                    Main.record.setP2Type(Enums.Tank.NAVY);
                }  else if (greyClicked2) {
                    Main.record.setP2Type(Enums.Tank.GREY);
                }   else {
                    Main.record.setP2Type(Enums.Tank.GREEN);
                }
                Main.round = 1;
                Main.initializeGame();
            }
        });
    }
}
