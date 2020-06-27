package com.example.redeem_integer_module.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.base_lib.base.MVPBaseActivity;
import com.example.common_lib.adapter.QMUIViewPageAdapter;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.java_bean.RedeemRecordBean;
import com.example.redeem_integer_module.R;
import com.example.redeem_integer_module.fragment.RedeemFragment;

import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterContract.REDEEM_REDEEM)
public class RedeemActivity extends MVPBaseActivity {


    private ImageView mBackButton;
    private TextView mRightText;

    private QMUITabSegment mTableLayout;
    private QMUIViewPager mViewPager;

    private List<QMUIFragment> mFragments = new ArrayList<>();//碎片集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redeem_activity_redeem);
        initView();
        initFragment();
        initListener();
    }

    /**
     * 初始化碎片
     */
    private void initFragment() {

        /**
         *  public static final String INTEGRAL_TYPE_BONUS = "分润";
         *     public static final String INTEGRAL_TYPE_PAY = "支付";
         *     public static final String INTEGRAL_TYPE_CONVERSION = "转换";
         *     public static final String INTEGRAL_TYPE_RECHARGE = "充值";
         */

        mFragments.add(new RedeemFragment(RedeemRecordBean.INTEGRAL_TYPE_BONUS));//分润
        mFragments.add(new RedeemFragment(RedeemRecordBean.INTEGRAL_TYPE_PAY));//支付
        mFragments.add(new RedeemFragment(RedeemRecordBean.INTEGRAL_TYPE_CONVERSION));//转换
        mFragments.add(new RedeemFragment(RedeemRecordBean.INTEGRAL_TYPE_RECHARGE));//充值
        //mFragments.add(new RedeemFragment(RedeemRecordBean.INTEGRAL_TYPE_ELSE));//其他

        List<String> titles = new ArrayList<>();
        titles.add(RedeemRecordBean.INTEGRAL_TYPE_BONUS);//分润
        titles.add(RedeemRecordBean.INTEGRAL_TYPE_PAY);//支付
        titles.add(RedeemRecordBean.INTEGRAL_TYPE_CONVERSION);//转换
        titles.add(RedeemRecordBean.INTEGRAL_TYPE_RECHARGE);//充值
        //titles.add(RedeemRecordBean.INTEGRAL_TYPE_ELSE);//间推

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
