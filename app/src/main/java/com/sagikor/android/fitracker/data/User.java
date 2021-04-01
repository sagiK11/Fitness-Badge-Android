package com.sagikor.android.fitracker.data;




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

    public List<Student> getStudentList() {
        return studentList;
    }

    public void addStudentToList(Student student) {
        studentList.add(student);
    }

    public void clearStudentsList() {
        studentList.clear();
    }


}
