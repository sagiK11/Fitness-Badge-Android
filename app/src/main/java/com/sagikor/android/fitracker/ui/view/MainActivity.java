package com.sagikor.android.fitracker.ui.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;


import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import au.com.bytecode.opencsv.CSVWriter;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.sagikor.android.fitracker.R;
import com.sagikor.android.fitracker.ui.contracts.MainActivityContract;
import com.sagikor.android.fitracker.ui.presenter.MainActivityPresenter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    private static final String TAG = "MainActivity";
    Button btnAddStudent;
    Button btnSearchStudent;
    Button btnMailResults;
    Button btnUpdateStudent;
    ProgressBar progressBar;
    ImageView ivBackgroundImage;
    Toolbar toolbar;
    MainActivityPresenter presenter;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        addButtonsOnClickListeners();
        initFirebase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter == null)
            presenter = new MainActivityPresenter();
        presenter.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbind();
    }


    private void initFirebase() {
        FirebaseApp.initializeApp(this);
    }

    @Override
    public void navToAddStudentScreen() {
        startActivity(new Intent(this, AddStudentActivity.class));
    }

    @Override
    public void navToUpdateStudentScreen() {
        navToViewStudentsScreen();
    }

    @Override
    public void navToViewStudentsScreen() {
        startActivity(new Intent(this, ViewStudentsActivity.class));
    }

    @Override
    public void navToSettingsScreen() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    public void navToStatisticsScreen() {
        startActivity(new Intent(this, StatisticsActivity.class));
    }

    @Override
    public void sendDatabaseToEmail() {
        //check if we already got permission.
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //if we're here then we have'nt got the permission yet.
            //the line below prompts the window for the user.
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        } else {
            new EmailSendTask().execute();
        }

    }

    @Override
    public void setActiveMode() {
        hideProgressBar();
        setViewsEnableMode(true);
    }


    @Override
    public void setLoadingMode() {
        showProgressBar();
        setViewsEnableMode(false);
    }

    private void setViewsEnableMode(boolean isEnable) {
        btnAddStudent.setClickable(isEnable);
        btnSearchStudent.setClickable(isEnable);
        btnMailResults.setClickable(isEnable);
        btnUpdateStudent.setClickable(isEnable);
        ivBackgroundImage.setAlpha(isEnable ? 1f : 0.1f);
        btnAddStudent.setAlpha(isEnable ? 1f : 0.1f);
        btnSearchStudent.setAlpha(isEnable ? 1f : 0.1f);
        btnMailResults.setAlpha(isEnable ? 1f : 0.1f);
        btnUpdateStudent.setAlpha(isEnable ? 1f : 0.1f);
        toolbar.setAlpha(isEnable ? 1f : 0f);
    }

    @Override
    public void disconnectUser() {
        signOutQuestionPop();
    }


    private void bindViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnAddStudent = findViewById(R.id.button_add_student);
        btnSearchStudent = findViewById(R.id.button_search);
        btnMailResults = findViewById(R.id.button_mail_results);
        btnUpdateStudent = findViewById(R.id.button_update_student);
        progressBar = findViewById(R.id.main_activity_progress_bar);
        ivBackgroundImage = findViewById(R.id.background_image);
        setButtonsBackgroundAlpha();
    }


    private void setButtonsBackgroundAlpha() {
        btnAddStudent.getBackground().setAlpha(50);
        btnSearchStudent.getBackground().setAlpha(50);
        btnMailResults.getBackground().setAlpha(50);
        btnUpdateStudent.getBackground().setAlpha(50);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_drawer, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_statistics) {
            presenter.onNavToStatisticsClick();
        } else if (id == R.id.nav_settings) {
            presenter.onNavToSettingsClick();
        } else if (id == R.id.nav_sign_out) {
            presenter.onDisconnectClick();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addButtonsOnClickListeners() {
        btnAddStudent.setOnClickListener(e -> presenter.onNavToAddStudentClick());
        btnSearchStudent.setOnClickListener(e -> presenter.onNavToViewStudentsClick());
        btnMailResults.setOnClickListener(e -> presenter.onSendToEmailClick());
        btnUpdateStudent.setOnClickListener(e -> presenter.onNavToUpdateStudentClick());
    }


    private void signOutQuestionPop() {
        final String SURE_QUESTION = getResources().getString(R.string.exit_question);
        final String YES = getResources().getString(R.string.yes);
        final String NO = getResources().getString(R.string.no);
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(SURE_QUESTION)
                .setConfirmText(YES)
                .setConfirmClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    signOut();
                })
                .setCancelButton(NO, SweetAlertDialog::dismissWithAnimation)
                .show();

    }

    private void signOut() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }


    public class EmailSendTask extends AsyncTask<String, String, String> {
        private static final String TAG = "EmailSendTask";

        @Override
        protected void onPreExecute() {
            // AUTO GENERATED
        }

        @Override
        protected void onPostExecute(String success) {
            super.onPostExecute(success);

            String exportCompleted = getResources().getString(R.string.export_completed);
            Toast toastOne = Toast.makeText(MainActivity.this, exportCompleted, Toast.LENGTH_SHORT);
            toastOne.show();

        }

        @Override
        protected String doInBackground(final String... strings) {
            File file = getNewFile();
            try (CSVWriter csvWrite = new CSVWriter(new FileWriter(file))) {
                writeHeader(csvWrite, getExcelHeaders());
                writeStudentsData(csvWrite);
                sendEmail(file);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return null;
        }

        private void sendEmail(File file) {
            Intent sendToMail = new Intent(Intent.ACTION_SENDTO);
            sendToMail.setData(Uri.parse("mailto:"));
            sendToMail.putExtra(Intent.EXTRA_SUBJECT, "Students Test Grades");
            sendToMail.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file.getAbsoluteFile()));
            if (sendToMail.resolveActivity(getPackageManager()) != null)
                startActivity(sendToMail);
        }

        private void writeStudentsData(CSVWriter csvWrite) {
            presenter.writeStudentsData(csvWrite);
        }

        private void writeHeader(CSVWriter csvWrite, String[] excelHeaders) {
            csvWrite.writeNext(excelHeaders);
        }


        private File getNewFile() {
            File exportDir = new File(Environment.getExternalStorageDirectory(), "");
            return new File(exportDir, "ExcelFile.csv");
        }


        private String[] getExcelHeaders() {
            final String name = getResources().getString(R.string.name);
            final String studentClass = getResources().getString(R.string.student_class);
            final String aerobicResult = getResources().getString(R.string.aerobic_result);
            final String aerobicScore = getResources().getString(R.string.aerobic_score);
            final String absResult = getResources().getString(R.string.abs_result);
            final String absScore = getResources().getString(R.string.abs_score);
            final String jumpResult = getResources().getString(R.string.jump_result);
            final String jumpScore = getResources().getString(R.string.jump_score);
            final String handsResult = getResources().getString(R.string.hands_result);
            final String handsScore = getResources().getString(R.string.hands_score);
            final String cubesResult = getResources().getString(R.string.cubes_result);
            final String cubesScore = getResources().getString(R.string.cubes_score);
            final String finalScore = getResources().getString(R.string.final_score);

            return new String[]{name, studentClass, aerobicScore, aerobicResult,
                    absScore, absResult, handsScore, handsResult, cubesScore, cubesResult
                    , jumpScore, jumpResult, finalScore};

        }
    }

}
