package com.sagikor.android.fitracker.ui.settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.sagikor.android.fitracker.R;
import com.sagikor.android.fitracker.ui.addclass.AddClassesActivity;
import com.sagikor.android.fitracker.ui.signin.SignInActivity;

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

    @Override
    public void navToSignInScreen() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
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
        final String DELETE_STUDENTS_QUESTION = getString(R.string.delete_all_students_question);
        final String NO_STUDENTS_DELETED = getString(R.string.no_students_were_deleted);
        final String[] answers = {getString(R.string.no),
                getString(R.string.yes)};

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(DELETE_STUDENTS_QUESTION);
        builder.setItems(answers, (dialog, choice) -> {
            if (choice == NO) {
                popMessage(NO_STUDENTS_DELETED, msgType.alert);
            } else {
                presenter.onClearDatabaseClick();
            }
        });
        builder.show();
    }

    @Override
    public void popMessage(String message, msgType type) {
        int backgroundColor;
        switch (type) {
            case success:
                backgroundColor = getColor(R.color.colorPrimary);
                break;
            case alert:
                backgroundColor = getColor(R.color.alert);
                break;
            case dangerous:
                backgroundColor = getColor(R.color.red);
                break;
            default:
                backgroundColor = getColor(R.color.black);
        }
        View contextView = findViewById(R.id.settings_activity_root);
        Snackbar.make(contextView, message, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(backgroundColor)
                .show();
    }

    private void goodByeMessage() {
        final String SAD_TO_SEE_YOU_LEAVE = getString(R.string.farewell_user);
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(SAD_TO_SEE_YOU_LEAVE)
                .setConfirmClickListener(sDialog -> {
                    presenter.onDeleteAccountClick();
                    sDialog.dismissWithAnimation();
                })
                .show();
    }

    private void deleteAccountPopWarning() {
        final String YES = getString(R.string.yes);
        final String NO = getString(R.string.no);
        final String DELETE_QUESTION = getString(R.string.delete_account_question);
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(DELETE_QUESTION)
                .setConfirmText(YES)
                .setConfirmClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    goodByeMessage();
                })
                .setCancelButton(NO, SweetAlertDialog::dismissWithAnimation)
                .show();
    }
}
