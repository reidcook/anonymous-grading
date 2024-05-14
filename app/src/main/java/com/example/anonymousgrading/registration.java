package com.example.anonymousgrading;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class registration extends AppCompatActivity implements View.OnClickListener
{

    private EditText username;
    private EditText password;

    private TextView regFailed;
    private EditText code;
    private Button registerButton;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        code = (EditText) findViewById(R.id.editTextCode);
        confirmButton = (Button) findViewById(R.id.buttonConfirm);
        registerButton = (Button) findViewById(R.id.buttonRegister);

        regFailed = (TextView) findViewById(R.id.regFailedTxt);

        registerButton.setOnClickListener(this);
        confirmButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        int g = Color.GREEN;
        int r = Color.RED;

        if (v.getId() == R.id.buttonRegister)
        {

            AuthState("", g, View.INVISIBLE);

            AuthSignUpOptions options = AuthSignUpOptions.builder().userAttribute(
                    AuthUserAttributeKey.email(), username.getText().toString()).build();

            String em = username.getText().toString();
            String pass = password.getText().toString();

            if (!isValidEmail(em))
            {
                String sd = "Bad Email Format";
                AuthState(sd, r, View.VISIBLE);

                return;
            }

            if(pass.length() < 5)
            {
                String sd = "Bad Password: Must be greater than 4";
                AuthState(sd, r, View.VISIBLE);
                return;
            }

            Amplify.Auth.signUp(em, pass, options,
                    result ->
                    {
                        Log.i("Amplify Register", result.toString());

                        String sd = "Check Email For confirmation code";
                        AuthState(sd, g, View.VISIBLE);
                        EnableConfirm(0);
                    },
                    error ->
                    {
                        String sd = "Authentication Failure: Check Inputs";
                        AuthState(sd, r, View.VISIBLE);
                    });

        }
        else
        {
            Amplify.Auth.confirmSignUp(username.getText().toString(),
                    code.getText().toString(),
                    result ->
                    {
                        Log.i("Amplify Confirm Registration", result.toString());
                        finishConfirm();
                    },
                    error ->
                    {
                        Log.e("Amplify Confirm Registration", error.toString());
                        String sd = "Wrong Confirmation Code";
                        AuthState(sd, r, View.VISIBLE);
                    });

        }
    }

    public static boolean isValidEmail(String email)
    {
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailPattern);
        if (email == null)
        {
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    void EnableConfirm(int num)
    {
        runOnUiThread(() -> {

            if(num == 0)
            {
                code.setVisibility(View.VISIBLE);
                confirmButton.setVisibility(View.VISIBLE);
                return;
            }

            code.setVisibility(View.INVISIBLE);
            confirmButton.setVisibility(View.INVISIBLE);

        });
    }

    void finishConfirm()
    {
        runOnUiThread(() -> {
            finish();
        });
    }

    void AuthState(String msg, int col, int v)
    {
        runOnUiThread(() -> {

            regFailed.setTextColor(col);
            regFailed.setText(msg);
            regFailed.setVisibility(v);
        });
    }
}