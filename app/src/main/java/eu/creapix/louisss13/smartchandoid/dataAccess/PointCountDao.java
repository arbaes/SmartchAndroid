package eu.creapix.louisss13.smartchandoid.dataAccess;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.dataAccess.daomodel.PointDaoModel;
import eu.creapix.louisss13.smartchandoid.dataAccess.enums.RequestMethods;
import eu.creapix.louisss13.smartchandoid.model.WebserviceListener;
import eu.creapix.louisss13.smartchandoid.model.daoInterfaces.PointCountDataAccess;
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.ScoreCalculatedParser;
import eu.creapix.louisss13.smartchandoid.utils.Constants;

/**
 * Created by arnau on 04-01-18.
 */


public class PointCountDao implements PointCountDataAccess {

    private ApiService apiService;
    private Gson gson;
    private HTTPJsonHandler datahandler = new HTTPJsonHandler();
    private String TAG = "PointDao";


    public PointCountDao() {
        apiService = new ApiService();
        gson = new Gson();
    }

    @Override
    public void postPoint(WebserviceListener webserviceListener, int scoredBy, int matchId, String token, RequestMethods requestMethod) throws IOException {
        Boolean player = false;
        switch (scoredBy) {
            case 0:
                player = false;
                break;
            case 1:
                player = true;
                break;
        }

        PointDaoModel pointDaoModel = new PointDaoModel(player);
        String urlString = "";
        switch (requestMethod) {
            case POST:
                urlString = Constants.BASE_URL_COUNT_POINT + "/" + matchId + "/" + Constants.URL_DIRECTORY_POINT;
                break;
            case DELETE:
                urlString = Constants.BASE_URL_COUNT_POINT + "/" + matchId + "/" + Constants.URL_DIRECTORY_POINT + "/" + scoredBy;
                break;
        }


        URL url = new URL(urlString);
        HttpURLConnection urlConnection = apiService.getCustomUrlConnection(url, requestMethod, token);

        try {

            OutputStream outputStream = urlConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            String stringObject = gson.toJson(pointDaoModel);
            urlConnection.connect();
            outputStreamWriter.write(stringObject);
            outputStreamWriter.flush();
            outputStreamWriter.close();


        } catch (IOException e1) {
            e1.printStackTrace();
        }

        if ((urlConnection.getResponseCode() >= 200) && (urlConnection.getResponseCode() < 300)) {
            Log.e(TAG, "SENDPOINT OK - " + urlConnection.getResponseCode());
            String stream = datahandler.StreamToJson(urlConnection.getInputStream());
            Log.e(TAG, "JSON Response Content : " + stream);

            Type scoreCalculatedType = new TypeToken<ScoreCalculatedParser>() {
            }.getType();
            ScoreCalculatedParser sets = gson.fromJson(stream, scoreCalculatedType);
            ArrayList<Object> scores = new ArrayList<>();
            scores.add(sets);


            switch (requestMethod) {
                case POST:
                    webserviceListener.onWebserviceFinishWithSuccess(Constants.POST_POINT, scoredBy, scores);
                    break;
                case DELETE:
                    webserviceListener.onWebserviceFinishWithSuccess(Constants.DELETE_POINT, scoredBy, scores);
                    break;
            }


        } else {
            Log.e(TAG, "SENDPOINT NOT OK : " + urlConnection.getResponseCode());
            webserviceListener.onWebserviceFinishWithError(urlConnection.getResponseCode() + " - " + urlConnection.getResponseMessage(), urlConnection.getResponseCode());

        }
    }
}
