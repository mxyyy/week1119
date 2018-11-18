package com.bwie.week1119.home.presenter;


import com.bwie.week1119.home.bean.BannerBean;
import com.bwie.week1119.home.bean.ShopBean;
import com.bwie.week1119.home.model.HomeModel;
import com.bwie.week1119.home.view.IViewHome;
import com.bwie.week1119.inter.ICallBack;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;



public class HomePresenter {

    private IViewHome iv;
    private HomeModel model;

    public void attach(IViewHome iv) {
        this.iv = iv;
        model = new HomeModel();
    }

    /**
     * 获取轮播图数据
     *
     * @param url
     */
    public void getBanner(String url) {
        Type type = new TypeToken<BannerBean>() {
        }.getType();
        model.get(url, new ICallBack() {
            @Override
            public void onSuccess(Object obj) {
                iv.getBanner((BannerBean) obj);
            }

            @Override
            public void onFailed(Exception e) {

            }
        }, type);
    }

    /**
     * 获取商品列表
     *
     * @param url
     */
    public void getShop(String url) {
        Type type = new TypeToken<ShopBean>() {
        }.getType();
        model.get(url, new ICallBack() {
            @Override
            public void onSuccess(Object obj) {
                iv.getShop((ShopBean) obj);
            }

            @Override
            public void onFailed(Exception e) {

            }
        }, type);
    }

    public void detach() {
        if (iv != null) {
            iv = null;
        }
    }

}
