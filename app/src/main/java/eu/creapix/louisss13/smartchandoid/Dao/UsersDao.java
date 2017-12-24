package eu.creapix.louisss13.smartchandoid.Dao;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import eu.creapix.louisss13.smartchandoid.Dao.Model.*;
import eu.creapix.louisss13.smartchandoid.Dao.enums.RequestMethods;
import eu.creapix.louisss13.smartchandoid.Dao.enums.Urls;
import eu.creapix.louisss13.smartchandoid.Model.Token;

import com.google.gson.*;

import org.json.JSONException;

/**
 * Created by Louisss13 on 18-12-17.
 */

public class UsersDao {
    private static final String TAG = "UsersDao";

    private ApiService apiService;
    private Gson gson ;
    private HTTPJsonHandler datahandler = new HTTPJsonHandler();

    public UsersDao(){
        apiService = new ApiService();
        gson = new Gson();
    }

    public boolean login(String mEmail, String mPassword) throws IOException, JSONException {
        ConnexionDaoModel connexionDaoModel = new ConnexionDaoModel(mEmail, mPassword);
        HttpURLConnection connection = datahandler.PostHTTPData(Urls.Login, connexionDaoModel);

        if(connection.getResponseCode()== 200){
            Log.e(TAG,"Connexion OK");
            Token tokenData = (Token) datahandler.ExtractHTTPData(connection.getInputStream(), Token.class);

            Log.i("TOKEN", tokenData.getAccess_token());
            Log.i("TOKEN", "Expires in : " + tokenData.getExpires_in() + "s");
            return true;
        }
        else{
            Log.e(TAG,"Connexion NOT OK : "+connection.getResponseCode());
            return false;
        }

    }
    public boolean register(String mEmail, String mPassword) throws IOException {

        RegisterDaoModel registerDaoModel = new RegisterDaoModel(mEmail, mPassword);
        HttpURLConnection connection = datahandler.PostHTTPData(Urls.Account, registerDaoModel);

        if((connection.getResponseCode() < 300) && (connection.getResponseCode() >= 200)){
            Log.e(TAG,"Inscription OK");
            return true;
        }
        else{
            Log.e(TAG,"Inscription NOT OK : "+connection.getResponseCode());
            return false;
        }
    }
}
