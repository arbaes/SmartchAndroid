package eu.creapix.louisss13.smartchandoid.model;

/**
 * Created by arnau on 29-12-17.
 */

public class Point {

    private enum Player {player1, player2};
    private int valeur;
    private int position;

    public Point() {
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
