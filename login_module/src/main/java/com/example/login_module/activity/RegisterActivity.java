package com.example.login_module.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_view.custom_view.ShowPasswordView;
import com.example.common_view.editText.MyEditText;
import com.example.login_module.R;
import com.example.login_module.contract.RegisterContract;
import com.example.login_module.presenter.RegisterPresenter;


@Route(path = ARouterContract.LOGIN_REGISTER)
public class RegisterActivity extends AppMvpBaseActivity implements View.OnClickListener, RegisterContract.IView {

    private MyEditText mNameText;//姓名
    private MyEditText mTelPhone;
    private MyEditText mVertexUser;
    private TextView mVertexUserName;

    private MyEditText mVerCode;
    private Button mGetVrCodeBt;

    private MyEditText mLoginPassword;
    private ShowPasswordView mPasswordBt1;
    private MyEditText mConfirmLoginPassword;

    private ShowPasswordView mPasswordBt2;
    private MyEditText mPayPassword;

    private ShowPasswordView mPasswordBt3;
    private MyEditText mConfirmPayPassword;
    private ShowPasswordView mPasswordBt4;

    private RegisterPresenter mPresenter = new RegisterPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view
        setSubmitEnable(false);//提交按钮不可用
        initView();
        initData();
        initListener();
        mPresenter.attachView(this);//绑定一下
    }

    /**
     * 初始化view
     */
    private void initView() {

        mNameText = findViewById(R.id.nameText);//姓名

        mTelPhone = findViewById(R.id.telPhone);
        mVertexUser = findViewById(R.id.vertex_user_phone);
        mVertexUserName = findViewById(R.id.vertex_user_name);

        mVerCode = findViewById(R.id.verCode);
        mGetVrCodeBt = findViewById(R.id.getVrCodeBt);

        mLoginPassword = findViewById(R.id.loginPassword);
        mPasswordBt1 = findViewById(R.id.passwordBt1);
        mConfirmLoginPassword = findViewById(R.id.confirmLoginPassword);
        mPasswordBt2 = findViewById(R.id.passwordBt2);
        mPayPassword = findViewById(R.id.payPassword);
        mPasswordBt3 = findViewById(R.id.passwordBt3);
        mConfirmPayPassword = findViewById(R.id.confirmPayPassword);
        mPasswordBt4 = findViewById(R.id.passwordBt4);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        UserBean userBean = NowUserInfo.getNowUserInfo();
        if (userBean == null)//为空就返回
            return;

        mVertexUser.setText(userBean.getPhone());
        mVertexUserName.setText("安置者姓名：" + userBean.getName());

    }


    private void initListener() {
        mGetVrCodeBt.setOnClickListener(this);
        MyEditText.OnTextChangedListener textChangedListener = () -> {
            onTextChange();
        };
        mTelPhone.setOnTextChangedListener(textChangedListener);
        mVertexUser.setOnTextChangedListener(() -> {
            onTextChange();
            String vertexUserPhone = mVertexUser.getText();
            if (!TextUtils.isEmpty(vertexUserPhone) && vertexUserPhone.length() == 11)
                mPresenter.getUserInfo(vertexUserPhone);//得到安置者信息
            else {
                mVertexUserName.setText("安置者姓名：未知");
            }
        });

        mVerCode.setOnTextChangedListener(textChangedListener);

        mLoginPassword.setOnTextChangedListener(textChangedListener);
        mConfirmLoginPassword.setOnTextChangedListener(textChangedListener);
        mPayPassword.setOnTextChangedListener(textChangedListener);
        mConfirmPayPassword.setOnTextChangedListener(textChangedListener);

        mPasswordBt1.setEditText(mLoginPassword.getEditText());
        mPasswordBt2.setEditText(mConfirmLoginPassword.getEditText());
        mPasswordBt3.setEditText(mPayPassword.getEditText());
        mPasswordBt4.setEditText(mConfirmPayPassword.getEditText());

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.getVrCodeBt) {
            mPresenter.sendQrCode();//发送验证码
        }
    }


    @Override
    protected String getTitleName() {
        return "注册";
    }

    @Override
    protected String getRightTextName() {
        return "下一步";
    }


    /**
     * 正常view
     *
     * @return
     */
    @Override
    protected int getNormalViewId() {
        return R.layout.login_layout_register;
    }

    @Override
    protected void onRefresh() {

    }

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onFloatBtClick() {
        if (isRight()) {
            Log.d(TAG, "onFloatBtClick: ");
            String phoneNum = mTelPhone.getText();
            String vertexPhoneNum = mVertexUser.getText();

            String loginPass = mLoginPassword.getText();
            String payPass = mPayPassword.getText();//支付密码

            mPresenter.register(phoneNum, loginPass, payPass, NowUserInfo.getNowUserPhone(), vertexPhoneNum);
        }
    }


    @Override
    public void setSendBtEnable(boolean enable) {
        mGetVrCodeBt.setEnabled(enable);//设置是否可用
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
    public boolean isRight() {

        String phoneNum = mTelPhone.getText();
        String vertexPhoneNum = mVertexUser.getText();

        String loginPass = mLoginPassword.getText();
        String confirmLoginPass = mConfirmLoginPassword.getText();

        String payPass = mPayPassword.getText();//支付密码
        String confirmPayPass = mConfirmPayPassword.getText();

        if (TextUtils.isEmpty(phoneNum) || phoneNum.length() < 11) {
            showErrorHint("请输入正确的手机号");
            return false;
        }
        if (TextUtils.isEmpty(vertexPhoneNum) || vertexPhoneNum.length() < 11) {
            showErrorHint("请输入正确安置者手机号");
            return false;
        }
        if (TextUtils.isEmpty(loginPass) || TextUtils.isEmpty(confirmLoginPass)
                || TextUtils.isEmpty(payPass) || TextUtils.isEmpty(confirmPayPass)) {
            showErrorHint("请输入密码");
            return false;
        }
        if (!loginPass.equals(confirmLoginPass)) {
            showErrorHint("登陆密码前后不一致");
            return false;
        }
        if (!payPass.equals(confirmPayPass)) {
            showErrorHint("支付密码前后不一致");
            return false;
        }

        if (loginPass.length() < 6) {
            showErrorHint("登陆密码至少6位");
            return false;
        }

        if (payPass.length() != 6) {
            showErrorHint("支付密码必须6位");
            return false;
        }

        return true;
    }


    @Override
    public void setUserInfo(UserBean userInfo) {
        if (userInfo != null)
            mVertexUserName.setText("安置者姓名:" + userInfo.getName());//
    }

    /**
     * 注册成功
     *
     * @param userId
     */
    @Override
    public void registerSuccess(int userId) {
        Log.d(TAG, "registerSuccess: " + userId);
        finish();//销毁Activity
        InformationActivity.actionStart(this, userId);
    }

    /**
     * 得到手机号
     *
     * @return
     */
    @Override
    public String getPhoneNum() {
        return mTelPhone.getText();
    }

    /**
     * 得到验证码
     *
     * @return
     */
    @Override
    public String getVerCode() {
        return mVerCode.getText();
    }


    /**
     * 销毁时
     */
    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }

    /**
     * 当文本改变时
     */
    public void onTextChange() {
        String phoneNum = mTelPhone.getText();
        String vertexPhoneNum = mVertexUser.getText();
        String verCode = mVerCode.getText();

        String loginPass = mLoginPassword.getText();
        String confirmLoginPass = mConfirmLoginPassword.getText();

        String payPass = mPayPassword.getText();//支付密码
        String confirmPayPass = mConfirmPayPassword.getText();

        if (TextUtils.isEmpty(phoneNum) || phoneNum.length() != 11
                || TextUtils.isEmpty(vertexPhoneNum) || vertexPhoneNum.length() != 11
                || TextUtils.isEmpty(verCode)
                || TextUtils.isEmpty(loginPass) || TextUtils.isEmpty(confirmLoginPass)
                || TextUtils.isEmpty(payPass) || TextUtils.isEmpty(confirmPayPass)
                || loginPass.length() < 6 || confirmLoginPass.length() < 6
                || payPass.length() != 6 || confirmPayPass.length() != 6
        )
            setSubmitEnable(false);
        else
            setSubmitEnable(true);
    }


}
