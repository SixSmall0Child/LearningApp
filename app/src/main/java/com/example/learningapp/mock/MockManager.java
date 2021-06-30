package com.example.learningapp.mock;

import android.util.Log;
import android.widget.Toast;

import com.example.learningapp.App;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MockManager {
    private static final String TAG = "MockManager";
    private static final MockManager sINSTANCE = new MockManager();
    private CountDownLatch mLatch = new CountDownLatch(1);

    // TODO: 2021/6/22 锁竞争
    private MockManager() {
    }

    public static MockManager getInstance() {
        return sINSTANCE;
    }


    public void init() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "MockManager#init");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mLatch.countDown();
            }
        }, "MockManager-init");
        thread.setPriority(10);
        thread.start();
    }

    public void callFunc() {
        Log.d(TAG, "callFunc() called");
        try {
            mLatch.await(11, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Toast.makeText(App.getApp(), "call func", Toast.LENGTH_SHORT).show();
    }

}
