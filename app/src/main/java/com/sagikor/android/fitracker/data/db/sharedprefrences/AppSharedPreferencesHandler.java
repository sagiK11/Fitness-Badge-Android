package com.sagikor.android.fitracker.data.db.sharedprefrences;


import android.content.SharedPreferences;

public class AppSharedPreferencesHandler implements SharedPreferencesHandler {
    private final static AppSharedPreferencesHandler sharedPreferencesHandler = init();
    private SharedPreferences sharedPreferences;

    private static AppSharedPreferencesHandler init() {
        return new AppSharedPreferencesHandler();
    }

    public static AppSharedPreferencesHandler getInstance(){
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
    public void editGenderPreferences(String gender,boolean isChecked) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(gender.equals("Girls")){
            editor.putBoolean("alwaysGirlsSwitch", isChecked);
        }else if(gender.equals("Boys")){
            editor.putBoolean("alwaysBoysSwitch", isChecked);
        }
        editor.apply();
    }
}
