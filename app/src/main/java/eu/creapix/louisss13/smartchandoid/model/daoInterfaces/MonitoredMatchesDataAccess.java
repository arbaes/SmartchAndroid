package eu.creapix.louisss13.smartchandoid.model.daoInterfaces;

import org.json.JSONException;

import java.io.IOException;

import eu.creapix.louisss13.smartchandoid.model.WebserviceListener;

/**
 * Created by Arnaud Baes on 07-01-18.
 * IG-3C 2017 - 2018
 */

public interface MonitoredMatchesDataAccess {
    void getMonitoredMatches(WebserviceListener webserviceListener, String token) throws IOException, JSONException;
}
