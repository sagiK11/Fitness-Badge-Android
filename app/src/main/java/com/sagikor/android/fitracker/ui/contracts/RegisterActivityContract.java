package com.sagikor.android.fitracker.ui.contracts;

public interface RegisterActivityContract {
    interface Presenter {
        void bind(RegisterActivityContract.View view);

        void unbind();

    }

    interface View {

    }
}
