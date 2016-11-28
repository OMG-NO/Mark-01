package com.example.hp.mark_01.Util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by hp on 2016/8/20.
 */
public class VolleyUtil {
    private static RequestQueue requestQueue;
    public static void initContext(Context context){
        if (requestQueue==null){
            requestQueue= Volley.newRequestQueue(context);
        }
    }

    public static Net_Request.Builder get(String url){
        return new Net_Request.Builder()
                .setURL(url)
                .setMethod(Request.Method.GET);
    }

    //发送请求
    public static void start(Net_Request request){
        requestQueue.add(request);
    }

    public static void cancel(RequestQueue.RequestFilter requestFilter){
        requestQueue.cancelAll(requestFilter);
    }
}
