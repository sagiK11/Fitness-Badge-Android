package com.sagikor.android.fitracker.data.db.firebase;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sagikor.android.fitracker.data.model.Student;
import com.sagikor.android.fitracker.data.model.User;
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

import java.util.ArrayList;
import java.util.List;

public class AppFirebaseHandler implements FirebaseHandler {
    private static final AppFirebaseHandler appFirebaseHandler = init();
    private List<Student> studentList;
    private List<UserClass> classesList;
    private String userName;
    private static final String USERS_CHILD = "users";
    private static final String STUDENTS_CHILD = "students";
    private static final String CLASSES_CHILD = "classes";
    private static final String TAG = "AppFirebaseHandler";
    private FirebaseAuth firebaseAuth;
    private boolean isStudentsLoaded;
    private boolean isClassesLoaded;

    private static AppFirebaseHandler init() {
        return new AppFirebaseHandler();
    }


    public static AppFirebaseHandler getInstance() {
        return appFirebaseHandler;
    }

    private AppFirebaseHandler() {
    }

    @Override
    public List<Student> getStudents() {
        return studentList;
    }

    @Override
    public void getDataFromDatabase(MainActivityContract.Presenter loaderPresenter) {
        loaderPresenter.onLoadingData();
        getUserNameData();
        getStudentsData();
        getUserClassesData(loaderPresenter);
    }

    private void getUserClassesData(MainActivityContract.Presenter loaderPresenter) {
        final DatabaseReference classes_node = FirebaseDatabase.getInstance().getReference(USERS_CHILD)
                .child(getUserId()).child(CLASSES_CHILD);

        classes_node.addListenerForSingleValueEvent((AppValueEventListener) snapshot -> {
            classesList = new ArrayList<>();
            Log.i(TAG, "starting to load user classes..");
            for (DataSnapshot obj : snapshot.getChildren()) {
                classesList.add(obj.getValue(UserClass.class));
            }
            isClassesLoaded = true;
            Log.i(TAG, "finished loading user classes");
            loaderPresenter.onFinishedLoadingData();
        });
    }

    private void getStudentsData() {
        final DatabaseReference students_node = FirebaseDatabase.getInstance()
                .getReference(USERS_CHILD).child(getUserId()).child(STUDENTS_CHILD);

        students_node.addListenerForSingleValueEvent((AppValueEventListener) snapshot -> {
            studentList = new ArrayList<>();
            Log.i(TAG, "starting to load user students..");
            for (DataSnapshot obj : snapshot.getChildren())
                studentList.add(obj.getValue(Student.class));
            isStudentsLoaded = true;
            Log.i(TAG, "finished loading user students");
        });
    }

    private void getUserNameData() {
        final DatabaseReference rf = FirebaseDatabase.getInstance()
                .getReference(USERS_CHILD).child(getUserId()).child("fullName");

        rf.addListenerForSingleValueEvent((AppValueEventListener)
                snapshot -> userName = snapshot.getValue(String.class));
    }


    @Override
    public void addStudent(AddStudentActivityContract.Presenter adderPresenter, Student student) {
        FirebaseDatabase.getInstance().getReference(USERS_CHILD).child(getUserId())
                .child(STUDENTS_CHILD).child(student.getKey()).setValue(student)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        adderPresenter.onAddStudentSuccess(student);
                        studentList.add(student);
                    } else {
                        adderPresenter.onAddStudentFailed();
                    }
                });
    }

    @Override
    public void checkStudentExistsInFirebase(Student student) throws AppExceptions.StudentExistsAlready {
        for (Student curr : studentList) {
            if (student.getName().equals(curr.getName()) &&
                    student.getStudentClass().equals(curr.getStudentClass())) {
                throw new AppExceptions.StudentExistsAlready();
            }
        }

    }

    @Override
    public void updateStudent(UpdateStudentActivityContract.Presenter updaterPresenter, Student student) {
        FirebaseDatabase.getInstance().getReference(USERS_CHILD).
                child(getUserId()).child(STUDENTS_CHILD).child(student.getKey()).
                setValue(student).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                updaterPresenter.onUpdateStudentSuccess(student);
                updateLocalList(student);
            } else {
                updaterPresenter.onUpdateStudentFailed();
            }
        });
    }

    private void updateLocalList(Student student) {
        for (int i = 0; i < studentList.size(); i++) {
            Student cur = studentList.get(i);
            if (cur.getKey().equals(student.getKey())) {
                studentList.set(i, student);
                return;
            }
        }
    }

    @Override
    public void deleteStudent(ViewStudentsActivityContract.Presenter deleterPresenter, Student student) {
        //delete in firebase
        FirebaseDatabase.getInstance().getReference(USERS_CHILD)
                .child(getUserId()).child(STUDENTS_CHILD).child(student.getKey())
                .removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                deleterPresenter.onDeleteStudentSuccess(student);
            } else {
                deleterPresenter.onDeleteStudentFailed();
            }
        });
        //delete in local list
        //this is being done in the adapter
    }

    @Override
    public List<UserClass> getClassesUserTeaches() {
        return classesList;
    }

    @Override
    public void addClassUserTeaches(AddClassesActivityContract.Presenter presenter, UserClass classToTeach) {
        String classKey = FirebaseDatabase.getInstance().getReference(USERS_CHILD).push().getKey();
        classToTeach.setKey(classKey);

        assert (classKey != null) : "oops: classKey is null";
        FirebaseDatabase.getInstance().getReference(USERS_CHILD).child(getUserId())
                .child(CLASSES_CHILD).child(classKey).setValue(classToTeach)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        classesList.add(classToTeach);
                        presenter.onAddClassSuccess(classToTeach);
                    } else {
                        presenter.onAddSClassFailed();
                    }
                });

    }

    @Override
    public void deleteClassUserTeaches(AddClassesActivityContract.Presenter presenter, UserClass classToTeach) {
        FirebaseDatabase.getInstance().getReference(USERS_CHILD)
                .child(getUserId()).child(CLASSES_CHILD).child(classToTeach.getKey())
                .removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                presenter.onDeleteClassSuccess(classToTeach);
            } else {
                presenter.onDeleteClassFailed();
            }
        });
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void clearDatabase(SettingsActivityContract.Presenter deleterPresenter) {
        FirebaseDatabase.getInstance().getReference(USERS_CHILD)
                .child(getUserId()).child(STUDENTS_CHILD).removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        studentList.clear();
                        deleterPresenter.onDeleteStudentSuccess(null);
                    } else {
                        deleterPresenter.onDeleteStudentFailed();
                    }
                });
    }

    @Override
    public void deleteAccount(SettingsActivityContract.Presenter presenter) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference(USERS_CHILD).child(getUserId()).removeValue();

        assert (user != null) : "oops: user is null";
        user.delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                signOut();
                presenter.onDeleteAccountSuccess();
                Log.i(TAG, "user account deleted.");
            } else {
                presenter.onDeleteAccountFailure();
            }
        });
    }

    @Override
    public boolean isDataLoaded() {
        return isStudentsLoaded && isClassesLoaded;
    }

    @Override
    public void signInWithEmailAndPassword(SignInActivityContract.Presenter sPresenter, String email, String password) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.i(TAG, "signInWithEmail:success");
                        sPresenter.onSignInSuccess();
                    } else {
                        sPresenter.onSignInFailure();
                        assert task.getException() != null : "oops: task's exception is null";
                        Log.e(TAG, task.getException().toString());
                    }
                });
    }

    @Override
    public void createUserWithEmailAndPassword(RegisterActivityContract.Presenter presenter,
                                               String email, String password, String userName) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        assert (firebaseAuth.getCurrentUser() != null) : "oops: user is null";
                        String userId = firebaseAuth.getCurrentUser().getUid();
                        User newUser = new User(userName, email, userId);

                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(USERS_CHILD);
                        dbRef.child(userId).setValue(newUser).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                presenter.onRegisterSuccess();
                            }
                        });
                    } else {
                        presenter.onRegisterFailure(task);
                    }
                });
    }

    @Override
    public void resetPassword(SignInActivityContract.Presenter presenter, String userEmail) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.sendPasswordResetEmail(userEmail)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        presenter.onResetPassSuccess();
                    } else {
                        presenter.onResetPassFailure();
                    }
                });
    }

    @Override
    public boolean isUserSigned() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user != null;
    }

    private String getUserId() {
        assert FirebaseAuth.getInstance().getCurrentUser() != null : "oops: user is null";
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        isStudentsLoaded = false;
        isClassesLoaded = false;
    }


    private interface AppValueEventListener extends ValueEventListener {
        @Override
        default void onCancelled(@NonNull DatabaseError error) {
            Log.i(TAG, error.getMessage());
        }
    }

}
