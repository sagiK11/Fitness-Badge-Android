package com.sagiKor1193.android.fitracker;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;


public class SettingActivity extends AppCompatActivity {
    private final String TAG = "Setting";
    private final String[] answers = { "לא", "כן" };
    private final int NO = 0, YES = 1;


    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_setting );
    }

    public void deleteAllStudents( View view ) {
        final AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( "האם את/ה בטוח שאתה רוצה למחוק את כל התלמידים?" );
        builder.setItems( answers, ( dialog, which ) -> {

            switch ( which ) {
                case NO: {
                    popToast( "לא נמחקו תלמידים" );
                    return;
                }
                case YES: {
                    Log.w( TAG, "user deleted all students" );
                    clearDataBase();
                    popToast( "התלמידים נמחקו בהצלחה." );
                    break;
                }
            }
        } );
        builder.show();
    }

    private void clearDataBase() {
        MainActivity.dbRef.removeValue();
        MainActivity.studentList.clear();
    }


    private void popToast( String msg ) {
        Toast toast = Toast.makeText( getApplicationContext(), msg, Toast.LENGTH_SHORT );
        toast.show();
    }
}
