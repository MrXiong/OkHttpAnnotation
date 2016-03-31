package com.zx.okhttp.api;


import com.zx.okhttp.env.ApiEnv;
import com.zx.okhttp.env.SignUtil;
import com.zx.okhttp.env.URLEncoder;

import java.util.HashMap;
import java.util.Map;


public class RequestURL {

    private final static class ParameterKey {
        static final String Method = "method";
        static final String AppKey = "appKey";
        static final String ApiVersion = "v";
        static final String Sign = "sign";
    }

    private Map<String, Object> mParamMap;
    private String mMethod;

    public RequestURL(final String method) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put(ParameterKey.Method, method);
        parameterMap.put(ParameterKey.AppKey, ApiEnv.getAppKey());
        parameterMap.put(ParameterKey.ApiVersion, ApiEnv.getApiVersion());
        mMethod = method;
        mParamMap = parameterMap;
    }

    public void putParameter(String key, Object value) {
        mParamMap.put(key, (value != null ? value : ""));
    }

    public void putParameters(Map<String, Object> map) {
        if (map != null && map.size() > 0) {
            mParamMap.putAll(map);
        }
    }

    @Override
    public String toString() {
        int env = ApiEnv.getEnv();
        Map<String, Object> parameterMap = mParamMap;
        String sign = SignUtil.sign(parameterMap, ApiEnv.getAppSecret());
        StringBuilder builder = new StringBuilder();
        builder.append(ApiEnv.getURL());
        builder.append("?");
        builder.append(ParameterKey.Method);
        builder.append("=");
        builder.append(mMethod);
        for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
            if (!ParameterKey.Method.equals(entry.getKey())) {
                builder.append("&");
                builder.append(entry.getKey());
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue().toString(), "utf-8"));
            }
        }
        builder.append("&");
        builder.append(ParameterKey.Sign);
        builder.append("=");
        builder.append(sign);
        return builder.toString();
    }


}
