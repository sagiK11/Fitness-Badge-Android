package com.sagikor.android.fitracker;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class StudentActivity extends AppCompatActivity {
    String sClassString;
    String sGenderString;
    static final String TAG = "StudentActivity";
    static final int INVALID_INPUT = -2;
    static final int MISSING_INPUT = -1;
    Student currentStudent;
    SportResults sportResults = new SportResults();
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
    double aerobicScore;
    double cubesScore;
    double handsScore;
    double absScore;
    double jumpScore;
    double avg;
    double avgWithOutAerobic;
    int aerobicGrade;
    int cubesGrade;
    int handsGrade;
    int absGrade;
    int jumpGrade;
    boolean isAbsSitUp;
    boolean isStaticHands;

    protected void popSuccessWindow() {
        final String STUDENT_SAVED = getResources().getString(R.string.student_saved);
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(STUDENT_SAVED)
                .show();
    }

    void popFailWindow() {
        final String STUDENT_EXISTS = getResources().getString(R.string.student_exists);
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(STUDENT_EXISTS)
                .show();
    }

    void askForSendingSMS() {
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

    void chooseAbsTestOptionPopup() {
        final String CHOOSE_EXERCISE = getResources().getString(R.string.choose_exercise);
        final String SIT_UP = getResources().getString(R.string.situp);
        final String STATIC = getResources().getString(R.string.static_type);
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(CHOOSE_EXERCISE)
                .setConfirmText(SIT_UP)
                .setConfirmClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    isAbsSitUp = true;
                })
                .setCancelButton(STATIC, SweetAlertDialog::dismissWithAnimation)
                .show();

    }

    void chooseHandsTestOptionPopup() {
        final String CHOOSE_EXERCISE = getResources().getString(R.string.choose_exercise);
        final String SIT_UP = getResources().getString(R.string.situp);
        final String STATIC = getResources().getString(R.string.static_type);

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(CHOOSE_EXERCISE)
                .setConfirmText(STATIC)
                .setConfirmClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    isStaticHands = true;
                })
                .setCancelButton(SIT_UP, SweetAlertDialog::dismissWithAnimation)
                .show();
    }

    boolean studentHasPhoneNumber() {
        return getStudentPhoneNumber().length() > 1;
    }

    String getStudentPhoneNumber() {
        return this.sPhoneNumber.getText().toString().trim();
    }

    void sendSms() {
        String studentPhoneNumber = getStudentPhoneNumber();
        Uri uri = Uri.parse("smsto:" + studentPhoneNumber);
        Intent smsIntent = new Intent(Intent.ACTION_SEND, uri);

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
        final int MISSING = -1;
        final int DONE = 5;
        int cnt = 0;
        StringBuilder res = new StringBuilder();
        final String HELLO = getResources().getString(R.string.hello);
        final String CURRENT_GRADES = getResources().getString(R.string.current_grades);
        final String AEROBIC = getResources().getString(R.string.aero_c);
        final String HANDS = getResources().getString(R.string.hands_c);
        final String JUMP = getResources().getString(R.string.jump_c);
        final String CUBES = getResources().getString(R.string.cubes_c);
        final String ABS = getResources().getString(R.string.abs_c);
        final String GRADE = getResources().getString(R.string.grade_c);
        final String TOTAL_SCORE = getResources().getString(R.string.total_grade_c);
        final String MINUTES = getResources().getString(R.string.minutes);
        final String SECONDS = getResources().getString(R.string.seconds);
        final String CM = getResources().getString(R.string.cm);
        final String SITUP = getResources().getString(R.string.situp);
        final String AMOUNT = getResources().getString(R.string.amount);
        String sGender = genderButton.getText().toString();

        res.append(HELLO + " " + sName.getText() + " " + CURRENT_GRADES + "\n");
        if (aerobicScore != MISSING) {
            res.append(AEROBIC).append(" " + aerobicScore + " ").append(MINUTES).
                    append(" " + GRADE + " ").append(aerobicGrade + ".\n");
            cnt++;
        }
        if (jumpScore != MISSING) {
            res.append(JUMP).append(" " + jumpScore + " ").append(CM).
                    append(" " + GRADE).append(jumpGrade + ".\n");
            cnt++;
        }
        if (absScore != MISSING) {
            res.append(ABS).append(" " + absScore + " ").append(AMOUNT)
                    .append(" " + GRADE).append(" " + absGrade + ".\n");
            cnt++;
        }
        if (cubesScore != MISSING) {
            res.append(CUBES).append(" " + cubesScore + " ").append(SECONDS).
                    append(" " + GRADE).append(" " + cubesGrade + ".\n");
            cnt++;
        }
        if (handsScore != MISSING) {
            res.append(HANDS).append(" " + handsScore + " ")
                    .append(sGender.equals(getResources().getString(R.string.boy)) ?
                            AMOUNT : MINUTES).
                    append(" " + GRADE).append(" " + handsGrade + ".");
            cnt++;
        }
        return cnt == DONE ? res.append("\n" + TOTAL_SCORE).append(" " + avg + ".").toString() : res.toString();
    }

    String calGrade(String score, String sportType) {
        double tempScore = 0;
        boolean isGirl;
        try {
            if (genderButton.getText().toString().equals(getResources().getString(R.string.choose_gender)))
                isGirl = sGenderString.equals(getResources().getString(R.string.girl));
            else
                isGirl = genderButton.getText().toString().equals(getResources().getString(R.string.girl));
            tempScore = Double.parseDouble(score);
        } catch (NullPointerException e) {
            popToast(getResources().getString(R.string.choose_gender), Toast.LENGTH_SHORT, this);
            return getResources().getString(R.string.grade);
        } catch (NumberFormatException e) {
            return getResources().getString(R.string.grade);
        }
        return calGrade(sportType, isGirl, tempScore);
    }

    private String calGrade(String sportType, boolean isGirl, double tempScore) {
        switch (sportType) {
            case SportResults.AEROBIC:
                if (isGirl)
                    aerobicGrade = sportResults.getGirlsAerobicResult(tempScore);
                else
                    aerobicGrade = sportResults.getBoysAerobicResult(tempScore);
                return String.valueOf(aerobicGrade);
            case SportResults.ABS:
                if (isGirl)
                    absGrade = sportResults.getGirlsSitUpAbsResult((int) tempScore);
                else
                    absGrade = sportResults.getBoysSitUpAbsResult((int) tempScore);
                return String.valueOf(absGrade);
            case SportResults.JUMP:
                if (isGirl)
                    jumpGrade = sportResults.getGirlsJumpResult((int) tempScore);
                else
                    jumpGrade = sportResults.getBoysJumpResult((int) tempScore);
                return String.valueOf(jumpGrade);
            case SportResults.CUBES:
                if (isGirl)
                    cubesGrade = sportResults.getGirlsCubesResult(tempScore);
                else
                    cubesGrade = sportResults.getBoysCubesResult(tempScore);
                return String.valueOf(cubesGrade);
            default:
                if (isGirl)
                    handsGrade = sportResults.getGirlsStaticHandsResult(tempScore);
                else
                    handsGrade = sportResults.getBoysHandsResult(tempScore);
                return String.valueOf(handsGrade);
        }
    }

    void calAverages() {
        final int CATEGORY_NUM = 5;
        int[] gradesArray = new int[CATEGORY_NUM];
        int activeGrades = 0;
        avg = 0;

        gradesArray[0] = sAerobicScore.getText().toString().length() != 0 ? aerobicGrade : 0;
        gradesArray[1] = sAbsScore.getText().toString().length() != 0 ? absGrade : 0;
        gradesArray[2] = sJumpScore.getText().toString().length() != 0 ? jumpGrade : 0;
        gradesArray[3] = sHandsScore.getText().toString().length() != 0 ? handsGrade : 0;
        gradesArray[4] = sCubesScore.getText().toString().length() != 0 ? cubesGrade : 0;

        for (int grade : gradesArray) {
            if (grade != 0) {
                avg += grade;
                activeGrades++;
            }
        }

        if (avg != 0)
            avg = (double) avg / activeGrades;
    }

    double testInput(EditText text, String place, Context context) {
        final String INVALID_INPUT_MSG = getResources().getString(R.string.invalid_input);
        try {
            Double.parseDouble(text.getText().toString());
        } catch (NumberFormatException e) {
            if (text.getText().toString().length() == 0)
                return MISSING_INPUT;
            else {
                popToast(INVALID_INPUT_MSG + " "+ place, Toast.LENGTH_LONG, context);
                return INVALID_INPUT;
            }
        }
        return Double.parseDouble(text.getText().toString());
    }

    protected boolean isErrorsInStudentScores() {
        return aerobicScore == INVALID_INPUT || jumpScore == INVALID_INPUT ||
                absScore == INVALID_INPUT || cubesScore == INVALID_INPUT ||
                handsScore == INVALID_INPUT;
    }

    void popToast(String msg, int duration, Context context) {
        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }

    protected void addScoresTextChangedListeners() {
        sAerobicScore.addTextChangedListener((StudentTextWatcher)
                (charSequence, start, count, after) ->
                        sAerobicScoreText.setText(calGrade(charSequence.toString(), SportResults.AEROBIC)));
        sCubesScore.addTextChangedListener((StudentTextWatcher)
                (charSequence, start, count, after) ->
                        sCubesScoreText.setText(calGrade(charSequence.toString(), SportResults.CUBES)));
        sHandsScore.addTextChangedListener((StudentTextWatcher)
                (charSequence, start, count, after) ->
                        sHandsScoreText.setText(calGrade(charSequence.toString(), SportResults.HANDS)));
        sJumpScore.addTextChangedListener((StudentTextWatcher)
                (charSequence, start, count, after) ->
                        sJumpScoreText.setText(calGrade(charSequence.toString(), SportResults.JUMP)));
        sAbsScore.addTextChangedListener((StudentTextWatcher)
                (charSequence, start, count, after) ->
                        sAbsScoreText.setText(calGrade(charSequence.toString(), SportResults.ABS)));
    }

    protected void updateGradeTextFields() {
        sCubesScoreText.setText(calGrade(sCubesScore.getText().toString(), SportResults.CUBES));
        sAerobicScoreText.setText(calGrade(sAerobicScore.getText().toString(), SportResults.AEROBIC));
        sHandsScoreText.setText(calGrade(sHandsScore.getText().toString(), SportResults.HANDS));
        sJumpScoreText.setText(calGrade(sJumpScore.getText().toString(), SportResults.JUMP));
        sAbsScoreText.setText(calGrade(sAbsScore.getText().toString(), SportResults.ABS));
        calAverages();
        sTotalScoreText.setText(String.valueOf(avg));
    }

}
