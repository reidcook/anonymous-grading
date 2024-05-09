package com.example.anonymousgrading;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.StudentAWS;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

// This will list all the students in a given class
public class ClassRosterActivity extends AppCompatActivity implements View.OnClickListener
{
    GradedClass gradedClass;

    String className;
    String primaryKey;
    String tempStudentId;
    String tempClass;
    String tempName;
    String tempProfessor;
    String tempBarcode;
    String tempExam;
    ListView listView;
    TextView classRosterTitleTxt;
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
                //qrResultText.setText(qrResult);
                String studentGrade = qrResult + " A";
                Amplify.API.query(
                        ModelQuery.list(StudentAWS.class, StudentAWS.STUDENT_ID.contains(qrResult).and(StudentAWS.CLASSNAME.contains(gradedClass.className))),
                        response ->{
                            for(StudentAWS tempStudent : response.getData()){
                                primaryKey = tempStudent.getId();
                                tempName = tempStudent.getName();
                                tempBarcode = tempStudent.getBarcode();
                                tempExam = tempStudent.getExam();
                                tempProfessor = tempStudent.getProfessor();
                                tempStudentId = tempStudent.getStudentId();
                                tempClass = tempStudent.getClassname();
                                Log.i("GraphQL", "response: " + tempStudent.getName());
                                Random rand = new Random();
                                int randGrade = rand.nextInt(101);
                                String randGradeString = String.valueOf(randGrade);
                                StudentAWS tempStu = StudentAWS.builder()
                                        .name(tempName)
                                        .studentId(tempStudentId)
                                        .professor(tempProfessor)
                                        .exam(tempExam)
                                        .barcode(tempBarcode)
                                        .grade(randGradeString)
                                        .classname(tempClass)
                                        .id(primaryKey)
                                        .build();
                                Amplify.API.mutate(
                                        ModelMutation.update(tempStu),
                                        response2 -> Log.i("GraphQL Update", "response: " + response2),
                                        error -> Log.e("GraphQL Update", "Error: " + error)
                                );
                            }
                        },
                        error -> Log.e("GraphQL", "error: " + error)
                );
                Log.i("tempName", "ttemp name is " + tempName);
                /*
                Random rand = new Random();
                int randGrade = rand.nextInt(101);
                String randGradeString = String.valueOf(randGrade);
                StudentAWS tempStu = StudentAWS.builder()
                        .name(tempName)
                        .studentId(tempStudentId)
                        .professor(tempProfessor)
                        .barcode(tempBarcode)
                        .exam(tempExam)
                        .grade("65")
                        .id(primaryKey)
                        .build();
                Amplify.API.mutate(
                        ModelMutation.update(tempStu),
                        response -> Log.i("GraphQL Update", "response: " + response),
                        error -> Log.e("GraphQL Update", "Error: " + error)
                );
                 */
            }
        }
    }
}