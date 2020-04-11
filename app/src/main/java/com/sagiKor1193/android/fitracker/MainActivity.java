package com.sagiKor1193.android.fitracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    static DatabaseReference dbRef;
    Button addStudentButton, searchStudentsButton, mailResultsButton, updateStudentButton;


    @Override
    protected void onCreate( final Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        linkObjects();
        establishFireBase();
        addButtonsOnClickListeners();

    }

    private void establishFireBase() {
        FirebaseApp.initializeApp( this );
        dbRef = FirebaseDatabase.getInstance().getReference( "students" );
    }


    private void linkObjects() {
        addStudentButton = findViewById( R.id.button_add_student );
        searchStudentsButton = findViewById( R.id.button_search );
        mailResultsButton = findViewById( R.id.button_mail_results );
        updateStudentButton = findViewById( R.id.button_update_student );
    }

    private void addButtonsOnClickListeners() {
        addStudentButton.setOnClickListener( e -> addStudent() );
        searchStudentsButton.setOnClickListener( e -> searchStudents() );
        mailResultsButton.setOnClickListener( e -> mailResults() );
        updateStudentButton.setOnClickListener( e -> searchStudents() );
    }


    public void addStudent() {
        startActivity( new Intent( this, AddStudentActivity.class ) );
    }


    public void searchStudents() {
        startActivity( new Intent( this, ViewStudentsActivity.class ) );
    }

    public void mailResults() {
        if ( ContextCompat.checkSelfPermission( this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE )
                != PackageManager.PERMISSION_GRANTED ) {

            if ( ActivityCompat.shouldShowRequestPermissionRationale( this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE ) ) {

            } else {
                final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
                ActivityCompat.requestPermissions( this,
                        new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE );
            }
        } else {
            EmailSendTask task = new EmailSendTask( this );
            task.execute();
        }

    }


    public void setting( View view ) {
        startActivity( new Intent( this, SettingActivity.class ) );
    }

}
