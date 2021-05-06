package com.sagikor.android.fitracker.ui.addupdatestudent;

import android.os.Bundle;

import com.sagikor.android.fitracker.R;


public class UpdateStudentActivity extends StudentActivity implements UpdateStudentActivityContract.View {

    private UpdateStudentActivityContract.Presenter presenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        this.bindViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter == null)
            presenter = new UpdateStudentActivityPresenter();
        presenter.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbind();
    }

    @Override
    protected void bindViews() {
        super.bindViews();
        btnChooseClass.setEnabled(false);
        btnChooseGender.setEnabled(false);
        etStudentName.setEnabled(false);
        btnSaveStudent.setOnClickListener(e -> presenter.onSaveButtonClick());
    }

    @Override
    public void setStudentName(String name) {
        etStudentName.setText(name);
    }

    @Override
    public void setStudentClass(String studentClass) {
        btnChooseClass.setText(studentClass);
    }

    @Override
    public void setStudentPhoneNo(String phoneNo) {
        etStudentPhoneNo.setText(phoneNo);
    }

    @Override
    public void setAerobicScore(String score) {
        etAerobicScore.setText(score);
    }

    @Override
    public void setCubesScore(String score) {
        etCubesScore.setText(score);
    }

    @Override
    public void setAbsScore(String score) {
        etAbsScore.setText(score);
    }

    @Override
    public void setHandsScore(String score) {
        etHandsScore.setText(score);
    }

    @Override
    public void setJumpScore(String score) {
        etJumpScore.setText(score);
    }

    @Override
    public void setAerobicGrade(String grade) {
        tvAerobicGrade.setText(grade);
    }

    @Override
    public void setCubesGrade(String grade) {
        tvCubesGrade.setText(grade);
    }

    @Override
    public void setAbsGrade(String grade) {
        tvAerobicGrade.setText(grade);
    }

    @Override
    public void setHandsGrade(String grade) {
        tvHandsGrade.setText(grade);
    }

    @Override
    public void setJumpGrade(String grade) {
        tvJumpGrade.setText(grade);
    }

    @Override
    public void setAerobicWalkingSwitch(boolean isWalking) {
        swAerobicOption.setChecked(isWalking);
    }

    @Override
    public void setPushUpHalfSwitch(boolean isPushUpHalf) {
        swPushUpOption.setChecked(isPushUpHalf);
    }

    @Override
    public void setStudentGender(String gender) {
        btnChooseGender.setText(gender);
        if (gender.equals(getString(R.string.male))) {
            changeLayoutToMale();
        } else {
            changeLayoutToFemale();
        }
    }
}
