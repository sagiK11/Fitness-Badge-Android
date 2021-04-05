package com.sagikor.android.fitracker.data.db.firebase;

import android.util.Log;

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
import com.sagikor.android.fitracker.data.model.Student;
import com.sagikor.android.fitracker.ui.contracts.BaseContract;
import com.sagikor.android.fitracker.ui.contracts.ViewStudentsActivityContract;

import java.util.ArrayList;
import java.util.List;

public class AppFirebaseHandler implements FirebaseHandler {
    public static AppFirebaseHandler appFirebaseHandler = init();
    private static List<Student> studentList;
    private final static String TAG = "AppFirebaseHandler";
    private static BaseContract.LoaderPresenter loaderPresenter;
    private BaseContract.AdderPresenter adderPresenter;
    private BaseContract.UpdaterPresenter updaterPresenter;
    private BaseContract.DeleterPresenter deleterPresenter;
    private static boolean isLoadingData = true;

    private static AppFirebaseHandler init() {
        studentList = new ArrayList<>();
        populateListFromFirebase();
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

    private static void populateListFromFirebase() {
        Log.d(TAG, "method: populateListFromFirebase");
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
                isLoadingData = true;
                Log.d(TAG, "loading students from firebase..");
                final String STUDENTS_CHILD = "students";
                for (DataSnapshot obj : dataSnapshot.child(STUDENTS_CHILD).getChildren()) {
                    Student student = obj.getValue(Student.class);
                    studentList.add(student);
                }
                if (loaderPresenter != null)
                    loaderPresenter.onFinishedLoadingData();
                isLoadingData = false;
                Log.d(TAG, "finished loading students from firebase");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v(TAG, databaseError.getDetails());
            }
        });
    }

    @Override
    public void addStudent(Student student) {
        final String USER_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String STUDENTS_CHILD = "students";
        FirebaseDatabase.getInstance().getReference("users").child(USER_ID)
                .child(STUDENTS_CHILD).child(student.getKey()).setValue(student)
                .addOnCompleteListener(task-> {
                    if (task.isSuccessful()) {
                        adderPresenter.onAddStudentSuccess(student);
                        studentList.add(student);
                    } else {
                        adderPresenter.onAddStudentFailed();
                    }
                });
    }


    @Override
    public boolean isStudentExistsInFirebase(Student student) {
        for (Student curr : studentList) {
            if (student.getName().equals(curr.getName()) &&
                    student.getStudentClass().equals(curr.getStudentClass())) {
                return true;
            }
        }
        return false;
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
                Log.d(TAG, "updating student in local list:" + student);
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
    public boolean isLoadingData() {
        return isLoadingData;
    }

}
