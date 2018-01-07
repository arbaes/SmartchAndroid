package eu.creapix.louisss13.smartchandoid.dataAccess;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;

import eu.creapix.louisss13.smartchandoid.dataAccess.daomodel.ConnexionDaoModel;
import eu.creapix.louisss13.smartchandoid.dataAccess.daomodel.RegisterDaoModel;
import eu.creapix.louisss13.smartchandoid.dataAccess.enums.Urls;
import eu.creapix.louisss13.smartchandoid.model.WebserviceListener;
import eu.creapix.louisss13.smartchandoid.model.daoInterfaces.UsersDataAccess;
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.TokenParser;
import eu.creapix.louisss13.smartchandoid.utils.Constants;
import eu.creapix.louisss13.smartchandoid.utils.PreferencesUtils;

/**
 * Created by Louisss13 on 18-12-17.
 */

public class UsersDao implements UsersDataAccess {


    private ApiService apiService;
    private Gson gson;
    private HTTPJsonHandler datahandler = new HTTPJsonHandler();

    public UsersDao() {
        apiService = new ApiService();
        gson = new Gson();
    }

    @Override
    public boolean login(WebserviceListener webserviceListener, Context context, String mEmail, String mPassword) throws IOException, JSONException {
        ConnexionDaoModel connexionDaoModel = new ConnexionDaoModel(mEmail, mPassword);
        HttpURLConnection connection = datahandler.PostHTTPData(Urls.Login, connexionDaoModel, null);

        if (connection.getResponseCode() == 200) {
            String stream = datahandler.StreamToJson(connection.getInputStream());

            Type tokenType = new TypeToken<TokenParser>() {
            }.getType();
            TokenParser tokenParserData = gson.fromJson(stream, tokenType);

            PreferencesUtils.saveToken(context, tokenParserData.getAccessToken());
            PreferencesUtils.saveTokenExpiration(context, tokenParserData.getExpiresIn());
            webserviceListener.onWebserviceFinishWithSuccess(Constants.ATTEMPT_LOGIN, null, null);
            return true;
        } else {
            webserviceListener.onWebserviceFinishWithError(connection.getResponseMessage(), connection.getResponseCode());
            return false;
        }

    }

    @Override
    public boolean register(WebserviceListener webserviceListener, String mEmail, String mPassword) throws IOException {

        RegisterDaoModel registerDaoModel = new RegisterDaoModel(mEmail, mPassword);
        HttpURLConnection connection = datahandler.PostHTTPData(Urls.Account, registerDaoModel, null);

        if ((connection.getResponseCode() < 300) && (connection.getResponseCode() >= 200)) {
            webserviceListener.onWebserviceFinishWithSuccess(Constants.ATTEMPT_REGISTER, null, null);
            return true;
        } else {
            webserviceListener.onWebserviceFinishWithError(connection.getResponseMessage(), connection.getResponseCode());
            return false;
        }
    }
}
