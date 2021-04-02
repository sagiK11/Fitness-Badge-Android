package com.sagikor.android.fitracker.ui.presenter;

import com.sagikor.android.fitracker.data.db.AppDatabaseHandler;
import com.sagikor.android.fitracker.data.db.DatabaseHandler;
import com.sagikor.android.fitracker.ui.contracts.SignInActivityContract;

public class SignInActivityPresenter implements SignInActivityContract.Presenter {
    private final DatabaseHandler databaseHandler = AppDatabaseHandler.getInstance();
    private SignInActivityContract.View view;

    @Override
    public void onLoginClick() {

    }

    @Override
    public void onRegisterClick() {

    }

    @Override
    public void onResetPasswordClick() {

    }

    @Override
    public void bind(SignInActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void unbind() {
        this.view = null;
    }
}
