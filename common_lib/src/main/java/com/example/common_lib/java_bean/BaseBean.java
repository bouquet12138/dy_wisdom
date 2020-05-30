package com.example.common_lib.java_bean;

import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.GsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BaseBean<T> {

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private int code = 0;

    private String msg;

    private T data;

    @Override
    public String toString() {
        return "BaseBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }


    public static <T> BaseBean<T> analysisBaseBean(String json, Class<T> cls) {
        BaseBean<T> baseBean = new BaseBean<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            baseBean.setCode(jsonObject.getInt("code"));
            baseBean.setMsg(jsonObject.getString("msg"));
            String data = jsonObject.getString("data");
            T t = GsonUtils.fromJson(data, cls);
            baseBean.setData(t);//设置数据
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return baseBean;
    }


    public static <T> BaseBean<List<T>> analysisBaseBeanList(String json, Class<T> cls) {
        BaseBean<List<T>> baseBean = new BaseBean<>();

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);
            baseBean.setCode(jsonObject.getInt("code"));
            baseBean.setMsg(jsonObject.getString("msg"));
            String data = jsonObject.getString("data");
            Log.d("data", "analysisBaseBeanList: " + data);
            if (TextUtils.isEmpty(data) || "null".equals(data)) {
                Log.d("GsonUtil", "analysisBaseBeanList: 空 ");
                return baseBean;//直接返回
            }

            JSONArray array = new JSONArray(data);

            List<T> list = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                String d = array.getString(i);
                Log.d("GsonUtil", "analysisBaseBeanList: " + d);
                T t = GsonUtils.fromJson(d, cls);
                if (t != null)
                    list.add(t);//将对象添加进来
            }
            baseBean.setData(list);//设置数据

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return baseBean;
    }

}
