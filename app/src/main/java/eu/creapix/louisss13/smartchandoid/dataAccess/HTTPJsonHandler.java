package eu.creapix.louisss13.smartchandoid.dataAccess;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import eu.creapix.louisss13.smartchandoid.dataAccess.enums.RequestMethods;
import eu.creapix.louisss13.smartchandoid.dataAccess.enums.Urls;

/**
 * Created by arnau on 23-12-17.
 */

public class HTTPJsonHandler {


    static String stream = null;
    private ApiService apiService;
    private Gson gson;


    public HTTPJsonHandler() {
        apiService = new ApiService();
        gson = new Gson();
    }


    //Only works if no array
    public JSONObject extractHTTPData(InputStream connectionInputStream, Class c) {

        try {

            InputStream inStream = new BufferedInputStream(connectionInputStream);
            BufferedReader r = new BufferedReader(new InputStreamReader(inStream));
            r.close();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line);
            }
            stream = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject extractedData = (JSONObject) gson.fromJson(stream, c);
        return extractedData;
    }

    public String StreamToJson(InputStream connectionInputStream) {
        String jsonString = null;
        try {
            InputStream inStream = new BufferedInputStream(connectionInputStream);
            BufferedReader r = new BufferedReader(new InputStreamReader(inStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line);
            }
            jsonString = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    public List<JSONObject> extractHTTPDataArray(InputStream connectionInputStream, Type type) {

        try {

            InputStream inStream = new BufferedInputStream(connectionInputStream);
            BufferedReader r = new BufferedReader(new InputStreamReader(inStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line);
            }
            stream = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }


        List<JSONObject> extractedData = gson.fromJson(stream, type);
        return extractedData;
    }

    public HttpURLConnection PostHTTPData(Urls url, Object DaoModel, String token) {

        HttpURLConnection urlConnection = apiService.getUrlConnection(url, RequestMethods.POST, token);

        try {

            OutputStream outputStream = urlConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            if (DaoModel != null) {
                String stringObject = gson.toJson(DaoModel);
                urlConnection.connect();
                outputStreamWriter.write(stringObject);
                outputStreamWriter.flush();
                outputStreamWriter.close();
            } else {
                Log.e("MODEL", "DaoModel is " + DaoModel);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlConnection;
    }

    public HttpURLConnection GetHTTPData(Urls url, String token) {

        HttpURLConnection urlConnection = apiService.getUrlConnection(url, RequestMethods.GET, token);
        Log.e("URL", "" + url.toString());
        Log.e("TOKEN", "" + token);

        try {
            urlConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlConnection;

    }
}