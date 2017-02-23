package com.test.githubsearch.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Vaibhav Bhandula on 23-02-2017.
 */

public class PreferenceManager {

    private static final String APPLICATION_SETTINGS = "application_settings";

    private static SharedPreferences prefs;

    public static void initialize(Context context) {
        prefs = context.getSharedPreferences(APPLICATION_SETTINGS, 0);
    }

    private static SharedPreferences getPreferences() {
        return prefs;
    }

    private static SharedPreferences.Editor getEditor() {
        return getPreferences().edit();
    }

    public static String getString(String key, String defValue) {
        return prefs.getString(key, defValue);
    }

    public static void putString(String key, String value) {
        getEditor().putString(key, value).commit();
    }
}
