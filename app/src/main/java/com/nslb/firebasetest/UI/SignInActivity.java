package com.nslb.firebasetest.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nslb.firebasetest.FirebaseMethodUserSettings;
import com.nslb.firebasetest.Interface.SignInListener;
import com.nslb.firebasetest.MainActivity;
import com.nslb.firebasetest.R;


public class SignInActivity extends AppCompatActivity implements View.OnClickListener, SignInListener {
    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mSignInButton;
    private Button mSignUpButton;
    public FirebaseMethodUserSettings firebaseMethodUserSettings;
    @Override
    protected void onCreate(Bundle savedInsanceState){
        super.onCreate(savedInsanceState);
        setContentView(R.layout.activity_sign_in);

        mEmailField = findViewById(R.id.fieldEmail);
        mPasswordField = findViewById(R.id.fieldPassword);
        mSignInButton = findViewById(R.id.buttonSignIn);
        mSignUpButton = findViewById(R.id.buttonSignUp);

        mSignInButton.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);



        firebaseMethodUserSettings= new FirebaseMethodUserSettings();
        firebaseMethodUserSettings.onSignInListener(this);
    }

    @Override
    public void onStart(){
        super.onStart();
        if (firebaseMethodUserSettings.CheckAuth()==1){
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonSignIn) {
            String Email = mEmailField.getText().toString();
            String Password = mPasswordField.getText().toString();
            if (!validateForm()){
                return;
            }
            firebaseMethodUserSettings.signIn(Email, Password);
        }else if (i == R.id.buttonSignUp) {
            String Email = mEmailField.getText().toString();
            String Password = mPasswordField.getText().toString();
            firebaseMethodUserSettings.signUp(Email,Password);
        }
    }
    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(mEmailField.getText().toString())) {
            mEmailField.setError("Required");
            result = false;
        } else {
            mEmailField.setError(null);
        }

        if (TextUtils.isEmpty(mPasswordField.getText().toString())) {
            mPasswordField.setError("Required");
            result = false;
        } else {
            mPasswordField.setError(null);
        }

        return result;
    }

    @Override
    public void SignSuccess() {
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish();
    }
    @Override
    public void SignFail(){
        Toast.makeText(SignInActivity.this, "Sign In Failed",
                Toast.LENGTH_SHORT).show();
    }
}
