package com.sagikor.android.fitracker.ui.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.sagikor.android.fitracker.R;
import com.sagikor.android.fitracker.ui.contracts.SettingsActivityContract;
import com.sagikor.android.fitracker.ui.presenter.SettingsActivityPresenter;

public class SettingsActivity extends AppCompatActivity implements SettingsActivityContract.View {

    private Button deleteStudentsButton;
    private Button deleteAccountButton;
    private Switch alwaysGirlsSwitch;
    private Switch alwaysBoysSwitch;
    boolean isGirlsSwitchOn;
    boolean isBoysSwitchOn;
    private SettingsActivityContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        linkObjects();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(presenter == null)
            presenter = new SettingsActivityPresenter();
        presenter.bind(this,getSharedPreferences("sharedPreferences",MODE_PRIVATE));
        switchLogic();
        enableSwitchesLogic();
    }

    @Override
    protected void onPause(){
        super.onPause();
        presenter.unbind();
    }

    private void linkObjects() {
        deleteStudentsButton = findViewById(R.id.delete_all_students_button);
        deleteStudentsButton.setOnClickListener(e -> deleteAllStudents());
        alwaysGirlsSwitch = findViewById(R.id.switch_always_girls);
        alwaysBoysSwitch = findViewById(R.id.switch_always_boys);
        deleteAccountButton = findViewById(R.id.delete_account_button);
        deleteAccountButton.setOnClickListener(e -> deleteAccountPop());
        alwaysGirlsSwitch.setOnClickListener(e -> presenter.editGenderPreferences("Girls",alwaysGirlsSwitch.isChecked()));
        alwaysBoysSwitch.setOnClickListener(e -> presenter.editGenderPreferences("Boys",alwaysBoysSwitch.isChecked()));
    }

    @Override
    public void switchLogic() {
        isGirlsSwitchOn = presenter.isGirlsSwitchOn();
        isBoysSwitchOn = presenter.isBoysSwitchOn();

        alwaysGirlsSwitch.setChecked(isGirlsSwitchOn);
        alwaysBoysSwitch.setChecked(isBoysSwitchOn);
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
                presenter.clearDatabase();
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

    private void deleteAccountPop() {
        final String YES = getResources().getString(R.string.yes);
        final String NO = getResources().getString(R.string.no);
        final String DELETE_QUESTION = getResources().getString(R.string.delete_account_question);
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(DELETE_QUESTION)
                .setConfirmText(YES)
                .setConfirmClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    goodByeMessage();
                    presenter.deleteAccount();

                })
                .setCancelButton(NO, SweetAlertDialog::dismissWithAnimation)
                .show();
    }
}
