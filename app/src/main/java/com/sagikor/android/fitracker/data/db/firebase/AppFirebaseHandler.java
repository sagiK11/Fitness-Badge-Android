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
    private static List<Student> studentList;
    private static List<UserClass> classesList;
    private final static String TAG = "AppFirebaseHandler";
    private BaseContract.LoaderPresenter loaderPresenter;
    private BaseContract.AdderPresenter adderPresenter;
    private BaseContract.UpdaterPresenter updaterPresenter;
    private BaseContract.DeleterPresenter deleterPresenter;
    private BaseContract.SignInPresenter signInPresenter;
    private BaseContract.RegisterPresenter registerPresenter;
    private FirebaseAuth firebaseAuth;
    private boolean isDataLoaded;
    private boolean isStudentsLoaded;
    private boolean isClassesLoaded;

    private static AppFirebaseHandler init() {
        studentList = new ArrayList<>();
        classesList = new ArrayList<>();
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
        Thread t1 = new Thread(this::getAllDataFromDatabase);
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    private void getAllDataFromDatabase() {
        //get Students
        final String user_id = getUserId();
        final String students_child = getStudentsChild();
        final DatabaseReference students_node = FirebaseDatabase.getInstance().getReference("users")
                .child(user_id).child(students_child);
        students_node.get().addOnSuccessListener(dataSnapshot -> {
            Log.i(TAG, "starting to load user students..");
            dataSnapshot.getChildren().forEach(dataSnapshot1 -> {
                Log.d(TAG, "loading student");
                studentList.add(dataSnapshot1.getValue(Student.class));
            });
        }).addOnCompleteListener(e -> {
            Log.i(TAG, "finished loading user students");
            isStudentsLoaded = true;
        });
        // get classes
        final DatabaseReference classes_node = FirebaseDatabase.getInstance().getReference("users")
                .child(user_id).child(getClassesChild());
        classes_node.get().addOnSuccessListener(dataSnapshot -> {
            Log.i(TAG, "starting to load user classes..");
            dataSnapshot.getChildren().forEach(dataSnapshot1 -> {
                Log.d(TAG, "loading class: " + dataSnapshot1.getValue(UserClass.class).getClassName());
                classesList.add(dataSnapshot1.getValue(UserClass.class));
            });
        }).addOnCompleteListener(e -> {
            Log.i(TAG, "finished loading user classes");
            isClassesLoaded = true;
            if (loaderPresenter != null) loaderPresenter.onFinishedLoadingData();
        });
    }


    @Override
    public void addStudent(Student student) {
        final String user_id = getUserId();
        final String students_child = getStudentsChild();
        FirebaseDatabase.getInstance().getReference("users").child(user_id)
                .child(students_child).child(student.getKey()).setValue(student)
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
        final String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String students_child = getStudentsChild();
        Log.d(TAG, "updating " + student.getName() + " in firebase");
        FirebaseDatabase.getInstance().getReference("users").
                child(user_id).child(students_child).child(student.getKey()).
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
        final String user_id = getUserId();
        final String students_child = getClassesChild();
        FirebaseDatabase.getInstance().getReference("users")
                .child(user_id).child(students_child).child(student.getKey())
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
        final String user_id = getUserId();
        final String class_child = getClassesChild();

        String classKey = FirebaseDatabase.getInstance().getReference("users").push().getKey();
        classToTeach.setKey(classKey);
        FirebaseDatabase.getInstance().getReference("users").child(user_id)
                .child(class_child).child(classKey).setValue(classToTeach)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        classesList.add(classToTeach);
                    }
                });

    }

    @Override
    public void deleteClassUserTeaches(UserClass classToTeach) {
        String user_id = getUserId();
        String classes_child = getClassesChild();
        FirebaseDatabase.getInstance().getReference("users")
                .child(user_id).child(classes_child).child(classToTeach.getKey())
                .removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //deleterPresenter.onDeleteClassSuccess(classToTeach); TODO
            } else {
                // deleterPresenter.onDeleteClassFailed();
            }
        });
    }

    @Override
    public void clearDatabase() {
        final String user_id = getUserId();
        final String students_child = getStudentsChild();
        FirebaseDatabase.getInstance().getReference("users").child(user_id).child(students_child).removeValue();
    }

    @Override
    public void deleteAccount() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).removeValue();

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

                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("users");
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

    private String getStudentsChild() {
        return "students";
    }

    private String getClassesChild() {
        return "classes";
    }

}
