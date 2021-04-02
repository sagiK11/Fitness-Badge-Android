package com.sagikor.android.fitracker.ui.presenter;

import com.sagikor.android.fitracker.data.db.AppDatabaseHandler;
import com.sagikor.android.fitracker.data.db.DatabaseHandler;
import com.sagikor.android.fitracker.ui.contracts.RegisterActivityContract;

public class RegisterActivityPresenter implements RegisterActivityContract.Presenter {
    private final DatabaseHandler databaseHandler = AppDatabaseHandler.getInstance();
    private RegisterActivityContract.View view;

    @Override
    public void bind(RegisterActivityContract.View view) {
        this.view =view;
    }

    @Override
    public void unbind() {
        view = null;
    }
}
