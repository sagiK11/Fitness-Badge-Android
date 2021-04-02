package com.sagikor.android.fitracker.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sagikor.android.fitracker.R;
import com.sagikor.android.fitracker.ui.contracts.SignInActivityContract;
import com.sagikor.android.fitracker.ui.presenter.SignInActivityPresenter;
import com.sagikor.android.fitracker.utils.Utility;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity implements SignInActivityContract.View {

    Button loginButton;
    Button resetPasswordButton;
    TextView signUpButton;
    EditText userEmailEditText;
    EditText userPasswordEditText;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private static final String TAG = "WelcomeActivity";
    private SignInActivityContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().setTitle(R.string.login_screen);
        linkObjects();
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(presenter == null)
            presenter = new SignInActivityPresenter();
        presenter.bind(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        presenter.unbind();
    }

    private void linkObjects() {
        mAuth = FirebaseAuth.getInstance();
        loginButton = findViewById(R.id.login_button_welcome_activity);
        signUpButton = findViewById(R.id.sign_up_button_welcome_activity);
        userEmailEditText = findViewById(R.id.user_email_welcome_activity);
        userPasswordEditText = findViewById(R.id.user_password_welcome_activity);
        resetPasswordButton = findViewById(R.id.forgot_pass_button_welcome_activity);
        progressBar = findViewById(R.id.progressBar_welcome_activity);
        loginButton.setOnClickListener(e -> login());
        signUpButton.setOnClickListener(e -> signUp());
        resetPasswordButton.setOnClickListener(e -> resetPassword());

    }

    private void login() {
        progressBar.setVisibility(View.VISIBLE);
        String email = userEmailEditText.getText().toString().trim();
        String password = userPasswordEditText.getText().toString().trim();

        if (email.isEmpty()) {
            userEmailEditText.setError(getString(R.string.input_error));
            userEmailEditText.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }

        if (password.isEmpty()) {
            userPasswordEditText.setError(getString(R.string.input_error));
            userPasswordEditText.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }

        if (password.length() < Utility.PASS_LENGTH) {
            userPasswordEditText.setError(getString(R.string.input_error));
            userPasswordEditText.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Log.e(TAG, task.getException().toString());
                        final String AUTHEN_ERROR = getResources().getString(R.string.authentication_error);
                        Toast.makeText(getApplicationContext(), AUTHEN_ERROR,
                                Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void signUp() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void resetPassword() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = userEmailEditText.getText().toString().trim();
        String checkEMail = getResources().getString(R.string.check_your_email);
        String wrongEmail = getResources().getString(R.string.wrong_email);
        String fillEmailField = getResources().getString(R.string.fill_email_field);

        if (emailAddress.isEmpty()) {
            Toast.makeText(this, fillEmailField, Toast.LENGTH_LONG).show();
            return;
        }

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, checkEMail, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, wrongEmail, Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Moving to main activity if the user is already signed in.
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (isUserSigned(user)) {
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }
    }

    private boolean isUserSigned(FirebaseUser user) {
        return user != null;
    }
}
