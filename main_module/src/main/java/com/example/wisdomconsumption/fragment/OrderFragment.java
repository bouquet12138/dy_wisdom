package com.example.wisdomconsumption.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SizeUtils;
import com.example.base_lib.base.BaseFragment;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.java_bean.OrderRecordBean;
import com.example.wisdomconsumption.R;
import com.example.wisdomconsumption.contract.OrderContract;
import com.example.wisdomconsumption.presenter.OrderPresenter;
import com.qmuiteam.qmui.layout.QMUILinearLayout;

public class OrderFragment extends BaseFragment implements View.OnClickListener, OrderContract.IView {

    private QMUILinearLayout mCardLayout2;

    private TextView mAllRecord;

    private ViewGroup mWaitGroup;
    private ViewGroup mHadGroup;
    private TextView mHadNum;

    private TextView mWaitNum;
    private ViewGroup mHasComplete;

    private OrderPresenter mPresenter = new OrderPresenter();


    @Override
    protected int getContentViewId() {
        return R.layout.main_layout_order;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initView();
        initListener();
        mPresenter.attachView(this);
        mPresenter.getDeliverGoodNum();//获取一下
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mAllRecord.setOnClickListener(this);
        mWaitGroup.setOnClickListener(this);
        mHadGroup.setOnClickListener(this);
        mHasComplete.setOnClickListener(this);
    }

    private void initView() {
        mCardLayout2 = mView.findViewById(R.id.cardLayout2);
        mAllRecord = mView.findViewById(R.id.allRecord);
        mWaitGroup = mView.findViewById(R.id.waitGroup);
        mWaitNum = mView.findViewById(R.id.waitNum);
        mHadGroup = mView.findViewById(R.id.hadGroup);
        mHadNum = mView.findViewById(R.id.hadNum);
        mHasComplete = mView.findViewById(R.id.hasComplete);

        QMUILinearLayout cardLayout = mView.findViewById(R.id.cardLayout2);//第二个cardLayout
        cardLayout.setRadiusAndShadow(SizeUtils.dp2px(10), SizeUtils.dp2px(5), 0.5f);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.allRecord:
                ARouter.getInstance().build(ARouterContract.SHOP_ORDER) //所有订单
                        .withString("order_type", OrderRecordBean.STATUS_ALL).navigation();
                break;
            case R.id.waitGroup:
                ARouter.getInstance().build(ARouterContract.SHOP_ORDER) //等待发货
                        .withString("order_type", OrderRecordBean.STATUS_WAIT_DELIVERY).navigation();
                break;
            case R.id.hadGroup:
                ARouter.getInstance().build(ARouterContract.SHOP_ORDER) //已发货
                        .withString("order_type", OrderRecordBean.STATUS_HAD_DELIVERY).navigation();
                break;
            case R.id.hasComplete:
                ARouter.getInstance().build(ARouterContract.SHOP_ORDER) //已完成
                        .withString("order_type", OrderRecordBean.STATUS_COMPLETE).navigation();
                break;
        }
    }

    @Override
    public void setWaitDelivery(int num) {
        if (num != 0) {
            mWaitNum.setVisibility(View.VISIBLE);
            mWaitNum.setText(num + "");
        }
    }

    @Override
    public void setHadDelivery(int num) {
        if (num != 0) {
            mHadNum.setVisibility(View.VISIBLE);
            mHadNum.setText(num + "");
        }
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();//解除绑定
        super.onDestroyView();
    }
}