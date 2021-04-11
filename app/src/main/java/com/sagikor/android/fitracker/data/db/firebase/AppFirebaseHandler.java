package com.sagikor.android.fitracker.data.db.firebase;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sagikor.android.fitracker.R;
import com.sagikor.android.fitracker.data.model.Student;
import com.sagikor.android.fitracker.data.model.User;
import com.sagikor.android.fitracker.data.model.UserClass;
import com.sagikor.android.fitracker.ui.contracts.BaseContract;
import com.sagikor.android.fitracker.ui.contracts.ViewStudentsActivityContract;
import com.sagikor.android.fitracker.utils.AppExceptions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class AppFirebaseHandler implements FirebaseHandler {
    public static AppFirebaseHandler appFirebaseHandler = init();
    private List<Student> studentList;
    private List<UserClass> classesList;
    private String userName;
    private final static String USERS_CHILD = "users";
    private final static String STUDENT_CHILD = "students";
    private final static String CLASSES_CHILD = "classes";
    private final static String TAG = "AppFirebaseHandler";
    private BaseContract.LoaderPresenter loaderPresenter;
    private BaseContract.AdderPresenter adderPresenter;
    private BaseContract.UpdaterPresenter updaterPresenter;
    private BaseContract.DeleterPresenter deleterPresenter;
    private BaseContract.SignInPresenter signInPresenter;
    private BaseContract.RegisterPresenter registerPresenter;
    private BaseContract.ClassAdderPresenter classAdderPresenter;
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
        if (isUserSigned()) {
            getDataFromDatabase();
        }
    }


    @Override
    public List<Student> getStudents() {
        return studentList;
    }

    @Override
    public void getDataFromDatabase() {
        getUserNameData();
        getStudentsData();
        getUserClassesData();
    }

    private void getUserClassesData() {
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
            if (loaderPresenter != null)
                loaderPresenter.onFinishedLoadingData();
        });
    }

    private void getStudentsData() {
        final DatabaseReference students_node = FirebaseDatabase.getInstance()
                .getReference(USERS_CHILD).child(getUserId()).child(STUDENT_CHILD);

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
    public void addStudent(Student student) {
        FirebaseDatabase.getInstance().getReference(USERS_CHILD).child(getUserId())
                .child(STUDENT_CHILD).child(student.getKey()).setValue(student)
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
    public void updateStudent(Student student) {
        Log.i(TAG, "updating " + student.getName() + " in firebase");
        FirebaseDatabase.getInstance().getReference(USERS_CHILD).
                child(getUserId()).child(STUDENT_CHILD).child(student.getKey()).
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
    public void deleteStudent(Student student) {
        //delete in firebase
        FirebaseDatabase.getInstance().getReference(USERS_CHILD)
                .child(getUserId()).child(STUDENT_CHILD).child(student.getKey())
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
    public void addClassUserTeaches(UserClass classToTeach) {
        String classKey = FirebaseDatabase.getInstance().getReference(USERS_CHILD).push().getKey();
        classToTeach.setKey(classKey);

        FirebaseDatabase.getInstance().getReference(USERS_CHILD).child(getUserId())
                .child(CLASSES_CHILD).child(classKey).setValue(classToTeach)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        classesList.add(classToTeach);
                        classAdderPresenter.onAddClassSuccess(classToTeach);
                    } else {
                        classAdderPresenter.onAddSClassFailed();
                    }
                });

    }

    @Override
    public void deleteClassUserTeaches(UserClass classToTeach) {
        FirebaseDatabase.getInstance().getReference(USERS_CHILD)
                .child(getUserId()).child(CLASSES_CHILD).child(classToTeach.getKey())
                .removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //deleterPresenter.onDeleteClassSuccess(classToTeach); TODO
            } else {
                // deleterPresenter.onDeleteClassFailed();
            }
        });
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void clearDatabase() {
        FirebaseDatabase.getInstance().getReference(USERS_CHILD)
                .child(getUserId()).child(STUDENT_CHILD).removeValue();
    }

    @Override
    public void deleteAccount() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference(USERS_CHILD).child(getUserId()).removeValue();

        user.delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "User account deleted.");
            }
        });
    }

    @Override
    public void setLoaderPresenter(BaseContract.LoaderPresenter presenter) {
        loaderPresenter = presenter;
    }

    @Override
    public void setAdderPresenter(BaseContract.AdderPresenter presenter) {
        adderPresenter = presenter;
    }

    @Override
    public void setUpdaterPresenter(BaseContract.UpdaterPresenter presenter) {
        updaterPresenter = presenter;
    }

    @Override
    public void setDeleterPresenter(BaseContract.DeleterPresenter presenter) {
        deleterPresenter = presenter;
    }

    @Override
    public void setSignInPresenter(BaseContract.SignInPresenter presenter) {
        signInPresenter = presenter;
    }

    @Override
    public void setRegisterPresenter(BaseContract.RegisterPresenter presenter) {
        registerPresenter = presenter;
    }

    @Override
    public void setClassAdderPresenter(BaseContract.ClassAdderPresenter presenter) {
        classAdderPresenter = presenter;
    }

    @Override
    public boolean isDataLoaded() {
        return isStudentsLoaded && isClassesLoaded;
    }

    @Override
    public void signInWithEmailAndPassword(String email, String password) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        getDataFromDatabase();
                        signInPresenter.onSignInSuccess();

                    } else {
                        signInPresenter.onSignInFailure();
                        Log.e(TAG, task.getException().toString());
                    }
                });
    }

    @Override
    public void createUserWithEmailAndPassword(String email, String password, String userName) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = firebaseAuth.getCurrentUser().getUid();
                        User newUser = new User(userName, email, userId);

                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(USERS_CHILD);
                        dbRef.child(userId).setValue(newUser).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                getDataFromDatabase();
                                registerPresenter.onRegisterSuccess();
                            }
                        });
                    } else {
                        registerPresenter.onRegisterFailure(task);
                    }
                });
    }

    @Override
    public void resetPassword(String userEmail) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.sendPasswordResetEmail(userEmail)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        signInPresenter.onResetPassSuccess();
                    } else {
                        signInPresenter.onResetPassFailure();
                    }
                });
    }

    @Override
    public boolean isUserSigned() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user != null;
    }

    private String getUserId() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    private interface AppValueEventListener extends ValueEventListener {
        @Override
        default void onCancelled(@NonNull DatabaseError error) {
            Log.i(TAG, error.getMessage());
        }

    }

}
