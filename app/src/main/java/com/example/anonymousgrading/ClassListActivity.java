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
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
//        editor.putString("Algebra", "Algebra");
//        editor.putString("Biology", "Biology");
//        editor.putString("Calculus", "Calculus");
//        editor.putString("Mobile Computing", "Mobile Computing");
//        editor.putString("History", "History");
//        editor.commit();
//        prefs = getSharedPreferences("Algebra", MODE_APPEND);
//        editor =  prefs.edit();
//        editor.putString("Tom", "123456");
//        editor.commit();
//        prefs = getSharedPreferences("Biology", MODE_APPEND);
//        editor =  prefs.edit();
//        editor.putString("Sarah", "111111");
//        editor.commit();
//        prefs = getSharedPreferences("Calculus", MODE_APPEND);
//        editor =  prefs.edit();
//        editor.putString("Eric", "222226");
//        editor.commit();
//        prefs = getSharedPreferences("Mobile Computing", MODE_APPEND);
//        editor =  prefs.edit();
//        editor.putString("Emily", "777777");
//        editor.commit();
//        prefs = getSharedPreferences("History", MODE_APPEND);
//        editor =  prefs.edit();
//        editor.putString("Bob", "999999");
//        editor.commit();
        GetSaveJson();

        if(gradedClasses.size() == 0)
        {
            gradedClasses = GradedClass.GenerateXClasses(5, 5);
            SaveJson();
        }

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
                String theName = names.get(position);

                Intent classIntent = new Intent(ClassListActivity.this, ClassRosterActivity.class);

                classIntent.putExtra("className", theName);
                startActivity(classIntent);
            }
        });
    }

    private static String classPref = "Classes";
    private void SaveJson()
    {
        // Saves the Graded class list.
        // This is done by serialzing the gradedclass list into a string and saving said string
        SharedPreferences mPrefs = getSharedPreferences(classPref, Context.MODE_APPEND);
        SharedPreferences.Editor editor = mPrefs.edit();
        Gson gson = new Gson();

        String json = "";
        String ms = "";
        try
        {
            json =  gson.toJson(gradedClasses).toString();
        }
        catch(Exception e)
        {
            ms = e.getMessage();
            Log.d("Error", e.getMessage());
        }

        editor.remove(classPref).commit();
        editor.putString(classPref, json);
        editor.commit();

    }

    private void GetSaveJson()
    {
        // we get from shared pref a json which is a list of graded classes
        Gson gson = new Gson();
        prefs = getSharedPreferences(classPref, MODE_APPEND);
        ArrayList<String> classNames = new ArrayList<>();
        String json = prefs.getString(classPref, "");
        Map<String,?> keys = prefs.getAll();

        if(json == "")
        {
            gradedClasses =  new ArrayList<>();
        }
        else
        {
            Type type = new TypeToken<ArrayList<GradedClass>>() {}.getType();
            gradedClasses = gson.fromJson(json, type);
        }

    }

}
