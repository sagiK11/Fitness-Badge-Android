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

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ViewStudentsActivity extends AppCompatActivity {
    private final String TAG = "ViewDataActivity";
    StudentAdapter adapter;
    private ListView listView;
    private EditText inputSearch;
    private Context thisContext;
    private ArrayList<Student> studentsList;


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
            new SweetAlertDialog( this, SweetAlertDialog.WARNING_TYPE )
                    .setTitleText( "למחוק תלמיד?" )
                    .setConfirmText( "כן" )
                    .setConfirmClickListener( sDialog -> {
                        sDialog.dismissWithAnimation();
                        deleteStudent( currentStudent );
                    } )
                    .setCancelButton( "לא", SweetAlertDialog::dismissWithAnimation )
                    .show();
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
