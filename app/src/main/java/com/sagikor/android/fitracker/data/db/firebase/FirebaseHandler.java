package com.sagikor.android.fitracker.data.db.firebase;

import com.sagikor.android.fitracker.data.model.Student;

import com.sagikor.android.fitracker.data.model.UserClass;
import com.sagikor.android.fitracker.ui.addclass.AddClassesActivityContract;
import com.sagikor.android.fitracker.ui.addupdatestudent.AddStudentActivityContract;
import com.sagikor.android.fitracker.ui.home.MainActivityContract;
import com.sagikor.android.fitracker.ui.register.RegisterActivityContract;
import com.sagikor.android.fitracker.ui.settings.SettingsActivityContract;
import com.sagikor.android.fitracker.ui.signin.SignInActivityContract;
import com.sagikor.android.fitracker.ui.addupdatestudent.UpdateStudentActivityContract;
import com.sagikor.android.fitracker.ui.studentslist.ViewStudentsActivityContract;
import com.sagikor.android.fitracker.utils.AppExceptions;

import java.util.List;

public interface FirebaseHandler {

    void getDataFromDatabase(MainActivityContract.Presenter presenter);

    void addStudent(AddStudentActivityContract.Presenter presenter, Student student);

    void updateStudent(UpdateStudentActivityContract.Presenter presenter, Student student);

    void deleteStudent(ViewStudentsActivityContract.Presenter presenter, Student student);

    void addClassUserTeaches(AddClassesActivityContract.Presenter presenter, UserClass classToTeach);

    void deleteClassUserTeaches(AddClassesActivityContract.Presenter presenter, UserClass classToTeach);

    void clearDatabase(SettingsActivityContract.Presenter presenter);

    void checkStudentExistsInFirebase(Student student) throws AppExceptions.StudentExistsAlready;

    void signInWithEmailAndPassword(SignInActivityContract.Presenter presenter,
                                    String email, String password);

    void createUserWithEmailAndPassword(RegisterActivityContract.Presenter presenter,
                                        String email, String password, String name);

    void resetPassword(SignInActivityContract.Presenter presenter, String userEmail);

    String getUserName();

    List<Student> getStudents();

    List<UserClass> getClassesUserTeaches();

    boolean isUserSigned();

    boolean isDataLoaded();

    void deleteAccount(SettingsActivityContract.Presenter presenter);

    void signOut();

}
