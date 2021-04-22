package com.sagikor.android.fitracker.ui.contracts;

public interface MainActivityContract {
    interface Presenter extends BaseContract.BasePresenter {
        void onSendToEmailClick();

        void onNavToSettingsClick();

        void onNavToAddStudentClick();

        void onNavToViewStudentsClick();

        void onNavToAddClassesClick();

        void onNavToStatisticsClick();

        void onOpenDrawer();

        void onNavToTermsOfUseClick();

        void onNavToRateUsClick();

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

        void navToAddClassesScreen();

        void navToViewStudentsScreen();

        void navToSettingsScreen();

        void navToStatisticsScreen();

        void navToTermsOfUseUsUrl();

        void navToRateUs();

        void openNavDrawer();

        void disconnectUser();

        void sendDatabaseToEmail();

        void setActiveMode();

        void setLoadingMode();

        void showProgressBar();

        void hideProgressBar();

        void setUserName(String name);

    }
}
