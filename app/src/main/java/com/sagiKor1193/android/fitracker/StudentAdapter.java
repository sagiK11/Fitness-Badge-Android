package com.sagiKor1193.android.fitracker;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

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

        //COLUMNS INDEX
        TextView col2 = listItemView.findViewById(R.id.name_to_display);
        TextView col4 = listItemView.findViewById(R.id.class_to_display);

        TextView col22 = listItemView.findViewById(R.id.aero_score_to_diasplay);
        TextView col23 = listItemView.findViewById(R.id.aero_res_to_diasplay);

        TextView col32 = listItemView.findViewById(R.id.abs_score_to_diasplay);
        TextView col33 = listItemView.findViewById(R.id.abs_res_to_diasplay);

        TextView col42 = listItemView.findViewById(R.id.jump_score_to_diasplay);
        TextView col43 = listItemView.findViewById(R.id.jump_res_to_diasplay);

        TextView col52 = listItemView.findViewById(R.id.hands_score_to_diasplay);
        TextView col53 = listItemView.findViewById(R.id.hands_res_to_diasplay);

        TextView col62 = listItemView.findViewById(R.id.cubes_score_to_diasplay);
        TextView col63 = listItemView.findViewById(R.id.cubes_res_to_diasplay);
        TextView col71 = listItemView.findViewById(R.id.total_score_row_7_to_display);
        TextView col73 = listItemView.findViewById(R.id.total_score_without_aero_row_7_to_display);

        TextView col74 = listItemView.findViewById(R.id.sms_to_id);

        //SETTING TEXT IN SCREEN
        col2.setText( currentStudent.getSName().trim() );
        col4.setText( currentStudent.getSClass().trim() );

        if(currentStudent.getPhoneNumber() == null)
            col74.setText(R.string.no_phone);
        else
            col74.setText( currentStudent.getPhoneNumber().trim() );

        col22.setText( String.valueOf( currentStudent.getAeroScore() ));
        col23.setText( String.valueOf( currentStudent.getAeroRes() ));
        col32.setText( String.valueOf( currentStudent.getAbsScore() ));
        col33.setText( String.valueOf( currentStudent.getAbsResult() ));
        col42.setText( String.valueOf( currentStudent.getJumpScore() ));
        col43.setText( String.valueOf( currentStudent.getJumpResult() ));
        col52.setText( String.valueOf( currentStudent.getHandsScore() ));
        col53.setText( String.valueOf( currentStudent.getHandsResult() ));
        col62.setText( String.valueOf( currentStudent.getCubesScore() ));
        col63.setText( String.valueOf( currentStudent.getCubesResult() ));
        col71.setText( String.valueOf( currentStudent.getTotalScore() ));
        col73.setText( String.valueOf( currentStudent.getTotScoreWithoutAerobic() ));


        Log.d(TAG,"finished");
        return listItemView;
    }
}
