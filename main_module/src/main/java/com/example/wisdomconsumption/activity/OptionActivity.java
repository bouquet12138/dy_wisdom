package com.example.wisdomconsumption.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.wisdomconsumption.R;
import com.example.wisdomconsumption.contract.OptionContract;
import com.example.wisdomconsumption.presenter.OptionPresenter;

public class OptionActivity extends AppMvpBaseActivity implements OptionContract.IView {

    private OptionPresenter mPresenter = new OptionPresenter();

    private EditText mTitle;
    private EditText mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();
        setSubmitEnable(false);//提交按钮不可用
        initView();
        initListener();
        mPresenter.attachView(this);//绑定view
    }

    private void initView() {
        mTitle = findViewById(R.id.title);
        mContent = findViewById(R.id.content);
        mSmartRefreshLayout.setBackgroundColor(getResources().getColor(R.color.app_line_color));
    }

    @Override
    protected void onBackBtPressed() {
        if (!TextUtils.isEmpty(mContent.getText().toString()))
            mHintDialog.show();
        else
            finish();
    }

    private void initListener() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(mTitle.getText().toString()) &&
                        !TextUtils.isEmpty(mContent.getText().toString()))
                    setSubmitEnable(true);
                else
                    setSubmitEnable(false);
            }
        };
        mContent.addTextChangedListener(textWatcher);
        mTitle.addTextChangedListener(textWatcher);
    }

    @Override
    protected String getTitleName() {
        return "意见建议";
    }

    @Override
    protected String getRightTextName() {
        return "提交";
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.main_layout_option;
    }

    @Override
    protected void onRefresh() {

    }

    @Override
    protected void onFloatBtClick() {
        mPresenter.submit();//提交一下
    }


    @Override
    public String getTitleStr() {
        return mTitle.getText().toString();
    }

    @Override
    public String getContentStr() {
        return mContent.getText().toString();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }
}

