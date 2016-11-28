package com.example.hp.mark_01.Util;

import com.android.volley.VolleyError;

/**
 * Created by hp on 2016/8/20.
 */
public interface CallBack {
    public void onSuccess(String response);
    public void onError(VolleyError error);
}
