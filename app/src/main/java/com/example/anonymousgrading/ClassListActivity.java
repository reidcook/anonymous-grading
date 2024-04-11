package com.example.anonymousgrading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

// This will list all the classes an Instructor has to teach
public class ClassListActivity extends AppCompatActivity
{
    ArrayList<GradedClass> gradedClasses;
    ListView listView;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        listView = (ListView) findViewById(R.id.classListView);

        // These insert data as shared prefrences. After ran once, will be saved on machine
/*
        prefs = getSharedPreferences("Classes", MODE_APPEND);
        editor =  prefs.edit();
        editor.clear().commit();
        editor.putString("Algebra", "Algebra");
        editor.putString("Biology", "Biology");
        editor.putString("Calculus", "Calculus");
        editor.putString("Mobile Computing", "Mobile Computing");
        editor.putString("History", "History");
        editor.commit();
        prefs = getSharedPreferences("Algebra", MODE_APPEND);
        editor =  prefs.edit();
        editor.putString("Tom", "123456");
        editor.commit();
        prefs = getSharedPreferences("Biology", MODE_APPEND);
        editor =  prefs.edit();
        editor.putString("Sarah", "111111");
        editor.commit();
        prefs = getSharedPreferences("Calculus", MODE_APPEND);
        editor =  prefs.edit();
        editor.putString("Eric", "222226");
        editor.commit();
        prefs = getSharedPreferences("Mobile Computing", MODE_APPEND);
        editor =  prefs.edit();
        editor.putString("Emily", "777777");
        editor.commit();
        prefs = getSharedPreferences("History", MODE_APPEND);
        editor =  prefs.edit();
        editor.putString("Bob", "999999");
        editor.commit();
*/
        prefs = getSharedPreferences("Classes", MODE_APPEND);
        ArrayList<String> names = new ArrayList<>();
        Map<String,?> keys = prefs.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.d("map values",entry.getKey() + ": " + entry.getValue().toString());
            names.add(entry.getValue().toString());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, R.layout.activity_class_text, R.id.classNameTxt, names);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String theName = names.get(position);

                Intent classIntent = new Intent(ClassListActivity.this, ClassRosterActivity.class);

                classIntent.putExtra("className", theName);
                startActivity(classIntent);
            }
        });


    }

}