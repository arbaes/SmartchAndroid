package eu.creapix.louisss13.smartchandoid.model.daoInterfaces;

import android.content.Context;

import org.json.JSONException;

import java.io.IOException;

import eu.creapix.louisss13.smartchandoid.model.WebserviceListener;

/**
 * Created by Arnaud Baes on 07-01-18.
 * IG-3C 2017 - 2018
 */

public interface UsersDataAccess {
    boolean login(WebserviceListener webserviceListener, Context context, String mEmail, String mPassword) throws IOException, JSONException;

    boolean register(WebserviceListener webserviceListener, String mEmail, String mPassword) throws IOException;
}
