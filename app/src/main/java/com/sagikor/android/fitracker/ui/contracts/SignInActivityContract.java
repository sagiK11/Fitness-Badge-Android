package com.sagikor.android.fitracker.ui.contracts;

public interface SignInActivityContract {
    interface Presenter {
        void onLoginClick();

        void onRegisterClick();

        void onResetPasswordClick();

        void bind(SignInActivityContract.View view);

        void unbind();
    }

    interface View {

    }
}
