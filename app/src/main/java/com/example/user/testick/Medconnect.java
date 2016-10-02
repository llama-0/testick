package com.example.user.testick;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by user on 02.10.2016.
 */

public class Medconnect extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        /*Newer version*/
        if (!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
    }
}
