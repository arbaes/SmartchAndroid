package eu.creapix.louisss13.smartchandoid.dataAccess;

import java.util.ArrayList;

/**
 * Created by arnau on 28-12-17.
 */

public interface WebserviceListener {

    void onWebserviceFinishWithSuccess(String method, Integer id, ArrayList<Object> datas);

    void onWebserviceFinishWithError(String error, int errorCode);
}
