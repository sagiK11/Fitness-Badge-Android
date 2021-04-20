package com.sagikor.android.fitracker.ui.presenter;

import android.content.SharedPreferences;

import com.sagikor.android.fitracker.data.db.AppDatabaseHandler;
import com.sagikor.android.fitracker.data.db.DatabaseHandler;
import com.sagikor.android.fitracker.data.model.Student;
import com.sagikor.android.fitracker.ui.contracts.BaseContract;
import com.sagikor.android.fitracker.ui.contracts.SettingsActivityContract;
import com.sagikor.android.fitracker.utils.Utility;

public class SettingsActivityPresenter implements SettingsActivityContract.Presenter {
    private DatabaseHandler databaseHandler;
    private SettingsActivityContract.View view;

    @Override
    public void onClearDatabaseClick() {
        databaseHandler.clearDatabase(this);
    }

    @Override
    public void bind(SettingsActivityContract.View view, SharedPreferences sharedPreferences) {
        this.view = view;
        databaseHandler = AppDatabaseHandler.getInstance();
        databaseHandler.setSharedPreferences(sharedPreferences);
    }

    @Override
    public void unbind() {
        this.view = null;
        databaseHandler.setSharedPreferences(null);
        databaseHandler = null;
    }

    @Override
    public String getUserName() {
        return databaseHandler.getUserName();
    }

    @Override
    public void onDeleteAccountClick() {
        databaseHandler.deleteAccount(this);
    }

    @Override
    public void onAddClassesClick() {
        view.navToAddClasses();
    }

    @Override
    public void editGenderPreferences(String gender, boolean isChecked) {
        databaseHandler.editGenderPreferences(gender, isChecked);
        view.switchLogic();
    }

    @Override
    public boolean isGirlsSwitchOn() {
        return databaseHandler.isGirlsSwitchOn();
    }

    @Override
    public boolean isBoysSwitchOn() {
        return databaseHandler.isBoysSwitchOn();
    }

    @Override
    public void onDeleteStudentSuccess(Student student) {
        view.popMessage("database cleared", BaseContract.BaseView.msgType.success);
    }

    @Override
    public void onDeleteStudentFailed() {
        view.popMessage("no worries! nothing has been deleted!", BaseContract.BaseView.msgType.fail);
    }

    @Override
    public void onDeleteAccountSuccess() {
        view.navToSignInScreen();
    }

    @Override
    public void onDeleteAccountFailure() {
        view.popMessage(Utility.GENERIC_ERROR_MESSAGE, BaseContract.BaseView.msgType.fail);
    }
}
