package com.sagiKor1193.android.fitracker;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class AddStudentActivity extends AppCompatActivity {


    SportResultsList sportResultsList = new SportResultsList();
    private final int CATEGORY_NUM = 5;
    private final int INVALID_INPUT = -2;
    EditText sName;
    private String sClass;
    EditText sAeroScore;
    EditText sCubesScore;
    EditText sHandsScore;
    EditText sAbsScore;
    EditText sJumpScore;
    TextView sAeroScoreText;
    TextView sCubesScoreText;
    TextView sHandsScoreText;
    TextView sAbsScoreText;
    TextView sJumpScoreText;
    TextView sTotalScoreText;
    CheckBox checkBox;
    private double aeroScore;
    private double cubesScore;
    private double handsScore;
    private double absScore;
    private double jumpScore;
    private int aeroGrade;
    private int cubesGrade;
    private int handsGrade;
    private int absGrade;
    private int jumpGrade;
    private double avg;
    private double avgWithOutAero;

    private String[] studentsClass = {"ט 1", "ט 2", "ט 3", "ט 4", "ט 5", "ט 6", "ט 7", "ט 8",
                                        "י 1", "י 2", "י 3", "י-4", "י 5", "י 6", "י 7", "י 8",
                                        "י\"א 1", "י\"א 2", "י\"א 3", "י\"א 4", "י\"א 5", "י\"א 6",
                                        "י\"א 7", "י\"א 8", "י\"ב 1", "י\"ב 2", "י\"ב 3", "י\"ב 4"
                                        , "י\"ב 5", "י\"ב 6", "י\"ב 7", "י\"ב 8"};

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_data);
        linkObjects();
        }


        public void enterButtonClicked(View view){
            //VALIDATING INPUT
            if( ! testForErrors() || getUserInput() )
                return;
            //UPDATING SCREEN
            updateFields();
            //UPDATING DB
            addOrUpdateStudent();
        }

    private void addOrUpdateStudent() {
        //UPDATING OR ADDING STUDENT TO DB
        if(checkBox.isChecked()) {
            int[] ctrl = {1};
            MainActivity.getDbHandler().updateStudent(sName.getText().toString(), sClass,
                    aeroScore, cubesScore, absScore, jumpScore, handsScore, ctrl);
            if(ctrl[0] != 1)
                popToast("Student was not found.",Toast.LENGTH_LONG);
            else
                popToast("Student " + sName.getText().toString() + " updated!",Toast.LENGTH_SHORT);

        }else{
            Student newStudent = new Student(sName.getText().toString(), sClass,
                    aeroScore, cubesScore, absScore, jumpScore, handsScore);
            //Updating Student fields.
            newStudent.setAbsRes(absGrade);
            newStudent.setAeroRes(aeroGrade);
            newStudent.setJumpRes(jumpGrade);
            newStudent.setHandsRes(handsGrade);
            newStudent.setCubesRes(cubesGrade);
            newStudent.setTotalScore(avg);
            newStudent.setTotScoreWithoutAerobic(avgWithOutAero);

            //Adding student to data base
            if( ! (MainActivity.getDbHandler().addStudentData( newStudent )) ){
                popToast("Student with that name and class already entered",Toast.LENGTH_LONG);
            }else{
                popToast("Student scores entered!", Toast.LENGTH_SHORT);
            }
        }
    }

    private boolean getUserInput() {
        //Getting Input + Testing for validation
        aeroScore = testInput(sAeroScore, "aerobic score");
        jumpScore = testInput(sJumpScore, "jump score");
        absScore = testInput(sAbsScore, "abs score");
        cubesScore = testInput(sCubesScore, "cubes score");
        handsScore = testInput(sHandsScore, "hands Score");
        return aeroScore == INVALID_INPUT || jumpScore == INVALID_INPUT ||
                absScore == INVALID_INPUT || cubesScore == INVALID_INPUT ||
                handsScore == INVALID_INPUT;
    }

    private void updateFields() {
        //Updating relevant text with grades.
        sCubesScoreText.setText(this.calCubesGrade(sCubesScore.getText().toString()));
        sAeroScoreText.setText(this.calAeroGrade(sAeroScore.getText().toString()));
        sHandsScoreText.setText(this.calHandGrade(sHandsScore.getText().toString()));
        sJumpScoreText.setText(this.calJumpGrade(sJumpScore.getText().toString()));
        sAbsScoreText.setText(this.calAbsGrade(sAbsScore.getText().toString()));
        avg = (double) (aeroGrade + absGrade + jumpGrade + handsGrade + cubesGrade) / CATEGORY_NUM;
        avgWithOutAero = (double) ( absGrade + jumpGrade + handsGrade + cubesGrade) / (CATEGORY_NUM - 1);
        //Updating total score to the screen
        sTotalScoreText.setText(String.valueOf( avg ));

    }

    private boolean testForErrors() {
        if (sClass == null) {
            popToast("Please enter student class.", Toast.LENGTH_LONG);
            return false;
        } else if (sName.getText().toString().length() == 0) {
            popToast("Please enter student name.", Toast.LENGTH_LONG);
            return false;
        }
        return true;
    }

    private void linkObjects() {
        //USER INPUT
        sName = findViewById(R.id.student_name_id);
        sAeroScore =  findViewById(R.id.student_aerobic_id);
        sCubesScore = findViewById(R.id.student_cubes_id);
        sHandsScore = findViewById(R.id.student_hands_id);
        sJumpScore = findViewById(R.id.student_jump_id);
        sAbsScore = findViewById(R.id.student_abs_id);
        checkBox = findViewById(R.id.student_update_check_box);
        //TEXT TO DISPLAY
        sAeroScoreText = findViewById(R.id.student_aerobic_text);
        sCubesScoreText = findViewById(R.id.student_cubes_text);
        sHandsScoreText = findViewById(R.id.student_hands_text);
        sJumpScoreText = findViewById(R.id.student_jump_text);
        sAbsScoreText = findViewById(R.id.student_abs_text);
        sTotalScoreText = findViewById(R.id.student_total_score);
    }

    private String calCubesGrade(String score){
            double tempCubes = 0;

            try{
                tempCubes = Double.parseDouble(score);
            }catch (NumberFormatException e){
                return null;
            }
            cubesGrade = sportResultsList.getCubesResult(tempCubes);
            return String.valueOf( cubesGrade );


        }
        private String calAeroGrade(String score){
            double temAero = 0;

            try{
                temAero = Double.parseDouble(score);
            }catch (NumberFormatException e){
                return null;
            }
            aeroGrade = sportResultsList.getAeroResult(temAero) ;
            return String.valueOf( aeroGrade );

        }
        private String calHandGrade(String score){
            double tempHands = 0;

            try{
                tempHands = Double.parseDouble(score);
            }catch (NumberFormatException e){
                return null;
            }
            handsGrade =  sportResultsList.getHandsResult(tempHands);
            return String.valueOf( handsGrade );


        }
        private String calJumpGrade(String score){
            int tempJump = 0;
            try{
                tempJump = Integer.parseInt(score);
            }catch (NumberFormatException e){
                return null;
            }
            jumpGrade = sportResultsList.geJumpResult(tempJump);
            return String.valueOf( jumpGrade );


        }
        private String calAbsGrade(String score){
            int tempAbs = 0;

            try{
                tempAbs = Integer.parseInt(score);
            }catch (NumberFormatException e){
                return null;
            }
            absGrade =  sportResultsList.getAbsResult(tempAbs);
            return String.valueOf( absGrade );

        }
        private void popToast(String msg, int duration){
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, msg, duration);
            toast.show();
        }

        private double testInput(EditText text, String place){
            try {
                Double.parseDouble(text.getText().toString());
            }catch (NumberFormatException e){
                if(text.getText().toString().length() == 0)
                    return  -1;
                else {
                    popToast("Invalid input at "+place, Toast.LENGTH_LONG);
                    return INVALID_INPUT;
                }
            }
            return Double.parseDouble(text.getText().toString());
        }
    //Choosing Student Class.
    public void selectStudentClass(View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a class");
        builder.setItems(studentsClass, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which){
                    case 0: sClass = "ט 1";
                        break;
                    case 1: sClass = "ט 2";
                        break;
                    case 2: sClass = "ט 3";
                        break;
                    case 3: sClass = "ט 4";
                        break;
                    case 4: sClass = "ט 5";
                        break;
                    case 5: sClass = "ט 6";
                        break;
                    case 6: sClass = "ט 7";
                        break;
                    case 7: sClass = "ט 8";
                        break;
                    case 8: sClass = "י 1";
                        break;
                    case 9: sClass = "י 2";
                        break;
                    case 10: sClass = "י 3";
                        break;
                    case 11: sClass = "י 4";
                        break;
                    case 12: sClass = "י 5";
                        break;
                    case 13: sClass = "י 6";
                        break;
                    case 14: sClass = "י 7";
                        break;
                    case 15: sClass = "י 8";
                        break;
                    case 16: sClass = "י\"א 1";
                        break;
                    case 17: sClass = "י\"א 2";
                        break;
                    case 18: sClass = "י\"א 3";
                        break;
                    case 19: sClass = "י\"א 4";
                        break;
                    case 20: sClass = "י\"א 5";
                        break;
                    case 21: sClass = "י\"א 6";
                        break;
                    case 22: sClass = "י\"א 7";
                        break;
                    case 23: sClass = "י\"א 8";
                        break;
                    case 24: sClass = "י\"ב 1";
                        break;
                    case 25: sClass = "י\"ב 2";
                        break;
                    case 26: sClass = "י\"ב 3";
                        break;
                    case 27: sClass = "י\"ב 4";
                        break;
                    case 28: sClass = "י\"ב 5";
                        break;
                    case 29: sClass = "י\"ב 6";
                        break;
                    case 30: sClass = "י\"ב 7";
                        break;
                    case 31: sClass = "י\"ב 8";
                        break;
                }
            }
        });
        builder.show();
    }



}
