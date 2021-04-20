package com.sagikor.android.fitracker.ui.contracts;

import com.sagikor.android.fitracker.data.model.Student;

import java.util.List;

public interface ViewStudentsActivityContract {
    interface Presenter extends BaseContract.BasePresenter {
        void onStudentClick(String key);

        List<Student> getStudentsList();

        void deleteStudent(Student student);

        List<Student> getFilteredList(String prefix);

        void bind(ViewStudentsActivityContract.View view);

        void unbind();

        void onDeleteStudentSuccess(Student student);

        void onDeleteStudentFailed();
    }

    interface View extends BaseContract.BaseView {
        void navToStudentUpdate();

        void popDeleteStudentSuccess();

        void popDeleteStudentFail();
    }
}
