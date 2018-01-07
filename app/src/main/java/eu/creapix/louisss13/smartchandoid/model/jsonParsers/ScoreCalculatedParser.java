package eu.creapix.louisss13.smartchandoid.model.jsonParsers;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by arnau on 06-01-18.
 */

public class ScoreCalculatedParser implements Serializable {

    @SerializedName("pointLevel")
    ArrayList<PointLevelParser> pointLevels;

    public ScoreCalculatedParser() {
    }

    public ArrayList<PointLevelParser> getPointLevels() {
        return pointLevels;
    }

    public void setPointLevels(ArrayList<PointLevelParser> pointLevels) {
        this.pointLevels = pointLevels;
    }
}
