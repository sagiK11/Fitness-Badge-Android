package com.sagikor.android.fitracker.utils;

import android.text.format.DateFormat;

import java.util.Date;

public class Utility {
    public static final int PASS_LENGTH = 6;

    private Utility() {
        //NO OBJECTS SHOULD INSTANTIATE FROM THIS CLASS.
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

}
