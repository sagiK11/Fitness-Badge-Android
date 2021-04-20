package com.sagikor.android.fitracker.ui.contracts;


import com.sagikor.android.fitracker.data.model.UserClass;
import com.sagikor.android.fitracker.utils.AppExceptions;

import java.util.List;


public interface AddClassesActivityContract {
    interface Presenter extends BaseContract.BasePresenter {

        void onAddClassClick();

        void onDeleteClassToTeach(UserClass classToTeach);

        List<UserClass> getClassesUserTeaches();

        void checkValidInput(String input) throws AppExceptions.Input;

        void bind(AddClassesActivityContract.View view);

        void unbind();

        void onAddClassSuccess(UserClass userClass);

        void onAddSClassFailed();

        void onDeleteClassSuccess(UserClass userClass);

        void onDeleteClassFailed();

    }

    interface View extends BaseContract.BaseView {

        String getClassToTeach();

        void updateList();

        void popAddClassSuccess();

        void popAddClassFail();

        void popDeleteClassSuccess();

        void popDeleteClassFail();

        void popEmptyClassFieldAlert();

        void popLongClassNameAlert();

        void popInvalidClassAlert();

    }
}
