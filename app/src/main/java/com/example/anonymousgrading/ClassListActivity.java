package com.example.anonymousgrading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.CoursesAWS;
import com.amplifyframework.datastore.generated.model.StudentAWS;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
//import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

// This will list all the classes an Instructor has to teach
public class ClassListActivity extends AppCompatActivity implements View.OnClickListener
{
    ArrayList<GradedClass> gradedClasses;
    ListView listView;

    Button newCourseBtn;


    public static String newClassExtra = "newClassExtra";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        listView = (ListView) findViewById(R.id.classListView);
        newCourseBtn = (Button) findViewById(R.id.newCourseBtn);

        newCourseBtn.setOnClickListener(this);

        GradedClass.ClearSharedPrefs(this);
        gradedClasses = new ArrayList<>();

        // the on resume class will be called from here
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        GetClassesFromDataBase();
    }

    private ArrayList<CoursesAWS> coursesAwsList = new ArrayList<>();
    private ArrayList<StudentAWS> studentAwsList = new ArrayList<>();
    String profName = "";
    private void queryCoursesAWS()
    {
        Amplify.API.query(
                ModelQuery.list(CoursesAWS.class),
                response -> {
                    for(CoursesAWS coursesAWS : response.getData())
                    {
                        coursesAwsList.add(coursesAWS);
                        Log.i("GraphQL", "CoursesAWS: " + coursesAWS.getCoursename());
                    }
                },
                error -> Log.e("GraphQL", "Query failure for CoursesAWS", error)
        );
    }

    private void queryStudentAWS()
    {
        Amplify.API.query(
                ModelQuery.list(StudentAWS.class),
                response -> {
                    for(StudentAWS studentAWS : response.getData())
                    {
                        studentAwsList.add(studentAWS);
                        Log.i("GraphQL", "StudentAWS: " + studentAWS.getName());
                    }
                },
                error -> Log.e("GraphQL", "Query failure for StudentAWS", error)
        );
    }

    boolean cDone = false;
    boolean sDone = false;
    private void GetClassesFromDataBase()
    {
        studentAwsList.clear();
        coursesAwsList.clear();

        Amplify.Auth.fetchUserAttributes(
                success -> {
                    profName = success.get(0).getValue().toString();
                },
                error2 -> {
                    Log.e("UserAuth", "error " + error2);
                }
        );

        Amplify.API.query(
                ModelQuery.list(CoursesAWS.class),
                response -> {
                    for(CoursesAWS coursesAWS : response.getData())
                    {
                        coursesAwsList.add(coursesAWS);
                        Log.i("GraphQL", "CoursesAWS data: " + coursesAWS.toString());
                    }

                    cDone = true;

                    LoadAfterQuery();
                },
                error -> Log.e("GraphQL", "Query failure", error)
        );


        // Fetch data from the StudentAWS table
        Amplify.API.query(
                ModelQuery.list(StudentAWS.class),
                response -> {
                    for(StudentAWS studentAWS : response.getData())
                    {
                        studentAwsList.add(studentAWS);
                        Log.i("GraphQL", "StudentAWS data: " + studentAWS.toString());
                    }

                    sDone = true;
                    LoadAfterQuery();
                },
                error -> Log.e("GraphQL", "Query failure", error)
        );
    }


    private void LoadAfterQuery()
    {
        if (cDone && sDone)
        {
            runOnUiThread(() ->
                    DisplayClasses_Query()
            );
        }
    }

    private void DisplayClasses_Query()
    {
        gradedClasses = new ArrayList<>();

        for(CoursesAWS c: coursesAwsList)
        {
            if(profName.equals(c.getProfessor()))
            {
                GradedClass g = new GradedClass(c.getCoursename());
                g.professor = c.getProfessor();

                gradedClasses.add(g);
            }
        }

        for(StudentAWS s: studentAwsList)
        {
            Student stu = Student.CreateStudent(s);

            for(GradedClass ga: gradedClasses)
            {
                if(ga.className.equals(s.getClassname()))
                {
                    if(ga.professor.equals(s.getProfessor()))
                    {
                        ga.AddStudent(stu);
                    }
                }
            }
        }

        DisplayClasses();
    }


    private void DisplayClasses()
    {
        ArrayList<String> names = new ArrayList<>();

        for(GradedClass gc : gradedClasses)
        {
            names.add(gc.className);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, R.layout.activity_class_text, R.id.classNameTxt, names);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
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
        if (v.getId() == R.id.newCourseBtn)
        {
            Intent myIntent = new Intent(ClassListActivity.this, AddCourseActivity.class);
            startActivity(myIntent);
        }
    }


}
