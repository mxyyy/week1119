package com.bwie.week1119;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bwie.week1119.fragment.HomeFragment;
import com.bwie.week1119.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager vpContent;
    private TabLayout tbl;
    private List<String> list;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();

        // 设置TabLayout的展示模式
        tbl.setTabMode(TabLayout.MODE_FIXED);
        // 设置适配器
        vpContent.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return list.get(position);
            }
        });
        // TabLayout和ViewPager的联立
        tbl.setupWithViewPager(vpContent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private void initData() {
        list = new ArrayList<>();
        list.add("首页");
        list.add("我的");
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new MineFragment());
    }

    private void initView() {
        vpContent = findViewById(R.id.vp_content);
        tbl = findViewById(R.id.tbl);
    }
}
