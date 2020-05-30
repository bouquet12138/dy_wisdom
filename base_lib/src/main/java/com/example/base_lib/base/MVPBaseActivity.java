package com.example.base_lib.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.R;
import com.example.base_lib.util.NetChangeObserver;
import com.example.base_lib.util.NetworkManager;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

/**
 * 所有需要mvp设计模式的activity的基类
 * Created by xiaohan on 2018/3/4.
 */

public class MVPBaseActivity extends AppCompatActivity implements IMVPBaseView, NetChangeObserver {

    private QMUITipDialog mLoadingDialog;//加载dialog
    private QMUITipDialog mErrorDialog;//错误dialog
    private QMUITipDialog mSuccessDialog;//成功dialog

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        //BarUtils.setStatusBarColor(getWindow(), getResources().getColor(R.color.app_title_color));//设置状态栏颜色
    }

    /**
     * 展示加载进度
     *
     * @param msg
     */
    @Override
    public void showLoading(String msg) {
        mLoadingDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(msg)
                .create();
        mLoadingDialog.show();//展示一下
    }


    /**
     * 隐藏加载进度
     */
    @Override
    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();//消失
        }
    }


    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public Context getContext() {
        return MVPBaseActivity.this;
    }

    @Override
    public void startLogin() {
        ActivityCollector.finishAll();//销毁所有activity
        ARouter.getInstance().build("/app/login") // 目标页面
                .navigation();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    /**
     * 展示错误信息
     *
     * @param hintStr
     */
    @Override
    public void showErrorHint(String hintStr) {
        mErrorDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(hintStr)
                .create();
        mErrorDialog.show();//展示一下
        new Handler().postDelayed(() -> mErrorDialog.dismiss(), 1000);
    }

    @Override
    public void hideErrorHint() {
        if (mErrorDialog != null)
            mErrorDialog.dismiss();//消失
    }

    /**
     * 展示成功信息
     *
     * @param hintStr
     */
    @Override
    public void showSuccessHint(String hintStr) {
        mSuccessDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(hintStr)
                .create();
        mSuccessDialog.show();//展示一下
        new Handler().postDelayed(() -> mSuccessDialog.dismiss(), 1000);
    }

    @Override
    public void hideSuccessHint() {
        if (mSuccessDialog != null)
            mSuccessDialog.dismiss();
    }


    @Override
    public void registerNetworkListener() {
        NetworkManager.getDefault().init(getApplication());
        NetworkManager.getDefault().setListener(this);
    }

    /**
     * 销毁时
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideLoading();//隐藏loading
        hideSuccessHint();//隐藏成功
        hideErrorHint();//隐藏失败
        ActivityCollector.removeActivity(this);
        NetworkManager.getDefault().logout();//注销监听
    }


    /**
     * 全屏
     */
    protected void fullScreen() {
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    /**
     * 链接
     *
     * @param type
     */
    @Override
    public void onConnected(NetworkUtils.NetworkType type) {
    }

    /**
     * 断开链接
     */
    @Override
    public void onDisConnected() {
    }


}
