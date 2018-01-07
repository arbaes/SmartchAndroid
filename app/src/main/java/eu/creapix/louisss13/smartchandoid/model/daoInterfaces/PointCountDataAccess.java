package eu.creapix.louisss13.smartchandoid.model.daoInterfaces;

import java.io.IOException;

import eu.creapix.louisss13.smartchandoid.model.WebserviceListener;
import eu.creapix.louisss13.smartchandoid.dataAccess.enums.RequestMethods;

/**
 * Created by Arnaud Baes on 07-01-18.
 * IG-3C 2017 - 2018
 */

public interface PointCountDataAccess {
    void postPoint(WebserviceListener webserviceListener, int scoredBy, int matchId, String token, RequestMethods requestMethod) throws IOException;
}
