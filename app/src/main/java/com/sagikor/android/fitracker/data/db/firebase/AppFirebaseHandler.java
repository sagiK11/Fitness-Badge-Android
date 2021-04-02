package com.sagikor.android.fitracker.data.db.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sagikor.android.fitracker.data.model.Student;

import java.util.ArrayList;
import java.util.List;

public class AppFirebaseHandler implements FirebaseHandler {
    public static AppFirebaseHandler appFirebaseHandler = init();
    private static List<Student> studentList;
    private final static String TAG = "AppFirebaseHandler";

    private static AppFirebaseHandler init() {
        studentList = new ArrayList<>();
        refreshList();
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

    private static void refreshList() {
        studentList.clear();
        final String USER_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String debug_id = "IPyH6cDYq5TKJ9gpDwD81eHwKG13";//TODO don't forget this
        FirebaseDatabase.getInstance().getReference("users").child(debug_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String STUDENTS_CHILD = "students";
                for (DataSnapshot obj : dataSnapshot.child(STUDENTS_CHILD).getChildren()) {
                    Student student = obj.getValue(Student.class);
                    studentList.add(student);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v(TAG, databaseError.getDetails());
            }
        });
    }

    @Override
    public void addStudent(Student Student) {
        final String USER_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String STUDENTS_CHILD = "students";
        FirebaseDatabase.getInstance().getReference("users").child(USER_ID)
                .child(STUDENTS_CHILD).child(Student.getKey()).setValue(Student);
        refreshList();
    }

    @Override
    public boolean isStudentExistsInFirebase(Student student) {
        //TODO this needs to be tested.
        for (int i = 0; i < studentList.size() ; i++) {
            Student curr = studentList.get(i);
            if(student.getName().equals(curr.getName()) && student.getClass().equals(curr.getClass())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateStudent(Student student) {
        //update in firebase
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String studentsChild = "students";
        FirebaseDatabase.getInstance().getReference("users").
                child(userId).child(studentsChild).child(student.getKey()).
                setValue(student);
        //update in local list
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getKey().equals(student.getKey())) {
                studentList.set(i, student);
                break;
            }
        }
        refreshList();
    }

    @Override
    public void deleteStudent(Student Student) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("users")
                .child(userId).child("students").child(Student.getKey())
                .removeValue();
        refreshList();
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
                Log.d("SettingActivity", "User account deleted.");
            }
        });
    }
}
