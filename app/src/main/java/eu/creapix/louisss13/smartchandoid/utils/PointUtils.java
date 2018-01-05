package eu.creapix.louisss13.smartchandoid.utils;

import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers.PointParser;
import eu.creapix.louisss13.smartchandoid.model.MatchDetails;
import eu.creapix.louisss13.smartchandoid.model.Point;

/**
 * Created by arnau on 04-01-18.
 */

public class PointUtils {

    private ArrayList<PointParser> matchScore;

    public PointUtils() {
    }

    public ArrayList<PointParser> getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(ArrayList<PointParser> matchScore) {
        this.matchScore = matchScore;
    }

    public MatchDetails getMatchDetails(){
        int pointPlayer1 = 0;
        int pointPlayer2 = 0;
        int nbSetWonPlayer1 = 0;
        int nbSetWonPlayer2 = 0;
        ArrayList<Integer> setDetailsPlayer1 = null;
        ArrayList<Integer> setDetailsPlayer2 = null;

        for(int i = 0; i < matchScore.size(); i++){

            if((pointPlayer1 >= 11) || (pointPlayer2 >= 11)){

                if((pointPlayer1 > pointPlayer2) && ( pointPlayer1 - pointPlayer2 >= 2 )){
                    nbSetWonPlayer1++;
                    setDetailsPlayer1.add(pointPlayer1);
                    setDetailsPlayer2.add(pointPlayer2);
                    pointPlayer1 = 0;

                } else if  ((pointPlayer1 < pointPlayer2) && (pointPlayer2 - pointPlayer1 >= 2 )){
                    nbSetWonPlayer2++;
                    setDetailsPlayer1.add(pointPlayer1);
                    setDetailsPlayer2.add(pointPlayer2);
                    pointPlayer1 = 0;
                    pointPlayer2 = 0;
                }

            }

            switch (matchScore.get(i).getScoredBy()){
                case Constants.PLAYER_1_POINT:
                    pointPlayer1++;
                    break;
                case Constants.PLAYER_2_POINT:
                    pointPlayer2++;
                    break;
            }
        }

        MatchDetails matchDetails = new MatchDetails(
                pointPlayer1,
                pointPlayer2,
                nbSetWonPlayer1,
                nbSetWonPlayer2,
                setDetailsPlayer1,
                setDetailsPlayer2
        );
        return matchDetails;
    }
}