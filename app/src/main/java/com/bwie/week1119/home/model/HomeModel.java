package com.bwie.week1119.home.model;



import com.bwie.week1119.inter.ICallBack;
import com.bwie.week1119.utils.HttpUtils;

import java.lang.reflect.Type;

import okhttp3.RequestBody;



public class HomeModel {

    /**
     * get获取数据
     *
     * @param url
     * @param callBack
     * @param type
     */
    public void get(String url, ICallBack callBack, Type type) {
        HttpUtils.getInstance().get(url, callBack, type);
    }

    /**
     * post获取数据
     * @param url
     * @param callBack
     * @param type
     * @param requestBody
     */
    public void post(String url, ICallBack callBack, Type type, RequestBody requestBody) {
        HttpUtils.getInstance().post(url, callBack, type, requestBody);
    }
}
