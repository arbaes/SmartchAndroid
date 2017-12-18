package eu.creapix.louisss13.smartchandoid.Dao;

import java.io.IOException;
import java.net.HttpURLConnection;

import eu.creapix.louisss13.smartchandoid.Dao.enums.RequestMethods;
import eu.creapix.louisss13.smartchandoid.Dao.enums.Urls;

/**
 * Created by Louisss13 on 18-12-17.
 */

public class UsersDao {

    private ApiService apiService;

    public UsersDao(){
        apiService = new ApiService();
    }

    public boolean login(String mEmail, String mPassword) throws IOException {
        HttpURLConnection connection = apiService.getUrlConnection(Urls.Users, RequestMethods.POST);
        connection.connect();
        if(connection.getResponseCode()== 200){
            return true;
        }
        else{
            return false;
        }
    }
}
