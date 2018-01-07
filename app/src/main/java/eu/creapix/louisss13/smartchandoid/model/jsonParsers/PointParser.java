package eu.creapix.louisss13.smartchandoid.model.jsonParsers;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * Created by arnau on 03-01-18.
 */

public class PointParser extends JSONObject {

    @SerializedName("id")
    private int pointId;

    @SerializedName("joueur")
    private int scoredBy;

    @SerializedName("order")
    private int pointOrder;

    public PointParser(int scoredBy) {
        this.scoredBy = scoredBy;
    }

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public int getScoredBy() {
        return scoredBy;
    }

    public void setScoredBy(int scoredBy) {
        this.scoredBy = scoredBy;
    }

    public int getPointOrder() {
        return pointOrder;
    }

    public void setPointOrder(int pointOrder) {
        this.pointOrder = pointOrder;
    }
}
