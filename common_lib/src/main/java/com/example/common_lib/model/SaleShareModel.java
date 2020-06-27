package com.example.common_lib.model;

import android.util.Log;

import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.RedeemRecordBean;
import com.example.common_lib.java_bean.SaleShareRecordBean;
import com.example.common_lib.java_bean.TransferBean;
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

        ModelListUtil<SaleShareRecordBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("init_sale_share_record", jsonObject.toString(), listener, SaleShareRecordBean.class);

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

        ModelListUtil<SaleShareRecordBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("load_more_sale_share_record", jsonObject.toString(), listener, SaleShareRecordBean.class);
    }


    /**
     * 初始化团队收益明细
     *
     * @param user_id
     * @param role
     * @param listener
     */
    public void initTeamSaleIntegerDetail(int user_id, String role, OnGetInfoListener<BaseBean<List<SaleShareRecordBean>>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);//用户id
            jsonObject.put("role", role);//用户等级
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "initTeamSaleIntegerDetail: " + jsonObject.toString());

        ModelListUtil<SaleShareRecordBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("init_team_sale_integer_detail", jsonObject.toString(), listener, SaleShareRecordBean.class);
    }

    /**
     * 加载更多团队收益明细
     *
     * @param user_id
     * @param role
     * @param listener
     */
    public void loadMoreTeamSaleIntegerDetail(int user_id, String role, int sale_share_id, OnGetInfoListener<BaseBean<List<SaleShareRecordBean>>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);//用户id
            jsonObject.put("role", role);//积分类型
            jsonObject.put("sale_share_id", sale_share_id);//销售积分
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //init_sale_share_record

        ModelListUtil<SaleShareRecordBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("load_more_sale_integer_detail", jsonObject.toString(), listener, SaleShareRecordBean.class);
    }

    /**
     * 销售积分转兑换积分
     *
     * @param transferBean
     * @param listener
     */
    public void sale_to_redeem(TransferBean transferBean, OnGetInfoListener<BaseBean> listener) {
        ModelUtil.postJsonBean(transferBean, "sale_to_redeem", listener);
    }

}
