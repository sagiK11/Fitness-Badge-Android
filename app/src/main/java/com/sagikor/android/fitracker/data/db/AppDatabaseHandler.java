package com.sagikor.android.fitracker.data.db;


import android.content.SharedPreferences;

import com.sagikor.android.fitracker.data.model.Student;
import com.sagikor.android.fitracker.data.db.firebase.AppFirebaseHandler;
import com.sagikor.android.fitracker.data.db.firebase.FirebaseHandler;
import com.sagikor.android.fitracker.data.db.sharedprefrences.AppSharedPreferencesHandler;
import com.sagikor.android.fitracker.data.db.sharedprefrences.SharedPreferencesHandler;
import com.sagikor.android.fitracker.ui.contracts.BaseContract;


import java.util.List;

public class AppDatabaseHandler implements DatabaseHandler {
    private static final AppDatabaseHandler appDatabaseHandler = init();
    private final FirebaseHandler firebaseHandler = AppFirebaseHandler.getInstance();
    private final SharedPreferencesHandler sharedPreferencesHandler = AppSharedPreferencesHandler.getInstance();
    private int cachedObjectIndex;

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
    public void setLoaderPresenter(BaseContract.LoaderPresenter presenter) {
        firebaseHandler.setLoaderPresenter(presenter);
    }

    @Override
    public void setAdderPresenter(BaseContract.AdderPresenter presenter) {
        firebaseHandler.setAdderPresenter(presenter);
    }

    @Override
    public void setUpdaterPresenter(BaseContract.UpdaterPresenter presenter) {
        firebaseHandler.setUpdaterPresenter(presenter);
    }

    @Override
    public void setDeleterPresenter(BaseContract.DeleterPresenter presenter) {
        firebaseHandler.setDeleterPresenter(presenter);
    }

    @Override
    public void setSignInPresenter(BaseContract.SignInPresenter presenter) {
        firebaseHandler.setSignInPresenter(presenter);
    }

    @Override
    public void setRegisterPresenter(BaseContract.RegisterPresenter presenter) {
        firebaseHandler.setRegisterPresenter(presenter);
    }

    @Override
    public boolean isLoadingData() {
        return firebaseHandler.isLoadingData();
    }

    @Override
    public void signInWithEmailAndPassword(String email, String password) {
        firebaseHandler.signInWithEmailAndPassword(email,password);
    }

    @Override
    public void createUserWithEmailAndPassword(String email, String password, String name) {
        firebaseHandler.createUserWithEmailAndPassword(email,password,name);
    }

    @Override
    public void resetPassword(String userEmail) {
        firebaseHandler.resetPassword(userEmail);
    }

    @Override
    public boolean isUserSigned() {
        return firebaseHandler.isUserSigned();
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
        sharedPreferencesHandler.editGenderPreferences(gender, isChecked);
    }

    @Override
    public void cacheObject(int index) {
        cachedObjectIndex = index;
    }

    @Override
    public Student getCachedObject() {
        return getStudents().get(cachedObjectIndex);
    }
}
