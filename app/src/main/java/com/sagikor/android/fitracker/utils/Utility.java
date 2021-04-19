package com.sagikor.android.fitracker.utils;

import android.text.format.DateFormat;

import java.util.Date;

public class Utility {
    public static final int PASS_LENGTH = 6;
    public static final int MISSING_INPUT = -1;
    public static final String GENERIC_ERROR_MESSAGE = "Ho no! Something went wrong!";

    private Utility() {
        //Not meant to be initiated
    }

    public static String getTodayDate() {
        Date date = new Date();
        StringBuilder res = new StringBuilder();
        String day = (String) android.text.format.DateFormat.format("dd", date);
        String monthNumber = (String) android.text.format.DateFormat.format("MM", date);
        String year = (String) DateFormat.format("yy", date); // 2013
        res.append(day).append("/").append(monthNumber).append("/").append(year);
        return res.toString();
    }

    public static boolean isValidClassName(String classToTech) {
        if (classToTech.length() == 1 && !Character.isAlphabetic(classToTech.charAt(0)))
            return false;
        for (Character curr : classToTech.toCharArray()) {
            if ((!Character.isLetterOrDigit(curr)) && curr != ' ' && curr != '\"') {
                return false;
            }
        }
        return true;
    }

}
