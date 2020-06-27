package com.example.common_lib.fragment;


import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common_lib.R;
import com.example.common_lib.base.MVPQMUIFragment;
import com.example.common_lib.contract.LoadMoreContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class LoadMoreFragment extends MVPQMUIFragment implements LoadMoreContract.IView {

    protected SmartRefreshLayout mSmartRefreshLayout;//刷新view
    protected RecyclerView mRecyclerView;//recyclerView

    protected ViewGroup mNetErrorLayout;//网络错误布局
    protected Button mRefresh;//刷新按钮

    protected boolean mBottomNoMoreData;

    public LoadMoreFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentViewId() {
        return R.layout.common_fragment_load_more;
    }

    @Override
    protected void initAllMembersView() {
        initView();//初始化一下view
        initListener();
    }

    /**
     * 初始化view
     */
    private void initView() {
        mSmartRefreshLayout = mView.findViewById(R.id.smartRefreshLayout);
        mSmartRefreshLayout.closeHeaderOrFooter();
        // mSmartRefreshLayout.setRefreshHeader(null);//没有刷新
        mRecyclerView = mView.findViewById(R.id.recyclerView);

        mNetErrorLayout = mView.findViewById(R.id.netErrorLayout);
        mRefresh = mView.findViewById(R.id.refresh);
        showNormalView();//展示正常view
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mRefresh.setOnClickListener(v -> {
            showNormalView();//展示正常view
            onRefreshBtClick();
        });
    }

    /**
     * 刷新监听 和 加载更多的监听
     */
    protected void initRefreshListener() {

        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            showNormalView();//展示正常view
            onLoadMore();//加载更多
        });

        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            new Handler().postDelayed(() -> {
                mSmartRefreshLayout.finishRefresh();//完成刷新
                if (mBottomNoMoreData)
                    setFootNoMoreData();//没有更多数据
            }, 1000);
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
        mSmartRefreshLayout.finishLoadMore(500);//结束加载更多
    }

    /**
     * 展示网络错误
     */
    @Override
    public void showNetError() {
        mNetErrorLayout.setVisibility(View.VISIBLE);//网络错误可见
        mSmartRefreshLayout.setVisibility(View.GONE);//刷新view不可见
    }

    /**
     * 展示正常view
     */
    @Override
    public void showNormalView() {
        mSmartRefreshLayout.setVisibility(View.VISIBLE);//刷新view可见
        mNetErrorLayout.setVisibility(View.GONE);//网络错误不可见
    }


}
