package com.sagikor.android.fitracker.ui.contracts;

import android.widget.AdapterView;

import com.sagikor.android.fitracker.data.model.Student;

import java.util.List;

public interface ViewStudentsActivityContract {
    interface Presenter {
        void onStudentClick(Object obj);

        List<Student> getStudentsList();

        void deleteStudent(Student student);

        void bind(ViewStudentsActivityContract.View view);

        void unbind();

    }

    interface View {
        void refreshList();

        void navToStudentUpdate();
    }
}
