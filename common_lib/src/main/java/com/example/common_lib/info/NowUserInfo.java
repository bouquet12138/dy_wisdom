package com.example.common_lib.info;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.blankj.utilcode.util.GsonUtils;
import com.example.base_lib.base.MyApplication;
import com.example.common_lib.java_bean.UserBean;


public class NowUserInfo {

    private static final String TAG = "NowUserInfo";

    private static UserBean sUserBean;

    /**
     * 设置学生信息
     */
    public static void setNowUserInfo(UserBean userBean) {
        sUserBean = userBean;//设置一下
        Log.d(TAG, "setUser: " + userBean);
        SharedPreferences sharedPreferences = MyApplication.getContext().
                getSharedPreferences("nowUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userBean", GsonUtils.toJson(userBean));
        editor.apply();
    }

    /**
     * 得到当前学生信息
     */
    public static UserBean getNowUserInfo() {
        if (sUserBean == null) {
            SharedPreferences sharedPreferences = MyApplication.getContext().
                    getSharedPreferences("nowUser", Context.MODE_PRIVATE);
            String userInfo = sharedPreferences.getString("userBean", "");//学生信息
            sUserBean = GsonUtils.fromJson(userInfo, UserBean.class);
        }

        if (sUserBean == null) {//还是空的话
            Log.e(TAG, "getNowStudentInfo: ");
        }
        return sUserBean;
    }


    /**
     * 得到当前用户的id
     *
     * @return
     */
    public static int getNowUserId() {

        UserBean userBean = getNowUserInfo();
        if (userBean == null)
            return 0;
        return userBean.getUser_id();//返回用户id

    }

    /**
     * 得到当前用户的手机号
     *
     * @return
     */
    public static String getNowUserPhone() {

        UserBean userBean = getNowUserInfo();
        if (userBean == null)
            return "";
        return userBean.getPhone();//得到手机号
    }


}
