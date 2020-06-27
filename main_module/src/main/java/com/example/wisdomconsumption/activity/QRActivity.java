package com.example.wisdomconsumption.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.base_lib.base.MVPBaseActivity;
import com.example.common_lib.adapter.QMUIViewPageAdapter;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.UserBean;
import com.example.wisdomconsumption.R;
import com.example.wisdomconsumption.fragment.QRFragment;
import com.example.wisdomconsumption.java_bean.QRBean;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import java.util.ArrayList;
import java.util.List;

public class QRActivity extends MVPBaseActivity {

    private ImageView mBackButton;

    private QMUITabSegment mTableLayout;
    private QMUIViewPager mViewPager;

    private List<QMUIFragment> mFragments = new ArrayList<>();//碎片集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout_qr);//布局id
        initView();
        initFragment();
    }

    /**
     * 初始化view
     */
    private void initView() {
        mBackButton = findViewById(R.id.backButton);
        mTableLayout = findViewById(R.id.tableLayout);
        mViewPager = findViewById(R.id.viewPager);

        mBackButton.setOnClickListener(v -> {
            finish();//销毁
        });
    }

    /**
     * 初始化碎片
     */
    private void initFragment() {
        UserBean userBean = NowUserInfo.getNowUserInfo();
        if (userBean == null)
            return;
        mFragments.add(new QRFragment(QRBean.SALE_SHARE));//销售
        mFragments.add(new QRFragment(QRBean.SPREAD));//推广


        List<String> titles = new ArrayList<>();
        titles.add(QRBean.SALE_SHARE);//销售
        titles.add(QRBean.SPREAD);//推广

        if (UserBean.MERCHANT_YES.equals(userBean.getIs_merchant())) {
            mFragments.add(new QRFragment(QRBean.MERCHANTS));//商家
            titles.add(QRBean.MERCHANTS);//商家
        }

        QMUIViewPageAdapter myFragmentAdapter = new QMUIViewPageAdapter(getSupportFragmentManager(), mFragments, titles);
        mViewPager.setAdapter(myFragmentAdapter);//设置适配器
        mTableLayout.setupWithViewPager(mViewPager, true);//设置上面的标题
    }


}
