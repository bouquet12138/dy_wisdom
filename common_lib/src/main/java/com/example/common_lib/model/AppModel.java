package com.example.common_lib.model;

import android.util.Log;

import com.example.base_lib.listener.OnGetInfoListener;

import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.AppBean;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.util.OkHttpUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AppModel {

    private static final String TAG = "AppModel";

    /**
     * 得到app信息
     */
    public void getAppInfo(OnGetInfoListener<BaseBean<AppBean>> listener) {

        OkHttpUtil.postJson(ServerInfo.getServerAddress("get_app_info"), "", new Callback() {
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
                BaseBean<AppBean> info = BaseBean.analysisBaseBean(responseData, AppBean.class);
                if (info == null)
                    listener.onNetError();
                else
                    listener.onResult(info);//传递出去
            }
        });

    }

}
