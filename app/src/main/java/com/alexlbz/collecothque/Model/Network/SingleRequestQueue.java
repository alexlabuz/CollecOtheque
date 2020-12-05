package com.alexlbz.collecothque.Model.Network;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingleRequestQueue {
    private static SingleRequestQueue instance;
    private RequestQueue requestQueue;
    private Context context;

    private SingleRequestQueue(Context context){
        this.context = context;
    }

    public static synchronized SingleRequestQueue

    getInstance(Context context){
        if(instance == null){
            instance = new SingleRequestQueue(context);
        }
        return instance;
    }
    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

}
