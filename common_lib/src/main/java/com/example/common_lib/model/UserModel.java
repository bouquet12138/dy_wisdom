package com.example.common_lib.model;

import android.util.Log;

import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.util.OkHttpUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.example.common_lib.model.ModelUtil.postJsonBean;

public class UserModel {

    private static final String TAG = "UserModel";

    /**
     * 修改用户信息
     *
     * @param userBean
     * @param listener
     */
    public void modifyUserInfo(UserBean userBean, OnGetInfoListener<BaseBean> listener) {
        postJsonBean(userBean, "modify_user_info", listener);
    }

    /**
     * 得到用户信息
     *
     * @param user_id
     * @param listener
     */
    public void getUserInfo(int user_id, OnGetInfoListener<BaseBean<UserBean>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpUtil.postJson(ServerInfo.getServerAddress("get_user_info_by_id"), jsonObject.toString(), new okhttp3.Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                listener.onComplete();
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);//设置响应信息
                BaseBean<UserBean> baseBean = BaseBean.analysisBaseBean(responseData, UserBean.class);

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
     * 得到用户信息
     *
     * @param user_phone
     * @param listener
     */
    public void getUserInfoWithPhone(String user_phone, OnGetInfoListener<BaseBean<UserBean>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone_num", user_phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "getUserInfoWithPhone: " + jsonObject.toString());

        OkHttpUtil.postJson(ServerInfo.getServerAddress("get_user_info_with_phone"), jsonObject.toString(), new okhttp3.Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                listener.onComplete();
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);//设置响应信息
                BaseBean<UserBean> baseBean = BaseBean.analysisBaseBean(responseData, UserBean.class);

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
            jsonObject.put("old_login_password", old_login_password);
            jsonObject.put("new_login_password", new_login_password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "modify_login_password_with_old: " + jsonObject.toString());

        ModelUtil.postJson(jsonObject.toString(), "modify_login_password_with_old", listener);
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
            jsonObject.put("old_pay_password", old_pay_password);
            jsonObject.put("new_pay_password", new_pay_password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "modify_pay_password_with_old: " + jsonObject.toString());

        ModelUtil.postJson(jsonObject.toString(), "modify_pay_password_with_old", listener);
    }


    /**
     * 得到我的团队信息
     *
     * @param user_id
     * @param listener
     */
    public void myTeam(int user_id, OnGetInfoListener<BaseBean<List<UserBean>>> listener) {


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "myTeam: " + jsonObject.toString());

        OkHttpUtil.postJson(ServerInfo.getServerAddress("my_team"), jsonObject.toString(), new okhttp3.Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                listener.onComplete();
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);//设置响应信息
                BaseBean<List<UserBean>> baseBean = BaseBean.analysisBaseBeanList(responseData, UserBean.class);

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
