package com.sagikor.android.fitracker.ui.statistics;


import com.sagikor.android.fitracker.data.db.AppDatabaseHandler;
import com.sagikor.android.fitracker.data.db.DatabaseHandler;
import com.sagikor.android.fitracker.data.model.Student;
import com.sagikor.android.fitracker.ui.statistics.StatisticsActivityContract;

import static com.sagikor.android.fitracker.utils.Utility.MISSING_INPUT;

import java.util.List;

public class StatisticsActivityPresenter implements StatisticsActivityContract.Presenter {
    private static final String TAG = "StatisticsActivityPres";
    private final DatabaseHandler databaseHandler = AppDatabaseHandler.getInstance();
    private StatisticsActivityContract.View view;
    private float aerobicGradesAverage;
    private float absGradesAverage;
    private float handsGradeAverage;
    private float cubesGradeAverage;
    private float jumpGradesAverage;
    private static final int FINISHED_INDEX = 0;
    private static final int UNFINISHED_INDEX = 1;
    private final float[] finishedArray = new float[2];

    @Override
    public void bind(StatisticsActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void unbind() {
        this.view = null;
    }

    @Override
    public List<Student> getStudentsList() {
        return databaseHandler.getStudents();
    }

    @Override
    public void calculateFinishedNoOfStudents() {
        float finishedStudentsCounter = 0;
        float unFinishedStudentsCounter = 0;

        for (Student student : getStudentsList()) {
            if (student.isFinished()) {
                finishedStudentsCounter++;
            } else {
                unFinishedStudentsCounter++;
            }
        }
        finishedArray[FINISHED_INDEX] = finishedStudentsCounter;
        finishedArray[UNFINISHED_INDEX] = unFinishedStudentsCounter;
    }

    @Override
    public float getAerobicGradesAverage() {
        return aerobicGradesAverage;
    }

    @Override
    public float getAbsGradesAverage() {
        return absGradesAverage;
    }

    @Override
    public float getHandsGradeAverage() {
        return handsGradeAverage;
    }

    @Override
    public float getCubesGradeAverage() {
        return cubesGradeAverage;
    }

    @Override
    public float getJumpGradesAverage() {
        return jumpGradesAverage;
    }

    @Override
    public float getFinishedNo() {
        return finishedArray[FINISHED_INDEX];
    }

    @Override
    public float getUnFinishedNo() {
        return finishedArray[UNFINISHED_INDEX];
    }

    public void calculateGradesAverage() {
        calculateAerobicAverage();
        calculateAbsAverage();
        calculateCubesAverage();
        calculateHandsAverage();
        calculateJumpAverage();
    }

    private void calculateAerobicAverage() {
        int aerobicGradesCounter = 0;
        float sumAerobic = 0;
        for (Student student : getStudentsList()) {
            if (student.getAerobicResult() != MISSING_INPUT) {
                aerobicGradesCounter++;
                sumAerobic += student.getAerobicResult();
            }
        }
        aerobicGradesAverage = aerobicGradesCounter != 0 ? sumAerobic / aerobicGradesCounter : 0;
    }

    private void calculateHandsAverage() {
        int handsGradesCounter = 0;
        float sumHands = 0;
        for (Student student : getStudentsList()) {
            if (student.getHandsResult() != MISSING_INPUT) {
                handsGradesCounter++;
                sumHands += student.getHandsResult();
            }
        }
        handsGradeAverage = handsGradesCounter != 0 ? sumHands / handsGradesCounter : 0;
    }

    private void calculateCubesAverage() {
        int cubesGradesCounter = 0;
        float sumCubes = 0;
        for (Student student : getStudentsList()) {
            if (student.getCubesResult() != MISSING_INPUT) {
                cubesGradesCounter++;
                sumCubes += student.getCubesResult();
            }
        }
        cubesGradeAverage = cubesGradesCounter != 0 ? sumCubes / cubesGradesCounter : 0;
    }

    private void calculateJumpAverage() {
        int jumpGradesCounter = 0;
        float sumJump = 0;
        for (Student student : getStudentsList()) {
            if (student.getJumpResult() != MISSING_INPUT) {
                jumpGradesCounter++;
                sumJump += student.getJumpResult();
            }
        }
        jumpGradesAverage = jumpGradesCounter != 0 ? sumJump / jumpGradesCounter : 0;
    }

    private void calculateAbsAverage() {
        int absGradesCounter = 0;
        float sumAbs = 0;
        for (Student student : getStudentsList()) {
            if (student.getAbsResult() != MISSING_INPUT) {
                absGradesCounter++;
                sumAbs += student.getAbsResult();
            }
        }
        absGradesAverage = absGradesCounter != 0 ? sumAbs / absGradesCounter : 0;
    }
}
