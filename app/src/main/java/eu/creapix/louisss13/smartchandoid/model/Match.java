package eu.creapix.louisss13.smartchandoid.model;

import java.util.Date;

import eu.creapix.louisss13.smartchandoid.utils.enums.EMatchState;

/**
 * Created by arnau on 29-12-17.
 */

public class Match {

    private Player player1;
    private Player player2;
    private UserAccount arbitre;
    private String emplacement;
    private EMatchState status;
    private Score score;
    private Date time;

    public Match() {
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public UserAccount getArbitre() {
        return arbitre;
    }

    public void setArbitre(UserAccount arbitre) {
        this.arbitre = arbitre;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public EMatchState getStatus() {
        return status;
    }

    public void setStatus(EMatchState status) {
        this.status = status;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }
}
