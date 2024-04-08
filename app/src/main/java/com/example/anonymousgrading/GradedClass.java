package com.example.anonymousgrading;

import java.net.URI;
import java.util.ArrayList;
import java.util.Random;

public class GradedClass
{
    String InstructorName;
    String ClassName;
    public ArrayList<Student> students = new ArrayList<>();

    public GradedClass(String className, String instrutorName)
    {
        this.InstructorName = instrutorName;
        this.ClassName = className;
    }

    public GradedClass()
    {
        this.InstructorName = "Default Instructor";
        this.ClassName = "Default Class";
    }

    public static GradedClass GenerateRandomClass(int studentCount)
    {
        String sName = "Student ";
        String className = "Computer Science ";
        String instructorName = "Nilan B";
        GradedClass gc = new GradedClass();

        int min = 100;
        int max = 1000;

        int n = (int) ((Math.random() * (max - min)) + min);

        gc.ClassName = className + n;
        gc.InstructorName = instructorName;

        for(int i = 0; i < studentCount; i++)
        {
            String name = sName + i;
            String id = "XX-" + i + "-00000";

            gc.AddStudent(name, id);
        }

        return  gc;
    }

    public void AddStudent(Student student)
    {
        students.add(student);
    }

    public void AddStudent(String name, String id)
    {
        Student student = new Student(name, id);
        students.add(student);
    }

    public void AddStudents(URI csvFile)
    {
        /// parse csv, and add names
    }

}
