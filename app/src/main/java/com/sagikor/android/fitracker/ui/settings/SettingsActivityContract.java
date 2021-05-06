package com.sagikor.android.fitracker.ui.settings;

import android.content.SharedPreferences;

import com.sagikor.android.fitracker.data.model.Student;
import com.sagikor.android.fitracker.ui.contracts.BaseContract;

public interface SettingsActivityContract {
    interface Presenter extends BaseContract.BasePresenter {
        void onClearDatabaseClick();

        void onDeleteAccountClick();

        void onAddClassesClick();

        void editGenderPreferences(String gender, boolean isChecked);

        boolean isGirlsSwitchOn();

        boolean isBoysSwitchOn();

        void bind(SettingsActivityContract.View view, SharedPreferences sharedPreferences);

        void unbind();

        String getUserName();

        void onDeleteStudentSuccess(Student student);

        void onDeleteStudentFailed();

        void onDeleteAccountSuccess();

        void onDeleteAccountFailure();

    }

    interface View extends BaseContract.BaseView {
        void switchLogic();

        void navToAddClasses();

        void navToSignInScreen();

    }
}
