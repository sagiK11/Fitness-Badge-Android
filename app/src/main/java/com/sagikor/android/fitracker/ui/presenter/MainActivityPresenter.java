package com.sagikor.android.fitracker.ui.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.sagikor.android.fitracker.data.db.AppDatabaseHandler;
import com.sagikor.android.fitracker.data.db.DatabaseHandler;
import com.sagikor.android.fitracker.data.model.Student;
import com.sagikor.android.fitracker.ui.contracts.MainActivityContract;
import com.sagikor.android.fitracker.ui.view.MainActivity;

import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

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
    public void onDisconnectClick() {
        FirebaseAuth.getInstance().signOut();
        view.disconnectUser();
    }

    @Override
    public void writeStudentsData(CSVWriter writer) {
        List<Student> studentList = databaseHandler.getStudents();
        for (Student student : studentList) {
            writer.writeNext(student.toArray());
        }
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
