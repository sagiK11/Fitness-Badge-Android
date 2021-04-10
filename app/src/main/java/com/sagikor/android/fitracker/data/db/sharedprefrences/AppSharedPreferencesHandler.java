package com.sagikor.android.fitracker.data.db.sharedprefrences;


import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

public class AppSharedPreferencesHandler implements SharedPreferencesHandler {
    private final static AppSharedPreferencesHandler sharedPreferencesHandler = init();
    private static final String CLASSES_USER_TEACHES = "CLASSES_USER_TEACHES";
    private static final String TAG = "AppSharedPrefHandler";
    private SharedPreferences sharedPreferences;

    private static AppSharedPreferencesHandler init() {
        return new AppSharedPreferencesHandler();
    }

    public static AppSharedPreferencesHandler getInstance() {
        return sharedPreferencesHandler;
    }

    @Override
    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public boolean isGirlsSwitchOn() {
        return sharedPreferences.getBoolean("alwaysGirlsSwitch", false);

    }

    @Override
    public boolean isBoysSwitchOn() {
        return sharedPreferences.getBoolean("alwaysBoysSwitch", false);
    }

    @Override
    public void editGenderPreferences(String gender, boolean isChecked) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (gender.equals("Girls")) {
            editor.putBoolean("alwaysGirlsSwitch", isChecked);
        } else if (gender.equals("Boys")) {
            editor.putBoolean("alwaysBoysSwitch", isChecked);
        }
        editor.apply();
    }

}
