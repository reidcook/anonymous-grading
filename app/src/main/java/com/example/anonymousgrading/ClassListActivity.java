package com.example.anonymousgrading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

// This will list all the classes an Instructor has to teach
public class ClassListActivity extends AppCompatActivity implements View.OnClickListener
{
    ArrayList<GradedClass> gradedClasses;
    ListView listView;

    Button newCourseBtn;


    public static  String newClassExtra = "newClassExtra";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        listView = (ListView) findViewById(R.id.classListView);
        newCourseBtn = (Button) findViewById(R.id.newCourseBtn);

        gradedClasses = GradedClass.GetSavedJson(getApplicationContext());

        Bundle bundle = getIntent().getExtras();
        // if we are opening this activty with a new class, we use this
        if(bundle != null)
        {
            String extra = bundle.getString(newClassExtra);

            if(extra != null)
            {
                Gson gson = new Gson();
                GradedClass cl = gson.fromJson(extra, GradedClass.class);

                gradedClasses.add(cl);

                GradedClass.SaveClasses(getApplicationContext(), gradedClasses);
            }
        }

        newCourseBtn.setOnClickListener(this);

//        for testing purposes only
//        if(gradedClasses.size() == 0)
//        {
//            gradedClasses = GradedClass.GenerateXClasses(5, 5);
//            SaveClasses();
//        }

       DisplayClasses();
    }

    private  void DisplayClasses()
    {
        ArrayList<String> names = new ArrayList<>();

        for (GradedClass gc: gradedClasses)
        {
            names.add(gc.className);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, R.layout.activity_class_text, R.id.classNameTxt, names);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent classIntent = new Intent(ClassListActivity.this, ClassRosterActivity.class);

                GradedClass gClass = gradedClasses.get(position);

                String json = GradedClass.SerializeClass(gClass);
                classIntent.putExtra(ClassRosterActivity.classRosterExtra, json);
                startActivity(classIntent);
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.newCourseBtn)
        {
            Intent myIntent = new Intent(ClassListActivity.this, AddCourseActivity.class);
            startActivity(myIntent);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        gradedClasses = GradedClass.GetSavedJson(getApplicationContext());

        DisplayClasses();

    }
}
