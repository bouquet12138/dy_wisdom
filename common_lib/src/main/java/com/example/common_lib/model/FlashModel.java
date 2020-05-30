package com.example.common_lib.model;

import android.util.Log;


import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.FlashBean;
import com.example.common_lib.util.OkHttpUtil;


import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class FlashModel {

    private static final String TAG = "FlashModel";

    /**
     * 上传快讯
     */
    public void upload_flash(FlashBean flashBean, OnGetInfoListener<BaseBean> onGetInfoListener) {
        ModelUtil.postJsonBean(flashBean, "upload_flash", onGetInfoListener);
    }

    /**
     * 初始化商店信息
     */
    public void initFlashInfo(OnGetInfoListener<BaseBean<List<FlashBean>>> listener) {
        getFlashList("init_flash", "", listener);
    }

    /**
     * 刷新快讯信息
     *
     * @param flash_id
     * @param listener
     */
    public void refreshFlashInfo(int flash_id, OnGetInfoListener<BaseBean<List<FlashBean>>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("flash_id", flash_id);//商店id
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getFlashList("refresh_flash", jsonObject.toString(), listener);
    }

    /**
     * 加载更多快讯信息
     *
     * @param flash_id
     * @param listener
     */
    public void loadMoreFlashInfo(int flash_id, OnGetInfoListener<BaseBean<List<FlashBean>>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("flash_id", flash_id);//商店id
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getFlashList("load_more_flash", jsonObject.toString(), listener);
    }


    /**
     * 得到快讯列表
     */
    public void getFlashList(String url, String json, OnGetInfoListener<BaseBean<List<FlashBean>>> listener) {

        Log.d(TAG, "getFlashList: url " + url + " json " + json);

        OkHttpUtil.postJson(ServerInfo.getServerAddress(url), json, new okhttp3.Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                listener.onComplete();
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);//设置响应信息
                BaseBean<List<FlashBean>> baseBean = BaseBean.analysisBaseBeanList(responseData, FlashBean.class);

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

    public void addFlashReadingVolume(int flash_id, OnGetInfoListener<BaseBean> listener) {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("flash_id", flash_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ModelUtil.postJson(jsonObject.toString(), "add_flash_reading_volume", listener);
    }

}
