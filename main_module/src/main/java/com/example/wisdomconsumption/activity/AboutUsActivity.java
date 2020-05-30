package com.example.wisdomconsumption.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.wisdomconsumption.R;


public class AboutUsActivity extends AppMvpBaseActivity {

    private TextView mAboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSubmitEnable(false);//提交不可用
        showNormalView();//展示正常view
        initView();
        mAboutUs.setText(Html.fromHtml(getResources().getString(R.string.main_about_us)));
    }

    @Override
    protected String getTitleName() {
        return "关于我们";
    }

    @Override
    protected String getRightTextName() {
        return null;
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.main_layout_about_us;
    }

    @Override
    protected void onRefresh() {

    }

    @Override
    protected void onFloatBtClick() {

    }

    private void initView() {
        mAboutUs = findViewById(R.id.aboutUs);
    }
}
