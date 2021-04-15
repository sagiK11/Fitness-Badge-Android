package com.sagikor.android.fitracker.ui.contracts;

public interface SignInActivityContract {
    interface Presenter extends BaseContract.BasePresenter {
        void onLoginClick();

        void onRegisterClick();

        void onResetPasswordClick();

        void bind(SignInActivityContract.View view);

        void unbind();

        void onStart();

        void onSignInSuccess();

        void onSignInFailure();

        void onResetPassSuccess();

        void onResetPassFailure();
    }

    interface View extends BaseContract.BaseView {
        void navToHomeScreen();

        void navSignedUserToHomeScreen();

        void navToRegisterScreen();

        void showProgressBar();

        void hideProgressBar();

        void setErrorInEmail();

        void setErrorInPassword();

        String getUserEmail();

        String getUserPassword();

        void popError(String error);

        void popSignInFailureMessage();

        void notifyUserToCheckMail();

        void notifyUserToFillEmail();

    }
}
