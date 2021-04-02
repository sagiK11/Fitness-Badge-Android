package com.sagikor.android.fitracker.data.db.firebase;

import com.sagikor.android.fitracker.data.model.Student;

import java.util.List;

public interface FirebaseHandler {

    List<Student> getStudents();

    void addStudent(Student student);

    void updateStudent(Student student);

    void deleteStudent(Student student);

    boolean isStudentExistsInFirebase(Student student);

    void clearDatabase();

    void deleteAccount();
}
