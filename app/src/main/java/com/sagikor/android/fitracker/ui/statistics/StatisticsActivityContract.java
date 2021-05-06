package com.sagikor.android.fitracker.ui.statistics;

import com.sagikor.android.fitracker.data.model.Student;
import com.sagikor.android.fitracker.ui.contracts.BaseContract;

import java.util.List;

public interface StatisticsActivityContract {

    interface Presenter extends BaseContract.BasePresenter {
        void bind(StatisticsActivityContract.View view);

        void unbind();

        List<Student> getStudentsList();

        void calculateFinishedNoOfStudents();

        void calculateGradesAverage();

        float getAerobicGradesAverage();

        float getAbsGradesAverage();

        float getHandsGradeAverage();

        float getCubesGradeAverage();

        float getJumpGradesAverage();

        float getFinishedNo();

        float getUnFinishedNo();

    }

    interface View extends BaseContract.BaseView {

    }
}
