package com.example.wisdomconsumption.activity;


import android.os.Bundle;

import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.wisdomconsumption.R;

public class ServiceCenterActivity extends AppMvpBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view
        setSubmitEnable(false);//提交不可用
    }

    @Override
    protected String getTitleName() {
        return "客服中心";
    }

    @Override
    protected String getRightTextName() {
        return "";
    }



    @Override
    protected int getNormalViewId() {
        return R.layout.main_layout_service_center;
    }

    @Override
    protected void onRefresh() {

    }

    @Override
    protected void onFloatBtClick() {

    }
}
