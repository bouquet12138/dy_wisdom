package com.example.common_lib.model;

import android.util.Log;

import com.blankj.utilcode.util.GsonUtils;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.bean.SMSBean;
import com.example.common_lib.util.OkHttpUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SMSModel {

    private static final String TAG = "SMSModel";

    public void sendQrCode(String mobile, String qrCode, OnGetInfoListener<SMSBean> listener) {

        String address = "http://v.juhe.cn/sms/send?mobile=" + mobile + "&tpl_id=214625&tpl_value=%23code%23%3D" + qrCode + "&key=86f66e90100d21a21ab62c3eb8373249";

        OkHttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                listener.onComplete();
                listener.onNetError();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                listener.onComplete();
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);//设置响应信息
                SMSBean smsBean = GsonUtils.fromJson(responseData, SMSBean.class);
                listener.onResult(smsBean);//网络
            }
        });

    }

}
