package com.sagikor.android.fitracker.ui.contracts;

import android.content.SharedPreferences;

public interface AddStudentActivityContract {
    interface Presenter extends StudentActivityContract.Presenter {
        void onSelectStudentClass();

        void onSelectStudentGender();

        void onAddStudentClick();

        void applyUserSetting();

        void bind(AddStudentActivityContract.View view, SharedPreferences sharedPreferences);

        void unbind();

    }

    interface View extends StudentActivityContract.View {
        void selectStudentClass();

        void selectStudentGender();

        void setGirlsPreferences();

        void setBoysPreferences();

        void setDefaultPreferences();

    }
}
