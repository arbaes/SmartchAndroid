package eu.creapix.louisss13.smartchandoid.model.daoInterfaces;

import org.json.JSONException;

import java.io.IOException;

import eu.creapix.louisss13.smartchandoid.model.WebserviceListener;

/**
 * Created by Arnaud Baes on 07-01-18.
 * IG-3C 2017 - 2018
 */

public interface ClubsDataAccess {

    void getClubs(WebserviceListener webserviceListener, int userId, String token) throws IOException, JSONException;

}
