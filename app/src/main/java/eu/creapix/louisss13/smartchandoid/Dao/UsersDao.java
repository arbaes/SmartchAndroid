package eu.creapix.louisss13.smartchandoid.Dao;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import eu.creapix.louisss13.smartchandoid.Dao.Model.ConnexionDaoModel;
import eu.creapix.louisss13.smartchandoid.Dao.enums.RequestMethods;
import eu.creapix.louisss13.smartchandoid.Dao.enums.Urls;
import com.google.gson.*;

/**
 * Created by Louisss13 on 18-12-17.
 */

public class UsersDao {
    private static final String TAG = "UsersDao";

    private ApiService apiService;
    private Gson gson ;

    public UsersDao(){
        apiService = new ApiService();
        gson = new Gson();
    }

    public boolean login(String mEmail, String mPassword) throws IOException {
        ConnexionDaoModel connexionDaoModel = new ConnexionDaoModel(mEmail, mPassword);
        HttpURLConnection connection = apiService.getUrlConnection(Urls.Login, RequestMethods.POST);
        OutputStream outputStream = connection.getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        String stringObject = gson.toJson(connexionDaoModel);
        connection.connect();
        outputStreamWriter.write(stringObject);
        outputStreamWriter.flush();
        outputStreamWriter.close();
        if(connection.getResponseCode()== 200){
            Log.e(TAG,"Connexion OK");
            return true;
        }
        else{
            Log.e(TAG,"Connexion NOT OK : "+connection.getResponseCode());
            return false;
        }

    }
}