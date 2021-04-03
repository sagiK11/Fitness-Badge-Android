package com.sagikor.android.fitracker.ui.contracts;


public interface UpdateStudentActivityContract extends StudentActivityContract {
    interface Presenter extends StudentActivityContract.Presenter {
        void onSaveButtonClick();

        Object getCachedObject();

        void bind(UpdateStudentActivityContract.View view);

        void unbind();
    }

    interface View extends StudentActivityContract.View {
        void setStudentName(String name);

        void setStudentClass(String studentClass);

        void setStudentPhoneNo(String phoneNo);

        void setStudentGender(String gender);

        void setAerobicScore(String score);

        void setCubesScore(String score);

        void setAbsScore(String score);

        void setHandsScore(String score);

        void setJumpScore(String score);

    }
}
