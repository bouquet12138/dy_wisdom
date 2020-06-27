package com.example.common_lib.model;

import android.util.Log;

import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.GoodBean;
import com.example.common_lib.util.OkHttpUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class ModelListUtil<T> {

    private static final String TAG = "ModelListUtil";

    public void getList(String url, String json, OnGetInfoListener<BaseBean<List<T>>> listener, Class<T> cls) {
        Log.d(TAG, "getStoreList: " + json);
        OkHttpUtil.postJson(ServerInfo.getServerAddress(url), json, new okhttp3.Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                listener.onComplete();
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);//设置响应信息
                BaseBean<List<T>> baseBean = BaseBean.analysisBaseBeanList(responseData, cls);

                if (baseBean != null)
                    listener.onResult(baseBean);//传递出去
                else
                    listener.onNetError();

            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: ");
                listener.onComplete();
                listener.onNetError();//网络错误
            }
        });
    }

}
