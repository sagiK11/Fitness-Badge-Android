package com.sagikor.android.fitracker.ui.presenter;

import com.sagikor.android.fitracker.data.model.Student;
import com.sagikor.android.fitracker.data.db.AppDatabaseHandler;
import com.sagikor.android.fitracker.data.db.DatabaseHandler;
import com.sagikor.android.fitracker.ui.contracts.BaseContract;
import com.sagikor.android.fitracker.ui.contracts.ViewStudentsActivityContract;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentsActivityPresenter implements
        ViewStudentsActivityContract.Presenter,
        BaseContract.LoaderPresenter,
        BaseContract.DeleterPresenter {

    private final DatabaseHandler databaseHandler = AppDatabaseHandler.getInstance();
    private ViewStudentsActivityContract.View view;

    @Override
    public void onStudentClick(int position) {
        databaseHandler.cacheObject(position);
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
    public List<Student> getFilteredList(String prefix) {
        List<Student> filteredList = new ArrayList<>();

        for (Student student : getStudentsList()) {
            if (student.getName().startsWith(prefix))
                filteredList.add(student);
        }
        return filteredList;
    }

    @Override
    public void bind(ViewStudentsActivityContract.View view) {
        this.view = view;
        databaseHandler.setLoaderPresenter(this);
        databaseHandler.setDeleterPresenter(this);
    }

    @Override
    public void unbind() {
        this.view = null;
        databaseHandler.setLoaderPresenter(null);
        databaseHandler.setDeleterPresenter(null);
    }

    @Override
    public void onFinishedLoadingData() {
        view.notifyAdapterDataChanged();
        view.hideProgressBar();
    }

    @Override
    public void onLoadingData() {
        view.showProgressBar();
    }

    @Override
    public void onDeleteStudentSuccess(Student student) {
        //TODO this can be left empty for the moment
    }

    @Override
    public void onDeleteStudentFailed() {
        //TODO this can be left empty for the moment
    }
}
