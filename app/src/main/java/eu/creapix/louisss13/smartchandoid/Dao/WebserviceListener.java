package eu.creapix.louisss13.smartchandoid.Dao;

/**
 * Created by arnau on 28-12-17.
 */

public interface WebserviceListener {

    void onWebserviceFinishWithSuccess(String method, Object data);

    void onWebserviceFinishWithError(String error);
}
