package eu.creapix.louisss13.smartchandoid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by arnau on 28-12-17.
 */

public class Utils {

    public static boolean hasConnexion(Context context) {
        if (context != null) {
            ConnectivityManager managerConn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (managerConn != null) {
                NetworkInfo activeNetwork = managerConn.getActiveNetworkInfo();
                return activeNetwork != null && activeNetwork.isConnected();
            }
        }

        return hostAvailable("www.google.com", 80);
    }

    public static boolean hostAvailable(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 2000);
            return true;
        } catch (IOException e) {
            // Either we have a timeout or unreachable host or failed DNS lookup
            e.printStackTrace();
            return false;
        }
    }

}
