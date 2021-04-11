package com.sagikor.android.fitracker.ui.presenter;

import android.content.SharedPreferences;

import com.sagikor.android.fitracker.data.db.AppDatabaseHandler;
import com.sagikor.android.fitracker.data.db.DatabaseHandler;
import com.sagikor.android.fitracker.data.model.UserClass;
import com.sagikor.android.fitracker.ui.contracts.AddClassesActivityContract;
import com.sagikor.android.fitracker.ui.contracts.BaseContract;
import com.sagikor.android.fitracker.utils.AppExceptions;

import java.util.List;


public class AddClassesActivityPresenter implements AddClassesActivityContract.Presenter,
        BaseContract.ClassOperationsPresenter {
    private final DatabaseHandler databaseHandler = AppDatabaseHandler.getInstance();
    private AddClassesActivityContract.View view;

    @Override
    public void onAddClassClick() {
        String classToTeach = view.getClassToTeach();
        try {
            checkValidInput(classToTeach);
        } catch (AppExceptions.Input e) {
            view.popMessage(e.getMessage());
            return;
        }

        databaseHandler.addClassUserTeaches(new UserClass(classToTeach));
        view.updateList();
    }

    @Override
    public void onDeleteClassToTeach(UserClass classToTeach) {
        databaseHandler.deleteClassUserTeaches(classToTeach);
    }

    @Override
    public List<UserClass> getClassesUserTeaches() {
        return databaseHandler.getClassesUserTeaches();
    }

    private void checkValidInput(String classToTeach) throws AppExceptions.Input {
        final int max_class_name = 10;
        if (classToTeach == null || classToTeach.length() == 0)
            throw new AppExceptions.Input("please insert class to teach");
        else if (classToTeach.length() > max_class_name)
            throw new AppExceptions.Input("class name too long");
    }

    @Override
    public void bind(AddClassesActivityContract.View view, SharedPreferences sharedPreferences) {
        this.view = view;
        databaseHandler.setSharedPreferences(sharedPreferences);
        databaseHandler.setClassOperationPresenter(this);
    }

    @Override
    public void unbind() {
        this.view = null;
        databaseHandler.setSharedPreferences(null);
        databaseHandler.setClassOperationPresenter(null);
    }

    @Override
    public void onAddClassSuccess(UserClass userClass) {
        view.updateList();
    }

    @Override
    public void onAddSClassFailed() {
        view.popMessage("Ho no! Something wen't wrong!");
    }

    @Override
    public void onDeleteClassSuccess(UserClass userClass) {
        view.updateList();
    }

    @Override
    public void onDeleteClassFailed() {
        view.popMessage("Ho no! Something wen't wrong!");
    }
}
