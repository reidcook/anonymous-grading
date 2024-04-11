package com.example.anonymousgrading;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class Student extends AppCompatActivity
{
    String name;
    String Id;
    int[] barcode = null;
    String grade;
    String className;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public Student(String name, String id, String className)
    {
        this.name = name;
        this.Id = id;
        this.className = className;
        prefs = getSharedPreferences(className, MODE_APPEND);
        editor = prefs.edit();
        editor.putString(name, id);
        editor.commit();
    }
}
