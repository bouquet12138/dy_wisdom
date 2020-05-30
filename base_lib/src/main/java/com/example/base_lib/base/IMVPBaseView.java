package com.example.base_lib.base;

import android.content.Context;

/**
 * Created by xiaohan on 2018/3/4.
 */

public interface IMVPBaseView {


    /**
     * 显示加载中
     *
     * @param msg
     */
    void showLoading(String msg);

    /**
     * 关闭正在加载view
     */
    void hideLoading();


    /**
     * 显示提示
     *
     * @param msg
     */
    void showToast(String msg);

    /**
     * 获取上下文
     *
     * @return 上下文
     */
    Context getContext();

    /**
     * 跳到登陆页面
     */
    void startLogin();

    /**
     * 销毁Activity
     */
    void finishActivity();

    /**
     * 展示错误信息
     *
     * @param hintStr
     */
    void showErrorHint(String hintStr);

    /**
     * 隐藏错误提示
     */
    void hideErrorHint();

    /**
     * 展示成功信息
     *
     * @param hintStr
     */
    void showSuccessHint(String hintStr);

    /**
     * 隐藏成功提示
     */
    void hideSuccessHint();

    /**
     * 注册网络监听
     */
    void registerNetworkListener();

}
