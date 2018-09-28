package com.sagiKor1193.android.fitracker;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class AddStudentFromResultsActivity extends AppCompatActivity {

        private final String TAG = "AddStudentFromResults";
        private final String ZERO = "0";
        SportResultsList sportResultsList = new SportResultsList();
        private final int CATEGORY_NUM = 5;
        private final int INVALID_INPUT = -2;
        private final int MISSING = -1;
        Student currentStudent;
        TextView sName;
        TextView sClass;
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
        EditText sPhoneNumber;
        //Scores
        private double aeroScore;
        private double cubesScore;
        private double handsScore;
        private double absScore;
        private double jumpScore;
        //Grades
        private int aeroGrade;
        private int cubesGrade;
        private int handsGrade;
        private int absGrade;
        private int jumpGrade;
        private double avg;
        private double avgWithOutAero;

        protected void onCreate(Bundle savedInstanceState){

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_enter_data_v2);
            linkObjects();
            currentStudent = (Student) getIntent().getParcelableExtra("student");
            Log.d(TAG,"student: " + currentStudent);
            setFields();

        }

    private void setFields() {
        sName.setText( currentStudent.getSName() );
        sClass.setText( currentStudent.getSClass() );

        if( currentStudent.getAeroScore() != MISSING)
            sAeroScore.setText( String.valueOf( currentStudent.getAeroScore()) );

        if( currentStudent.getCubesScore() != MISSING)
            sCubesScore.setText( String.valueOf( currentStudent.getCubesScore()) );

        if( currentStudent.getHandsScore() != MISSING)
            sHandsScore.setText( String.valueOf( currentStudent.getHandsScore()) );

        if( currentStudent.getAbsScore() != MISSING)
            sAbsScore.setText( String.valueOf( currentStudent.getAbsScore()) );

        if( currentStudent.getJumpScore() != MISSING)
            sJumpScore.setText( String.valueOf( currentStudent.getJumpScore()) );

        if(currentStudent.getPhoneNumber() != null)
            sPhoneNumber.setText(currentStudent.getPhoneNumber());
    }

    private void linkObjects() {
        sName = findViewById(R.id.enter_data_vs_student_to_display_id);
        sClass = findViewById(R.id.enter_data_v2_student_class_to_disply_id);
        sPhoneNumber = findViewById(R.id.phone_number_to_enter_id);

        sAeroScore =  findViewById(R.id.update_student_aerobic_id);
        sCubesScore = findViewById(R.id.update_student_cubes_id);
        sHandsScore = findViewById(R.id.update_student_hands_id);
        sJumpScore = findViewById(R.id.update_student_jump_id);
        sAbsScore = findViewById(R.id.update_student_abs_id);

        sAeroScoreText = findViewById(R.id.update_student_aerobic_text);
        sCubesScoreText = findViewById(R.id.update_student_cubes_text);
        sHandsScoreText = findViewById(R.id.update_student_hands_text);
        sJumpScoreText = findViewById(R.id.update_student_jump_text);
        sAbsScoreText = findViewById(R.id.update_student_abs_text);
        sTotalScoreText = findViewById(R.id.update_student_total_score);

    }



    public void enterButtonClicked(View view) {

        //Getting Input + Testing for validation
        if (! testAllInputs())
            return;
        //Updating relevant text with grades.
        updateTextFields();

        MainActivity.getDbHandler().updateStudent(
                sName.getText().toString(),
                sClass.getText().toString(),
                aeroScore, aeroGrade, cubesScore, cubesGrade,
                absScore, absGrade, jumpScore, jumpGrade, handsScore,
                handsGrade, avg, sPhoneNumber.getText().toString(), avgWithOutAero
        );

        popToast("Student scores entered!", Toast.LENGTH_SHORT);
    }

    private void updateTextFields() {

        sCubesScoreText.setText( this.calCubesGrade(sCubesScore.getText().toString()) );
        sAeroScoreText.setText( this.calAeroGrade(sAeroScore.getText().toString()) );
        sHandsScoreText.setText( this.calHandGrade(sHandsScore.getText().toString()) );
        sJumpScoreText.setText( this.calJumpGrade(sJumpScore.getText().toString()) );
        sAbsScoreText.setText( this.calAbsGrade(sAbsScore.getText().toString()) );
        avg = (double) (aeroGrade + absGrade + jumpGrade + handsGrade + cubesGrade) / CATEGORY_NUM;
        avgWithOutAero = (double) ( absGrade + jumpGrade + handsGrade + cubesGrade) / (CATEGORY_NUM - 1);

        //Updating total score to the screen
        sTotalScoreText.setText(String.valueOf( avg ));
    }

    private boolean testAllInputs() {
        aeroScore = testInput(sAeroScore, "aerobic score");
        jumpScore = testInput(sJumpScore, "jump score");
        absScore = testInput(sAbsScore, "abs score");
        cubesScore = testInput(sCubesScore, "cubes score");
        handsScore = testInput(sHandsScore, "hands Score");
        if(aeroScore == INVALID_INPUT || jumpScore == INVALID_INPUT || absScore == INVALID_INPUT
                || cubesScore == INVALID_INPUT || handsScore == INVALID_INPUT) {

            popToast("Invalid input!", Toast.LENGTH_SHORT);
            return false;
        }
        return  true;
    }

    private String calCubesGrade(String score){
            if( score.length() == 0 )
                return ZERO;
            cubesGrade = sportResultsList.getCubesResult( Double.parseDouble(score) );
            return String.valueOf( cubesGrade );

        }
        private String calAeroGrade(String score){
            if( score.length() == 0 )
                return ZERO;
            aeroGrade = sportResultsList.getAeroResult( Double.parseDouble(score) ) ;
            return String.valueOf( aeroGrade );

        }
        private String calHandGrade(String score){
            if( score.length() == 0 )
                return ZERO;
            handsGrade =  sportResultsList.getHandsResult( Double.parseDouble(score));
            return String.valueOf( handsGrade );

        }
        private String calJumpGrade(String score){
            if( score.length() == 0 )
                return ZERO;
            jumpGrade = sportResultsList.geJumpResult( (int) Double.parseDouble(score));
            return String.valueOf( jumpGrade );
        }
        private String calAbsGrade(String score){
            if( score.length() == 0 )
                return ZERO;
            absGrade =  sportResultsList.getAbsResult( (int) Double.parseDouble(score) );
            return String.valueOf( absGrade );

        }
        private void popToast(String msg, int duration){
            Toast toast = Toast.makeText(getApplicationContext(), msg, duration);
            toast.show();
        }

        private double testInput(EditText text, String place){
            try {
                Double.parseDouble(text.getText().toString());
            }catch (NumberFormatException e){

                if(text.getText().toString().length() == 0)
                    return  -1;
                else {
                    popToast("Invalid input at "+ place, Toast.LENGTH_LONG);
                    return INVALID_INPUT;
                }
            }
            return Double.parseDouble(text.getText().toString());
        }

    }


