package com.example.hp.mark_01.Util;

import android.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hp on 2016/8/20.
 */
public class Net_Request extends StringRequest {
    Priority priority;      //消息请求的优先级
    private Map<String, String> header;
    private Map<String, String> params;

    private void init() {
        priority = Priority.NORMAL;
        header = new HashMap<>();
    }

    public Net_Request(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    //设置请求头
    public Net_Request addRequestHeader(String key, String value) {
        Map<String, String> h = new HashMap();
        h.put(key, value);
        this.header = h;
        return this;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (this.header != null) {
            return this.header;
        } else {
            return super.getHeaders();
        }
    }

    public static class Builder {
        private String url;
        private int method = Method.GET;
        private CallBack callBack;

        public Builder setURL(String url) {
            this.url = url;
            return this;
        }

        public Builder setMethod(int Method) {
            this.method = Method;
            return this;
        }

        public Builder setCallBack(CallBack callBack) {
            this.callBack = callBack;
            return this;
        }

        public Net_Request build() {
            Net_Request net_request = new Net_Request(
                    method,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (callBack != null) {
                                callBack.onSuccess(response);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (callBack != null) {
                                callBack.onError(error);
                            }
                        }
                    }
            );
            return net_request;
        }

    }

    public void start() {
        VolleyUtil.start(this);
    }
}
