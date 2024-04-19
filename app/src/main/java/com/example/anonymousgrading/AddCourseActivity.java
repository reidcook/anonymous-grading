package com.example.anonymousgrading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddCourseActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText courseNameETxt;

    private Button uploadRosterBtn;
    private Button addCourseBtn;

    private TextView uploadResultTxt;
    private TextView addCourseErrorTxt;

    GradedClass newClass;

    ArrayList<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        courseNameETxt = (EditText) findViewById(R.id.courseRosterETxt);
        uploadRosterBtn = (Button) findViewById(R.id.uploadRosterBtn);

        addCourseBtn = (Button) findViewById(R.id.finishAddCourseBtn);

        uploadResultTxt = (TextView) findViewById(R.id.uploadResultTxt);

        addCourseErrorTxt = (TextView) findViewById(R.id.addCourseErrorText);

        addCourseBtn.setOnClickListener(this);
        uploadRosterBtn.setOnClickListener(this);

        students = new ArrayList<>();

    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.finishAddCourseBtn)
        {
            String name = courseNameETxt.getText().toString();
            String error;

            if(name.isEmpty())
            {
                error = "Please Enter a Course Name";
                addCourseErrorTxt.setText(error);
                addCourseErrorTxt.setTextColor(Color.RED);
                return;
            }

            if(students.size() == 0)
            {
                error = "Please Upload a Student Roster";
                addCourseErrorTxt.setText(error);
                addCourseErrorTxt.setTextColor(Color.RED);
                return;
            }

            newClass = new GradedClass(name);
            newClass.AddStudent(students);

            // reset after click..just cuz
            error = "";
            addCourseErrorTxt.setText(error);
            addCourseErrorTxt.setTextColor(Color.RED);

            GradedClass.InsertClass(getApplicationContext(), newClass);

//            String data = GradedClass.SerializeClass(newClass);
//            Intent myIntent = new Intent(AddCourseActivity.this, ClassListActivity.class);
//
//            myIntent.putExtra(ClassListActivity.newClassExtra, data);
//
//            startActivity(myIntent);
            finish();
        }
        else if (v.getId() == R.id.uploadRosterBtn)
        {
            openFileChooser();
        }
    }

    private static final int FILE_PICKER_REQUEST_CODE = 50;

    private void openFileChooser()
    {
        // Create an intent to pick a file
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*"); // Set type to any file
        intent.addCategory(Intent.CATEGORY_OPENABLE); // Only files that can be opened

        // Start the file picker
        startActivityForResult(intent, FILE_PICKER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // Check the request code and result code
        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK)
        {
            // Get the URI of the selected file
            Uri fileUri = data.getData();

            // Handle the file URI as needed
            if (fileUri != null)
            {
                String result;

                try
                {
                    ArrayList<Student> datas = readTextFromUri(fileUri);

                     result = "Extracted " + datas.size() + " Students";
                    uploadResultTxt.setTextColor(Color.GREEN);
                    uploadResultTxt.setText(result);

                    students = datas;

                    int sd = 23;
                }
                catch(Exception e)
                {
                    result = "Error Parsing File. Must be of Format {Name, Id}";
                    uploadResultTxt.setTextColor(Color.RED);
                    uploadResultTxt.setText(result);

                    throw new RuntimeException(e);
                }
                //students = Student.GetStudents(fileUri);
            }
        }
    }

    public ArrayList<String> readLinesFromFile(Uri fileUri)
    {
        ArrayList<String> lines = new ArrayList<>();

        try
        {
            // open the file for reading
            InputStream instream = Files.newInputStream(Paths.get(String.valueOf(fileUri)));

            // if file the available for reading
            if (instream != null)
            {
                // prepare the file for reading
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);

                String line;

                // read every line of the file into the line-variable, on line at the time
                do
                {
                    line = buffreader.readLine();
                    // do something with the line
                }
                while(line != null);

                instream.close();

            }
        }
        catch(Exception ex)
        {
            Log.d("Error", ex.getMessage());
        }
        finally
        {

        }

        return lines;
    }

    private ArrayList<Student> readTextFromUri(Uri uri) throws Exception
    {
        ArrayList<Student> students = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();
        try(InputStream inputStream =
                    getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(Objects.requireNonNull(inputStream))))
        {
            String line;
            Student newStudent;

            while((line = reader.readLine()) != null)
            {
                String[] split = line.split(",");

                // improper file format
                if(split.length != 2)
                {
                    throw new Exception();
                }

                newStudent = new Student(split[0], split[1]);
                students.add(newStudent);
            }
        }

        return students;
    }

}