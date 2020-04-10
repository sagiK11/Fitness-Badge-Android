package com.sagiKor1193.android.fitracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHandler extends SQLiteOpenHelper {
    private final String TAG = "MyDBHandler";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Gila_Data";

    private static final String TABLE_DATA = "data";
    private static final String COLUMN_SNAME = "_sname";
    private static final String COLUMN_SCLASS = "_sclass";

    private static final String COLUMN_SAEROBIC_RES = "_saeroresult";
    private static final String COLUMN_SAEROBIC_SCORE = "_saeroscore";

    private static final String COLUMN_SCUBES_RES = "_scubesresult";
    private static final String COLUMN_SCUBES_SCORE = "_scubesscore";

    private static final String COLUMN_SABS_RES = "_sabsresult";
    private static final String COLUMN_SABS_SCORE = "_sabsscore";

    private static final String COLUMN_HANDS_RES = "_shandsresult";
    private static final String COLUMN_HANDS_SCORE = "_shandsscore";

    private static final String COLUMN_JUMP_RES = "_sjumpresult";
    private static final String COLUMN_JUMP_SCORE = "_sjumpscore";

    private static final String COLUMN_TOTAL_SCORE = "_stotalscore";
    private static final String COLUMN_TOTAL_SCORE_WITHOUT_AEROBIC = "_stotalscorenoaerobic";
    private static final String COLUMN_PHONE_NUMBER = "_phonenumber";
    private static final String COLUMN_SDATE = "_sdate";


    DataBaseHandler( Context context, String name, SQLiteDatabase.CursorFactory factory, int version ) {
        super( context, DATABASE_NAME, factory, DATABASE_VERSION );
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        Log.d( TAG, "creating tables" );
        String query = "CREATE TABLE " + TABLE_DATA + " ( " +
                COLUMN_SNAME + " TEXT NOT NULL, " +
                COLUMN_SCLASS + " TEXT NOT NULL, " + COLUMN_SAEROBIC_RES + " DOUBLE, "
                + COLUMN_SAEROBIC_SCORE + " DOUBLE, "
                + COLUMN_SCUBES_RES + " DOUBLE, " + COLUMN_SCUBES_SCORE + " DOUBLE, " + COLUMN_SABS_RES + " DOUBLE, "
                + COLUMN_SABS_SCORE + " DOUBLE, " + COLUMN_HANDS_RES + " DOUBLE, " + COLUMN_HANDS_SCORE + " DOUBLE, "
                + COLUMN_JUMP_RES + " DOUBLE, " + COLUMN_JUMP_SCORE + " DOUBLE," + COLUMN_TOTAL_SCORE + " DOUBLE, " +
                COLUMN_TOTAL_SCORE_WITHOUT_AEROBIC + " DOUBLE, " + COLUMN_PHONE_NUMBER + " TEXT, " + COLUMN_SDATE + " TEXT, " +
                " PRIMARY KEY(_sname , _sclass) " +
                " );";
        db.execSQL( query );
        Log.d( TAG, "query = " + query );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_DATA );
        onCreate( db );
    }

    boolean addStudentData( Student data ) {
        ContentValues values = new ContentValues();

        values.put( COLUMN_SNAME, data.getName() );
        values.put( COLUMN_SCLASS, data.getStudentClass() );
        values.put( COLUMN_SDATE, data.getUpdatedDate() ); // Date
        values.put( COLUMN_PHONE_NUMBER, data.getPhoneNumber() );

        values.put( COLUMN_SAEROBIC_RES, data.getAerobicResult() );
        values.put( COLUMN_SAEROBIC_SCORE, data.getAerobicScore() );

        values.put( COLUMN_SCUBES_RES, data.getCubesResult() );
        values.put( COLUMN_SCUBES_SCORE, data.getCubesScore() );

        values.put( COLUMN_SABS_RES, data.getAbsResult() );
        values.put( COLUMN_SABS_SCORE, data.getAbsScore() );

        values.put( COLUMN_HANDS_RES, data.getHandsResult() );
        values.put( COLUMN_HANDS_SCORE, data.getHandsScore() );

        values.put( COLUMN_JUMP_RES, data.getJumpResult() );
        values.put( COLUMN_JUMP_SCORE, data.getJumpScore() );

        values.put( COLUMN_TOTAL_SCORE, data.getTotalScore() );
        values.put( COLUMN_TOTAL_SCORE_WITHOUT_AEROBIC, data.getTotScoreWithoutAerobic() );

        SQLiteDatabase db = getWritableDatabase();
        if ( db.insert( TABLE_DATA, null, values ) == - 1 ) {
            db.close();
            return false;
        }
        return true;
    }

    void deleteStudentData( String sName, String sClass ) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL( "DELETE FROM " + TABLE_DATA +
                " WHERE " + COLUMN_SNAME + "=\"" + sName + "\";" +
                COLUMN_SCLASS + "=\"" + sClass + "\";"
        );
    }

    void clearDataBase() {
        Log.d( TAG, "clearing table" );
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL( "DELETE FROM " + TABLE_DATA );
    }


    int getRowsNum() {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_DATA;

        Cursor recordSet = db.rawQuery( query, null );
        return recordSet.getCount();

    }

    void updateStudent( Student student ) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put( COLUMN_SAEROBIC_SCORE, student.getAerobicScore() );
        content.put( COLUMN_SAEROBIC_RES, student.getAerobicResult() );
        content.put( COLUMN_HANDS_SCORE, student.getHandsScore() );
        content.put( COLUMN_HANDS_RES, student.getHandsResult() );
        content.put( COLUMN_JUMP_SCORE, student.getJumpScore() );
        content.put( COLUMN_JUMP_RES, student.getJumpResult() );
        content.put( COLUMN_SABS_SCORE, student.getAbsScore() );
        content.put( COLUMN_SABS_RES, student.getAbsResult() );
        content.put( COLUMN_SCUBES_SCORE, student.getCubesScore() );
        content.put( COLUMN_SCUBES_RES, student.getCubesResult() );
        content.put( COLUMN_TOTAL_SCORE, student.getTotalScore() );
        content.put( COLUMN_TOTAL_SCORE_WITHOUT_AEROBIC, student.getTotScoreWithoutAerobic() );
        content.put( COLUMN_PHONE_NUMBER, student.getPhoneNumber() );
        content.put( COLUMN_SDATE, student.getUpdatedDate() ); //Date

        String where = COLUMN_SNAME + " = " + "\'" + student.getName() + "\'" + " and " +
                COLUMN_SCLASS + " = " + "\'" + student.getStudentClass() + "\'";

        db.update( TABLE_DATA, content, where, null );
        db.close();
    }

    Student getCurrentRow( int position ) {

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_DATA;
        Student newStudent = new Student();

        Cursor recordSet = db.rawQuery( query, null );
        recordSet.moveToPosition( position );

        if ( recordSet.getString( recordSet.getColumnIndex( COLUMN_SNAME ) ) != null ) {
            newStudent.setName( recordSet.getString( recordSet.getColumnIndex( COLUMN_SNAME ) ) );
        }//1
        if ( recordSet.getString( recordSet.getColumnIndex( COLUMN_SCLASS ) ) != null ) {
            newStudent.setStudentClass( recordSet.getString( recordSet.getColumnIndex( COLUMN_SCLASS ) ) );
        }//2
        if ( recordSet.getString( recordSet.getColumnIndex( COLUMN_SAEROBIC_RES ) ) != null ) {
            newStudent.setAerobicResult( Double.parseDouble( recordSet.getString( recordSet.getColumnIndex( COLUMN_SAEROBIC_RES ) ) ) );
        }//3
        if ( recordSet.getString( recordSet.getColumnIndex( COLUMN_SAEROBIC_SCORE ) ) != null ) {
            newStudent.setAerobicScore( Double.parseDouble( recordSet.getString( recordSet.getColumnIndex( COLUMN_SAEROBIC_SCORE ) ) ) );
        }//4
        if ( recordSet.getString( recordSet.getColumnIndex( COLUMN_SCUBES_RES ) ) != null ) {
            newStudent.setCubesResult( Double.parseDouble( recordSet.getString( recordSet.getColumnIndex( COLUMN_SCUBES_RES ) ) ) );
        }//5
        if ( recordSet.getString( recordSet.getColumnIndex( COLUMN_SCUBES_SCORE ) ) != null ) {
            newStudent.setCubesScore( Double.parseDouble( recordSet.getString( recordSet.getColumnIndex( COLUMN_SCUBES_SCORE ) ) ) );
        }//6
        if ( recordSet.getString( recordSet.getColumnIndex( COLUMN_SABS_RES ) ) != null ) {
            newStudent.setAbsResult( Double.parseDouble( recordSet.getString( recordSet.getColumnIndex( COLUMN_SABS_RES ) ) ) );
        }//7
        if ( recordSet.getString( recordSet.getColumnIndex( COLUMN_SABS_SCORE ) ) != null ) {
            newStudent.setAbsScore( Double.parseDouble( recordSet.getString( recordSet.getColumnIndex( COLUMN_SABS_SCORE ) ) ) );
        }//8
        if ( recordSet.getString( recordSet.getColumnIndex( COLUMN_HANDS_RES ) ) != null ) {
            newStudent.setHandsResult( Double.parseDouble( recordSet.getString( recordSet.getColumnIndex( COLUMN_HANDS_RES ) ) ) );
        }//9
        if ( recordSet.getString( recordSet.getColumnIndex( COLUMN_HANDS_SCORE ) ) != null ) {
            newStudent.setHandsScore( Double.parseDouble( recordSet.getString( recordSet.getColumnIndex( COLUMN_HANDS_SCORE ) ) ) );
        }//10
        if ( recordSet.getString( recordSet.getColumnIndex( COLUMN_JUMP_RES ) ) != null ) {
            newStudent.setJumpResult( Double.parseDouble( recordSet.getString( recordSet.getColumnIndex( COLUMN_JUMP_RES ) ) ) );
        }//11
        if ( recordSet.getString( recordSet.getColumnIndex( COLUMN_JUMP_SCORE ) ) != null ) {
            newStudent.setJumpScore( Double.parseDouble( recordSet.getString( recordSet.getColumnIndex( COLUMN_JUMP_SCORE ) ) ) );
        }//12
        if ( recordSet.getString( recordSet.getColumnIndex( COLUMN_TOTAL_SCORE ) ) != null ) {
            newStudent.setTotalScore( Double.parseDouble( recordSet.getString( recordSet.getColumnIndex( COLUMN_TOTAL_SCORE ) ) ) );
        }//13
        if ( recordSet.getString( recordSet.getColumnIndex( COLUMN_TOTAL_SCORE_WITHOUT_AEROBIC ) ) != null ) {
            newStudent.setTotScoreWithoutAerobic( Double.parseDouble( recordSet.getString( recordSet.getColumnIndex( COLUMN_TOTAL_SCORE_WITHOUT_AEROBIC ) ) ) );
        }//14
        if ( recordSet.getString( recordSet.getColumnIndex( COLUMN_PHONE_NUMBER ) ) != null ) {
            newStudent.setPhoneNumber( recordSet.getString( recordSet.getColumnIndex( COLUMN_PHONE_NUMBER ) ) );
        }//15
        if ( recordSet.getString( recordSet.getColumnIndex( COLUMN_SDATE ) ) != null ) { // Date
            newStudent.setUpdateDate( recordSet.getString( recordSet.getColumnIndex( COLUMN_SDATE ) ) );
        }//16
        recordSet.close();
        db.close();
        return newStudent;

    }
}
