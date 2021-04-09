package com.sagikor.android.fitracker.ui.presenter;

import android.content.SharedPreferences;

import com.sagikor.android.fitracker.data.db.AppDatabaseHandler;
import com.sagikor.android.fitracker.data.db.DatabaseHandler;
import com.sagikor.android.fitracker.ui.contracts.SettingsActivityContract;

public class SettingsActivityPresenter implements SettingsActivityContract.Presenter {
    private final DatabaseHandler databaseHandler = AppDatabaseHandler.getInstance();
    private SettingsActivityContract.View view;

    @Override
    public void onClearDatabaseClick() {
        databaseHandler.clearDatabase();
    }

    @Override
    public void bind(SettingsActivityContract.View view, SharedPreferences sharedPreferences) {
        this.view = view;
        databaseHandler.setSharedPreferences(sharedPreferences);
    }

    @Override
    public void unbind() {
        this.view = null;
        databaseHandler.setSharedPreferences(null);
    }

    @Override
    public void onDeleteAccountClick() {
        databaseHandler.deleteAccount();
    }

    @Override
    public void onAddClassesClick() {
        view.navToAddClasses();
    }


    @Override
    public void editGenderPreferences(String gender, boolean isChecked) {
        databaseHandler.editGenderPreferences(gender, isChecked);
        view.switchLogic();
    }


    @Override
    public boolean isGirlsSwitchOn() {
        return databaseHandler.isGirlsSwitchOn();
    }

    @Override
    public boolean isBoysSwitchOn() {
        return databaseHandler.isBoysSwitchOn();
    }
}
