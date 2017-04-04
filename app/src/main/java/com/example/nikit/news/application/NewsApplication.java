package com.example.nikit.news.application;

import android.app.Application;

import com.example.nikit.news.database.DatabaseManager;
import com.example.nikit.news.database.SqLiteDbHelper;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by nikit on 22.03.2017.
 */

public class NewsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(getApplicationContext());
        DatabaseManager.initializeInstance(new SqLiteDbHelper(getApplicationContext()));
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
