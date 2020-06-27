package com.example.withdraw_module.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import com.example.base_lib.base.MVPBaseActivity;
import com.example.common_lib.adapter.QMUIViewPageAdapter;

import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.java_bean.SaleShareRecordBean;

import com.example.common_lib.java_bean.WithdrawBean;
import com.example.withdraw_module.R;

import com.example.withdraw_module.fragment.WithdrawRecordFragment;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterContract.WITHDRAW_RECORD)
public class WithDrawRecordActivity extends MVPBaseActivity {

    private ImageView mBackButton;
    private TextView mRightText;

    private QMUITabSegment mTableLayout;
    private QMUIViewPager mViewPager;

    private List<QMUIFragment> mFragments = new ArrayList<>();//碎片集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withdraw_layout_record);
        initView();
        initFragment();
        initListener();
    }

    /**
     * 初始化碎片
     */
    private void initFragment() {
        mFragments.add(new WithdrawRecordFragment(WithdrawBean.SALE_WITHDRAW));//销售积分提现
        mFragments.add(new WithdrawRecordFragment(WithdrawBean.MERCHANT_WITHDRAW));//商家提现


        List<String> titles = new ArrayList<>();
        titles.add(WithdrawBean.SALE_WITHDRAW);//销售积分提现
        titles.add(WithdrawBean.MERCHANT_WITHDRAW);//商家提现


        QMUIViewPageAdapter myFragmentAdapter = new QMUIViewPageAdapter(getSupportFragmentManager(), mFragments, titles);
        mViewPager.setAdapter(myFragmentAdapter);//设置适配器
        mTableLayout.setupWithViewPager(mViewPager, true);//设置上面的标题
    }

    private void initView() {
        mBackButton = findViewById(R.id.backButton);
        mRightText = findViewById(R.id.rightText);
        mTableLayout = findViewById(R.id.tableLayout);
        mViewPager = findViewById(R.id.viewPager);
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mBackButton.setOnClickListener(v -> {
            finish();//销毁自己
        });

    }

}
