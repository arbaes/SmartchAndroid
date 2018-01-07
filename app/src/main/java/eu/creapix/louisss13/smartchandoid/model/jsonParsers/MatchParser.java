package eu.creapix.louisss13.smartchandoid.model.jsonParsers;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import eu.creapix.louisss13.smartchandoid.model.PlayerScore;
import eu.creapix.louisss13.smartchandoid.utils.Constants;

/**
 * Created by arnau on 03-01-18.
 */

public class MatchParser extends JSONObject {

    @SerializedName("debutPrevu")
    private String startDate;

    @SerializedName("emplacement")
    private String location;

    @SerializedName("id")
    private int id;

    @SerializedName("joueur1")
    private UserInfoParser player1;

    @SerializedName("joueur2")
    private UserInfoParser player2;

    @SerializedName("phase")
    private int phase;

    @SerializedName("calculatedScore")
    private ScoreCalculatedParser score;

    public MatchParser() {

    }

    public PlayerScore getMatchScore(){

        int totPlayer1Points = 0;
        int totPlayer2Points = 0;


        if (this.score.getPointLevels() != null ) {
            for (int i = 0; i < this.score.getPointLevels().size() - 1; i++) {

                switch (this.score.getPointLevels().get(i).getSetWinner()) {
                    case Constants.PLAYER_1_POINT:
                        totPlayer1Points++;
                        break;
                    case Constants.PLAYER_2_POINT:
                        totPlayer2Points++;
                        break;
                }
            }
        }


        PlayerScore matchScore = new PlayerScore();
        matchScore.setPlayer1Score(totPlayer1Points);
        matchScore.setPlayer2Score(totPlayer2Points);
        return matchScore;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserInfoParser getPlayer1() {
        return player1;
    }

    public void setPlayer1(UserInfoParser player1) {
        this.player1 = player1;
    }

    public UserInfoParser getPlayer2() {
        return player2;
    }

    public void setPlayer2(UserInfoParser player2) {
        this.player2 = player2;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public ScoreCalculatedParser getScore() {
        return score;
    }

    public void setScore(ScoreCalculatedParser score) {
        this.score = score;
    }
}
