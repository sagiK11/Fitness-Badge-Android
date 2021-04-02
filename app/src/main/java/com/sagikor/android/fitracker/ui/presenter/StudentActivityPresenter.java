package com.sagikor.android.fitracker.ui.presenter;


import android.util.Log;

import com.sagikor.android.fitracker.ui.contracts.StudentActivityContract;
import com.sagikor.android.fitracker.utils.datastructure.SportResults;

public class StudentActivityPresenter implements StudentActivityContract.Presenter {
    private final SportResults sportResults = new SportResults();
    private final static String TAG = "StudentActivityPre";
    private StudentActivityContract.View view;


    //TODO implement user input tests here
    @Override
    public String calculateGrade(String score, String sportType, String gender) {
        boolean isGirl = gender.equals("Girl") || gender.equals("בת");
        final double grade;
        final double tempScore;
        try {
            tempScore = Double.parseDouble(score);
        }catch (NumberFormatException | NullPointerException e){
            e.printStackTrace();
            Log.d(TAG,"number format exception");
            throw e;
        }
        switch (sportType) {
            case SportResults.AEROBIC:
                if (isGirl)
                    grade = sportResults.getGirlsAerobicResult(tempScore);
                else
                    grade = sportResults.getBoysAerobicResult(tempScore);
                return String.valueOf(grade);
            case SportResults.ABS:
                if (isGirl)
                    grade = sportResults.getGirlsSitUpAbsResult((int) tempScore);
                else
                    grade = sportResults.getBoysSitUpAbsResult((int) tempScore);
                return String.valueOf(grade);
            case SportResults.JUMP:
                if (isGirl)
                    grade = sportResults.getGirlsJumpResult((int) tempScore);
                else
                    grade = sportResults.getBoysJumpResult((int) tempScore);
                return String.valueOf(grade);
            case SportResults.CUBES:
                if (isGirl)
                    grade = sportResults.getGirlsCubesResult(tempScore);
                else
                    grade = sportResults.getBoysCubesResult(tempScore);
                return String.valueOf(grade);
            default:
                if (isGirl)
                    grade = sportResults.getGirlsStaticHandsResult(tempScore);
                else
                    grade = sportResults.getBoysHandsResult(tempScore);
                return String.valueOf(grade);
        }
    }
    protected double parse(String target) {
        return Double.parseDouble(target);
    }

}
