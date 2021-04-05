package com.sagikor.android.fitracker.ui.contracts;

import com.sagikor.android.fitracker.data.model.Student;

public interface BaseContract {
    interface BasePresenter{



    }
    interface BaseView{

    }

    interface LoaderPresenter extends BaseContract.BasePresenter{
        void onFinishedLoadingData();

        void onLoadingData();
    }

    interface AdderPresenter extends BaseContract.BasePresenter{

        void onAddStudentSuccess(Student student);

        void onAddStudentFailed();
    }


    interface UpdaterPresenter extends BaseContract.BasePresenter{
        void onUpdateStudentSuccess(Student student);

        void onUpdateStudentFailed();
    }

    interface DeleterPresenter extends BaseContract.BasePresenter{
        void onDeleteStudentSuccess(Student student);

        void onDeleteStudentFailed();
    }

    interface LoaderView extends BaseContract.BaseView{
        void showProgressBar();

        void hideProgressBar();
    }
}
