package com.sagikor.android.fitracker.ui.contracts;

public interface StatisticsActivityContract {
    interface Presenter {
        void bind(StatisticsActivityContract.View view);

        void unbind();

    }

    interface View {

    }
}
