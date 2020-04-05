package com.sagiKor1193.android.fitracker;


import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class UpdateStudentActivity extends AppCompatActivity {

    private final String TAG = "AddStudentFromResults";
    private final String ZERO = "0";
    SportResultsArrayList sportResultsArrayList = new SportResultsArrayList();
    private final int INVALID_INPUT = - 2, MISSING = - 1;
    Student currentStudent;
    TextView sName, sClass, sAerobicScoreText, sCubesScoreText, sHandsScoreText, sAbsScoreText;
    TextView sJumpScoreText, sTotalScoreText;
    EditText sAerobicScore, sCubesScore, sHandsScore, sAbsScore, sJumpScore, sPhoneNumber;
    Button saveButton;
    //Scores
    private double aerobicScore, cubesScore, handsScore, absScore, jumpScore;
    //Grades
    private int aerobicGrade, cubesGrade, handsGrade, absGrade, jumpGrade;
    private double avg, avgWithOutAerobic;
    //Alert dialog
    private final String[] answers = { "No", "Yes" };
    private final int NO = 0, YES = 1;

    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_update_student );

        linkObjects();
        getCurrentStudent();
        setFieldsInApp();
    }

    private void getCurrentStudent() {
        currentStudent = getIntent().getParcelableExtra( "student" );
    }

    private void setFieldsInApp() {
        updateStudentDetailsTextFields();
        updateScoreTextFields();
        updateGradeTextFields();
    }

    private void updateStudentDetailsTextFields() {
        sName.setText( currentStudent.getName() );
        sClass.setText( currentStudent.getStudentClass() );
    }

    private void updateScoreTextFields() {
        if ( currentStudent.getAerobicScore() != MISSING )
            sAerobicScore.setText( String.valueOf( currentStudent.getAerobicScore() ) );

        if ( currentStudent.getCubesScore() != MISSING )
            sCubesScore.setText( String.valueOf( currentStudent.getCubesScore() ) );

        if ( currentStudent.getHandsScore() != MISSING )
            sHandsScore.setText( String.valueOf( currentStudent.getHandsScore() ) );

        if ( currentStudent.getAbsScore() != MISSING )
            sAbsScore.setText( String.valueOf( currentStudent.getAbsScore() ) );

        if ( currentStudent.getJumpScore() != MISSING )
            sJumpScore.setText( String.valueOf( currentStudent.getJumpScore() ) );

        if ( currentStudent.getPhoneNumber() != null )
            sPhoneNumber.setText( currentStudent.getPhoneNumber() );
    }

    private void linkObjects() {
        sName = findViewById( R.id.enter_data_v2_student_to_display_id );
        sClass = findViewById( R.id.enter_data_v2_student_class_to_display_id );
        sPhoneNumber = findViewById( R.id.phone_number_to_enter_id );

        sAerobicScore = findViewById( R.id.update_student_aerobic_id );
        sCubesScore = findViewById( R.id.update_student_cubes_id );
        sHandsScore = findViewById( R.id.update_student_hands_id );
        sJumpScore = findViewById( R.id.update_student_jump_id );
        sAbsScore = findViewById( R.id.update_student_abs_id );

        sAerobicScoreText = findViewById( R.id.update_student_aerobic_text );
        sCubesScoreText = findViewById( R.id.update_student_cubes_text );
        sHandsScoreText = findViewById( R.id.update_student_hands_text );
        sJumpScoreText = findViewById( R.id.update_student_jump_text );
        sAbsScoreText = findViewById( R.id.update_student_abs_text );
        sTotalScoreText = findViewById( R.id.update_student_total_score );
        saveButton = findViewById( R.id.button_add_student_enter_data );
        saveButton.setOnClickListener( e -> saveButtonClicked() );

    }

    public void saveButtonClicked() {
        if ( inputErrors() )
            return;
        updateGradeTextFields();
        updateStudentInDataBase();
        askForSendingSMS();
        notifyUserStudentSuccessfullyEntered();
    }

    private void notifyUserStudentSuccessfullyEntered() {
        popToast( "Student scores entered!", Toast.LENGTH_SHORT );
    }

    private void askForSendingSMS() {
        if ( sPhoneNumber.getText().toString().length() > 1 ) {

            final AlertDialog.Builder builder = new AlertDialog.Builder( this );
            builder.setTitle( "לשלוח ציונים לתלמיד?" );

            builder.setItems( answers, new DialogInterface.OnClickListener() {
                @Override
                public void onClick( DialogInterface dialog, int which ) {

                    switch ( which ) {
                        case NO: {
                            return;
                        }
                        case YES: {
                            sendSms();
                            break;
                        }
                    }
                }
            } );
            builder.show();
        }
    }

    private void updateStudentInDataBase() {
        Student updatedStudent = new Student();

        updatedStudent.setName( sName.getText().toString() );
        updatedStudent.setStudentClass( sClass.getText().toString() );
        updatedStudent.setPhoneNumber( sPhoneNumber.getText().toString() );
        updatedStudent.setUpdateDate( AddStudentActivity.getTodayDate() );
        updatedStudent.setAerobicScore( aerobicScore );
        updatedStudent.setAerobicResult( aerobicGrade );
        updatedStudent.setCubesScore( cubesScore );
        updatedStudent.setCubesResult( cubesGrade );
        updatedStudent.setHandsScore( handsScore );
        updatedStudent.setHandsResult( handsGrade );
        updatedStudent.setAbsScore( absScore );
        updatedStudent.setAbsResult( absGrade );
        updatedStudent.setJumpScore( jumpScore );
        updatedStudent.setJumpResult( jumpGrade );
        updatedStudent.setTotalScore( avg );
        updatedStudent.setTotScoreWithoutAerobic( avgWithOutAerobic );
        MainActivity.getDbHandler().updateStudent( updatedStudent );
    }

    private void sendSms() {
        String sPhone = sPhoneNumber.getText().toString();
        Uri uri = Uri.parse( "smsto:" + sPhone );
        Intent smsIntent = new Intent( Intent.ACTION_SEND, uri );
        smsIntent.setType( "text/plain" );
        smsIntent.putExtra( "address", sPhone );

        String txt = getStudentGrades();
        smsIntent.putExtra( Intent.EXTRA_TEXT, txt );

        try {
            startActivity( smsIntent );
        } catch ( ActivityNotFoundException e ) {
            Log.d( TAG, e.getMessage() );
        }
    }

    public void deleteSpecificStudentClicked( View view ) {
        final AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( "Are you sure you want to delete this student?" );

        builder.setItems( answers, new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialog, int which ) {

                switch ( which ) {
                    case NO: {
                        return;
                    }
                    case YES: {
                        Log.w( TAG, "user deleted " + currentStudent );
                        MainActivity.dbHandler.deleteStudentData( currentStudent.getName(), currentStudent.getStudentClass() );
                        String msg = "deleted successfully  " + currentStudent.getName();
                        popToast( msg, Toast.LENGTH_LONG );
                        finish();
                        break;
                    }
                }
            }
        } );
        builder.show();
    }

    private String getStudentGrades() {
        StringBuilder res = new StringBuilder();
        int cnt = 0, STUDENT_FINISHED = 5;
        res.append( "\nשלום " + currentStudent.getName() + ", אלו הם הציונים העדכניים שלך\n" );
        if ( aerobicScore != MISSING && ++ cnt != 0 )
            res.append( "אירובי: " ).append( aerobicScore ).append( " דקות " ).append( " ציון: " ).append( aerobicGrade );
        if ( jumpScore != MISSING && ++ cnt != 0 )
            res.append( "\n" + " קפיצה: " ).append( jumpScore ).append( " ס\"מ " ).append( " ציון: " ).append( jumpGrade );
        if ( absScore != MISSING && ++ cnt != 0 )
            res.append( "\n" + " בטן: " ).append( absScore ).append( " כפיפות " ).append( " ציון: " ).append( absGrade );
        if ( cubesScore != MISSING && ++ cnt != 0 )
            res.append( "\n" + " קוביות: " ).append( cubesScore ).append( " שניות " ).append( " ציון: " ).append( cubesGrade );
        if ( handsScore != MISSING && ++ cnt != 0 )
            res.append( "\n" + "ידיים: " ).append( handsScore ).append( " דקות " ).append( " ציון: " ).append( handsGrade );
        if ( cnt == STUDENT_FINISHED )
            res.append( "\n" + "ציון כולל: " ).append( avg );
        return res.toString();
    }

    private void updateGradeTextFields() {

        sCubesScoreText.setText( calCubesGrade( sCubesScore.getText().toString() ) );
        sAerobicScoreText.setText( calAerobicGrade( sAerobicScore.getText().toString() ) );
        sHandsScoreText.setText( calHandGrade( sHandsScore.getText().toString() ) );
        sJumpScoreText.setText( calJumpGrade( sJumpScore.getText().toString() ) );
        sAbsScoreText.setText( calAbsGrade( sAbsScore.getText().toString() ) );
        final int CATEGORY_NUM = 5;
        avg = (double) ( aerobicGrade + absGrade + jumpGrade + handsGrade + cubesGrade ) / CATEGORY_NUM;
        avgWithOutAerobic = (double) ( absGrade + jumpGrade + handsGrade + cubesGrade ) / ( CATEGORY_NUM - 1 );

        //Updating total score to the screen
        sTotalScoreText.setText( String.valueOf( avg ) );
    }

    private boolean inputErrors() {
        aerobicScore = testInput( sAerobicScore, "aerobic score" );
        jumpScore = testInput( sJumpScore, "jump score" );
        absScore = testInput( sAbsScore, "abs score" );
        cubesScore = testInput( sCubesScore, "cubes score" );
        handsScore = testInput( sHandsScore, "hands Score" );
        if ( aerobicScore == INVALID_INPUT || jumpScore == INVALID_INPUT || absScore == INVALID_INPUT
                || cubesScore == INVALID_INPUT || handsScore == INVALID_INPUT ) {

            popToast( "Invalid input!", Toast.LENGTH_SHORT );
            return true;
        }
        return false;
    }

    private String calCubesGrade( String score ) {
        if ( score.length() == 0 )
            return ZERO;
        cubesGrade = sportResultsArrayList.getCubesResult( Double.parseDouble( score ) );
        return String.valueOf( cubesGrade );

    }

    private String calAerobicGrade( String score ) {
        if ( score.length() == 0 )
            return ZERO;
        aerobicGrade = sportResultsArrayList.getAerobicResult( Double.parseDouble( score ) );
        return String.valueOf( aerobicGrade );

    }

    private String calHandGrade( String score ) {
        if ( score.length() == 0 )
            return ZERO;
        handsGrade = sportResultsArrayList.getHandsResult( Double.parseDouble( score ) );
        return String.valueOf( handsGrade );

    }

    private String calJumpGrade( String score ) {
        if ( score.length() == 0 )
            return ZERO;
        jumpGrade = sportResultsArrayList.geJumpResult( (int) Double.parseDouble( score ) );
        return String.valueOf( jumpGrade );
    }

    private String calAbsGrade( String score ) {
        if ( score.length() == 0 )
            return ZERO;
        absGrade = sportResultsArrayList.getAbsResult( (int) Double.parseDouble( score ) );
        return String.valueOf( absGrade );

    }

    private void popToast( String msg, int duration ) {
        Toast toast = Toast.makeText( getApplicationContext(), msg, duration );
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

}


