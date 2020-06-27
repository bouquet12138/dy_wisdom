package com.example.common_lib.model;

import com.example.base_lib.listener.OnGetInfoListener;

import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.RedeemRecordBean;
import com.example.common_lib.java_bean.TransferBean;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;


public class RedeemModel {

    private static final String TAG = "RedeemModel";

    public void initRedeemRecord(int user_id, String integral_type, OnGetInfoListener<BaseBean<List<RedeemRecordBean>>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);//用户id
            jsonObject.put("integral_type", integral_type);//积分类型
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //init_redeem_record

        ModelListUtil<RedeemRecordBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("init_redeem_record", jsonObject.toString(), listener, RedeemRecordBean.class);
    }

    public void loadMoreRedeemRecord(int user_id, String integral_type, int redeem_id, OnGetInfoListener<BaseBean<List<RedeemRecordBean>>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);//用户id
            jsonObject.put("integral_type", integral_type);//积分类型
            jsonObject.put("redeem_id", redeem_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //init_redeem_record

        ModelListUtil<RedeemRecordBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("load_more_redeem_record", jsonObject.toString(), listener, RedeemRecordBean.class);

    }


    /**
     * 向商家付款
     *
     * @param transferBean
     * @param listener
     */
    public void payToMerchant(TransferBean transferBean, OnGetInfoListener<BaseBean> listener) {
        ModelUtil.postJsonBean(transferBean, "pay_to_merchant", listener);
    }

}
