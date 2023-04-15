package com.youwu.shopowner_saas.service;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.util.Map;


public class VolleyProcessor implements IhttpProcessor {

    public static final String TAG ="VolleyProcessor";

    private static RequestQueue mQueue = null;

    public VolleyProcessor(Context context){
        mQueue = Volley.newRequestQueue(context);
    }

    @Override
    public void get(String url, Map<String, Object> params, final ICallBack callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onFailed(volleyError.toString());
            }
        });
        mQueue.add(stringRequest);
    }

    @Override
    public void post(String url, MyHashMap<String> params, final ICallBack callback) {
        StringRequestWithAuth stringRequest = new StringRequestWithAuth(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onFailed(volleyError.toString());
            }
        }){

            @Override
            protected MyHashMap<String> getParams() throws AuthFailureError {
                MyHashMap<String> postMap = new MyHashMap<String>();

                postMap.put("order_id", params.get("order_id"));


                //过滤掉Value为空的
                MapRemoveNullUtil.removeNullValue(postMap);

                //..... Add as many key value pairs in the map as necessary for your request
                return postMap;
            }
        };
        mQueue.add(stringRequest);
    }
    @Override
    public void postGender(String url, MyHashMap<String> params, final ICallBack callback) {
        StringRequestWithAuth stringRequest = new StringRequestWithAuth(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onFailed(volleyError.toString());
            }
        }){

        };
        mQueue.add(stringRequest);
    }
}
