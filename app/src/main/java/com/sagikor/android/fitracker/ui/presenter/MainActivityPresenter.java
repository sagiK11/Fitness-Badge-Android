package com.sagikor.android.fitracker.ui.presenter;

import com.sagikor.android.fitracker.data.db.AppDatabaseHandler;
import com.sagikor.android.fitracker.data.db.DatabaseHandler;

public class MainActivityPresenter implements MainActivityContract.Presenter {
    MainActivityContract.View view;
    DatabaseHandler databaseHandler = AppDatabaseHandler.getInstance();

    @Override
    public void onSendToEmailClick() {
        view.sendDatabaseToEmail();
    }

    @Override
    public void onNavToSettingsClick() {
        view.navToSettingsScreen();
    }

    @Override
    public void onNavToAddStudentClick() {
        view.navToAddStudentScreen();
    }

    @Override
    public void onNavToUpdateStudentClick() {
        view.navToUpdateStudentScreen();
    }

    @Override
    public void onNavToStatisticsClick() {
        view.navToStatisticsScreen();
    }

    @Override
    public void onNavToViewStudentsClick() {
        view.navToViewStudentsScreen();
    }

    @Override
    public void onDisconnectClick() {
        view.disconnectUser();
    }

    @Override
    public void bind(MainActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void unbind() {
        this.view = null;
    }
}
