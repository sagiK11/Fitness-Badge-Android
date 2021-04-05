package com.sagikor.android.fitracker.ui.presenter;

import android.util.Log;

import com.sagikor.android.fitracker.data.db.AppDatabaseHandler;
import com.sagikor.android.fitracker.data.db.DatabaseHandler;
import com.sagikor.android.fitracker.ui.contracts.StudentActivityContract;
import com.sagikor.android.fitracker.utils.datastructure.SportResults;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.sagikor.android.fitracker.utils.Utility.MISSING_INPUT;

public class StudentActivityPresenter implements StudentActivityContract.Presenter {
    private final SportResults sportResults = new SportResults();
    private final static String TAG = "StudentActivityPre";
    protected final DatabaseHandler databaseHandler = AppDatabaseHandler.getInstance();
    private StudentActivityContract.View view;

    @Override
    public boolean isValidPhoneNo(String phoneNoInput) {
        //phone number is optional
        if (phoneNoInput.length() == 0)
            return true;
        final int phoneNumberLength = 10;
        if (phoneNoInput.length() != phoneNumberLength)
            return false;
        return phoneNoInput.matches("^[0-9]+$");
    }


    @Override
    public boolean isValidScore(String scoreInput) {
        try {
            Double.parseDouble(scoreInput);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isGenderSelected() {
        return !view.getStudentGender().equals(view.getGenderStringResource());
    }


    @Override
    public String calculateGrade(String score, String sportType, boolean isFemale) {
        //this method is called only after we made user input testing and it passed.
        final double grade;
        final double tempScore = Double.parseDouble(score);

        switch (sportType) {
            case SportResults.AEROBIC:
                if (isFemale)
                    grade = sportResults.getGirlsAerobicResult(tempScore);
                else
                    grade = sportResults.getBoysAerobicResult(tempScore);
                return String.valueOf(grade);
            case SportResults.ABS:
                if (isFemale)
                    grade = sportResults.getGirlsSitUpAbsResult((int) tempScore);
                else
                    grade = sportResults.getBoysSitUpAbsResult((int) tempScore);
                return String.valueOf(grade);
            case SportResults.JUMP:
                if (isFemale)
                    grade = sportResults.getGirlsJumpResult((int) tempScore);
                else
                    grade = sportResults.getBoysJumpResult((int) tempScore);
                return String.valueOf(grade);
            case SportResults.CUBES:
                if (isFemale)
                    grade = sportResults.getGirlsCubesResult(tempScore);
                else
                    grade = sportResults.getBoysCubesResult(tempScore);
                return String.valueOf(grade);
            default:
                if (isFemale)
                    grade = sportResults.getGirlsStaticHandsResult(tempScore);
                else
                    grade = sportResults.getBoysHandsResult(tempScore);
                return String.valueOf(grade);
        }
    }

    @Override
    public void bind(StudentActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void unbind() {
        view = null;
    }

    protected boolean isNonEmptyInput(String nameInput) {
        return nameInput != null && nameInput.length() > 1;
    }

    protected double parse(String target) {
        //teacher doesn't have to enter all the scores.
        if (target.length() == 0)//parsing the user input
            return MISSING_INPUT;
        else if (target.equals(view.getGradeStringResource()))//parsing the grade
            return 0;
        return Double.parseDouble(target);
    }

    protected double getAverage() {
        List<Double> gradesList = new ArrayList<>();

        double average = 0;
        fillGradesList(gradesList, parse(view.getAerobicGrade()));
        fillGradesList(gradesList, parse(view.getAbsGrade()));
        fillGradesList(gradesList, parse(view.getCubesGrade()));
        fillGradesList(gradesList, parse(view.getHandsGrade()));
        fillGradesList(gradesList, parse(view.getJumpGrade()));
        for (Double grade : gradesList) {
            average += grade;
        }
        if (gradesList.size() > 0)
            average /= gradesList.size();
        else
            average = 0;
        return BigDecimal.valueOf(average).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private void fillGradesList(List<Double> gradesList, double grade) {
        if (grade != 0)
            gradesList.add(grade);
    }

}
