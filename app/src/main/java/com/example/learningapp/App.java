package com.example.learningapp;

import android.app.Application;
import android.content.Context;
import android.os.Debug;
import android.util.Log;

import com.example.learningapp.mock.MockManager;

public class App extends Application {
    private static final String TAG = "App";
    private static Application sApp = null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        Debug.startMethodTracing("/sdcard/a.trace");
        Log.d(TAG, "attachBaseContext() called with: base = [" + base + "]");
        sApp = this;
//        MockManager.getInstance().init();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() called");
    }

    public static Application getApp() {
        return sApp;
    }


}
