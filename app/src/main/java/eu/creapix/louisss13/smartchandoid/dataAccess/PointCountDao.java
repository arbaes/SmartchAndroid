package eu.creapix.louisss13.smartchandoid.dataAccess;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.dataAccess.daomodel.PointDaoModel;
import eu.creapix.louisss13.smartchandoid.dataAccess.enums.RequestMethods;
import eu.creapix.louisss13.smartchandoid.utils.Constants;

/**
 * Created by arnau on 04-01-18.
 */


public class PointCountDao {

    private ApiService apiService;
    private Gson gson;
    private HTTPJsonHandler datahandler = new HTTPJsonHandler();
    private String TAG = "PointDao";

    private static final String pointBaseUrl = "/api/matchs/{id}/point/";

    public PointCountDao() {
        apiService = new ApiService();
        gson = new Gson();
    }

    public void postPoint(WebserviceListener webserviceListener, int scoredBy, int matchId, String token, RequestMethods requestMethod) throws IOException {


        Boolean player = false;
        switch (scoredBy){
            case 0 :
                player = false;
                break;
            case 1 :
                player = true;
                break;
        }

        PointDaoModel pointDaoModel = new PointDaoModel(player);
        String urlString ="";
        switch (requestMethod){
            case POST:
                urlString = Constants.BASE_URL_COUNT_POINT + matchId + "/point";
                break;
            case DELETE:
                urlString = Constants.BASE_URL_COUNT_POINT + matchId + "/point/" + scoredBy;
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

        if (urlConnection.getResponseCode() == 200) {
            Log.e(TAG, "SENDPOINT OK - " + urlConnection.getResponseCode());
            ArrayList<Object> scoredBys = new ArrayList<Object>();
            scoredBys.add(scoredBy);
            switch (requestMethod){
                case POST:
                    webserviceListener.onWebserviceFinishWithSuccess(Constants.POST_POINT, scoredBys);
                    break;
                case DELETE:
                    webserviceListener.onWebserviceFinishWithSuccess(Constants.DELETE_POINT, scoredBys);
                    break;
            }


        } else {
            Log.e(TAG, "SENDPOINT NOT OK : " + urlConnection.getResponseCode());
            webserviceListener.onWebserviceFinishWithError(urlConnection.getResponseCode() +" - " + urlConnection.getResponseMessage());

        }
    }
}
