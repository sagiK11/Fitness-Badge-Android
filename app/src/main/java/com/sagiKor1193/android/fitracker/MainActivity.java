package com.sagiKor1193.android.fitracker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVWriter;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    static DataBaseHandler dbHandler;
    private int dbVersion = 1;
    private final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    Button addStudentButton, searchStudentsButton, mailResultsButton, updateStudentButton;


    @Override
    protected void onCreate( final Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        establishDataBase();
        linkObjects();
        addButtonsOnClickListeners();

    }

    private void establishDataBase() {
        dbHandler = new DataBaseHandler( this, null, null, dbVersion );
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

            // Permission is not granted
            // Should we show an explanation?
            if ( ActivityCompat.shouldShowRequestPermissionRationale( this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE ) ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions( this,
                        new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE );

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            MultiTasking task = new MultiTasking();
            task.execute();
        }

    }


    public void setting( View view ) {
        startActivity( new Intent( this, SettingActivity.class ) );
    }

    public static DataBaseHandler getDbHandler() {
        return dbHandler;
    }


    @Override
    public void onRequestPermissionsResult( int requestCode,
                                            String permissions[], int[] grantResults ) {
        switch ( requestCode ) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if ( grantResults.length > 0
                        && grantResults[ 0 ] == PackageManager.PERMISSION_GRANTED ) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private class MultiTasking extends AsyncTask<String, String, String> {
        //NUMBER OF COLUMNS IN EXCEL-FILE
        private final int rowsLength = 13;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute( String success ) {
            super.onPostExecute( success );

            Context context = getApplicationContext();
            CharSequence text_1 = "Exporting to Excel file completed";
            CharSequence text_2 = "Opening email";
            Toast toast_1 = Toast.makeText( context, text_1, Toast.LENGTH_SHORT );
            toast_1.show();
            Toast toast_2 = Toast.makeText( context, text_2, Toast.LENGTH_SHORT );
            toast_2.show();

        }

        @Override
        protected void onProgressUpdate( String... values ) {
            super.onProgressUpdate( values );
        }


        @Override
        protected String doInBackground( final String... strings ) {
            File exportDir = new File( Environment.getExternalStorageDirectory(), "" );
            if ( ! exportDir.exists() ) {
                exportDir.mkdirs();
            }

            File file = new File( exportDir, "ExcelFile.csv" );
            try {

                file.createNewFile();
                CSVWriter csvWrite = new CSVWriter( new FileWriter( file ) );
                //data
                ArrayList<String> studentsData = new ArrayList<>();
                addStudentsDataToArrayList( studentsData );
                //Headers
                String header[] = { "שם", "כתה", "אירובי-תוצאה", "אירובי-ציון",
                        "בטן-תוצאה", "בטן-ציון", "ידיים-תוצאה", "ידיים-ציון", "קוביות-תוצאה", "קוביות-ציון"
                        , "קפיצה-תוצאה", "קפיצה-ציון", "ציון סופי" };
                csvWrite.writeNext( header );
                for ( int q = 0, w = 1, e = 2, r = 3, t = 4, y = 5,
                      u = 6, i = 7, o = 8, p = 9, a = 10, s = 11, d = 12 ;
                      q < studentsData.size() ;
                      q += rowsLength, w += rowsLength, e += rowsLength, r += rowsLength,
                              t += rowsLength, y += rowsLength, u += rowsLength,
                              i += rowsLength, o += rowsLength, p += rowsLength,
                              a += rowsLength, s += rowsLength, d += rowsLength ) {

                    String arrStr[] =
                            { studentsData.get( q ), studentsData.get( w ), studentsData.get( e ),
                                    studentsData.get( r ), studentsData.get( t ), studentsData.get( y ),
                                    studentsData.get( u ), studentsData.get( i ), studentsData.get( o ),
                                    studentsData.get( p ), studentsData.get( a ), studentsData.get( s ),
                                    studentsData.get( d ) };
                    csvWrite.writeNext( arrStr );
                }

                csvWrite.close();
                //Email sending
                Intent sendToMail = new Intent( Intent.ACTION_SENDTO );
                sendToMail.setData( Uri.parse( "mailto:" ) );

                sendToMail.putExtra( Intent.EXTRA_SUBJECT, "Students Test Grades" );
                sendToMail.putExtra( Intent.EXTRA_STREAM, Uri.fromFile( file.getAbsoluteFile() ) );

                if ( sendToMail.resolveActivity( getPackageManager() ) != null )
                    startActivity( sendToMail );
                return null;
            } catch ( IOException e ) {
                Log.e( TAG, e.getMessage(), e );
                return null;
            }

        }

        private void addStudentsDataToArrayList( ArrayList<String> studentsData ) {
            for ( int i = 0 ; i < MainActivity.getDbHandler().getRowsNum() ; i++ ) {
                Student currentStudent = MainActivity.dbHandler.getCurrentRow( i );

                studentsData.add( currentStudent.getName() );
                studentsData.add( currentStudent.getStudentClass() );
                studentsData.add( String.valueOf( currentStudent.getAerobicScore() ) );
                studentsData.add( String.valueOf( currentStudent.getAerobicResult() ) );
                studentsData.add( String.valueOf( currentStudent.getAbsScore() ) );
                studentsData.add( String.valueOf( currentStudent.getAbsResult() ) );
                studentsData.add( String.valueOf( currentStudent.getHandsScore() ) );
                studentsData.add( String.valueOf( currentStudent.getHandsResult() ) );
                studentsData.add( String.valueOf( currentStudent.getCubesScore() ) );
                studentsData.add( String.valueOf( currentStudent.getCubesResult() ) );
                studentsData.add( String.valueOf( currentStudent.getJumpScore() ) );
                studentsData.add( String.valueOf( currentStudent.getJumpResult() ) );
                studentsData.add( String.valueOf( currentStudent.getTotalScore() ) );
            }
        }
    }

}
