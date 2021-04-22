package com.sagikor.android.fitracker.ui.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import cn.pedant.SweetAlert.SweetAlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.sagikor.android.fitracker.R;
import com.sagikor.android.fitracker.ui.contracts.MainActivityContract;
import com.sagikor.android.fitracker.ui.presenter.MainActivityPresenter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    private static final String TAG = "MainActivity";
    MaterialCardView btnAddStudent;
    MaterialCardView btnSearchStudent;
    MaterialCardView btnMailResults;
    MaterialCardView btnAddClasses;
    ProgressBar progressBar;
    ImageView ivBackgroundImage;
    DrawerLayout drawerLayout;
    MainActivityPresenter presenter;
    TextView btnDrawerNavToSettings;
    TextView btnDrawerNavToStatistics;
    TextView btnDrawerNavToAbout;
    TextView btnDrawerNavToRate;
    TextView btnDrawerLogout;
    TextView tvUserName;
    ImageView btnDrawerMenu;


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

    @Override
    public void navToAddStudentScreen() {
        startActivity(new Intent(this, AddStudentActivity.class));
    }

    @Override
    public void navToAddClassesScreen() {
        startActivity(new Intent(this, AddClassesActivity.class));
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
    public void navToTermsOfUseUsUrl() {
        final String url = "https://sites.google.com/view/fitrackerprivacy/home";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    @Override
    public void navToRateUs() {
        final String url = "https://play.google.com/store/apps/details?id=com.sagikor.android.fitracker&hl=en_US";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }


    @Override
    public void openNavDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void sendDatabaseToEmail() {
        Thread sender = new EmailSendThread();
        sender.start();
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

    @Override
    public void disconnectUser() {
        signOutQuestionPop();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUserName(String name) {
        tvUserName.setText(name);
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
    }

    private void setViewsEnableMode(boolean isEnable) {
        btnAddStudent.setClickable(isEnable);
        btnSearchStudent.setClickable(isEnable);
        btnMailResults.setClickable(isEnable);
        btnAddClasses.setClickable(isEnable);
        ivBackgroundImage.setAlpha(isEnable ? 1f : 0.1f);
        btnAddStudent.setAlpha(isEnable ? 1f : 0.1f);
        btnSearchStudent.setAlpha(isEnable ? 1f : 0.1f);
        btnMailResults.setAlpha(isEnable ? 1f : 0.1f);
        btnAddClasses.setAlpha(isEnable ? 1f : 0.1f);
    }

    private void bindViews() {
        btnAddStudent = findViewById(R.id.button_add_student);
        btnSearchStudent = findViewById(R.id.button_search);
        btnMailResults = findViewById(R.id.button_mail_results);
        btnAddClasses = findViewById(R.id.button_add_classes);
        progressBar = findViewById(R.id.main_activity_progress_bar);
        ivBackgroundImage = findViewById(R.id.background_image);
        drawerLayout = findViewById(R.id.main_activity_root);
        btnDrawerMenu = findViewById(R.id.toolbar_menu);
        btnDrawerNavToSettings = findViewById(R.id.btn_nav_drawer_settings);
        btnDrawerNavToAbout = findViewById(R.id.btn_nav_drawer_about);
        btnDrawerNavToRate = findViewById(R.id.btn_nav_drawer_rate_us);
        btnDrawerNavToStatistics = findViewById(R.id.btn_nav_drawer_statistics);
        btnDrawerLogout = findViewById(R.id.btn_nav_drawer_log_out);
        tvUserName = findViewById(R.id.drawer_user_name);
    }


    private void addButtonsOnClickListeners() {
        btnAddStudent.setOnClickListener(e -> presenter.onNavToAddStudentClick());
        btnSearchStudent.setOnClickListener(e -> presenter.onNavToViewStudentsClick());
        btnMailResults.setOnClickListener(e -> presenter.onSendToEmailClick());
        btnAddClasses.setOnClickListener(e -> presenter.onNavToAddClassesClick());
        btnDrawerMenu.setOnClickListener(e -> presenter.onOpenDrawer());
        btnDrawerNavToSettings.setOnClickListener(e -> presenter.onNavToSettingsClick());
        btnDrawerNavToAbout.setOnClickListener(e -> presenter.onNavToTermsOfUseClick());
        btnDrawerNavToRate.setOnClickListener(e -> presenter.onNavToRateUsClick());
        btnDrawerNavToStatistics.setOnClickListener(e -> presenter.onNavToStatisticsClick());
        btnDrawerLogout.setOnClickListener(e -> disconnectUser());
    }

    private void signOutQuestionPop() {
        final String SURE_QUESTION = getString(R.string.exit_question);
        final String YES = getString(R.string.yes);
        final String NO = getString(R.string.no);
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(SURE_QUESTION)
                .setConfirmText(YES)
                .setConfirmClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    presenter.onDisconnectClick();
                    signOut();
                })
                .setCancelButton(NO, SweetAlertDialog::dismissWithAnimation)
                .show();

    }

    private void signOut() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity(intent);
        finish();
    }

    @Override
    public void popMessage(String message, msgType type) {
        View contextView = findViewById(R.id.main_activity_root);
        Snackbar.make(contextView, message, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(getColor(R.color.colorPrimary))
                .show();
    }

    public boolean isDrawerOpen() {
        return drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if (isDrawerOpen()) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    private class EmailSendThread extends Thread {
        private static final String TAG = "EmailSendThread";

        @Override
        public void run() {
            final String students_grade = getString(R.string.students_grades);
            //get students data
            StringBuilder sb = new StringBuilder();
            sb.append(getExcelHeaders());
            sb.append(presenter.getStudentsAsCSV());


            try {
                FileOutputStream out = openFileOutput(students_grade + ".csv", MODE_PRIVATE);
                out.write((sb.toString()).getBytes());
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //exporting
            File file = new File(getFilesDir(), students_grade + ".csv");
            String authority = getApplicationContext().getPackageName() + ".provider";
            Uri path = FileProvider.getUriForFile(getApplicationContext(), authority, file);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, students_grade);
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(Intent.createChooser(fileIntent, "Send mail"));
        }

        private String getExcelHeaders() {
            final String name = getString(R.string.name);
            final String studentClass = getString(R.string.student_class);
            final String aerobicResult = getString(R.string.aerobic_result);
            final String aerobicScore = getString(R.string.aerobic_score);
            final String absResult = getString(R.string.abs_result);
            final String absScore = getString(R.string.abs_score);
            final String jumpResult = getString(R.string.jump_result);
            final String jumpScore = getString(R.string.jump_score);
            final String handsResult = getString(R.string.hands_result);
            final String handsScore = getString(R.string.hands_score);
            final String cubesResult = getString(R.string.cubes_result);
            final String cubesScore = getString(R.string.cubes_score);
            final String finalScore = getString(R.string.final_score);
            StringBuilder sb = new StringBuilder();
            return sb.append(name).append(",").append(studentClass).append(",").append(aerobicScore)
                    .append(",").append(aerobicResult).append(",").append(absScore)
                    .append(",").append(absResult).append(",").append(handsScore).append(",")
                    .append(handsResult).append(",").append(cubesScore).append(",")
                    .append(cubesResult).append(",").append(jumpScore).append(",")
                    .append(jumpResult).append(",").append(finalScore).toString();
        }
    }

}
