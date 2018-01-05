package eu.creapix.louisss13.smartchandoid.dataAccess;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.dataAccess.daomodel.PointDaoModel;
import eu.creapix.louisss13.smartchandoid.dataAccess.enums.RequestMethods;
import eu.creapix.louisss13.smartchandoid.dataAccess.enums.Urls;
import eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers.TournamentParser;
import eu.creapix.louisss13.smartchandoid.utils.Constants;

/**
 * Created by arnau on 05-01-18.
 */

public class ClubsDao {

    private static final String TAG = "TournamentsDao";

    private ApiService apiService;
    private Gson gson;
    private HTTPJsonHandler datahandler = new HTTPJsonHandler();

    public ClubsDao() {
        apiService = new ApiService();
        gson = new Gson();
    }

    public void getClubs(WebserviceListener webserviceListener, String userId, String token) throws IOException, JSONException {



        String urlString = Constants.BASE_URL_GET_CLUB_BY_USERID+ userId ;

        URL url = new URL(urlString);

        HttpURLConnection connection = apiService.getCustomUrlConnection(url, RequestMethods.GET, token);

        if (connection.getResponseCode() == 200) {
            Log.e(TAG, "Connexion OK - " + connection.getResponseCode());

            String stream = datahandler.StreamToJson(connection.getInputStream());

            Log.e("JSON",""+stream);
            //Type tournamentListType = new TypeToken<ArrayList<TournamentParser>>(){}.getType();
            //ArrayList<Object> tournaments = gson.fromJson(stream, tournamentListType);
            webserviceListener.onWebserviceFinishWithSuccess(Constants.GET_CLUBS, null);
        } else {
            Log.e(TAG, "Connexion NOT OK : " + connection.getResponseCode());
            webserviceListener.onWebserviceFinishWithError(connection.getResponseCode() + " - " + connection.getResponseMessage());
        }
    }
}
