package eu.creapix.louisss13.smartchandoid.model;

import java.util.ArrayList;

/**
 * Created by arnau on 29-12-17.
 */

public class MatchsPhase {

    private int numPhase;
    private ArrayList<Match> matchs;

    public MatchsPhase() {
    }

    public int getNumPhase() {
        return numPhase;
    }

    public void setNumPhase(int numPhase) {
        this.numPhase = numPhase;
    }

    public ArrayList<Match> getMatchs() {
        return matchs;
    }

    public void setMatchs(ArrayList<Match> matchs) {
        this.matchs = matchs;
    }
}
