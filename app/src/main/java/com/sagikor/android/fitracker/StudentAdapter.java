package com.sagikor.android.fitracker;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {
    private Context context;

    StudentAdapter( Activity context, List<Student> studentsList ) {
        super( context, 0, studentsList );
        this.context =context;
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

        ImageView studentAvatar = listItemView.findViewById( R.id.avatar_image_view );

        if(currentStudent.getGender().equals( context.getResources().getString( R.string.girl ) )){
            studentAvatar.setImageDrawable( context.getResources().getDrawable( R.mipmap.female_athlete_avatar ,null) );
        }else {
            studentAvatar.setImageDrawable( context.getResources().getDrawable( R.mipmap.male_avatar ,null) );
        }

        return listItemView;
    }

}
