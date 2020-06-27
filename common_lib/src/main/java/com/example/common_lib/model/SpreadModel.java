package com.example.common_lib.model;

import android.util.Log;

import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.SetRecordBean;
import com.example.common_lib.java_bean.SpreadRecordBean;
import com.example.common_lib.util.OkHttpUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class SpreadModel {

    private static final String TAG = "SpreadModel";

    /**
     * 初始化推广记录
     *
     * @param user_id
     * @param integral_type
     * @param listener
     */
    public void initSpreadRecord(int user_id, String integral_type, OnGetInfoListener<BaseBean<List<SpreadRecordBean>>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);//用户id
            jsonObject.put("integral_type", integral_type);//积分类型
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ModelListUtil<SpreadRecordBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("init_spread_record", jsonObject.toString(), listener, SpreadRecordBean.class);
    }

    /**
     * 加载更多推广记录
     *
     * @param user_id
     * @param integral_type
     * @param listener
     */
    public void loadMoreSpreadRecord(int user_id, String integral_type, int spread_id, OnGetInfoListener<BaseBean<List<SpreadRecordBean>>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);//用户id
            jsonObject.put("integral_type", integral_type);//积分类型
            jsonObject.put("spread_id", spread_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //init_spread_record

        ModelListUtil<SpreadRecordBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("load_more_spread_record", jsonObject.toString(), listener, SpreadRecordBean.class);
    }

}
