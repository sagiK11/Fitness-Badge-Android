package com.sagikor.android.fitracker.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sagikor.android.fitracker.R;
import com.sagikor.android.fitracker.ui.contracts.SignInActivityContract;
import com.sagikor.android.fitracker.ui.presenter.SignInActivityPresenter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity implements SignInActivityContract.View {

    Button loginButton;
    Button resetPasswordButton;
    TextView signUpButton;
    EditText userEmailEditText;
    EditText userPasswordEditText;
    ProgressBar progressBar;
    private static final String TAG = "WelcomeActivity";
    private SignInActivityContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        getSupportActionBar().setTitle(R.string.login_screen);
        bindViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter == null)
            presenter = new SignInActivityPresenter();
        presenter.bind(this);
        presenter.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbind();
    }


    @Override
    public void navToHomeScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void navToRegisterScreen() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
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
    public void setErrorInEmail() {
        setError(userPasswordEditText);
    }

    @Override
    public void setErrorInPassword() {
        setError(userEmailEditText);
    }

    @Override
    public String getUserEmail() {
        return userEmailEditText.getText().toString().trim();
    }

    @Override
    public String getUserPassword() {
        return userPasswordEditText.getText().toString().trim();
    }

    @Override
    public void popError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    public void popSignInFailureMessage() {
        final String error = getResources().getString(R.string.authentication_error);
        popError(error);
    }

    @Override
    public void notifyUserToCheckMail() {
        String checkEMail = getResources().getString(R.string.check_your_email);
        popError(checkEMail);
    }

    @Override
    public void notifyUserToFillEmail() {
        String fillEmailField = getResources().getString(R.string.fill_email_field);
        popError(fillEmailField);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void navSignedUserToHomeScreen() {
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void setError(EditText editText) {
        editText.setError(getString(R.string.input_error));
        editText.requestFocus();
        progressBar.setVisibility(View.GONE);
    }

    private void bindViews() {
        loginButton = findViewById(R.id.login_button_welcome_activity);
        signUpButton = findViewById(R.id.sign_up_button_welcome_activity);
        userEmailEditText = findViewById(R.id.user_email_welcome_activity);
        userPasswordEditText = findViewById(R.id.user_password_welcome_activity);
        resetPasswordButton = findViewById(R.id.forgot_pass_button_welcome_activity);
        progressBar = findViewById(R.id.progressBar_welcome_activity);
        loginButton.setOnClickListener(e -> presenter.onLoginClick());
        signUpButton.setOnClickListener(e -> presenter.onRegisterClick());
        resetPasswordButton.setOnClickListener(e -> presenter.onResetPasswordClick());
    }
}
