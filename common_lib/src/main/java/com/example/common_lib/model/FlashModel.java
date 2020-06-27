package com.example.common_lib.model;

import android.util.Log;


import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.BonusRecordBean;
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
        ModelListUtil<FlashBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("init_flash", "", listener, FlashBean.class);
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

        ModelListUtil<FlashBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("refresh_flash", jsonObject.toString(), listener, FlashBean.class);
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
        ModelListUtil<FlashBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("load_more_flash", jsonObject.toString(), listener, FlashBean.class);

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
