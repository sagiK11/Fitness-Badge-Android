package com.sagikor.android.fitracker;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddStudentActivity extends StudentActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        linkObjects();
        applyUserSetting();
    }

    private void linkObjects() {
        linkUserInputObjects();
        linkTextToDisplayObjects();
    }

    private void linkUserInputObjects() {
        sName = findViewById(R.id.student_name_to_enter_id);
        sPhoneNumber = findViewById(R.id.phone_number_to_enter_id);
        chooseClassButton = findViewById(R.id.student_class_id);
        genderButton = findViewById(R.id.gender_button);
        saveStudentButton = findViewById(R.id.button_add_student_enter_data);
        sAerobicScore = findViewById(R.id.update_student_aerobic_id);
        sCubesScore = findViewById(R.id.update_student_cubes_id);
        sHandsScore = findViewById(R.id.update_student_hands_id);
        sJumpScore = findViewById(R.id.update_student_jump_id);
        sAbsScore = findViewById(R.id.update_student_abs_id);
        handsTypeText = findViewById(R.id.hands_minutes_text_view);
        chooseClassButton.setOnClickListener(e -> selectStudentClass());
        genderButton.setOnClickListener(e -> selectStudentGender());
        saveStudentButton.setOnClickListener(e -> addStudentClicked());
        sName.requestFocus();
        addScoresTextChangedListeners();

    }

    private void linkTextToDisplayObjects() {
        sAerobicScoreText = findViewById(R.id.update_student_aerobic_text);
        sCubesScoreText = findViewById(R.id.student_cubes_text);
        sHandsScoreText = findViewById(R.id.student_hands_text);
        sJumpScoreText = findViewById(R.id.student_jump_text);
        sAbsScoreText = findViewById(R.id.student_abs_text);
        sTotalScoreText = findViewById(R.id.student_total_score);
    }

    private void applyUserSetting() {
        final String SHARED_PREFS = "sharedPrefs";
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        boolean isGirlsSwitchOn = sharedPreferences.getBoolean("alwaysGirlsSwitch", false);
        boolean isBoysSwitchOn = sharedPreferences.getBoolean("alwaysBoysSwitch", false);

        if (isGirlsSwitchOn) {
            genderButton.setText(getResources().getString(R.string.girl));
            genderButton.setEnabled(false);
            sGenderString = getResources().getString(R.string.girl);
            handsTypeText.setText(getResources().getString(R.string.minutes));
        } else if (isBoysSwitchOn) {
            genderButton.setText(getResources().getString(R.string.boy));
            genderButton.setEnabled(false);
            sGenderString = getResources().getString(R.string.boy);
            handsTypeText.setText(getResources().getString(R.string.amount));
        } else {
            genderButton.setText(getResources().getString(R.string.choose_gender));
        }
    }

    public void addStudentClicked() {
        getAndTestUserInput();
        if (isInputErrorsDetected())
            return;

        updateGradeTextFields();
        addStudent();
    }

    private boolean isInputErrorsDetected() {
        return isErrorsInStudentName() || isErrorsInStudentClass() || isErrorsInStudentScores()
                || isErrorsInGender();
    }

    private void addStudent() {
        Student newStudent = createNewStudent();
        addStudentToFirebase(newStudent);
    }

    private void addStudentToFirebase(Student newStudent) {
        if (isStudentExistsInFirebase(newStudent))
            return;

        final String USER_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String STUDENTS_CHILD = "students";
        FirebaseDatabase.getInstance().getReference("users").child(USER_ID)
                .child(STUDENTS_CHILD).child(newStudent.getKey()).setValue(newStudent);
        MainActivity.currentUser.addStudentToList(newStudent);
        askForSendingSMS();
    }

    private boolean isStudentExistsInFirebase(Student newStudent) {
        for (Student student : MainActivity.currentUser.getStudentList()) {
            if (isStudentExists(student, newStudent)) {
                popFailWindow();
                return true;
            }
        }
        return false;
    }

    private boolean isStudentExists(Student student, Student newStudent) {
        return student.getName().equals(newStudent.getName()) && student.getClass().equals(newStudent.getClass());
    }

    private Student createNewStudent() {
        String studentKey = FirebaseDatabase.getInstance().getReference("users").push().getKey();
        return new Student.Builder(getStudentName())
                .studentClass(sClassString)
                .phoneNumber(sPhoneNumber.getText().toString())
                .key(studentKey)
                .userId(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .studentGender(sGenderString)
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
    }

    private String getStudentName() {
        return sName.getText().toString().trim();
    }

    private void getAndTestUserInput() {
        aerobicScore = testInput(sAerobicScore, "aerobic score");
        jumpScore = testInput(sJumpScore, "jump score");
        absScore = testInput(sAbsScore, "abs score");
        cubesScore = testInput(sCubesScore, "cubes score");
        handsScore = testInput(sHandsScore, "hands score");
    }

    private boolean isErrorsInStudentName() {
        final String STUDENT_NAME = getResources().getString(R.string.please_enter_student_name);
        if (sName.getText().toString().length() == 0) {
            popToast(STUDENT_NAME, Toast.LENGTH_LONG, getApplicationContext());
            return true;
        }
        return false;
    }

    private boolean isErrorsInStudentClass() {
        final String STUDENT_CLASS = getResources().getString(R.string.please_enter_student_class);
        if (sClassString == null) {
            popToast(STUDENT_CLASS, Toast.LENGTH_LONG, getApplicationContext());
            return true;
        }
        return false;
    }



    private boolean isErrorsInGender() {
        final String GENDER_ERROR = getResources().getString(R.string.gender_error);
        if (sGenderString == null) {
            popToast(GENDER_ERROR, Toast.LENGTH_LONG, getApplicationContext());
            return true;
        }
        return false;
    }

    private double testInput(EditText text, String place) {
        return super.testInput(text, place, getApplicationContext());
    }

    private void selectStudentGender() {
        final String CHOOSE_GENDER = getResources().getString(R.string.choose_gender);
        final String BOY = getResources().getString(R.string.boy);
        final String GIRL = getResources().getString(R.string.girl);

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(CHOOSE_GENDER)
                .setConfirmText(GIRL)
                .setConfirmClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    sGenderString = GIRL;
                    genderButton.setText(sGenderString);
                    handsTypeText.setText(getResources().getString(R.string.minutes));

                })
                .setCancelButton(BOY, sDialog -> {
                    sDialog.dismissWithAnimation();
                    sGenderString = BOY;
                    handsTypeText.setText(getResources().getString(R.string.amount));
                    genderButton.setText(sGenderString);
                })
                .show();
    }

    public void selectStudentClass() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        View row = getLayoutInflater().inflate(R.layout.class_list_item, null);

        ListView listView = row.findViewById(R.id.class_list_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getStudentsClasses());

        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        alertDialog.setView(row);

        AlertDialog dialog = alertDialog.create();
        final String CHOOSE_CLASS = getResources().getString(R.string.choose_class);
        dialog.setTitle(CHOOSE_CLASS);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            setClass((String) parent.getItemAtPosition(position));
            dialog.dismiss();
        });
        dialog.show();
    }

    private void setClass(String sClass) {
        this.sClassString = sClass;
        chooseClassButton.setText(sClass);
    }

    private String[] getStudentsClasses() {
        final String ninth1 = getResources().getString(R.string.ninth1);
        final String ninth2 = getResources().getString(R.string.ninth2);
        final String ninth3 = getResources().getString(R.string.ninth3);
        final String ninth4 = getResources().getString(R.string.ninth4);
        final String ninth5 = getResources().getString(R.string.ninth5);
        final String ninth6 = getResources().getString(R.string.ninth6);
        final String ninth7 = getResources().getString(R.string.ninth7);
        final String ninth8 = getResources().getString(R.string.ninth8);
        final String ninth9 = getResources().getString(R.string.ninth9);

        final String tenth1 = getResources().getString(R.string.tenth1);
        final String tenth2 = getResources().getString(R.string.tenth2);
        final String tenth3 = getResources().getString(R.string.tenth3);
        final String tenth4 = getResources().getString(R.string.tenth4);
        final String tenth5 = getResources().getString(R.string.tenth5);
        final String tenth6 = getResources().getString(R.string.tenth6);
        final String tenth7 = getResources().getString(R.string.tenth7);
        final String tenth8 = getResources().getString(R.string.tenth8);
        final String tenth9 = getResources().getString(R.string.tenth9);

        final String eleventh1 = getResources().getString(R.string.eleventh1);
        final String eleventh2 = getResources().getString(R.string.eleventh2);
        final String eleventh3 = getResources().getString(R.string.eleventh3);
        final String eleventh4 = getResources().getString(R.string.eleventh4);
        final String eleventh5 = getResources().getString(R.string.eleventh5);
        final String eleventh6 = getResources().getString(R.string.eleventh6);
        final String eleventh7 = getResources().getString(R.string.eleventh7);
        final String eleventh8 = getResources().getString(R.string.eleventh8);
        final String eleventh9 = getResources().getString(R.string.eleventh9);

        final String twelfth1 = getResources().getString(R.string.twelfth1);
        final String twelfth2 = getResources().getString(R.string.twelfth2);
        final String twelfth3 = getResources().getString(R.string.twelfth3);
        final String twelfth4 = getResources().getString(R.string.twelfth4);
        final String twelfth5 = getResources().getString(R.string.twelfth5);
        final String twelfth6 = getResources().getString(R.string.twelfth6);
        final String twelfth7 = getResources().getString(R.string.twelfth7);
        final String twelfth8 = getResources().getString(R.string.twelfth8);
        final String twelfth9 = getResources().getString(R.string.twelfth9);

        return new String[]{ninth1, ninth2, ninth3, ninth4, ninth5, ninth6, ninth7, ninth8, ninth9,
                tenth1, tenth2, tenth3, tenth4, tenth5, tenth6, tenth7, tenth8, tenth9,
                eleventh1, eleventh2, eleventh3, eleventh4, eleventh5, eleventh6,
                eleventh7, eleventh8, eleventh9, twelfth1, twelfth2, twelfth3, twelfth4
                , twelfth5, twelfth6, twelfth7, twelfth8, twelfth9};
    }

}

