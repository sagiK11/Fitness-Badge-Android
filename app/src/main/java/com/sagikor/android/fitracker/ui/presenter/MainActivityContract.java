package com.sagikor.android.fitracker.ui.presenter;

public interface MainActivityContract {
    interface Presenter{
        void onSendToEmailClick();
        void onNavToSettingsClick();
        void onNavToAddStudentClick();
        void onNavToUpdateStudentClick();
        void onNavToStatisticsClick();
        void onNavToViewStudentsClick();
        void onDisconnectClick();
        void bind(MainActivityContract.View view);
        void unbind();

    }
    interface View{
        void navToAddStudentScreen();
        void navToUpdateStudentScreen();
        void navToViewStudentsScreen();
        void navToSettingsScreen();
        void navToStatisticsScreen();
        void disconnectUser();
        void sendDatabaseToEmail();

    }
}
