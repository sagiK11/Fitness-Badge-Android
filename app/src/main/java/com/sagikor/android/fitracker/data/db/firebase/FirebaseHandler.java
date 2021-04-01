package com.sagikor.android.fitracker.data.db.firebase;

public interface FirebaseHandler {
    void initFirebase();
    void getStudents();
    void addStudent();
    void updateStudent();
    void deleteStudent();
    void clearDatabase();
}
