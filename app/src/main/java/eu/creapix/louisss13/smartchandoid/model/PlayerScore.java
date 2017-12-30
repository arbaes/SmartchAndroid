package eu.creapix.louisss13.smartchandoid.model;

/**
 * Created by arnau on 23-12-17.
 */

public class PlayerScore {

    private String playerName;
    private int playerScore;

    public PlayerScore (String playerName, int playerScore){

        this.playerName = playerName;
        this.playerScore = playerScore;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
