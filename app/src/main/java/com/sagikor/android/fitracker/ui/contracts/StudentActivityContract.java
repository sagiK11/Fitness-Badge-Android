package com.sagikor.android.fitracker.ui.contracts;


import com.sagikor.android.fitracker.data.model.Student;

public interface StudentActivityContract {
    interface Presenter extends BaseContract.BasePresenter {

        boolean isValidPhoneNo(String phoneNoInput);

        boolean isValidScore(String scoreInput);

        boolean isGenderSelected();

        String calculateGrade(String score, String sportType, boolean isFemale);

        void bind(StudentActivityContract.View view);

        void unbind();
    }

    interface View extends BaseContract.BaseView {
        void updateTotalScore(double score);

        void askForSendingSMS(Student student);

        void popSuccessWindow();

        String getStudentName();

        String getStudentClass();

        String getStudentPhoneNo();

        String getStudentGender();

        String getAerobicScore();

        String getCubesScore();

        String getAbsSore();

        String getHandsScore();

        String getAbsGrade();

        String getAerobicGrade();

        String getJumpGrade();

        String getHandsGrade();

        String getCubesGrade();

        String getJumpScore();

        void popFailWindow(String error);

        String getGradeStringResource();

        String getGenderStringResource();
    }
}
