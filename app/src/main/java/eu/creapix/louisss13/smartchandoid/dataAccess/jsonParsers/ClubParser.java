package eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers;

import org.json.JSONObject;

/**
 * Created by arnau on 03-01-18.
 */

public class ClubParser extends JSONObject {

    private String name;

    public ClubParser(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
