package com.sagiKor1193.android.fitracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StudentAdapter extends ArrayAdapter<Student> {
    private String TAG = "StudentAdapter";


    StudentAdapter(Activity context, ArrayList<Student> studentsList) {
        super(context, 0, studentsList);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Student currentStudent = this.getItem(position);

        TextView studentName = listItemView.findViewById( R.id.student_name_view_data_id );
        studentName.setText( currentStudent.getName() );

        TextView studentClass = listItemView.findViewById(R.id.student_class_view_data_id);
        studentClass.setText( currentStudent.getStudentClass() );

        Log.d(TAG,"finished");
        return listItemView;
    }

    private void linkObjects() {
    }
}
