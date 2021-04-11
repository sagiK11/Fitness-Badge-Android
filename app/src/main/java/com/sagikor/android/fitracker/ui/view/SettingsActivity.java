package com.sagikor.android.fitracker.ui.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.sagikor.android.fitracker.R;
import com.sagikor.android.fitracker.ui.contracts.SettingsActivityContract;
import com.sagikor.android.fitracker.ui.presenter.SettingsActivityPresenter;

public class SettingsActivity extends AppCompatActivity implements SettingsActivityContract.View {

    private static final String TAG = "SettingsActivity";
    private TextView btnDeleteStudents;
    private TextView btnDeleteAccount;
    private TextView btnAddClasses;
    private SwitchMaterial switchIsAlwaysFemale;
    private SwitchMaterial switchIsAlwaysMale;
    boolean isGirlsSwitchOn;
    boolean isBoysSwitchOn;
    private TextView tvUserName;
    private SettingsActivityContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        bindViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter == null)
            presenter = new SettingsActivityPresenter();
        presenter.bind(this, getSharedPreferences("sharedPreferences", MODE_PRIVATE));
        tvUserName.setText(presenter.getUserName());
        switchLogic();
        enableSwitchesLogic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbind();
    }

    private void bindViews() {
        btnDeleteStudents = findViewById(R.id.delete_all_students_button);
        btnDeleteStudents.setOnClickListener(e -> deleteAllStudentsPopWarning());
        switchIsAlwaysFemale = findViewById(R.id.switch_always_girls);
        switchIsAlwaysMale = findViewById(R.id.switch_always_boys);
        btnDeleteAccount = findViewById(R.id.delete_account_button);
        btnDeleteAccount.setOnClickListener(e -> deleteAccountPopWarning());
        btnAddClasses = findViewById(R.id.btn_add_classes);
        btnAddClasses.setOnClickListener(e -> presenter.onAddClassesClick());
        switchIsAlwaysFemale.setOnClickListener(e -> presenter.editGenderPreferences("Girls", switchIsAlwaysFemale.isChecked()));
        switchIsAlwaysMale.setOnClickListener(e -> presenter.editGenderPreferences("Boys", switchIsAlwaysMale.isChecked()));
        tvUserName = findViewById(R.id.user_details);
    }


    @Override
    public void switchLogic() {
        isGirlsSwitchOn = presenter.isGirlsSwitchOn();
        isBoysSwitchOn = presenter.isBoysSwitchOn();

        switchIsAlwaysFemale.setChecked(isGirlsSwitchOn);
        switchIsAlwaysMale.setChecked(isBoysSwitchOn);
        enableSwitchesLogic();
    }

    @Override
    public void navToAddClasses() {
        startActivity(new Intent(this, AddClassesActivity.class));
    }


    private void enableSwitchesLogic() {
        if (isGirlsSwitchOn) {
            switchIsAlwaysMale.setEnabled(false);
            switchIsAlwaysMale.setChecked(false);
            switchIsAlwaysFemale.setEnabled(true);
        } else if (isBoysSwitchOn) {
            switchIsAlwaysFemale.setEnabled(false);
            switchIsAlwaysFemale.setChecked(false);
            switchIsAlwaysMale.setEnabled(true);
        } else {
            switchIsAlwaysFemale.setEnabled(true);
            switchIsAlwaysMale.setEnabled(true);
            switchIsAlwaysFemale.setChecked(false);
            switchIsAlwaysMale.setChecked(false);
        }
    }

    private void deleteAllStudentsPopWarning() {
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
                presenter.onClearDatabaseClick();
                popToast(STUDENTS_DELETED_SUCCESSFULLY);
            }
        });
        builder.show();
    }

    private void popToast(String msg) {
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }


    private void goodByeMessage() {
        final String SAD_TO_SEE_YOU_LEAVE = getResources().getString(R.string.farewell_user);
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(SAD_TO_SEE_YOU_LEAVE)
                .setConfirmClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    Intent intent = new Intent(this, SignInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .show();
    }

    private void deleteAccountPopWarning() {
        final String YES = getResources().getString(R.string.yes);
        final String NO = getResources().getString(R.string.no);
        final String DELETE_QUESTION = getResources().getString(R.string.delete_account_question);
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(DELETE_QUESTION)
                .setConfirmText(YES)
                .setConfirmClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    goodByeMessage();
                    presenter.onDeleteAccountClick();

                })
                .setCancelButton(NO, SweetAlertDialog::dismissWithAnimation)
                .show();
    }
}
