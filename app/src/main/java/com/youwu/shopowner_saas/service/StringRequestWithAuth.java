package com.youwu.shopowner_saas.service;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.youwu.shopowner_saas.app.UserUtils;

import java.util.LinkedHashMap;
import java.util.Map;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * 在Volley http请求中添加请求头
 * Created by dxb on 2017/6/1.
 */
public class StringRequestWithAuth extends StringRequest {

    public StringRequestWithAuth(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public StringRequestWithAuth(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new LinkedHashMap<>();
        //添加请求头

        headers.put("Authorization", UserUtils.getTokenType() +" "+UserUtils.getToken());

        KLog.i("token------------->"+UserUtils.getTokenType() +" "+UserUtils.getToken());

        return headers;
    }
}