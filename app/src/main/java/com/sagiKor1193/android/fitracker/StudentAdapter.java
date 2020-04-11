package com.sagiKor1193.android.fitracker;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {
    private String TAG = "StudentAdapter";


    StudentAdapter( Activity context, List<Student> studentsList ) {
        super( context, 0, studentsList );
    }

    public View getView( int position, View convertView, ViewGroup parent ) {
        View listItemView = convertView;
        if ( listItemView == null ) {
            listItemView = LayoutInflater.from( getContext() ).inflate(
                    R.layout.student_list_item, parent, false );
        }

        Student currentStudent = this.getItem( position );

        TextView studentName = listItemView.findViewById( R.id.student_name_view_data_id );
        studentName.setText( currentStudent.getName() );

        TextView studentClass = listItemView.findViewById( R.id.student_class_view_data_id );
        studentClass.setText( currentStudent.getStudentClass() );

        Log.d( TAG, "finished" );
        return listItemView;
    }

}
