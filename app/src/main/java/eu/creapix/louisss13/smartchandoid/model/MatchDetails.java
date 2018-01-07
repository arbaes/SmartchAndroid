package eu.creapix.louisss13.smartchandoid.model;

import java.io.Serializable;
import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.utils.Constants;

/**
 * Created by arnau on 04-01-18.
 */

public class MatchDetails implements Serializable {

    private int currentPointPlayer1;
    private int currentPointPlayer2;
    private int nbSetWonPlayer1;
    private int nbSetWonPlayer2;
    private ArrayList<Integer> detailsPointsPlayer1;
    private ArrayList<Integer> detailsPointsPlayer2;

    public MatchDetails(int currentPointPlayer1, int currentPointPlayer2, int nbSetWonPlayer1, int nbSetWonPlayer2, ArrayList<Integer> detailsPointsPlayer1, ArrayList<Integer> detailsPointsPlayer2) {
        this.currentPointPlayer1 = currentPointPlayer1;
        this.currentPointPlayer2 = currentPointPlayer2;
        this.nbSetWonPlayer1 = nbSetWonPlayer1;
        this.nbSetWonPlayer2 = nbSetWonPlayer2;
        this.detailsPointsPlayer1 = detailsPointsPlayer1;
        this.detailsPointsPlayer2 = detailsPointsPlayer2;
    }

    public void addPoint(int scoredBy) {

        switch (scoredBy) {
            case Constants.PLAYER_1_POINT:
                currentPointPlayer1++;
                break;
            case Constants.PLAYER_2_POINT:
                currentPointPlayer2++;
                break;
        }

        if ((this.currentPointPlayer2 >= 11) || (this.currentPointPlayer1 >= 11)) {

            if ((this.currentPointPlayer1 > this.currentPointPlayer2) && (this.currentPointPlayer1 - this.currentPointPlayer2 >= 2)) {
                this.nbSetWonPlayer1++;
                this.detailsPointsPlayer1.add(this.currentPointPlayer1);
                this.detailsPointsPlayer2.add(this.currentPointPlayer2);
                this.currentPointPlayer1 = 0;
                this.currentPointPlayer2 = 0;

            } else if ((this.currentPointPlayer1 > this.currentPointPlayer2) && (this.currentPointPlayer1 - this.currentPointPlayer2 >= 2)) {
                this.nbSetWonPlayer2++;
                this.detailsPointsPlayer1.add(this.currentPointPlayer1);
                this.detailsPointsPlayer2.add(this.currentPointPlayer2);
                this.currentPointPlayer1 = 0;
                this.currentPointPlayer2 = 0;
            }
        }

    }

    public int getCurrentPointPlayer1() {
        return currentPointPlayer1;
    }

    public int getCurrentPointPlayer2() {
        return currentPointPlayer2;
    }

    public int getNbSetWonPlayer1() {
        return nbSetWonPlayer1;
    }

    public int getNbSetWonPlayer2() {
        return nbSetWonPlayer2;
    }

    public ArrayList<Integer> getDetailsPointsPlayer1() {
        return detailsPointsPlayer1;
    }

    public ArrayList<Integer> getDetailsPointsPlayer2() {
        return detailsPointsPlayer2;
    }
}
