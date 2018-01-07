package eu.creapix.louisss13.smartchandoid.dataAccess;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;

import eu.creapix.louisss13.smartchandoid.dataAccess.daomodel.ConnexionDaoModel;
import eu.creapix.louisss13.smartchandoid.dataAccess.daomodel.RegisterDaoModel;
import eu.creapix.louisss13.smartchandoid.dataAccess.enums.Urls;
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.TokenParser;
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

    public boolean login(WebserviceListener webserviceListener, Context context, String mEmail, String mPassword) throws IOException, JSONException {
        ConnexionDaoModel connexionDaoModel = new ConnexionDaoModel(mEmail, mPassword);
        HttpURLConnection connection = datahandler.PostHTTPData(Urls.Login, connexionDaoModel, null);

        if (connection.getResponseCode() == 200) {
            Log.e(TAG, "Connexion OK");
            String stream = datahandler.StreamToJson(connection.getInputStream());

            Type tokenType = new TypeToken<TokenParser>(){}.getType();
            TokenParser tokenParserData  = gson.fromJson(stream, tokenType);

            PreferencesUtils.saveToken(context, tokenParserData.getAccessToken());
            PreferencesUtils.saveTokenExpiration(context, tokenParserData.getExpiresIn());
            Log.i("TOKEN", "access token: " + tokenParserData.getAccessToken());
            Log.i("TOKEN", "Expires in : " + tokenParserData.getExpiresIn() + "s");
            webserviceListener.onWebserviceFinishWithSuccess(Constants.ATTEMPT_LOGIN, null, null);
            return true;
        } else {
            Log.e(TAG, "Connexion NOT OK : " + connection.getResponseCode());
            webserviceListener.onWebserviceFinishWithError(connection.getResponseMessage(),connection.getResponseCode());
            return false;
        }

    }

    public boolean register(WebserviceListener webserviceListener, String mEmail, String mPassword) throws IOException {

        RegisterDaoModel registerDaoModel = new RegisterDaoModel(mEmail, mPassword);
        HttpURLConnection connection = datahandler.PostHTTPData(Urls.Account, registerDaoModel, null);

        if ((connection.getResponseCode() < 300) && (connection.getResponseCode() >= 200)) {
            webserviceListener.onWebserviceFinishWithSuccess(Constants.ATTEMPT_REGISTER, null, null);
            Log.e(TAG, "Inscription OK");
            return true;
        } else {
            webserviceListener.onWebserviceFinishWithError(connection.getResponseMessage(),connection.getResponseCode());
            Log.e(TAG, "Inscription NOT OK : " + connection.getResponseCode());
            return false;
        }
    }
}
