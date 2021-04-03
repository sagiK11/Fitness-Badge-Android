package com.sagikor.android.fitracker.ui.view;

import android.os.Bundle;

import com.sagikor.android.fitracker.R;
import com.sagikor.android.fitracker.ui.contracts.UpdateStudentActivityContract;
import com.sagikor.android.fitracker.ui.presenter.UpdateStudentActivityPresenter;


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
        sClassButton = findViewById(R.id.student_class_id);
        sClassButton.setEnabled(false);
        genderButton.setEnabled(false);
        sName.setEnabled(false);
        saveStudentButton.setOnClickListener(e -> presenter.onSaveButtonClick());
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
