package com.example.common_lib.model;

import android.util.Log;

import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.java_bean.WithdrawBean;
import com.example.common_lib.util.OkHttpUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 提现后台接口
 */
public class WithdrawModel {

    private static final String TAG = "WithdrawModel";


    /**
     * 初始化提现记录
     *
     * @param user_id
     * @param listener
     */
    public void initWithdrawRecord(int user_id, String withdraw_type, OnGetInfoListener<BaseBean<List<WithdrawBean>>> listener) {


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("withdraw_type", withdraw_type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ModelListUtil<WithdrawBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("init_withdraw_record", jsonObject.toString(), listener, WithdrawBean.class);
    }

    public void loadMoreWithdrawRecord(int user_id, String withdraw_type, int withdraw_id, OnGetInfoListener<BaseBean<List<WithdrawBean>>> listener) {


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("withdraw_type", withdraw_type);
            jsonObject.put("withdraw_id", withdraw_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ModelListUtil<WithdrawBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("load_more_withdraw_record", jsonObject.toString(), listener, WithdrawBean.class);
    }

    /**
     * 提现
     */
    public void withdraw(WithdrawBean withdrawBean, OnGetInfoListener<BaseBean> listener) {
        ModelUtil.postJsonBean(withdrawBean, "withdraw", listener);
    }

}
