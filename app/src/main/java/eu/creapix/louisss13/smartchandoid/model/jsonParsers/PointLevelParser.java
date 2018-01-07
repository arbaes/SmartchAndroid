package eu.creapix.louisss13.smartchandoid.model.jsonParsers;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import eu.creapix.louisss13.smartchandoid.utils.Constants;

/**
 * Created by arnau on 06-01-18.
 */

public class PointLevelParser implements Serializable {


    @SerializedName("Joueur1")
    private int scorePlayer1;

    @SerializedName("Joueur2")
    private int scorePlayer2;

    public PointLevelParser() {
    }

    public PointLevelParser(int scorePlayer1, int scorePlayer2) {
        this.scorePlayer1 = scorePlayer1;
        this.scorePlayer2 = scorePlayer2;
    }

    public int getSetWinner(){

        return scorePlayer1 > scorePlayer2 ? Constants.PLAYER_1_POINT : Constants.PLAYER_2_POINT;
    }

    public int getScorePlayer1() {
        return scorePlayer1;
    }

    public void setScorePlayer1(int scorePlayer1) {
        this.scorePlayer1 = scorePlayer1;
    }

    public int getScorePlayer2() {
        return scorePlayer2;
    }

    public void setScorePlayer2(int scorePlayer2) {
        this.scorePlayer2 = scorePlayer2;
    }
}
