package com.sagikor.android.fitracker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVWriter;

class EmailSendTask extends AsyncTask<String, String, String> {
    private Context context;
    private static final String TAG = "EmailSendTask";

    EmailSendTask( Context context ) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        // AUTO GENERATED
    }

    @Override
    protected void onPostExecute( String success ) {
        super.onPostExecute( success );

        CharSequence exportCompleted = "Exporting to Excel file completed";
        CharSequence openingEmail = "Opening email";
        Toast toastOne = Toast.makeText( context, exportCompleted, Toast.LENGTH_SHORT );
        toastOne.show();
        Toast toastTwo = Toast.makeText( context, openingEmail, Toast.LENGTH_SHORT );
        toastTwo.show();

    }

    @Override
    protected String doInBackground( final String... strings ) {
        File file = getNewFile();
        try ( CSVWriter csvWrite = new CSVWriter( new FileWriter( file ) ) ) {
            writeHeader( csvWrite, getExcelHeaders() );
            writeStudentsData( csvWrite );
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

    private void writeStudentsData( CSVWriter csvWrite ) {
        for ( Student student : MainActivity.studentList ) {
            csvWrite.writeNext( student.toArray() );
        }
    }

    private void writeHeader( CSVWriter csvWrite, String[] excelHeaders ) {
        csvWrite.writeNext( excelHeaders );
    }


    private File getNewFile() {
        File exportDir = new File( Environment.getExternalStorageDirectory(), "" );
        return new File( exportDir, "ExcelFile.csv" );
    }


    private String[] getExcelHeaders() {
        final String name = context.getResources().getString( R.string.name );
        final String studentClass = context.getResources().getString( R.string.student_class );
        final String aerobicResult = context.getResources().getString( R.string.aerobic_result );
        final String aerobicScore = context.getResources().getString( R.string.aerobic_score );
        final String absResult = context.getResources().getString( R.string.abs_result );
        final String absScore = context.getResources().getString( R.string.abs_score );
        final String jumpResult = context.getResources().getString( R.string.jump_result );
        final String jumpScore = context.getResources().getString( R.string.jump_score );
        final String handsResult = context.getResources().getString( R.string.hands_result );
        final String handsScore = context.getResources().getString( R.string.hands_score );
        final String cubesResult = context.getResources().getString( R.string.cubes_result );
        final String cubesScore = context.getResources().getString( R.string.cubes_score );
        final String finalScore = context.getResources().getString( R.string.final_score );


        return new String[]{ name, studentClass, aerobicScore, aerobicResult,
                absScore, absResult, handsScore, handsResult, cubesScore, cubesResult
                , jumpScore, jumpResult, finalScore };

    }
}