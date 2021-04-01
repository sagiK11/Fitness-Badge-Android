package com.sagikor.android.fitracker.data.db;


import com.sagikor.android.fitracker.data.db.firebase.FirebaseHandler;
import com.sagikor.android.fitracker.data.db.sharedprefrences.SharedPreferencesHandler;

public class AppDatabaseHandler implements DatabaseHandler {
    private static final AppDatabaseHandler appDatabaseHandler = init();
    private FirebaseHandler firebaseHandler;
    private SharedPreferencesHandler sharedPreferencesHandler;

    private AppDatabaseHandler(){}

    private static AppDatabaseHandler init(){
        return new AppDatabaseHandler();
    }
    public static AppDatabaseHandler getInstance(){
        return appDatabaseHandler;
    }
}
