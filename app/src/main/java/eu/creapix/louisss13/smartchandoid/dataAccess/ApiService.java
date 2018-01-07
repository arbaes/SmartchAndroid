package eu.creapix.louisss13.smartchandoid.dataAccess;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import eu.creapix.louisss13.smartchandoid.dataAccess.enums.RequestMethods;
import eu.creapix.louisss13.smartchandoid.dataAccess.enums.Urls;

public class ApiService {

    private URL loginUrl;
    private URL usersUrl;
    private URL accountUrl;
    private URL monitorUrl;
    private URL tournamentUrl;
    private URL matchBaseUrl;

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
            usersUrl = new URL("http://smartch.azurewebsites.net/api/users");
            loginUrl = new URL("http://smartch.azurewebsites.net/api/jwt");
            accountUrl = new URL("http://smartch.azurewebsites.net/api/account");
            matchBaseUrl = new URL("http://smartch.azurewebsites.net/api/matchs/");
            monitorUrl = new URL("http://smartch.azurewebsites.net/api/matchs/arbitrage");
            tournamentUrl = new URL("http://smartch.azurewebsites.net/api/tournaments");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        urls.put(Urls.Login, loginUrl);
        urls.put(Urls.Users, usersUrl);
        urls.put(Urls.Account, accountUrl);
        urls.put(Urls.Tournaments, tournamentUrl);
        urls.put(Urls.Monitoring, monitorUrl);
        urls.put(Urls.MatchBase, matchBaseUrl);
    }

    public HttpURLConnection getUrlConnection(Urls urlE, RequestMethods method, String token) {

        URL url = urls.get(urlE);
        HttpURLConnection urlConnection = null;

        try {

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-type", "application/json");
            urlConnection.setRequestMethod(methods.get(method));
            if (token != null) {
                urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urlConnection;
    }

    public HttpURLConnection getCustomUrlConnection(URL url, RequestMethods method, String token) {

        HttpURLConnection urlConnection = null;

        try {

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-type", "application/json");
            urlConnection.setRequestMethod(methods.get(method));
            if (token != null) {
                urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urlConnection;
    }

}
