package com.example.anonymousgrading;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class QRCodeList extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<Student> students;
    private ListView listView_;
    private Button gradeExamsButton;
    private Button addExamButton;
    private TextView result;
    private TextView className;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_list);
        gradeExamsButton = (Button) findViewById(R.id.button);
        result = (TextView) findViewById(R.id.textView4);
        className = (TextView) findViewById(R.id.textView5);
        addExamButton = (Button) findViewById(R.id.buttonAdd);
        className.setText(getIntent().getStringExtra("ClassName"));
        gradeExamsButton.setOnClickListener(this);
        addExamButton.setOnClickListener(this);
        //Student student = new Student("Joe", "1234546", "class1");
        //Student student2 = new Student("Bob", "653093", "class1");
        //students = new ArrayList<Student>();
        //students.add(student);
        //students.add(student2);
        //listView_ = (ListView) findViewById(R.id.qrListView);
        //QRListAdapter adapter_ = new QRListAdapter(getApplicationContext(), students);
        //listView_.setAdapter(adapter_);
        listView_.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // clicked on item: + fruitnames[position]
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button){
            // start new intent
            IntentIntegrator intentIntegrator = new IntentIntegrator(QRCodeList.this);
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.CODE_39);
            intentIntegrator.initiateScan();
        }
        if (v.getId() == R.id.buttonAdd){
            // start new intent
            Intent myIntent = new Intent(QRCodeList.this, AddExam.class);
            startActivity(myIntent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(intentResult != null){
            // something got scanned
            String scanned = intentResult.getContents();
            if(scanned != null){
                //text found
                result.setText(scanned);
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}