package eu.creapix.louisss13.smartchandoid.dataAccess;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.dataAccess.enums.Urls;
import eu.creapix.louisss13.smartchandoid.model.WebserviceListener;
import eu.creapix.louisss13.smartchandoid.model.daoInterfaces.ProfileDataAccess;
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.AccountParser;
import eu.creapix.louisss13.smartchandoid.utils.Constants;

/**
 * Created by arnau on 03-01-18.
 */

public class ProfileDao implements ProfileDataAccess {

    private static final String TAG = "ProfileDao";

    private ApiService apiService;
    private Gson gson;
    private HTTPJsonHandler datahandler = new HTTPJsonHandler();

    public ProfileDao() {
        apiService = new ApiService();
        gson = new Gson();
    }

    @Override
    public void getProfile(WebserviceListener webserviceListener, String token) throws IOException, JSONException {

        HttpURLConnection connection = datahandler.GetHTTPData(Urls.Account, token);

        if ((connection.getResponseCode() >= 200) && (connection.getResponseCode() < 300)) {
            Log.e(TAG, "Connexion OK - " + connection.getResponseCode());

            String stream = datahandler.StreamToJson(connection.getInputStream());
            Log.e("JSON", "Content : " + stream);

            Type accountType = new TypeToken<AccountParser>() {
            }.getType();
            AccountParser profile = gson.fromJson(stream, accountType);
            ArrayList<Object> profileList = new ArrayList<Object>();
            profileList.add(profile);
            webserviceListener.onWebserviceFinishWithSuccess(Constants.GET_PROFILE, 0, profileList);
        } else {
            Log.e(TAG, "Connexion NOT OK : " + connection.getResponseCode());
            webserviceListener.onWebserviceFinishWithError(connection.getResponseCode() + " - " + connection.getResponseMessage(), 707);
        }
    }
}
