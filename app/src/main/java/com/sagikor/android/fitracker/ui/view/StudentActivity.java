package com.sagikor.android.fitracker.ui.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.sagikor.android.fitracker.R;
import com.sagikor.android.fitracker.data.model.Student;
import com.sagikor.android.fitracker.ui.contracts.StudentActivityContract;
import com.sagikor.android.fitracker.ui.presenter.StudentActivityPresenter;
import com.sagikor.android.fitracker.utils.AppExceptions;
import com.sagikor.android.fitracker.utils.StudentTextWatcher;
import com.sagikor.android.fitracker.utils.Utility;
import com.sagikor.android.fitracker.utils.datastructure.SportResults;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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
    SwitchMaterial swAerobicOption;
    SwitchMaterial swPushUpOption;
    TextView tvAerobicGrade;
    TextView tvCubesGrade;
    TextView tvHandsGrade;
    TextView tvAbsGrade;
    TextView tvJumpGrade;
    TextView tvFinalGrade;
    TextView tvHandsType;
    TextView tvAbsType;
    Button btnChooseClass;
    Button btnChooseGender;
    Button btnSaveStudent;
    TextView tvAbsGuide;
    TextView tvHandsGuide;


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
        return cleanText(tvAbsGrade);
    }

    @Override
    public String getAerobicGrade() {
        return cleanText(tvAerobicGrade);
    }

    @Override
    public String getJumpGrade() {
        return cleanText(tvJumpGrade);
    }

    @Override
    public String getHandsGrade() {
        return cleanText(tvHandsGrade);
    }

    @Override
    public String getCubesGrade() {
        return cleanText(tvCubesGrade);
    }

    @Override
    public void popMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getGradeStringResource() {
        return getString(R.string.grade);
    }

    @Override
    public String getGenderStringResource() {
        return getString(R.string.gender);
    }

    @Override
    public boolean isAerobicWalkingChecked() {
        return swAerobicOption.isChecked();
    }

    @Override
    public boolean isPushUpHalfChecked() {
        return swPushUpOption.isChecked();
    }

    @Override
    public InputStream getFemaleGradesFile() {
        return getResources().openRawResource(R.raw.female_grades);
    }

    @Override
    public InputStream getMaleGradesFile() {
        return getResources().openRawResource(R.raw.male_grades);
    }

    private String cleanText(TextView textView) {
        return textView.getText().toString().trim();
    }

    @Override
    public void popSuccessWindow() {
        View contextView = findViewById(R.id.add_student_root);
        Snackbar.make(contextView, R.string.student_saved, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(getColor(R.color.colorPrimary))
                .show();
//
    }

    @Override
    public void popFailWindow(String error) {
        View contextView = findViewById(R.id.add_student_root);
        error = getErrorInUserLanguage(error);
        Snackbar.make(contextView, error, Snackbar.LENGTH_LONG)
                .setBackgroundTint(getColor(R.color.black))
                .setAction(getString(R.string.try_again), v -> {
                    //dismissing
                })
                .show();
    }

    private String getErrorInUserLanguage(String error) {
        switch (error) {
            case AppExceptions.STUDENT_EXISTS:
                return getString(R.string.student_exists);
            case AppExceptions.SELECT_NAME:
                return getString(R.string.please_enter_student_name);
            case AppExceptions.INVALID_PHONE:
                return getString(R.string.invalid_phone_number);
            case AppExceptions.SELECT_CLASS:
                return getString(R.string.please_enter_student_class);
            case AppExceptions.SELECT_GENDER:
                return getString(R.string.gender_error);
            case AppExceptions.MISSING_CLASSES:
                return getString(R.string.add_classes_in_settings);
            case AppExceptions.INVALID_NAME:
                return getString(R.string.invalid_name);
            default:
                return error;
        }
    }

    @Override
    public void askForSendingSMS(Student student) {
        final String SEND_QUESTION = getString(R.string.send_to_student_question);
        final String YES = getString(R.string.yes);
        final String NO = getString(R.string.no);

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
        final String HELLO = getString(R.string.hello);
        final String CURRENT_GRADES = getString(R.string.current_grades);
        res.append(HELLO).append(" ").append(etStudentName.getText()).
                append(" ").append(CURRENT_GRADES).append("\n");
    }

    private String appendTotalScore(StringBuilder res, Student student) {
        final String TOTAL_SCORE = getString(R.string.total_grade_c);
        return res.append("\n").append(TOTAL_SCORE).append(" ")
                .append(student.getTotalScore()).append(".").toString();
    }

    private int appendAerobicText(StringBuilder res, Student student) {
        final String AEROBIC = getString(R.string.aero_c);
        final String GRADE = getString(R.string.grade_c);
        final String MINUTES = getString(R.string.minutes);

        if (student.getAerobicScore() != MISSING_INPUT) {
            res.append(AEROBIC).append(" ").append(student.getAerobicScore())
                    .append(" ").append(MINUTES).append(" ").append(GRADE).append(" ")
                    .append(student.getAerobicResult()).append(".\n");
            return 1;
        }
        return 0;
    }

    private int appendJumpText(StringBuilder res, Student student) {
        final String JUMP = getString(R.string.jump_c);
        final String CM = getString(R.string.cm);
        final String GRADE = getString(R.string.grade_c);

        if (student.getJumpScore() != MISSING_INPUT) {
            res.append(JUMP).append(" ").append(student.getJumpScore()).append(" ")
                    .append(CM).append(" ").append(GRADE)
                    .append(student.getJumpResult()).append(".\n");
            return 1;
        }
        return 0;
    }

    private int appendAbsText(StringBuilder res, Student student) {
        final String ABS = getString(R.string.abs_c);
        final String AMOUNT = getString(R.string.amount);
        final String GRADE = getString(R.string.grade_c);

        if (student.getAbsScore() != MISSING_INPUT) {
            res.append(ABS).append(" ").append(student.getAbsScore()).append(" ")
                    .append(AMOUNT).append(" ").append(GRADE)
                    .append(" ").append(student.getAbsResult()).append(".\n");
            return 1;
        }
        return 0;
    }

    private int appendCubesText(StringBuilder res, Student student) {
        final String SECONDS = getString(R.string.seconds);
        final String GRADE = getString(R.string.grade_c);
        final String CUBES = getString(R.string.cubes_c);

        if (student.getCubesScore() != MISSING_INPUT) {
            res.append(CUBES).append(" ").append(student.getCubesScore()).append(" ")
                    .append(SECONDS).append(" ").append(GRADE).append(" ")
                    .append(student.getCubesResult()).append(".\n");
            return 1;
        }
        return 0;
    }

    private int appendHandsText(StringBuilder res, Student student) {
        final String HANDS = getString(R.string.hands_c);
        final String MINUTES = getString(R.string.minutes);
        final String AMOUNT = getString(R.string.amount);
        final String GRADE = getString(R.string.grade_c);
        final String MALE = getString(R.string.male);

        if (student.getHandsScore() != MISSING_INPUT) {
            res.append(HANDS).append(" ").append(student.getHandsScore()).append(" ")
                    .append(student.getGender().equals(MALE) ? AMOUNT : MINUTES)
                    .append(" ").append(GRADE).append(" ")
                    .append(student.getHandsResult()).append(".");
            return 1;
        }
        return 0;
    }

    @Override
    public void updateTotalScore(double avg) {
        tvFinalGrade.setText(String.valueOf(avg));
    }

    protected void addScoresTextChangedListeners() {
        addScoreListener(etAerobicScore, tvAerobicGrade, SportResults.AEROBIC);
        addScoreListener(etCubesScore, tvCubesGrade, SportResults.CUBES);
        addScoreListener(etHandsScore, tvHandsGrade, SportResults.HANDS);
        addScoreListener(etJumpScore, tvJumpGrade, SportResults.JUMP);
        addScoreListener(etAbsScore, tvAbsGrade, SportResults.ABS);
    }

    private void addScoreListener(EditText editText, TextView tvGrade, String type) {
        editText.addTextChangedListener((StudentTextWatcher)
                (charSequence, start, count, after) -> {
                    if (presenter.isGenderSelected()) {
                        String input = charSequence.toString().trim();
                        if (presenter.isValidScore(input)) {
                            boolean isFemale = btnChooseGender.getText().toString().equals(getString(R.string.female));
                            boolean isWalking = swAerobicOption.isChecked();
                            boolean isPushUpHalf = swPushUpOption.isChecked();
                            Map<String, Boolean> map = new HashMap<>();
                            map.put(SportResults.IS_FEMALE, isFemale);
                            map.put(SportResults.IS_WALKING, isWalking);
                            map.put(SportResults.IS_PUSH_UP_HALF, isPushUpHalf);
                            String grade = presenter.calculateGrade(input, type, map);

                            tvGrade.setText(grade);
                        } else {
                            tvGrade.setText(getString(R.string.grade));
                        }
                    } else {
                        popFailWindow(getString(R.string.gender_error));
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
        bindGuidingTextViews();
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
        tvAbsType = findViewById(R.id.abs_type_text_view);
        swAerobicOption = findViewById(R.id.switch_aerobic_walking);
        swAerobicOption.setOnClickListener(e -> resetAerobicInput());
        swPushUpOption = findViewById(R.id.switch_push_up);
        swPushUpOption.setOnClickListener(e -> resetPushUpInput());
    }

    private void resetPushUpInput() {
        etHandsScore.setText("");
        tvHandsGrade.setText(getString(R.string.grade));
    }

    private void resetAerobicInput() {
        etAerobicScore.setText("");
        tvAerobicGrade.setText(getString(R.string.grade));
    }

    protected void bindTextToDisplayViews() {
        tvAerobicGrade = findViewById(R.id.update_student_aerobic_text);
        tvCubesGrade = findViewById(R.id.student_cubes_text);
        tvHandsGrade = findViewById(R.id.student_hands_text);
        tvJumpGrade = findViewById(R.id.student_jump_text);
        tvAbsGrade = findViewById(R.id.student_abs_text);
        tvFinalGrade = findViewById(R.id.student_total_score);
    }

    protected void bindGuidingTextViews() {
        tvAbsGuide = findViewById(R.id.absTextView);
        tvHandsGuide = findViewById(R.id.handsTextView);
    }

    protected void changeLayoutToFemale() {
        tvHandsType.setText(getString(R.string.amount));
        tvAbsType.setText(getString(R.string.minutes));
        swAerobicOption.setVisibility(View.VISIBLE);
        swPushUpOption.setVisibility(View.VISIBLE);
        tvAbsGuide.setText(getString(R.string.plank));
        tvHandsGuide.setText(getString(R.string.push_up));
        btnChooseGender.setText(getString(R.string.female));
    }

    protected void changeLayoutToMale() {
        tvHandsType.setText(getString(R.string.amount));
        tvAbsType.setText(getString(R.string.amount));
        swAerobicOption.setVisibility(View.GONE);
        swPushUpOption.setVisibility(View.GONE);
        tvAbsGuide.setText(getString(R.string.abs));
        tvHandsGuide.setText(getString(R.string.hands));
        btnChooseGender.setText(getString(R.string.male));

    }

}
