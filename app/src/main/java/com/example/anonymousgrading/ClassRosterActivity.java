package com.example.anonymousgrading;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

// This will list all the students in a given class
public class ClassRosterActivity extends AppCompatActivity implements View.OnClickListener
{
    GradedClass gradedClass;

    String className;
    ListView listView;
    TextView classRosterTitleTxt;
    TextView qrResultText;
    Button addExam;
    Button gradeExams;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public  static  String classRosterExtra = "SerializedGradedClass";

    public ClassRosterActivity()
    {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_roster);

        listView = (ListView) findViewById(R.id.studentListView);
        classRosterTitleTxt = (TextView) findViewById(R.id.classRosterTitleTxt);
        addExam = (Button) findViewById(R.id.buttonAdd);
        gradeExams = (Button) findViewById(R.id.buttonGrade);
        qrResultText = (TextView) findViewById(R.id.textViewQRResult);

        Bundle bundle = getIntent().getExtras();

        String extra = bundle.getString(classRosterExtra);

        if(extra != null)
        {
            Gson gson = new Gson();
            gradedClass = gson.fromJson(extra, GradedClass.class);
        }

        addExam.setOnClickListener(this);
        gradeExams.setOnClickListener(this);

        DisplayClass();
    }

    public void DisplayClass()
    {
        ArrayList<String> studentInfo = new ArrayList<String>();
        ArrayList<String> ids = new ArrayList<String>();

        gradedClass.students.forEach(student ->
        {
            studentInfo.add(student.GetStudentInfo());
            ids.add(student.Id);
        });

        classRosterTitleTxt.setText(gradedClass.className);

        QRListAdapter adapter_ = new QRListAdapter(getApplicationContext(), studentInfo, ids);

        listView.setAdapter(adapter_);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonGrade){
            // start new intent
            IntentIntegrator intentIntegrator = new IntentIntegrator(ClassRosterActivity.this);
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.CODE_39);
            intentIntegrator.initiateScan();
        }
        else if (v.getId() == R.id.buttonAdd){
            // start new intent
            Intent myIntent = new Intent(ClassRosterActivity.this, AddExam.class);
            startActivity(myIntent);
        }
    }

    @Override
    protected  void onPause()
    {
        super.onPause();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(intentResult != null){
            String qrResult = intentResult.getContents();
            if(qrResult != null){
                // qrResult holds the qr code result which should be user id
                qrResultText.setText(qrResult);
                String studentGrade = qrResult + " A";
            }
        }
    }
}