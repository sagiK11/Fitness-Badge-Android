package com.sagikor.android.fitracker.ui.presenter;

import com.sagikor.android.fitracker.data.db.AppDatabaseHandler;
import com.sagikor.android.fitracker.data.db.DatabaseHandler;
import com.sagikor.android.fitracker.data.model.Student;
import com.sagikor.android.fitracker.ui.contracts.MainActivityContract;

import java.util.List;


public class MainActivityPresenter implements MainActivityContract.Presenter {
    private MainActivityContract.View view;
    private final DatabaseHandler databaseHandler = AppDatabaseHandler.getInstance();

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
    public void onNavToAddClassesClick() {
        view.navToAddClassesScreen();
    }

    @Override
    public void onNavToStatisticsClick() {
        view.navToStatisticsScreen();
    }

    @Override
    public void onOpenDrawer() {
        view.openNavDrawer();
    }

    @Override
    public void onNavToTermsOfUseClick() {
        view.navToTermsOfUseUsUrl();
    }

    @Override
    public void onNavToRateUsClick() {
        view.navToRateUs();
    }

    @Override
    public void onNavToViewStudentsClick() {
        view.navToViewStudentsScreen();
    }

    @Override
    public void onCompleteDataWrite() {
        view.hideProgressBar();
    }

    @Override
    public void onDisconnectClick() {
        databaseHandler.signOut();
    }


    @Override
    public void bind(MainActivityContract.View view) {
        this.view = view;
        if (!databaseHandler.isDataLoaded())
            databaseHandler.getDataFromDatabase(this);
        else
            view.setActiveMode();
    }

    @Override
    public void unbind() {
        this.view = null;
    }

    @Override
    public void onFinishedLoadingData() {
        view.setActiveMode();
        view.setUserName(databaseHandler.getUserName());
    }

    @Override
    public void onLoadingData() {
        view.setLoadingMode();
    }

    @Override
    public String getStudentsAsCSV() {
        List<Student> studentList = databaseHandler.getStudents();
        StringBuilder sb = new StringBuilder();
        for (Student student : studentList) {
            sb.append("\n").append(student.asCSV());
        }
        return sb.toString();
    }
}
