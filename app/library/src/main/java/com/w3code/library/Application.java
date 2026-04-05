package com.w3code.library;

import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.w3code.library.theme.ThemePreference;

public class Application extends MultiDexApplication {
    @Override
    public void onCreate() {
        AppCompatDelegate.setDefaultNightMode(ThemePreference.getAppNightDayMode(this));
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        super.onCreate();
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}