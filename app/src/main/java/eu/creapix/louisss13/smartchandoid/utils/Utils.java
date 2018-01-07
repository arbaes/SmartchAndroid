package eu.creapix.louisss13.smartchandoid.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import eu.creapix.louisss13.smartchandoid.R;
import eu.creapix.louisss13.smartchandoid.conroller.activities.LoginActivity;

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

        return hostAvailable(Constants.HOST_URL, 80);
    }

    public static boolean hostAvailable(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 2000);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void alertError(Context context, String title, String content) {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(content).setCancelable(true).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void alertSessionExpired(final Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

        // set title
        alertDialogBuilder.setTitle(R.string.title_session_expired);

        // set dialog message
        alertDialogBuilder.setMessage(R.string.content_session_expired).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(activity, LoginActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
    public static boolean[] PasswordValidity(String password) {

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
