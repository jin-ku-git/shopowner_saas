package com.youwu.shopowner_saas.service;


import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * Result 是javabean对象
 */

public abstract class  HttpCallback<T> implements ICallBack {

    @Override
    public void onSuccess(String result) {
        Gson gson = new Gson();
        Class<?> cls = analysisClazzInfo(this);

        KLog.i("输出结果："+result);

        T objResult = (T)gson.fromJson(result,cls);
        onSuccess(objResult);

    }

    public abstract void onSuccess(T result);

    public static Class<?> analysisClazzInfo(Object object){
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }


}
