package com.example.anonymousgrading;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class Student
{
    String name;
    String Id;
    int[] barcode;
    String grade;

    public Student(String name, String id)
    {
        this.name = name;
        this.Id = id;

//        prefs = getSharedPreferences(className, MODE_APPEND);
//        editor = prefs.edit();
//        editor.putString(name, id);
//        editor.commit();
    }
}
