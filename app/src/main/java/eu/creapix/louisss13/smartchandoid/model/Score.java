package eu.creapix.louisss13.smartchandoid.model;

import java.util.ArrayList;

/**
 * Created by arnau on 29-12-17.
 */

public class Score {

    private ArrayList<Point> points;
    private Player winner;

    public Score() {
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
