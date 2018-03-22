package com.example.blondeaa.taverneoubliee;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by blondeaa on 22/03/18.
 */

public class TaverneApp extends Application {

    private RequestQueue mVolleyRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        // On initialise notre Thread-Pool et notre ImageLoader
        mVolleyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mVolleyRequestQueue.start();
    }

    public RequestQueue getVolleyRequestQueue() {
        return mVolleyRequestQueue;
    }


    @Override
    public void onTerminate() {
        mVolleyRequestQueue.stop();
        super.onTerminate();
    }
}
