/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.wisdomconsumption.custom;


import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.AppUtils;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.AppBean;
import com.example.common_lib.java_bean.BaseBean;
import com.xuexiang.xupdate.entity.UpdateEntity;
import com.xuexiang.xupdate.listener.IUpdateParseCallback;
import com.xuexiang.xupdate.proxy.IUpdateParser;

/**
 * 自定义更新解析器
 *
 * @author xuexiang
 * @since 2018/7/12 下午3:46
 */
public class CustomUpdateParser implements IUpdateParser {

    private static final String TAG = "CustomUpdateParser";

    @Override
    public UpdateEntity parseJson(String json) {
        return getParseResult(json);
    }

    private UpdateEntity getParseResult(String json) {
        Log.d(TAG, "getParseResult: " + json);
        BaseBean<AppBean> result = BaseBean.analysisBaseBean(json, AppBean.class);
        if (result != null && result.getCode() == 1 && result.getData() != null) {
            AppBean appBean = result.getData();
            Log.d(TAG, "getParseResult: 是否有更新 " + hasUpdate(appBean.getVersion_code(), appBean.getVersion_name()));
            return new UpdateEntity().
                    setHasUpdate(hasUpdate(appBean.getVersion_code(), appBean.getVersion_name()))
                    .setIsIgnorable(appBean.getUpdate_status() == 1)
                    .setVersionCode(appBean.getVersion_code())
                    .setVersionName(appBean.getVersion_name())
                    .setUpdateContent(appBean.getApp_describe())
                    .setDownloadUrl(ServerInfo.getServerAddress(appBean.getApp_url()))
                    .setSize(appBean.getApp_size() / 1024);
        }
        return null;
    }

    /**
     * @return
     */
    private boolean hasUpdate(int versionCode, String versionName) {

        if (versionCode > AppUtils.getAppVersionCode())
            return true;


        if (!TextUtils.isEmpty(versionName) && !TextUtils.isEmpty(AppUtils.getAppVersionName())) {
            String[] nowVersionsNames = AppUtils.getAppVersionName().split("\\.");
            String[] versionsNames = versionName.split("\\.");

            for (int i = 0; i < nowVersionsNames.length && i < versionsNames.length; i++) {
                int versionCode1 = Integer.parseInt(nowVersionsNames[i]);
                int versionCode2 = Integer.parseInt(versionsNames[i]);
                if (versionCode2 > versionCode1)
                    return true;
            }
        }

        return false;
    }

    @Override
    public void parseJson(String json, @NonNull IUpdateParseCallback callback) throws Exception {
        //当isAsyncParser为 true时调用该方法, 所以当isAsyncParser为false可以不实现
        callback.onParseResult(getParseResult(json));
    }


    @Override
    public boolean isAsyncParser() {
        return false;
    }
}
