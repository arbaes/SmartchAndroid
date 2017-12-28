package eu.creapix.louisss13.smartchandoid.Dao;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import eu.creapix.louisss13.smartchandoid.Dao.ApiService;
import eu.creapix.louisss13.smartchandoid.Dao.Model.*;
import eu.creapix.louisss13.smartchandoid.Dao.enums.RequestMethods;
import eu.creapix.louisss13.smartchandoid.Dao.enums.Urls;
import eu.creapix.louisss13.smartchandoid.Model.Token;

/**
 * Created by arnau on 23-12-17.
 */

public class HTTPJsonHandler {


    static String stream = null;
    private ApiService apiService;
    private Gson gson ;


    public HTTPJsonHandler(){
        apiService = new ApiService();
        gson = new Gson();
    }

    public JSONObject extractHTTPData(InputStream connectionInputStream, Class c){

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

        JSONObject extractedData = (JSONObject) gson.fromJson(stream, c);
        return extractedData;
    }

    public HttpURLConnection PostHTTPData(Urls url, Object DaoModel){

        HttpURLConnection urlConnection = apiService.getUrlConnection(url, RequestMethods.POST);

        try {

            OutputStream outputStream = urlConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            if (DaoModel != null){
                String stringObject = gson.toJson(DaoModel);
                urlConnection.connect();
                outputStreamWriter.write(stringObject);
                outputStreamWriter.flush();
                outputStreamWriter.close();
            }
            else {
                Log.e("MODEL", "DaoModel is " + DaoModel);
            }

        }catch(IOException e) {
            e.printStackTrace();
        }

        return urlConnection;
    }
}