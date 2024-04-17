package com.example.anonymousgrading;

import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.opencsv.CSVReader;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileReader;
import java.util.ArrayList;
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

    public static ArrayList<Student> GetStudents(Uri fileName)
    {
        // I will assume the format is name,id
        ArrayList<Student> students = new ArrayList<>();

        try
        {
            CSVReader reader = new CSVReader(new FileReader(fileName.toString()));
            String[] nextLine;
            Student newStudent;

            while ((nextLine = reader.readNext()) != null)
            {
                newStudent = new Student(nextLine[0], nextLine[1]);
                students.add(newStudent);
            }
        }
        catch (Exception e)
        {
            Log.d("Error", "Error reading file");
        }

        return  students;
    }
}
