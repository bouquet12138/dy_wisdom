package com.example.login_module.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.BarUtils;
import com.example.base_lib.base.MVPBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_view.custom_view.SelectImg;
import com.example.common_view.custom_view.ShowPasswordView;
import com.example.common_view.editText.MyEditText;
import com.example.login_module.R;
import com.example.login_module.contract.LoginContract;
import com.example.login_module.presenter.LoginPresenter;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

@Route(path = ARouterContract.LOGIN_LOGIN)
public class LoginActivity extends MVPBaseActivity implements View.OnClickListener, LoginContract.IView {


    private ImageView mHeadImage;
    private MyEditText mAccountEdit;
    private MyEditText mPasswordEdit;
    private ShowPasswordView mOpenImage;
    private CheckBox mRememberPW;
    private CheckBox mAutoLand;

    private TextView mForgetPassword;

    private Button mConfirmBt;

    private TextView mUserText;
    private TextView mPrivacyText;

    private SelectImg mRadioButton;//单选按钮

    private LoginPresenter mPresenter = new LoginPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_login);
        BarUtils.setStatusBarColor(this, getResources().getColor(R.color.app_theme_color));
        initView();
        initListener();
        mPresenter.attachView(this);//绑定一下
        mPresenter.initInfo();//初始化一下信息
    }

    /**
     * 初始化view
     */
    private void initView() {
        mHeadImage = findViewById(R.id.headImage);
        mAccountEdit = findViewById(R.id.accountEdit);
        mPasswordEdit = findViewById(R.id.passwordEdit);
        mOpenImage = findViewById(R.id.openImage);
        mRememberPW = findViewById(R.id.rememberPW);
        mAutoLand = findViewById(R.id.autoLand);
        mForgetPassword = findViewById(R.id.forgetPassword);
        mConfirmBt = findViewById(R.id.confirmBt);
        mUserText = findViewById(R.id.userText);
        mPrivacyText = findViewById(R.id.privacyText);
        mRadioButton = findViewById(R.id.radio);

        mOpenImage.setEditText(mPasswordEdit.getEditText());
    }

    /**
     * 初始化监听
     */
    private void initListener() {

        mAutoLand.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                mRememberPW.setChecked(true);//记住密码也选中
        });


        mRememberPW.setOnCheckedChangeListener((compoundButton, b) -> {
            if (!b && mAutoLand.isChecked())//都选中
                mAutoLand.setChecked(false);//不选中
        });

        mForgetPassword.setOnClickListener(this);
        mConfirmBt.setOnClickListener(this);
        mUserText.setOnClickListener(this);
        mPrivacyText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.forgetPassword) {
            startActivity(new Intent(LoginActivity.this, SelectPassActivity.class));//启动选择密码界面
        } else if (id == R.id.confirmBt) {
            if (!mRadioButton.isSelect())
                showToast("请先同意用户隐私条例");
            else
                mPresenter.login();//登陆一下
        } else if (id == R.id.userText) {
            showUserAgreement();
        } else if (id == R.id.privacyText) {
            showPrivacy();
        }
    }

    @Override
    public void setAgree(boolean agree) {
        mRadioButton.setSelect(agree);
    }

    @Override
    public void setAccount(String account) {
        mAccountEdit.setText(account);
        if (!TextUtils.isEmpty(account))
            mAccountEdit.setCursorPosition(account.length());
    }

    @Override
    public void setPassword(String password) {
        mPasswordEdit.setText(password);
    }

    @Override
    public void setRememberChecked() {
        mRememberPW.setChecked(true);
    }

    @Override
    public void setAutoLandChecked() {
        mAutoLand.setChecked(true);
    }

    @Override
    public boolean isRememberPassword() {
        return mRememberPW.isChecked();
    }

    @Override
    public boolean isAutoLogin() {
        return mAutoLand.isChecked();
    }

    @Override
    public String getAccount() {
        return mAccountEdit.getText();
    }

    @Override
    public String getPassword() {
        return mPasswordEdit.getText();
    }

    @Override
    public void startMainActivity() {
        ARouter.getInstance().build(ARouterContract.MAIN_MAIN) // 目标页面
                .navigation();
        finish();//销毁自己
    }

    @Override
    public void showPrivacy() {
        new QMUIDialog.MessageDialogBuilder(this)
                .setTitle("隐私条例")
                .setMessage(Html.fromHtml(getResources().getString(R.string.login_user_agreement)))
                .addAction("确定", (dialog, index) -> dialog.dismiss())
                .show();
    }

    /**
     * 展示用户协议
     */
    private void showUserAgreement() {
        new QMUIDialog.MessageDialogBuilder(this)
                .setTitle("用户协议")
                .setMessage(Html.fromHtml(getResources().getString(R.string.login_user_agreement)))
                .addAction("确定", (dialog, index) -> dialog.dismiss())
                .show();
    }

    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }
}
