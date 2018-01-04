package eu.creapix.louisss13.smartchandoid.dataAccess.daomodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arnau on 04-01-18.
 */

public class PointDaoModel {

    @SerializedName("joueur")
    private boolean player;

    public PointDaoModel(boolean player) {
        this.player = player;
    }
}
