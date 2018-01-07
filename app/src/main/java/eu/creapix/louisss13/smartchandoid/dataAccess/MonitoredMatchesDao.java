package eu.creapix.louisss13.smartchandoid.dataAccess;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.dataAccess.enums.Urls;
import eu.creapix.louisss13.smartchandoid.model.WebserviceListener;
import eu.creapix.louisss13.smartchandoid.model.daoInterfaces.MonitoredMatchesDataAccess;
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.MatchParser;
import eu.creapix.louisss13.smartchandoid.utils.Constants;

/**
 * Created by arnau on 30-12-17.
 */

public class MonitoredMatchesDao implements MonitoredMatchesDataAccess {

    private static final String TAG = "MonitoredMatchesDao";

    private ApiService apiService;
    private Gson gson;
    private HTTPJsonHandler datahandler = new HTTPJsonHandler();

    public MonitoredMatchesDao() {
        apiService = new ApiService();
        gson = new Gson();
    }

    @Override
    public void getMonitoredMatches(WebserviceListener webserviceListener, String token) throws IOException, JSONException {

        HttpURLConnection connection = datahandler.GetHTTPData(Urls.Monitoring, token);

        if (connection.getResponseCode() == 200) {

            String stream = datahandler.StreamToJson(connection.getInputStream());


            Type matchType = new TypeToken<ArrayList<MatchParser>>() {
            }.getType();
            ArrayList<Object> monitoredMatches = gson.fromJson(stream, matchType);
            webserviceListener.onWebserviceFinishWithSuccess(Constants.GET_MONITORED_MATCHES, 0, monitoredMatches);
        } else {
            webserviceListener.onWebserviceFinishWithError(connection.getResponseCode() + " - " + connection.getResponseMessage(), connection.getResponseCode());
        }
    }
}
