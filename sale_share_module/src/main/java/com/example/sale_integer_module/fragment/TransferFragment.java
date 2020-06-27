package com.example.sale_integer_module.fragment;


import androidx.fragment.app.Fragment;

import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.utils.TextUtils;
import com.example.common_lib.base.MVPQMUIFragment;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.TransferBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_view.custom_view.ShowPasswordView;
import com.example.common_view.editText.MyEditText;
import com.example.sale_integer_module.R;
import com.example.sale_integer_module.contract.TransfersContract;
import com.example.sale_integer_module.presenter.TransfersPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransferFragment extends MVPQMUIFragment implements TransfersContract.IView {


    private TransfersPresenter mPresenter = new TransfersPresenter();

    private String mTransferType;
    private String mTargetPhone;//目标手机号

    public TransferFragment(String transferType, String targetPhone) {
        mTransferType = transferType;
        mTargetPhone = targetPhone;
    }

    private MyEditText mAmountNum;
    private MyEditText mPassword;
    private ShowPasswordView mPasswordBt;
    private MyEditText mRemark;
    private MyEditText mTargetUserText;
    private TextView mUserNameText;
    private Button mConfirmBt;


    @Override
    protected int getContentViewId() {
        return R.layout.sale_fragment_transfers;
    }


    @Override
    protected void initAllMembersView() {
        initView();
        initListener();//初始化监听
        mPresenter.attachView(this);//绑定一下view
        if (!TextUtils.isEmpty(mTargetPhone))
            mTargetUserText.setText(mTargetPhone);//目标手机号
    }


    /**
     * 初始化view
     */
    private void initView() {
        TextView integerTYpe = mView.findViewById(R.id.integerType);//积分类型

        if (TransferBean.SPREAD_TRANSFER.equals(mTransferType))
            integerTYpe.setText("推广积分:\u3000");

        mAmountNum = mView.findViewById(R.id.amountNum);
        mPassword = mView.findViewById(R.id.password);
        mPasswordBt = mView.findViewById(R.id.passwordBt);
        mRemark = mView.findViewById(R.id.remark);
        mTargetUserText = mView.findViewById(R.id.targetUserText);
        mUserNameText = mView.findViewById(R.id.userNameText);
        mConfirmBt = mView.findViewById(R.id.confirmBt);

        mPasswordBt.setEditText(mPassword.getEditText());//设置一下

        mAmountNum.setText("1950");
        mAmountNum.setCursorPosition();//设置一下光标位置
    }

    /**
     * 初始化监听
     */
    private void initListener() {

        mConfirmBt.setOnClickListener(view -> {
            transfer();
        });

        mAmountNum.setOnTextChangedListener(() -> {
            mConfirmBt.setEnabled(isRight());//提交是否可用
        });
        mPassword.setOnTextChangedListener(() -> {
            mConfirmBt.setEnabled(isRight());//提交是否可用
        });
        mTargetUserText.setOnTextChangedListener(() -> {
            mConfirmBt.setEnabled(isRight());//提交是否可用
            String targetUser = mTargetUserText.getText();
            if (!TextUtils.isEmpty(targetUser) && targetUser.length() == 11) {
                mPresenter.getUserInfo(targetUser);//获取用户信息
            } else
                mUserNameText.setText("");//清空

            if (!mTransmit) {
                if (mOnPhoneChangeListener != null)
                    mOnPhoneChangeListener.onPhoneChange(targetUser);//目标用户
            } else
                mTransmit = false;

        });
    }

    /**
     * 互转
     */
    private void transfer() {
        String targetUser = mTargetUserText.getText();
        if (targetUser.equals(NowUserInfo.getNowUserPhone())) {
            showErrorHint("不能给自己转账");
            return;
        }
        mPresenter.payrollTransfers();//开始转账
    }

    /**
     * 是否合格
     *
     * @return
     */
    private boolean isRight() {

        Integer amount = 0;//互转金额
        try {
            if (!TextUtils.isEmpty(mAmountNum.getText()))
                amount = Integer.parseInt(mAmountNum.getText());//提现金额
        } catch (NumberFormatException e) {
        }

        String password = mPassword.getText();//密码

        String targetUser = mTargetUserText.getText();
        if (amount != 0 && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(targetUser) && targetUser.length() == 11)
            return true;
        return false;
    }

    @Override
    public String getTransactionType() {
        return mTransferType;
    }

    @Override
    public void transferSuccess() {
    }

    /**
     * 销毁时
     */
    @Override
    public void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }

    /**
     * 设置用户信息
     *
     * @param userBean
     */
    @Override
    public void setUserInfo(UserBean userBean) {
        if (userBean != null) {
            mUserNameText.setText("姓名:" + userBean.getName());
        }
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

    @Override
    public String getPayPassword() {
        return mPassword.getText().toString();
    }

    @Override
    public String getRemark() {
        return mRemark.getText();
    }

    @Override
    public String getTargetUserPhone() {
        return mTargetUserText.getText();
    }

    public interface OnPhoneChangeListener {
        void onPhoneChange(String phone);
    }

    private OnPhoneChangeListener mOnPhoneChangeListener;

    public void setOnPhoneChangeListener(OnPhoneChangeListener onPhoneChangeListener) {
        mOnPhoneChangeListener = onPhoneChangeListener;
    }

    private boolean mTransmit;

    /**
     * 设置
     */
    public void setPhoneText(String phone) {
        if (mTargetUserText != null) {
            mTransmit = true;
            mTargetUserText.setText(phone);//设置一下
        }
    }

}
