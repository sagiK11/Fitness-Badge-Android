package com.sagikor.android.fitracker.ui.addupdatestudent;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sagikor.android.fitracker.R;
import com.sagikor.android.fitracker.data.model.UserClass;
import com.sagikor.android.fitracker.utils.AppExceptions;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddStudentActivity extends StudentActivity implements AddStudentActivityContract.View {
    protected AddStudentActivityContract.Presenter presenter;

    @Override
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
        final String CHOOSE_GENDER = getString(R.string.choose_gender);
        final String BOY = getString(R.string.male);
        final String GIRL = getString(R.string.female);

        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(CHOOSE_GENDER)
                .setConfirmText(GIRL)
                .setConfirmClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    changeLayoutToFemale();
                })
                .setCancelButton(BOY, sDialog -> {
                    sDialog.dismissWithAnimation();
                    changeLayoutToMale();
                })
                .show();
    }

    @Override
    public void setGirlsPreferences() {
        btnChooseGender.setText(getString(R.string.female));
        btnChooseGender.setEnabled(false);
        changeLayoutToFemale();
    }

    @Override
    public void setBoysPreferences() {
        btnChooseGender.setText(getString(R.string.male));
        btnChooseGender.setEnabled(false);
        changeLayoutToMale();
    }

    @Override
    public void setDefaultPreferences() {
        btnChooseGender.setText(getString(R.string.gender));
    }

    @Override
    public void selectStudentClass() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        View row = getLayoutInflater().inflate(R.layout.class_list_view, null);

        ListView listView = row.findViewById(R.id.class_list_view);

        if (presenter.getTeacherClasses().isEmpty()) {
            popFailWindow(AppExceptions.MISSING_CLASSES);
            return;
        }
        List<String> list = new ArrayList<>();
        for (UserClass clss : presenter.getTeacherClasses()) {
            list.add(clss.getClassName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        alertDialog.setView(row);

        AlertDialog dialog = alertDialog.create();
        final String CHOOSE_CLASS = getString(R.string.choose_class);
        dialog.setTitle(CHOOSE_CLASS);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            setClass((String) parent.getItemAtPosition(position));
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    public String getClassStringResource() {
        return getString(R.string.Class);
    }

    @Override
    public String getGradeStringResource() {
        return getString(R.string.grade);
    }

    @Override
    protected void bindViews() {
        super.bindViews();
        btnChooseClass.setOnClickListener(e -> presenter.onSelectStudentClass());
        btnChooseGender.setOnClickListener(e -> presenter.onSelectStudentGender());
        btnSaveStudent.setOnClickListener(e -> presenter.onAddStudentClick());
        etStudentName.clearFocus();
    }

    private void setClass(String sClass) {
        btnChooseClass.setText(sClass);
    }
}

