package com.sagikor.android.fitracker.ui.addupdatestudent;


import com.sagikor.android.fitracker.data.model.Student;
import com.sagikor.android.fitracker.ui.contracts.BaseContract;

import java.io.InputStream;
import java.util.Map;

public interface StudentActivityContract {
    interface Presenter extends BaseContract.BasePresenter {

        boolean isValidPhoneNo(String phoneNoInput);

        boolean isValidScore(String scoreInput);

        boolean isGenderSelected();

        void onScoreTextChanged(String score, String sportType, Map<String, Boolean> map);

        String calculateGrade(String score, String sportType, Map<String, Boolean> map);

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

        void setAbsGrade(String grade);

        void setAerobicGrade(String grade);

        void setJumpGrade(String grade);

        void setHandsGrade(String grade);

        void setCubesGrade(String grade);

        void setTotalGrade(String grade);

        void popFailWindow(String error);

        void popGenderError();

        String getGradeStringResource();

        String getGenderStringResource();

        boolean isAerobicWalkingChecked();

        boolean isPushUpHalfChecked();

        InputStream getFemaleGradesFile();

        InputStream getMaleGradesFile();
    }
}
