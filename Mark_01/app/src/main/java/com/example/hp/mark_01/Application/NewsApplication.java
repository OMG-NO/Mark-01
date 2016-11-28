package com.example.hp.mark_01.Application;

import android.app.Application;

import com.example.hp.mark_01.Util.VolleyUtil;

/**
 * Created by hp on 2016/8/21.
 */
public class NewsApplication extends Application {

    private static NewsApplication instance;

    public static NewsApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyUtil.initContext(this);
        instance = this;
    }
}
