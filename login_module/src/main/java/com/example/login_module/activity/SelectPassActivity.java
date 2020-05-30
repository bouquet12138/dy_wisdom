package com.example.login_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.login_module.R;

public class SelectPassActivity extends AppMvpBaseActivity implements View.OnClickListener {

    private ViewGroup mModifyPass;
    private ViewGroup mModifyPayPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view
        setSubmitEnable(false);//提交按钮不可见
        initView();//初始化view
        initListener();//初始化监听
    }

    /**
     * 初始化view
     */
    private void initView() {
        mModifyPass = findViewById(R.id.modifyPass);
        mModifyPayPass = findViewById(R.id.modifyPayPass);
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mModifyPass.setOnClickListener(this);
        mModifyPayPass.setOnClickListener(this);
    }

    @Override
    protected String getTitleName() {
        return "忘记密码";
    }

    @Override
    protected String getRightTextName() {
        return "";
    }


    @Override
    protected int getNormalViewId() {
        return R.layout.login_layout_select_pass;
    }

    @Override
    protected void onRefresh() {

    }

    @Override
    protected void onFloatBtClick() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.modifyPass) {
            startActivity(new Intent(SelectPassActivity.this, ForgetLoginPassActivity.class));//启动忘记登陆密码界面
        } else if (id == R.id.modifyPayPass) {
            startActivity(new Intent(SelectPassActivity.this, ForgetPayPassActivity.class));//启动忘记支付密码界面
        }
    }
}
