package com.sagikor.android.fitracker.data.db.firebase;

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
import com.sagikor.android.fitracker.ui.contracts.BaseContract;
import com.sagikor.android.fitracker.ui.contracts.ViewStudentsActivityContract;
import com.sagikor.android.fitracker.utils.AppExceptions;

import java.util.ArrayList;
import java.util.List;

public class AppFirebaseHandler implements FirebaseHandler {
    public static AppFirebaseHandler appFirebaseHandler = init();
    private static List<Student> studentList;
    private final static String TAG = "AppFirebaseHandler";
    private BaseContract.LoaderPresenter loaderPresenter;
    private BaseContract.AdderPresenter adderPresenter;
    private BaseContract.UpdaterPresenter updaterPresenter;
    private BaseContract.DeleterPresenter deleterPresenter;
    private BaseContract.SignInPresenter signInPresenter;
    private BaseContract.RegisterPresenter registerPresenter;
    private FirebaseAuth firebaseAuth;
    private boolean isDataLoaded;

    private static AppFirebaseHandler init() {
        studentList = new ArrayList<>();
        return new AppFirebaseHandler();
    }


    public static AppFirebaseHandler getInstance() {
        return appFirebaseHandler;
    }

    private AppFirebaseHandler() {
        if (isUserSigned())
            getDataFromDatabase();
    }


    @Override
    public List<Student> getStudents() {
        return studentList;
    }

    @Override
    public void getDataFromDatabase() {
        new Thread(() -> {
            studentList.clear();
            final String USER_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            final String debug_id = "IPyH6cDYq5TKJ9gpDwD81eHwKG13";//TODO don't forget this
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(debug_id);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (loaderPresenter != null)
                        loaderPresenter.onLoadingData();
                    studentList.clear();
                    Log.d(TAG, "loading students from firebase..");
                    final String STUDENTS_CHILD = "students";
                    for (DataSnapshot obj : dataSnapshot.child(STUDENTS_CHILD).getChildren()) {
                        Student student = obj.getValue(Student.class);
                        studentList.add(student);
                    }
                    if (loaderPresenter != null)
                        loaderPresenter.onFinishedLoadingData();
                    isDataLoaded = true;
                    Log.d(TAG, "finished loading students from firebase");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.v(TAG, databaseError.getDetails());
                }
            });
        }).start();

    }

    @Override
    public void addStudent(Student student) {
        final String USER_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String STUDENTS_CHILD = "students";
        FirebaseDatabase.getInstance().getReference("users").child(USER_ID)
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
    public void updateStudent(Student student) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String studentsChild = "students";
        Log.d(TAG, "updating " + student.getName() + " in firebase");
        FirebaseDatabase.getInstance().getReference("users").
                child(userId).child(studentsChild).child(student.getKey()).
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
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("users")
                .child(userId).child("students").child(student.getKey())
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
    public void clearDatabase() {
        final String USER_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String STUDENTS_CHILD = "students";
        FirebaseDatabase.getInstance().getReference("users").child(USER_ID).child(STUDENTS_CHILD).removeValue();
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
        return isDataLoaded;
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

}
