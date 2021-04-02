package com.sagikor.android.fitracker.ui.view;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.sagikor.android.fitracker.R;
import com.sagikor.android.fitracker.ui.contracts.UpdateStudentActivityContract;
import com.sagikor.android.fitracker.ui.presenter.UpdateStudentActivityPresenter;
import com.sagikor.android.fitracker.utils.datastructure.SportResults;
import com.sagikor.android.fitracker.utils.StudentTextWatcher;


public class UpdateStudentActivity extends StudentActivity implements UpdateStudentActivityContract.View {

    private UpdateStudentActivityContract.Presenter presenter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        bindViews();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(presenter == null)
            presenter = new UpdateStudentActivityPresenter();
        presenter.bind(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        presenter.unbind();
    }

    private void bindViews() {
        bindUserInputViews();
        bindTextToDisplayViews();
        addScoresTextChangedListeners();
    }

    private void bindUserInputViews() {
        sName = findViewById(R.id.student_name_to_enter_id);
        sClassButton = findViewById(R.id.student_class_id);
        genderButton = findViewById(R.id.gender_button);
        sPhoneNumber = findViewById(R.id.phone_number_to_enter_id);
        sAerobicScore = findViewById(R.id.update_student_aerobic_id);
        sCubesScore = findViewById(R.id.update_student_cubes_id);
        sHandsScore = findViewById(R.id.update_student_hands_id);
        sJumpScore = findViewById(R.id.update_student_jump_id);
        sAbsScore = findViewById(R.id.update_student_abs_id);
        handsTypeText = findViewById(R.id.hands_minutes_text_view);
        sName.setEnabled(false);
        sClassButton.setEnabled(false);
        genderButton.setEnabled(false);
    }

    private void bindTextToDisplayViews() {
        sAerobicScoreText = findViewById(R.id.update_student_aerobic_text);
        sCubesScoreText = findViewById(R.id.student_cubes_text);
        sHandsScoreText = findViewById(R.id.student_hands_text);
        sJumpScoreText = findViewById(R.id.student_jump_text);
        sAbsScoreText = findViewById(R.id.student_abs_text);
        sTotalScoreText = findViewById(R.id.student_total_score);
        saveStudentButton = findViewById(R.id.button_add_student_enter_data);
        saveStudentButton.setOnClickListener(e -> presenter.onSaveButtonClick());
    }

    protected void addScoresTextChangedListeners() {
        String sGenderString = genderButton.getText().toString();
        addScoreListener(sAerobicScore,sAerobicScoreText, SportResults.AEROBIC, sGenderString);
        addScoreListener(sCubesScore,sCubesScoreText, SportResults.CUBES, sGenderString);
        addScoreListener(sHandsScore,sHandsScoreText, SportResults.HANDS, sGenderString);
        addScoreListener(sJumpScore,sJumpScoreText, SportResults.JUMP, sGenderString);
        addScoreListener(sAbsScore,sAbsScoreText, SportResults.ABS, sGenderString);
    }

    private void addScoreListener(EditText editText, TextView tvGrade, String type, String gender) {
        editText.addTextChangedListener((StudentTextWatcher)
                (charSequence, start, count, after) -> {
                    String grade = presenter.calculateGrade(charSequence.toString(), type, gender);
                    tvGrade.setText(grade);
                }
        );
    }


    @Override
    public void setStudentName(String name) {
        sName.setText(name);
    }

    @Override
    public void setStudentClass(String studentClass) {
        sClassButton.setText(studentClass);
    }

    @Override
    public void setStudentPhoneNo(String phoneNo) {
        sPhoneNumber.setText(phoneNo);
    }

    @Override
    public void setStudentGender(String gender) {
        genderButton.setText(gender);
        if (gender.equals(getResources().getString(R.string.boy)))
            handsTypeText.setText(getResources().getString(R.string.amount));
    }

    @Override
    public void setAerobicScore(String score) {
        sAerobicScore.setText(score);
    }

    @Override
    public void setCubesScore(String score) {
        sCubesScore.setText(score);
    }

    @Override
    public void setAbsScore(String score) {
        sAbsScore.setText(score);
    }

    @Override
    public void setHandsScore(String score) {
        sHandsScore.setText(score);
    }

    @Override
    public void setJumpScore(String score) {
        sJumpScore.setText(score);
    }
}
