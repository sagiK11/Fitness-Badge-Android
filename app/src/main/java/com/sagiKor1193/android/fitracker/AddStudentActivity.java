package com.sagiKor1193.android.fitracker;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class AddStudentActivity extends StudentActivity {

    private final String TAG = "AddStudentActivity";
    //Alert dialog
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

    private void linkObjects() {
        linkUserInputObjects();
        linkTextToDisplayObjects();
    }

    private void linkUserInputObjects() {
        sName = findViewById( R.id.enter_data_v2_student_to_display_id );
        sPhoneNumber = findViewById( R.id.phone_number_to_enter_id );
        chooseClassButton = findViewById( R.id.student_class_id );
        saveStudentButton = findViewById( R.id.button_add_student_enter_data );
        sAerobicScore = findViewById( R.id.update_student_aerobic_id );
        sCubesScore = findViewById( R.id.update_student_cubes_id );
        sHandsScore = findViewById( R.id.update_student_hands_id );
        sJumpScore = findViewById( R.id.update_student_jump_id );
        sAbsScore = findViewById( R.id.update_student_abs_id );
        chooseClassButton.setOnClickListener( e -> selectStudentClass() );
        saveStudentButton.setOnClickListener( e -> addStudentClicked() );
    }


    private void linkTextToDisplayObjects() {
        sAerobicScoreText = findViewById( R.id.update_student_aerobic_text );
        sCubesScoreText = findViewById( R.id.student_cubes_text );
        sHandsScoreText = findViewById( R.id.student_hands_text );
        sJumpScoreText = findViewById( R.id.student_jump_text );
        sAbsScoreText = findViewById( R.id.student_abs_text );
        sTotalScoreText = findViewById( R.id.student_total_score );
    }

    public void addStudentClicked() {
        if ( inputErrorsDetected() )
            return;
        updateFieldsInApp();
        addStudent();
    }

    private boolean inputErrorsDetected() {
        return errorsInStudentName() || errorsInStudentClass() || errorsInStudentScores();
    }

    private void addStudent() {
        Student newStudent = createNewStudent();
        addStudentToDataBase( newStudent );
    }

    private void addStudentToDataBase( Student newStudent ) {
        if ( ! studentAlreadyExists( newStudent ) ) {
            popFailWindow();
        } else {
            askForSendingSMS();
        }
    }

    private boolean studentAlreadyExists( Student newStudent ) {
        return MainActivity.getDbHandler().addStudentData( newStudent );
    }

    private Student createNewStudent() {
        getUserInput();

        return new Student.Builder( sName.getText().toString().trim() )
                .studentClass( sClassString )
                .phoneNumber( sPhoneNumber.getText().toString() )
                .aerobicScore( aerobicScore )
                .cubesScore( cubesScore )
                .absScore( absScore )
                .jumpScore( jumpScore )
                .handsScore( handsScore )
                .absResult( absGrade )
                .aerobicResult( aerobicGrade )
                .jumpResult( jumpGrade )
                .handsResult( handsGrade )
                .cubesResult( cubesGrade )
                .totalScore( avg )
                .totalScoreWithoutAerobic( avgWithOutAerobic )
                .updatedDate( Utility.getTodayDate() )
                .build();

    }

    private void getUserInput() {
        aerobicScore = testInput( sAerobicScore, "aerobic score" );
        jumpScore = testInput( sJumpScore, "jump score" );
        absScore = testInput( sAbsScore, "abs score" );
        cubesScore = testInput( sCubesScore, "cubes score" );
        handsScore = testInput( sHandsScore, "hands score" );
    }

    private void updateFieldsInApp() {
        sCubesScoreText.setText( calCubesGrade( sCubesScore.getText().toString() ) );
        sAerobicScoreText.setText( calAerobicGrade( sAerobicScore.getText().toString() ) );
        sHandsScoreText.setText( calHandGrade( sHandsScore.getText().toString() ) );
        sJumpScoreText.setText( calJumpGrade( sJumpScore.getText().toString() ) );
        sAbsScoreText.setText( calAbsGrade( sAbsScore.getText().toString() ) );
        calAverages();

        sTotalScoreText.setText( String.valueOf( avg ) );

    }

    private boolean errorsInStudentName() {
        if ( sName.getText().toString().length() == 0 ) {
            popToast( "Please enter student name.", Toast.LENGTH_LONG, getApplicationContext() );
            return true;
        }
        return false;

    }

    private boolean errorsInStudentClass() {
        if ( sClassString == null ) {
            popToast( "Please enter student class.", Toast.LENGTH_LONG, getApplicationContext() );
            return true;
        }
        return false;
    }

    private boolean errorsInStudentScores() {
        return aerobicScore == INVALID_INPUT || jumpScore == INVALID_INPUT ||
                absScore == INVALID_INPUT || cubesScore == INVALID_INPUT ||
                handsScore == INVALID_INPUT;
    }

    private double testInput( EditText text, String place ) {
        return super.testInput( text, place, getApplicationContext() );
    }

    public void selectStudentClass() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder( this );
        View row = getLayoutInflater().inflate( R.layout.class_list_item, null );

        ListView listView = row.findViewById( R.id.class_list_view );
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>( this, android.R.layout.simple_list_item_1, studentsClass );

        listView.setAdapter( arrayAdapter );
        arrayAdapter.notifyDataSetChanged();
        alertDialog.setView( row );

        AlertDialog dialog = alertDialog.create();
        listView.setOnItemClickListener( ( parent, view, position, id ) -> {
            setClass( (String) parent.getItemAtPosition( position ) );
            dialog.dismiss();
        } );
        dialog.show();
    }

    private void setClass( String sClass ) {
        this.sClassString = sClass;
        chooseClassButton.setText( sClass );
    }
}

