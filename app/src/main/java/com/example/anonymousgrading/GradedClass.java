package com.example.anonymousgrading;

import java.net.URI;
import java.util.ArrayList;

public class GradedClass
{
    String className;
    public ArrayList<Student> students = new ArrayList<>();

    public GradedClass(String className)
    {
        this.className = className;
    }

    public GradedClass()
    {
        this.className = "Default Class Name";
    }

    public static GradedClass GenerateRandomClass(int studentCount, String className)
    {
        String sName = "Student ";
        GradedClass gc = new GradedClass();

        int min = 100;
        int max = 1000;

        int n = (int) ((Math.random() * (max - min)) + min);

        gc.className = className;

        for(int i = 0; i < studentCount; i++)
        {
            String name = sName + i;
            String id = "XX-" + i + "-00000";

            gc.AddStudent(name, id, className);
        }

        return  gc;
    }

    public static  ArrayList<GradedClass> GenerateXClasses(int classCount, int studentCount)
    {
        ArrayList<GradedClass> classes = new ArrayList<>();

        for(int i = 0; i < classCount; i++)
        {
            classes.add(GenerateRandomClass(classCount, "Class " + i));
        }

        return classes;
    }

    public void AddStudent(Student student)
    {
        students.add(student);
    }

    public void AddStudent(String name, String id, String className)
    {
        Student student = new Student(name, id);
        students.add(student);
    }

    public void AddStudent(ArrayList<Student> students)
    {
        students.addAll(students);
    }

    public void GetStudent(URI csvFile)
    {
        /// parse csv, and add names
        // csv has been parsed, and students have been
        ArrayList<Student> theStudents;
    }

}
