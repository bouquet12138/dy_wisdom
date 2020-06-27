package com.example.sale_integer_module.activity;


import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common_lib.adapter.QMUIViewPageAdapter;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.java_bean.TransferBean;
import com.example.sale_integer_module.R;
import com.example.sale_integer_module.fragment.ConversionFragment;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterContract.SALE_SHARE_CONVERSION)
public class ConversionActivity extends AppMvpBaseActivity{


    @Autowired(name = "position")
    protected int mPosition;//位置

    private QMUITabSegment mTableLayout;
    private QMUIViewPager mViewPager;

    private List<QMUIFragment> mFragments = new ArrayList<>();//碎片集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view
        initView();
        initData();
        initFragment();
    }

    private void initView() {

        mTableLayout = findViewById(R.id.tableLayout);
        mViewPager = findViewById(R.id.viewPager);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        ARouter.getInstance().inject(this);//先绑定数据
    }

    private static final String TAG = "ConversionActivity";

    /**
     * 初始化碎片
     */
    private void initFragment() {

        ConversionFragment conversionFragment1 = new ConversionFragment(TransferBean.SALE_TO_REDEEM);//转兑换
        ConversionFragment conversionFragment2 = new ConversionFragment(TransferBean.SALE_TO_INTEGER);//转积分

        mFragments.add(conversionFragment1);
        mFragments.add(conversionFragment2);

        List<String> titles = new ArrayList<>();
        titles.add(TransferBean.SALE_TO_REDEEM);//销售积分转兑换
        titles.add(TransferBean.SALE_TO_INTEGER);//销售积分转积分

        QMUIViewPageAdapter myFragmentAdapter = new QMUIViewPageAdapter(getSupportFragmentManager(), mFragments, titles);
        mViewPager.setAdapter(myFragmentAdapter);//设置适配器
        mTableLayout.setupWithViewPager(mViewPager, true);//设置上面的标题

        Log.d(TAG, "initFragment: " + mPosition);

        if (mPosition == 1)
            mViewPager.setCurrentItem(1);//选中第二个

    }

    @Override
    protected String getTitleName() {
        return "积分转换";
    }

    @Override
    protected String getRightTextName() {
        return null;
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.sale_layout_conversion;
    }

    @Override
    protected void onRefresh() {

    }

    @Override
    protected void onFloatBtClick() {

    }
}
