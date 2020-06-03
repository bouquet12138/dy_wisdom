package com.example.login_module.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.SetBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_view.custom_view.ShowPasswordView;
import com.example.common_view.editText.MyEditText;
import com.example.login_module.R;
import com.example.login_module.contract.RegisterContract;
import com.example.login_module.presenter.RegisterPresenter;


@Route(path = ARouterContract.LOGIN_REGISTER)
public class RegisterActivity extends AppMvpBaseActivity implements View.OnClickListener, RegisterContract.IView {


    private MyEditText mNameText;
    private MyEditText mTelPhone;
    private MyEditText mPlaceUserPhone;
    private TextView mPlaceUserName;
    private MyEditText mVerCode;
    private Button mGetVrCodeBt;
    private MyEditText mLoginPassword;
    private ShowPasswordView mPasswordBt1;

    private MyEditText mPayPassword;
    private ShowPasswordView mPasswordBt2;

    private ImageView mImageView;

    private ViewGroup mWeChatPay;
    private ViewGroup mAliPay;
    private ViewGroup mIntegralPay;

    private MyEditText mMyPayPassword;
    private ShowPasswordView mPasswordBt3;

    private TextView mMoneyText;
    private TextView mMoneyDetailText;

    private Button mConfirmBt;

    private RegisterPresenter mPresenter = new RegisterPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view
        setSubmitEnable(true);//提交可用
        initView();
        initData();
        initListener();
        mPresenter.attachView(this);//绑定一下
        mPresenter.getSetInfo();//得到套餐信息
    }

    /**
     * 初始化view
     */
    private void initView() {

        mNameText = findViewById(R.id.nameText);
        mTelPhone = findViewById(R.id.telPhone);
        mPlaceUserPhone = findViewById(R.id.place_user_phone);
        mPlaceUserName = findViewById(R.id.place_user_name);
        mVerCode = findViewById(R.id.verCode);
        mGetVrCodeBt = findViewById(R.id.getVrCodeBt);
        mLoginPassword = findViewById(R.id.loginPassword);
        mPasswordBt1 = findViewById(R.id.passwordBt1);
        mPayPassword = findViewById(R.id.payPassword);
        mPasswordBt2 = findViewById(R.id.passwordBt2);
        mImageView = findViewById(R.id.imageView);
        mWeChatPay = findViewById(R.id.we_chat_pay);
        mAliPay = findViewById(R.id.ali_pay);
        mIntegralPay = findViewById(R.id.integral_pay);
        mMyPayPassword = findViewById(R.id.myPayPassword);
        mPasswordBt3 = findViewById(R.id.passwordBt3);

        mConfirmBt = findViewById(R.id.confirmBt);

        mMoneyText = findViewById(R.id.moneyText);
        mMoneyDetailText = findViewById(R.id.moneyDetailText);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        UserBean userBean = NowUserInfo.getNowUserInfo();
        if (userBean == null)//为空就返回
            return;

        mPlaceUserPhone.setText(userBean.getPhone());
        mPlaceUserName.setText("安置者姓名：" + userBean.getName());

    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mGetVrCodeBt.setOnClickListener(this);
        mConfirmBt.setOnClickListener(this);//确认按钮

        MyEditText.OnTextChangedListener textChangedListener = () -> {
            onTextChange();
        };
        mTelPhone.setOnTextChangedListener(textChangedListener);
        mPlaceUserPhone.setOnTextChangedListener(() -> {
            onTextChange();
            String placeUserPhone = mPlaceUserPhone.getText();
            if (!TextUtils.isEmpty(placeUserPhone) && placeUserPhone.length() == 11)
                mPresenter.getUserInfo();//得到安置者信息
            else {
                mPlaceUserName.setText("安置者姓名：未知");
            }
        });

        mNameText.setOnTextChangedListener(textChangedListener);
        mTelPhone.setOnTextChangedListener(textChangedListener);

        mVerCode.setOnTextChangedListener(textChangedListener);
        mLoginPassword.setOnTextChangedListener(textChangedListener);
        mPayPassword.setOnTextChangedListener(textChangedListener);
        mMyPayPassword.setOnTextChangedListener(textChangedListener);

        mPasswordBt1.setEditText(mLoginPassword.getEditText());
        mPasswordBt2.setEditText(mPayPassword.getEditText());
        mPasswordBt3.setEditText(mMyPayPassword.getEditText());


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.getVrCodeBt) {
            mPresenter.sendQrCode();//发送验证码
        } else if (v.getId() == R.id.confirmBt) {
            mPresenter.register();//注册一下
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
        Log.d(TAG, "onFloatBtClick: ");
        mPresenter.register();
    }

    /**
     * 设置套餐信息
     *
     * @param setBean
     */
    @Override
    public void setSetInfo(SetBean setBean) {
        mMoneyText.setText("￥" + setBean.getSet_price());
        mMoneyDetailText.setText("销售积分" + (int) (setBean.getSet_price() * setBean.getSale_radio()) + "  推广积分" +
                (int) (setBean.getSet_price() * setBean.getRedeem_ratio()));//详情
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
    public void setUserInfo(UserBean userInfo) {
        if (userInfo != null)
            mPlaceUserName.setText("安置者姓名:" + userInfo.getName());//设置安置者姓名
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
        InformationActivity.actionStart(this, userId, mNameText.getText());
    }

    @Override
    public String getPhone() {
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

    @Override
    public String getLoginPass() {
        return mLoginPassword.getText();
    }

    @Override
    public String getPayPass() {
        return mPayPassword.getText();
    }

    @Override
    public String getPlaceUserPhone() {
        return mPlaceUserPhone.getText();
    }

    @Override
    public String getName() {
        return mNameText.getText();
    }

    @Override
    public String getRecommendUserPayPass() {
        return mMyPayPassword.getText();
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
       /* setSubmitEnable(mPresenter.isRight());//设置提交是否可用
        mConfirmBt.setEnabled(mPresenter.isRight());//提交按钮是否可用*/
    }


}
