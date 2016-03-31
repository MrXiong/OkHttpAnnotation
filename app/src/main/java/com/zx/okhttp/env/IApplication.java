package com.zx.okhttp.env;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.HashMap;
import java.util.Map;

import cn.finalteam.okhttpfinal.OkHttpFinal;

/**
 * Created by zx on 2016/1/20.
 */
public class IApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Map<String,String> commonParamMap = new HashMap<>();
        Map<String,String> commonHeaderMap = new HashMap<>();

        OkHttpFinal okHttpFinal = new OkHttpFinal.Builder()
                .setCommenHeader(commonHeaderMap)
                .setCommenParams(commonParamMap)
                .setTimeout(Constants.REQ_TIMEOUT)
                .setDebug(true)
                .build();
        okHttpFinal.init();
        initImageLoader(this);

    }
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
}
