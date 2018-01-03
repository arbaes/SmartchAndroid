package eu.creapix.louisss13.smartchandoid.dataAccess;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import eu.creapix.louisss13.smartchandoid.dataAccess.daomodel.ConnexionDaoModel;
import eu.creapix.louisss13.smartchandoid.dataAccess.daomodel.RegisterDaoModel;
import eu.creapix.louisss13.smartchandoid.dataAccess.enums.Urls;
import eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers.TokenParser;
import eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers.TournamentParser;
import eu.creapix.louisss13.smartchandoid.model.Tournament;
import eu.creapix.louisss13.smartchandoid.utils.Constants;

/**
 * Created by arnau on 30-12-17.
 */

public class TournamentsDao {
    private static final String TAG = "TournamentsDao";

    private ApiService apiService;
    private Gson gson;
    private HTTPJsonHandler datahandler = new HTTPJsonHandler();

    public TournamentsDao() {
        apiService = new ApiService();
        gson = new Gson();
    }

    public void getTournaments(WebserviceListener webserviceListener, String token) throws IOException, JSONException {

        HttpURLConnection connection = datahandler.GetHTTPData(Urls.Tournaments, token);

        if (connection.getResponseCode() == 200) {
            Log.e(TAG, "Connexion OK - " + connection.getResponseCode());

            String stream = datahandler.StreamToJson(connection.getInputStream());

            Type tournamentListType = new TypeToken<ArrayList<TournamentParser>>(){}.getType();
            ArrayList<Object> tournaments = gson.fromJson(stream, tournamentListType);
            webserviceListener.onWebserviceFinishWithSuccess(Constants.GET_TOURNAMENT, tournaments);
        } else {
            Log.e(TAG, "Connexion NOT OK : " + connection.getResponseCode());
            webserviceListener.onWebserviceFinishWithError(connection.getResponseCode() + " - " + connection.getResponseMessage());
        }
    }

}