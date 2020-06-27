package com.example.redeem_integer_module.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.utils.TextUtils;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_view.custom_view.ShowPasswordView;
import com.example.common_view.editText.MyEditText;
import com.example.redeem_integer_module.R;
import com.example.redeem_integer_module.contract.RedeemPayContract;
import com.example.redeem_integer_module.presenter.RedeemPayPresenter;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

@Route(path = ARouterContract.REDEEM_PAY)
public class RedeemPayActivity extends AppMvpBaseActivity implements RedeemPayContract.IView {

    @Autowired(name = "remark_str")
    protected String mRemarkStr;

    @Autowired(name = "target_user_id")
    protected int mTargetUserId;//用户id

    private MyEditText mAmountNum;
    private MyEditText mPassword;
    private ShowPasswordView mPasswordBt;
    private MyEditText mRemark;

    private QMUIRoundButton mConfirmBt;

    private TextView mConversion;

    private RedeemPayPresenter mPresenter = new RedeemPayPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view
        initView();
        initData();
        initListener();
        mPresenter.attachView(this);//绑定一下
    }


    @Override
    protected String getTitleName() {
        return "店内支付";
    }

    @Override
    protected String getRightTextName() {
        return null;
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.redeem_activity_pay;
    }

    @Override
    protected void onRefresh() {
    }

    @Override
    protected void onFloatBtClick() {
    }


    private void initView() {
        mAmountNum = mNormalView.findViewById(R.id.amountNum);
        mPassword = mNormalView.findViewById(R.id.password);
        mPasswordBt = mNormalView.findViewById(R.id.passwordBt);
        mRemark = mNormalView.findViewById(R.id.remark);

        mConversion = mNormalView.findViewById(R.id.conversion);//转换
        mConfirmBt = mNormalView.findViewById(R.id.confirmBt);
        mPasswordBt.setEditText(mPassword.getEditText());//设置
    }

    /**
     * 初始化数据
     */
    private void initData() {
        ARouter.getInstance().inject(this);//绑定一下
        if (!TextUtils.isEmpty(mRemarkStr))
            mRemark.setText(mRemarkStr);
    }

    private void initListener() {
        mAmountNum.setOnTextChangedListener(() -> {
            mConfirmBt.setEnabled(isRight());//提交是否可用
        });
        mPassword.setOnTextChangedListener(() -> {
            mConfirmBt.setEnabled(isRight());//提交是否可用
        });

        mConversion.setOnClickListener((v) -> {
            //SALE_SHARE_CONVERSION
            ARouter.getInstance().build(ARouterContract.SALE_SHARE_CONVERSION).navigation();//跳转到积分互转页面
        });

        mConfirmBt.setOnClickListener(v -> {
            mPresenter.redeemPay();//支付一下
        });
    }

    /**
     * 是否合格
     *
     * @return
     */
    private boolean isRight() {

        Integer amount = getIntegralAmount();//互转金额

        String password = mPassword.getText();//密码

        if (amount != 0 && !TextUtils.isEmpty(password))
            return true;
        return false;
    }


    @Override
    public void paySuccess() {
        finish();//销毁自己
    }

    @Override
    public int getIntegralAmount() {
        Integer amount = 0;//提现金额
        try {
            if (!TextUtils.isEmpty(mAmountNum.getText()))
                amount = Integer.parseInt(mAmountNum.getText());//提现金额
        } catch (NumberFormatException e) {
        }
        return amount;
    }

    /**
     * 得到密码
     *
     * @return
     */
    @Override
    public String getPayPassword() {
        return mPassword.getText();
    }

    @Override
    public String getRemark() {
        return mRemark.getText();
    }

    @Override
    public int getTargetUserId() {
        return mTargetUserId;
    }


}
