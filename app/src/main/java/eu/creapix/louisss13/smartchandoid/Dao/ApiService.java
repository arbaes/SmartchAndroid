package eu.creapix.louisss13.smartchandoid.Dao;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Dictionary;
import java.util.HashMap;

import eu.creapix.louisss13.smartchandoid.Dao.enums.*;

public class ApiService  {
    private static final String TAG = "ApiService";
    private String Token = null;
    private URL loginUrl;
    private URL usersUrl;
    private URL accountUrl;
    private URL monitorUrl;
    private HashMap<RequestMethods, String> methods;
    private HashMap<Urls, URL> urls;
    public ApiService() {
        methods = new HashMap<>();
        methods.put(RequestMethods.POST, "POST");
        methods.put(RequestMethods.DELETE, "DELETE");
        methods.put(RequestMethods.GET, "GET");
        methods.put(RequestMethods.PUT, "PUT");

        urls = new HashMap<>();

        try {
            usersUrl = new URL("https://smartch.azurewebsites.net/api/users");
            loginUrl = new URL("https://smartch.azurewebsites.net/api/jwt");
            accountUrl = new URL("https://smartch.azurewebsites.net/api/account");
            monitorUrl = new URL("https://smartch.azurewebsites.net/api/matchs/arbitrage");

        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        urls.put(Urls.Login, loginUrl );
        urls.put(Urls.Users, usersUrl );
        urls.put(Urls.Account, accountUrl );
    }

    public HttpURLConnection getUrlConnection(Urls urlE, RequestMethods method){
        URL url = urls.get(urlE);
        Log.e(TAG, "URL : "+urls.get(urlE).toString());
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestProperty("Content-type", "application/json");
            urlConnection.setRequestMethod(methods.get(method));
            if(Token != null)
                urlConnection.setRequestProperty("Authorization", "Bearer : " + Token);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return urlConnection;
    }
    public void setToken(String Token){
        this.Token = Token;
    }


}
