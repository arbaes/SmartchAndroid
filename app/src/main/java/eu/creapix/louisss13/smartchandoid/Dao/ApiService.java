package eu.creapix.louisss13.smartchandoid.Dao;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Dictionary;
import java.util.HashMap;

import eu.creapix.louisss13.smartchandoid.Dao.enums.*;

public class ApiService  {
    private String Token = null;
    private URL loginUrl;
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
            loginUrl = new URL("http://smartch.azurewebsites.net/users");

        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        urls.put(Urls.Users, loginUrl );
    }
    public HttpURLConnection getUrlConnection(Urls urlE, RequestMethods method){
        URL url = urls.get(urlE);
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection)url.openConnection();

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
