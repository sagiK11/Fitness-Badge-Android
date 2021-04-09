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
        if (scoreInput == null || scoreInput.length() == 0)
            return false;
        try {
            Double.parseDouble(scoreInput);
        } catch (NumberFormatException e) {
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
        final double tempScore = Double.parseDouble(score);

        if (isFemale) {
            switch (sportType) {
                case SportResults.AEROBIC:
                    return String.valueOf(sportResults.getGirlsAerobicResult(tempScore));
                case SportResults.ABS:
                    return String.valueOf(sportResults.getGirlsSitUpAbsResult((int) tempScore));
                case SportResults.JUMP:
                    return String.valueOf(sportResults.getGirlsJumpResult((int) tempScore));
                case SportResults.CUBES:
                    return String.valueOf(sportResults.getGirlsCubesResult(tempScore));
                default:
                    return String.valueOf(sportResults.getGirlsStaticHandsResult(tempScore));
            }
        } else {
            switch (sportType) {
                case SportResults.AEROBIC:
                    return String.valueOf(sportResults.getBoysAerobicResult(tempScore));
                case SportResults.ABS:
                    return String.valueOf(sportResults.getBoysSitUpAbsResult((int) tempScore));
                case SportResults.JUMP:
                    return String.valueOf(sportResults.getBoysJumpResult((int) tempScore));
                case SportResults.CUBES:
                    return String.valueOf(sportResults.getBoysCubesResult(tempScore));
                default:
                    return String.valueOf(sportResults.getBoysHandsResult(tempScore));
            }
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
