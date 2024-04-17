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
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        listView = (ListView) findViewById(R.id.classListView);
        newCourseBtn = (Button) findViewById(R.id.newCourseBtn);

        GetSavedJson();

        newCourseBtn.setOnClickListener(this);

        if(gradedClasses.size() == 0)
        {
            gradedClasses = GradedClass.GenerateXClasses(5, 5);
            SaveClasses();
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
                Intent classIntent = new Intent(ClassListActivity.this, ClassRosterActivity.class);

                GradedClass gClass = gradedClasses.get(position);

                String json = SerializeClass(gClass);
                classIntent.putExtra(ClassRosterActivity.classRosterExtra, json);
                startActivity(classIntent);
            }
        });
    }

    public static String classPref = "Classes";
    private void SaveClasses()
    {
        // Saves the Graded class list.
        // This is done by serialzing the gradedclass list into a string and saving said string
        SharedPreferences mPrefs = getSharedPreferences(classPref, MODE_PRIVATE);
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
    private void GetSavedJson()
    {
        // we get from shared pref a json which is a list of graded classes
        prefs = getSharedPreferences(classPref, MODE_PRIVATE);

        Gson gson = new Gson();
        String json = prefs.getString(classPref, "");
        Map<String,?> keys = prefs.getAll();

        if(json == "")
        {
            gradedClasses =  new ArrayList<>();
        }
        else
        {
            gradedClasses = DeserializeClassList(json);
        }

    }
    public static String SerializeClass(GradedClass gradedClass)
    {
        Gson gson = new Gson();
        return gson.toJson(gradedClass);
    }
    public static String SerializeClass(ArrayList<GradedClass> gradedClasses)
    {
        Gson gson = new Gson();
        return gson.toJson(gradedClasses);
    }
    public static GradedClass DeserializeClass(String gradedClassJson)
    {
        Gson gson = new Gson();
        return gson.fromJson(gradedClassJson, GradedClass.class);
    }
    public static ArrayList<GradedClass> DeserializeClassList(String classListJson)
    {
        Gson gson = new Gson();
        ArrayList<GradedClass> gradedClasses = new ArrayList<>();
        Type type = new TypeToken<ArrayList<GradedClass>>() {}.getType();
        gradedClasses = gson.fromJson(classListJson, type);

        return gradedClasses;
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
}
