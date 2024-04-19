package com.example.anonymousgrading;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.opencsv.CSVReader;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    }

    public String GetStudentInfo()
    {
        String str = "";

        str += name + "\n";
        str += "Id: " + Id.trim();

        return str;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }

        if (obj.getClass() != this.getClass())
        {
            return false;
        }

        Student other = (Student) obj;

        boolean sameName = name.equals(other.name);
        boolean sameID = Id.equals(other.Id);

        boolean sameCode = Arrays.equals(barcode, other.barcode);

        // dont compare grade because we might modify the grade when grading the exam
        return (sameName && sameID && sameCode);
    }

}
