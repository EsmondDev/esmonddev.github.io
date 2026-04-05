package com.w3code.library.theme;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;

public class ThemePreference {

    public static SharedPreferences mPreferences;
    public static final String PREF_NIGHT_MODE = "night_mode";
    public static SharedPreferences getInstance(Context context) {
        if (mPreferences == null) {
            mPreferences = context.getApplicationContext()
                    .getSharedPreferences("wall_data", Context.MODE_PRIVATE);
        }
        return mPreferences;
    }
    public static int getInt(Context context, String key, int defaultValue) {
        return getInstance(context).getInt(key, defaultValue);
    }
    public static void setInt(Context context, String key, int value) {
        getInstance(context).edit().putInt(key, value).apply();
    }
    public static int getAppNightDayMode(Context context) {
        return getInt(context, PREF_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_YES);
    }
}