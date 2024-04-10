package com.example.anonymousgrading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

// This will list all the students in a given class
public class ClassRosterActivity extends AppCompatActivity
{
    GradedClass gradedClass;

    String className;
    ListView listView;
    TextView classRosterTxt;

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

        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("className") != null)
        {
            className = bundle.getString("className");
        }

        DisplayClass();
    }

    public void DisplayClass()
    {
        ArrayList<String> names = new ArrayList<>();

        gradedClass = GradedClass.GenerateRandomClass(10);
        gradedClass.ClassName = className;

        classRosterTxt.setText(className);

        for(int i = 0; i < gradedClass.students.size(); i++)
        {
            String name = gradedClass.students.get(i).name;
            names.add(name);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, R.layout.activity_class_text, R.id.classNameTxt, names);

        listView.setAdapter(arrayAdapter);

    }
}