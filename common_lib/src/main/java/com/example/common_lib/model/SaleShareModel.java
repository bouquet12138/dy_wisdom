package com.example.common_lib.model;

import android.util.Log;

import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.SaleShareRecordBean;
import com.example.common_lib.util.OkHttpUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class SaleShareModel {

    private static final String TAG = "SaleShareModel";

    public void initSaleShareRecord(int user_id, String integral_type, OnGetInfoListener<BaseBean<List<SaleShareRecordBean>>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);//用户id
            jsonObject.put("integral_type", integral_type);//积分类型
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //init_sale_share_record

        getSaleShareRecord("init_sale_share_record", jsonObject.toString(), listener);//得到销售记录
    }

    public void loadMoreSaleShareRecord(int user_id, String integral_type, int sale_share_id, OnGetInfoListener<BaseBean<List<SaleShareRecordBean>>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);//用户id
            jsonObject.put("integral_type", integral_type);//积分类型
            jsonObject.put("sale_share_id", sale_share_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //init_sale_share_record

        getSaleShareRecord("load_more_sale_share_record", jsonObject.toString(), listener);//得到销售记录
    }

    private void getSaleShareRecord(String url, String json, OnGetInfoListener<BaseBean<List<SaleShareRecordBean>>> listener) {
        OkHttpUtil.postJson(ServerInfo.getServerAddress(url), json, new okhttp3.Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                listener.onComplete();
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);//设置响应信息
                BaseBean<List<SaleShareRecordBean>> baseBean = BaseBean.analysisBaseBeanList(responseData, SaleShareRecordBean.class);

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
