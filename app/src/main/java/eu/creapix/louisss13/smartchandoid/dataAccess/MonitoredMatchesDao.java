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
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.MatchParser;
import eu.creapix.louisss13.smartchandoid.utils.Constants;

/**
 * Created by arnau on 30-12-17.
 */

public class MonitoredMatchesDao {

    private static final String TAG = "MonitoredMatchesDao";

    private ApiService apiService;
    private Gson gson;
    private HTTPJsonHandler datahandler = new HTTPJsonHandler();

    public MonitoredMatchesDao() {
        apiService = new ApiService();
        gson = new Gson();
    }

    public void getMonitoredMatches(WebserviceListener webserviceListener, String token) throws IOException, JSONException {

        HttpURLConnection connection = datahandler.GetHTTPData(Urls.Monitoring, token);

        if (connection.getResponseCode() == 200) {
            Log.e(TAG, "Connexion OK - " + connection.getResponseCode());

            String stream = datahandler.StreamToJson(connection.getInputStream());
            Log.e(TAG, "JSON - " + stream);


            Type matchType = new TypeToken<ArrayList<MatchParser>>(){}.getType();
            ArrayList<Object> monitoredMatches = gson.fromJson(stream, matchType);
            webserviceListener.onWebserviceFinishWithSuccess(Constants.GET_MONITORED_MATCHES, 0, monitoredMatches);
        } else {
            Log.e(TAG, "Connexion NOT OK : " + connection.getResponseCode());
            webserviceListener.onWebserviceFinishWithError(connection.getResponseCode() + " - " + connection.getResponseMessage(), 707);
        }
    }
}
