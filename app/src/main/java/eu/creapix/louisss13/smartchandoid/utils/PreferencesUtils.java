package eu.creapix.louisss13.smartchandoid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by arnau on 30-12-17.
 */

public class PreferencesUtils {

    private static final String TOKEN = "token";
    private static final String TOKEN_EXPIRATION = "token_expiration";
    private static final String EMAIL = "email";
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";

    public static void saveToken(Context context, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TOKEN, value);
        editor.apply();
    }

    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(TOKEN, null);
    }

    public static void saveTokenExpiration(Context context, int value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(TOKEN_EXPIRATION, value);
        editor.apply();
    }

    public static int getTokenExpiration(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(TOKEN_EXPIRATION, 0);
    }

    public static void saveEmail(Context context, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(EMAIL, value);
        editor.apply();
    }

    public static String getEmail(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(EMAIL, null);
    }

    public static void saveFirstName(Context context, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(FIRSTNAME, value);
        editor.apply();
    }

    public static String getFirstName(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(FIRSTNAME, null);
    }

    public static void saveLastName(Context context, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(LASTNAME, value);
        editor.apply();
    }

    public static String getLastname(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(LASTNAME, null);
    }
}
