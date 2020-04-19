package com.sagikor.android.fitracker;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddStudentActivity extends StudentActivity {

    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_student );
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
        genderButton = findViewById( R.id.gender_button );
        saveStudentButton = findViewById( R.id.button_add_student_enter_data );
        sAerobicScore = findViewById( R.id.update_student_aerobic_id );
        sCubesScore = findViewById( R.id.update_student_cubes_id );
        sHandsScore = findViewById( R.id.update_student_hands_id );
        sJumpScore = findViewById( R.id.update_student_jump_id );
        sAbsScore = findViewById( R.id.update_student_abs_id );
        chooseClassButton.setOnClickListener( e -> selectStudentClass() );
        genderButton.setOnClickListener( e -> selectStudentGender() );
        saveStudentButton.setOnClickListener( e -> addStudentClicked() );
        //TODO ISSUE 1183 & 1184 - WAITING FOR CLIENT SCORES TABLE
//        sAbsScore.setOnClickListener( e -> chooseAbsTestOptionPopup() );
//        sHandsScore.setOnClickListener( e -> chooseHandsTestOptionPopup() );
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
        setDefaultGender();
        Student newStudent = createNewStudent();
        addStudentToFireBase( newStudent );
    }



    private void addStudentToFireBase( Student newStudent ) {
        for ( Student student : MainActivity.studentList ) {
            if ( studentExists( student, newStudent ) ) {
                popFailWindow();
                return;
            }
        }
        DatabaseReference dbRef = MainActivity.dbRef;
        dbRef.child( newStudent.getKey() ).setValue( newStudent );
        MainActivity.studentList.add( newStudent );
        askForSendingSMS();
    }

    private boolean studentExists( Student student, Student newStudent ) {
        return student != null && student.getKey() != null && student.getKey().equals( newStudent.getKey() );
    }

    private Student createNewStudent() {
        getUserInput();
        String studentKey = MainActivity.dbRef.push().getKey();
        return new Student.Builder( getStudentName() )
                .studentClass( sClassString )
                .phoneNumber( sPhoneNumber.getText().toString() )
                .key( studentKey )
                .studentGender( sGenderString )
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

    private String getStudentName() {
        return sName.getText().toString().trim();
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

    private void setDefaultGender() {
        sGenderString = getResources().getString( R.string.girl );
    }

    private void selectStudentGender() {
        final String CHOOSE_GENDER = getResources().getString( R.string.choose_gender );
        final String BOY = getResources().getString( R.string.boy );
        final String GIRL = getResources().getString( R.string.girl );

        new SweetAlertDialog( this, SweetAlertDialog.WARNING_TYPE )
                .setTitleText( CHOOSE_GENDER )
                .setConfirmText( GIRL )
                .setConfirmClickListener( sDialog -> {
                    sDialog.dismissWithAnimation();
                    sGenderString = GIRL;
                    genderButton.setText( sGenderString );

                } )
                .setCancelButton( BOY, sDialog -> {
                    sDialog.dismissWithAnimation();
                    sGenderString = BOY;
                    genderButton.setText( sGenderString );
                } )
                .show();
    }

    public void selectStudentClass() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder( this );
        View row = getLayoutInflater().inflate( R.layout.class_list_item, null );

        ListView listView = row.findViewById( R.id.class_list_view );
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>( this, android.R.layout.simple_list_item_1, getStudentsClasses() );

        listView.setAdapter( arrayAdapter );
        arrayAdapter.notifyDataSetChanged();
        alertDialog.setView( row );

        AlertDialog dialog = alertDialog.create();
        final String CHOOSE_CLASS = getResources().getString( R.string.choose_class );
        dialog.setTitle(CHOOSE_CLASS);
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

    private String[] getStudentsClasses() {
        final String ninth1 = getResources().getString( R.string.ninth1 );
        final String ninth2 = getResources().getString( R.string.ninth2 );
        final String ninth3 = getResources().getString( R.string.ninth3 );
        final String ninth4 = getResources().getString( R.string.ninth4 );
        final String ninth5 = getResources().getString( R.string.ninth5 );
        final String ninth6 = getResources().getString( R.string.ninth6 );
        final String ninth7 = getResources().getString( R.string.ninth7 );
        final String ninth8 = getResources().getString( R.string.ninth8 );
        final String ninth9 = getResources().getString( R.string.ninth9 );

        final String tenth1 = getResources().getString( R.string.tenth1 );
        final String tenth2 = getResources().getString( R.string.tenth2 );
        final String tenth3 = getResources().getString( R.string.tenth3 );
        final String tenth4 = getResources().getString( R.string.tenth4 );
        final String tenth5 = getResources().getString( R.string.tenth5 );
        final String tenth6 = getResources().getString( R.string.tenth6 );
        final String tenth7 = getResources().getString( R.string.tenth7 );
        final String tenth8 = getResources().getString( R.string.tenth8 );
        final String tenth9 = getResources().getString( R.string.tenth9 );

        final String eleventh1 = getResources().getString( R.string.eleventh1 );
        final String eleventh2 = getResources().getString( R.string.eleventh2 );
        final String eleventh3 = getResources().getString( R.string.eleventh3 );
        final String eleventh4 = getResources().getString( R.string.eleventh4 );
        final String eleventh5 = getResources().getString( R.string.eleventh5 );
        final String eleventh6 = getResources().getString( R.string.eleventh6 );
        final String eleventh7 = getResources().getString( R.string.eleventh7 );
        final String eleventh8 = getResources().getString( R.string.eleventh8 );
        final String eleventh9 = getResources().getString( R.string.eleventh9 );

        final String twelfth1 = getResources().getString( R.string.twelfth1 );
        final String twelfth2 = getResources().getString( R.string.twelfth2 );
        final String twelfth3 = getResources().getString( R.string.twelfth3 );
        final String twelfth4 = getResources().getString( R.string.twelfth4 );
        final String twelfth5 = getResources().getString( R.string.twelfth5 );
        final String twelfth6 = getResources().getString( R.string.twelfth6 );
        final String twelfth7 = getResources().getString( R.string.twelfth7 );
        final String twelfth8 = getResources().getString( R.string.twelfth8 );
        final String twelfth9 = getResources().getString( R.string.twelfth9 );

        return new String[]{ ninth1, ninth2, ninth3, ninth4, ninth5, ninth6, ninth7, ninth8, ninth9,
                tenth1, tenth2, tenth3, tenth4, tenth5, tenth6, tenth7, tenth8, tenth9,
                eleventh1, eleventh2, eleventh3, eleventh4, eleventh5, eleventh6,
                eleventh7, eleventh8, eleventh9, twelfth1, twelfth2, twelfth3, twelfth4
                , twelfth5, twelfth6, twelfth7, twelfth8, twelfth9 };
    }

}

