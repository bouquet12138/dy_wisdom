package com.example.sale_integer_module.fragment;

import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.utils.TextUtils;
import com.example.common_lib.base.MVPQMUIFragment;
import com.example.common_view.custom_view.ShowPasswordView;
import com.example.common_view.editText.MyEditText;
import com.example.sale_integer_module.R;
import com.example.sale_integer_module.contract.ConversionContract;
import com.example.sale_integer_module.presenter.ConversionPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversionFragment extends MVPQMUIFragment implements ConversionContract.IView {

    private ConversionPresenter mPresenter = new ConversionPresenter();
    private MyEditText mAmountNum;
    private MyEditText mPassword;
    private MyEditText mRemark;

    private ShowPasswordView mPasswordBt;

    private Button mConfirmBt;

    private String mTransferType;

    public ConversionFragment(String transferType) {
        mTransferType = transferType;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.sale_fragment_conversion;
    }

    @Override
    protected void initAllMembersView() {
        initView();
        initListener();
        mPresenter.attachView(this);//绑定一下
    }

    /**
     * 初始化view
     */
    private void initView() {
        mAmountNum = mView.findViewById(R.id.amountNum);
        mPassword = mView.findViewById(R.id.password);
        mPasswordBt = mView.findViewById(R.id.passwordBt);
        mRemark = mView.findViewById(R.id.remark);

        mConfirmBt = mView.findViewById(R.id.confirmBt);

        mPasswordBt.setEditText(mPassword.getEditText());
    }

    /**
     * 初始化监听
     */
    private void initListener() {

        mAmountNum.setOnTextChangedListener(() -> {
            mConfirmBt.setEnabled(isRight());//提交是否可用
        });
        mPassword.setOnTextChangedListener(() -> {
            mConfirmBt.setEnabled(isRight());//提交是否可用
        });

        mConfirmBt.setOnClickListener(v -> {
            mPresenter.conversion();//转换一下
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
    public String getConversionType() {
        return mTransferType;
    }

    @Override
    public void conversionSuccess() {
        finishActivity();//销毁活动
    }

    /**
     * 销毁时
     */
    @Override
    public void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
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
}
