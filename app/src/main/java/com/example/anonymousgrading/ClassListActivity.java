package com.example.anonymousgrading;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

// This will list all the classes an Instructor has to teach
public class ClassListActivity extends AppCompatActivity
{

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        listView = (ListView) findViewById(R.id.classListView);

        ArrayList<GradedClass> c = GradedClass.GenerateXClasses(5,5);

        ArrayList<String> names = new ArrayList<>();

        for(int i = 0; i < c.size(); i++)
        {
            String name = c.get(i).ClassName;
            names.add(name);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, R.layout.activity_class_text, R.id.classNameTxt, names);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //Intent classIntent = new Intent()
            }
        });
    }


}