package eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers;

import org.json.JSONObject;

import eu.creapix.louisss13.smartchandoid.model.UserInfo;

/**
 * Created by arnau on 03-01-18.
 */

public class AccountParser extends JSONObject {

    private String email;
    private String id;
    private UserInfo[] infosUsers;
}
