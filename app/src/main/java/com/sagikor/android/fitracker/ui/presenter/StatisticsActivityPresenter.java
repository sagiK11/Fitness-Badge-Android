package com.sagikor.android.fitracker.ui.presenter;

import com.sagikor.android.fitracker.data.db.AppDatabaseHandler;
import com.sagikor.android.fitracker.data.db.DatabaseHandler;
import com.sagikor.android.fitracker.ui.contracts.StatisticsActivityContract;

public class StatisticsActivityPresenter implements StatisticsActivityContract.Presenter {
    private final DatabaseHandler databaseHandler = AppDatabaseHandler.getInstance();
    private StatisticsActivityContract.View view;

    @Override
    public void bind(StatisticsActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void unbind() {
        this.view = null;
    }
}
