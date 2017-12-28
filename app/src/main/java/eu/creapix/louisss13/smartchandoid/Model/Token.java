package eu.creapix.louisss13.smartchandoid.Model;

import org.json.JSONObject;

/**
 * Created by arnau on 24-12-17.
 */

public class Token extends JSONObject {
    private String access_token;
    private int expires_in;

    public Token(){
    }

    public String getAccessToken() {
        return access_token;
    }

    public int getExpiresIn() {
        return expires_in;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
