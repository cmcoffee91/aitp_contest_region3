package com.usaa.acaitp.bashscriptcrazy.russhanneman;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by christophercoffee on 10/20/16.
 */

public class CM_prefs {

    public static void setPref(Context context, String key, String value)
    {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key,value).apply();
    }

    public static String getPref(Context context, String key)
    {
        String ret = "";
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key,null);
    }
}
