package com.example.common_lib.model;

import android.util.Log;

import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.FlashBean;
import com.example.common_lib.java_bean.SaleShareRecordBean;
import com.example.common_lib.java_bean.SetBean;
import com.example.common_lib.java_bean.SetRecordBean;
import com.example.common_lib.util.OkHttpUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 套餐model
 */
public class SetModel {

    private static final String TAG = "SetModel";

    /**
     * 得到套餐信息
     */
    public void getSetByName(String setName, OnGetInfoListener<BaseBean<SetBean>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("set_name", setName);//商店id
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtil.postJson(ServerInfo.getServerAddress("get_set_by_name"), jsonObject.toString(), new okhttp3.Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                listener.onComplete();
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);//设置响应信息
                BaseBean<SetBean> baseBean = BaseBean.analysisBaseBean(responseData, SetBean.class);//解析一下后台返回的数据

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

    /**
     * 得到套餐列表
     */
    public void getSetList(int user_id, OnGetInfoListener<BaseBean<List<SetBean>>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);//商店id
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ModelListUtil<SetBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("get_set_list", jsonObject.toString(), listener, SetBean.class);
    }

    /**
     * 购买套餐
     *
     * @param user_id
     * @param set_id
     * @param pay_pass
     */
    public void buySet(int user_id, int set_id, String pay_pass, OnGetInfoListener<BaseBean> listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);//用户id
            jsonObject.put("set_id", set_id);//套餐id
            jsonObject.put("pay_pass", pay_pass);//支付密码
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ModelUtil.postJson(jsonObject.toString(), "buy_set", listener);

    }

    /**
     * 初始化可领奖励
     *
     * @param user_id
     * @param listener
     */
    public void initCanGetReward(int user_id, OnGetInfoListener<BaseBean<List<SetRecordBean>>> listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);//用户id
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ModelListUtil<SetRecordBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("init_can_get_reward", jsonObject.toString(), listener, SetRecordBean.class);
    }

    /**
     * 加载更多可领奖励
     *
     * @param user_id
     * @param listener
     */
    public void loadMoreCanGetReward(int user_id, int set_record_id, OnGetInfoListener<BaseBean<List<SetRecordBean>>> listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);//用户id
            jsonObject.put("set_record_id", set_record_id);//用户id
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ModelListUtil<SetRecordBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("load_more_can_get_reward", jsonObject.toString(), listener, SetRecordBean.class);
    }


}
