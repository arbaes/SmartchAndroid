package eu.creapix.louisss13.smartchandoid.dataAccess;

import com.google.gson.Gson;

import eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers.PointParser;
import eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers.TokenParser;

/**
 * Created by arnau on 04-01-18.
 */


public class PointCountDao {

    private ApiService apiService;
    private Gson gson;
    private HTTPJsonHandler datahandler = new HTTPJsonHandler();

    private static final String pointBaseUrl = "/api/matchs/{id}/point/";

    public PointCountDao() {
        apiService = new ApiService();
        gson = new Gson();
    }

    public void postPoint(PointParser point, TokenParser token){
        //TODO - Ecrire la fonction selon l'api
    }
}
