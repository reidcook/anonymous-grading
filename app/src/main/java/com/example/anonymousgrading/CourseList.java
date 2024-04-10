package com.example.anonymousgrading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class CourseList extends AppCompatActivity {
    private String[] classNames = {"Class1", "Class2"};
    private ListView listView_;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        listView_ = (ListView) findViewById(R.id.classListView);
        CustomAdapter adapter_ = new CustomAdapter(getApplicationContext(), classNames);
        listView_.setAdapter(adapter_);

        listView_.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // clicked on item: + fruitnames[position]
                Intent myIntent = new Intent(CourseList.this, QRCodeList.class);
                // passing which class was clicked on to text activity
                myIntent.putExtra("ClassName", classNames[position]);
                startActivity(myIntent);
            }
        });
    }
}