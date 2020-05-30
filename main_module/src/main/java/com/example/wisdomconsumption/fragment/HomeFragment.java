package com.example.wisdomconsumption.fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SizeUtils;
import com.example.base_lib.util.CornerUtil;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.java_bean.ImageBean;
import com.example.flash_module.fragment.FlashFragment;
import com.example.wisdomconsumption.R;
import com.example.wisdomconsumption.activity.AboutUsActivity;
import com.example.wisdomconsumption.activity.PromotionActivity;
import com.example.wisdomconsumption.activity.ServiceCenterActivity;
import com.example.wisdomconsumption.adapter.ImageAdapter;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    protected View mView;

    private TextView mTitle;
    private Banner mBanner;

    private ViewGroup mMyShop;
    private ViewGroup mIntegralShop;
    private ViewGroup mCity;
    private ViewGroup mPopularize_app;

    private ViewGroup mCompanyBusiness;//公司业务
    private ViewGroup mMember_register;
    private ViewGroup mCustomer_center;
    private ViewGroup mAbout_us;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.main_fragment_home, container, false);
        initView();
        initListener();
        initData();
        return mView;
    }

    private void initData() {
        initBanner();
        FlashFragment flashFragment = new FlashFragment();
        replaceFragment(flashFragment);//替换碎片
    }

    /**
     * 初始化banner
     */
    private void initBanner() {
        List<ImageBean> imageList = new ArrayList<>();

        imageList.add(new ImageBean(R.drawable.main_shop_a, "配送中心图片"));
        imageList.add(new ImageBean(R.drawable.main_shop_b, "蔬菜水果批发中心"));

        mBanner.setAdapter(new ImageAdapter(imageList, getContext()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) //加个圆角
            CornerUtil.clipViewCornerByDp(mBanner, SizeUtils.dp2px(8));

        mBanner.start();//启动banner
    }

    private void initView() {
        mTitle = mView.findViewById(R.id.title);
        mBanner = mView.findViewById(R.id.banner);
        mMyShop = mView.findViewById(R.id.myShop);
        mIntegralShop = mView.findViewById(R.id.integralShop);//积分商城

        mCompanyBusiness = mView.findViewById(R.id.companyBusiness);//公司业务
        mCity = mView.findViewById(R.id.city);
        mPopularize_app = mView.findViewById(R.id.popularize_app);
        mMember_register = mView.findViewById(R.id.member_register);
        mCustomer_center = mView.findViewById(R.id.customer_center);
        mAbout_us = mView.findViewById(R.id.about_us);
    }

    /**
     * 初始化监听
     */
    private void initListener() {

        mMyShop.setOnClickListener(this);
        mIntegralShop.setOnClickListener(this);//点击监听
        mCity.setOnClickListener(this);

        mCompanyBusiness.setOnClickListener(this);//公司业务
        mPopularize_app.setOnClickListener(this);
        mMember_register.setOnClickListener(this);
        mCustomer_center.setOnClickListener(this);
        mAbout_us.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBanner.stop();//停止
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myShop:
                //  startActivity(new Intent(getContext(), ShoppingActivity.class));//启动积分商城activity
                break;
            case R.id.integralShop://积分商城
                // startActivity(new Intent(getContext(), IntegralShopActivity.class));//启动积分商城activity
                break;
            case R.id.city:
               /* ARouter.getInstance().build(ARouterContract.CITY_SERVICES) //城市服务
                        .navigation();*/
                break;
            case R.id.companyBusiness://公司业务
               /* ARouter.getInstance().build(ARouterContract.BUSINESS_ADVERT) //公司业务
                        .navigation();*/
                break;
            case R.id.popularize_app:
                startActivity(new Intent(getContext(), PromotionActivity.class));//启动推广activity
                break;
            case R.id.member_register:
                ARouter.getInstance().build(ARouterContract.LOGIN_REGISTER) //跳转到注册页面
                        .navigation();
                break;
            case R.id.customer_center:
                startActivity(new Intent(getContext(), ServiceCenterActivity.class));//启动activity
                break;
            case R.id.about_us:
                startActivity(new Intent(getContext(), AboutUsActivity.class));//启动activity
                break;

        }
    }

    /**
     * 替换碎片
     *
     * @param fragment
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        //在按下返回键的时候不是退出程序而是退到第一个碎片使用transaction的addToBackStack方法，值一般为null
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
