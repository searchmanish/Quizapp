package com.quizapp.quizbox.Login;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Lenovo on 16-01-2017.
 */

public class Singleton {
    private static Singleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;
    private Singleton(Context context)
    {
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
        {
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized Singleton getmInstance(Context context)
    {
        if(mInstance == null)
        {
            mInstance = new Singleton(context);
        }
        return mInstance;
    }
    public<T> void addToRequestque(Request<T> request)

    {
        requestQueue.add(request);
    }
}
