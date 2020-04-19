package com.sagikor.android.fitracker;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.Toast;


public class SettingActivity extends AppCompatActivity {

    private Button deleteStudentsButton;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_setting );
        linkObjects();

    }

    private void linkObjects() {
        deleteStudentsButton = findViewById( R.id.delete_all_students_button );
        deleteStudentsButton.setOnClickListener( e -> deleteAllStudents() );
    }

    private void deleteAllStudents() {
        final int NO = 0;
        final String DELETE_STUDENTS_QUESTION = getResources().getString( R.string.delete_all_students_question );
        final String NO_STUDENTS_DELETED = getResources().getString( R.string.no_students_were_deleted );
        final String STUDENTS_DELETED_SUCCESSFULLY = getResources().getString( R.string.students_deleted_successfully );
        final String[] answers = { getResources().getString( R.string.no ),
                getResources().getString( R.string.yes ) };

        final AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( DELETE_STUDENTS_QUESTION );
        builder.setItems( answers, ( dialog, choice ) -> {
            if ( choice == NO ) {
                popToast( NO_STUDENTS_DELETED );
            } else {
                clearDataBase();
                popToast( STUDENTS_DELETED_SUCCESSFULLY );
            }
        } );
        builder.show();
    }

    private void clearDataBase() {
        MainActivity.dbRef.removeValue();
        MainActivity.studentList.clear();
    }


    private void popToast( String msg ) {
        Toast toast = Toast.makeText( getApplicationContext(), msg, Toast.LENGTH_SHORT );
        toast.show();
    }
}
