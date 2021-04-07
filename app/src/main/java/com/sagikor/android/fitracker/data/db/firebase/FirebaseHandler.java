 package com.sagikor.android.fitracker.data.db.firebase;

import com.sagikor.android.fitracker.data.model.Student;
import com.sagikor.android.fitracker.ui.contracts.BaseContract;
import com.sagikor.android.fitracker.ui.contracts.ViewStudentsActivityContract;
import com.sagikor.android.fitracker.ui.view.ViewStudentsActivity;

import java.util.List;

public interface FirebaseHandler {

    void getDataFromDatabase();

    List<Student> getStudents();

    void addStudent(Student student);

    void updateStudent(Student student);

    void deleteStudent(Student student);

    boolean isStudentExistsInFirebase(Student student);

    void clearDatabase();

    void deleteAccount();

    void setLoaderPresenter(BaseContract.LoaderPresenter presenter);

    void setAdderPresenter(BaseContract.AdderPresenter presenter);

    void setUpdaterPresenter(BaseContract.UpdaterPresenter presenter);

    void setDeleterPresenter(BaseContract.DeleterPresenter presenter);

    void setSignInPresenter(BaseContract.SignInPresenter presenter);

    void setRegisterPresenter(BaseContract.RegisterPresenter presenter);

    void signInWithEmailAndPassword(String email,String password);

    void createUserWithEmailAndPassword(String email,String password,String name);

    void resetPassword(String userEmail);

    boolean isUserSigned();

    boolean isDataLoaded();

}
