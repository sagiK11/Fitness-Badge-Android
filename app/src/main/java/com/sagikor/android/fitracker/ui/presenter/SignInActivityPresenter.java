package com.sagikor.android.fitracker.ui.presenter;


import com.sagikor.android.fitracker.data.db.AppDatabaseHandler;
import com.sagikor.android.fitracker.data.db.DatabaseHandler;
import com.sagikor.android.fitracker.ui.contracts.SignInActivityContract;
import com.sagikor.android.fitracker.utils.Utility;

public class SignInActivityPresenter implements SignInActivityContract.Presenter {
    private DatabaseHandler databaseHandler;
    private SignInActivityContract.View view;

    @Override
    public void onLoginClick() {
        String email = view.getUserEmail();
        String password = view.getUserPassword();

        view.showProgressBar();

        if (email.isEmpty()) {
            view.setErrorInEmail();
            return;
        }

        if (password.isEmpty() || password.length() < Utility.PASS_LENGTH) {
            view.setErrorInPassword();
            return;
        }
        databaseHandler.signInWithEmailAndPassword(this, email, password);
    }

    @Override
    public void onRegisterClick() {
        view.navToRegisterScreen();
    }

    @Override
    public void onResetPasswordClick() {
        String userEmail = view.getUserEmail();
        if (userEmail.isEmpty()) {
            view.notifyUserToFillEmail();
            return;
        }
        databaseHandler.resetPassword(this, view.getUserEmail());
    }

    @Override
    public void bind(SignInActivityContract.View view) {
        this.view = view;
        databaseHandler = AppDatabaseHandler.getInstance();
    }

    @Override
    public void unbind() {
        this.view = null;
        databaseHandler = null;
    }

    @Override
    public void onStart() {
        //Moving to main activity if the user is already signed in with his device.
        boolean isUserSigned = databaseHandler.isUserSigned();
        if (isUserSigned)
            view.navSignedUserToHomeScreen();
    }

    @Override
    public void onSignInSuccess() {
        view.hideProgressBar();
        view.navToHomeScreen();
    }

    @Override
    public void onSignInFailure() {
        view.hideProgressBar();
        view.popSignInFailureMessage();
    }

    @Override
    public void onResetPassSuccess() {
        view.notifyUserToCheckMail();
    }

    @Override
    public void onResetPassFailure() {
        view.popError("Wrong email address");
    }
}
