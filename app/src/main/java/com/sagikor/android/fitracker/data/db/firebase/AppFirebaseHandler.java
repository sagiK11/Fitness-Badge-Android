package com.sagikor.android.fitracker.data.db.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sagikor.android.fitracker.data.Student;

public class AppFirebaseHandler implements FirebaseHandler {
    @Override
    public void initFirebase() {

    }

    @Override
    public void getStudents() {

        final String USER_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("users").child(USER_ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String STUDENTS_CHILD = "students";
                for (DataSnapshot obj : dataSnapshot.child(STUDENTS_CHILD).getChildren()) {
                    Student student = obj.getValue(Student.class);
                    //currentUser.addStudentToList(student);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("MainActivity", databaseError.getDetails());
            }
        });

    }

    @Override
    public void addStudent() {

    }

    @Override
    public void updateStudent() {

    }

    @Override
    public void deleteStudent() {

    }

    @Override
    public void clearDatabase() {

    }
}
