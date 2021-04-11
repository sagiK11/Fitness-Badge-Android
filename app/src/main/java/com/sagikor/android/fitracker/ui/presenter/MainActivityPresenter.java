package com.sagikor.android.fitracker.ui.presenter;

import com.google.firebase.auth.FirebaseAuth;
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
    public void onCompleteDataWrite() {
        view.hideProgressBar();
    }

    @Override
    public void onDisconnectClick() {
        FirebaseAuth.getInstance().signOut();
        view.disconnectUser();
    }


    @Override
    public void bind(MainActivityContract.View view) {
        this.view = view;
        databaseHandler.setLoaderPresenter(this);
        if (!databaseHandler.isDataLoaded()) {
            view.setLoadingMode();
        }else{
            view.setActiveMode();
        }
    }

    @Override
    public void unbind() {
        this.view = null;
        databaseHandler.setLoaderPresenter(null);
    }

    @Override
    public void onFinishedLoadingData() {
        view.setActiveMode();
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
