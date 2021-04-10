package com.sagikor.android.fitracker.ui.contracts;

import android.content.SharedPreferences;

import com.sagikor.android.fitracker.data.model.UserClass;

import java.util.List;


public interface AddClassesActivityContract {

    interface Presenter extends BaseContract.BasePresenter {

        void onAddClassClick();

        void onDeleteClassToTeach(UserClass classToTeach);

        List<UserClass> getClassesUserTeaches();

        void bind(AddClassesActivityContract.View view, SharedPreferences sharedPreferences);

        void unbind();

    }

    interface View extends BaseContract.BaseView {

        String getClassToTeach();

        void popMessage(String message);

        void updateList();

    }
}
