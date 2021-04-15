package com.sagikor.android.fitracker.ui.contracts;

public interface MainActivityContract {
    interface Presenter extends BaseContract.BasePresenter {
        void onSendToEmailClick();

        void onNavToSettingsClick();

        void onNavToAddStudentClick();

        void onNavToUpdateStudentClick();

        void onNavToStatisticsClick();

        void onNavToViewStudentsClick();

        void onCompleteDataWrite();

        void onDisconnectClick();

        String getStudentsAsCSV();

        void bind(MainActivityContract.View view);

        void unbind();

        void onFinishedLoadingData();

        void onLoadingData();

    }

    interface View extends BaseContract.BaseView {
        void navToAddStudentScreen();

        void navToUpdateStudentScreen();

        void navToViewStudentsScreen();

        void navToSettingsScreen();

        void navToStatisticsScreen();

        void disconnectUser();

        void sendDatabaseToEmail();

        void setActiveMode();

        void setLoadingMode();

        void showProgressBar();

        void hideProgressBar();

    }
}
