package com.example.common_lib.model;

import android.util.Log;

import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.BannerBean;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.BonusRecordBean;
import com.example.common_lib.util.OkHttpUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class BonusModel {

    private static final String TAG = "BonusModel";

    public void initBonusRecord(int user_id, String integral_type, OnGetInfoListener<BaseBean<List<BonusRecordBean>>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);//用户id
            jsonObject.put("integral_type", integral_type);//积分类型
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //init_bonus_record

        ModelListUtil<BonusRecordBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("init_bonus_record", jsonObject.toString(), listener, BonusRecordBean.class);
    }

    public void loadMoreBonusRecord(int user_id, String integral_type, int bonus_id, OnGetInfoListener<BaseBean<List<BonusRecordBean>>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);//用户id
            jsonObject.put("integral_type", integral_type);//积分类型
            jsonObject.put("bonus_id", bonus_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //init_bonus_record

        ModelListUtil<BonusRecordBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("load_more_bonus_record", jsonObject.toString(), listener, BonusRecordBean.class);
    }

}
