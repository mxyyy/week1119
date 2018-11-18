package com.bwie.week1119.home.view;


import com.bwie.week1119.home.bean.BannerBean;
import com.bwie.week1119.home.bean.ShopBean;

public interface IViewHome {
    void getBanner(BannerBean bannerBean);

    void getShop(ShopBean shopBean);
}
