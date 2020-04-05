package com.sagiKor1193.android.fitracker;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;


public class AddStudentActivity extends AppCompatActivity {

    SportResultsArrayList sportResultsArrayList = new SportResultsArrayList();
    private final String TAG = "AddStudentActivity";
    private final int INVALID_INPUT = - 2, NO = 0, YES = 1;
    private String sClass;
    EditText sName, sPhone, sAerobicScore, sCubesScore, sHandsScore, sAbsScore, sJumpScore;
    TextView sAerobicScoreText, sCubesScoreText, sHandsScoreText, sAbsScoreText, sJumpScoreText;
    TextView sTotalScoreText;
    Button chooseClassButton, saveStudentButton;
    private double aerobicScore, cubesScore, handsScore, absScore, jumpScore, avg, avgWithOutAerobic;
    private int aerobicGrade, cubesGrade, handsGrade, absGrade, jumpGrade;

    //Alert dialog
    private final String[] answers = { "No", "Yes" };
    private String[] studentsClass = { "ט 1", "ט 2", "ט 3", "ט 4", "ט 5", "ט 6", "ט 7", "ט 8", "ט 9",
            "י 1", "י 2", "י 3", "י-4", "י 5", "י 6", "י 7", "י 8", "י 9",
            "י\"א 1", "י\"א 2", "י\"א 3", "י\"א 4", "י\"א 5", "י\"א 6",
            "י\"א 7", "י\"א 8", "י\"א 9", "י\"ב 1", "י\"ב 2", "י\"ב 3", "י\"ב 4"
            , "י\"ב 5", "י\"ב 6", "י\"ב 7", "י\"ב 8", "י\"ב 9" };

    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_enter_student );
        linkObjects();
    }

    public void addStudentClicked() {
        if ( inputErrorsDetected() )
            return;
        updateFieldsInApp();
        addStudent();
    }

    private boolean inputErrorsDetected() {
        return errorsInStudentClassAndName() && errorsInStudentScores();
    }

    private void addStudent() {
        Student newStudent = createNewStudent();
        askForSendingSMS();
        addStudentToDataBase( newStudent );
    }

    private void addStudentToDataBase( Student newStudent ) {
        if ( ! studentAlreadyExists( newStudent ) ) {
            popToast( "Student with that name and class already entered", Toast.LENGTH_LONG );
        } else {
            popToast( "Student scores entered!", Toast.LENGTH_SHORT );
        }

    }

    private void askForSendingSMS() {
        if ( sPhone.getText().toString().length() > 1 ) {
            final AlertDialog.Builder builder = new AlertDialog.Builder( this );
            builder.setTitle( "לשלוח ציונים לתלמיד?" );
            builder.setItems( answers, ( dialog, which ) -> {
                switch ( which ) {
                    case NO: {
                        return;
                    }
                    case YES: {
                        sendSms();
                        break;
                    }
                }
            } );
            builder.show();
        }
    }

    private boolean studentAlreadyExists( Student newStudent ) {
        return MainActivity.getDbHandler().addStudentData( newStudent );
    }

    private Student createNewStudent() {
        getUserInput();

        Student newStudent = new Student();
        newStudent.setName( sName.getText().toString().trim() );
        newStudent.setStudentClass( sClass );
        newStudent.setPhoneNumber( sPhone.getText().toString() );
        newStudent.setAerobicScore( aerobicScore );
        newStudent.setCubesScore( cubesScore );
        newStudent.setAbsScore( absScore );
        newStudent.setJumpScore( jumpScore );
        newStudent.setHandsScore( handsScore );
        newStudent.setAbsResult( absGrade );
        newStudent.setAerobicResult( aerobicGrade );
        newStudent.setJumpResult( jumpGrade );
        newStudent.setHandsResult( handsGrade );
        newStudent.setCubesResult( cubesGrade );
        newStudent.setTotalScore( avg );
        newStudent.setTotScoreWithoutAerobic( avgWithOutAerobic );
        newStudent.setUpdateDate( getTodayDate() ); // Date
        return newStudent;
    }

    private void getUserInput() {
        aerobicScore = testInput( sAerobicScore, "aerobic score" );
        jumpScore = testInput( sJumpScore, "jump score" );
        absScore = testInput( sAbsScore, "abs score" );
        cubesScore = testInput( sCubesScore, "cubes score" );
        handsScore = testInput( sHandsScore, "hands score" );
    }


    private void updateFieldsInApp() {
        //Updating relevant text with grades.
        sCubesScoreText.setText( calCubesGrade( sCubesScore.getText().toString() ) );
        sAerobicScoreText.setText( calAerobicGrade( sAerobicScore.getText().toString() ) );
        sHandsScoreText.setText( calHandGrade( sHandsScore.getText().toString() ) );
        sJumpScoreText.setText( calJumpGrade( sJumpScore.getText().toString() ) );
        sAbsScoreText.setText( calAbsGrade( sAbsScore.getText().toString() ) );
        calAverages();

        sTotalScoreText.setText( String.valueOf( avg ) );

    }

    private void calAverages() {
        final int CATEGORY_NUM = 5;
        avg = (double) ( aerobicGrade + absGrade + jumpGrade + handsGrade + cubesGrade )
                / CATEGORY_NUM;
        avgWithOutAerobic = (double) ( absGrade + jumpGrade + handsGrade + cubesGrade )
                / ( CATEGORY_NUM - 1 );
    }

    private boolean errorsInStudentClassAndName() {
        if ( sClass == null ) {
            popToast( "Please enter student class.", Toast.LENGTH_LONG );
            return true;
        } else if ( sName.getText().toString().length() == 0 ) {
            popToast( "Please enter student name.", Toast.LENGTH_LONG );
            return true;
        }
        return false;
    }

    private boolean errorsInStudentScores() {
        return aerobicScore == INVALID_INPUT || jumpScore == INVALID_INPUT ||
                absScore == INVALID_INPUT || cubesScore == INVALID_INPUT ||
                handsScore == INVALID_INPUT;
    }

    private void linkObjects() {
        //USER INPUT
        sName = findViewById( R.id.enter_data_v2_student_to_display_id );
        sPhone = findViewById( R.id.phone_number_to_enter_id );
        chooseClassButton = findViewById( R.id.student_class_id );
        saveStudentButton = findViewById( R.id.button_add_student_enter_data );
        sAerobicScore = findViewById( R.id.update_student_aerobic_id );
        sCubesScore = findViewById( R.id.update_student_cubes_id );
        sHandsScore = findViewById( R.id.update_student_hands_id );
        sJumpScore = findViewById( R.id.update_student_jump_id );
        sAbsScore = findViewById( R.id.update_student_abs_id );
        chooseClassButton.setOnClickListener( e -> selectStudentClass() );
        saveStudentButton.setOnClickListener( e -> addStudentClicked() );

        //TEXT TO DISPLAY
        sAerobicScoreText = findViewById( R.id.update_student_aerobic_text );
        sCubesScoreText = findViewById( R.id.student_cubes_text );
        sHandsScoreText = findViewById( R.id.student_hands_text );
        sJumpScoreText = findViewById( R.id.student_jump_text );
        sAbsScoreText = findViewById( R.id.student_abs_text );
        sTotalScoreText = findViewById( R.id.student_total_score );
    }

    private String calCubesGrade( String score ) {
        double tempCubes = 0;

        try{
            tempCubes = Double.parseDouble(score);
        }catch (NumberFormatException e){
            return null;
        }
        cubesGrade = sportResultsArrayList.getCubesResult(tempCubes);
        return String.valueOf( cubesGrade );
    }

    private String calAerobicGrade( String score ) {
        double tempAerobic = 0;

        try{
            tempAerobic = Double.parseDouble(score);
        }catch (NumberFormatException e){
            return null;
        }
        aerobicGrade = sportResultsArrayList.getAerobicResult(tempAerobic) ;
        return String.valueOf( aerobicGrade );
    }

    private String calHandGrade( String score ) {
        double tempHands = 0;

        try{
            tempHands = Double.parseDouble(score);
        }catch (NumberFormatException e){
            return null;
        }
        handsGrade =  sportResultsArrayList.getHandsResult(tempHands);
        return String.valueOf( handsGrade );
    }

    private String calJumpGrade( String score ) {
        int tempJump = 0;
        try{
            tempJump = Integer.parseInt(score);
        }catch (NumberFormatException e){
            return null;
        }
        jumpGrade = sportResultsArrayList.geJumpResult(tempJump);
        return String.valueOf( jumpGrade );
    }

    private String calAbsGrade( String score ) {
        int tempAbs = 0;

        try{
            tempAbs = Integer.parseInt(score);
        }catch (NumberFormatException e){
            return null;
        }
        absGrade =  sportResultsArrayList.getAbsResult(tempAbs);
        return String.valueOf( absGrade );
    }

    private void popToast( String msg, int duration ) {
        Context context = getApplicationContext();
        Toast toast = Toast.makeText( context, msg, duration );
        toast.show();
    }

    private double testInput( EditText text, String place ) {
        try {
            Double.parseDouble( text.getText().toString() );
        } catch ( NumberFormatException e ) {
            if ( text.getText().toString().length() == 0 )
                return - 1;
            else {
                popToast( "Invalid input at " + place, Toast.LENGTH_LONG );
                return INVALID_INPUT;
            }
        }
        return Double.parseDouble( text.getText().toString() );
    }

    //Choosing Student Class.
    public void selectStudentClass() {
        final AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( "בחר כתה" );
        builder.setItems( studentsClass, new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialog, int which ) {

                switch ( which ) {
                    case 0:
                        setClass( "ט 1" );
                        break;
                    case 1:
                        setClass( "ט 2" );
                        break;
                    case 2:
                        setClass( "ט 3" );
                        break;
                    case 3:
                        setClass( "ט 4" );
                        break;
                    case 4:
                        setClass( "ט 5" );
                        break;
                    case 5:
                        setClass( "ט 6" );
                        break;
                    case 6:
                        setClass( "ט 7" );
                        break;
                    case 7:
                        setClass( "ט 8" );
                        break;
                    case 8:
                        setClass( "ט 9" );
                        break;
                    case 9:
                        setClass( "י 1" );
                        break;
                    case 10:
                        setClass( "י 2" );
                        break;
                    case 11:
                        setClass( "י 3" );
                        break;
                    case 12:
                        setClass( "י 4" );
                        break;
                    case 13:
                        setClass( "י 5" );
                        break;
                    case 14:
                        setClass( "י 6" );
                        break;
                    case 15:
                        setClass( "י 7" );
                        break;
                    case 16:
                        setClass( "י 8" );
                        break;
                    case 17:
                        setClass( "י 9" );
                        break;
                    case 18:
                        setClass( "יא 1" );
                        break;
                    case 19:
                        setClass( "יא 2" );
                        break;
                    case 20:
                        setClass( "יא 3" );
                        break;
                    case 21:
                        setClass( "יא 4" );
                        break;
                    case 22:
                        setClass( "יא 5" );
                        break;
                    case 23:
                        setClass( "יא 6" );
                        break;
                    case 24:
                        setClass( "יא 7" );
                        break;
                    case 25:
                        setClass( "יא 8" );
                        break;
                    case 26:
                        setClass( "יא 9" );
                        break;
                    case 27:
                        setClass( "יב 1" );
                        break;
                    case 28:
                        setClass( "יב 2" );
                        break;
                    case 29:
                        setClass( "יב 3" );
                        break;
                    case 30:
                        setClass( "יב 4" );
                        break;
                    case 31:
                        setClass( "יב 5" );
                        break;
                    case 32:
                        setClass( "יב 6" );
                        break;
                    case 33:
                        setClass( "יב 7" );
                        break;
                    case 34:
                        setClass( "יב 8" );
                        break;
                    case 35:
                        setClass( "יב 9" );
                        break;
                }
            }
        } );
        builder.show();
    }

    /*Sets the student class as the user entered and changing the text in the button*/
    private void setClass( String sClass ) {
        this.sClass = sClass;
        chooseClassButton.setText( sClass );
    }

    private void sendSms() {
        String sPhoneNumber = sPhone.getText().toString();
        Uri uri = Uri.parse( "smsto:" + sPhone );
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

    private String getStudentGrades() {
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
