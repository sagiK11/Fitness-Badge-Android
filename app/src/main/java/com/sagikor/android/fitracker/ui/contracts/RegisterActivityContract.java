package com.sagikor.android.fitracker.ui.contracts;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface RegisterActivityContract {
    interface Presenter extends BaseContract.BasePresenter {
        void bind(RegisterActivityContract.View view);

        void unbind();

        void onRegisterClick();

        void onRegisterSuccess();

        void onRegisterFailure(Task<AuthResult> task);

    }

    interface View extends BaseContract.BaseView {

        String getUserEmail();

        String getUserPassword();

        String getUserName();

        void navToHomeScreen();

        void notifyErrorsToUser(Task<AuthResult> task);

        void setEmailError();

        void setPasswordError();

        void setWeakPassError();

        void setNameError();

        void showProgressBar();

        void hideProgressBar();

    }
}
