package com.sagiKor1193.android.fitracker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewStudentsActivity extends AppCompatActivity {
    private final String TAG = "ViewDataActivity";
    StudentAdapter adapter;
    private ListView listView;
    private EditText inputSearch;
    private Context thisContext;
    private ArrayList<Student> studentsList;
    private String studentClass;

    private String[] studentsClass = { "ט 1", "ט 2", "ט 3", "ט 4", "ט 5", "ט 6", "ט 7", "ט 8", "ט 9",
            "י 1", "י 2", "י 3", "י-4", "י 5", "י 6", "י 7", "י 8", "י 9",
            "י\"א 1", "י\"א 2", "י\"א 3", "י\"א 4", "י\"א 5", "י\"א 6",
            "י\"א 7", "י\"א 8", "י\"א 9", "י\"ב 1", "י\"ב 2", "י\"ב 3", "י\"ב 4"
            , "י\"ב 5", "י\"ב 6", "י\"ב 7", "י\"ב 8", "י\"ב 9", "כולם" };


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate( final Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_view_students );
        checkFilePermissions();

        linkObjects();
        addInputSearch();

        createStudentList();
        refreshList();

        createStudentAdapter();

        addListViewItemListener();
        setListViewAdapter();
    }




    private boolean studentFinished( Student curr ) {

        final int MISSING = - 1;
        return curr.getAbsScore() != MISSING && curr.getAerobicScore() != MISSING
                && curr.getCubesScore() != MISSING && curr.getJumpScore() != MISSING
                && curr.getHandsScore() != MISSING;
    }

    private void linkObjects() {
        thisContext = this;
        listView = findViewById( R.id.list_view );
        inputSearch = findViewById( R.id.search_input );
    }

    private void addListViewItemListener() {
        //Update this student
        listView.setOnItemClickListener( ( parent, view, position, id ) -> {
            Intent enterData = new Intent( thisContext, UpdateStudentActivity.class );
            enterData.putExtra( "student", (Student) parent.getItemAtPosition( position ) );
            startActivity( enterData );
        } );

        listView.setOnItemLongClickListener( ( parent, view, position, id ) -> {
            final Student currentStudent = (Student) parent.getItemAtPosition( position );
            final int DELETE_STUDENT = 1;

            final AlertDialog.Builder builder = new AlertDialog.Builder( thisContext );
            builder.setTitle( "מחק סטודנט?" );
            String[] answers = { "No", "Yes" };

            builder.setItems( answers, ( dialog, which ) -> {
                if ( which == DELETE_STUDENT ) {
                    deleteStudent( currentStudent );
                }
            } );
            builder.show();

            return false;

        } );

    }

    private void deleteStudent( Student currentStudent ) {
        String studentName = currentStudent.getName();
        String studentClass = currentStudent.getStudentClass();
        MainActivity.dbHandler.deleteStudentData( studentName, studentClass );
        onRestart();
    }

    public void refreshList() {
        int length = MainActivity.dbHandler.getRowsNum();
        studentsList.clear();
        Student currentStudent;
        for ( int i = 0 ; i < length ; i++ ) {
            currentStudent = MainActivity.dbHandler.getCurrentRow( i );

            if ( studentClass == null )
                studentsList.add( currentStudent );
            else if ( currentStudent.getStudentClass().trim().equals( studentClass ) )
                studentsList.add( currentStudent );

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkFilePermissions() {
        if ( Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP ) {
            int permissionCheck = this.checkSelfPermission( "Manifest.permission.SEND_SMS" );
            if ( permissionCheck != 0 ) {
                this.requestPermissions( new String[]{ Manifest.permission.SEND_SMS }, 1001 ); //Any number
            }
        } else {
            Log.d( TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP." );
        }
    }

    private void addInputSearch() {

        inputSearch.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {
                //AUTO-GENERATED
            }

            @Override
            public void onTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {
                ViewStudentsActivity.this.adapter.getFilter().filter( charSequence );
            }

            @Override
            public void afterTextChanged( Editable editable ) {
                //AUTO-GENERATED
            }
        } );

    }

    private void createStudentAdapter() {
        adapter = new StudentAdapter( this, studentsList );
    }

    private void createStudentList() {
        studentsList = new ArrayList<>();
    }

    private void setListViewAdapter() {
        listView.setAdapter( adapter );
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.d( TAG, "restarted" );

        refreshList();
        adapter.notifyDataSetChanged();
    }

}
