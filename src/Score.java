import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.*;
import java.util.ArrayList;

/**
 * File Name: Score.java
 * <p>
 * Class containing all methods pertaining to the scores
 *
 * @author  Jonah Garmaise
 * @version 1.0
 * @since   April 26, 2018
 */

public class Score {

    private static Pane pane;
    private static SortedLinkedList<Record> newRecords = new SortedLinkedList<>();
    private static File file = new File("scores.tws");
    private static boolean SEARCH = true;

    /**
     * Reads the scores into a SortedLinkedList
     *
     * @throws IOException May be thrown from ObjectInputStream
     * @throws ClassNotFoundException May be thrown if the correct class cannot be located
     * @return SortedLinkedList
     * @author Jonah Garmaise
     */
    @SuppressWarnings("unchecked")
    static SortedLinkedList<Record> read() throws IOException, ClassNotFoundException {

        //Checks if the file exists in the directory

        if (!file.exists()) {
            file.createNewFile();
        }

        //Reads the file into a SortedLinkedList

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        return (SortedLinkedList<Record>) ois.readObject();
    }

    /**
     * Writes the scores as an object so they are difficult to tamper with
     *
     * @throws IOException May be thrown from ObjectOutputStream
     * @param records Collection to write to the file
     * @author Jonah Garmaise
     */
    static void write(SortedLinkedList<Record> records) throws IOException {

        //Writes a SortedLinkedList to the scores file

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(records);
        oos.flush();
        oos.close();
    }

    /**
     * Recursively searches the existing Collection of records for a given parameter.
     * <p>
     * Returns a new SortedLinkedList with the elements that contained the parameter.
     *
     * @param string Parameter to be searched for
     * @param records Collection to search
     * @param index Index to start search from
     * @return SortedLinkedList
     */
    static SortedLinkedList<Record> search(String string, SortedLinkedList<Record> records, int index) {

        if (records.size() == 0) {
            return null;
        }

        if (index < records.size()) {

            if (records.get(index).getP1Name().toLowerCase().contains(string) || records.get(index).getP2Name().toLowerCase().contains(string)) {
                newRecords.add(records.get(index));
            }
            return search(string, records, index + 1);
        }
        return newRecords;
    }

    /**
     * Adds all the elements making up a Record to individual Nodes.
     * <p>
     * The nodes are then added into one Pane of all the scores,
     *
     * @param records Collection to be displayed
     */
    static void displayScores(SortedLinkedList<Record> records) {

        ArrayList<Text> p1Text = new ArrayList<>();
        ArrayList<Text> p2Text = new ArrayList<>();
        ArrayList<Text> dateText = new ArrayList<>();
        ArrayList<Text> p1Text2 = new ArrayList<>();
        ArrayList<Text> p2Text2 = new ArrayList<>();
        ArrayList<Text> dateText2 = new ArrayList<>();
        Text scrollText = new Text("(SCROLL)");
        ArrayList<BorderPane> recordPanes = new ArrayList<>();
        BorderPane previous = new BorderPane();
        ImageView scoresBG = new ImageView(new Image("/resources/CamoBG.png"));
        ImageView scoresTitle = new ImageView(new Image("/resources/RecordsBG.png"));
        Font font = Font.loadFont(Main.class.getResourceAsStream("/resources/Army Font.otf"), 40);
        Button menuButton = new Button();
        Button searchButton = new Button();
        Button undoButton = new Button();
        Button clearButton = new Button();
        pane = new Pane();
        int xCoord = 0;

        scrollText.setFont(font);
        scrollText.relocate(1035, 135);
        scrollText.setFill(Color.WHITE);

        menuButton.setScaleX(0.8);
        menuButton.setScaleY(0.8);
        menuButton.setStyle("-fx-background-color: transparent;");
        menuButton.setTranslateX(20);
        menuButton.setTranslateY(135);
        menuButton.graphicProperty().bind(Bindings.when(menuButton.pressedProperty())
                .then(new ImageView(new Image("/resources/MenuButtonPress.png")))
                .otherwise(new ImageView(new Image("/resources/MenuButton.png"))));

        searchButton.setScaleX(0.8);
        searchButton.setScaleY(0.8);
        searchButton.setStyle("-fx-background-color: transparent;");
        searchButton.setTranslateX(20);
        searchButton.setTranslateY(195);
        searchButton.graphicProperty().bind(Bindings.when(searchButton.pressedProperty())
                .then(new ImageView(new Image("/resources/SearchButtonPress.png")))
                .otherwise(new ImageView(new Image("/resources/SearchButton.png"))));

        undoButton.setScaleX(0.8);
        undoButton.setScaleY(0.8);
        undoButton.setStyle("-fx-background-color: transparent;");
        undoButton.setTranslateX(20);
        undoButton.setTranslateY(195);
        undoButton.graphicProperty().bind(Bindings.when(undoButton.pressedProperty())
                .then(new ImageView(new Image("/resources/UndoButtonPress.png")))
                .otherwise(new ImageView(new Image("/resources/UndoButton.png"))));

        clearButton.setScaleX(0.8);
        clearButton.setScaleY(0.8);
        clearButton.setStyle("-fx-background-color: transparent;");
        clearButton.setTranslateX(20);
        clearButton.setTranslateY(255);
        clearButton.graphicProperty().bind(Bindings.when(clearButton.pressedProperty())
                .then(new ImageView(new Image("/resources/ClearButtonPress.png")))
                .otherwise(new ImageView(new Image("/resources/ClearButton.png"))));

        if (records.size() == 0) {

            Text noScores = new Text(425, 250, "NO SCORES FOUND");
            Text noScores2 = new Text(428, 253, "NO SCORES FOUND");
            noScores.setFont(font);
            noScores2.setFont(font);
            noScores.setScaleX(1.5);
            noScores.setScaleY(1.5);
            noScores2.setScaleX(1.5);
            noScores2.setScaleY(1.5);
            noScores2.setFill(Color.WHITE);

            Main.root.getChildren().addAll(scoresBG, scoresTitle, menuButton, noScores, noScores2);

        } else {

            //Reads the scores into individual panes

            int i = 0;
            for (Record record : records) {

                dateText.add(new Text(record.getDate() + "\n"));
                p1Text.add(new Text("PLAYER 1:\n\n" + "Name: " + record.getP1Name() + "\nShots: " + record.getP1Shots() + "\nWins: " + record.getP1Wins() + "/3"));
                p2Text.add(new Text("PLAYER 2:\n\n" + "Name: " + record.getP2Name() + "\nShots: " + record.getP2Shots() + "\nWins: " + record.getP2Wins() + "/3"));
                dateText2.add(new Text(record.getDate() + "\n"));
                p1Text2.add(new Text("PLAYER 1:\n\n" + "Name: " + record.getP1Name() + "\nShots: " + record.getP1Shots() + "\nWins: " + record.getP1Wins() + "/3"));
                p2Text2.add(new Text("PLAYER 2:\n\n" + "Name: " + record.getP2Name() + "\nShots: " + record.getP2Shots() + "\nWins: " + record.getP2Wins() + "/3"));

                p1Text.get(i).setFont(font);
                p2Text.get(i).setFont(font);
                dateText.get(i).setFont(font);
                p1Text.get(i).setWrappingWidth(300);
                p2Text.get(i).setWrappingWidth(300);
                dateText.get(i).setWrappingWidth(300);
                p1Text.get(i).setTextAlignment(TextAlignment.CENTER);
                p2Text.get(i).setTextAlignment(TextAlignment.CENTER);
                dateText.get(i).setTextAlignment(TextAlignment.CENTER);

                p1Text2.get(i).setFont(font);
                p2Text2.get(i).setFont(font);
                dateText2.get(i).setFont(font);
                p1Text2.get(i).setWrappingWidth(300);
                p2Text2.get(i).setWrappingWidth(300);
                dateText2.get(i).setWrappingWidth(300);
                p1Text2.get(i).setFill(Color.WHITE);
                p2Text2.get(i).setFill(Color.WHITE);
                dateText2.get(i).setFill(Color.WHITE);
                p1Text2.get(i).setTextAlignment(TextAlignment.CENTER);
                p2Text2.get(i).setTextAlignment(TextAlignment.CENTER);
                dateText2.get(i).setTextAlignment(TextAlignment.CENTER);

                BorderPane borderPane = new BorderPane(new Rectangle(150, 0), dateText.get(i), p2Text.get(i), null, p1Text.get(i));
                BorderPane borderPane2 = new BorderPane(new Rectangle(150, 0), dateText2.get(i), p2Text2.get(i), null, p1Text2.get(i));

                BorderPane.setAlignment(dateText.get(i), Pos.TOP_CENTER);
                BorderPane.setAlignment(dateText2.get(i), Pos.TOP_CENTER);

                borderPane2.setTranslateX(borderPane2.getTranslateX() + 2);
                borderPane2.setTranslateY(borderPane2.getTranslateY() + 2);

                xCoord += previous.getBoundsInParent().getHeight() * 1.75;
                recordPanes.add(new BorderPane(new Pane(borderPane, borderPane2)));
                recordPanes.get(i).setTranslateY(xCoord);
                recordPanes.get(i).setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");
                pane.getChildren().add(recordPanes.get(i));
                previous = borderPane;
                i++;
            }
            pane.setTranslateX((Main.width - pane.getBoundsInParent().getWidth() * 2 - 150) / 2);
            pane.setTranslateY(140);
            Main.root.getChildren().addAll(scoresBG, pane, scoresTitle, menuButton, searchButton, Menu.muteButton, scrollText);

            if (SEARCH) {
                Main.root.getChildren().add(clearButton);
            }
        }

        Main.root.setOnScroll(scroll -> {

                //Allows the user to scroll through the scores

                double newY = Score.pane.getTranslateY() + scroll.getDeltaY();

                if (newY < 145 && newY > -pane.getBoundsInParent().getHeight() + 480) {
                    Score.pane.setTranslateY(newY);
                }
            });

        menuButton.setOnAction(action -> {

                //Takes the user to the menu

                Main.root.getChildren().clear();
                Menu.menu();
                Menu.CLICKABLE = true;
                SEARCH = true;
            });

        searchButton.setOnAction(action2 -> {

            if (SEARCH) {

                SEARCH = false;

                TextField searchField = new TextField();
                ImageView searchPrompt = new ImageView(new Image("/resources/PromptBG2.png"));
                StackPane stackPane = new StackPane(searchPrompt, searchField);

                stackPane.relocate(438, 161);
                searchField.setMaxWidth(300);

                Main.root.getChildren().add(stackPane);

                searchField.setOnKeyPressed(keyPressed -> {

                    //Searches on ENTER pressed

                    if (keyPressed.getCode() == KeyCode.ENTER) {

                        Main.root.getChildren().remove(stackPane);
                        String string = searchField.getText().toLowerCase();
                        newRecords.clear();

                        //Searches the scores with the given parameter

                        Main.root.getChildren().clear();
                        displayScores(search(string, records, 0));
                        SEARCH = false;

                        Main.root.getChildren().add(undoButton);

                        undoButton.setOnAction(action3 -> {

                            //Undoes the search

                            Main.root.getChildren().clear();
                            Main.root.requestFocus();
                            SEARCH = true;
                            displayScores(records);
                        });
                    }
                });
            }
        });

        clearButton.setOnAction(action3 -> {

            Main.records.clear();
            try {
                write(Main.records);
            } catch (IOException e) {
                e.printStackTrace();
            }
            displayScores(Main.records);
        });
    }
}

