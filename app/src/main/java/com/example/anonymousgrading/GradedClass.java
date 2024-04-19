package com.example.anonymousgrading;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

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
        this.students.addAll(students);
    }

    public void GetStudent(URI csvFile)
    {
        /// parse csv, and add names
        // csv has been parsed, and students have been
        ArrayList<Student> theStudents;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        GradedClass other = (GradedClass) obj;

        boolean sameName = className.equals(other.className);
        boolean sameStudent = Arrays.equals(students.toArray(), other.students.toArray());

        return  (sameName && sameStudent);
    }

    private static String classPref = "Classes";
    public static void SaveClasses(Context context, ArrayList<GradedClass> gradedClasses)
    {
        // Saves the Graded class list.
        // This is done by serialzing the gradedclass list into a string and saving said string
        SharedPreferences mPrefs = context.getSharedPreferences(classPref, MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        Gson gson = new Gson();

        String json = "";
        String ms = "";

        try
        {
            json =  gson.toJson(gradedClasses).toString();
        }
        catch(Exception e)
        {
            ms = e.getMessage();
            Log.d("Error", e.getMessage());
        }

        editor.remove(classPref).commit();
        editor.putString(classPref, json);
        editor.commit();

    }

    // This will Insert a class into the saved json.
    // If the class already exists, it will replace it
    // Use this to update a class after you have made changes to it
    public  static  void InsertClass(Context context, GradedClass class2Update)
    {
        ArrayList<GradedClass> gradedClasses = new ArrayList<>();

        gradedClasses = GetSavedJson(context);

        int index = gradedClasses.indexOf(class2Update);

        if(index != -1)
        {
            gradedClasses.remove(index);
        }

        gradedClasses.add(class2Update);

        SaveClasses(context, gradedClasses);
    }
    public static ArrayList<GradedClass> GetSavedJson(Context context)
    {
        ArrayList<GradedClass> gradedClasses;
        // we get from shared pref a json which is a list of graded classes
        SharedPreferences prefs = context.getSharedPreferences(classPref, MODE_PRIVATE);

        Gson gson = new Gson();
        String json = prefs.getString(classPref, "");
        Map<String,?> keys = prefs.getAll();

        if(json == "")
        {
            gradedClasses =  new ArrayList<>();
        }
        else
        {
            gradedClasses = DeserializeClassList(json);
        }

        return  gradedClasses;
    }
    public static void ClearSharedPrefs(Context context)
    {
        SharedPreferences mPrefs = context.getSharedPreferences(classPref, MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.remove(classPref).commit();
    }

    public static String SerializeClass(GradedClass gradedClass)
    {
        Gson gson = new Gson();
        return gson.toJson(gradedClass);
    }
    public static String SerializeClass(ArrayList<GradedClass> gradedClasses)
    {
        Gson gson = new Gson();
        return gson.toJson(gradedClasses);
    }
    public static GradedClass DeserializeClass(String gradedClassJson)
    {
        Gson gson = new Gson();
        return gson.fromJson(gradedClassJson, GradedClass.class);
    }
    public static ArrayList<GradedClass> DeserializeClassList(String classListJson)
    {
        Gson gson = new Gson();
        ArrayList<GradedClass> gradedClasses = new ArrayList<>();
        Type type = new TypeToken<ArrayList<GradedClass>>() {}.getType();
        gradedClasses = gson.fromJson(classListJson, type);

        return gradedClasses;
    }

}
