package com.example.login_module.activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_view.custom_view.ShowPasswordView;
import com.example.common_view.editText.MyEditText;
import com.example.login_module.R;
import com.example.login_module.contract.ForgetPassContract;
import com.example.login_module.presenter.ForgetPassPresenter;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

public class ForgetPayPassActivity extends AppMvpBaseActivity implements View.OnClickListener, ForgetPassContract.IView {


    private MyEditText mMyTelPhone;
    private MyEditText mVerCode;
    private QMUIRoundButton mGetVrCodeBt;
    private MyEditText mPayPassword;
    private ShowPasswordView mPasswordBt1;
    private MyEditText mConfirmPayPassword;
    private ShowPasswordView mPasswordBt2;

    private ForgetPassPresenter mPresenter = new ForgetPassPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view
        setSubmitEnable(false);//提交按钮不可用
        initView();
        initListener();
        mPresenter.attachView(this);
    }

    @Override
    protected String getTitleName() {
        return "忘记支付密码";
    }

    @Override
    protected String getRightTextName() {
        return "修改";
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.login_layout_forget_pay_pass;
    }

    @Override
    protected void onRefresh() {

    }

    @Override
    protected void onFloatBtClick() {
        mPresenter.submit();//提交一下
    }

    private void initView() {
        mMyTelPhone = findViewById(R.id.myTelPhone);
        mVerCode = findViewById(R.id.verCode);
        mGetVrCodeBt = findViewById(R.id.getVrCodeBt);
        mPayPassword = findViewById(R.id.payPassword);
        mPasswordBt1 = findViewById(R.id.passwordBt1);
        mConfirmPayPassword = findViewById(R.id.confirmPayPassword);
        mPasswordBt2 = findViewById(R.id.passwordBt2);
        mPasswordBt1.setEditText(mPayPassword.getEditText());
        mPasswordBt2.setEditText(mConfirmPayPassword.getEditText());

        mMyTelPhone.setText(NowUserInfo.getNowUserPhone());
        mMyTelPhone.setCursorPosition();//设置光标位置
    }

    private void initListener() {
        mGetVrCodeBt.setOnClickListener(this);
        MyEditText.OnTextChangedListener onTextChange = () -> setSubmitEnable(isRight());
        mMyTelPhone.setOnTextChangedListener(onTextChange);
        mVerCode.setOnTextChangedListener(onTextChange);
        mPayPassword.setOnTextChangedListener(onTextChange);
        mConfirmPayPassword.setOnTextChangedListener(onTextChange);
    }

    /**
     * 是否正确
     */
    private boolean isRight() {

        String phoneNum = mMyTelPhone.getText();
        String verCode = mVerCode.getText();//验证码
        String password1 = mPayPassword.getText();//密码
        String password2 = mConfirmPayPassword.getText();//密码

        if (TextUtils.isEmpty(phoneNum) || phoneNum.length() != 11
                || TextUtils.isEmpty(verCode) || TextUtils.isEmpty(password1)
                || TextUtils.isEmpty(password2) || password1.length() < 6
                || !password2.equals(password1))
            return false;
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.getVrCodeBt) {
            mPresenter.sendQrCode();//发送验证码
        }
    }

    @Override
    public String getPhoneNum() {
        return mMyTelPhone.getText();
    }

    @Override
    public String getVerCode() {
        return mVerCode.getText();
    }

    @Override
    public String getLoginPassword() {
        return mPayPassword.getText();
    }

    @Override
    public void setSendBtEnable(boolean enable) {
        mGetVrCodeBt.setEnabled(enable);
    }

    /**
     * 销毁后
     */
    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }

    /**
     * 设置按钮上的文字
     *
     * @param text
     */
    @Override
    public void setSendBtText(String text) {
        mGetVrCodeBt.setText(text);
    }

    @Override
    public boolean isForgetPay() {
        return true;
    }
}
