package eu.creapix.louisss13.smartchandoid.dataAccess;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;

import eu.creapix.louisss13.smartchandoid.dataAccess.daomodel.ConnexionDaoModel;
import eu.creapix.louisss13.smartchandoid.dataAccess.daomodel.RegisterDaoModel;
import eu.creapix.louisss13.smartchandoid.dataAccess.enums.Urls;
import eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers.TokenParser;
import eu.creapix.louisss13.smartchandoid.utils.Constants;
import eu.creapix.louisss13.smartchandoid.utils.PreferencesUtils;

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

    public boolean login(Context context, String mEmail, String mPassword) throws IOException, JSONException {
        ConnexionDaoModel connexionDaoModel = new ConnexionDaoModel(mEmail, mPassword);
        HttpURLConnection connection = datahandler.PostHTTPData(Urls.Login, connexionDaoModel, null);

        if (connection.getResponseCode() == 200) {
            Log.e(TAG, "Connexion OK");
            TokenParser tokenParserData = (TokenParser) datahandler.extractHTTPData(connection.getInputStream(), TokenParser.class);
            PreferencesUtils.saveToken(context, tokenParserData.getAccessToken());
            PreferencesUtils.saveTokenExpiration(context, tokenParserData.getExpiresIn());
            Log.i("TOKEN", "access token: " + tokenParserData.getAccessToken());
            Log.i("TOKEN", "Expires in : " + tokenParserData.getExpiresIn() + "s");
            return true;
        } else {
            Log.e(TAG, "Connexion NOT OK : " + connection.getResponseCode());
            return false;
        }

    }

    public void getScores(WebserviceListener webserviceListener, String token) throws IOException {
        ConnexionDaoModel registerDaoModel = new ConnexionDaoModel("arnaud.baes@hotmail.com", "Coucou-123");
        HttpURLConnection connection = datahandler.PostHTTPData(Urls.Login, registerDaoModel, null);

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
        HttpURLConnection connection = datahandler.PostHTTPData(Urls.Account, registerDaoModel, null);

        if ((connection.getResponseCode() < 300) && (connection.getResponseCode() >= 200)) {
            Log.e(TAG, "Inscription OK");
            return true;
        } else {
            Log.e(TAG, "Inscription NOT OK : " + connection.getResponseCode());
            return false;
        }
    }
}
