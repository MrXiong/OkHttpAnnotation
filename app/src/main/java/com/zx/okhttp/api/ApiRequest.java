package com.zx.okhttp.api;


import com.zx.okhttp.env.ObjectTool;

/**
 * Created by Administrator on 2016/3/31.
 */
public abstract class ApiRequest<T extends BaseApiResponse> extends BaseRequest<T> {

    private Object mParam;

    public Object getParam() {
        return mParam;
    }

    public void setParam(Object mParam) {
        this.mParam = mParam;
    }

    public String getURL(){
        RequestURL requestURL = new RequestURL(getMethod());
        requestURL.putParameters(ObjectTool.toMap(getParam()));
        return requestURL.toString();
    }
    protected abstract String getMethod();

}
