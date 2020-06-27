package com.example.shop_module.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.base_lib.base.MVPBaseActivity;
import com.example.common_lib.adapter.QMUIViewPageAdapter;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.java_bean.OrderRecordBean;
import com.example.shop_module.R;
import com.example.shop_module.fragment.OrderFragment;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import java.util.ArrayList;
import java.util.List;

import static com.example.common_lib.java_bean.OrderRecordBean.*;

@Route(path = ARouterContract.SHOP_ORDER)
public class OrderActivity extends MVPBaseActivity implements View.OnClickListener {

    @Autowired(name = "order_type")
    protected String mOrderType;

    private ImageView mBackButton;
    private QMUITabSegment mTableLayout;
    private QMUIViewPager mViewPager;
    private List<QMUIFragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_activity_order);
        initView();
        initFragment();
        initData();
        initListener();
    }

    /**
     * 初始化view
     */
    private void initView() {
        mBackButton = findViewById(R.id.backButton);
        mTableLayout = findViewById(R.id.tableLayout);
        mViewPager = findViewById(R.id.viewPager);
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mBackButton.setOnClickListener(this);
    }

    /**
     * 初始碎片
     */
    private void initFragment() {

        mFragments = new ArrayList<>();

        List<String> titles = new ArrayList<>();

        titles.add(STATUS_ALL);//全部
        titles.add(OrderRecordBean.STATUS_WAIT_DELIVERY);//待发货
        titles.add(OrderRecordBean.STATUS_HAD_DELIVERY);//待收货
        titles.add(OrderRecordBean.STATUS_COMPLETE);//已完成

        mFragments.add(new OrderFragment(STATUS_ALL));
        mFragments.add(new OrderFragment(OrderRecordBean.STATUS_WAIT_DELIVERY));
        mFragments.add(new OrderFragment(OrderRecordBean.STATUS_HAD_DELIVERY));
        mFragments.add(new OrderFragment(OrderRecordBean.STATUS_COMPLETE));

        QMUIViewPageAdapter myFragmentAdapter = new QMUIViewPageAdapter(getSupportFragmentManager(), mFragments, titles);
        mViewPager.setAdapter(myFragmentAdapter);//设置适配器
        mTableLayout.setupWithViewPager(mViewPager, true);//设置上面的标题
    }

    /**
     * 初始化数据
     */
    private void initData() {
        ARouter.getInstance().inject(this);//邦定一下
        switch (mOrderType) {
            case STATUS_ALL:
                break;
            case STATUS_WAIT_DELIVERY:
                mViewPager.setCurrentItem(1);
                break;
            case STATUS_HAD_DELIVERY:
                mViewPager.setCurrentItem(2);
                break;
            case STATUS_COMPLETE:
                mViewPager.setCurrentItem(3);
                break;

        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backButton) {
            finish();
        }
    }

}
