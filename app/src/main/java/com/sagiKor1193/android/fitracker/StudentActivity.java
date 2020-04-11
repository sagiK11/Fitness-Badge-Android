package com.sagiKor1193.android.fitracker;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class StudentActivity extends AppCompatActivity {
    String sClassString, sGenderString, TAG = "StudentActivity";
    final int INVALID_INPUT = - 2, MISSING_INPUT = - 1;
    Student currentStudent;
    SportResultsArrayList sportResultsArrayList = new SportResultsArrayList();
    EditText sName, sPhoneNumber, sAerobicScore, sCubesScore, sHandsScore, sAbsScore, sJumpScore;
    TextView sAerobicScoreText, sCubesScoreText, sHandsScoreText, sAbsScoreText, sJumpScoreText;
    TextView sTotalScoreText;
    Button chooseClassButton, genderButton, saveStudentButton, sClassButton;
    double aerobicScore, cubesScore, handsScore, absScore, jumpScore, avg, avgWithOutAerobic;
    int aerobicGrade, cubesGrade, handsGrade, absGrade, jumpGrade;

    private void popSuccessWindow() {
        new SweetAlertDialog( this, SweetAlertDialog.SUCCESS_TYPE )
                .setTitleText( "התלמיד נשמר!" )
                .show();
    }

    void popFailWindow() {
        new SweetAlertDialog( this, SweetAlertDialog.ERROR_TYPE )
                .setTitleText( "התלמיד כבר קיים במערכת" )
                .show();
    }

    void askForSendingSMS() {
        if ( studentHasPhoneNumber() ) {
            new SweetAlertDialog( this, SweetAlertDialog.SUCCESS_TYPE )
                    .setTitleText( "לשלוח ציונים לתלמיד?" )
                    .setConfirmText( "כן" )
                    .setConfirmClickListener( sDialog -> {
                        sDialog.dismissWithAnimation();
                        sendSms();
                    } )
                    .setCancelButton( "לא", SweetAlertDialog::dismissWithAnimation )
                    .show();
        } else {
            popSuccessWindow();
        }
    }

    boolean studentHasPhoneNumber() {
        return getStudentPhoneNumber().length() > 1;
    }

    String getStudentPhoneNumber() {
        return this.sPhoneNumber.getText().toString().trim();
    }

    void sendSms() {
        String sPhoneNumber = getStudentPhoneNumber();
        Uri uri = Uri.parse( "smsto:" + sPhoneNumber );
        Intent smsIntent = new Intent( Intent.ACTION_SEND, uri );
        smsIntent.setType( "text/plain" );
        smsIntent.putExtra( "address", sPhoneNumber );

        String txt = getStudentGrades();
        smsIntent.putExtra( Intent.EXTRA_TEXT, txt );

        try {
            startActivity( smsIntent );
        } catch ( ActivityNotFoundException e ) {
            Log.d( TAG, e.getMessage() );
        }
    }

    String getStudentGrades() {
        final int MISSING = - 1, DONE = 5;
        int cnt = 0;
        StringBuilder res = new StringBuilder();
        res.append( "\nשלום " + sName.getText() + ", אלו הם הציונים העדכניים שלך\n" );
        if ( aerobicScore != MISSING ) {
            res.append( "אירובי: " ).append( aerobicScore ).append( " דקות " ).
                    append( " ציון: " ).append( aerobicGrade );
            cnt++;
        }
        if ( jumpScore != MISSING ) {
            res.append( "\n" + " קפיצה: " ).append( jumpScore ).append( " ס\"מ " ).
                    append( " ציון: " ).append( jumpGrade );
            cnt++;
        }
        if ( absScore != MISSING ) {
            res.append( "\n" + " בטן: " ).append( absScore ).append( " כפיפות " ).
                    append( " ציון: " ).append( absGrade );
            cnt++;
        }
        if ( cubesScore != MISSING ) {
            res.append( "\n" + " קוביות: " ).append( cubesScore ).append( " שניות " ).
                    append( " ציון: " ).append( cubesGrade );
            cnt++;
        }
        if ( handsScore != MISSING ) {
            res.append( "\n" + "ידיים: " ).append( handsScore ).append( " דקות " ).
                    append( " ציון: " ).append( handsGrade );
            cnt++;
        }
        return cnt == DONE ? res.append( "\n" + "ציון כולל: " ).append( avg ).toString() : res.toString();
    }

    String calCubesGrade( String score ) {
        double tempCubes = 0;

        try {
            tempCubes = Double.parseDouble( score );
        } catch ( NumberFormatException e ) {
            Log.d( TAG, e.getMessage() );
        }
        cubesGrade = sportResultsArrayList.getCubesResult( tempCubes );
        return String.valueOf( cubesGrade );
    }

    String calAerobicGrade( String score ) {
        double tempAerobic = 0;

        try {
            tempAerobic = Double.parseDouble( score );
        } catch ( NumberFormatException e ) {
            Log.d( TAG, e.getMessage() );
        }
        aerobicGrade = sportResultsArrayList.getAerobicResult( tempAerobic );
        return String.valueOf( aerobicGrade );
    }

    String calHandGrade( String score ) {
        double tempHands = 0;

        try {
            tempHands = Double.parseDouble( score );
        } catch ( NumberFormatException e ) {
            Log.d( TAG, e.getMessage() );
        }
        handsGrade = sportResultsArrayList.getHandsResult( tempHands );
        return String.valueOf( handsGrade );
    }

    String calJumpGrade( String score ) {
        int tempJump = 0;
        try {
            tempJump = Integer.parseInt( score );
        } catch ( NumberFormatException e ) {
            Log.d( TAG, e.getMessage() );
        }
        jumpGrade = sportResultsArrayList.geJumpResult( tempJump );
        return String.valueOf( jumpGrade );
    }

    String calAbsGrade( String score ) {
        int tempAbs = 0;

        try {
            tempAbs = Integer.parseInt( score );
        } catch ( NumberFormatException e ) {
            Log.d( TAG, e.getMessage() );
        }
        absGrade = sportResultsArrayList.getAbsResult( tempAbs );
        return String.valueOf( absGrade );
    }

    void calAverages() {
        final int CATEGORY_NUM = 5;
        avg = (double) ( aerobicGrade + absGrade + jumpGrade + handsGrade + cubesGrade )
                / CATEGORY_NUM;
        avgWithOutAerobic = (double) ( absGrade + jumpGrade + handsGrade + cubesGrade )
                / ( CATEGORY_NUM - 1 );
    }


    double testInput( EditText text, String place, Context context ) {
        try {
            Double.parseDouble( text.getText().toString() );
        } catch ( NumberFormatException e ) {
            if ( text.getText().toString().length() == 0 )
                return MISSING_INPUT;
            else {
                popToast( "Invalid input at " + place, Toast.LENGTH_LONG, context );
                return INVALID_INPUT;
            }
        }
        return Double.parseDouble( text.getText().toString() );
    }

    void popToast( String msg, int duration, Context context ) {
        Toast toast = Toast.makeText( context, msg, duration );
        toast.show();
    }

}
