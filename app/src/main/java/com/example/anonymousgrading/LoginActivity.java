package com.example.anonymousgrading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{

    private EditText username;
    private EditText password;
    private TextView failed;
    private Button submitButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.editTextUsername);
        failed = (TextView) findViewById(R.id.textViewFailed);
        submitButton = (Button) findViewById(R.id.buttonLogin);
        password = (EditText) findViewById(R.id.editTextPassword);
        registerButton = (Button) findViewById(R.id.buttonRegister);
        submitButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        Amplify.Auth.signOut(
                result -> Log.i("Amplify Logout", result.toString())
        );
        //makes new student in database
        /*
        StudentAWS studentaws = StudentAWS.builder()
                .name("reid")
                .studentId("123456")
                .professor("mr prof")
                .exam("exam")
                .barcode("barcode")
                .grade("bad grade")
                .classname("class")
                .build();
        Amplify.API.mutate(
                ModelMutation.create(studentaws),
                response -> Log.i("GraphQL", "response: " + response),
                error -> Log.e("GraphQL", "error: " + error)
        );
        Amplify.API.query(
                ModelQuery.list(StudentAWS.class, StudentAWS.STUDENT_ID.contains("123456")),
                response ->{
                        for(StudentAWS tempStudent : response.getData()){
                            Log.i("GraphQL", "response: " + response);
                        }
                },
                error -> Log.e("GraphQL", "error: " + error)
        );
        */
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.buttonLogin)
        {
            Intent myIntent = new Intent(LoginActivity.this, ClassListActivity.class);
            String user = username.getText().toString();
            String pass = password.getText().toString();
//            myIntent.putExtra("user", user);
//            myIntent.putExtra("pass", pass);
            final boolean[] state = {false};
            try {
                Amplify.Auth.signIn(user, pass,
                        result -> {
                            Log.i("Amplify Login", result.toString());
                            startActivity(myIntent);
                        },
                        error -> {
                            Log.e("Amplify Login", error.toString());
                            state[0] = true;
                        });
                Amplify.Auth.fetchAuthSession(
                        result -> Log.i("Amplify Session", "Result: " + result.toString()),
                        error -> Log.e("Amplify Session", "Error: " + error.toString())
                );
                Log.i("email test", "emal: "+failed.getText().toString());
            }
            catch (Exception e){
                e.printStackTrace();
            }
        } else if (v.getId() == R.id.buttonRegister)
        {
            Intent myIntent = new Intent(LoginActivity.this, registration.class);
            startActivity(myIntent);
        }
    }
}