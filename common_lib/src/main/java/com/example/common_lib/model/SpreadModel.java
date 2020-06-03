package com.example.common_lib.model;

import android.util.Log;

import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.BaseBean;
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

        getSpreadRecord("init_spread_record", jsonObject.toString(), listener);//得到销售记录
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

        getSpreadRecord("load_more_spread_record", jsonObject.toString(), listener);//得到销售记录
    }

    private void getSpreadRecord(String url, String json, OnGetInfoListener<BaseBean<List<SpreadRecordBean>>> listener) {
        OkHttpUtil.postJson(ServerInfo.getServerAddress(url), json, new okhttp3.Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                listener.onComplete();
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);//设置响应信息
                BaseBean<List<SpreadRecordBean>> baseBean = BaseBean.analysisBaseBeanList(responseData, SpreadRecordBean.class);

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
