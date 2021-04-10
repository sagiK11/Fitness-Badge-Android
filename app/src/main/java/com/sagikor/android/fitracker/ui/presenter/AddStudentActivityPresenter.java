package com.sagikor.android.fitracker.ui.presenter;


import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import com.sagikor.android.fitracker.data.model.Student;

import com.sagikor.android.fitracker.data.model.UserClass;
import com.sagikor.android.fitracker.ui.contracts.AddStudentActivityContract;
import com.sagikor.android.fitracker.ui.contracts.BaseContract;
import com.sagikor.android.fitracker.utils.AppExceptions;
import com.sagikor.android.fitracker.utils.Utility;


import java.util.List;


public class AddStudentActivityPresenter extends StudentActivityPresenter implements
        AddStudentActivityContract.Presenter, BaseContract.AdderPresenter {

    private static final String TAG = "AddStudentActivityPres";
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

        try {
            checkInput();
        } catch (AppExceptions.Input e) {
            view.popMessage(e.getMessage());
            return;
        }

        Student newStudent = createNewStudent();
        try {
            databaseHandler.checkStudentExistsInFirebase(newStudent);
        } catch (AppExceptions.StudentExistsAlready e) {
            view.popFailWindow(e.getMessage());
            return;
        }
        databaseHandler.addStudent(newStudent);
    }

    @Override
    public void bind(AddStudentActivityContract.View view, SharedPreferences sharedPreferences) {
        super.bind(view);
        this.view = view;
        databaseHandler.setSharedPreferences(sharedPreferences);
        databaseHandler.setAdderPresenter(this);
    }

    @Override
    public void unbind() {
        super.unbind();
        this.view = null;
        databaseHandler.setSharedPreferences(null);
        databaseHandler.setAdderPresenter(null);

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
                .totalScore(super.getAverage()) //TODO test it
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

    @Override
    public boolean isValidClass(String classInput) {
        String chooseClassLabel = view.getClassStringResource();
        return !(classInput.equals(chooseClassLabel));
    }


    @Override
    public boolean isValidGender(String genderInput) {
        String chooseGenderLabel = view.getGenderStringResource();
        return !(genderInput.equals(chooseGenderLabel));
    }


    private void checkInput() throws AppExceptions.Input {
        if (!isValidName(view.getStudentName())) {
            throw new AppExceptions.Input("please select name");
        } else if (!super.isValidPhoneNo(view.getStudentPhoneNo())) {
            throw new AppExceptions.Input("invalid phone number");
        } else if (!isValidClass(view.getStudentClass())) {
            throw new AppExceptions.Input("please select class");
        } else if (!isValidGender(view.getStudentGender())) {
            throw new AppExceptions.Input("please select gender");
        }
    }

    @Override
    public boolean isValidName(String nameInput) {
        final int maxLength = 25;
        return isNonEmptyInput(nameInput) && nameInput.length() < maxLength
                && isAlphabetic(nameInput);
    }

    @Override
    public List<UserClass> getTeacherClasses() {
        return databaseHandler.getClassesUserTeaches();
    }

    private boolean isAlphabetic(String nameInput) {
        for (Character ch : nameInput.toCharArray()) {
            if (!Character.isAlphabetic(ch) && ch != ' ' && ch != '\'') {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onAddStudentSuccess(Student student) {
        view.updateTotalScore(student.getTotalScore());
        view.popSuccessWindow();
        view.askForSendingSMS(student);
    }

    @Override
    public void onAddStudentFailed() {
        view.popFailWindow("Ho no! Something wen't wrong!");
    }
}
