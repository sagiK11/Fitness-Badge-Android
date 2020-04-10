package com.sagiKor1193.android.fitracker;

import android.text.format.DateFormat;

import java.util.Date;

public class Utility {

    public static String getTodayDate() {
        Date date = new Date();
        StringBuilder res = new StringBuilder();
        String day = (String) android.text.format.DateFormat.format( "dd", date );
        String monthNumber = (String) android.text.format.DateFormat.format( "MM", date );
        String year = (String) DateFormat.format( "yy", date ); // 2013
        res.append( day ).append( "/" ).append( monthNumber ).append( "/" ).append( year );
        return res.toString();
    }

}
