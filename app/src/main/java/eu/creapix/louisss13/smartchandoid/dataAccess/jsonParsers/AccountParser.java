package eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * Created by arnau on 03-01-18.
 */

public class AccountParser extends JSONObject {



    private String email;
    private String id;

    @SerializedName("infosUsers")
    private UserInfoParser[] userInfos;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserInfoParser[] getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(UserInfoParser[] infosUsers) {
        this.userInfos = infosUsers;
    }

    public AccountParser() {
    }
}
