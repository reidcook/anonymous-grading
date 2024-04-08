package com.example.anonymousgrading;

public class Student
{
    String name;
    String Id;
    int barcode[];
    String grade;
    public Student(String name, String id)
    {
        this.name = name;
        this.Id = id;
    }

    public static Student GenerateRandomStudent()
    {
        String sName = "Student - ";
        String sId = "XX-";

        int n = (int) ((Math.random() * (1000 - 100)) + 100);
        sName += n;

        int min = 100000;
        int max = 999999;

        n = (int) ((Math.random() * (max - min)) + min);
        sId += n;

        return  new Student(sName, sId);
    }
}
