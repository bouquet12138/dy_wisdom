package com.example.spread_integer_module.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.base_lib.base.MVPBaseActivity;
import com.example.common_lib.adapter.QMUIViewPageAdapter;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.java_bean.SpreadRecordBean;
import com.example.spread_integer_module.R;
import com.example.spread_integer_module.fragment.SpreadFragment;

import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterContract.SPREAD_SPREAD)//推广
public class SpreadActivity extends MVPBaseActivity {


    private ImageView mBackButton;
    private TextView mRightText;

    private QMUITabSegment mTableLayout;
    private QMUIViewPager mViewPager;

    private List<QMUIFragment> mFragments = new ArrayList<>();//碎片集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spread_activity_spread);
        initView();
        initFragment();
        initListener();
    }

    /**
     * 初始化碎片
     */
    private void initFragment() {

        /**
         *   public static final String INTEGRAL_TYPE_TRAN = "互转";
         *     public static final String INTEGRAL_TYPE_REGISTER = "注册用户";
         *     public static final String INTEGRAL_TYPE_RECHARGE = "充值";
         */
        mFragments.add(new SpreadFragment(SpreadRecordBean.INTEGRAL_TYPE_TRAN));//互转
        mFragments.add(new SpreadFragment(SpreadRecordBean.INTEGRAL_TYPE_REGISTER));//注册用户
        mFragments.add(new SpreadFragment(SpreadRecordBean.INTEGRAL_TYPE_RECHARGE));//充值


        List<String> titles = new ArrayList<>();
        titles.add(SpreadRecordBean.INTEGRAL_TYPE_TRAN);//互转
        titles.add(SpreadRecordBean.INTEGRAL_TYPE_REGISTER);//注册用户
        titles.add(SpreadRecordBean.INTEGRAL_TYPE_RECHARGE);//充值

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
