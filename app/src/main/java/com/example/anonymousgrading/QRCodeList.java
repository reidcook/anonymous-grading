package com.example.anonymousgrading;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCodeList extends AppCompatActivity {

    private Student[] students;
    private ImageView qrCode;
    private ListView listView_;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_list);
        Student student = new Student("Joe", "1234546");
        Student student2 = new Student("Bob", "653093");
        students = new Student[2];
        students[0] = student;
        students[1] = student2;
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
}