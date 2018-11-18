package com.bwie.week1119.utils;

import android.os.Handler;


import com.bwie.week1119.inter.ICallBack;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;



/**
 * 封装的网络请求工具类
 */
public class HttpUtils {

    private static volatile HttpUtils instance;
    private final OkHttpClient client;
    private Handler handler = new Handler();

    private HttpUtils() {
        // 应用拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }

    /**
     * 工具类的单例模式
     *
     * @return
     */
    public static HttpUtils getInstance() {
        if (instance == null) {
            synchronized (HttpUtils.class) {
                if (null == instance) {
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }

    /**
     * Get请求方式
     *
     * @param url
     * @param callBack
     * @param type
     */
    public void get(String url, final ICallBack callBack, final Type type) {
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Gson gson = new Gson();
                final Object o = gson.fromJson(s, type);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(o);
                    }
                });
            }
        });
    }

    /**
     * Post请求
     *
     * @param url
     * @param callBack
     * @param type
     * @param requestBody
     */
    public void post(String url, final ICallBack callBack, final Type type, RequestBody requestBody) {
        Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Gson gson = new Gson();
                final Object o = gson.fromJson(s, type);
                // 回传到主线程
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(o);
                    }
                });
            }
        });
    }
}
