package com.example.anonymousgrading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddCourseActivity extends AppCompatActivity implements View.OnClickListener
{

    private Button singleCourseBtn;
    private Button courseRsterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        singleCourseBtn = (Button) findViewById(R.id.singleCourseBtn);
        courseRsterBtn = (Button) findViewById(R.id.courseRsterBtn);

        singleCourseBtn.setOnClickListener(this);
        courseRsterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.singleCourseBtn)
        {
            finish();
        }
        else if (v.getId() == R.id.courseRsterBtn)
        {
            finish();
        }
    }
}