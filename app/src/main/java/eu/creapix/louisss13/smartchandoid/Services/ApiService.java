package eu.creapix.louisss13.smartchandoid.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ApiService extends Service {
    private String Token = null;
    public ApiService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
