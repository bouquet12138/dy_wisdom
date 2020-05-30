package com.example.common_lib.model;

import android.util.Log;

import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.util.OkHttpUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginAndRegisterModel {

    private static final String TAG = "LoginAndRegisterModel";

    /**
     * 登陆
     *
     * @param phone
     * @param login_pass
     * @param listener
     */
    public void login(String phone, String login_pass, OnGetInfoListener<BaseBean<Integer>> listener) {

        String json = "{\"phone\": \"" + phone + "\",\"login_pass\": \"" + login_pass + "\"}";

        Log.d(TAG, "login: " + json);
        Log.d(TAG, "login: 地址 " + ServerInfo.getServerAddress("user_login"));
        OkHttpUtil.postJson(ServerInfo.getServerAddress("user_login"), json, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: " + e);
                listener.onComplete();//完成
                listener.onNetError();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);
                listener.onComplete();//完成
                BaseBean<Integer> result = BaseBean.analysisBaseBean(responseData, Integer.class);
                if (result == null)
                    listener.onNetError();
                else
                    listener.onResult(result);
            }
        });
    }

    /**
     * "phone_num": "1575215661",
     * 	"login_password": "123456",
     * 	"pay_password": "212121",
     * 	"recommend_user_phone": "12112333213"
     * 	"vertex_user_phone":"123"
     */
    /**
     * 注册
     *
     * @param phone_num
     * @param login_password
     * @param listener
     */
    public void register(String phone_num, String login_password, String pay_password, String recommend_user_phone,
                         String vertex_user_phone, OnGetInfoListener<BaseBean<Integer>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone_num", phone_num);
            jsonObject.put("login_password", login_password);
            jsonObject.put("pay_password", pay_password);
            jsonObject.put("recommend_user_phone", recommend_user_phone);
            jsonObject.put("recommend_user_phone", recommend_user_phone);
            jsonObject.put("vertex_user_phone", vertex_user_phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtil.postJson(ServerInfo.getServerAddress("register"), jsonObject.toString(), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: " + e);
                listener.onComplete();//完成
                listener.onNetError();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);
                listener.onComplete();//完成
                BaseBean<Integer> result = BaseBean.analysisBaseBean(responseData, Integer.class);
                if (result == null)
                    listener.onNetError();
                else
                    listener.onResult(result);
            }
        });

    }

    /**
     * 修改登陆密码
     *
     * @param user_id
     * @param old_login_password
     * @param new_login_password
     */
    public void modify_login_password_with_old(int user_id, String old_login_password,
                                               String new_login_password, OnGetInfoListener<BaseBean> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("old_login_pass", old_login_password);
            jsonObject.put("new_login_pass", new_login_password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "modify_login_password_with_old: " + jsonObject.toString());

        ModelUtil.postJson(jsonObject.toString(), "modify_login_password_with_old", listener);
    }

    /**
     * 修改登陆密码
     *
     * @param user_id
     * @param new_login_password
     */
    public void modify_login_password(int user_id, String new_login_password,
                                      OnGetInfoListener<BaseBean> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("new_login_pass", new_login_password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "modify_login_password: " + jsonObject.toString());

        ModelUtil.postJson(jsonObject.toString(), "modify_login_password", listener);
    }

    /**
     * 修改支付密码
     *
     * @param user_id
     * @param old_pay_password
     * @param new_pay_password
     */
    public void modify_pay_password_with_old(int user_id, String old_pay_password,
                                             String new_pay_password, OnGetInfoListener<BaseBean> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("old_pay_pass", old_pay_password);
            jsonObject.put("new_pay_pass", new_pay_password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "modify_pay_password_with_old: " + jsonObject.toString());

        ModelUtil.postJson(jsonObject.toString(), "modify_pay_password_with_old", listener);
    }

    /**
     * 修改支付密码
     *
     * @param user_id
     * @param new_pay_password
     */
    public void modify_pay_password(int user_id, String new_pay_password,
                                    OnGetInfoListener<BaseBean> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("new_pay_pass", new_pay_password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "modify_pay_password: " + jsonObject.toString());

        ModelUtil.postJson(jsonObject.toString(), "modify_pay_password", listener);
    }



}
