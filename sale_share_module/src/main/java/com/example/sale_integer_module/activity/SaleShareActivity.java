package com.example.sale_integer_module.activity;

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
import com.example.sale_integer_module.R;
import com.example.sale_integer_module.fragment.SaleShareFragment;

import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import java.util.ArrayList;
import java.util.List;


@Route(path = ARouterContract.SALE_SHARE_SALE_SHARE)
public class SaleShareActivity extends MVPBaseActivity {


    private ImageView mBackButton;
    private TextView mRightText;

    private QMUITabSegment mTableLayout;
    private QMUIViewPager mViewPager;

    private List<QMUIFragment> mFragments = new ArrayList<>();//碎片集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sale_activity_share);
        initView();
        initFragment();
        initListener();
    }

    /**
     * 初始化碎片
     */
    private void initFragment() {
        mFragments.add(new SaleShareFragment(SaleShareRecordBean.INTEGRAL_TYPE_DIRECT));//直推
        mFragments.add(new SaleShareFragment(SaleShareRecordBean.INTEGRAL_TYPE_INDIRECT));//间推
        mFragments.add(new SaleShareFragment(SaleShareRecordBean.INTEGRAL_TYPE_BUY));//买套餐
        mFragments.add(new SaleShareFragment(SaleShareRecordBean.INTEGRAL_TYPE_REGISTER));//注册用户

        mFragments.add(new SaleShareFragment(SaleShareRecordBean.INTEGRAL_TYPE_RECHARGE));//充值
        mFragments.add(new SaleShareFragment(SaleShareRecordBean.INTEGRAL_TYPE_TRAN));//互转
        mFragments.add(new SaleShareFragment(SaleShareRecordBean.INTEGRAL_TYPE_CONVERSION));//转换
        mFragments.add(new SaleShareFragment(SaleShareRecordBean.INTEGRAL_TYPE_WITHDRAW));//提现
        mFragments.add(new SaleShareFragment(SaleShareRecordBean.INTEGRAL_TYPE_BONUS));//分润

        List<String> titles = new ArrayList<>();
        titles.add(SaleShareRecordBean.INTEGRAL_TYPE_DIRECT);//直推
        titles.add(SaleShareRecordBean.INTEGRAL_TYPE_INDIRECT);//间推
        titles.add(SaleShareRecordBean.INTEGRAL_TYPE_BUY);//买套餐
        titles.add(SaleShareRecordBean.INTEGRAL_TYPE_REGISTER);//注册用户


        titles.add(SaleShareRecordBean.INTEGRAL_TYPE_RECHARGE);//充值
        titles.add(SaleShareRecordBean.INTEGRAL_TYPE_TRAN);//互转
        titles.add(SaleShareRecordBean.INTEGRAL_TYPE_CONVERSION);//转换
        titles.add(SaleShareRecordBean.INTEGRAL_TYPE_WITHDRAW);//提现
        titles.add(SaleShareRecordBean.INTEGRAL_TYPE_BONUS);//分润


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
        mRightText.setOnClickListener((v) -> {
            ARouter.getInstance().build(ARouterContract.WITHDRAW_WITHDRAW) //提现
                    .withString("withdraw_type", WithdrawBean.SALE_WITHDRAW).navigation();
        });
    }

}
