package com.sagikor.android.fitracker.data.db;

import com.sagikor.android.fitracker.data.db.firebase.FirebaseHandler;
import com.sagikor.android.fitracker.data.db.sharedprefrences.SharedPreferencesHandler;

public interface DatabaseHandler extends FirebaseHandler, SharedPreferencesHandler {

    void cacheObject(int objIndex);

    Object getCachedObject();

}
