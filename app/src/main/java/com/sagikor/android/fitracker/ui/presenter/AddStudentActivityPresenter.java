package com.sagikor.android.fitracker.ui.presenter;


import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import com.sagikor.android.fitracker.data.model.Student;
import com.sagikor.android.fitracker.data.db.AppDatabaseHandler;
import com.sagikor.android.fitracker.data.db.DatabaseHandler;
import com.sagikor.android.fitracker.ui.contracts.AddStudentActivityContract;
import com.sagikor.android.fitracker.utils.Utility;

public class AddStudentActivityPresenter extends StudentActivityPresenter implements AddStudentActivityContract.Presenter {
    private final DatabaseHandler databaseHandler = AppDatabaseHandler.getInstance();
    private AddStudentActivityContract.View view;

    @Override
    public void onSelectStudentClass() {
        view.selectStudentClass();
    }

    @Override
    public void onSelectStudentGender() {
        view.selectStudentGender();
    }

    @Override
    public void onAddStudentClick() {
        //TODO test here the user input

        Student newStudent = createNewStudent();
        if (databaseHandler.isStudentExistsInFirebase(newStudent))
            return;
        addStudentToFirebase(newStudent);
        //TODO show total score in the view.
        //view.updateTotalScore(score);
    }

    @Override
    public void bind(AddStudentActivityContract.View view, SharedPreferences sharedPreferences) {
        this.view = view;
        databaseHandler.setSharedPreferences(sharedPreferences);
    }

    @Override
    public void unbind() {
        this.view = null;
        databaseHandler.setSharedPreferences(null);

    }

    private Student createNewStudent() {
        String studentKey = FirebaseDatabase.getInstance().getReference("users").push().getKey();
        return new Student.Builder(view.getStudentName())
                .studentClass(view.getStudentClass())
                .phoneNumber(view.getStudentPhoneNo())
                .key(studentKey)
                .userId(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .studentGender(view.getStudentGender())
                .aerobicScore(parse(view.getAerobicScore()))
                .cubesScore(parse(view.getCubesScore()))
                .absScore(parse(view.getAbsSore()))
                .jumpScore(parse(view.getJumpScore()))
                .handsScore(parse(view.getHandsScore()))
                .absResult(parse(view.getAbsGrade()))
                .aerobicResult(parse(view.getAerobicGrade()))
                .jumpResult(parse(view.getJumpGrade()))
                .handsResult(parse(view.getHandsGrade()))
                .cubesResult(parse(view.getCubesGrade()))
                //.totalScore(view.getAvg()) TODO this should be calculated here
                .updatedDate(Utility.getTodayDate())
                .build();
    }

    @Override
    public void applyUserSetting() {
        boolean isGirlsSwitchOn = databaseHandler.isGirlsSwitchOn();
        boolean isBoysSwitchOn = databaseHandler.isBoysSwitchOn();

        if (isGirlsSwitchOn) {
            view.setGirlsPreferences();
        } else if (isBoysSwitchOn) {
            view.setBoysPreferences();
        } else {
            view.setDefaultPreferences();
        }
    }


    private void addStudentToFirebase(Student newStudent) {
        databaseHandler.addStudent(newStudent);
        view.askForSendingSMS();
    }

}
