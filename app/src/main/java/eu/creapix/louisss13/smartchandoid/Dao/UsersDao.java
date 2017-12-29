package eu.creapix.louisss13.smartchandoid.Dao;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;

import eu.creapix.louisss13.smartchandoid.Dao.Model.ConnexionDaoModel;
import eu.creapix.louisss13.smartchandoid.Dao.Model.RegisterDaoModel;
import eu.creapix.louisss13.smartchandoid.Dao.enums.Urls;
import eu.creapix.louisss13.smartchandoid.Model.Token;
import eu.creapix.louisss13.smartchandoid.utils.Constants;

/**
 * Created by Louisss13 on 18-12-17.
 */

public class UsersDao {
    private static final String TAG = "UsersDao";

    private ApiService apiService;
    private Gson gson;
    private HTTPJsonHandler datahandler = new HTTPJsonHandler();

    public UsersDao() {
        apiService = new ApiService();
        gson = new Gson();
    }

    public boolean login(String mEmail, String mPassword) throws IOException, JSONException {
        ConnexionDaoModel connexionDaoModel = new ConnexionDaoModel(mEmail, mPassword);
        HttpURLConnection connection = datahandler.PostHTTPData(Urls.Login, connexionDaoModel);

        if (connection.getResponseCode() == 200) {
            Log.e(TAG, "Connexion OK");
            Token tokenData = (Token) datahandler.extractHTTPData(connection.getInputStream(), Token.class);

            Log.i("TOKEN", "access token: " + tokenData.getAccessToken());
            Log.i("TOKEN", "Expires in : " + tokenData.getExpiresIn() + "s");
            return true;
        } else {
            Log.e(TAG, "Connexion NOT OK : " + connection.getResponseCode());
            return false;
        }

    }

    public void getScores(WebserviceListener webserviceListener, String token) throws IOException {
        ConnexionDaoModel registerDaoModel = new ConnexionDaoModel("arnaud.baes@hotmail.com", "Coucou-123");
        HttpURLConnection connection = datahandler.PostHTTPData(Urls.Login, registerDaoModel);

        if ((connection.getResponseCode() < 300) && (connection.getResponseCode() >= 200)) {
            Log.e(TAG, "Inscription OK");
            // TODO parse response and get JSON
            webserviceListener.onWebserviceFinishWithSuccess(Constants.GET_SCORE, null);
        } else {
            Log.e(TAG, "Inscription NOT OK : " + connection.getResponseCode());
            webserviceListener.onWebserviceFinishWithError(String.valueOf(connection.getResponseCode()));
        }
    }

    public boolean register(String mEmail, String mPassword) throws IOException {

        RegisterDaoModel registerDaoModel = new RegisterDaoModel(mEmail, mPassword);
        HttpURLConnection connection = datahandler.PostHTTPData(Urls.Account, registerDaoModel);

        if ((connection.getResponseCode() < 300) && (connection.getResponseCode() >= 200)) {
            Log.e(TAG, "Inscription OK");
            return true;
        } else {
            Log.e(TAG, "Inscription NOT OK : " + connection.getResponseCode());
            return false;
        }
    }
}
