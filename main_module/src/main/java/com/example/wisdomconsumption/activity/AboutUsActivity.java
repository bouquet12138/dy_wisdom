package com.example.wisdomconsumption.activity;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.wisdomconsumption.R;
import com.example.wisdomconsumption.contract.AboutUsContract;
import com.example.wisdomconsumption.presenter.AboutUsPresenter;


public class AboutUsActivity extends AppMvpBaseActivity implements AboutUsContract.IView {

    private TextView mAboutUs;
    private AboutUsPresenter mPresenter = new AboutUsPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSubmitEnable(false);//提交不可用
        showNormalView();//展示正常view
        initView();
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

    }

    @Override
    protected void onFloatBtClick() {

    }

    private void initView() {
        mAboutUs = findViewById(R.id.aboutUs);
    }

    /**
     * 销毁的时候
     */
    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
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
}
