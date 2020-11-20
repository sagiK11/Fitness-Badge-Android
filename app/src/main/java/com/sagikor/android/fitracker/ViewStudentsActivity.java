package com.sagikor.android.fitracker;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ViewStudentsActivity extends AppCompatActivity {
    private static final String TAG = "ViewDataActivity";
    StudentAdapter adapter;
    private ListView listView;
    private EditText inputSearch;
    private List<Student> studentsList;
    private static final String STUDENTS_CHILD = "students";


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate( final Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_view_students );
        checkFilePermissions();

        linkObjects();
        addInputSearch();

        createStudentList();
        createStudentAdapter();
        setListViewAdapter();
        refreshList();

        addListViewItemListener();
    }


    private void linkObjects() {
        listView = findViewById( R.id.list_view );
        inputSearch = findViewById( R.id.search_input );
    }

    private void addListViewItemListener() {
        listView.setOnItemClickListener( ( parent, view, position, id ) -> {
            Intent enterData = new Intent( this, UpdateStudentActivity.class );
            enterData.putExtra( "student", (Student) parent.getItemAtPosition( position ) );
            startActivity( enterData );
        } );

        final String YES = getResources().getString( R.string.yes );
        final String NO = getResources().getString( R.string.no );
        final String DELETE_STUDENT_QUESTION = getResources().getString( R.string.delete_student_question );

        listView.setOnItemLongClickListener( ( parent, view, position, id ) -> {
            final Student currentStudent = (Student) parent.getItemAtPosition( position );
            new SweetAlertDialog( this, SweetAlertDialog.WARNING_TYPE )
                    .setTitleText( DELETE_STUDENT_QUESTION )
                    .setConfirmText( YES )
                    .setConfirmClickListener( sDialog -> {
                        sDialog.dismissWithAnimation();
                        deleteStudent( currentStudent );
                    } )
                    .setCancelButton( NO,
                            SweetAlertDialog::dismissWithAnimation )
                    .show();
            return true;

        } );

    }

    private void deleteStudent( Student currentStudent ) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference( "users" )
                .child( userId ).child( STUDENTS_CHILD ).child( currentStudent.getKey() )
                .removeValue();
        onRestart();
    }

    public void refreshList() {

        FirebaseDatabase.getInstance().getReference( "users" )
                .child( FirebaseAuth.getInstance().getCurrentUser().getUid() )
                .addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                studentsList.clear();
                for ( DataSnapshot obj : dataSnapshot.child( STUDENTS_CHILD ).getChildren() ) {
                    Student student = obj.getValue( Student.class );
                    studentsList.add( student );
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                Log.d( TAG, databaseError.getDetails() );
            }
        } );

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
        refreshList();
    }
}
