package com.sagikor.android.fitracker.ui.addupdatestudent;


import android.util.Log;

import com.sagikor.android.fitracker.data.db.AppDatabaseHandler;
import com.sagikor.android.fitracker.data.db.DatabaseHandler;
import com.sagikor.android.fitracker.ui.addupdatestudent.StudentActivityContract;
import com.sagikor.android.fitracker.utils.datastructure.SportResults;
import com.sagikor.android.fitracker.utils.datastructure.SportResultsHandler;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.sagikor.android.fitracker.utils.Utility.MISSING_INPUT;

public class StudentActivityPresenter implements StudentActivityContract.Presenter {

    private static final String TAG = "StudentActivityPre";
    protected final DatabaseHandler databaseHandler = AppDatabaseHandler.getInstance();
    private SportResultsHandler sportResults;
    private StudentActivityContract.View view;


    @Override
    public void onScoreTextChanged(String score, String sportType, Map<String, Boolean> map) {
        String emptyGrade = view.getGradeStringResource();
        if (isGenderSelected()) {
            switch (sportType) {
                case SportResults.AEROBIC:
                    view.setAerobicGrade(getGrade(score, sportType, map));
                    break;
                case SportResults.ABS:
                    view.setAbsGrade(getGrade(score, sportType, map));
                    break;
                case SportResults.CUBES:
                    view.setCubesGrade(getGrade(score, sportType, map));
                    break;
                case SportResults.HANDS:
                    view.setHandsGrade(getGrade(score, sportType, map));
                    break;
                default:
                    view.setJumpGrade(getGrade(score, sportType, map));
            }
        } else {
            view.popGenderError();
        }
        double average = getAverage();
        view.setTotalGrade(average != 0 ? average + "" : emptyGrade);
    }

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
        Log.d(TAG, "isGenderSelected: " + view.getStudentGender() + " vs " + view.getGenderStringResource());
        return !view.getStudentGender().equals(view.getGenderStringResource());
    }


    public String calculateGrade(String score, String sportType, Map<String, Boolean> map) {
        //this method is called only after we made user input testing and it passed.
        final double tempScore = Double.parseDouble(score);
        final boolean isFemale = map.get(SportResults.IS_FEMALE);
        final boolean isWalking = map.get(SportResults.IS_WALKING);
        final boolean isPushUpHalf = map.get(SportResults.IS_PUSH_UP_HALF);

        if (isFemale) {
            switch (sportType) {
                case SportResults.AEROBIC:
                    return String.valueOf(sportResults.getFemalesAerobicResult(tempScore, isWalking));
                case SportResults.ABS:
                    return String.valueOf(sportResults.getFemalesAbsResult(tempScore));
                case SportResults.JUMP:
                    return String.valueOf(sportResults.getFemalesJumpResult((int) tempScore));
                case SportResults.CUBES:
                    return String.valueOf(sportResults.getFemalesCubesResult(tempScore));
                default:
                    return String.valueOf(sportResults.getFemalesHandsResult(tempScore, isPushUpHalf));
            }
        } else {
            switch (sportType) {
                case SportResults.AEROBIC:
                    return String.valueOf(sportResults.getMalesAerobicResult(tempScore));
                case SportResults.ABS:
                    return String.valueOf(sportResults.getMalesSitUpAbsResult((int) tempScore));
                case SportResults.JUMP:
                    return String.valueOf(sportResults.getMalesJumpResult((int) tempScore));
                case SportResults.CUBES:
                    return String.valueOf(sportResults.getMalesCubesResult(tempScore));
                default:
                    return String.valueOf(sportResults.getMalesHandsResult(tempScore));
            }
        }


    }

    @Override
    public void bind(StudentActivityContract.View view) {
        this.view = view;
        final InputStream femaleGrades = view.getFemaleGradesFile();
        final InputStream maleGrades = view.getMaleGradesFile();
        sportResults = new SportResults(femaleGrades, maleGrades);
    }

    @Override
    public void unbind() {
        view = null;
    }

    private String getGrade(String score, String sportType, Map<String, Boolean> map) {
        String emptyGrade = view.getGradeStringResource();
        return isValidScore(score) ? calculateGrade(score, sportType, map) : emptyGrade;
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
        if (!gradesList.isEmpty())
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
