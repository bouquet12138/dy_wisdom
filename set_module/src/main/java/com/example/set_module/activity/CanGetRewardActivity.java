package com.example.set_module.activity;

import android.graphics.Color;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.SizeUtils;
import com.example.base_lib.view.SpacesItemDecoration;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.java_bean.SetRecordBean;
import com.example.set_module.R;
import com.example.set_module.adapter.CanGetAdapter;
import com.example.set_module.contract.CanGetRewardContract;
import com.example.set_module.presenter.CanGetRewardPresenter;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

import java.util.List;

/**
 * 得到可领奖励
 */
@Route(path = ARouterContract.SET_CAN_GET_REWARD)
public class CanGetRewardActivity extends AppMvpBaseActivity implements CanGetRewardContract.IView {

    private CanGetAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private boolean mBottomNoMoreData;

    private CanGetRewardPresenter mPresenter = new CanGetRewardPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        mPresenter.attachView(this);//绑定一下
        mPresenter.initCanGetReward();
    }

    /**
     * 初始化view
     */
    private void initView() {
        mRecyclerView = mNormalView.findViewById(R.id.recyclerView);//recyclerView
    }

    @Override
    protected String getTitleName() {
        return "可领奖励";
    }

    @Override
    protected String getRightTextName() {
        return null;
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.set_layout_can_get_reward;
    }

    @Override
    protected void onRefresh() {
        mPresenter.initCanGetReward();//初始化一下
    }

    @Override
    protected void onFloatBtClick() {

    }


    @Override
    public void refreshSetRecordList(List<SetRecordBean> setRecordBeans) {
        if (mAdapter == null) {
            mAdapter = new CanGetAdapter(setRecordBeans);
            mRecyclerView.setAdapter(mAdapter);//设置适配器
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(SizeUtils.dp2px(5)));
            initRefreshListener();//初始化刷新监听
        } else {
            mAdapter.notifyDataSetChanged();//唤醒数据更新
        }
    }

    private void initRefreshListener() {
        mSmartRefreshLayout.setPrimaryColors(getResources().getColor(R.color.app_theme_color));
        mSmartRefreshLayout.setEnableLoadMore(true);//加载更多可用
        mSmartRefreshLayout.setEnableHeaderTranslationContent(true);
        mSmartRefreshLayout.setEnablePureScrollMode(false);//不是只滑动

        TaurusHeader header = new TaurusHeader(this);
        mSmartRefreshLayout.setRefreshHeader(header);//设置头布局

        ClassicsFooter footer = new ClassicsFooter(this);//底部
        footer.setBackgroundColor(Color.WHITE);//白色
        mSmartRefreshLayout.setRefreshFooter(footer);//底部布局

        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMoreCanGetReward();//加载更多可领奖励
        });
    }

    @Override
    public void setFootNoMoreData() {
        mSmartRefreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
        mBottomNoMoreData = true;//底部没有更多数据
    }

    @Override
    public void completeLoadMore() {
        if (mBottomNoMoreData)
            setFootNoMoreData();//没有更多数据
        super.completeRefresh();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }
}
