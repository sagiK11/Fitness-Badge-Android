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
import com.sagikor.android.fitracker.data.model.Student;
import com.sagikor.android.fitracker.ui.contracts.StudentActivityContract;
import com.sagikor.android.fitracker.ui.presenter.StudentActivityPresenter;
import com.sagikor.android.fitracker.utils.StudentTextWatcher;
import com.sagikor.android.fitracker.utils.datastructure.SportResults;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.sagikor.android.fitracker.utils.Utility.MISSING_INPUT;

public class StudentActivity extends AppCompatActivity implements StudentActivityContract.View {
    private static final String TAG = "StudentActivity";
    private StudentActivityContract.Presenter presenter;
    EditText etStudentName;
    EditText etStudentPhoneNo;
    EditText etAerobicScore;
    EditText etCubesScore;
    EditText etHandsScore;
    EditText etAbsScore;
    EditText etJumpScore;
    TextView tvAerobicScore;
    TextView tvCubesScore;
    TextView tvHandsScore;
    TextView tvAbsScore;
    TextView tvJumpScore;
    TextView tvTotalScore;
    TextView tvHandsType;
    Button btnChooseClass;
    Button btnChooseGender;
    Button btnSaveStudent;


    @Override
    public String getStudentName() {
        return cleanText(etStudentName);
    }

    @Override
    public String getStudentClass() {
        return cleanText(btnChooseClass);
    }

    @Override
    public String getStudentPhoneNo() {
        return cleanText(etStudentPhoneNo);
    }

    @Override
    public String getStudentGender() {
        return cleanText(btnChooseGender);
    }

    @Override
    public String getAerobicScore() {
        return cleanText(etAerobicScore);
    }

    @Override
    public String getCubesScore() {
        return cleanText(etCubesScore);
    }

    @Override
    public String getAbsSore() {
        return cleanText(etAbsScore);
    }

    @Override
    public String getHandsScore() {
        return cleanText(etHandsScore);
    }

    @Override
    public String getJumpScore() {
        return cleanText(etJumpScore);
    }

    @Override
    public String getAbsGrade() {
        return cleanText(tvAbsScore);
    }

    @Override
    public String getAerobicGrade() {
        return cleanText(tvAerobicScore);
    }

    @Override
    public String getJumpGrade() {
        return cleanText(tvJumpScore);
    }

    @Override
    public String getHandsGrade() {
        return cleanText(tvHandsScore);
    }

    @Override
    public String getCubesGrade() {
        return cleanText(tvCubesScore);
    }

    @Override
    public void popMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getGradeStringResource() {
        return getResources().getString(R.string.grade);
    }

    @Override
    public String getGenderStringResource() {
        return getResources().getString(R.string.gender);
    }

    private String cleanText(TextView textView) {
        return textView.getText().toString().trim();
    }

    @Override
    public void popSuccessWindow() {
        final String STUDENT_SAVED = getResources().getString(R.string.student_saved);
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(STUDENT_SAVED)
                .show();
    }

    @Override
    public void popFailWindow(String error) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(error)
                .show();
    }

    @Override
    public void askForSendingSMS(Student student) {
        final String SEND_QUESTION = getResources().getString(R.string.send_to_student_question);
        final String YES = getResources().getString(R.string.yes);
        final String NO = getResources().getString(R.string.no);

        if (isStudentHasStudentPhoneNo()) {
            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText(SEND_QUESTION)
                    .setConfirmText(YES)
                    .setConfirmClickListener(sDialog -> {
                        sDialog.dismissWithAnimation();
                        sendSms(student);
                    })
                    .setCancelButton(NO, SweetAlertDialog::dismissWithAnimation)
                    .show();
        }
    }

    boolean isStudentHasStudentPhoneNo() {
        return getStudentPhoneNumber().length() > 1;
    }

    String getStudentPhoneNumber() {
        return this.etStudentPhoneNo.getText().toString().trim();
    }

    void sendSms(Student student) {
        String studentPhoneNumber = student.getPhoneNumber();
        Intent smsIntent = new Intent(Intent.ACTION_SEND);

        smsIntent.setType("text/plain");
        smsIntent.putExtra("address", studentPhoneNumber);

        String txt = getStudentMessage(student);
        smsIntent.putExtra(Intent.EXTRA_TEXT, txt);

        try {
            startActivity(smsIntent);
        } catch (ActivityNotFoundException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    String getStudentMessage(Student student) {
        StringBuilder message = new StringBuilder();
        int finishedNo = 0;

        appendGreetings(message);
        finishedNo += appendAerobicText(message, student);
        finishedNo += appendAbsText(message, student);
        finishedNo += appendJumpText(message, student);
        finishedNo += appendCubesText(message, student);
        finishedNo += appendHandsText(message, student);
        final int FINISHED_ALL_TESTS = 5;
        return finishedNo == FINISHED_ALL_TESTS ? appendTotalScore(message, student)
                : message.toString();
    }

    private void appendGreetings(StringBuilder res) {
        final String HELLO = getResources().getString(R.string.hello);
        final String CURRENT_GRADES = getResources().getString(R.string.current_grades);
        res.append(HELLO).append(" ").append(etStudentName.getText()).
                append(" ").append(CURRENT_GRADES).append("\n");
    }

    private String appendTotalScore(StringBuilder res, Student student) {
        final String TOTAL_SCORE = getResources().getString(R.string.total_grade_c);
        return res.append("\n").append(TOTAL_SCORE).append(" ")
                .append(student.getTotalScore()).append(".").toString();
    }

    private int appendAerobicText(StringBuilder res, Student student) {
        final String AEROBIC = getResources().getString(R.string.aero_c);
        final String GRADE = getResources().getString(R.string.grade_c);
        final String MINUTES = getResources().getString(R.string.minutes);

        if (student.getAerobicScore() != MISSING_INPUT) {
            res.append(AEROBIC).append(" ").append(student.getAerobicScore())
                    .append(" ").append(MINUTES).append(" ").append(GRADE).append(" ")
                    .append(student.getAerobicResult()).append(".\n");
            return 1;
        }
        return 0;
    }

    private int appendJumpText(StringBuilder res, Student student) {
        final String JUMP = getResources().getString(R.string.jump_c);
        final String CM = getResources().getString(R.string.cm);
        final String GRADE = getResources().getString(R.string.grade_c);

        if (student.getJumpScore() != MISSING_INPUT) {
            res.append(JUMP).append(" ").append(student.getJumpScore()).append(" ")
                    .append(CM).append(" ").append(GRADE)
                    .append(student.getJumpResult()).append(".\n");
            return 1;
        }
        return 0;
    }

    private int appendAbsText(StringBuilder res, Student student) {
        final String ABS = getResources().getString(R.string.abs_c);
        final String AMOUNT = getResources().getString(R.string.amount);
        final String GRADE = getResources().getString(R.string.grade_c);

        if (student.getAbsScore() != MISSING_INPUT) {
            res.append(ABS).append(" ").append(student.getAbsScore()).append(" ")
                    .append(AMOUNT).append(" ").append(GRADE)
                    .append(" ").append(student.getAbsResult()).append(".\n");
            return 1;
        }
        return 0;
    }

    private int appendCubesText(StringBuilder res, Student student) {
        final String SECONDS = getResources().getString(R.string.seconds);
        final String GRADE = getResources().getString(R.string.grade_c);
        final String CUBES = getResources().getString(R.string.cubes_c);

        if (student.getCubesScore() != MISSING_INPUT) {
            res.append(CUBES).append(" ").append(student.getCubesScore()).append(" ")
                    .append(SECONDS).append(" ").append(GRADE).append(" ")
                    .append(student.getCubesResult()).append(".\n");
            return 1;
        }
        return 0;
    }

    private int appendHandsText(StringBuilder res, Student student) {
        final String HANDS = getResources().getString(R.string.hands_c);
        final String MINUTES = getResources().getString(R.string.minutes);
        final String AMOUNT = getResources().getString(R.string.amount);
        final String GRADE = getResources().getString(R.string.grade_c);
        final String BOY = getResources().getString(R.string.boy);

        if (student.getHandsScore() != MISSING_INPUT) {
            res.append(HANDS).append(" ").append(student.getHandsScore()).append(" ")
                    .append(student.getGender().equals(BOY) ? AMOUNT : MINUTES)
                    .append(" ").append(GRADE).append(" ")
                    .append(student.getHandsResult()).append(".");
            return 1;
        }
        return 0;
    }

    @Override
    public void updateTotalScore(double avg) {
        tvTotalScore.setText(String.valueOf(avg));
    }

    protected void addScoresTextChangedListeners() {
        boolean isFemale = btnChooseGender.getText().toString().equals(getResources().getString(R.string.girl));
        addScoreListener(etAerobicScore, tvAerobicScore, SportResults.AEROBIC, isFemale);
        addScoreListener(etCubesScore, tvCubesScore, SportResults.CUBES, isFemale);
        addScoreListener(etHandsScore, tvHandsScore, SportResults.HANDS, isFemale);
        addScoreListener(etJumpScore, tvJumpScore, SportResults.JUMP, isFemale);
        addScoreListener(etAbsScore, tvAbsScore, SportResults.ABS, isFemale);
    }

    private void addScoreListener(EditText editText, TextView tvGrade, String type, boolean isFemale) {
        editText.addTextChangedListener((StudentTextWatcher)
                (charSequence, start, count, after) -> {
                    if (presenter.isGenderSelected()) {
                        String input = charSequence.toString().trim();
                        if (presenter.isValidScore(input)) {
                            String grade = presenter.calculateGrade(input, type, isFemale);
                            tvGrade.setText(grade);
                        } else {
                            tvGrade.setText(getResources().getString(R.string.grade));
                        }
                    } else {
                        popMessage(getResources().getString(R.string.gender_error));
                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter == null)
            presenter = new StudentActivityPresenter();
        presenter.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbind();
    }

    protected void bindViews() {
        bindUserInputViews();
        bindTextToDisplayViews();
        addScoresTextChangedListeners();
    }

    protected void bindUserInputViews() {
        etStudentName = findViewById(R.id.student_name_to_enter_id);
        etStudentPhoneNo = findViewById(R.id.phone_number_to_enter_id);
        btnChooseClass = findViewById(R.id.student_class_id);
        btnChooseGender = findViewById(R.id.gender_button);
        btnSaveStudent = findViewById(R.id.button_add_student_enter_data);
        etAerobicScore = findViewById(R.id.update_student_aerobic_id);
        etCubesScore = findViewById(R.id.update_student_cubes_id);
        etHandsScore = findViewById(R.id.update_student_hands_id);
        etJumpScore = findViewById(R.id.update_student_jump_id);
        etAbsScore = findViewById(R.id.update_student_abs_id);
        tvHandsType = findViewById(R.id.hands_minutes_text_view);
    }

    protected void bindTextToDisplayViews() {
        tvAerobicScore = findViewById(R.id.update_student_aerobic_text);
        tvCubesScore = findViewById(R.id.student_cubes_text);
        tvHandsScore = findViewById(R.id.student_hands_text);
        tvJumpScore = findViewById(R.id.student_jump_text);
        tvAbsScore = findViewById(R.id.student_abs_text);
        tvTotalScore = findViewById(R.id.student_total_score);
    }

}
