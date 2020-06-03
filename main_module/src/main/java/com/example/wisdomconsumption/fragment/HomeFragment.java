package com.example.wisdomconsumption.fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SizeUtils;
import com.example.base_lib.base.BaseFragment;
import com.example.base_lib.util.CornerUtil;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.java_bean.BannerBean;
import com.example.flash_module.fragment.FlashFragment;
import com.example.wisdomconsumption.R;
import com.example.wisdomconsumption.activity.AboutUsActivity;
import com.example.wisdomconsumption.activity.PromotionActivity;
import com.example.wisdomconsumption.activity.ServiceCenterActivity;
import com.example.wisdomconsumption.adapter.MyBannerAdapter;
import com.example.wisdomconsumption.contract.HomeContract;
import com.example.wisdomconsumption.presenter.HomePresenter;
import com.youth.banner.Banner;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener, HomeContract.IView {

    private NestedScrollView mScrollView;//滚动view
    private Banner mBanner;

    private ViewGroup mMyShop;
    private ViewGroup mIntegralShop;
    private ViewGroup mCity;
    private ViewGroup mPopularize_app;

    private ViewGroup mCompanyBusiness;//公司业务
    private ViewGroup mMember_register;
    private ViewGroup mCustomer_center;
    private ViewGroup mAbout_us;

    private HomePresenter mPresenter = new HomePresenter();


    @Override
    protected int getContentViewId() {
        return R.layout.main_fragment_home;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initView();
        initListener();
        mPresenter.attachView(this);//绑定一下
        initData();
    }

    private void initData() {
        mPresenter.getBannerInfo();//得到轮播图信息
        FlashFragment flashFragment = new FlashFragment();
        replaceFragment(flashFragment);//替换碎片
    }


    private void initView() {

        mScrollView = mView.findViewById(R.id.scrollView);

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

    /**
     * 销毁时
     */
    @Override
    public void onDestroy() {
        mPresenter.detachView();//解除绑定
        mBanner.stop();//停止
        super.onDestroy();
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

    private long mLastRefreshTime = 0;//上一次刷新

    /**
     * 刷新主页
     */
    public void refresh() {
        long nowRefreshTime = System.currentTimeMillis();//当前时间
        if (nowRefreshTime - mLastRefreshTime > 5000) {
            initData();//重新初始化一下数据
            mLastRefreshTime = nowRefreshTime;
        }
        mScrollView.fullScroll(ScrollView.FOCUS_UP);//滚动到顶部

    }

    @Override
    public void setBannerInfo(List<BannerBean> bannerList) {

        mBanner.setAdapter(new MyBannerAdapter(bannerList, getContext()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) //加个圆角
            CornerUtil.clipViewCornerByDp(mBanner, SizeUtils.dp2px(8));

        mBanner.start();//启动banner
    }
}
