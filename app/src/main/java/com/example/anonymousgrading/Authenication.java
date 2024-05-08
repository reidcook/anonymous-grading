package com.example.anonymousgrading;

import android.app.Application;

import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;

public class Authenication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
