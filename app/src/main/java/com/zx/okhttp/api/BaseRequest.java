package com.zx.okhttp.api;

import android.util.Log;

import cn.finalteam.okhttpfinal.HttpRequest;

/**
 * Created by Administrator on 2016/3/31.
 */
public abstract class BaseRequest<T extends BaseApiResponse> extends MyBaseHttpRequestCallback<T> {
    public abstract String getURL();

    public final void get(){
        String url = getURL();
        Log.v("Mains", "GET request:\n" + url);
        HttpRequest.get(url,this);
    }
}
