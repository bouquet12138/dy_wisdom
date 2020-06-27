package com.example.wisdomconsumption.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.example.base_lib.util.CornerUtil;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.java_bean.BannerBean;
import com.example.wisdomconsumption.R;
import com.example.wisdomconsumption.adapter.ImageAdapter;
import com.example.wisdomconsumption.adapter.MyBannerAdapter;
import com.example.wisdomconsumption.contract.AboutUsContract;
import com.example.wisdomconsumption.presenter.AboutUsPresenter;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;


public class AboutUsActivity extends AppMvpBaseActivity implements AboutUsContract.IView {

    private Banner mBanner;
    private TextView mAboutUs;
    private AboutUsPresenter mPresenter = new AboutUsPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSubmitEnable(false);//提交不可用
        showNormalView();//展示正常view
        initView();
        initData();
        mPresenter.attachView(this);//绑定一下
        mPresenter.getAboutUsInfo();//获取数据
        //mAboutUs.setText(Html.fromHtml(getResources().getString(R.string.main_about_us)));
    }


    @Override
    protected String getTitleName() {
        return "关于我们";
    }

    @Override
    protected String getRightTextName() {
        return null;
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.main_layout_about_us;
    }

    @Override
    protected void onRefresh() {
        mPresenter.getAboutUsInfo();//重新获取一下
    }

    @Override
    protected void onFloatBtClick() {

    }

    /**
     * 初始化view
     */
    private void initView() {
        mBanner = findViewById(R.id.banner);
        mAboutUs = findViewById(R.id.aboutUs);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.main_about_us_a);
       /* imageList.add(R.drawable.main_about_us_b);
        imageList.add(R.drawable.main_about_us_c);
        imageList.add(R.drawable.main_about_us_d);*/
        setBannerInfo(imageList);//设置一下
    }

    /**
     * 销毁的时候
     */
    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
        mBanner.stop();//停止
        super.onDestroy();
    }

    /**
     * 设置关于我们
     *
     * @param info
     */
    @Override
    public void setAboutUsInfo(String info) {
        if (!TextUtils.isEmpty(info))
            mAboutUs.setText(Html.fromHtml(info));
        else
            showNoMoreData();//展示没有更多数据
    }

    public void setBannerInfo(List<Integer> bannerList) {

        mBanner.setAdapter(new ImageAdapter(bannerList, getContext()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) //加个圆角
            CornerUtil.clipViewCornerByDp(mBanner, SizeUtils.dp2px(8));

        mBanner.start();//启动banner
    }

}
