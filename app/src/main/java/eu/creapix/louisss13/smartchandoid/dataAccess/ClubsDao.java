package eu.creapix.louisss13.smartchandoid.dataAccess;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.dataAccess.enums.RequestMethods;
import eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers.ClubParser;
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


        String urlString = Constants.BASE_URL_GET_CLUB_BY_USERID + userId;

        URL url = new URL(urlString);

        HttpURLConnection connection = apiService.getCustomUrlConnection(url, RequestMethods.GET, token);

        if (connection.getResponseCode() == 200) {
            Log.e(TAG, "Connexion OK - " + connection.getResponseCode());

            String stream = datahandler.StreamToJson(connection.getInputStream());

            Log.e("JSON", "" + stream);
            //Type tournamentListType = new TypeToken<ArrayList<TournamentParser>>(){}.getType();
            //ArrayList<Object> tournaments = gson.fromJson(stream, tournamentListType);
            ArrayList<Object> datas = new ArrayList<>();
            if (Integer.parseInt(userId) == 3) {
                datas.add(new ClubParser("Club 1 for id 3"));
                datas.add(new ClubParser("Club 2 for id 3"));
                datas.add(new ClubParser("Club 3 for id 3"));
                datas.add(new ClubParser("Club 4 for id 3"));
                datas.add(new ClubParser("Club 5 for id 3"));
            } else if (Integer.parseInt(userId) == 4)
                datas.add(new ClubParser("Club for id 4"));
            else if (Integer.parseInt(userId) == 5)
                datas.add(new ClubParser("Club for id 5"));
            webserviceListener.onWebserviceFinishWithSuccess(Constants.GET_CLUBS, userId, datas);
        } else {
            Log.e(TAG, "Connexion NOT OK : " + connection.getResponseCode());
            webserviceListener.onWebserviceFinishWithError(connection.getResponseCode() + " - " + connection.getResponseMessage());
        }
    }
}
