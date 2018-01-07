package eu.creapix.louisss13.smartchandoid.model.jsonParsers;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * Created by arnau on 24-12-17.
 */

public class TokenParser extends JSONObject {

    //Le nom des variables est identique aux noms dans les JSon, ne pas modifier !
    @SerializedName("access_token")
    private String access_token;

    @SerializedName("expires_in")
    private int expires_in;

    public TokenParser(){
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
