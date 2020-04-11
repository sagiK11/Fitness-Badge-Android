package com.sagiKor1193.android.fitracker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import au.com.bytecode.opencsv.CSVWriter;

class EmailSendTask extends AsyncTask<String, String, String> {
    private boolean isDataCopyingFinished;
    private Context context;
    private final String TAG = "EmailSendTask";

    EmailSendTask( Context context ) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute( String success ) {
        super.onPostExecute( success );

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
        File file = getNewFile();
        try {
            CSVWriter csvWrite = new CSVWriter( new FileWriter( file ) );
            List<String> studentsData = new ArrayList<>();
            addStudentsDataToArrayList( studentsData, this );
            waitForStudentsData();
            writeHeader( csvWrite, getExcelHeaders() );
            writeStudentsData( csvWrite, studentsData );
            csvWrite.close();
            sendEmail( file );
        } catch ( IOException e ) {
            Log.e( TAG, e.getMessage(), e );
        }
        return null;
    }

    private void sendEmail( File file ) {
        Intent sendToMail = new Intent( Intent.ACTION_SENDTO );
        sendToMail.setData( Uri.parse( "mailto:" ) );
        sendToMail.putExtra( Intent.EXTRA_SUBJECT, "Students Test Grades" );
        sendToMail.putExtra( Intent.EXTRA_STREAM, Uri.fromFile( file.getAbsoluteFile() ) );
        if ( sendToMail.resolveActivity( context.getPackageManager() ) != null )
            context.startActivity( sendToMail );
    }

    private void writeStudentsData( CSVWriter csvWrite, List<String> studentsData ) {
        final int rowsLength = 13;
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
    }

    private void writeHeader( CSVWriter csvWrite, String[] excelHeaders ) {
        csvWrite.writeNext( excelHeaders );
    }

    private synchronized void waitForStudentsData() {
        try {
            while ( ! isDataCopyingFinished ) {
                wait();
            }
        } catch ( InterruptedException e ) {
            Log.d( TAG, e.getMessage() );
        }
    }

    private File getNewFile() {
        File exportDir = new File( Environment.getExternalStorageDirectory(), "" );
        return new File( exportDir, "ExcelFile.csv" );
    }


    private void addStudentsDataToArrayList( List<String> studentsData, EmailSendTask ctx ) {
        MainActivity.dbRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                Log.d( TAG, "WRITING TO STUDENTS LIST" );
                for ( DataSnapshot obj : dataSnapshot.getChildren() ) {
                    Student currentStudent = obj.getValue( Student.class );
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
                isDataCopyingFinished = true;
                Log.d( TAG, "DONE WRITING, NOTIFYING ALL" );
                synchronized ( ctx ) {
                    ctx.notifyAll();
                }
            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                Log.d( TAG, databaseError.getDetails() );
            }
        } );
    }

    private String[] getExcelHeaders() {
        String[] res = { "שם", "כתה", "אירובי-תוצאה", "אירובי-ציון",
                "בטן-תוצאה", "בטן-ציון", "ידיים-תוצאה", "ידיים-ציון", "קוביות-תוצאה", "קוביות-ציון"
                , "קפיצה-תוצאה", "קפיצה-ציון", "ציון סופי" };
        return res;
    }
}