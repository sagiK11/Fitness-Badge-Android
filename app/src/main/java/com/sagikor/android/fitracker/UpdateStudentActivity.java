package com.sagikor.android.fitracker;


import android.os.Bundle;

import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UpdateStudentActivity extends StudentActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        linkObjects();
        getCurrentStudent();
        setFieldsInApp();
    }

    private void getCurrentStudent() {
        currentStudent = getIntent().getParcelableExtra("student");
    }

    private void setFieldsInApp() {
        updateStudentDetailsTextFields();
        updateScoreTextFields();
        updateGradeTextFields();

    }

    private void updateStudentDetailsTextFields() {
        sName.setText(currentStudent.getName());
        sClassButton.setText(currentStudent.getStudentClass());
        genderButton.setText(currentStudent.getGender());
        if (currentStudent.getGender().equals(getResources().getString(R.string.boy)))
            handsTypeText.setText(getResources().getString(R.string.amount));

    }

    private void updateScoreTextFields() {
        if (currentStudent.getAerobicScore() != MISSING_INPUT)
            sAerobicScore.setText(String.valueOf(currentStudent.getAerobicScore()));

        if (currentStudent.getCubesScore() != MISSING_INPUT)
            sCubesScore.setText(String.valueOf(currentStudent.getCubesScore()));

        if (currentStudent.getHandsScore() != MISSING_INPUT)
            sHandsScore.setText(String.valueOf(currentStudent.getHandsScore()));

        if (currentStudent.getAbsScore() != MISSING_INPUT)
            sAbsScore.setText(String.valueOf(currentStudent.getAbsScore()));

        if (currentStudent.getJumpScore() != MISSING_INPUT)
            sJumpScore.setText(String.valueOf(currentStudent.getJumpScore()));

        if (currentStudent.getPhoneNumber() != null)
            sPhoneNumber.setText(currentStudent.getPhoneNumber());
    }

    private void linkObjects() {
        linkUserInputObjects();
        linkTextToDisplayObjects();
    }

    private void linkUserInputObjects() {
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
        addScoresTextChangedListeners();
    }

    private void linkTextToDisplayObjects() {
        sAerobicScoreText = findViewById(R.id.update_student_aerobic_text);
        sCubesScoreText = findViewById(R.id.student_cubes_text);
        sHandsScoreText = findViewById(R.id.student_hands_text);
        sJumpScoreText = findViewById(R.id.student_jump_text);
        sAbsScoreText = findViewById(R.id.student_abs_text);
        sTotalScoreText = findViewById(R.id.student_total_score);
        saveStudentButton = findViewById(R.id.button_add_student_enter_data);
        saveStudentButton.setOnClickListener(e -> saveButtonClicked());
    }

    public void saveButtonClicked() {
        if (inputErrors())
            return;
        updateGradeTextFields();
        updateStudent();
        askForSendingSMS();
    }

    private void updateStudent() {
        Student updatedStudent = new Student.Builder(sName.getText().toString().trim())
                .studentClass(sClassButton.getText().toString())
                .phoneNumber(getStudentPhoneNumber())
                .key(currentStudent.getKey())
                .studentGender(genderButton.getText().toString())
                .aerobicScore(aerobicScore)
                .cubesScore(cubesScore)
                .absScore(absScore)
                .jumpScore(jumpScore)
                .handsScore(handsScore)
                .absResult(absGrade)
                .aerobicResult(aerobicGrade)
                .jumpResult(jumpGrade)
                .handsResult(handsGrade)
                .cubesResult(cubesGrade)
                .totalScore(avg)
                .totalScoreWithoutAerobic(avgWithOutAerobic)
                .updatedDate(Utility.getTodayDate())
                .build();

        updateStudentInFirebase(updatedStudent);
        updateStudentInStudentsList(updatedStudent);
    }

    private void updateStudentInFirebase(Student updatedStudent) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String studentsChild = "students";
        FirebaseDatabase.getInstance().getReference("users").
                child(userId).child(studentsChild).child(updatedStudent.getKey()).
                setValue(updatedStudent);
    }

    private void updateStudentInStudentsList(Student updatedStudent) {
        for (int i = 0; i < MainActivity.currentUser.getStudentList().size(); i++) {
            Student student = MainActivity.currentUser.getStudentList().get(i);
            if (student.getKey().equals(updatedStudent.getKey())) {
                MainActivity.currentUser.getStudentList().set(i, updatedStudent);
                return;
            }
        }
    }

    private boolean inputErrors() {
        aerobicScore = testInput(sAerobicScore, "aerobic score");
        jumpScore = testInput(sJumpScore, "jump score");
        absScore = testInput(sAbsScore, "abs score");
        cubesScore = testInput(sCubesScore, "cubes score");
        handsScore = testInput(sHandsScore, "hands Score");
        if (aerobicScore == INVALID_INPUT || jumpScore == INVALID_INPUT || absScore == INVALID_INPUT
                || cubesScore == INVALID_INPUT || handsScore == INVALID_INPUT) {

            popToast("Invalid input!", Toast.LENGTH_SHORT, getApplicationContext());
            return true;
        }
        return false;
    }

    private double testInput(EditText text, String place) {
        return super.testInput(text, place, getApplicationContext());
    }

}
