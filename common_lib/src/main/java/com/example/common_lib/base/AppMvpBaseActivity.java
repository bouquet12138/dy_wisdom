package com.example.common_lib.base;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBaseActivity;
import com.example.common_lib.R;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;


public abstract class AppMvpBaseActivity extends MVPBaseActivity implements IAppMvpView {


    private String mTitleName;//标题栏的名字
    private String mRightTextName;//右边按钮的名字
    private int mNormalViewId;//正常布局的id

    protected QMUIDialog mHintDialog;//提醒对话框


    protected ImageView mBackButton;//返回按钮
    protected SmartRefreshLayout mSmartRefreshLayout;
    private Button mRefreshBt;//网络错误时的刷新按钮

    protected RelativeLayout mRootView;

    protected TextView mTitleText;
    protected TextView mRightText;
    protected FrameLayout mFrameLayout;

    protected View mNetErrorView;//网络错误View
    protected View mNoMoreDataView;//没有更多数据的view
    protected View mNormalView;//正常View

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_app_mvp_base);
        BarUtils.setStatusBarLightMode(this, true);//浅色模式
        mTitleName = getTitleName();
        mRightTextName = getRightTextName();
        mNormalViewId = getNormalViewId();//正常view的Id

        initBaseView();
        initBaseListener();
    }

    protected abstract String getTitleName();

    protected abstract String getRightTextName();

    protected abstract int getNormalViewId();

    private void initBaseView() {

        mHintDialog = new QMUIDialog.MessageDialogBuilder(this)
                .setTitle("正在编辑")
                .setMessage("确定要退出吗？")
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction(0, "确定", QMUIDialogAction.ACTION_PROP_NEGATIVE, (dialog, index) -> {
                    dialog.dismiss();
                    finishActivity();//销毁Activity
                }).create();

        mRootView = findViewById(R.id.rootView);

        mSmartRefreshLayout = findViewById(R.id.refreshLayout);

        mBackButton = findViewById(R.id.backButton);
        mTitleText = findViewById(R.id.titleText);
        mRightText = findViewById(R.id.rightText);

        mFrameLayout = findViewById(R.id.frameLayout);

        mTitleText.setText(mTitleName);
        mRightText.setText(mRightTextName);

        mNetErrorView = findViewById(R.id.netErrorLayout);//网络错误
        mNoMoreDataView = findViewById(R.id.noMoreData);//没有更多数据
        mNormalView = getLayoutInflater().inflate(mNormalViewId,
                null, false);//正常view

        mNetErrorView.setVisibility(View.GONE);//不可见
        mNoMoreDataView.setVisibility(View.GONE);
        mFrameLayout.addView(mNormalView);//添加正确的布局

        mRefreshBt = mNetErrorView.findViewById(R.id.refresh);//刷新按钮
        mSmartRefreshLayout.setEnablePureScrollMode(true);//只是滑动
    }

    private void initBaseListener() {
        mBackButton.setOnClickListener(v -> onBackBtPressed());
        mRightText.setOnClickListener(v -> onFloatBtClick());
        mRefreshBt.setOnClickListener(v -> onRefresh());
    }

    @Override
    public void onBackPressed() {
        onBackBtPressed();//返回按钮按下
    }

    /**
     * 点击刷新方法
     */
    protected abstract void onRefresh();


    /**
     * 返回按钮按下
     */
    protected void onBackBtPressed() {
        finish();
    }

    /**
     * 当点击了浮点按钮
     */
    protected abstract void onFloatBtClick();


    /**
     * 提交是否可用
     *
     * @param enable
     */
    @Override
    public void setSubmitEnable(boolean enable) {
        mRightText.setEnabled(enable);
        if (enable)
            mRightText.setAlpha(1);
        else
            mRightText.setAlpha(0.5f);

    }

    /**
     * 展示网络错误
     */
    @Override
    public void showNetError() {
        mNetErrorView.setVisibility(View.VISIBLE);//可见
        mSmartRefreshLayout.setVisibility(View.GONE);
        mNoMoreDataView.setVisibility(View.GONE);//不可见
    }

    /**
     * 展示没有正常数据
     */
    @Override
    public void showNoMoreData() {
        mNetErrorView.setVisibility(View.GONE);//不可见
        mSmartRefreshLayout.setVisibility(View.GONE);
        mNoMoreDataView.setVisibility(View.VISIBLE);//可见
    }

    /**
     * 展示正常view
     */
    @Override
    public void showNormalView() {

        mNetErrorView.setVisibility(View.GONE);//不可见
        mNoMoreDataView.setVisibility(View.GONE);//不可见
        mSmartRefreshLayout.setVisibility(View.VISIBLE);//可见
    }

    private static final String TAG = "AppMvpBaseActivity";

    /**
     * 展示提交
     *
     * @param show
     */
    protected void showSubmit(boolean show) {
        if (!show) {
            mRightText.setVisibility(View.GONE);//不可见
        } else {
            mRightText.setVisibility(View.VISIBLE);//不可见
        }
    }

    /**
     * 网络连上
     *
     * @param type
     */
    @Override
    public void onConnected(NetworkUtils.NetworkType type) {
        super.onConnected(type);
        onRefresh();//刷新一下
    }

    /**
     * 浅色背景
     */
    protected void lightBack() {
        mSmartRefreshLayout.setBackgroundColor(getResources().getColor(R.color.app_light_back_color));
    }

    /**
     * 完成刷新
     */
    @Override
    public void completeRefresh() {
        mSmartRefreshLayout.finishRefresh();//完成刷新
    }

}
