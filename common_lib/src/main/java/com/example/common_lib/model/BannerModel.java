package com.example.common_lib.model;

import android.util.Log;

import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.BannerBean;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.FlashBean;
import com.example.common_lib.util.OkHttpUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BannerModel {

    private static final String TAG = "BannerModel";

    /**
     * 初始化一下轮播图信息
     */
    public void initBanner(OnGetInfoListener<BaseBean<List<BannerBean>>> listener) {

        OkHttpUtil.postJson(ServerInfo.getServerAddress("init_banner"), "", new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                listener.onComplete();
                listener.onNetError();//网络错误
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                listener.onComplete();
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);//设置响应信息
                BaseBean<List<BannerBean>> baseBean = BaseBean.analysisBaseBeanList(responseData, BannerBean.class);

                if (baseBean != null)
                    listener.onResult(baseBean);//传递出去
                else
                    listener.onNetError();
            }
        });

    }

}
