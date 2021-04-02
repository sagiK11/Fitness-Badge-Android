package com.sagikor.android.fitracker.ui.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sagikor.android.fitracker.R;
import com.sagikor.android.fitracker.ui.contracts.StudentActivityContract;

import cn.pedant.SweetAlert.SweetAlertDialog;

public abstract class StudentActivity extends AppCompatActivity implements StudentActivityContract.View {
    protected StudentActivityContract.Presenter presenter;
    private static final String TAG = "StudentActivity";

    EditText sName;
    EditText sPhoneNumber;
    EditText sAerobicScore;
    EditText sCubesScore;
    EditText sHandsScore;
    EditText sAbsScore;
    EditText sJumpScore;
    TextView sAerobicScoreText;
    TextView sCubesScoreText;
    TextView sHandsScoreText;
    TextView sAbsScoreText;
    TextView sJumpScoreText;
    TextView sTotalScoreText;
    TextView handsTypeText;
    Button chooseClassButton;
    Button genderButton;
    Button saveStudentButton;
    Button sClassButton;


    @Override
    public String getStudentName() {
        return cleanText(sName);
    }

    @Override
    public String getStudentClass() {
        return cleanText(chooseClassButton);
    }

    @Override
    public String getStudentPhoneNo() {
        return cleanText(sPhoneNumber);
    }

    @Override
    public String getStudentGender() {
        return cleanText(genderButton);
    }

    @Override
    public String getAerobicScore() {
        return cleanText(sAerobicScore);
    }

    @Override
    public String getCubesScore() {
        return cleanText(sCubesScore);
    }

    @Override
    public String getAbsSore() {
        return cleanText(sAbsScore);
    }

    @Override
    public String getHandsScore() {
        return cleanText(sHandsScore);
    }

    @Override
    public String getJumpScore() {
        return cleanText(sJumpScore);
    }

    @Override
    public String getAbsGrade() {
        return cleanText(sAbsScoreText);
    }

    @Override
    public String getAerobicGrade() {
        return cleanText(sAerobicScoreText);
    }

    @Override
    public String getJumpGrade() {
        return cleanText(sJumpScoreText);
    }

    @Override
    public String getHandsGrade() {
        return cleanText(sHandsScoreText);
    }

    @Override
    public String getCubesGrade() {
        return cleanText(sCubesScoreText);
    }

    @Override
    public void popMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private String cleanText(TextView textView) {
        return textView.getText().toString().trim();
    }


    protected void popSuccessWindow() {
        final String STUDENT_SAVED = getResources().getString(R.string.student_saved);
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(STUDENT_SAVED)
                .show();
    }

    @Override
    public void popFailWindow() {
        final String STUDENT_EXISTS = getResources().getString(R.string.student_exists);
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(STUDENT_EXISTS)
                .show();
    }

    @Override
    public void askForSendingSMS() {
        final String SEND_QUESTION = getResources().getString(R.string.send_to_student_question);
        final String YES = getResources().getString(R.string.yes);
        final String NO = getResources().getString(R.string.no);

        if (studentHasPhoneNumber()) {
            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText(SEND_QUESTION)
                    .setConfirmText(YES)
                    .setConfirmClickListener(sDialog -> {
                        sDialog.dismissWithAnimation();
                        sendSms();
                        popSuccessWindow();
                    })
                    .setCancelButton(NO, sDialog -> {
                        popSuccessWindow();
                        sDialog.dismissWithAnimation();
                    })
                    .show();
        } else {
            popSuccessWindow();
        }

    }


    boolean studentHasPhoneNumber() {
        return getStudentPhoneNumber().length() > 1;
    }

    String getStudentPhoneNumber() {
        return this.sPhoneNumber.getText().toString().trim();
    }

    void sendSms() {
        String studentPhoneNumber = getStudentPhoneNumber();
        Intent smsIntent = new Intent(Intent.ACTION_SEND);

        smsIntent.setType("text/plain");
        smsIntent.putExtra("address", studentPhoneNumber);

        String txt = getStudentGrades();
        smsIntent.putExtra(Intent.EXTRA_TEXT, txt);

        try {
            startActivity(smsIntent);
        } catch (ActivityNotFoundException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    String getStudentGrades() {
        //TODO yeah.. thats a bummer
//        final int MISSING = -1;
//        final int DONE = 5;
//        int cnt = 0;
//        StringBuilder res = new StringBuilder();
//        final String HELLO = getResources().getString(R.string.hello);
//        final String CURRENT_GRADES = getResources().getString(R.string.current_grades);
//        final String AEROBIC = getResources().getString(R.string.aero_c);
//        final String HANDS = getResources().getString(R.string.hands_c);
//        final String JUMP = getResources().getString(R.string.jump_c);
//        final String CUBES = getResources().getString(R.string.cubes_c);
//        final String ABS = getResources().getString(R.string.abs_c);
//        final String GRADE = getResources().getString(R.string.grade_c);
//        final String TOTAL_SCORE = getResources().getString(R.string.total_grade_c);
//        final String MINUTES = getResources().getString(R.string.minutes);
//        final String SECONDS = getResources().getString(R.string.seconds);
//        final String CM = getResources().getString(R.string.cm);
//        final String AMOUNT = getResources().getString(R.string.amount);
//        String sGender = genderButton.getText().toString();
//
//        res.append(HELLO + " " + sName.getText() + " " + CURRENT_GRADES + "\n");
//        if (aerobicScore != MISSING) {
//            res.append(AEROBIC).append(" " + aerobicScore + " ").append(MINUTES).
//                    append(" " + GRADE + " ").append(aerobicGrade + ".\n");
//            cnt++;
//        }
//        if (jumpScore != MISSING) {
//            res.append(JUMP).append(" " + jumpScore + " ").append(CM).
//                    append(" " + GRADE).append(jumpGrade + ".\n");
//            cnt++;
//        }
//        if (absScore != MISSING) {
//            res.append(ABS).append(" " + absScore + " ").append(AMOUNT)
//                    .append(" " + GRADE).append(" " + absGrade + ".\n");
//            cnt++;
//        }
//        if (cubesScore != MISSING) {
//            res.append(CUBES).append(" " + cubesScore + " ").append(SECONDS).
//                    append(" " + GRADE).append(" " + cubesGrade + ".\n");
//            cnt++;
//        }
//        if (handsScore != MISSING) {
//            res.append(HANDS).append(" " + handsScore + " ")
//                    .append(sGender.equals(getResources().getString(R.string.boy)) ?
//                            AMOUNT : MINUTES).
//                    append(" " + GRADE).append(" " + handsGrade + ".");
//            cnt++;
//        }
//        return cnt == DONE ? res.append("\n" + TOTAL_SCORE).append(" " + avg + ".").toString() : res.toString();
        return "Find me";
    }

    @Override
    public void updateTotalScore(int avg) {
        sTotalScoreText.setText(String.valueOf(avg));
    }

}
