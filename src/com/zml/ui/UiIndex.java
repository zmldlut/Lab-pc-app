package com.zml.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ab.adapter.AbFragmentPagerAdapter;
import com.zml.R;
import com.zml.base.BaseUi;

public class UiIndex extends BaseUi {

    private ViewPager mTabPager;
    private ArrayList<Fragment> pagerItemList = null;
    private LinearLayout mTabIndex, mTabOrder, mTabMine;
    private ImageView mImgIndex, mImgOrder, mImgMine;
    private ImageView mTabImg;// 动画图片
    private int currIndex = 0;// 当前页卡编号
    private int zero = 0;     // 动画图片偏移量
    private int one;          //单个水平动画位移
    private int two;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.ui_index);
        initViews();
    }

    private void initViews() {
        mImgIndex = (ImageView) findViewById(R.id.img_index);
        mImgOrder = (ImageView) findViewById(R.id.img_order);
        mImgMine = (ImageView) findViewById(R.id.img_mine);
        mTabPager = (ViewPager) findViewById(R.id.vPager);
        mTabPager.setOnTouchListener(new OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        FragmentIndex index = new FragmentIndex();
        FragmentOrder order = new FragmentOrder();
        FragmentMine mine = new FragmentMine();
        pagerItemList = new ArrayList<Fragment>();
        pagerItemList.add(index);
        pagerItemList.add(order);
        pagerItemList.add(mine);
        FragmentManager mFragmentManager = this.getSupportFragmentManager();
        AbFragmentPagerAdapter mFragmentPagerAdapter = new AbFragmentPagerAdapter(
                mFragmentManager, pagerItemList);
        mTabPager.setAdapter(mFragmentPagerAdapter);

        mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mTabImg = (ImageView) findViewById(R.id.img_tab_now);
        mTabIndex = (LinearLayout) findViewById(R.id.tab_index);
        mTabOrder = (LinearLayout) findViewById(R.id.tab_order);
        mTabMine = (LinearLayout) findViewById(R.id.tab_mine);
        mTabIndex.setOnClickListener(new MyOnClickListener(0));
        mTabOrder.setOnClickListener(new MyOnClickListener(1));
        mTabMine.setOnClickListener(new MyOnClickListener(2));

        //获取屏幕的分辨率，以计算偏移量
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int s = width/3;  
        one = s;
        two = one * 2;
    }

    public class MyOnPageChangeListener implements OnPageChangeListener{

        @Override
        public void onPageScrollStateChanged(int arg0) {
            
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            
        }

        @Override
        public void onPageSelected(int arg0) {
            Animation anim = null;
            switch (arg0) {
            case 0:
                mImgIndex.setImageDrawable(getResources().getDrawable(R.drawable.home_f));
                if (currIndex == 1) {
                    anim = new TranslateAnimation(one, 0, 0, 0);
                    mImgOrder.setImageDrawable(getResources().getDrawable(R.drawable.take_notes_n));
                }else if (currIndex == 2) {
                    anim = new TranslateAnimation(two, 0, 0, 0);
                    mImgMine.setImageDrawable(getResources().getDrawable(R.drawable.m_more_n));
                }
                break;
            case 1:
                mImgOrder.setImageDrawable(getResources().getDrawable(R.drawable.take_notes_f));
                if (currIndex == 0) {
                    anim = new TranslateAnimation(zero, one, 0, 0);
                    mImgIndex.setImageDrawable(getResources().getDrawable(R.drawable.home_n));
                }else if (currIndex == 2) {
                    anim = new TranslateAnimation(two, one, 0, 0);
                    mImgMine.setImageDrawable(getResources().getDrawable(R.drawable.m_more_n));
                }
                break;
            case 2:
                mImgMine.setImageDrawable(getResources().getDrawable(R.drawable.m_more_f));
                if (currIndex == 0) {
                    anim = new TranslateAnimation(zero, two, 0, 0);
                    mImgIndex.setImageDrawable(getResources().getDrawable(R.drawable.home_n));
                }else if (currIndex == 1) {
                    anim = new TranslateAnimation(one, two, 0, 0);
                    mImgOrder.setImageDrawable(getResources().getDrawable(R.drawable.take_notes_n));
                }
                break;
            }
            currIndex = arg0;
            //图片停在偏移的位置
            anim.setFillAfter(true);
            anim.setDuration(150);
            mTabImg.startAnimation(anim);
        }
    }

    public class MyOnClickListener implements OnClickListener{
        int index = 0;
        public MyOnClickListener(int i) {
            this.index = i;
        }
        @Override
        public void onClick(View v) {
            mTabPager.setCurrentItem(index);
        }
    }
}

