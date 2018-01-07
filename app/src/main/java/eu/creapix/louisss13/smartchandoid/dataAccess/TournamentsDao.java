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
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.TournamentParser;
import eu.creapix.louisss13.smartchandoid.utils.Constants;

/**
 * Created by Arnaud Baes on 30-12-17.
 * IG-3C 2017 - 2018
 */

public class TournamentsDao {

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

            String stream = datahandler.StreamToJson(connection.getInputStream());

            Type tournamentListType = new TypeToken<ArrayList<TournamentParser>>() {
            }.getType();
            ArrayList<Object> tournaments = gson.fromJson(stream, tournamentListType);
            webserviceListener.onWebserviceFinishWithSuccess(Constants.GET_TOURNAMENT, 0, tournaments);
        } else {
            webserviceListener.onWebserviceFinishWithError(connection.getResponseCode() + " - " + connection.getResponseMessage(), connection.getResponseCode());
        }
    }

}
