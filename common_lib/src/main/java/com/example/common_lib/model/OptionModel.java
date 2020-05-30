package com.example.common_lib.model;

import android.util.Log;

import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.BaseBean;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.common_lib.model.ModelUtil.postJson;

public class OptionModel {

    private static final String TAG = "OptionModel";

    public void publicOption(int user_id, String title, String content, OnGetInfoListener<BaseBean> listener) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("title", title);
            jsonObject.put("content", content);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "publicOption: " + jsonObject.toString());
        Log.d(TAG, "publicOption: " + ServerInfo.getServerAddress("public_option"));

        postJson(jsonObject.toString(), "public_option", listener);
    }


}
