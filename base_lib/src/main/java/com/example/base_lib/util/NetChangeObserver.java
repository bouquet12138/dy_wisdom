package com.example.base_lib.util;

import com.blankj.utilcode.util.NetworkUtils;

/**
 * 网络变化监听
 */
public interface NetChangeObserver {
    /**
     * 网络连接成功
     */
    void onConnected(NetworkUtils.NetworkType type);
    /**
     * 网络断开
     */
    void onDisConnected();
}

