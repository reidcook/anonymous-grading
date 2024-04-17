package com.example.anonymousgrading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class AddCourseActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText courseNameETxt;

    private Button uploadRosterBtn;
    private Button addCourseBtn;

    GradedClass newClass;

    ArrayList<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        courseNameETxt = (EditText) findViewById(R.id.courseRosterETxt) ;
        uploadRosterBtn = (Button) findViewById(R.id.uploadRosterBtn);

        addCourseBtn = (Button) findViewById(R.id.finishAddCourseBtn);

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

            newClass = new GradedClass(name);
            newClass.AddStudent(students);

            finish();
        }
        else if(v.getId() == R.id.uploadRosterBtn)
        {
            openFileChooser();
        }
    }

    private static final int FILE_PICKER_REQUEST_CODE = 50;
    private void openFileChooser()
    {
        // Create an intent to pick a file
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*.csv*"); // Set type to any file
        intent.addCategory(Intent.CATEGORY_OPENABLE); // Only files that can be opened

        // Start the file picker
        start(intent, FILE_PICKER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // Check the request code and result code
        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            // Get the URI of the selected file
            Uri fileUri = data.getData();

            // Handle the file URI as needed
            if (fileUri != null)
            {
                students = Student.GetStudents(fileUri);
            }
        }
    }

}