package com.example.common_lib.model;

import android.util.Log;

import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.AboutUsBean;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.util.OkHttpUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AboutUsModel {

    private static final String TAG = "AboutUsModel";
    
    /**
     * 得到app信息
     */
    public void getAboutUs(OnGetInfoListener<BaseBean<AboutUsBean>> listener) {

        OkHttpUtil.postJson(ServerInfo.getServerAddress("get_about_us"), "", new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                listener.onComplete();
                listener.onNetError();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                listener.onComplete();
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);//设置响应信息
                BaseBean<AboutUsBean> info = BaseBean.analysisBaseBean(responseData, AboutUsBean.class);
                if (info == null)
                    listener.onNetError();
                else
                    listener.onResult(info);//传递出去
            }
        });

    }

}
