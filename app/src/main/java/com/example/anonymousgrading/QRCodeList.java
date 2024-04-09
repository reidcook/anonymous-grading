package com.example.anonymousgrading;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.List;

public class QRCodeList extends AppCompatActivity implements View.OnClickListener{

    private List<Student> students;
    private ListView listView_;
    private Button gradeExamsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_list);
        gradeExamsButton = (Button) findViewById(R.id.button);
        gradeExamsButton.setOnClickListener(this);
        Student student = new Student("Joe", "1234546");
        Student student2 = new Student("Bob", "653093");
        students = new ArrayList<Student>();
        students.add(student);
        students.add(student2);
        listView_ = (ListView) findViewById(R.id.qrListView);
        QRListAdapter adapter_ = new QRListAdapter(getApplicationContext(), students);
        listView_.setAdapter(adapter_);
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
        }
    }
}