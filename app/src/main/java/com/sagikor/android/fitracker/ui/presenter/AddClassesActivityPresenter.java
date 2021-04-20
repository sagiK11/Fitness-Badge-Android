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
    private static final String INSERT_CLASS = "please insert class to teach";
    private static final String NAME_TOO_LONG = "class name too long";
    private static final String INVALID_CLASS = "please insert valid class name";

    @Override
    public void onAddClassClick() {
        String classToTeach = view.getClassToTeach();
        try {
            checkValidInput(classToTeach);
        } catch (AppExceptions.Input e) {
            switch (e.getMessage()) {
                case INSERT_CLASS:
                    view.popEmptyClassFieldAlert();
                    return;
                case NAME_TOO_LONG:
                    view.popLongClassNameAlert();
                    return;
                case INVALID_CLASS:
                    view.popInvalidClassAlert();
                default:
                    return;
            }
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
            throw new AppExceptions.Input(INSERT_CLASS);
        else if (classToTeach.length() > max_class_name)
            throw new AppExceptions.Input(NAME_TOO_LONG);
        else if (!Utility.isValidClassName(classToTeach))
            throw new AppExceptions.Input(INVALID_CLASS);
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
        view.popAddClassSuccess();
    }

    @Override
    public void onAddSClassFailed() {
        view.popAddClassFail();
    }

    @Override
    public void onDeleteClassSuccess(UserClass userClass) {
        view.updateList();
        view.popDeleteClassSuccess();
    }

    @Override
    public void onDeleteClassFailed() {
        view.popDeleteClassFail();
    }
}
