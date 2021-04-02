package com.sagikor.android.fitracker.ui.contracts;

import android.content.SharedPreferences;

public interface SettingsActivityContract {
    interface Presenter {
        void clearDatabase();

        void bind(SettingsActivityContract.View view, SharedPreferences sharedPreferences);

        void unbind();

        void deleteAccount();

        void editGenderPreferences(String gender, boolean isChecked);

        boolean isGirlsSwitchOn();

        boolean isBoysSwitchOn();

    }

    interface View {
        void switchLogic();
    }
}
