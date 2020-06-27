package com.example.sale_integer_module.activity;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common_lib.adapter.QMUIViewPageAdapter;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.java_bean.TransferBean;
import com.example.sale_integer_module.R;
import com.example.sale_integer_module.fragment.TransferFragment;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterContract.SALE_SHARE_TRANSFER)
public class TransferActivity extends AppMvpBaseActivity {

    @Autowired(name = "targetPhone")
    protected String mTargetPhone;//目标手机号

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

    /**
     * 初始化view
     */
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

    /**
     * 初始化碎片
     */
    private void initFragment() {

        TransferFragment transferFragment = new TransferFragment(TransferBean.SALE_TRANSFER, mTargetPhone);
        TransferFragment transferFragment1 = new TransferFragment(TransferBean.SPREAD_TRANSFER, mTargetPhone);

        transferFragment.setOnPhoneChangeListener(phone -> transferFragment1.setPhoneText(phone));

        transferFragment1.setOnPhoneChangeListener(phone -> transferFragment.setPhoneText(phone));

        mFragments.add(transferFragment);//销售积分互转
        mFragments.add(transferFragment1);//推广积分互转

        List<String> titles = new ArrayList<>();
        titles.add(TransferBean.SALE_TRANSFER);//销售积分互转
        titles.add(TransferBean.SPREAD_TRANSFER);//推广积分互转

        QMUIViewPageAdapter myFragmentAdapter = new QMUIViewPageAdapter(getSupportFragmentManager(), mFragments, titles);
        mViewPager.setAdapter(myFragmentAdapter);//设置适配器
        mTableLayout.setupWithViewPager(mViewPager, true);//设置上面的标题

        Log.d(TAG, "initFragment: " + mPosition);

        if (mPosition == 1)
            mViewPager.setCurrentItem(1);//选中第二个
    }

    private static final String TAG = "TransferActivity";

    @Override
    protected String getTitleName() {
        return "积分互转";
    }

    @Override
    protected String getRightTextName() {
        return "";
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.sale_layout_transfer;
    }

    @Override
    protected void onRefresh() {
    }

    @Override
    protected void onFloatBtClick() {
    }

}
