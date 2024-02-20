package com.example.anonymousgrading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText username;
    private EditText password;
    private Button submitButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.editTextUsername);
        submitButton = (Button) findViewById(R.id.buttonLogin);
        password = (EditText) findViewById(R.id.editTextPassword);
        registerButton = (Button) findViewById(R.id.buttonRegister);
        submitButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonLogin) {
            Intent myIntent = new Intent(MainActivity.this, MainActivity2.class);
            String user = username.getText().toString();
            String pass = password.getText().toString();
            myIntent.putExtra("user", user);
            myIntent.putExtra("pass", pass);
            startActivity(myIntent);
        }
        else if(v.getId() == R.id.buttonRegister){
            Intent myIntent = new Intent(MainActivity.this, registration.class);
            startActivity(myIntent);
        }
    }
}