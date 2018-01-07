package eu.creapix.louisss13.smartchandoid.model;

import java.io.Serializable;

/**
 * Created by arnau on 23-12-17.
 */

public class PlayerScore implements Serializable {

    private String player1Name;
    private int player1Score;
    private String player2Name;
    private int player2Score;


    public PlayerScore() {
    }

    public PlayerScore(String player1Name, int player1Score, int player2Score, String player2Name) {

        this.player1Name = player1Name;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.player2Name = player2Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(int player2Score) {
        this.player2Score = player2Score;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Score(int playerScore) {
        this.player1Score = playerScore;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }
}
