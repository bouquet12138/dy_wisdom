package com.example.login_module.activity;


import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_view.custom_view.ShowPasswordView;
import com.example.common_view.editText.MyEditText;
import com.example.login_module.R;
import com.example.login_module.contract.ModifyLoginPasswordContract;
import com.example.login_module.presenter.ModifyLoginPasswordPresenter;


@Route(path = ARouterContract.LOGIN_MODIFY_LOGIN_PW)
public class ModifyLoginPasswordActivity extends AppMvpBaseActivity implements ModifyLoginPasswordContract.IView {

    private MyEditText mOldPasswordEdit;
    private TextView mOldPasswordHint;
    private ShowPasswordView mOldPasswordImg;

    private MyEditText mNewPasswordEdit;
    private TextView mNewPasswordHint;
    private ShowPasswordView mNewPasswordImg;


    private MyEditText mConfirmPasswordEdit;
    private TextView mConfirmPasswordHint;
    private ShowPasswordView mConfirmPasswordImg;

    private ModifyLoginPasswordPresenter mPresenter;//修改密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();

        mPresenter = new ModifyLoginPasswordPresenter();
        mPresenter.attachView(this);//绑定view
        setSubmitEnable(false);//提交按钮不可用
        showNormalView();//展示正常的view
    }

    @Override
    protected String getTitleName() {
        return "修改登陆密码";
    }

    @Override
    protected String getRightTextName() {
        return "修改";
    }


    @Override
    protected int getNormalViewId() {
        return R.layout.login_layout_modify_login_password;
    }

    @Override
    protected void onRefresh() {

    }


    @Override
    protected void onFloatBtClick() {
        mPresenter.modifyPassword();
    }

    /**
     * 初始化view
     */
    private void initView() {

        mNewPasswordEdit = mNormalView.findViewById(R.id.newPasswordEdit);
        mOldPasswordHint = mNormalView.findViewById(R.id.oldPasswordHint);
        mOldPasswordEdit = mNormalView.findViewById(R.id.oldPasswordEdit);
        mNewPasswordHint = mNormalView.findViewById(R.id.newPasswordHint);
        mConfirmPasswordEdit = mNormalView.findViewById(R.id.confirmPasswordEdit);
        mConfirmPasswordHint = mNormalView.findViewById(R.id.confirmPasswordHint);

        mOldPasswordImg = mNormalView.findViewById(R.id.oldPasswordBt);
        mNewPasswordImg = mNormalView.findViewById(R.id.newPasswordBt);
        mConfirmPasswordImg = mNormalView.findViewById(R.id.confirmPasswordBt);

    }

    private void initListener() {

        mOldPasswordImg.setEditText(mOldPasswordEdit.getEditText());
        mNewPasswordImg.setEditText(mNewPasswordEdit.getEditText());
        mConfirmPasswordImg.setEditText(mConfirmPasswordEdit.getEditText());

        MyEditText.OnTextChangedListener onTextChangedListener = () -> {
            mPresenter.onPasswordChange();//旧密码改变
        };

        mOldPasswordEdit.setOnTextChangedListener(onTextChangedListener);
        mNewPasswordEdit.setOnTextChangedListener(onTextChangedListener);
        mConfirmPasswordEdit.setOnTextChangedListener(onTextChangedListener);

    }

    @Override
    public String getOldPassword() {
        return mOldPasswordEdit.getText();
    }

    @Override
    public String getNewPassword() {
        return mNewPasswordEdit.getText();
    }

    @Override
    public String getConfirmPassword() {
        return mConfirmPasswordEdit.getText();
    }


    @Override
    public void setOldPasswordHint(String hint) {
        mOldPasswordHint.setText(hint);
    }

    @Override
    public void setNewPasswordHint(String hint) {
        mNewPasswordHint.setText(hint);//新密码文本
    }

    @Override
    public void setConfirmPasswordHint(String hint) {
        mConfirmPasswordHint.setText(hint);//确认密码提醒
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();//解除绑定
    }

    @Override
    protected void onBackBtPressed() {
        if (mPresenter.onPasswordChange())
            mHintDialog.show();//提醒对话框展示
        else
            finish();//销毁
    }
}
