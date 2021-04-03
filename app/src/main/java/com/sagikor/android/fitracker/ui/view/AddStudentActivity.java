package com.sagikor.android.fitracker.ui.view;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sagikor.android.fitracker.R;
import com.sagikor.android.fitracker.ui.contracts.AddStudentActivityContract;
import com.sagikor.android.fitracker.ui.presenter.AddStudentActivityPresenter;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddStudentActivity extends StudentActivity implements AddStudentActivityContract.View {
    protected AddStudentActivityContract.Presenter presenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        this.bindViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter == null)
            presenter = new AddStudentActivityPresenter();
        presenter.bind(this, getSharedPreferences("sharedPreferences", MODE_PRIVATE));
        presenter.applyUserSetting();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbind();
    }


    @Override
    public void selectStudentGender() {
        final String CHOOSE_GENDER = getResources().getString(R.string.choose_gender);
        final String BOY = getResources().getString(R.string.boy);
        final String GIRL = getResources().getString(R.string.girl);

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(CHOOSE_GENDER)
                .setConfirmText(GIRL)
                .setConfirmClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    genderButton.setText(GIRL);
                    handsTypeText.setText(getResources().getString(R.string.minutes));

                })
                .setCancelButton(BOY, sDialog -> {
                    sDialog.dismissWithAnimation();
                    handsTypeText.setText(getResources().getString(R.string.amount));
                    genderButton.setText(BOY);
                })
                .show();
    }

    @Override
    public void setGirlsPreferences() {
        genderButton.setText(getResources().getString(R.string.girl));
        genderButton.setEnabled(false);
        handsTypeText.setText(getResources().getString(R.string.minutes));
    }

    @Override
    public void setBoysPreferences() {
        genderButton.setText(getResources().getString(R.string.boy));
        genderButton.setEnabled(false);
        handsTypeText.setText(getResources().getString(R.string.amount));
    }

    @Override
    public void setDefaultPreferences() {
        genderButton.setText(getResources().getString(R.string.choose_gender));
    }

    @Override
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

    @Override
    public void askForSendingSMS() {
        super.askForSendingSMS();
    }

    @Override
    public void popMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getClassStringResource() {
        return getResources().getString(R.string.Class);
    }

    @Override
    public String getGenderStringResource() {
        return getResources().getString(R.string.choose_gender);
    }

    @Override
    public String getGradeStringResource() {
        return getResources().getString(R.string.grade);
    }

    @Override
    protected void bindViews() {
        super.bindViews();
        chooseClassButton.setOnClickListener(e -> presenter.onSelectStudentClass());
        genderButton.setOnClickListener(e -> presenter.onSelectStudentGender());
        saveStudentButton.setOnClickListener(e -> presenter.onAddStudentClick());
        sName.requestFocus();
    }


    private void setClass(String sClass) {
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

