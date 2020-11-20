package com.sagikor.android.fitracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "FirstActivity";
    EditText userFullNameEditText;
    EditText userEmailEditText;
    EditText userPasswordEditText;
    Button registerButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        mAuth = FirebaseAuth.getInstance();
        linkObjects();
    }


    private void linkObjects() {
        userFullNameEditText = findViewById( R.id.full_user_name_input_text );
        userEmailEditText = findViewById( R.id.user_name_email_input_text );
        userPasswordEditText = findViewById( R.id.user_name_password_input_text );
        registerButton = findViewById( R.id.register_button );
        progressBar = findViewById( R.id.progressBar_register_activity );
        registerButton.setOnClickListener( e -> registerUser() );

    }

    private void registerUser() {
        progressBar.setVisibility( View.VISIBLE );
        String email = userEmailEditText.getText().toString().trim();
        String password = userPasswordEditText.getText().toString().trim();
        if ( inputErrors() ) {
            progressBar.setVisibility( View.GONE );
            return;
        }
        mAuth.createUserWithEmailAndPassword( email, password )
                .addOnCompleteListener( this, task -> {
                    if ( task.isSuccessful() ) {
                        progressBar.setVisibility( View.GONE );
                        String userId = mAuth.getCurrentUser().getUid();
                        User newUser = new User( userFullNameEditText.getText().toString(), email, userId );

                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference( "users" );
                        dbRef.child( userId ).setValue( newUser ).addOnCompleteListener( task1 -> {
                            if ( task1.isSuccessful() ) {
                                openMainActivity();
                            }
                        } );
                    } else {
                        notifyErrorsToUser( task );
                    }
                    progressBar.setVisibility( View.GONE );
                } );

    }

    private void openMainActivity() {
        Intent intent = new Intent( RegisterActivity.this, MainActivity.class );
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity( intent );
    }

    private void notifyErrorsToUser( Task<AuthResult> task ) {
        try {
            throw task.getException();
        } catch ( FirebaseAuthWeakPasswordException e ) {
            userPasswordEditText.setError( getString( R.string.error_weak_password ) );
            userPasswordEditText.requestFocus();
        } catch ( FirebaseAuthInvalidCredentialsException e ) {
            userEmailEditText.setError( getString( R.string.error_invalid_email ) );
            userEmailEditText.requestFocus();
        } catch ( FirebaseAuthUserCollisionException e ) {
            userEmailEditText.setError( getString( R.string.error_user_exists ) );
            userEmailEditText.requestFocus();
        } catch ( Exception e ) {
            Log.e( TAG, e.getMessage() );
            progressBar.setVisibility( View.GONE );
        }
    }

    private boolean inputErrors() {
        String userFullName = userFullNameEditText.getText().toString().trim();
        String email = userEmailEditText.getText().toString().trim();
        String password = userPasswordEditText.getText().toString().trim();

        if ( userFullName.isEmpty() ) {
            userFullNameEditText.setError( getString( R.string.input_error ) );
            userFullNameEditText.requestFocus();
            return true;
        }

        if ( email.isEmpty() ) {
            userEmailEditText.setError( getString( R.string.input_error ) );
            userEmailEditText.requestFocus();
            return true;
        }

        if ( password.isEmpty() ) {
            userPasswordEditText.setError( getString( R.string.input_error ) );
            userPasswordEditText.requestFocus();
            return true;
        }

        if ( password.length() < Utility.PASS_LENGTH ) {
            userPasswordEditText.setError( getString( R.string.error_weak_password ) );
            userPasswordEditText.requestFocus();
            return true;
        }
        return false;
    }

}
