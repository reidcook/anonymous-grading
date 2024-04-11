package com.example.anonymousgrading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;
import java.util.List;

// This will list all the students in a given class
public class ClassRosterActivity extends AppCompatActivity implements View.OnClickListener
{
    GradedClass gradedClass;

    String className;
    ListView listView;
    TextView classRosterTxt;
    Button addExam;
    Button gradeExams;

    public ClassRosterActivity()
    {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_roster);

        listView = (ListView) findViewById(R.id.studentListView);
        classRosterTxt = (TextView) findViewById(R.id.classRosterNameTxt);
        addExam = (Button) findViewById(R.id.buttonAdd);
        gradeExams = (Button) findViewById(R.id.buttonGrade);

        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("className") != null)
        {
            className = bundle.getString("className");
        }
        addExam.setOnClickListener(this);
        gradeExams.setOnClickListener(this);
        DisplayClass();
    }

    public void DisplayClass()
    {
        ArrayList<Student> names = new ArrayList<Student>();

        gradedClass = GradedClass.GenerateRandomClass(10);
        gradedClass.ClassName = className;

        classRosterTxt.setText(className);

        for(int i = 0; i < gradedClass.students.size(); i++)
        {
            Student student = gradedClass.students.get(i);
            names.add(student);
        }
        QRListAdapter adapter_ = new QRListAdapter(getApplicationContext(), names);

        listView.setAdapter(adapter_);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonGrade){
            // start new intent
            IntentIntegrator intentIntegrator = new IntentIntegrator(ClassRosterActivity.this);
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.CODE_39);
            intentIntegrator.initiateScan();
        }
        else if (v.getId() == R.id.buttonAdd){
            // start new intent
            Intent myIntent = new Intent(ClassRosterActivity.this, AddExam.class);
            startActivity(myIntent);
        }
    }
}