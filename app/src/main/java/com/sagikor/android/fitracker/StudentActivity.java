package com.sagikor.android.fitracker;

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
    String sClassString;
    String sGenderString;
    static final String TAG = "StudentActivity";
    static final int INVALID_INPUT = - 2;
    static final int MISSING_INPUT = - 1;
    Student currentStudent;
    SportResultsArrayList sportResultsArrayList = new SportResultsArrayList();
    EditText sName;
    EditText sPhoneNumber;
    EditText sAerobicScore;
    EditText sCubesScore;
    EditText sHandsScore;
    EditText sAbsScore;
    EditText sJumpScore;
    TextView sAerobicScoreText;
    TextView sCubesScoreText;
    TextView sHandsScoreText;
    TextView sAbsScoreText;
    TextView sJumpScoreText;
    TextView sTotalScoreText;
    Button chooseClassButton;
    Button genderButton;
    Button saveStudentButton;
    Button sClassButton;
    double aerobicScore;
    double cubesScore;
    double handsScore;
    double absScore;
    double jumpScore;
    double avg;
    double avgWithOutAerobic;
    int aerobicGrade;
    int cubesGrade;
    int handsGrade;
    int absGrade;
    int jumpGrade;
    boolean isAbsSitUp;
    boolean isStaticHands;

    private void popSuccessWindow() {
        final String STUDENT_SAVED = getResources().getString( R.string.student_saved );
        new SweetAlertDialog( this, SweetAlertDialog.SUCCESS_TYPE )
                .setTitleText( STUDENT_SAVED )
                .show();
    }

    void popFailWindow() {
        final String STUDENT_EXISTS = getResources().getString( R.string.student_exists );
        new SweetAlertDialog( this, SweetAlertDialog.ERROR_TYPE )
                .setTitleText( STUDENT_EXISTS )
                .show();
    }

    void askForSendingSMS() {
        final String SEND_QUESTION = getResources().getString( R.string.send_to_student_question );
        final String YES = getResources().getString( R.string.yes );
        final String NO = getResources().getString( R.string.no );

        if ( studentHasPhoneNumber() ) {
            new SweetAlertDialog( this, SweetAlertDialog.SUCCESS_TYPE )
                    .setTitleText( SEND_QUESTION )
                    .setConfirmText( YES )
                    .setConfirmClickListener( sDialog -> {
                        sDialog.dismissWithAnimation();
                        sendSms();
                    } )
                    .setCancelButton( NO, SweetAlertDialog::dismissWithAnimation )
                    .show();
        } else {
            popSuccessWindow();
        }
    }

    void chooseAbsTestOptionPopup() {
        final String CHOOSE_EXERCISE = getResources().getString( R.string.choose_exercise );
        final String SIT_UP = getResources().getString( R.string.situp );
        final String STATIC = getResources().getString( R.string.static_type );
        new SweetAlertDialog( this, SweetAlertDialog.WARNING_TYPE )
                .setTitleText( CHOOSE_EXERCISE )
                .setConfirmText( SIT_UP )
                .setConfirmClickListener( sDialog -> {
                    sDialog.dismissWithAnimation();
                    isAbsSitUp = true;
                } )
                .setCancelButton( STATIC, SweetAlertDialog::dismissWithAnimation )
                .show();

    }

    void chooseHandsTestOptionPopup() {
        final String CHOOSE_EXERCISE = getResources().getString( R.string.choose_exercise );
        final String SIT_UP = getResources().getString( R.string.situp );
        final String STATIC = getResources().getString( R.string.static_type );

        new SweetAlertDialog( this, SweetAlertDialog.WARNING_TYPE )
                .setTitleText( CHOOSE_EXERCISE )
                .setConfirmText( STATIC )
                .setConfirmClickListener( sDialog -> {
                    sDialog.dismissWithAnimation();
                    isStaticHands = true;
                } )
                .setCancelButton( SIT_UP, SweetAlertDialog::dismissWithAnimation )
                .show();
    }


    boolean studentHasPhoneNumber() {
        return getStudentPhoneNumber().length() > 1;
    }

    String getStudentPhoneNumber() {
        return this.sPhoneNumber.getText().toString().trim();
    }

    void sendSms() {
        String studentPhoneNumber = getStudentPhoneNumber();
        Uri uri = Uri.parse( "smsto:" + studentPhoneNumber );
        Intent smsIntent = new Intent( Intent.ACTION_SEND, uri );
        smsIntent.setType( "text/plain" );
        smsIntent.putExtra( "address", studentPhoneNumber );

        String txt = getStudentGrades();
        smsIntent.putExtra( Intent.EXTRA_TEXT, txt );

        try {
            startActivity( smsIntent );
        } catch ( ActivityNotFoundException e ) {
            Log.d( TAG, e.getMessage() );
        }
    }

    String getStudentGrades() {
        final int MISSING = - 1;
        final int DONE = 5;
        int cnt = 0;
        StringBuilder res = new StringBuilder();
        final String HELLO = getResources().getString( R.string.hello );
        final String CURRENT_GRADES = getResources().getString( R.string.current_grades );
        final String AEROBIC = getResources().getString( R.string.aero_c );
        final String HANDS = getResources().getString( R.string.hands_c );
        final String JUMP = getResources().getString( R.string.jump_c );
        final String CUBES = getResources().getString( R.string.cubes_c );
        final String ABS = getResources().getString( R.string.abs_c );
        final String GRADE = getResources().getString( R.string.grade_c );
        final String TOTAL_SCORE = getResources().getString( R.string.total_grade_c );
        final String MINUTES = getResources().getString( R.string.minutes );
        final String SECONDS = getResources().getString( R.string.seconds );
        final String CM = getResources().getString( R.string.cm );
        final String SITUP = getResources().getString( R.string.situp );

        res.append( HELLO + " " + sName.getText() + " " + CURRENT_GRADES + "\n" );
        if ( aerobicScore != MISSING ) {
            res.append( AEROBIC ).append( " " + aerobicScore + " " ).append( MINUTES ).
                    append( " " + GRADE + " " ).append( aerobicGrade + "\n" );
            cnt++;
        }
        if ( jumpScore != MISSING ) {
            res.append( JUMP ).append( " " + jumpScore + " " ).append( CM ).
                    append( " " + GRADE ).append( jumpGrade + "\n" );
            cnt++;
        }
        if ( absScore != MISSING ) {
            res.append( ABS ).append( " " + absScore + " " ).
                    append( GRADE ).append( " " + absGrade + "\n" );
            cnt++;
        }
        if ( cubesScore != MISSING ) {
            res.append( CUBES ).append( " " + cubesScore + " " ).append( SECONDS ).
                    append( " " + GRADE ).append( " " + cubesGrade + "\n" );
            cnt++;
        }
        if ( handsScore != MISSING ) {
            res.append( HANDS ).append( " " + handsScore + " " ).append( MINUTES ).
                    append( " " + GRADE ).append( " " + handsGrade );
            cnt++;
        }
        return cnt == DONE ? res.append( "\n" + TOTAL_SCORE ).append( " " + avg + " " ).toString() : res.toString();
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
        //TODO ISSUE 1183 - WAITING FOR CLIENT SCORES TABLE.
//        if ( isStaticHands )
//            handsGrade = sportResultsArrayList.getStaticHandsResult( tempHands );
//        else {
//            handsGrade = sportResultsArrayList.getPushUpHandsResult( tempHands );
//        }
        handsGrade = sportResultsArrayList.getStaticHandsResult( tempHands );
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
        //TODO ISSUE 1183 - WAITING FOR CLIENT SCORES TABLE.
//        if ( isAbsSitUp ) {
//            absGrade = sportResultsArrayList.getSitUpAbsResult( tempAbs );
//        } else {
//            absGrade = sportResultsArrayList.getPlankAbsResult( tempAbs );
//        }
        absGrade = sportResultsArrayList.getSitUpAbsResult( tempAbs );
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
