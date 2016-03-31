package com.zx.okhttp.env;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ApiEnv {
    //other
    public static String BASE_API_URL = "http://api150.aiyouyou.paojiao.cn";

    /** 新游接口 */
    public static final String NEW_GAME = BASE_API_URL + "/v5/game/newlist.do";
    /**
     * 预发布环境
     */
    public static final int PREPARE = -1;
    /**
     * 生产环境
     */
    public static final int ONLINE = 0;
    /**
     * 开发环境1
     */
    public static final int DEBUG1 = 1;
    /**
     * 开发环境2
     */
    public static final int DEBUG2 = 2;
    /**
     * 开发环境3
     */
    public static final int DEBUG3 = 3;
    /**
     * 开发环境4
     */
    public static final int DEBUG4 = 4;

    private final static String PREF_KEY_API_ENV = "pref_key_api_env";

    private static final String URL_DEBUG = "url_debug";
    //private static final String URL_ONLINE = "http://open.treebear.cn/router";
    private static final String URL_ONLINE = NEW_GAME;
    private static final String URL_PREPARE = "url_prepare";

    private static final String API_VERSION = "1.0";
    private static final String APPKEY_DEBUG = "appkey_debug";
    private static final String APPKEY_ONLINE = "appkey";
    private static final String APPSECRET_DEBUG = "appsecret_debug";

    private static int env = ONLINE;



    public static void init(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        ApiEnv.env = prefs.getInt(PREF_KEY_API_ENV, ONLINE);
    }

    public static void switchTo(Context context, int env) {
        ApiEnv.env = env;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putInt(PREF_KEY_API_ENV, ApiEnv.env).apply();
    }

    public static int getEnv() {
        return env;
    }

    public static String getEnvStr() {
        int env = getEnv();
        if (env == ONLINE) {
            return "";
        } else if (env == PREPARE) {
            return "预发";
        } else {
            return String.format("test%s", env);
        }
    }

    public static String getAppKey() {
        int env = getEnv();
        if (env == ONLINE) {
            return APPKEY_ONLINE;
        } else {
            return APPKEY_DEBUG;
        }
    }

    public static String getApiVersion() {
        return API_VERSION;
    }

    public static String getAppSecret() {
        return APPSECRET_DEBUG;
    }

    public static String getURL() {
        int env = getEnv();
        if (env == ONLINE) {
            return URL_ONLINE;
        } else if (env == PREPARE) {
            return URL_PREPARE;
        } else {
            return String.format(URL_DEBUG, env);
        }
    }
}
