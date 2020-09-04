import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * File Name: Record.java
 * <p>
 * Object to store the games records
 *
 * @author  Jonah Garmaise
 * @version 1.0
 * @since   April 26, 2018
 */
class Record implements Serializable, Comparable<Record> {

    private String p1Name, p2Name, date;
    private int p1Shots = 0, p2Shots = 0, p1Wins = 0, p2Wins = 0;
    private Enums.Tank p1Type, p2Type;
    private Enums.Player winner;

    /**
     * Empty initializer for Record
     *
     * @author Jonah Garmaise
     */
    public Record(){

        //Adds the current date as soon as a record is created

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        this.date = dtf.format(localDate);
    }

    /**
     * Constructor for Record with all parameters
     *
     * @param p1Name Player 1 name
     * @param p2Name Player 2 name
     * @param p1Shots Player 1 shots
     * @param p2Shots Player 2 shots
     * @param p1Wins Player 1 wins (x/3)
     * @param p2Wins Player 2 wins (x/3)
     * @param p1Type Player 1 tank type
     * @param p2Type Player 2 tank type
     * @param winner Winning player
     * @author Jonah Garmaise
     */
    public Record(String p1Name, String p2Name, int p1Shots, int p2Shots, int p1Wins, int p2Wins, Enums.Tank p1Type, Enums.Tank p2Type, Enums.Player winner) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();

        this.p1Name  = p1Name;
        this.p2Name  = p2Name;
        this.p1Wins  = p1Wins;
        this.p2Wins  = p2Wins;
        this.p1Shots = p1Shots;
        this.p2Shots = p2Shots;
        this.p1Type  = p1Type;
        this.p2Type  = p2Type;
        this.winner  = winner;
        this.date = dtf.format(localDate);
    }

    /**
     * Returns player one's name
     *
     * @return String p1Name
     * @author Jonah Garmaise
     */
    public String getP1Name() {
        return p1Name;
    }

    /**
     * Sets player one's name
     *
     * @param p1Name Player 1 name
     *
     * @author Jonah Garmaise
     */
    public void setP1Name(String p1Name) {
        this.p1Name = p1Name;
    }

    /**
     * Returns player two's name
     *
     * @return String p2Name
     * @author Jonah Garmaise
     */
    public String getP2Name() {
        return p2Name;
    }

    /**
     * Sets player two's name
     *
     * @param p2Name Player 2 name
     * @author Jonah Garmaise
     */
    public void setP2Name(String p2Name) {
        this.p2Name = p2Name;
    }

    /**
     * Returns player one's wins
     *
     * @return int p1Wins
     * @author Jonah Garmaise
     */
    public int getP1Wins() {
        return p1Wins;
    }

    /**
     * Sets player one's wins
     *
     * @param p1Wins Player 1 wins
     * @author Jonah Garmaise
     */
    public void setP1Wins(int p1Wins) {
        this.p1Wins = p1Wins;
    }

    /**
     * Returns player one's shots
     *
     * @return int p1Shots
     * @author Jonah Garmaise
     */
    public int getP1Shots() {
        return p1Shots;
    }

    /**
     * Sets player ones shots
     *
     * @param p1Shots Player 1 shots
     * @author Jonah Garmaise
     */
    public void setP1Shots(int p1Shots) {
        this.p1Shots = p1Shots;
    }

    /**
     * Returns player two's shots
     *
     * @return int p2Shots
     * @author Jonah Garmaise
     */
    public int getP2Shots() {
        return p2Shots;
    }

    /**
     * Sets player two's shots
     *
     * @param p2Shots Player 2 shots
     * @author Jonah Garmaise
     */
    public void setP2Shots(int p2Shots) {
        this.p2Shots = p2Shots;
    }

    /**
     * Returns player two's wins
     *
     * @return int p2Wins
     * @author Jonah Garmaise
     */
    public int getP2Wins() {
        return p2Wins;
    }

    /**
     * Sets player two's wins
     *
     * @param p2Wins Player 2 wins
     * @author Jonah Garmaise
     */
    public void setP2Wins(int p2Wins) {
        this.p2Wins = p2Wins;
    }

    /**
     * Returns player one's type
     *
     * @return Enums.Tank p1Type
     * @author Jonah Garmaise
     */
    public Enums.Tank getP1Type() {
        return p1Type;
    }

    /**
     * Sets player one's type
     *
     * @param p1Type Player 1 type
     * @author Jonah Garmaise
     */
    public void setP1Type(Enums.Tank p1Type) {
        this.p1Type = p1Type;
    }

    /**
     * Returns player two's type
     *
     * @return Enums.Tank p2Type
     * @author Jonah Garmaise
     */
    public Enums.Tank getP2Type() {
        return p2Type;
    }

    /**
     * Sets player two's type
     *
     * @param p2Type Player 2 type
     * @author Jonah Garmaise
     */
    public void setP2Type(Enums.Tank p2Type) {
        this.p2Type = p2Type;
    }

    /**
     * Returns the winner
     *
     * @return Enums.Player winner
     * @author Jonah Garmaise
     */
    private Enums.Player getWinner() {
        return winner;
    }

    /**
     * Sets the games winner
     *
     * @param winner Game winner
     * @author Jonah Garmaise
     */
    public void setWinner(Enums.Player winner) {
        this.winner = winner;
    }

    /**
     * Returns the date
     *
     * @return String date
     * @author Jonah Garmaise
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns a string representation of a Record
     *
     * @return a string representation of the object.
     * @author Jonah Garmaise
     */
    @Override
    public String toString() {
        return "Record{" + "p1Name='" + p1Name + '\'' + ", p2Name='" + p2Name + '\'' + ", date='" + date + '\'' + ", p1Shots=" + p1Shots + ", p2Shots=" + p2Shots + ", p1Wins=" + p1Wins + ", p2Wins=" + p2Wins + ", p1Type=" + p1Type + ", p2Type=" + p2Type + ", winner=" + winner + '}';
    }

    /**
     * Overrides the compareTo method used in sorting
     *
     * @param r Record being compared to
     * @return integer value of where to place object
     * @author Jonah Garmaise
     */
    @Override
    public int compareTo(Record r) {

        //compares the score each winner to the one prior

        if (this.getWinner() == Enums.Player.ONE && r.getWinner() == Enums.Player.ONE) {
            return Integer.compare(this.getP1Shots(), r.getP1Shots());

        } else if (this.getWinner() == Enums.Player.ONE && r.getWinner() == Enums.Player.TWO) {
            return Integer.compare(this.getP1Shots(), r.getP2Shots());

        }  else if (this.getWinner() == Enums.Player.TWO && r.getWinner() == Enums.Player.TWO) {
            return Integer.compare(this.getP2Shots(), r.getP2Shots());

        } else {
            return Integer.compare(this.getP2Shots(), r.getP1Shots());
        }
    }
}

