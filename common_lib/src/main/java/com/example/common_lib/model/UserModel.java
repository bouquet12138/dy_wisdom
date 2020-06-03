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
     * @param phone
     * @param listener
     */
    public void getUserInfoWithPhone(String phone, OnGetInfoListener<BaseBean<UserBean>> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "getUserInfoWithPhone: " + jsonObject.toString());

        OkHttpUtil.postJson(ServerInfo.getServerAddress("get_user_info_by_phone"), jsonObject.toString(), new okhttp3.Callback() {

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
