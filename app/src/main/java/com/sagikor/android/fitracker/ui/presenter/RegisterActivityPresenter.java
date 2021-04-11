package com.sagikor.android.fitracker.ui.presenter;


import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.sagikor.android.fitracker.data.db.AppDatabaseHandler;
import com.sagikor.android.fitracker.data.db.DatabaseHandler;
import com.sagikor.android.fitracker.ui.contracts.BaseContract;
import com.sagikor.android.fitracker.ui.contracts.RegisterActivityContract;
import com.sagikor.android.fitracker.utils.Utility;

public class RegisterActivityPresenter implements
        RegisterActivityContract.Presenter,
        BaseContract.RegisterPresenter {
    private final DatabaseHandler databaseHandler = AppDatabaseHandler.getInstance();
    private RegisterActivityContract.View view;

    @Override
    public void bind(RegisterActivityContract.View view) {
        this.view = view;
        databaseHandler.setRegisterPresenter(this);
    }

    @Override
    public void unbind() {
        view = null;
        databaseHandler.setRegisterPresenter(null);
    }

    @Override
    public void onRegisterClick() {
        view.showProgressBar();
        final String email = view.getUserEmail();
        final String password = view.getUserPassword();
        final String name = view.getUserName();
        if (isInputErrorsFound()) {
            view.hideProgressBar();
            return;
        }
        databaseHandler.createUserWithEmailAndPassword(email, password, name);
    }

    @Override
    public void onRegisterSuccess() {
        view.hideProgressBar();
        view.navToHomeScreen();
    }

    @Override
    public void onRegisterFailure(Task<AuthResult> task) {
        view.hideProgressBar();
        view.notifyErrorsToUser(task);
    }

    private boolean isInputErrorsFound() {
        String userFullName = view.getUserName();
        String email = view.getUserEmail();
        String password = view.getUserPassword();

        if (userFullName.isEmpty()) {
            view.setNameError();
            return true;
        }

        if (email.isEmpty()) {
            view.setEmailError();
            return true;
        }

        if (password.isEmpty()) {
            view.setPasswordError();
            return true;
        }

        if (password.length() < Utility.PASS_LENGTH) {
            view.setWeakPassError();
            return true;
        }
        return false;
    }
}
