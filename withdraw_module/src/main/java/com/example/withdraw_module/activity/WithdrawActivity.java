package com.example.withdraw_module.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.java_bean.WithdrawBean;
import com.example.common_view.custom_view.ShowPasswordView;
import com.example.common_view.editText.MyEditText;
import com.example.withdraw_module.R;
import com.example.withdraw_module.contract.WithdrawContract;
import com.example.withdraw_module.presenter.WithdrawPresenter;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

@Route(path = ARouterContract.WITHDRAW_WITHDRAW)
public class WithdrawActivity extends AppMvpBaseActivity implements WithdrawContract.IView {

    @Autowired(name = "withdraw_type")
    protected String mWithdrawType = WithdrawBean.SALE_WITHDRAW;


    private ViewGroup mProfitLayout;
    private TextView mAllProfitMerchantText;
    private TextView mProfitMerchantText;
    private TextView mProfitMerchantFrozenText;

    private MyEditText mAmountNum;//提现金额
    private MyEditText mPassword;
    private MyEditText mRemark;


    private ShowPasswordView mPasswordView;//密码view

    private WithdrawPresenter mPresenter = new WithdrawPresenter();

    private RadioButton mBandCard;
    private RadioButton mAlipay;
    private RadioButton mWechat;

    private TextView mAccountTypeText;

    private MyEditText mAccountText;
    private Button mConfirmBt;//确认按钮

    private TextView mWithdrawRule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();
        setSubmitEnable(false);//右边的按钮不可用
        initView();
        initData();
        initListener();
        mPresenter.attachView(this);//绑定view
    }

    /**
     * 初始化view
     */
    private void initView() {

        mProfitLayout = findViewById(R.id.profitLayout);
        mAllProfitMerchantText = findViewById(R.id.allProfitMerchantText);
        mProfitMerchantText = findViewById(R.id.profitMerchantText);
        mProfitMerchantFrozenText = findViewById(R.id.profitMerchantFrozenText);

        mAmountNum = findViewById(R.id.amountNum);//提现金额
        mPassword = findViewById(R.id.password);
        mPasswordView = findViewById(R.id.passwordBt);//密码按钮
        mRemark = findViewById(R.id.remark);

        mBandCard = findViewById(R.id.bandCard);
        mAlipay = findViewById(R.id.alipay);
        mWechat = findViewById(R.id.wechat);
        mAccountTypeText = findViewById(R.id.accountTypeText);
        mAccountText = findViewById(R.id.accountText);
        mConfirmBt = findViewById(R.id.confirmBt);//确认按钮

        mPasswordView.setEditText(mPassword.getEditText());//设置edit
        mWithdrawRule = findViewById(R.id.withdrawTextView);
    }

    /**
     * 初始化
     */
    private void initData() {

        ARouter.getInstance().inject(this);//绑定一下数据
        UserBean userBean = NowUserInfo.getNowUserInfo();//当前用户

        if (userBean == null)//为空就返回
            return;

        mRemark.setText("提现");
        mAccountText.setText(userBean.getBank_num());//自动输入

        SharedPreferences sharedPreferences = getSharedPreferences("withdraw_rule", MODE_PRIVATE);//提现
        boolean isFirst = sharedPreferences.getBoolean("isFirst", true);//第一次

        if (isFirst) {//如果是第一次
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirst", false);
            editor.apply();//应用一下
            showWithdrawRule();
        }

        mTitleText.setText(mWithdrawType);//设置标题

        if (mWithdrawType.equals(WithdrawBean.MERCHANT_WITHDRAW)) {//如果是商家提现
            mProfitLayout.setVisibility(View.VISIBLE);
            mProfitMerchantText.setText("可提资金:" + (userBean.getProfit_merchant() - userBean.getProfit_frozen_merchant()));
            mAllProfitMerchantText.setText("总资金:" + userBean.getProfit_merchant());
            if (userBean.getProfit_frozen_merchant() != 0)
                mProfitMerchantFrozenText.setText("冻结资金:" + userBean.getProfit_frozen_merchant());
        } else {
            mProfitLayout.setVisibility(View.GONE);
        }

    }

    /**
     * 初始化监听
     */
    private void initListener() {
        MyEditText.OnTextChangedListener onTextChangedListener = () -> {
            mConfirmBt.setEnabled(isRight());//提交是否可用
        };
        mAccountText.setOnTextChangedListener(onTextChangedListener);
        mAmountNum.setOnTextChangedListener(onTextChangedListener);
        mPassword.setOnTextChangedListener(onTextChangedListener);//密码改变

        mAlipay.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                mAccountTypeText.setText("支付宝号:");
                mAccountText.setText(NowUserInfo.getNowUserPhone());
                mAccountText.setCursorPosition();
            }
        });

        mWechat.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                mAccountTypeText.setText("微信号:\u3000");
                mAccountText.setText(NowUserInfo.getNowUserPhone());
                mAccountText.setCursorPosition();
            }
        });

        mBandCard.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                mAccountTypeText.setText("银行卡号:");
                mAccountText.setText(NowUserInfo.getNowUserInfo().getBank_num());//设置银行卡号
                mAccountText.setCursorPosition();
            }
        });

        mConfirmBt.setOnClickListener(view -> onFloatBtClick());//确认

        mWithdrawRule.setOnClickListener(view -> {
            showWithdrawRule();//展示提现规则
        });

    }

    /**
     * 是否合格
     *
     * @return
     */
    private boolean isRight() {

        Integer amount = 0;//提现金额
        try {
            if (!TextUtils.isEmpty(mAmountNum.getText()))
                amount = Integer.parseInt(mAmountNum.getText());//提现金额
        } catch (NumberFormatException e) {
        }

        String password = mPassword.getText();//密码

        String account = mAccountText.getText();

        if (amount != 0 && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(account) && amount >= 100 && amount % 100 == 0)
            return true;
        return false;
    }

    @Override
    protected String getTitleName() {
        return "提现";
    }

    @Override
    protected String getRightTextName() {
        return "";
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.withdraw_layout_withdraw;
    }

    /**
     * 刷新
     */
    @Override
    protected void onRefresh() {

    }

    @Override
    protected void onFloatBtClick() {
        UserBean userBean = NowUserInfo.getNowUserInfo();

        if (userBean == null)
            return;

        Integer amount = 0;//提现金额
        try {
            if (!TextUtils.isEmpty(mAmountNum.getText()))
                amount = Integer.parseInt(mAmountNum.getText());//提现金额
        } catch (NumberFormatException e) {
        }

        String password = mPassword.getText();//密码
        String remark = mRemark.getText();

        WithdrawBean withdrawBean = new WithdrawBean(userBean.getUser_id(), amount, password, remark, mWithdrawType);

        if (mBandCard.isChecked())
            withdrawBean.setBank_card(mAccountText.getText());
        else if (mAlipay.isChecked())
            withdrawBean.setAli_pay(mAccountText.getText());
        else if (mWechat.isChecked())
            withdrawBean.setWe_chat(mAccountText.getText());

        mPresenter.withdrawInfo(withdrawBean);//提现
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }

    /**
     * 展示提现规则
     */
    private void showWithdrawRule() {
        new QMUIDialog.MessageDialogBuilder(this)
                .setTitle("提现规则")
                .setMessage(Html.fromHtml(getResources().getString(R.string.withdrawal_rules)))
                .addAction("确定", (dialog, index) -> dialog.dismiss())
                .show();
    }

    @Override
    public void withdrawSuccess() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}

