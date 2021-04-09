package com.sagikor.android.fitracker.ui.contracts;

import android.content.SharedPreferences;

import java.util.List;


public interface AddStudentActivityContract {
    interface Presenter extends StudentActivityContract.Presenter {
        void onSelectStudentClass();

        void onSelectStudentGender();

        void onAddStudentClick();

        void applyUserSetting();

        void bind(AddStudentActivityContract.View view, SharedPreferences sharedPreferences);

        void unbind();

        boolean isValidGender(String genderInput);

        boolean isValidClass(String classInput);

        boolean isValidName(String nameInput);

        List<String> getTeacherClasses();

    }

    interface View extends StudentActivityContract.View {
        void selectStudentClass();

        void selectStudentGender();

        void setGirlsPreferences();

        void setBoysPreferences();

        void setDefaultPreferences();

        String getClassStringResource();

        String getGenderStringResource();

        String getGradeStringResource();
    }
}
