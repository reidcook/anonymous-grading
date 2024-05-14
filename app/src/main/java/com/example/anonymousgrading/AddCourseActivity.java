package com.example.anonymousgrading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.CoursesAWS;
import com.amplifyframework.datastore.generated.model.StudentAWS;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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
    Student currStu;
    int i;

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

            // CODE WITH DATABASE
            // saves prof name and course name into CoursesAWS
            Amplify.Auth.fetchUserAttributes(
                    success -> {
                        CoursesAWS coursesaws = CoursesAWS.builder().coursename(name).professor(success.get(0).getValue().toString())
                                .build();
                        Amplify.API.mutate(
                                ModelMutation.create(coursesaws),
                                response -> Log.i("GraphQL", "response: " + response),
                                error1 -> Log.e("GraphQL", "error: " + error1)
                        );
                    },
                    error2 -> {
                        Log.e("UserAuth", "error " + error2);
                    }
            );
            // saves student names, ids, classname, and professor into StudentAWS table
            for(Student student: students) {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                String temp = "";
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(StringUtils.chop(student.Id), BarcodeFormat.CODE_39, 600, 300);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    ByteArrayOutputStream baos=new  ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
                    byte [] b=baos.toByteArray();
                    temp = Base64.encodeToString(b, Base64.DEFAULT);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                student.barcode = temp;
                Log.i("string test", temp);
            }
            for (Student student: students) {
                    Amplify.Auth.fetchUserAttributes(
                            success -> {
                                Log.i("UserAuth", "i index: " + students.size());
                                StudentAWS studentaws = StudentAWS.builder()
                                        .name(student.name)
                                        .studentId(StringUtils.chop(student.Id))
                                        .professor(success.get(0).getValue().toString())
                                        .exam("")
                                        .barcode(student.barcode)
                                        .grade("")
                                        .classname(name)
                                        .build();
                                Amplify.API.mutate(
                                        ModelMutation.create(studentaws),
                                        response -> Log.i("GraphQL", "response: " + response),
                                        error1 -> Log.e("GraphQL", "error: " + error1)
                                );
                            },
                            error2 -> {
                                Log.e("UserAuth", "error " + error2);
                            }
                    );
            }
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