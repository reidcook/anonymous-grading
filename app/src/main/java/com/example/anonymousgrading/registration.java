package com.example.anonymousgrading;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class registration extends AppCompatActivity implements View.OnClickListener{

    private EditText username;
    private EditText password;
    private Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        registerButton = (Button) findViewById(R.id.buttonRegister);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}