package com.example.common_lib.model;


import android.util.Log;

import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.DeliverNumBean;
import com.example.common_lib.java_bean.GoodBean;
import com.example.common_lib.java_bean.OrderRecordBean;
import com.example.common_lib.util.OkHttpUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


public class GoodModel {

    private static final String TAG = "GoodModel";

    /**
     * 初始化商品记录
     */
    public void initGoodList(String good_type, OnGetInfoListener<BaseBean<List<GoodBean>>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("good_type", good_type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ModelListUtil<GoodBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("init_good_list", jsonObject.toString(), listener, GoodBean.class);
    }

    /**
     * 初始化商品记录
     */
    public void loadMoreGoodList(String good_type, Integer good_id, OnGetInfoListener<BaseBean<List<GoodBean>>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("good_type", good_type);
            jsonObject.put("good_id", good_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ModelListUtil<GoodBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("load_more_good_list", jsonObject.toString(), listener, GoodBean.class);
    }

    /**
     * 买商品
     */
    public void buyGood(OrderRecordBean orderRecordBean, OnGetInfoListener<BaseBean> listener) {
        ModelUtil.postJsonBean(orderRecordBean, "buy_good", listener);
    }

    /**
     * 初始化订单记录
     *
     * @param user_id
     * @param status
     * @param listener
     */
    public void initOrderRecord(int user_id, String status, OnGetInfoListener<BaseBean<List<OrderRecordBean>>> listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ModelListUtil<OrderRecordBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("init_order_record", jsonObject.toString(), listener, OrderRecordBean.class);
    }

    /**
     * 加载更多订单记录
     *
     * @param user_id
     * @param status
     * @param listener
     */
    public void loadMoreOrderRecord(int user_id, String status, int order_id, OnGetInfoListener<BaseBean<List<OrderRecordBean>>> listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("status", status);
            jsonObject.put("order_id", order_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ModelListUtil<OrderRecordBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("load_more_order_record", jsonObject.toString(), listener, OrderRecordBean.class);
    }


    public void getDeliverGoodNum(Integer user_id, OnGetInfoListener<BaseBean<DeliverNumBean>> listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtil.postJson(ServerInfo.getServerAddress("get_deliver_good_num"), jsonObject.toString(), new okhttp3.Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                listener.onComplete();
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);//设置响应信息
                BaseBean<DeliverNumBean> baseBean = BaseBean.analysisBaseBean(responseData, DeliverNumBean.class);

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
