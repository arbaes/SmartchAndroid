package eu.creapix.louisss13.smartchandoid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

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

    public static boolean[] PasswordValidity(String password) {
        int errorCode = 0;
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasNonAlphanumeric = false;
        int nbDistinctChar = 0;

        boolean[] validity = new boolean[5];

        boolean[] charPresent = new boolean[Character.MAX_VALUE];
        for (int i = 0; i < password.length(); i++) {
            charPresent[password.charAt(i)] = true;
        }


        for (int i = 0; i < password.length(); i++) {

            if (charPresent[password.charAt(i)] == true) {
                nbDistinctChar++;
            }

            char c = password.charAt(i);

            if (!hasNonAlphanumeric && !Character.isLetterOrDigit(c)) {
                hasNonAlphanumeric = true;
            }
            if (!hasDigit && Character.isDigit(c)) {
                    hasDigit = true;
            }
            if (!hasUppercase || !hasLowercase) {
                if (Character.isUpperCase(c)) {
                    hasUppercase = true;
                } else {
                    hasLowercase = true;
                }
            }

        }
        validity[Constants.HAS_SPECIAL_CHAR] = hasNonAlphanumeric;
        validity[Constants.HAS_LOWER_CASE] = hasLowercase;
        validity[Constants.HAS_UPPER_CASE] = hasUppercase;
        validity[Constants.HAS_DIGIT] = hasDigit;

        validity[Constants.HAS_ENOUGH_UNIQUE_CHAR] = nbDistinctChar >= Constants.MIN_UNIQUE_CHAR_REQUIRED;

        return validity;
    }

}
