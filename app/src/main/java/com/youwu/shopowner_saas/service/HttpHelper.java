package com.youwu.shopowner_saas.service;


import java.util.HashMap;
import java.util.Map;

/**
 * 代理类
 */
public class HttpHelper implements IhttpProcessor {

    private static IhttpProcessor mIhttpProcessor;
    private static HttpHelper _instance;
	private Map<String,Object> mParams;
    
	private HttpHelper(){
		mParams = new HashMap<>();
    }

   

    public static HttpHelper obtain(){
		synchronized (HttpHelper.class){
			if(_instance == null){
				_instance = new HttpHelper();
			}
		}
        return _instance;
    }

	public static void init(IhttpProcessor httpProcessor){
        mIhttpProcessor = httpProcessor;

    }

    @Override
    public void get(String url, Map<String, Object> params, ICallBack callback) {
        //final String finalUrl = appendParams(url,params);
		mIhttpProcessor.get(url,params,callback);
    }

    @Override
    public void post(String url, MyHashMap<String> params, ICallBack callback) {

        params.put("order_id", "");



        //final String finalUrl = appendParams(url,params);
		mIhttpProcessor.post(url,params,callback);
    }
    @Override
    public void postGender(String url, MyHashMap<String> params, ICallBack callback) {


        //final String finalUrl = appendParams(url,params);
        mIhttpProcessor.postGender(url,params,callback);
    }

	//拼接url
	private String appendParams(String url, Map<String, Object> params){
        return "";
	}
}
