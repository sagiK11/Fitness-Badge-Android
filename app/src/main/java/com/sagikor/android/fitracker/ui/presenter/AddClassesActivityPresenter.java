package com.sagikor.android.fitracker.ui.presenter;


import com.sagikor.android.fitracker.data.db.AppDatabaseHandler;
import com.sagikor.android.fitracker.data.db.DatabaseHandler;
import com.sagikor.android.fitracker.data.model.UserClass;
import com.sagikor.android.fitracker.ui.contracts.AddClassesActivityContract;
import com.sagikor.android.fitracker.utils.AppExceptions;
import com.sagikor.android.fitracker.utils.Utility;

import java.util.List;


public class AddClassesActivityPresenter implements AddClassesActivityContract.Presenter {
    private DatabaseHandler databaseHandler;
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

        databaseHandler.addClassUserTeaches(this, new UserClass(classToTeach));
        view.updateList();
    }

    @Override
    public void onDeleteClassToTeach(UserClass classToTeach) {
        databaseHandler.deleteClassUserTeaches(this, classToTeach);
    }

    @Override
    public List<UserClass> getClassesUserTeaches() {
        return databaseHandler.getClassesUserTeaches();
    }

    @Override
    public void checkValidInput(String classToTeach) throws AppExceptions.Input {
        final int max_class_name = 10;
        if (classToTeach == null || classToTeach.length() == 0)
            throw new AppExceptions.Input("please insert class to teach");
        else if (classToTeach.length() > max_class_name)
            throw new AppExceptions.Input("class name too long");
        else if (!Utility.isValidClassName(classToTeach))
            throw new AppExceptions.Input("please insert valid class name");
    }

    @Override
    public void bind(AddClassesActivityContract.View view) {
        this.view = view;
        databaseHandler = AppDatabaseHandler.getInstance();
    }

    @Override
    public void unbind() {
        this.view = null;
        databaseHandler = null;
    }

    @Override
    public void onAddClassSuccess(UserClass userClass) {
        view.updateList();
    }

    @Override
    public void onAddSClassFailed() {
        view.popMessage(Utility.GENERIC_ERROR_MESSAGE);
    }

    @Override
    public void onDeleteClassSuccess(UserClass userClass) {
        view.updateList();
    }

    @Override
    public void onDeleteClassFailed() {
        view.popMessage(Utility.GENERIC_ERROR_MESSAGE);
    }
}
