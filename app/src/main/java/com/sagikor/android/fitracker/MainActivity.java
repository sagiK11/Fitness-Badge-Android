package com.sagikor.android.fitracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static List<Student> studentList = new ArrayList<>();
    Button addStudentButton;
    Button searchStudentsButton;
    Button mailResultsButton;
    Button updateStudentButton;
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
        if ( id == R.id.nav_statistics ) {
            openStatistics();
        } else if ( id == R.id.nav_settings ) {
            openSettings();
        } else if ( id == R.id.nav_sign_out ) {
            signOutQuestionPop();
        }
        return super.onOptionsItemSelected( item );
    }


    private void establishFireBase() {
        FirebaseApp.initializeApp( this );
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

            if ( ! ActivityCompat.shouldShowRequestPermissionRationale( this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE ) ) {
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
        final String USER_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference( "users" ).child( USER_ID ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                final String STUDENTS_CHILD = "students";
                for ( DataSnapshot obj : dataSnapshot.child( STUDENTS_CHILD ).getChildren() ) {
                    Student student = obj.getValue( Student.class );
                    studentList.add( student );
                }
            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                Log.v( "MainActivity", databaseError.getDetails() );
            }
        } );
    }

    private void signOutQuestionPop() {
        final String SURE_QUESTION = getResources().getString( R.string.exit_question );
        final String YES = getResources().getString( R.string.yes );
        final String NO = getResources().getString( R.string.no );
        new SweetAlertDialog( this, SweetAlertDialog.WARNING_TYPE )
                .setTitleText( SURE_QUESTION )
                .setConfirmText( YES )
                .setConfirmClickListener( sDialog -> {
                    sDialog.dismissWithAnimation();
                    signOut();
                } )
                .setCancelButton( NO, SweetAlertDialog::dismissWithAnimation )
                .show();

    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent( this, WelcomeActivity.class );
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity( intent );
        finish();
    }

}