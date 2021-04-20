package com.sagikor.android.fitracker.ui.contracts;


public interface BaseContract {
    interface BasePresenter {

    }

    interface BaseView {
        enum msgType {
            alert, success, fail, dangerous
        }

        void popMessage(String message, msgType type);
    }

}
