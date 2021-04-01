package com.sagikor.android.fitracker.ui.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

import android.util.Log;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.sagikor.android.fitracker.R;


public class SettingActivity extends AppCompatActivity {

    private Button deleteStudentsButton;
    private Button deleteAccountButton;
    private Switch alwaysGirlsSwitch;
    private Switch alwaysBoysSwitch;
    boolean isGirlsSwitchOn;
    boolean isBoysSwitchOn;
    final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        linkObjects();
        switchLogic();
    }

    private void linkObjects() {
        deleteStudentsButton = findViewById(R.id.delete_all_students_button);
        deleteStudentsButton.setOnClickListener(e -> deleteAllStudents());
        alwaysGirlsSwitch = findViewById(R.id.switch_always_girls);
        alwaysBoysSwitch = findViewById(R.id.switch_always_boys);
        deleteAccountButton = findViewById(R.id.delete_account_button);
        deleteAccountButton.setOnClickListener(e -> deleteAccountPop());
        alwaysGirlsSwitch.setOnClickListener(e -> girlsSwitchEvent());
        alwaysBoysSwitch.setOnClickListener(e -> boysSwitchEvent());
    }


    private void switchLogic() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        isGirlsSwitchOn = sharedPreferences.getBoolean("alwaysGirlsSwitch", false);
        isBoysSwitchOn = sharedPreferences.getBoolean("alwaysBoysSwitch", false);

        alwaysGirlsSwitch.setChecked(isGirlsSwitchOn);
        alwaysBoysSwitch.setChecked(isBoysSwitchOn);
        enableSwitchesLogic();
    }

    private void girlsSwitchEvent() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        isGirlsSwitchOn = alwaysGirlsSwitch.isChecked();

        editor.putBoolean("alwaysGirlsSwitch", isGirlsSwitchOn);
        editor.apply();
        enableSwitchesLogic();
    }

    private void boysSwitchEvent() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        isBoysSwitchOn = alwaysBoysSwitch.isChecked();

        editor.putBoolean("alwaysBoysSwitch", isBoysSwitchOn);
        editor.apply();
        enableSwitchesLogic();
    }

    private void enableSwitchesLogic() {
        if (isGirlsSwitchOn) {
            alwaysBoysSwitch.setEnabled(false);
            alwaysBoysSwitch.setChecked(false);
            alwaysGirlsSwitch.setEnabled(true);
        } else if (isBoysSwitchOn) {
            alwaysGirlsSwitch.setEnabled(false);
            alwaysGirlsSwitch.setChecked(false);
            alwaysBoysSwitch.setEnabled(true);
        } else {
            alwaysGirlsSwitch.setEnabled(true);
            alwaysBoysSwitch.setEnabled(true);
            alwaysGirlsSwitch.setChecked(false);
            alwaysBoysSwitch.setChecked(false);
        }
    }

    private void deleteAllStudents() {
        final int NO = 0;
        final String DELETE_STUDENTS_QUESTION = getResources().getString(R.string.delete_all_students_question);
        final String NO_STUDENTS_DELETED = getResources().getString(R.string.no_students_were_deleted);
        final String STUDENTS_DELETED_SUCCESSFULLY = getResources().getString(R.string.students_deleted_successfully);
        final String[] answers = {getResources().getString(R.string.no),
                getResources().getString(R.string.yes)};

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(DELETE_STUDENTS_QUESTION);
        builder.setItems(answers, (dialog, choice) -> {
            if (choice == NO) {
                popToast(NO_STUDENTS_DELETED);
            } else {
                clearDataBase();
                popToast(STUDENTS_DELETED_SUCCESSFULLY);
            }
        });
        builder.show();
    }

    private void clearDataBase() {
        final String USER_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String STUDENTS_CHILD = "students";
        FirebaseDatabase.getInstance().getReference("users").child(USER_ID).child(STUDENTS_CHILD).removeValue();
        MainActivity.currentUser.clearStudentsList();
    }


    private void popToast(String msg) {
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void deleteAccount() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).removeValue();

        user.delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("SettingActivity", "User account deleted.");
                goodByeMessage();
            }
        });
    }

    private void goodByeMessage() {
        final String SAD_TO_SEE_YOU_LEAVE = getResources().getString(R.string.farewell_user);
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(SAD_TO_SEE_YOU_LEAVE)
                .setConfirmClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    Intent intent = new Intent(this, WelcomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .show();
    }

    private void deleteAccountPop() {
        final String YES = getResources().getString(R.string.yes);
        final String NO = getResources().getString(R.string.no);
        final String DELETE_QUESTION = getResources().getString(R.string.delete_account_question);
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(DELETE_QUESTION)
                .setConfirmText(YES)
                .setConfirmClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    deleteAccount();

                })
                .setCancelButton(NO, SweetAlertDialog::dismissWithAnimation)
                .show();
    }
}
