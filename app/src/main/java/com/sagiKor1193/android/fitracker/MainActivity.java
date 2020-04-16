package com.sagiKor1193.android.fitracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    static DatabaseReference dbRef;
    static List<Student> studentList = new ArrayList<>();
    Button addStudentButton, searchStudentsButton, mailResultsButton, updateStudentButton;
    Toolbar toolbar;


    @Override
    protected void onCreate( final Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );


        linkObjects();
        establishFireBase();
        addButtonsOnClickListeners();
        addStudentToStudentsList();

    }


    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.activity_main_drawer, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        int id = item.getItemId();
        switch ( id ) {
            case R.id.nav_statistics: {
                openStatistics();
                break;
            }
            case R.id.nav_settings: {
                openSettings();
                break;
            }
        }
        return super.onOptionsItemSelected( item );
    }


    private void establishFireBase() {
        FirebaseApp.initializeApp( this );
        dbRef = FirebaseDatabase.getInstance().getReference( "students" );
    }


    private void linkObjects() {
        toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
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

    private void openStatistics() {
        startActivity( new Intent( this, StatisticsActivity.class ) );
    }

    private void openSettings() {
        startActivity( new Intent( this, SettingActivity.class ) );
    }

    private void addStudentToStudentsList() {
        dbRef.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                for ( DataSnapshot obj : dataSnapshot.getChildren() ) {
                    Student student = obj.getValue( Student.class );
                    if ( student != null )
                        studentList.add( student );
                }
            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                Log.v( TAG, databaseError.getDetails() );
            }
        } );
    }


}
