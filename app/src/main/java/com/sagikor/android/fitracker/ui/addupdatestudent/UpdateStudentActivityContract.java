package com.sagikor.android.fitracker.ui.addupdatestudent;


import com.sagikor.android.fitracker.data.model.Student;

public interface UpdateStudentActivityContract extends StudentActivityContract {
    interface Presenter extends StudentActivityContract.Presenter {
        void onSaveButtonClick();

        Object getCachedObject();

        void bind(UpdateStudentActivityContract.View view);

        void unbind();

        void onUpdateStudentSuccess(Student student);

        void onUpdateStudentFailed();
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

        void setAerobicGrade(String grade);

        void setCubesGrade(String grade);

        void setAbsGrade(String grade);

        void setHandsGrade(String grade);

        void setJumpGrade(String grade);

        void setAerobicWalkingSwitch(boolean isWalking);

        void setPushUpHalfSwitch(boolean isPushUpHalf);
    }
}
