package com.example.common_lib.model;

import android.util.Log;

import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.SpreadRecordBean;
import com.example.common_lib.java_bean.StoreBean;
import com.example.common_lib.util.OkHttpUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class StoreModel {

    private static final String TAG = "StoreModel";

    /**
     * 初始化商店信息
     */
    public void initStoreInfo(String province, String city, String district, String store_name, OnGetInfoListener<BaseBean<List<StoreBean>>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("province", province);
            jsonObject.put("city", city);
            jsonObject.put("district", district);
            jsonObject.put("store_name", store_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ModelListUtil<StoreBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("init_store_info", jsonObject.toString(), listener, StoreBean.class);
    }

    /**
     * 刷新商店信息
     *
     * @param store_id
     * @param province
     * @param city
     * @param district
     * @param store_name
     * @param listener
     */
    public void refreshStoreInfo(int store_id, String province, String city, String district, String store_name, OnGetInfoListener<BaseBean<List<StoreBean>>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("store_id", store_id);
            jsonObject.put("province", province);
            jsonObject.put("city", city);
            jsonObject.put("district", district);
            jsonObject.put("store_name", store_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ModelListUtil<StoreBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("refresh_store_info", jsonObject.toString(), listener, StoreBean.class);
    }

    /**
     * 下拉加载更多商店信息
     *
     * @param store_id
     * @param province
     * @param city
     * @param district
     * @param store_name
     * @param listener
     */
    public void loadMoreStoreInfo(int store_id, String province, String city, String district, String store_name, OnGetInfoListener<BaseBean<List<StoreBean>>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("store_id", store_id);
            jsonObject.put("province", province);
            jsonObject.put("city", city);
            jsonObject.put("district", district);
            jsonObject.put("store_name", store_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ModelListUtil<StoreBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("load_more_store_info", jsonObject.toString(), listener, StoreBean.class);
    }


}
