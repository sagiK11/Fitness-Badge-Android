package com.sagikor.android.fitracker.ui.presenter;

import com.sagikor.android.fitracker.data.model.Student;
import com.sagikor.android.fitracker.data.db.AppDatabaseHandler;
import com.sagikor.android.fitracker.data.db.DatabaseHandler;
import com.sagikor.android.fitracker.ui.contracts.ViewStudentsActivityContract;

import java.util.List;

public class ViewStudentsActivityPresenter implements ViewStudentsActivityContract.Presenter {
    private final DatabaseHandler databaseHandler = AppDatabaseHandler.getInstance();
    private ViewStudentsActivityContract.View view;

    @Override
    public void onStudentClick(Object obj) {
        databaseHandler.cacheObject(obj);
        view.navToStudentUpdate();
    }

    @Override
    public List<Student> getStudentsList() {
        return databaseHandler.getStudents();
    }

    @Override
    public void deleteStudent(Student student) {
        databaseHandler.deleteStudent(student);
    }

    @Override
    public void bind(ViewStudentsActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void unbind() {
        this.view = null;
    }
}
