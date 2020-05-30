package com.example.common_lib.util;

import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by xiaohan on 2017/11/18.
 */

public class OkHttpUtil {

    public static final MediaType JSON = MediaType.Companion.parse("application/json;charset=utf-8");



    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient client =
                httpBuilder.connectTimeout(5, TimeUnit.SECONDS)
                        .writeTimeout(5, TimeUnit.SECONDS)
                        .build();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback, int outTime) {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient client =
                httpBuilder.connectTimeout(outTime, TimeUnit.SECONDS)
                        .writeTimeout(outTime, TimeUnit.SECONDS)
                        .build();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }


    /**
     * 封装好的用于提交表单的类
     *
     * @param address
     * @param keyAndValues
     * @param callback
     */
    public static void postOkHttpFormRequest(String address,
                                             Map<String, String> keyAndValues, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        if (keyAndValues != null) {
            Iterator iterator = keyAndValues.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
                String key = entry.getKey();
                String value = entry.getValue();
                builder.add(key, value);
            }
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder().url(address).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }


    /**
     * 提交一组文件
     */
    public static void postForm(String address, Map<String, String> map, String fileHead, String filePath, okhttp3.Callback callback) {

        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            Log.d("HttpUtil ", "postImage: " + filePath);

            if (file.exists()) {
                MediaType mediaType = MediaType.Companion.parse("text/x-markdown; charset=utf-8");
                RequestBody fileBody = RequestBody.Companion.create(file, mediaType);
                requestBody.addFormDataPart(fileHead, file.getName(), fileBody);
            }
        } else {
            requestBody.addFormDataPart(fileHead, "", new RequestBody() {
                @Override
                public MediaType contentType() {
                    return null;
                }

                @Override
                public void writeTo(BufferedSink sink) {

                }
            });
        }

        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                Log.d("OKHttpUtil", "postForm: " + entry.getKey() + "  " + entry.getValue());
                requestBody.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody.build())
                .build();
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = httpBuilder.connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 提交文件
     *
     * @param address
     * @param map
     * @param fileHead
     * @param filePath 文件链接
     */
    public static String postAynForm(String address, Map<String, String> map, String fileHead, String filePath) {

        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        Log.d("HttpUtil ", "postImage: " + filePath);
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            if (file.exists()) {
                MediaType mediaType = MediaType.Companion.parse("text/x-markdown; charset=utf-8");
                RequestBody fileBody = RequestBody.Companion.create(file, mediaType);
                requestBody.addFormDataPart(fileHead, file.getName(), fileBody);
            } else
                return "";
        } else {
            return "";//上传失败
        }

        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                Log.d("OKHttpUtil", "postForm: " + entry.getKey() + "  " + entry.getValue());
                requestBody.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }

        Request request = new Request.Builder()
                .url(address)
                .post(requestBody.build())
                .build();
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = httpBuilder.connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful())
                return response.body().string();//将数据返回出去
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void postJson(String address, String json, okhttp3.Callback callback) {
        postJson(address, json, callback, 5);
    }

    public static void postJson(String address, String json, okhttp3.Callback callback, int outTime) {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient =
                httpBuilder.connectTimeout(outTime, TimeUnit.SECONDS)
                        .writeTimeout(outTime, TimeUnit.SECONDS)
                        .build();
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        //json为String类型的json数据

        RequestBody requestBody = RequestBody.Companion.create(json, JSON);
        //创建一个请求对象
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }


    public static String postAynJson(String address, String json) {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient =
                httpBuilder.connectTimeout(5, TimeUnit.SECONDS)
                        .writeTimeout(5, TimeUnit.SECONDS)
                        .build();
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        //json为String类型的json数据

        RequestBody requestBody = RequestBody.Companion.create(json, JSON);
        //创建一个请求对象
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

}
