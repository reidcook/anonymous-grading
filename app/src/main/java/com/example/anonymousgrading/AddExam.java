package com.example.anonymousgrading;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddExam extends AppCompatActivity implements View.OnClickListener{

    EditText examName;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam);
        examName = (EditText) findViewById(R.id.editTextText);
        submit = (Button) findViewById(R.id.button2);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // save exam with examName.getText();
        finish();
    }
}