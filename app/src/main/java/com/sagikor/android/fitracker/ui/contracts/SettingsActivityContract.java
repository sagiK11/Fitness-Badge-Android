package com.sagikor.android.fitracker.ui.contracts;

import android.content.SharedPreferences;

public interface SettingsActivityContract {
    interface Presenter extends BaseContract.BasePresenter {
        void onClearDatabaseClick();

        void onDeleteAccountClick();

        void onAddClassesClick();

        void editGenderPreferences(String gender, boolean isChecked);

        boolean isGirlsSwitchOn();

        boolean isBoysSwitchOn();

        void bind(SettingsActivityContract.View view, SharedPreferences sharedPreferences);

        void unbind();

        String getUserName();

    }

    interface View extends BaseContract.BaseView {
        void switchLogic();

        void navToAddClasses();
    }
}
