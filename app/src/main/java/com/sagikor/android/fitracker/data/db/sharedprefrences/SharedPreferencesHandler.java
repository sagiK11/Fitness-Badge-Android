package com.sagikor.android.fitracker.data.db.sharedprefrences;

import android.content.SharedPreferences;

import java.util.Set;

public interface SharedPreferencesHandler {

    void setSharedPreferences(SharedPreferences sharedPreferences);

    boolean isGirlsSwitchOn();

    boolean isBoysSwitchOn();

    void editGenderPreferences(String gender, boolean isChecked);

}
