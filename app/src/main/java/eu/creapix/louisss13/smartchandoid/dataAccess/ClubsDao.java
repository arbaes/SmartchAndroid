package eu.creapix.louisss13.smartchandoid.dataAccess;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.dataAccess.enums.RequestMethods;
import eu.creapix.louisss13.smartchandoid.model.WebserviceListener;
import eu.creapix.louisss13.smartchandoid.model.daoInterfaces.ClubsDataAccess;
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.ClubParser;
import eu.creapix.louisss13.smartchandoid.utils.Constants;

/**
 * Created by arnau on 05-01-18.
 */

public class ClubsDao implements ClubsDataAccess {


    private ApiService apiService;
    private Gson gson;
    private HTTPJsonHandler datahandler = new HTTPJsonHandler();

    public ClubsDao() {
        apiService = new ApiService();
        gson = new Gson();
    }

    @Override
    public void getClubs(WebserviceListener webserviceListener, int userId, String token) throws IOException, JSONException {


        String urlString = Constants.BASE_URL_GET_CLUB_BY_USERID + userId;

        URL url = new URL(urlString);

        HttpURLConnection connection = apiService.getCustomUrlConnection(url, RequestMethods.GET, token);

        if (connection.getResponseCode() == 200) {

            String stream = datahandler.StreamToJson(connection.getInputStream());

            Type clubType = new TypeToken<ArrayList<ClubParser>>() {
            }.getType();
            ArrayList<Object> clubs = gson.fromJson(stream, clubType);

            webserviceListener.onWebserviceFinishWithSuccess(Constants.GET_CLUBS, userId, clubs);
        } else {
            webserviceListener.onWebserviceFinishWithError(connection.getResponseCode() + " - " + connection.getResponseMessage(), connection.getResponseCode());
        }
    }
}
