package com.sagikor.android.fitracker.ui.contracts;

import android.content.SharedPreferences;

import java.util.List;


public interface AddClassesActivityContract {

    interface Presenter extends BaseContract.BasePresenter {

        void onAddClassClick();

        void onDeleteClassToTeach(String classToTeach);

        List<String> getClassesUserTeaches();

        void bind(AddClassesActivityContract.View view, SharedPreferences sharedPreferences);

        void unbind();

    }

    interface View extends BaseContract.BaseView {

        String getClassToTeach();

        void popMessage(String message);

        void updateList();

    }
}
