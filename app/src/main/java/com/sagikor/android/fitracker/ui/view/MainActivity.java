package com.sagikor.android.fitracker.ui.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sagikor.android.fitracker.R;
import com.sagikor.android.fitracker.data.Student;
import com.sagikor.android.fitracker.data.User;
import com.sagikor.android.fitracker.ui.presenter.MainActivityContract;
import com.sagikor.android.fitracker.ui.presenter.MainActivityPresenter;
import com.sagikor.android.fitracker.utils.EmailSendTask;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    Button addStudentButton;
    Button searchStudentsButton;
    Button mailResultsButton;
    Button updateStudentButton;
    Toolbar toolbar;
    public static User currentUser = new User();
    MainActivityPresenter presenter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        addButtonsOnClickListeners();
        initFirebase();
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
        startActivity(new Intent(this, SettingActivity.class));
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
            EmailSendTask task = new EmailSendTask(this);
            task.execute();
        }

    }

    @Override
    public void disconnectUser() {
        signOutQuestionPop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(presenter == null)
            presenter = new MainActivityPresenter();
        presenter.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbind();
    }


    private void bindViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addStudentButton = findViewById(R.id.button_add_student);
        searchStudentsButton = findViewById(R.id.button_search);
        mailResultsButton = findViewById(R.id.button_mail_results);
        updateStudentButton = findViewById(R.id.button_update_student);
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
            navToStatisticsScreen();
        } else if (id == R.id.nav_settings) {
            navToSettingsScreen();
        } else if (id == R.id.nav_sign_out) {
            disconnectUser();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addButtonsOnClickListeners() {
        addStudentButton.setOnClickListener(e -> presenter.onNavToAddStudentClick());
        searchStudentsButton.setOnClickListener(e -> presenter.onNavToViewStudentsClick());
        mailResultsButton.setOnClickListener(e -> presenter.onSendToEmailClick());
        updateStudentButton.setOnClickListener(e -> presenter.onNavToUpdateStudentClick());
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
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}
