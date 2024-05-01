package com.example.anonymousgrading;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

public class registration extends AppCompatActivity implements View.OnClickListener{

    private EditText username;
    private EditText password;
    private EditText code;
    private Button registerButton;
    private Button confirmButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        code = (EditText) findViewById(R.id.editTextCode);
        confirmButton = (Button) findViewById(R.id.buttonConfirm);
        registerButton = (Button) findViewById(R.id.buttonRegister);
        registerButton.setOnClickListener(this);
        confirmButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonRegister) {
            code.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.VISIBLE);
            AuthSignUpOptions options = AuthSignUpOptions.builder().userAttribute(
                    AuthUserAttributeKey.email(), username.getText().toString()).build();
            Amplify.Auth.signUp(username.getText().toString(), password.getText().toString(), options,
                    result -> Log.i("Amplify Register", result.toString()),
                    error -> Log.e("Amplify Register", error.toString()));
        }
        else{
            Amplify.Auth.confirmSignUp(username.getText().toString(),
                    code.getText().toString(),
                    result -> Log.i("Amplify Confirm Registration", result.toString()),
                    error -> Log.e("Amplify Confirm Registration", error.toString()));
            finish();
        }
    }
}