package eu.creapix.louisss13.smartchandoid.dataAccess;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.dataAccess.ApiService;
import eu.creapix.louisss13.smartchandoid.dataAccess.HTTPJsonHandler;
import eu.creapix.louisss13.smartchandoid.dataAccess.enums.Urls;
import eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers.AccountParser;
import eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers.TournamentParser;
import eu.creapix.louisss13.smartchandoid.utils.Constants;

/**
 * Created by arnau on 03-01-18.
 */

public class ProfileDao {

    private static final String TAG = "ProfileDao";

    private ApiService apiService;
    private Gson gson;
    private HTTPJsonHandler datahandler = new HTTPJsonHandler();

    public ProfileDao() {
        apiService = new ApiService();
        gson = new Gson();
    }

    public void getProfile(WebserviceListener webserviceListener, String token) throws IOException, JSONException {

        HttpURLConnection connection = datahandler.GetHTTPData(Urls.Account, token);

        if (connection.getResponseCode() == 200) {
            Log.e(TAG, "Connexion OK - " + connection.getResponseCode());

            String stream = datahandler.StreamToJson(connection.getInputStream());

            Type accountType = new TypeToken<AccountParser>(){}.getType();
            AccountParser profile = gson.fromJson(stream, accountType);
            ArrayList<Object> profileList = new ArrayList<Object>();
            profileList.add(profile);
            webserviceListener.onWebserviceFinishWithSuccess(Constants.GET_PROFILE, profileList);
        } else {
            Log.e(TAG, "Connexion NOT OK : " + connection.getResponseCode());
            webserviceListener.onWebserviceFinishWithError(connection.getResponseCode() + " - " + connection.getResponseMessage());
        }
    }
}