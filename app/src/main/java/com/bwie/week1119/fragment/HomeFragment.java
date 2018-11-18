package com.bwie.week1119.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bwie.week1119.R;
import com.bwie.week1119.adapter.ShopAdapter;
import com.bwie.week1119.home.bean.BannerBean;
import com.bwie.week1119.home.bean.ShopBean;
import com.bwie.week1119.home.presenter.HomePresenter;
import com.bwie.week1119.home.view.IViewHome;


import java.util.ArrayList;
import java.util.List;



public class HomeFragment extends Fragment implements IViewHome {

    public static final int FLAG = 123;
    private ViewPager vpBanner;
    private RecyclerView rvShop;
    private HomePresenter homePresenter;
    private List<BannerBean.DataBean> banners;
    private PagerAdapter pagerAdapter;
    private List<ShopBean.DataBean.ListBean> shops;
    private ShopAdapter shopAdapter;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == FLAG) {
                int currentItem = vpBanner.getCurrentItem();
                if (currentItem == banners.size() - 1) {
                    currentItem = 0;
                } else {
                    currentItem++;
                }
                vpBanner.setCurrentItem(currentItem);
                sendEmptyMessageDelayed(FLAG, 2500);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        // 初始化Presenter层
        homePresenter = new HomePresenter();
        homePresenter.attach(this);
        // 自动轮播
        handler.sendEmptyMessageDelayed(FLAG, 2500);
        initBanner();

        initShop();
    }

    /**
     * 初始化商品列表
     */
    private void initShop() {
        shops = new ArrayList<>();
        shopAdapter = new ShopAdapter(getActivity(), shops);
        rvShop.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvShop.setAdapter(shopAdapter);
        homePresenter.getShop("http://www.zhaoapi.cn/product/getCarts?uid=71");
    }

    /**
     * 初始化轮播图
     */
    private void initBanner() {
        // 初始化轮播图
        banners = new ArrayList<>();
        pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return banners.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView img = new ImageView(getActivity());
                img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                String icon = banners.get(position).getIcon().replace("https", "http");
                Glide.with(getActivity()).load(icon).into(img);
                container.addView(img);
                return img;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        };
        vpBanner.setAdapter(pagerAdapter);
        homePresenter.getBanner("http://www.zhaoapi.cn/ad/getAd");
    }

    private void initView(View view) {
        vpBanner = view.findViewById(R.id.vp_banner);
        rvShop = view.findViewById(R.id.rv_shop);
    }

    @Override
    public void getBanner(BannerBean bannerBean) {
        // 回调传来banner数据
        if (Integer.parseInt(bannerBean.getCode()) == 0) {
            List<BannerBean.DataBean> data = bannerBean.getData();
            if (data != null) {
                banners.clear();
                banners.addAll(data);
                pagerAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void getShop(ShopBean shopBean) {
        // 回调传来banner数据
        if (Integer.parseInt(shopBean.getCode()) == 0) {
            List<ShopBean.DataBean> data = shopBean.getData();
            for (int i = 0; i < data.size(); i++) {
                List<ShopBean.DataBean.ListBean> list = data.get(i).getList();
                if (list != null) {
                    shops.addAll(list);
                    shopAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    /**
     * 销毁handler和presenter
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        if (homePresenter != null) {
            homePresenter.detach();
        }
    }
}
