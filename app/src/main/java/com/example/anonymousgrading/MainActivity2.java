package com.example.anonymousgrading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener
{

    private TextView user;
    private Button addCouseButton;
    private Button uploadClassRosterBtn;
    private Button addExamButton;
    private Button viewBarcodesButton;
    private Button gradeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        uploadClassRosterBtn = (Button) findViewById(R.id.uploadCourseRsterBtn);
        addCouseButton = (Button) findViewById(R.id.buttonAddCourse);
        addExamButton = (Button) findViewById(R.id.buttonAddExam);
        viewBarcodesButton = (Button) findViewById(R.id.buttonViewBarcodes);
        gradeButton = (Button) findViewById(R.id.buttonGrade);
        user = (TextView) findViewById(R.id.textViewWelcome);
        String theMessage = "Welcome " + getIntent().getStringExtra("user");
        user.setText(theMessage);
        uploadClassRosterBtn.setOnClickListener(this);
        addCouseButton.setOnClickListener(this);
        addExamButton.setOnClickListener(this);
        viewBarcodesButton.setOnClickListener(this);
        gradeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.uploadCourseRsterBtn)
        {
            finish();
        }

        if (v.getId() == R.id.buttonAddCourse)
        {
            finish();
        }
        else if (v.getId() == R.id.buttonAddExam)
        {
            finish();
        }
        else if (v.getId() == R.id.buttonViewBarcodes)
        {
            finish();
        }
        else if (v.getId() == R.id.buttonGrade)
        {
            Intent myIntent = new Intent(MainActivity2.this, scanqr.class);
            startActivity(myIntent);
        }
    }
}