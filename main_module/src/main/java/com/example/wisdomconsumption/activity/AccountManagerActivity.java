package com.example.wisdomconsumption.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.AppUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;

import com.example.base_lib.base.ActivityCollector;
import com.example.base_lib.util.CacheUtil;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.wisdomconsumption.R;

import static com.example.base_lib.util.CacheUtil.deleteFolderFile;

public class AccountManagerActivity extends AppMvpBaseActivity implements View.OnClickListener {

    private ViewGroup mModifyPass;//修改密码
    private ViewGroup mModifyPayPass;//修改支付密码
    private ViewGroup mCleanCache;//清理缓存
    private TextView mCacheText;//缓存内存
    private ViewGroup mAppUpdate;
    private TextView mAppVersionText;//app版本

    private ViewGroup mOption;

    private View mExitCurrentAccount;//退出当前账号

    private static final int MEMORY_CLEAR_OK = 1;
    private static final int DISK_CLEAR_OK = 2;


    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MEMORY_CLEAR_OK:
                    Toast.makeText(AccountManagerActivity.this, "内存缓存清理成功！", Toast.LENGTH_SHORT).show();
                    mCacheText.setText(CacheUtil.getCacheSize(AccountManagerActivity.this));
                    break;
                case DISK_CLEAR_OK:
                    Toast.makeText(AccountManagerActivity.this, "磁盘缓存清理成功！", Toast.LENGTH_SHORT).show();
                    mCacheText.setText(CacheUtil.getCacheSize(AccountManagerActivity.this));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
        showNormalView();//展示正常视图
    }


    @Override
    protected String getTitleName() {
        return "账号管理";
    }

    @Override
    protected String getRightTextName() {
        return "";
    }


    @Override
    protected int getNormalViewId() {
        return R.layout.main_layout_account_manager;
    }

    @Override
    protected void onRefresh() {

    }


    private void initView() {

        mModifyPass = mNormalView.findViewById(R.id.modifyPass);
        mModifyPayPass = mNormalView.findViewById(R.id.modifyPayPass);

        mCleanCache = mNormalView.findViewById(R.id.cleanCache);
        mCacheText = mNormalView.findViewById(R.id.cacheText);
        mExitCurrentAccount = mNormalView.findViewById(R.id.exitCurrentAccount);
        mAppUpdate = mNormalView.findViewById(R.id.appUpdate);
        mAppVersionText = mNormalView.findViewById(R.id.appVersionText);

        mOption = mNormalView.findViewById(R.id.option);

        mRightText.setVisibility(View.GONE);//不可见
    }

    private void initData() {
        mCacheText.setText(CacheUtil.getCacheSize(AccountManagerActivity.this));
        mAppVersionText.setText(AppUtils.getAppVersionName());
    }

    private void initListener() {
        mModifyPass.setOnClickListener(this);
        mModifyPayPass.setOnClickListener(this);

        mCleanCache.setOnClickListener(this);
        mAppUpdate.setOnClickListener(this);//app更新

        mOption.setOnClickListener(this);

        mExitCurrentAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();//返回按钮

        if (i == R.id.backButton) {
            finish();//销毁
        } else if (i == R.id.modifyPass) { //修改密码
            ARouter.getInstance().build(ARouterContract.LOGIN_MODIFY_LOGIN_PW) //修改登陆密码
                    .navigation();
        } else if (i == R.id.modifyPayPass) { //修改密码
            ARouter.getInstance().build(ARouterContract.LOGIN_MODIFY_PAY_PW) //修改支付密码
                    .navigation();
        } else if (i == R.id.cleanCache) {//清理缓存
            if ("0.0B".equals(mCacheText.getText().toString()))
                Toast.makeText(this, "没有缓存", Toast.LENGTH_SHORT).show();
            else
                clearImageAllCache(AccountManagerActivity.this);
        } else if (i == R.id.appUpdate) {//app更新
            startActivity(new Intent(this, AppUpdateActivity.class));
        } else if (i == R.id.option) {
            startActivity(new Intent(this, OptionActivity.class));//启动意见活动
        } else if (i == R.id.exitCurrentAccount) {//退出当前账号
            ActivityCollector.finishAll();//销毁所有
            ARouter.getInstance().build(ARouterContract.LOGIN_LOGIN) //跳转到登陆页面
                    .navigation();
        }
    }


    @Override
    protected void onFloatBtClick() {

    }

    /**
     * 清除图片内存缓存
     */
    public void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context).clearMemory();
                handler.sendEmptyMessage(MEMORY_CLEAR_OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片磁盘缓存
     */
    public void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(() -> {
                    Glide.get(context).clearDiskCache();
                    handler.sendEmptyMessage(DISK_CLEAR_OK);
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片所有缓存
     */
    public void clearImageAllCache(Context context) {
        clearImageDiskCache(context);
        clearImageMemoryCache(context);
        String ImageExternalCatchDir = context.getExternalCacheDir() + ExternalPreferredCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
        deleteFolderFile(ImageExternalCatchDir, true);
    }


}

