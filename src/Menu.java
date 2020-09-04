import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;

/**
 * File Name: Menu.java
 * <p>
 * Main menu for the game containing all game options
 *
 * @author  Mason Silver
 * @version 1.0
 * @since   April 24, 2018
 */
public class Menu {

    private static int counter = 0;
    private static ImageView currentPane;
    static boolean CLICKABLE = true;
    static Button muteButton = new Button();

    /**
     * Adds all of the menu buttons and their respective listeners.
     * <p>
     * Contains the functions of the How To and the Information button
     *
     * @author Mason Silver
     */
    static void menu() {

        Main.SHOOT = false;
        ImageView bg = new ImageView(new Image("/resources/MenuBG.png"));
        ImageView bg2 = new ImageView(new Image("/resources/GameBG.png"));
        Text playButton = new Text("- PLAY -");
        Text howToButton = new Text("- HOW TO -");
        Text recordsButton = new Text("- RECORDS -");
        Text infoButton = new Text("- INFORMATION -");
        Text playButton2 = new Text("- PLAY -");
        Text howToButton2 = new Text("- HOW TO -");
        Text recordsButton2 = new Text("- RECORDS -");
        Text infoButton2 = new Text("- INFORMATION -");
        Font font = Font.loadFont(Main.class.getResourceAsStream("/resources/Army Font.otf"), 60);
        StackPane stackPane = new StackPane(bg, playButton2, recordsButton2, howToButton2, infoButton2, playButton, recordsButton, howToButton, infoButton, muteButton);

        muteButton.setStyle("-fx-background-color: transparent;");
        muteButton.setGraphic(new ImageView(new Image("/resources/SoundOn.png")));
        muteButton.setTranslateX(-590);
        muteButton.setTranslateY(-220);

        Main.root.setOnKeyPressed(press -> {
            if (press.getCode() == KeyCode.M && press.isControlDown()) {

                if (!Main.MUTED) {
                    Main.themeSound.pause();
                    muteButton.setGraphic(new ImageView(new Image("/resources/SoundOff.png")));
                    Main.MUTED = true;

                } else {
                    Main.themeSound.play();
                    muteButton.setGraphic(new ImageView(new Image("/resources/SoundOn.png")));
                    Main.MUTED = false;
                }
            }
        });

        muteButton.setOnAction(mutePressed -> {

            if (!Main.MUTED) {
                Main.themeSound.pause();
                muteButton.setGraphic(new ImageView(new Image("/resources/SoundOff.png")));
                Main.MUTED = true;

            } else {
                Main.themeSound.play();
                muteButton.setGraphic(new ImageView(new Image("/resources/SoundOn.png")));
                Main.MUTED = false;
            }
        });

        playButton.setFont(font);
        playButton.setFill(Color.WHITE);
        playButton.setStrokeWidth(2);
        playButton.setTranslateY(-50);
        recordsButton.setFont(font);
        recordsButton.setFill(Color.WHITE);
        recordsButton.setStrokeWidth(2);
        recordsButton.setTranslateY(100);
        howToButton.setFont(font);
        howToButton.setFill(Color.WHITE);
        howToButton.setStrokeWidth(2);
        howToButton.setTranslateY(25);
        infoButton.setFont(font);
        infoButton.setFill(Color.WHITE);
        infoButton.setStrokeWidth(2);
        infoButton.setTranslateY(175);

        playButton2.setFont(font);
        playButton2.setFill(Color.BLACK);
        playButton2.setStrokeWidth(2);
        playButton2.setTranslateY(-53);
        playButton2.setTranslateX(-3);
        recordsButton2.setFont(font);
        recordsButton2.setFill(Color.BLACK);
        recordsButton2.setStrokeWidth(2);
        recordsButton2.setTranslateY(97);
        recordsButton2.setTranslateX(-3);
        howToButton2.setFont(font);
        howToButton2.setFill(Color.BLACK);
        howToButton2.setStrokeWidth(2);
        howToButton2.setTranslateY(22);
        howToButton2.setTranslateX(-3);
        infoButton2.setFont(font);
        infoButton2.setFill(Color.BLACK);
        infoButton2.setStrokeWidth(2);
        infoButton2.setTranslateY(172);
        infoButton2.setTranslateX(-3);

        Main.root.getChildren().addAll(bg2, stackPane);

        if (Main.count == 0) {
            Main.rocket.animate();
        }

        playButton.setOnMouseClicked(playClicked -> {

            if (CLICKABLE) {
                playButton.setOnMouseClicked(null);

                FadeTransition ft = new FadeTransition(Duration.seconds(2), stackPane);
                ft.setFromValue(1);
                ft.setToValue(0);
                ft.play();

                CLICKABLE = false;
                Main.SHOOT = true;
                ft.setOnFinished(finished -> GameSetup.gameSetup());
            }
        });

        recordsButton.setOnMouseClicked(recordsClicked -> {

            if (CLICKABLE) {

                CLICKABLE = false;

                File file = new File("scores.tws");

                if (file.length() != 0 && file.exists()) {
                    try {
                        Main.records = Score.read();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                Score.displayScores(Main.records);
            }
        });

        howToButton.setOnMouseClicked(howToClicked -> {

            if (CLICKABLE) {

                CLICKABLE = false;
                counter = 0;
                Main.root.getChildren().remove(muteButton);

                Image[] howToPanes = IntStream.range(0, 14).mapToObj(i -> new Image("/resources/HowTo " + i + ".png")).toArray(Image[]::new);
                currentPane = new ImageView(howToPanes[0]);
                Button nextButton = new Button();
                Button backButton = new Button();
                Button homeButton = new Button();

                nextButton.setScaleX(0.8);
                nextButton.setScaleY(0.8);
                nextButton.setTranslateX(622);
                nextButton.setTranslateY(425);
                nextButton.setStyle("-fx-background-color: transparent;");
                nextButton.graphicProperty().bind(Bindings.when(nextButton
                        .pressedProperty()).then(new ImageView(new Image("/resources/NextButtonPress.png")))
                        .otherwise(new ImageView(new Image("/resources/NextButton.png"))));

                backButton.setScaleX(0.8);
                backButton.setScaleY(0.8);
                backButton.setTranslateX(544);
                backButton.setTranslateY(425);
                backButton.setStyle("-fx-background-color: transparent;");
                backButton.graphicProperty().bind(Bindings.when(backButton
                        .pressedProperty()).then(new ImageView(new Image("/resources/BackButtonPress.png")))
                        .otherwise(new ImageView(new Image("/resources/BackButton.png"))));

                homeButton.setStyle("-fx-background-color: transparent;");
                homeButton.setTranslateX(230);
                homeButton.setTranslateY(7);
                homeButton.setGraphic(new ImageView(new Image("/resources/HomeButton.png")));

                Menu.muteButton.setTranslateX(-305);

                nextButton.setOnAction(action -> {

                    if (counter < 14) {
                        counter++;

                        if (counter == 14) {
                            Main.root.getChildren().clear();
                            Menu.menu();
                            CLICKABLE = true;
                        } else {
                            currentPane.setImage(howToPanes[counter]);

                            if (counter == 13) {
                                homeButton.setTranslateX(5);
                                Menu.muteButton.setTranslateX(-530);
                            } else {
                                homeButton.setTranslateX(230);
                                Menu.muteButton.setTranslateX(-305);
                            }
                        }
                    }
                });

                backButton.setOnAction(action2 -> {

                    if (counter > 0) {
                        counter--;
                        currentPane.setImage(howToPanes[counter]);
                    }

                    if (homeButton.getTranslateX() == 5) {
                        homeButton.setTranslateX(230);
                        Menu.muteButton.setTranslateX(-305);
                    }
                });

                homeButton.setOnAction(homeClicked -> {
                    Main.root.getChildren().clear();
                    Menu.menu();
                    CLICKABLE = true;
                });

                Main.root.getChildren().addAll(currentPane, backButton, nextButton, homeButton, muteButton);
            }
        });

        infoButton.setOnMouseClicked(infoClicked -> {

            //Shows the image with the game info

            if (CLICKABLE) {

                CLICKABLE = false;

                ImageView info = new ImageView(new Image("/resources/Information.png"));
                Button menuButton = new Button();

                menuButton.setScaleX(0.8);
                menuButton.setScaleY(0.8);
                menuButton.setStyle("-fx-background-color: transparent;");
                menuButton.setTranslateX(515);
                menuButton.setTranslateY(403);
                menuButton.graphicProperty().bind(Bindings.when(menuButton.pressedProperty())
                        .then(new ImageView(new Image("/resources/MenuButtonPress.png")))
                        .otherwise(new ImageView(new Image("/resources/MenuButton.png"))));

                menuButton.setOnAction(homeClicked -> {
                    Main.root.getChildren().clear();
                    Menu.menu();
                    CLICKABLE = true;
                });

                Main.root.getChildren().addAll(info, menuButton, muteButton);
            }
        });

        playButton.hoverProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue) {
                playButton.setFill(Color.DARKGREEN);
            } else {
                playButton.setFill(Color.WHITE);
            }
        });

        recordsButton.hoverProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue) {
                recordsButton.setFill(Color.DARKGREEN);
            } else {
                recordsButton.setFill(Color.WHITE);
            }
        });

        howToButton.hoverProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue) {
                howToButton.setFill(Color.DARKGREEN);
            } else {
                howToButton.setFill(Color.WHITE);
            }
        });

        infoButton.hoverProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue) {
                infoButton.setFill(Color.DARKGREEN);
            } else {
                infoButton.setFill(Color.WHITE);
            }
        });
    }

}
