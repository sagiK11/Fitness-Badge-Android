package com.sagikor.android.fitracker.data.db;


import android.content.SharedPreferences;

import com.sagikor.android.fitracker.data.model.Student;
import com.sagikor.android.fitracker.data.db.firebase.AppFirebaseHandler;
import com.sagikor.android.fitracker.data.db.firebase.FirebaseHandler;
import com.sagikor.android.fitracker.data.db.sharedprefrences.AppSharedPreferencesHandler;
import com.sagikor.android.fitracker.data.db.sharedprefrences.SharedPreferencesHandler;

import java.util.List;

public class AppDatabaseHandler implements DatabaseHandler {
    private static final AppDatabaseHandler appDatabaseHandler = init();
    private final FirebaseHandler firebaseHandler = AppFirebaseHandler.getInstance();
    private final SharedPreferencesHandler sharedPreferencesHandler = AppSharedPreferencesHandler.getInstance();
    private Object cachedObject;

    private static AppDatabaseHandler init() {
        return new AppDatabaseHandler();
    }

    public static AppDatabaseHandler getInstance() {
        return appDatabaseHandler;
    }

    private AppDatabaseHandler() {
        //Not meant to be initiated
    }

    @Override
    public List<Student> getStudents() {
        return firebaseHandler.getStudents();
    }

    @Override
    public void addStudent(Student student) {
        firebaseHandler.addStudent(student);
    }

    @Override
    public void updateStudent(Student student) {
        firebaseHandler.updateStudent(student);
    }

    @Override
    public void deleteStudent(Student student) {
        firebaseHandler.deleteStudent(student);
    }

    @Override
    public void clearDatabase() {
        firebaseHandler.clearDatabase();
    }

    @Override
    public void deleteAccount() {
        firebaseHandler.deleteAccount();
    }

    @Override
    public boolean isStudentExistsInFirebase(Student student) {
        return firebaseHandler.isStudentExistsInFirebase(student);
    }

    @Override
    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        sharedPreferencesHandler.setSharedPreferences(sharedPreferences);
    }

    @Override
    public boolean isGirlsSwitchOn() {
        return sharedPreferencesHandler.isGirlsSwitchOn();
    }

    @Override
    public boolean isBoysSwitchOn() {
        return sharedPreferencesHandler.isBoysSwitchOn();
    }

    @Override
    public void editGenderPreferences(String gender, boolean isChecked) {
        sharedPreferencesHandler.editGenderPreferences(gender,isChecked);
    }

    @Override
    public void cacheObject(Object obj) {
        cachedObject = obj;
    }

    @Override
    public Object getCachedObject() {
        return cachedObject;
    }

    @Override
    public void clearCache() {
        cachedObject = null;
    }
}
