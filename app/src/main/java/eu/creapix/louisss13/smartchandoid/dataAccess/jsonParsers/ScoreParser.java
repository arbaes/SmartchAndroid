package eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by arnau on 03-01-18.
 */

public class ScoreParser extends JSONObject {

    @SerializedName("score")
    private ArrayList<PointParser> pointsSored;

    public ArrayList<PointParser> getPointsSored() {
        return pointsSored;
    }

    public void setPointsSored(ArrayList<PointParser> pointsSored) {
        this.pointsSored = pointsSored;
    }
}
