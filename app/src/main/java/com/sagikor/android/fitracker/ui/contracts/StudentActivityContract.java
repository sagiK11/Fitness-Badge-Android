package com.sagikor.android.fitracker.ui.contracts;


public interface StudentActivityContract {
    interface Presenter {

        String calculateGrade(String score, String sportType, String gender);

    }

    interface View {
        void updateTotalScore(int score);

        void askForSendingSMS();

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

        void popFailWindow();

        void popMessage(String message);
    }
}
