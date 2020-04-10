package com.sagiKor1193.android.fitracker;


import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;


public class UpdateStudentActivity extends StudentActivity {

    private final String TAG = "AddStudentFromResults";
    private final String ZERO = "0";
    SportResultsArrayList sportResultsArrayList = new SportResultsArrayList();


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
        sClassButton.setText( currentStudent.getStudentClass() );
    }

    private void updateScoreTextFields() {
        if ( currentStudent.getAerobicScore() != MISSING_INPUT )
            sAerobicScore.setText( String.valueOf( currentStudent.getAerobicScore() ) );

        if ( currentStudent.getCubesScore() != MISSING_INPUT )
            sCubesScore.setText( String.valueOf( currentStudent.getCubesScore() ) );

        if ( currentStudent.getHandsScore() != MISSING_INPUT )
            sHandsScore.setText( String.valueOf( currentStudent.getHandsScore() ) );

        if ( currentStudent.getAbsScore() != MISSING_INPUT )
            sAbsScore.setText( String.valueOf( currentStudent.getAbsScore() ) );

        if ( currentStudent.getJumpScore() != MISSING_INPUT )
            sJumpScore.setText( String.valueOf( currentStudent.getJumpScore() ) );

        if ( currentStudent.getPhoneNumber() != null )
            sPhoneNumber.setText( currentStudent.getPhoneNumber() );
    }

    private void updateGradeTextFields() {

        sCubesScoreText.setText( calCubesGrade( sCubesScore.getText().toString() ) );
        sAerobicScoreText.setText( calAerobicGrade( sAerobicScore.getText().toString() ) );
        sHandsScoreText.setText( calHandGrade( sHandsScore.getText().toString() ) );
        sJumpScoreText.setText( calJumpGrade( sJumpScore.getText().toString() ) );
        sAbsScoreText.setText( calAbsGrade( sAbsScore.getText().toString() ) );
        calAverages();
        sTotalScoreText.setText( String.valueOf( avg ) );
    }

    private void linkObjects() {
        linkUserInputObjects();
        linkTextToDisplayObjects();
    }

    private void linkUserInputObjects() {
        sName = findViewById( R.id.enter_data_v2_student_to_display_id );
        sClassButton = findViewById( R.id.enter_data_v2_student_class_to_display_id );
        sPhoneNumber = findViewById( R.id.phone_number_to_enter_id );
        sAerobicScore = findViewById( R.id.update_student_aerobic_id );
        sCubesScore = findViewById( R.id.update_student_cubes_id );
        sHandsScore = findViewById( R.id.update_student_hands_id );
        sJumpScore = findViewById( R.id.update_student_jump_id );
        sAbsScore = findViewById( R.id.update_student_abs_id );
    }


    private void linkTextToDisplayObjects() {
        sAerobicScoreText = findViewById( R.id.update_student_aerobic_text );
        sCubesScoreText = findViewById( R.id.update_student_cubes_text );
        sHandsScoreText = findViewById( R.id.update_student_hands_text );
        sJumpScoreText = findViewById( R.id.update_student_jump_text );
        sAbsScoreText = findViewById( R.id.update_student_abs_text );
        sTotalScoreText = findViewById( R.id.update_student_total_score );
        saveStudentButton = findViewById( R.id.button_add_student_enter_data );
        saveStudentButton.setOnClickListener( e -> saveButtonClicked() );
    }


    public void saveButtonClicked() {
        if ( inputErrors() )
            return;
        updateGradeTextFields();
        updateStudentInDataBase();
        askForSendingSMS();
    }


    private void updateStudentInDataBase() {
        MainActivity.getDbHandler().updateStudent(
                new Student.Builder( sName.getText().toString().trim() )
                        .studentClass( sClassButton.getText().toString() )
                        .phoneNumber( getStudentPhoneNumber() )
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
                        .build() );
    }


    private boolean inputErrors() {
        aerobicScore = testInput( sAerobicScore, "aerobic score" );
        jumpScore = testInput( sJumpScore, "jump score" );
        absScore = testInput( sAbsScore, "abs score" );
        cubesScore = testInput( sCubesScore, "cubes score" );
        handsScore = testInput( sHandsScore, "hands Score" );
        if ( aerobicScore == INVALID_INPUT || jumpScore == INVALID_INPUT || absScore == INVALID_INPUT
                || cubesScore == INVALID_INPUT || handsScore == INVALID_INPUT ) {

            popToast( "Invalid input!", Toast.LENGTH_SHORT, getApplicationContext() );
            return true;
        }
        return false;
    }

    @Override
    String calCubesGrade( String score ) {
        if ( score.length() == 0 )
            return ZERO;
        cubesGrade = sportResultsArrayList.getCubesResult( Double.parseDouble( score ) );
        return String.valueOf( cubesGrade );

    }

    @Override
    String calAerobicGrade( String score ) {
        if ( score.length() == 0 )
            return ZERO;
        aerobicGrade = sportResultsArrayList.getAerobicResult( Double.parseDouble( score ) );
        return String.valueOf( aerobicGrade );

    }

    @Override
    String calHandGrade( String score ) {
        if ( score.length() == 0 )
            return ZERO;
        handsGrade = sportResultsArrayList.getHandsResult( Double.parseDouble( score ) );
        return String.valueOf( handsGrade );

    }

    @Override
    String calJumpGrade( String score ) {
        if ( score.length() == 0 )
            return ZERO;
        jumpGrade = sportResultsArrayList.geJumpResult( (int) Double.parseDouble( score ) );
        return String.valueOf( jumpGrade );
    }

    @Override
    String calAbsGrade( String score ) {
        if ( score.length() == 0 )
            return ZERO;
        absGrade = sportResultsArrayList.getAbsResult( (int) Double.parseDouble( score ) );
        return String.valueOf( absGrade );

    }

    private double testInput( EditText text, String place ) {
        return super.testInput( text, place, getApplicationContext() );
    }
}
