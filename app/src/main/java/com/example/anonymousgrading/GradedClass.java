package com.example.anonymousgrading;

import java.net.URI;
import java.util.ArrayList;

public class GradedClass
{
    String InstructorName;
    String className;
    public ArrayList<Student> students = new ArrayList<>();

    public GradedClass(String className, String instrutorName)
    {
        this.InstructorName = instrutorName;
        this.className = className;
    }

    public GradedClass()
    {
        this.InstructorName = "Default Instructor";
        this.className = "Default Class";
    }

    public static GradedClass GenerateRandomClass(int studentCount, String className)
    {
        String sName = "Student ";
        String instructorName = "Nilan B";
        GradedClass gc = new GradedClass();

        int min = 100;
        int max = 1000;

        int n = (int) ((Math.random() * (max - min)) + min);

        gc.className = className;
        gc.InstructorName = instructorName;

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

    public void AddStudents(URI csvFile)
    {
        /// parse csv, and add names
        // csv has been parsed, and students have been
        ArrayList<Student> theStudents;
    }

}
