package com.sagikor.android.fitracker;


import java.util.ArrayList;
import java.util.List;

public class User {
    String fullName;
    String email;
    String userID;
    private List<Student> studentList = new ArrayList();

    public User() {
    }

    public User(String fullName, String email, String userId) {
        this.fullName = fullName;
        this.email = email;
        this.userID = userId;
    }

    List<Student> getStudentList() {
        return studentList;
    }

    void addStudentToList(Student student) {
        studentList.add(student);
    }

    void clearStudentsList() {
        studentList.clear();
    }


}
