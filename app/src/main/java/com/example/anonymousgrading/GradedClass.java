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

    public static GradedClass GenerateRandomClass(int studentCount, String className)
    {
        String sName = "Student ";
        String instructorName = "Nilan B";
        GradedClass gc = new GradedClass();

        int min = 100;
        int max = 1000;

        int n = (int) ((Math.random() * (max - min)) + min);

        gc.ClassName = className;
        gc.InstructorName = instructorName;

        for(int i = 0; i < studentCount; i++)
        {
            String name = sName + i;
            String id = "XX-" + i + "-00000";

            gc.AddStudent(name, id, className);
        }

        return  gc;
    }

    public static  ArrayList<GradedClass> GenerateXClasses(int classCount, int studentCount, String className)
    {
        ArrayList<GradedClass> classes = new ArrayList<>();

        for(int i = 0; i < classCount; i++)
        {
            classes.add(GenerateRandomClass(classCount, className));
        }

        return classes;
    }

    public void AddStudent(Student student)
    {
        students.add(student);
    }

    public void AddStudent(String name, String id, String className)
    {
        Student student = new Student(name, id, className);
        students.add(student);
    }

    public void AddStudents(URI csvFile)
    {
        /// parse csv, and add names
        // csv has been parsed, and students have been
        ArrayList<Student> theStudents;
    }

}
