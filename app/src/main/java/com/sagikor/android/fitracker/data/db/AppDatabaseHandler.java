package com.sagikor.android.fitracker.data.db;


import android.content.SharedPreferences;

import com.sagikor.android.fitracker.data.model.Student;
import com.sagikor.android.fitracker.data.db.firebase.AppFirebaseHandler;
import com.sagikor.android.fitracker.data.db.firebase.FirebaseHandler;
import com.sagikor.android.fitracker.data.db.sharedprefrences.AppSharedPreferencesHandler;
import com.sagikor.android.fitracker.data.db.sharedprefrences.SharedPreferencesHandler;
import com.sagikor.android.fitracker.data.model.UserClass;
import com.sagikor.android.fitracker.ui.contracts.AddClassesActivityContract;
import com.sagikor.android.fitracker.ui.contracts.AddStudentActivityContract;
import com.sagikor.android.fitracker.ui.contracts.MainActivityContract;
import com.sagikor.android.fitracker.ui.contracts.RegisterActivityContract;
import com.sagikor.android.fitracker.ui.contracts.SettingsActivityContract;
import com.sagikor.android.fitracker.ui.contracts.SignInActivityContract;
import com.sagikor.android.fitracker.ui.contracts.UpdateStudentActivityContract;
import com.sagikor.android.fitracker.ui.contracts.ViewStudentsActivityContract;
import com.sagikor.android.fitracker.utils.AppExceptions;


import java.util.List;

public class AppDatabaseHandler implements DatabaseHandler {
    private static final AppDatabaseHandler appDatabaseHandler = init();
    private final FirebaseHandler firebaseHandler = AppFirebaseHandler.getInstance();
    private final SharedPreferencesHandler sharedPreferencesHandler = AppSharedPreferencesHandler.getInstance();
    private String cachedObjectKey;

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
    public void getDataFromDatabase(MainActivityContract.Presenter presenter) {
        firebaseHandler.getDataFromDatabase(presenter);
    }

    @Override
    public List<Student> getStudents() {
        return firebaseHandler.getStudents();
    }

    @Override
    public void addStudent(AddStudentActivityContract.Presenter presenter, Student student) {
        firebaseHandler.addStudent(presenter, student);
    }

    @Override
    public void updateStudent(UpdateStudentActivityContract.Presenter presenter, Student student) {
        firebaseHandler.updateStudent(presenter, student);
    }

    @Override
    public void deleteStudent(ViewStudentsActivityContract.Presenter presenter, Student student) {
        firebaseHandler.deleteStudent(presenter, student);
    }

    @Override
    public void clearDatabase(SettingsActivityContract.Presenter presenter) {
        firebaseHandler.clearDatabase(presenter);
    }

    @Override
    public void deleteAccount(SettingsActivityContract.Presenter presenter) {
        firebaseHandler.deleteAccount(presenter);
    }

    @Override
    public boolean isDataLoaded() {
        return firebaseHandler.isDataLoaded();
    }

    @Override
    public void signInWithEmailAndPassword(SignInActivityContract.Presenter signInPresenter,
                                           String email, String password) {
        firebaseHandler.signInWithEmailAndPassword(signInPresenter, email, password);
    }

    @Override
    public void createUserWithEmailAndPassword(RegisterActivityContract.Presenter presenter,
                                               String email, String password, String name) {
        firebaseHandler.createUserWithEmailAndPassword(presenter, email, password, name);
    }

    @Override
    public void resetPassword(SignInActivityContract.Presenter presenter, String userEmail) {
        firebaseHandler.resetPassword(presenter, userEmail);
    }

    @Override
    public boolean isUserSigned() {
        return firebaseHandler.isUserSigned();
    }

    @Override
    public void checkStudentExistsInFirebase(Student student) throws AppExceptions.StudentExistsAlready {
        firebaseHandler.checkStudentExistsInFirebase(student);
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
    public List<UserClass> getClassesUserTeaches() {
        return firebaseHandler.getClassesUserTeaches();
    }

    @Override
    public void addClassUserTeaches(AddClassesActivityContract.Presenter presenter,
                                    UserClass classToTeach) {
        firebaseHandler.addClassUserTeaches(presenter, classToTeach);
    }

    @Override
    public void deleteClassUserTeaches(AddClassesActivityContract.Presenter presenter,
                                       UserClass classToTeach) {
        firebaseHandler.deleteClassUserTeaches(presenter, classToTeach);
    }

    @Override
    public String getUserName() {
        return firebaseHandler.getUserName();
    }

    @Override
    public void cacheObject(String objKey) {
        cachedObjectKey = objKey;
    }

    @Override
    public Student getCachedObject() {
        for (Student student : getStudents()) {
            if (student.getKey().equals(cachedObjectKey)) {
                return student;
            }
        }
        throw new AppExceptions.StudentNotFound();
    }

    @Override
    public void signOut() {
        firebaseHandler.signOut();
    }
}
