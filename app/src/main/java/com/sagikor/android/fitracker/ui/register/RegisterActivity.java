package com.sagikor.android.fitracker.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.sagikor.android.fitracker.R;
import com.sagikor.android.fitracker.ui.home.MainActivity;

import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements RegisterActivityContract.View {

    private static final String TAG = "FirstActivity";
    EditText userFullNameEditText;
    EditText userEmailEditText;
    EditText userPasswordEditText;
    Button registerButton;
    ProgressBar progressBar;
    private RegisterActivityContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter == null)
            presenter = new RegisterActivityPresenter();
        presenter.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbind();
    }

    @Override
    public String getUserEmail() {
        return cleanText(userEmailEditText);
    }

    @Override
    public String getUserPassword() {
        return cleanText(userPasswordEditText);
    }

    @Override
    public String getUserName() {
        return cleanText(userFullNameEditText);
    }

    @Override
    public void navToHomeScreen() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void notifyErrorsToUser(Task<AuthResult> task) {
        try {
            throw task.getException();
        } catch (FirebaseAuthWeakPasswordException e) {
            userPasswordEditText.setError(getString(R.string.error_weak_password));
            userPasswordEditText.requestFocus();
        } catch (FirebaseAuthInvalidCredentialsException e) {
            userEmailEditText.setError(getString(R.string.error_invalid_email));
            userEmailEditText.requestFocus();
        } catch (FirebaseAuthUserCollisionException e) {
            userEmailEditText.setError(getString(R.string.error_user_exists));
            userEmailEditText.requestFocus();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setEmailError() {
        userEmailEditText.setError(getString(R.string.input_error));
        userEmailEditText.requestFocus();
    }

    @Override
    public void setPasswordError() {
        userPasswordEditText.setError(getString(R.string.input_error));
        userPasswordEditText.requestFocus();
    }

    @Override
    public void setWeakPassError() {
        userPasswordEditText.setError(getString(R.string.error_weak_password));
        userPasswordEditText.requestFocus();
    }

    @Override
    public void setNameError() {
        userFullNameEditText.setError(getString(R.string.input_error));
        userFullNameEditText.requestFocus();

    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private void bindViews() {
        userFullNameEditText = findViewById(R.id.full_user_name_input_text);
        userEmailEditText = findViewById(R.id.user_name_email_input_text);
        userPasswordEditText = findViewById(R.id.user_name_password_input_text);
        registerButton = findViewById(R.id.register_button);
        progressBar = findViewById(R.id.progressBar_register_activity);
        registerButton.setOnClickListener(e -> presenter.onRegisterClick());
        registerButton.getBackground().setAlpha(50);
    }

    private String cleanText(EditText editText) {
        return editText.getText().toString().trim();
    }

    @Override
    public void popMessage(String message,msgType type) {
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
        View contextView = findViewById(R.id.register_activity_root);
        Snackbar.make(contextView, message, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(backgroundColor)
                .show();
    }
}
